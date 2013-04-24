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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.mobile.device.rulegroup.action.impl.SiteRedirectActionHandler;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.mobiledevicerules.model.MDRAction;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance;
import com.liferay.portlet.mobiledevicerules.service.MDRActionLocalServiceUtil;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceLocalServiceUtil;
import com.liferay.portlet.mobiledevicerules.service.persistence.MDRActionUtil;

import java.util.Map;

/**
 * @author Mate Thurzo
 */
public class MDRActionStagedModelDataHandler
	extends BaseStagedModelDataHandler<MDRAction> {

	public static final String[] CLASS_NAMES = {MDRAction.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, MDRAction action)
		throws Exception {

		MDRRuleGroupInstance ruleGroupInstance =
			MDRRuleGroupInstanceLocalServiceUtil.getRuleGroupInstance(
				action.getRuleGroupInstanceId());

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, ruleGroupInstance);

		Element actionElement = portletDataContext.getExportDataElement(action);

		String type = action.getType();

		if (type.equals(SiteRedirectActionHandler.class.getName())) {
			UnicodeProperties typeSettingsProperties =
				action.getTypeSettingsProperties();

			long plid = GetterUtil.getLong(
				typeSettingsProperties.getProperty("plid"));

			try {
				Layout layout = LayoutLocalServiceUtil.getLayout(plid);

				actionElement.addAttribute("layout-uuid", layout.getUuid());
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to set the layout uuid of layout " + plid +
							". Site redirect may not match after import.",
						e);
				}
			}
		}

		portletDataContext.addClassedModel(
			actionElement, ExportImportPathUtil.getModelPath(action), action,
			MDRPortletDataHandler.NAMESPACE);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, MDRAction action)
		throws Exception {

		String ruleGroupInstancePath = ExportImportPathUtil.getModelPath(
			portletDataContext, MDRRuleGroupInstance.class.getName(),
			action.getRuleGroupInstanceId());

		MDRRuleGroupInstance ruleGroupInstance =
			(MDRRuleGroupInstance)portletDataContext.getZipEntryAsObject(
				ruleGroupInstancePath);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, ruleGroupInstance);

		Map<Long, Long> ruleGroupInstanceIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				MDRRuleGroupInstance.class);

		long ruleGroupInstanceId = MapUtil.getLong(
			ruleGroupInstanceIds, action.getRuleGroupInstanceId(),
			action.getRuleGroupInstanceId());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			action, MDRPortletDataHandler.NAMESPACE);

		serviceContext.setUserId(
			portletDataContext.getUserId(action.getUserUuid()));

		Element element = portletDataContext.getImportDataStagedModelElement(
			action);

		validateLayout(element, action);

		MDRAction importedAction = null;

		if (portletDataContext.isDataStrategyMirror()) {
			MDRAction existingAction = MDRActionUtil.fetchByUUID_G(
				action.getUuid(), portletDataContext.getScopeGroupId());

			if (existingAction == null) {
				serviceContext.setUuid(action.getUuid());

				importedAction = MDRActionLocalServiceUtil.addAction(
					ruleGroupInstanceId, action.getNameMap(),
					action.getDescriptionMap(), action.getType(),
					action.getTypeSettingsProperties(), serviceContext);
			}
			else {
				importedAction = MDRActionLocalServiceUtil.updateAction(
					existingAction.getActionId(), action.getNameMap(),
					action.getDescriptionMap(), action.getType(),
					action.getTypeSettingsProperties(), serviceContext);
			}
		}
		else {
			importedAction = MDRActionLocalServiceUtil.addAction(
				ruleGroupInstanceId, action.getNameMap(),
				action.getDescriptionMap(), action.getType(),
				action.getTypeSettingsProperties(), serviceContext);
		}

		portletDataContext.importClassedModel(
			action, importedAction, MDRPortletDataHandler.NAMESPACE);
	}

	protected void validateLayout(Element actionElement, MDRAction action) {
		String type = action.getType();

		if (!type.equals(SiteRedirectActionHandler.class.getName())) {
			return;
		}

		String layoutUuid = actionElement.attributeValue("layout-uuid");

		if (Validator.isNull(layoutUuid)) {
			return;
		}

		UnicodeProperties typeSettingsProperties =
			action.getTypeSettingsProperties();

		long groupId = GetterUtil.getLong(
			typeSettingsProperties.getProperty("groupId"));
		boolean privateLayout = GetterUtil.getBoolean(
			actionElement.attributeValue("private-layout"));

		try {
			Layout layout = LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
				layoutUuid, groupId, privateLayout);

			typeSettingsProperties.setProperty(
				"plid", String.valueOf(layout.getPlid()));
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to find layout with uuid " + layoutUuid +
						" in group " + groupId + ". Site redirect may not " +
							"match target layout.",
					e);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		MDRActionStagedModelDataHandler.class);

}