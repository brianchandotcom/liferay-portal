/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.mobiledevicerules.lar;

import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.mobiledevicerules.model.MDRRule;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupLocalServiceUtil;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleLocalServiceUtil;
import com.liferay.portlet.mobiledevicerules.service.persistence.MDRRuleUtil;

import java.util.Map;

/**
 * @author Mate Thurzo
 */
public class MDRRuleStagedModelDataHandler
	extends BaseStagedModelDataHandler<MDRRule> {

	public static final String[] CLASS_NAMES = {MDRRule.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, MDRRule rule)
		throws Exception {

		MDRRuleGroup ruleGroup = MDRRuleGroupLocalServiceUtil.getRuleGroup(
			rule.getRuleGroupId());

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, ruleGroup);

		Element ruleElement = portletDataContext.getExportDataElement(rule);

		portletDataContext.addClassedModel(
			ruleElement, ExportImportPathUtil.getModelPath(rule), rule,
			MDRPortletDataHandler.NAMESPACE);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, MDRRule rule)
		throws Exception {

		String ruleGroupPath = ExportImportPathUtil.getModelPath(
			portletDataContext, MDRRuleGroup.class.getName(),
			rule.getRuleGroupId());

		MDRRuleGroup ruleGroup =
			(MDRRuleGroup)portletDataContext.getZipEntryAsObject(ruleGroupPath);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, ruleGroup);

		Map<Long, Long> ruleGroupIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				MDRRuleGroup.class);

		long ruleGroupId = MapUtil.getLong(
			ruleGroupIds, rule.getRuleGroupId(), rule.getRuleGroupId());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			rule, MDRPortletDataHandler.NAMESPACE);

		serviceContext.setUserId(
			portletDataContext.getUserId(rule.getUserUuid()));

		MDRRule importedRule = null;

		if (portletDataContext.isDataStrategyMirror()) {
			MDRRule existingRule = MDRRuleUtil.fetchByUUID_G(
				rule.getUuid(), portletDataContext.getScopeGroupId());

			if (existingRule == null) {
				serviceContext.setUuid(rule.getUuid());

				importedRule = MDRRuleLocalServiceUtil.addRule(
					ruleGroupId, rule.getNameMap(), rule.getDescriptionMap(),
					rule.getType(), rule.getTypeSettingsProperties(),
					serviceContext);
			}
			else {
				importedRule = MDRRuleLocalServiceUtil.updateRule(
					existingRule.getRuleId(), rule.getNameMap(),
					rule.getDescriptionMap(), rule.getType(),
					rule.getTypeSettingsProperties(), serviceContext);
			}
		}
		else {
			importedRule = MDRRuleLocalServiceUtil.addRule(
				ruleGroupId, rule.getNameMap(), rule.getDescriptionMap(),
				rule.getType(), rule.getTypeSettingsProperties(),
				serviceContext);
		}

		portletDataContext.importClassedModel(
			rule, importedRule, MDRPortletDataHandler.NAMESPACE);
	}

}