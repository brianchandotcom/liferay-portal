/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.exportimport.data.handler;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.journal.exception.FeedTargetLayoutFriendlyUrlException;
import com.liferay.journal.internal.exportimport.creation.strategy.JournalCreationStrategy;
import com.liferay.journal.model.JournalFeed;
import com.liferay.journal.service.JournalFeedLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(service = StagedModelDataHandler.class)
public class JournalFeedStagedModelDataHandler
	extends BaseStagedModelDataHandler<JournalFeed> {

	public static final String[] CLASS_NAMES = {JournalFeed.class.getName()};

	@Override
	public void deleteStagedModel(JournalFeed feed) throws PortalException {
		_journalFeedLocalService.deleteFeed(feed);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		JournalFeed feed = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (feed != null) {
			deleteStagedModel(feed);
		}
	}

	@Override
	public JournalFeed fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _journalFeedLocalService.fetchJournalFeedByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<JournalFeed> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _journalFeedLocalService.getJournalFeedsByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<JournalFeed>());
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, JournalFeed feed)
		throws Exception {

		Element feedElement = portletDataContext.getExportDataElement(feed);

		DDMStructure ddmStructure = _ddmStructureLocalService.fetchStructure(
			feed.getDDMStructureId());

		if (ddmStructure != null) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, feed, ddmStructure,
				PortletDataContext.REFERENCE_TYPE_STRONG);
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to find DDM structure with id " +
						feed.getDDMStructureId());
			}
		}

		DDMTemplate ddmTemplate = _ddmTemplateLocalService.fetchTemplate(
			feed.getGroupId(), _portal.getClassNameId(DDMStructure.class),
			feed.getDDMTemplateKey());

		if (ddmTemplate != null) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, feed, ddmTemplate,
				PortletDataContext.REFERENCE_TYPE_STRONG);
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to find DDM template with key " +
						feed.getDDMTemplateKey());
			}
		}

		DDMTemplate rendererDDMTemplate =
			_ddmTemplateLocalService.fetchTemplate(
				feed.getGroupId(), _portal.getClassNameId(DDMStructure.class),
				feed.getDDMRendererTemplateKey());

		if (rendererDDMTemplate != null) {
			Element rendererDDMTemplateElement =
				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, feed, rendererDDMTemplate,
					PortletDataContext.REFERENCE_TYPE_STRONG);

			rendererDDMTemplateElement.addAttribute(
				"rendererDDMTemplate", "true");
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to find DDM template with key " +
						feed.getDDMRendererTemplateKey());
			}
		}

		_journalFeedExportImportContentProcessor.replaceExportContentReferences(
			portletDataContext, feed, StringPool.BLANK, true, true);

		portletDataContext.addClassedModel(
			feedElement, ExportImportPathUtil.getModelPath(feed), feed);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, JournalFeed feed)
		throws Exception {

		long userId = portletDataContext.getUserId(feed.getUserUuid());

		long authorId = _journalCreationStrategy.getAuthorUserId(
			portletDataContext, feed);

		if (authorId != JournalCreationStrategy.USE_DEFAULT_USER_ID_STRATEGY) {
			userId = authorId;
		}

		_journalFeedExportImportContentProcessor.replaceImportContentReferences(
			portletDataContext, feed, StringPool.BLANK);

		String feedId = feed.getFeedId();

		boolean autoFeedId = false;

		if (Validator.isNumber(feedId)) {
			autoFeedId = true;
		}
		else {
			JournalFeed existingFeed = _journalFeedLocalService.fetchFeed(
				portletDataContext.getScopeGroupId(), feedId);

			if (existingFeed != null) {
				autoFeedId = true;
			}
		}

		Map<Long, Long> ddmStructureIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDMStructure.class);

		long parentDDMStructureId = MapUtil.getLong(
			ddmStructureIds, feed.getDDMStructureId(),
			feed.getDDMStructureId());

		Map<String, String> ddmTemplateKeys =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				DDMTemplate.class + ".ddmTemplateKey");

		String parentDDMTemplateKey = MapUtil.getString(
			ddmTemplateKeys, feed.getDDMTemplateKey(),
			feed.getDDMTemplateKey());
		String parentRendererDDMTemplateKey = MapUtil.getString(
			ddmTemplateKeys, feed.getDDMRendererTemplateKey(),
			feed.getDDMRendererTemplateKey());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			feed);

		JournalFeed importedFeed = null;

		try {
			if (portletDataContext.isDataStrategyMirror()) {
				JournalFeed existingFeed = fetchStagedModelByUuidAndGroupId(
					feed.getUuid(), portletDataContext.getScopeGroupId());

				if (existingFeed == null) {
					serviceContext.setUuid(feed.getUuid());

					importedFeed = _journalFeedLocalService.addFeed(
						userId, portletDataContext.getScopeGroupId(), feedId,
						autoFeedId, feed.getName(), feed.getDescription(),
						parentDDMStructureId, parentDDMTemplateKey,
						parentRendererDDMTemplateKey, feed.getDelta(),
						feed.getOrderByCol(), feed.getOrderByType(),
						feed.getTargetLayoutFriendlyUrl(),
						feed.getTargetPortletId(), feed.getContentField(),
						feed.getFeedFormat(), feed.getFeedVersion(),
						serviceContext);
				}
				else {
					importedFeed = _journalFeedLocalService.updateFeed(
						existingFeed.getGroupId(), existingFeed.getFeedId(),
						feed.getName(), feed.getDescription(),
						parentDDMStructureId, parentDDMTemplateKey,
						parentRendererDDMTemplateKey, feed.getDelta(),
						feed.getOrderByCol(), feed.getOrderByType(),
						feed.getTargetLayoutFriendlyUrl(),
						feed.getTargetPortletId(), feed.getContentField(),
						feed.getFeedFormat(), feed.getFeedVersion(),
						serviceContext);
				}
			}
			else {
				importedFeed = _journalFeedLocalService.addFeed(
					userId, portletDataContext.getScopeGroupId(), feedId,
					autoFeedId, feed.getName(), feed.getDescription(),
					parentDDMStructureId, parentDDMTemplateKey,
					parentRendererDDMTemplateKey, feed.getDelta(),
					feed.getOrderByCol(), feed.getOrderByType(),
					feed.getTargetLayoutFriendlyUrl(),
					feed.getTargetPortletId(), feed.getContentField(),
					feed.getFeedFormat(), feed.getFeedVersion(),
					serviceContext);
			}

			portletDataContext.importClassedModel(feed, importedFeed);

			if (!feedId.equals(importedFeed.getFeedId())) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"A feed with the ID ", feedId,
							" already exists. The new generated ID is ",
							importedFeed.getFeedId(), "."));
				}
			}
		}
		catch (FeedTargetLayoutFriendlyUrlException
					feedTargetLayoutFriendlyUrlException) {

			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"A feed with the ID ", feedId,
						" cannot be imported because layout with friendly URL ",
						feed.getTargetLayoutFriendlyUrl(), " does not exist: ",
						feedTargetLayoutFriendlyUrlException.getMessage()));
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalFeedStagedModelDataHandler.class);

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference
	private JournalCreationStrategy _journalCreationStrategy;

	@Reference(
		target = "(model.class.name=com.liferay.journal.model.JournalFeed)"
	)
	private ExportImportContentProcessor<String>
		_journalFeedExportImportContentProcessor;

	@Reference
	private JournalFeedLocalService _journalFeedLocalService;

	@Reference
	private Portal _portal;

}