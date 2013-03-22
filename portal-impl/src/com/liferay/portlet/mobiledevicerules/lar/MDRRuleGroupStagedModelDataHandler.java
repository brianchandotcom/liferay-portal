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
import com.liferay.portal.kernel.lar.StagedModelPathUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupLocalServiceUtil;
import com.liferay.portlet.mobiledevicerules.service.persistence.MDRRuleGroupUtil;

/**
 * @author Mate Thurzo
 */
public class MDRRuleGroupStagedModelDataHandler
	extends BaseStagedModelDataHandler<MDRRuleGroup> {

	@Override
	public String getClassName() {
		return MDRRuleGroup.class.getName();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, Element[] elements,
			MDRRuleGroup ruleGroup)
		throws Exception {

		Element ruleGroupsElement = elements[0];

		Element ruleGroupElement = ruleGroupsElement.addElement("rule-group");

		portletDataContext.addClassedModel(
			ruleGroupElement, StagedModelPathUtil.getPath(ruleGroup), ruleGroup,
			MDRPortletDataHandler.NAMESPACE);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, Element ruleGroupElement,
			MDRRuleGroup ruleGroup)
		throws Exception {

		long userId = portletDataContext.getUserId(ruleGroup.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			ruleGroup, MDRPortletDataHandler.NAMESPACE);

		serviceContext.setUserId(userId);

		MDRRuleGroup importedRuleGroup = null;

		if (portletDataContext.isDataStrategyMirror()) {
			MDRRuleGroup existingRuleGroup = MDRRuleGroupUtil.fetchByUUID_G(
				ruleGroup.getUuid(), portletDataContext.getScopeGroupId());

			if (existingRuleGroup == null) {
				serviceContext.setUuid(ruleGroup.getUuid());

				importedRuleGroup = MDRRuleGroupLocalServiceUtil.addRuleGroup(
					portletDataContext.getScopeGroupId(),
					ruleGroup.getNameMap(), ruleGroup.getDescriptionMap(),
					serviceContext);
			}
			else {
				importedRuleGroup =
					MDRRuleGroupLocalServiceUtil.updateRuleGroup(
						existingRuleGroup.getRuleGroupId(),
						ruleGroup.getNameMap(), ruleGroup.getDescriptionMap(),
						serviceContext);
			}
		}
		else {
			importedRuleGroup = MDRRuleGroupLocalServiceUtil.addRuleGroup(
				portletDataContext.getScopeGroupId(), ruleGroup.getNameMap(),
				ruleGroup.getDescriptionMap(), serviceContext);
		}

		portletDataContext.importClassedModel(
			ruleGroup, importedRuleGroup, MDRPortletDataHandler.NAMESPACE);
	}

}