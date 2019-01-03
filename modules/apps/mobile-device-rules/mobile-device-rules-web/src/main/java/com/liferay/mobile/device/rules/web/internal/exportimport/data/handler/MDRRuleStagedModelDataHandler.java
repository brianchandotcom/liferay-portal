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

package com.liferay.mobile.device.rules.web.internal.exportimport.data.handler;

import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.mobile.device.rules.model.MDRRule;
import com.liferay.mobile.device.rules.model.MDRRuleGroup;
import com.liferay.mobile.device.rules.service.MDRRuleGroupLocalService;
import com.liferay.mobile.device.rules.service.MDRRuleLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class MDRRuleStagedModelDataHandler
	extends BaseStagedModelDataHandler<MDRRule> {

	public static final String[] CLASS_NAMES = {MDRRule.class.getName()};

	@Override
	public void deleteStagedModel(MDRRule rule) {
		_mdrRuleLocalService.deleteRule(rule);
	}

	@Override
	public void deleteStagedModel(
		String uuid, long groupId, String className, String extraData) {

		MDRRule rule = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (rule != null) {
			deleteStagedModel(rule);
		}
	}

	@Override
	public MDRRule fetchStagedModelByUuidAndGroupId(String uuid, long groupId) {
		return _mdrRuleLocalService.fetchMDRRuleByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<MDRRule> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _mdrRuleLocalService.getMDRRulesByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<MDRRule>());
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(MDRRule rule) {
		return rule.getNameCurrentValue();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, MDRRule rule)
		throws Exception {

		MDRRuleGroup ruleGroup = _mdrRuleGroupLocalService.getRuleGroup(
			rule.getRuleGroupId());

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, rule, ruleGroup,
			PortletDataContext.REFERENCE_TYPE_PARENT);

		Element ruleElement = portletDataContext.getExportDataElement(rule);

		portletDataContext.addClassedModel(
			ruleElement, ExportImportPathUtil.getModelPath(rule), rule);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, MDRRule rule)
		throws Exception {

		Map<Long, Long> ruleGroupIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				MDRRuleGroup.class);

		long ruleGroupId = MapUtil.getLong(
			ruleGroupIds, rule.getRuleGroupId(), rule.getRuleGroupId());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			rule);

		serviceContext.setUserId(
			portletDataContext.getUserId(rule.getUserUuid()));

		MDRRule importedRule = null;

		if (portletDataContext.isDataStrategyMirror()) {
			MDRRule existingRule = fetchStagedModelByUuidAndGroupId(
				rule.getUuid(), portletDataContext.getScopeGroupId());

			if (existingRule == null) {
				serviceContext.setUuid(rule.getUuid());

				importedRule = _mdrRuleLocalService.addRule(
					ruleGroupId, rule.getNameMap(), rule.getDescriptionMap(),
					rule.getType(), rule.getTypeSettingsProperties(),
					serviceContext);
			}
			else {
				importedRule = _mdrRuleLocalService.updateRule(
					existingRule.getRuleId(), rule.getNameMap(),
					rule.getDescriptionMap(), rule.getType(),
					rule.getTypeSettingsProperties(), serviceContext);
			}
		}
		else {
			importedRule = _mdrRuleLocalService.addRule(
				ruleGroupId, rule.getNameMap(), rule.getDescriptionMap(),
				rule.getType(), rule.getTypeSettingsProperties(),
				serviceContext);
		}

		portletDataContext.importClassedModel(rule, importedRule);
	}

	@Reference(unbind = "-")
	protected void setMDRRuleGroupLocalService(
		MDRRuleGroupLocalService mdrRuleGroupLocalService) {

		_mdrRuleGroupLocalService = mdrRuleGroupLocalService;
	}

	@Reference(unbind = "-")
	protected void setMDRRuleLocalService(
		MDRRuleLocalService mdrRuleLocalService) {

		_mdrRuleLocalService = mdrRuleLocalService;
	}

	private MDRRuleGroupLocalService _mdrRuleGroupLocalService;
	private MDRRuleLocalService _mdrRuleLocalService;

}