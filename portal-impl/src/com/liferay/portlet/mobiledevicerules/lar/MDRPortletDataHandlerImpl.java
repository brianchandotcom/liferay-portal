/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.mobile.device.rulegroup.action.impl.SiteRedirectActionHandler;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.mobiledevicerules.model.MDRAction;
import com.liferay.portlet.mobiledevicerules.model.MDRRule;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance;
import com.liferay.portlet.mobiledevicerules.service.MDRActionLocalServiceUtil;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceLocalServiceUtil;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupLocalServiceUtil;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleLocalServiceUtil;
import com.liferay.portlet.mobiledevicerules.service.persistence.MDRActionUtil;
import com.liferay.portlet.mobiledevicerules.service.persistence.MDRRuleGroupInstanceUtil;
import com.liferay.portlet.mobiledevicerules.service.persistence.MDRRuleGroupUtil;
import com.liferay.portlet.mobiledevicerules.service.persistence.MDRRuleUtil;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class MDRPortletDataHandlerImpl extends BasePortletDataHandler {

	@Override
	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[] {
			_ruleGroups, _ruleGroupInstances
		};
	}

	@Override
	public boolean isAlwaysExportable() {
		return _ALWAYS_EXPORTABLE;
	}

	@Override
	public boolean isAlwaysStaged() {
		return _ALWAYS_STAGED;
	}

	@Override
	public boolean isPublishToLiveByDefault() {
		return _PUBLISH_TO_LIVE_BY_DEFAULT;
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (!portletDataContext.addPrimaryKey(
				MDRPortletDataHandlerImpl.class, "deleteData")) {

			MDRRuleGroupInstanceLocalServiceUtil.deleteGroupRuleGroupInstances(
				portletDataContext.getScopeGroupId());

			MDRRuleGroupLocalServiceUtil.deleteRuleGroups(
				portletDataContext.getGroupId());
		}

		return null;
	}

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPermissions(
			"com.liferay.portlet.mobiledevicerules",
			portletDataContext.getScopeGroupId());

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("mobiledevicerules-data");

		Element ruleGroupsElement = rootElement.addElement("rule-groups");

		List<MDRRuleGroup> ruleGroups = MDRRuleGroupUtil.findByGroupId(
			portletDataContext.getGroupId());

		for (MDRRuleGroup ruleGroup : ruleGroups) {
			exportRuleGroup(portletDataContext, ruleGroupsElement, ruleGroup);
		}

		Element ruleGroupInstancesElement = rootElement.addElement(
			"rule-group-instances");

		List<MDRRuleGroupInstance> ruleGroupInstances =
			MDRRuleGroupInstanceUtil.findByGroupId(
				portletDataContext.getScopeGroupId());

		for (MDRRuleGroupInstance ruleGroupInstance : ruleGroupInstances) {
			exportRuleGroupInstance(
				portletDataContext, ruleGroupInstancesElement,
				ruleGroupInstance);
		}

		return document.formattedString();
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPermissions(
			"com.liferay.portlet.mobiledevicerules",
			portletDataContext.getSourceGroupId(),
			portletDataContext.getScopeGroupId());

		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();

		Element ruleGroupsElement = rootElement.element("rule-groups");

		List<Element> ruleGroupElements = ruleGroupsElement.elements(
			"rule-group");

		for (Element ruleGroupElement : ruleGroupElements) {
			String path = ruleGroupElement.attributeValue("path");

			if (!portletDataContext.isPathNotProcessed(path)) {
				continue;
			}

			MDRRuleGroup ruleGroup =
				(MDRRuleGroup)portletDataContext.getZipEntryAsObject(path);

			importRuleGroup(portletDataContext, ruleGroupElement, ruleGroup);
		}

		Element ruleGroupInstancesElement = rootElement.element(
			"rule-group-instances");

		List<Element> ruleGroupInstanceElements =
			ruleGroupInstancesElement.elements("rule-group-instance");

		for (Element ruleGroupInstanceElement : ruleGroupInstanceElements) {
			String path = ruleGroupInstanceElement.attributeValue("path");

			if (!portletDataContext.isPathNotProcessed(path)) {
				continue;
			}

			MDRRuleGroupInstance ruleGroupInstance =
				(MDRRuleGroupInstance)portletDataContext.getZipEntryAsObject(
					path);

			importRuleGroupInstance(
				portletDataContext, ruleGroupInstanceElement,
				ruleGroupInstance);
		}

		return null;
	}

	protected void exportAction(
			PortletDataContext portletDataContext, Element actionsElement,
			MDRAction action)
		throws Exception {

		String path = getActionPath(portletDataContext, action);

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Element actionElement = actionsElement.addElement("action");

		String type = action.getType();

		if (type.equals(SiteRedirectActionHandler.class.getName())) {
			UnicodeProperties typeSettingsProperties =
				action.getTypeSettingsProperties();

			long targetPlid = GetterUtil.getLong(
				typeSettingsProperties.getProperty("plid"));

			try {
				Layout targetLayout = LayoutLocalServiceUtil.getLayout(
					targetPlid);

				actionElement.addAttribute(
					"layout-uuid", targetLayout.getUuid());
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to set the layout uuid of the target " +
							targetPlid +
								". Site redirect may not match after import.",
						e);
				}
			}
		}

		portletDataContext.addClassedModel(
			actionElement, path, action, _NAMESPACE);
	}

	protected void exportRule(
			PortletDataContext portletDataContext, Element rulesElement,
			MDRRule rule)
		throws Exception {

		String path = getRulePath(portletDataContext, rule);

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Element ruleElement = rulesElement.addElement("rule");

		portletDataContext.addClassedModel(ruleElement, path, rule, _NAMESPACE);
	}

	protected void exportRuleGroup(
			PortletDataContext portletDataContext, Element ruleGroupsElement,
			MDRRuleGroup ruleGroup)
		throws Exception {

		if (!portletDataContext.isWithinDateRange(
				ruleGroup.getModifiedDate())) {

			return;
		}

		String path = getRuleGroupPath(portletDataContext, ruleGroup);

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Element ruleGroupElement = ruleGroupsElement.addElement("rule-group");

		portletDataContext.addClassedModel(
			ruleGroupElement, path, ruleGroup, _NAMESPACE);

		Element mdrRulesElement = ruleGroupElement.addElement("rules");

		List<MDRRule> rules = ruleGroup.getRules();

		for (MDRRule rule : rules) {
			exportRule(portletDataContext, mdrRulesElement, rule);
		}
	}

	protected void exportRuleGroupInstance(
			PortletDataContext portletDataContext,
			Element ruleGroupInstancesElement,
			MDRRuleGroupInstance ruleGroupInstance)
		throws Exception {

		if (!portletDataContext.isWithinDateRange(
				ruleGroupInstance.getModifiedDate())) {

			return;
		}

		String path = getRuleGroupInstancePath(
			portletDataContext, ruleGroupInstance);

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Element ruleGroupInstanceElement = ruleGroupInstancesElement.addElement(
			"rule-group-instance");

		MDRRuleGroup ruleGroup = ruleGroupInstance.getRuleGroup();

		String className = ruleGroupInstance.getClassName();

		if (className.equals(Layout.class.getName())) {
			Layout layout = LayoutLocalServiceUtil.getLayout(
				ruleGroupInstance.getClassPK());

			ruleGroupInstanceElement.addAttribute(
				"layout-uuid", layout.getUuid());
		}

		String ruleGroupUuid = ruleGroup.getUuid();

		ruleGroupInstanceElement.addAttribute("rule-group-uuid", ruleGroupUuid);

		portletDataContext.addClassedModel(
			ruleGroupInstanceElement, path, ruleGroupInstance, _NAMESPACE);

		Element actionsElement = ruleGroupInstanceElement.addElement("actions");

		List<MDRAction> actions = ruleGroupInstance.getActions();

		for (MDRAction action : actions) {
			exportAction(portletDataContext, actionsElement, action);
		}
	}

	protected String getActionPath(
		PortletDataContext portletDataContext, MDRAction action) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			portletDataContext.getPortletPath(
				PortletKeys.MOBILE_DEVICE_SITE_ADMIN));
		sb.append("/actions/");
		sb.append(action.getActionId());
		sb.append(".xml");

		return sb.toString();
	}

	protected String getRuleGroupInstancePath(
		PortletDataContext portletDataContext,
		MDRRuleGroupInstance ruleGroupInstance) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			portletDataContext.getPortletPath(
				PortletKeys.MOBILE_DEVICE_SITE_ADMIN));
		sb.append("/rule-group-instances/");
		sb.append(ruleGroupInstance.getRuleGroupInstanceId());
		sb.append(".xml");

		return sb.toString();
	}

	protected String getRuleGroupPath(
		PortletDataContext portletDataContext, MDRRuleGroup ruleGroup) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			portletDataContext.getPortletPath(
				PortletKeys.MOBILE_DEVICE_SITE_ADMIN));
		sb.append("/rule-groups/");
		sb.append(ruleGroup.getRuleGroupId());
		sb.append(".xml");

		return sb.toString();
	}

	protected String getRulePath(
		PortletDataContext portletDataContext, MDRRule rule) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			portletDataContext.getPortletPath(
				PortletKeys.MOBILE_DEVICE_SITE_ADMIN));
		sb.append("/rules/");
		sb.append(rule.getRuleId());
		sb.append(".xml");

		return sb.toString();
	}

	protected void importAction(
			PortletDataContext portletDataContext, Element actionElement,
			MDRRuleGroupInstance ruleGroupInstance, MDRAction action)
		throws Exception {

		long userId = portletDataContext.getUserId(action.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			actionElement, action, _NAMESPACE);

		serviceContext.setUserId(userId);

		validateTargetLayoutPlid(actionElement, action);

		MDRAction importedAction = null;

		if (portletDataContext.isDataStrategyMirror()) {
			MDRAction existingAction = MDRActionUtil.fetchByUUID_G(
				action.getUuid(), portletDataContext.getScopeGroupId());

			if (existingAction == null) {
				serviceContext.setUuid(action.getUuid());

				importedAction = MDRActionLocalServiceUtil.addAction(
					ruleGroupInstance.getRuleGroupInstanceId(),
					action.getNameMap(), action.getDescriptionMap(),
					action.getType(), action.getTypeSettingsProperties(),
					serviceContext);
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
				ruleGroupInstance.getRuleGroupInstanceId(), action.getNameMap(),
				action.getDescriptionMap(), action.getType(),
				action.getTypeSettingsProperties(), serviceContext);
		}

		portletDataContext.importClassedModel(
			action, importedAction, _NAMESPACE);
	}

	protected void importRule(
			PortletDataContext portletDataContext, Element ruleElement,
			MDRRuleGroup ruleGroup, MDRRule rule)
		throws Exception {

		long userId = portletDataContext.getUserId(rule.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			ruleElement, rule, _NAMESPACE);

		serviceContext.setUserId(userId);

		MDRRule importedRule = null;

		if (portletDataContext.isDataStrategyMirror()) {
			MDRRule existingRule = MDRRuleUtil.fetchByUUID_G(
				rule.getUuid(), portletDataContext.getScopeGroupId());

			if (existingRule == null) {
				serviceContext.setUuid(rule.getUuid());

				importedRule = MDRRuleLocalServiceUtil.addRule(
					ruleGroup.getRuleGroupId(), rule.getNameMap(),
					rule.getDescriptionMap(), rule.getType(),
					rule.getTypeSettingsProperties(), serviceContext);
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
				ruleGroup.getRuleGroupId(), rule.getNameMap(),
				rule.getDescriptionMap(), rule.getType(),
				rule.getTypeSettingsProperties(), serviceContext);
		}

		portletDataContext.importClassedModel(rule, importedRule, _NAMESPACE);
	}

	protected void importRuleGroup(
			PortletDataContext portletDataContext, Element ruleGroupElement,
			MDRRuleGroup ruleGroup)
		throws Exception {

		long userId = portletDataContext.getUserId(ruleGroup.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			ruleGroupElement, ruleGroup, _NAMESPACE);

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
			ruleGroup, importedRuleGroup, _NAMESPACE);

		Element rulesElement = ruleGroupElement.element("rules");

		List<Element> ruleElements = rulesElement.elements("rule");

		for (Element ruleElement : ruleElements) {
			String path = ruleElement.attributeValue("path");

			if (!portletDataContext.isPathNotProcessed(path)) {
				continue;
			}

			MDRRule rule = (MDRRule)portletDataContext.getZipEntryAsObject(
				path);

			importRule(
				portletDataContext, ruleElement, importedRuleGroup, rule);
		}
	}

	protected void importRuleGroupInstance(
			PortletDataContext portletDataContext,
			Element ruleGroupInstanceElement,
			MDRRuleGroupInstance ruleGroupInstance)
		throws Exception {

		long userId = portletDataContext.getUserId(
			ruleGroupInstance.getUserUuid());

		Map<Long, Long> ruleGroupIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				MDRRuleGroup.class);

		Long ruleGroupId = ruleGroupIds.get(ruleGroupInstance.getRuleGroupId());

		if (ruleGroupId == null) {
			try {
				String ruleGroupUuid = ruleGroupInstanceElement.attributeValue(
					"rule-group-uuid");

				MDRRuleGroup ruleGroup = MDRRuleGroupUtil.fetchByUUID_G(
					ruleGroupUuid, portletDataContext.getScopeGroupId());

				ruleGroupId = ruleGroup.getRuleGroupId();
			}
			catch (Exception e) {
				if (_log.isErrorEnabled()) {
					_log.warn(
						"Unable to import rule group instance " +
							ruleGroupInstance,
						e);
				}

				return;
			}
		}

		long classPK = 0;

		String layoutUuid = ruleGroupInstanceElement.attributeValue(
			"layout-uuid");

		try {
			if (Validator.isNotNull(layoutUuid)) {
				Layout layout =
					LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
						layoutUuid, portletDataContext.getScopeGroupId(),
						portletDataContext.isPrivateLayout());

				classPK = layout.getPrimaryKey();
			}
			else {
				LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
					portletDataContext.getScopeGroupId(),
					portletDataContext.isPrivateLayout());

				classPK = layoutSet.getLayoutSetId();
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				StringBundler sb = new StringBundler(5);

				sb.append("Layout ");
				sb.append(layoutUuid);
				sb.append(" is missing for rule group instance ");
				sb.append(ruleGroupInstance.getRuleGroupInstanceId());
				sb.append(", skipping this rule group instance.");

				_log.warn(sb.toString());
			}

			return;
		}

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			ruleGroupInstanceElement, ruleGroupInstance, _NAMESPACE);

		serviceContext.setUserId(userId);

		MDRRuleGroupInstance importedRuleGroupInstance = null;

		if (portletDataContext.isDataStrategyMirror()) {
			MDRRuleGroupInstance existingMDRRuleGroupInstance =
				MDRRuleGroupInstanceUtil.fetchByUUID_G(
					ruleGroupInstance.getUuid(),
					portletDataContext.getScopeGroupId());

			if (existingMDRRuleGroupInstance == null) {
				serviceContext.setUuid(ruleGroupInstance.getUuid());

				importedRuleGroupInstance =
					MDRRuleGroupInstanceLocalServiceUtil.addRuleGroupInstance(
						portletDataContext.getScopeGroupId(),
						ruleGroupInstance.getClassName(), classPK, ruleGroupId,
						ruleGroupInstance.getPriority(), serviceContext);
			}
			else {
				importedRuleGroupInstance =
					MDRRuleGroupInstanceLocalServiceUtil.
						updateRuleGroupInstance(
							existingMDRRuleGroupInstance.
								getRuleGroupInstanceId(),
							ruleGroupInstance.getPriority());
			}
		}
		else {
			importedRuleGroupInstance =
				MDRRuleGroupInstanceLocalServiceUtil.addRuleGroupInstance(
					portletDataContext.getScopeGroupId(),
					ruleGroupInstance.getClassName(), classPK, ruleGroupId,
					ruleGroupInstance.getPriority(), serviceContext);
		}

		portletDataContext.importClassedModel(
			ruleGroupInstance, importedRuleGroupInstance, _NAMESPACE);

		Element actionsElement = ruleGroupInstanceElement.element("actions");

		List<Element> actionElements = actionsElement.elements("action");

		for (Element actionElement : actionElements) {
			String path = actionElement.attributeValue("path");

			if (!portletDataContext.isPathNotProcessed(path)) {
				continue;
			}

			MDRAction action =
				(MDRAction)portletDataContext.getZipEntryAsObject(path);

			importAction(
				portletDataContext, actionElement, importedRuleGroupInstance,
				action);
		}
	}

	protected void validateTargetLayoutPlid(
		Element actionElement, MDRAction action) {

		String type = action.getType();

		if (!type.equals(SiteRedirectActionHandler.class.getName())) {
			return;
		}

		String targetLayoutUuid = actionElement.attributeValue("layout-uuid");

		if (Validator.isNull(targetLayoutUuid)) {
			return;
		}

		UnicodeProperties typeSettingsProperties =
			action.getTypeSettingsProperties();

		long targetGroupId = GetterUtil.getLong(
			typeSettingsProperties.getProperty("groupId"));

		boolean privateLayout = GetterUtil.getBoolean(
			actionElement.attributeValue("private-layout"));

		try {
			Layout targetLayout =
				LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
					targetLayoutUuid, targetGroupId, privateLayout);

			typeSettingsProperties.setProperty(
				"plid", String.valueOf(targetLayout.getPlid()));
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to find target layout with uuid " +
						targetLayoutUuid + " in group " + targetGroupId +
							". Site redirect may not match target layout.",
					e);
			}
		}
	}

	private static final boolean _ALWAYS_EXPORTABLE = true;

	private static final boolean _ALWAYS_STAGED = true;

	private static final String _NAMESPACE = "mobile_device_rules";

	private static final boolean _PUBLISH_TO_LIVE_BY_DEFAULT = true;

	private static Log _log = LogFactoryUtil.getLog(
		MDRPortletDataHandlerImpl.class);

	private static PortletDataHandlerBoolean _ruleGroupInstances =
		new PortletDataHandlerBoolean(
			_NAMESPACE, "rule-group-instances", true, true);
	private static PortletDataHandlerBoolean _ruleGroups =
		new PortletDataHandlerBoolean(_NAMESPACE, "rule-groups", true, true);

}