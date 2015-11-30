/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.device.rules.lar;

import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.mobile.device.rules.model.MDRRuleGroup;
import com.liferay.mobile.device.rules.service.MDRRuleGroupLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.exportimport.lar.ExportImportPathUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;
import com.liferay.portlet.exportimport.lar.StagedModelModifiedDateComparator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class MDRRuleGroupStagedModelDataHandler
	extends BaseStagedModelDataHandler<MDRRuleGroup> {

	public static final String[] CLASS_NAMES = {MDRRuleGroup.class.getName()};

	@Override
	public void deleteStagedModel(MDRRuleGroup ruleGroup) {
		_mdrRuleGroupLocalService.deleteRuleGroup(ruleGroup);
	}

	@Override
	public void deleteStagedModel(
		String uuid, long groupId, String className, String extraData) {

		MDRRuleGroup ruleGroup = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (ruleGroup != null) {
			deleteStagedModel(ruleGroup);
		}
	}

	@Override
	public MDRRuleGroup fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _mdrRuleGroupLocalService.fetchMDRRuleGroupByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<MDRRuleGroup> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _mdrRuleGroupLocalService.getMDRRuleGroupsByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<MDRRuleGroup>());
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(MDRRuleGroup ruleGroup) {
		return ruleGroup.getNameCurrentValue();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, MDRRuleGroup ruleGroup)
		throws Exception {

		Element ruleGroupElement = portletDataContext.getExportDataElement(
			ruleGroup);

		portletDataContext.addClassedModel(
			ruleGroupElement, ExportImportPathUtil.getModelPath(ruleGroup),
			ruleGroup);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, MDRRuleGroup ruleGroup)
		throws Exception {

		long userId = portletDataContext.getUserId(ruleGroup.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			ruleGroup);

		serviceContext.setUserId(userId);

		MDRRuleGroup importedRuleGroup = null;

		if (portletDataContext.isDataStrategyMirror()) {
			MDRRuleGroup existingRuleGroup = fetchStagedModelByUuidAndGroupId(
				ruleGroup.getUuid(), portletDataContext.getScopeGroupId());

			if (existingRuleGroup == null) {
				serviceContext.setUuid(ruleGroup.getUuid());

				importedRuleGroup = _mdrRuleGroupLocalService.addRuleGroup(
					portletDataContext.getScopeGroupId(),
					ruleGroup.getNameMap(), ruleGroup.getDescriptionMap(),
					serviceContext);
			}
			else {
				importedRuleGroup = _mdrRuleGroupLocalService.updateRuleGroup(
					existingRuleGroup.getRuleGroupId(), ruleGroup.getNameMap(),
					ruleGroup.getDescriptionMap(), serviceContext);
			}
		}
		else {
			importedRuleGroup = _mdrRuleGroupLocalService.addRuleGroup(
				portletDataContext.getScopeGroupId(), ruleGroup.getNameMap(),
				ruleGroup.getDescriptionMap(), serviceContext);
		}

		portletDataContext.importClassedModel(ruleGroup, importedRuleGroup);
	}

	@Reference(unbind = "-")
	protected void setMDRRuleGroupLocalService(
		MDRRuleGroupLocalService mdrRuleGroupLocalService) {

		_mdrRuleGroupLocalService = mdrRuleGroupLocalService;
	}

	private volatile MDRRuleGroupLocalService _mdrRuleGroupLocalService;

}