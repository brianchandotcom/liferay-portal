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
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.StagedModelPathUtil;
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

	@Override
	public String getClassName() {
		return MDRRule.class.getName();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, Element[] elements,
			MDRRule rule)
		throws Exception {

		Element ruleGroupsElement = elements[0];

		MDRRuleGroup ruleGroup = MDRRuleGroupLocalServiceUtil.getRuleGroup(
			rule.getRuleGroupId());

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, ruleGroupsElement, ruleGroup);

		Element rulesElement = elements[1];

		Element ruleElement = rulesElement.addElement("rule");

		portletDataContext.addClassedModel(
			ruleElement, StagedModelPathUtil.getPath(rule), rule,
			MDRPortletDataHandler.NAMESPACE);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, Element element,
			MDRRule rule)
		throws Exception {

		String ruleGroupPath = StagedModelPathUtil.getPath(
			portletDataContext, MDRRuleGroup.class.getName(),
			rule.getRuleGroupId());

		MDRRuleGroup ruleGroup =
			(MDRRuleGroup)portletDataContext.getZipEntryAsObject(ruleGroupPath);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, element, ruleGroup);

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