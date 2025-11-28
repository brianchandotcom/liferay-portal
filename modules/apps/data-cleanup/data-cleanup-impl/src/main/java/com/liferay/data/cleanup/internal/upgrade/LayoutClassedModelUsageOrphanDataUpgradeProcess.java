/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.cleanup.internal.upgrade;

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.manager.ContentManager;
import com.liferay.layout.model.LayoutClassedModelUsage;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureRelLocalService;
import com.liferay.layout.service.LayoutClassedModelUsageLocalService;
import com.liferay.petra.function.UnsafeBiConsumer;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Georgel Pop
 */
public class LayoutClassedModelUsageOrphanDataUpgradeProcess
	extends BaseUpgradeProcess {

	public LayoutClassedModelUsageOrphanDataUpgradeProcess(
		ClassNameLocalService classNameLocalService,
		ContentManager contentManager,
		CTCollectionLocalService ctCollectionLocalService,
		FragmentEntryLinkLocalService fragmentEntryLinkLocalService,
		LayoutClassedModelUsageLocalService layoutClassedModelUsageLocalService,
		LayoutPageTemplateStructureLocalService
			layoutPageTemplateStructureLocalService,
		LayoutPageTemplateStructureRelLocalService
			layoutPageTemplateStructureRelLocalService) {

		_classNameLocalService = classNameLocalService;
		_contentManager = contentManager;
		_ctCollectionLocalService = ctCollectionLocalService;
		_fragmentEntryLinkLocalService = fragmentEntryLinkLocalService;
		_layoutClassedModelUsageLocalService =
			layoutClassedModelUsageLocalService;
		_layoutPageTemplateStructureLocalService =
			layoutPageTemplateStructureLocalService;
		_layoutPageTemplateStructureRelLocalService =
			layoutPageTemplateStructureRelLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_cleanUpdateLayoutClassedModelUsages();
	}

	private void _cleanUpdateLayoutClassedModelUsages() throws Exception {
		_processLayoutClassedModelUsages(
			_classNameLocalService.getClassNameId(
				FragmentEntryLink.class.getName()),
			"fragmentEntryLinkId", "FragmentEntryLink",
			this::_updateLayoutClassedModelUsagesForFragmentEntryLinks);
		_processLayoutClassedModelUsages(
			_classNameLocalService.getClassNameId(
				LayoutPageTemplateStructure.class.getName()),
			"layoutPageTemplateStructureId", "LayoutPageTemplateStructure",
			this::
				_updateLayoutClassedModelUsagesForLayoutPageTemplateStructure);
	}

	private void _deleteOrphanLayoutClassedModelUsage(
		CTCollection ctCollection, long ctCollectionId,
		long layoutClassedModelUsageId) {

		try {
			LayoutClassedModelUsage layoutClassedModelUsage =
				_layoutClassedModelUsageLocalService.
					fetchLayoutClassedModelUsage(layoutClassedModelUsageId);

			if ((layoutClassedModelUsage == null) ||
				_isCTCollectionReadOnly(ctCollection)) {

				try (PreparedStatement preparedStatement =
						connection.prepareStatement(
							"delete from LayoutClassedModelUsage where " +
								"ctCollectionId = ? and " +
									"layoutClassedModelUsageId = ?")) {

					preparedStatement.setLong(1, ctCollectionId);
					preparedStatement.setLong(2, layoutClassedModelUsageId);

					preparedStatement.executeUpdate();
				}
			}
			else {
				try (SafeCloseable safeCloseable =
						CTCollectionThreadLocal.
							setCTCollectionIdWithSafeCloseable(
								ctCollectionId)) {

					_layoutClassedModelUsageLocalService.
						deleteLayoutClassedModelUsage(layoutClassedModelUsage);
				}
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Deleted orphaned layout classed model usage ",
						layoutClassedModelUsageId,
						" with change tracking collection ID ",
						ctCollectionId));
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to delete orphaned layout classed model usage ",
						layoutClassedModelUsageId,
						" with change tracking collection ID ", ctCollectionId),
					exception);
			}
		}
	}

	private boolean _isCTCollectionReadOnly(CTCollection ctCollection) {
		if ((ctCollection != null) && ctCollection.isReadOnly()) {
			return true;
		}

		return false;
	}

	private void _processLayoutClassedModelUsages(
			long classNameId, String keyColumnName, String tableName,
			UnsafeBiConsumer<Long, Long, Exception> unsafeBiConsumer)
		throws Exception {

		Map<Long, Map<Long, Set<Long>>> ctCollectionIdsMaps = new HashMap<>();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				SQLTransformer.transform(
					StringBundler.concat(
						"select ctCollectionId, layoutClassedModelUsageId, ",
						"groupId, plid from LayoutClassedModelUsage where ",
						"containerType = ? and (plid <> (select plid from ",
						tableName, " where ", keyColumnName,
						" = CAST_LONG(containerKey) and ",
						"LayoutClassedModelUsage.ctCollectionId = ", tableName,
						".ctCollectionId) or (not exists (select 1 from ",
						tableName, " where ", keyColumnName,
						" = CAST_LONG(containerKey) and ",
						"LayoutClassedModelUsage.ctCollectionId = ", tableName,
						".ctCollectionId)))")))) {

			preparedStatement.setLong(1, classNameId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					long ctCollectionId = resultSet.getLong("ctCollectionId");
					long layoutClassedModelUsageId = resultSet.getLong(
						"layoutClassedModelUsageId");

					CTCollection ctCollection =
						_ctCollectionLocalService.fetchCTCollection(
							ctCollectionId);

					_deleteOrphanLayoutClassedModelUsage(
						ctCollection, ctCollectionId,
						layoutClassedModelUsageId);

					if (_isCTCollectionReadOnly(ctCollection)) {
						continue;
					}

					long groupId = resultSet.getLong("groupId");
					long plid = resultSet.getLong("plid");

					Map<Long, Set<Long>> ctCollectionIdsMap =
						ctCollectionIdsMaps.computeIfAbsent(
							groupId, key -> new HashMap<>());

					Set<Long> plids = ctCollectionIdsMap.computeIfAbsent(
						ctCollectionId, key -> new HashSet<>());

					plids.add(plid);
				}
			}
		}

		_processLayoutClassedModelUsages(ctCollectionIdsMaps, unsafeBiConsumer);
	}

	private void _processLayoutClassedModelUsages(
		Map<Long, Map<Long, Set<Long>>> ctCollectionIdsMaps,
		UnsafeBiConsumer<Long, Long, Exception> unsafeBiConsumer) {

		for (Map.Entry<Long, Map<Long, Set<Long>>> entry1 :
				ctCollectionIdsMaps.entrySet()) {

			long groupId = entry1.getKey();
			Map<Long, Set<Long>> ctCollectionIdsMap = entry1.getValue();

			for (Map.Entry<Long, Set<Long>> entry2 :
					ctCollectionIdsMap.entrySet()) {

				long ctCollectionId = entry2.getKey();
				Set<Long> plids = entry2.getValue();

				for (long plid : plids) {
					try (SafeCloseable safeCloseable =
							CTCollectionThreadLocal.
								setCTCollectionIdWithSafeCloseable(
									ctCollectionId)) {

						unsafeBiConsumer.accept(groupId, plid);

						if (_log.isDebugEnabled()) {
							_log.debug(
								StringBundler.concat(
									"Updated layout classed model usage with ",
									"change tracking collection ID ",
									ctCollectionId, " and PLID ", plid));
						}
					}
					catch (Exception exception) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								StringBundler.concat(
									"Unable to update layout classed model ",
									"usage with change tracking collection ID ",
									ctCollectionId, " and PLID ", plid),
								exception);
						}
					}
				}
			}
		}
	}

	private <T> void _updateLayoutClassedModelUsages(
		Consumer<T> consumer, Function<T, ?> function, List<T> items,
		String logPrefix) {

		for (T item : items) {
			if (item == null) {
				continue;
			}

			try {
				consumer.accept(item);
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(logPrefix + function.apply(item), exception);
				}
			}
		}
	}

	private void _updateLayoutClassedModelUsagesForFragmentEntryLinks(
		long groupId, long plid) {

		_updateLayoutClassedModelUsages(
			_contentManager::updateLayoutClassedModelUsage,
			FragmentEntryLink::getFragmentEntryLinkId,
			_fragmentEntryLinkLocalService.getFragmentEntryLinksByPlid(
				groupId, plid),
			"Unable to update layout classed model usages for fragment entry " +
				"link ");
	}

	private void _updateLayoutClassedModelUsagesForLayoutPageTemplateStructure(
		long groupId, long plid) {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(groupId, plid);

		if (layoutPageTemplateStructure == null) {
			return;
		}

		_updateLayoutClassedModelUsages(
			_contentManager::updateLayoutClassedModelUsage,
			LayoutPageTemplateStructureRel::getLayoutPageTemplateStructureRelId,
			_layoutPageTemplateStructureRelLocalService.
				getLayoutPageTemplateStructureRels(
					layoutPageTemplateStructure.
						getLayoutPageTemplateStructureId()),
			"Unable to update layout classed model usages for layout page " +
				"template structure relationship ");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutClassedModelUsageOrphanDataUpgradeProcess.class);

	private final ClassNameLocalService _classNameLocalService;
	private final ContentManager _contentManager;
	private final CTCollectionLocalService _ctCollectionLocalService;
	private final FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;
	private final LayoutClassedModelUsageLocalService
		_layoutClassedModelUsageLocalService;
	private final LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;
	private final LayoutPageTemplateStructureRelLocalService
		_layoutPageTemplateStructureRelLocalService;

}