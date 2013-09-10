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

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Layout;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.mobiledevicerules.model.MDRAction;
import com.liferay.portlet.mobiledevicerules.model.MDRRule;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupLocalServiceUtil;
import com.liferay.portlet.mobiledevicerules.service.permission.MDRPermission;
import com.liferay.portlet.mobiledevicerules.service.persistence.MDRActionExportActionableDynamicQuery;
import com.liferay.portlet.mobiledevicerules.service.persistence.MDRRuleExportActionableDynamicQuery;
import com.liferay.portlet.mobiledevicerules.service.persistence.MDRRuleGroupExportActionableDynamicQuery;
import com.liferay.portlet.mobiledevicerules.service.persistence.MDRRuleGroupInstanceExportActionableDynamicQuery;

import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 * @author Mate Thurzo
 */
public class MDRPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "mobile_device_rules";

	public MDRPortletDataHandler() {
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(MDRAction.class, Layout.class),
			new StagedModelType(MDRRule.class),
			new StagedModelType(MDRRuleGroup.class),
			new StagedModelType(MDRRuleGroupInstance.class, Layout.class));
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "rules", true, false, null, MDRRule.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "actions", true, false, null,
				MDRAction.class.getName(), Layout.class.getName()));
		setImportControls(getExportControls());
		setPublishToLiveByDefault(
			PropsValues.MOBILE_DEVICE_RULES_PUBLISH_TO_LIVE_BY_DEFAULT);
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				MDRPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		MDRRuleGroupLocalServiceUtil.deleteRuleGroups(
			portletDataContext.getGroupId());

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			final PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPortletPermissions(MDRPermission.RESOURCE_NAME);

		Element rootElement = addExportDataRootElement(portletDataContext);

		if (portletDataContext.getBooleanParameter(NAMESPACE, "rules")) {
			ActionableDynamicQuery rulesActionableDynamicQuery =
				new MDRRuleExportActionableDynamicQuery(portletDataContext);

			rulesActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "actions")) {
			ActionableDynamicQuery actionsActionableDynamicQuery =
				new MDRActionExportActionableDynamicQuery(portletDataContext) {

				@Override
				protected StagedModelType getStagedModelType() {
					return new StagedModelType(
						PortalUtil.getClassNameId(MDRAction.class),
						StagedModelType.REFERRER_CLASS_NAME_ID_ALL);
				}

			};

			actionsActionableDynamicQuery.performActions();
		}

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPortletPermissions(
			MDRPermission.RESOURCE_NAME);

		if (portletDataContext.getBooleanParameter(NAMESPACE, "rules")) {
			Element rulesElement = portletDataContext.getImportDataGroupElement(
				MDRRule.class);

			List<Element> ruleElements = rulesElement.elements();

			for (Element ruleElement : ruleElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, ruleElement);
			}
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "actions")) {
			Element actionsElement =
				portletDataContext.getImportDataGroupElement(MDRAction.class);

			List<Element> actionElements = actionsElement.elements();

			for (Element actionElement : actionElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, actionElement);
			}
		}

		return null;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		ActionableDynamicQuery actionsActionableDynamicQuery =
			new MDRActionExportActionableDynamicQuery(portletDataContext) {

			@Override
			protected StagedModelType getStagedModelType() {
				return new StagedModelType(
					MDRAction.class.getName(), Layout.class.getName());
			}

		};

		actionsActionableDynamicQuery.performCount();

		ActionableDynamicQuery rulesActionableDynamicQuery =
			new MDRRuleExportActionableDynamicQuery(portletDataContext);

		rulesActionableDynamicQuery.performCount();

		ActionableDynamicQuery ruleGroupsActionableDynamicQuery =
			new MDRRuleGroupExportActionableDynamicQuery(portletDataContext);

		ruleGroupsActionableDynamicQuery.performCount();

		ActionableDynamicQuery ruleGroupInstancesActionableDynamicQuery =
			new MDRRuleGroupInstanceExportActionableDynamicQuery(
				portletDataContext) {

				@Override
				protected StagedModelType getStagedModelType() {
					return new StagedModelType(
						MDRRuleGroupInstance.class.getName(),
						Layout.class.getName());
				}
			};

		ruleGroupInstancesActionableDynamicQuery.performCount();
	}

}