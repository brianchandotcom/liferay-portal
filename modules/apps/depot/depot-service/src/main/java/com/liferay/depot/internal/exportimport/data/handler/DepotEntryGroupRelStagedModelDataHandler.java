/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.internal.exportimport.data.handler;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.model.DepotEntryGroupRel;
import com.liferay.depot.service.DepotEntryGroupRelLocalService;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 * @author Roberto Díaz
 */
@Component(service = StagedModelDataHandler.class)
public class DepotEntryGroupRelStagedModelDataHandler
	extends BaseStagedModelDataHandler<DepotEntryGroupRel> {

	public static final String[] CLASS_NAMES = {
		DepotEntryGroupRel.class.getName()
	};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			DepotEntryGroupRel depotEntryGroupRel)
		throws Exception {

		Element depotEntryGroupRelElement =
			portletDataContext.getExportDataElement(depotEntryGroupRel);

		_populateDepotEntryGroupRelElement(
			depotEntryGroupRelElement, depotEntryGroupRel);

		portletDataContext.addClassedModel(
			depotEntryGroupRelElement,
			ExportImportPathUtil.getModelPath(depotEntryGroupRel),
			depotEntryGroupRel);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			DepotEntryGroupRel depotEntryGroupRel)
		throws Exception {

		DepotEntryGroupRel importedDepotEntryGroupRel =
			(DepotEntryGroupRel)depotEntryGroupRel.clone();

		importedDepotEntryGroupRel.setGroupId(
			portletDataContext.getScopeGroupId());
		importedDepotEntryGroupRel.setToGroupId(
			portletDataContext.getScopeGroupId());

		DepotEntryGroupRel existingDepotEntryGroupRel =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				depotEntryGroupRel.getUuid(),
				portletDataContext.getScopeGroupId());

		if ((existingDepotEntryGroupRel == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			Element importDataElement = portletDataContext.getImportDataElement(
				importedDepotEntryGroupRel);

			DepotEntry depotEntry =
				_depotEntryLocalService.fetchGroupDepotEntry(
					GetterUtil.getLong(
						importDataElement.attributeValue(
							"depot-entry-live-group-id")));

			if (depotEntry == null) {
				depotEntry = _getDepotEntryByLiveGroupKey(
					importDataElement, portletDataContext);

				if (depotEntry == null) {
					return;
				}
			}

			importedDepotEntryGroupRel.setDepotEntryId(
				depotEntry.getDepotEntryId());

			importedDepotEntryGroupRel = _stagedModelRepository.addStagedModel(
				portletDataContext, importedDepotEntryGroupRel);
		}
		else {
			existingDepotEntryGroupRel.setDdmStructuresAvailable(
				importedDepotEntryGroupRel.isDdmStructuresAvailable());
			existingDepotEntryGroupRel.setSearchable(
				importedDepotEntryGroupRel.isSearchable());

			importedDepotEntryGroupRel =
				_depotEntryGroupRelLocalService.updateDepotEntryGroupRel(
					existingDepotEntryGroupRel);
		}

		portletDataContext.importClassedModel(
			depotEntryGroupRel, importedDepotEntryGroupRel);
	}

	@Override
	protected StagedModelRepository<DepotEntryGroupRel>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	private DepotEntry _getDepotEntryByLiveGroupKey(
		Element importDataElement, PortletDataContext portletDataContext) {

		DepotEntry depotEntry = null;

		String depotEntryLiveGroupKey = GetterUtil.getString(
			importDataElement.attributeValue("depot-entry-live-group-key"));

		if (Validator.isNotNull(depotEntryLiveGroupKey)) {
			Group group = _groupLocalService.fetchGroup(
				portletDataContext.getCompanyId(), depotEntryLiveGroupKey);

			if (group != null) {
				depotEntry = _depotEntryLocalService.fetchGroupDepotEntry(
					group.getGroupId());
			}
		}

		if (depotEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to import DepotEntryGroupRel for DepotEntry ",
						"\"", depotEntryLiveGroupKey, "\""));
			}

			return null;
		}

		return depotEntry;
	}

	private void _populateDepotEntryGroupRelElement(
			Element depotEntryGroupRelElement,
			DepotEntryGroupRel depotEntryGroupRel)
		throws Exception {

		DepotEntry depotEntry = _depotEntryLocalService.getDepotEntry(
			depotEntryGroupRel.getDepotEntryId());

		Group group = depotEntry.getGroup();

		Group stagingGroup = group.getStagingGroup();

		if (stagingGroup != null) {
			depotEntryGroupRelElement.addAttribute(
				"depot-entry-live-group-id",
				String.valueOf(stagingGroup.getGroupId()));
			depotEntryGroupRelElement.addAttribute(
				"depot-entry-live-group-key", stagingGroup.getGroupKey());

			return;
		}

		long liveGroupId = group.getLiveGroupId();
		String liveGroupKey = group.getGroupKey();

		Group liveGroup = group.getLiveGroup();

		if (liveGroup != null) {
			liveGroupKey = liveGroup.getGroupKey();
		}

		if (group.isStagedRemotely()) {
			liveGroupId = group.getRemoteLiveGroupId();
			liveGroupKey = group.getGroupKey();
		}

		if (liveGroupId == GroupConstants.DEFAULT_LIVE_GROUP_ID) {
			liveGroupId = group.getGroupId();
		}

		depotEntryGroupRelElement.addAttribute(
			"depot-entry-live-group-id", String.valueOf(liveGroupId));
		depotEntryGroupRelElement.addAttribute(
			"depot-entry-live-group-key", liveGroupKey);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DepotEntryGroupRelStagedModelDataHandler.class);

	@Reference
	private DepotEntryGroupRelLocalService _depotEntryGroupRelLocalService;

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.depot.model.DepotEntryGroupRel)",
		unbind = "-"
	)
	private StagedModelRepository<DepotEntryGroupRel> _stagedModelRepository;

}