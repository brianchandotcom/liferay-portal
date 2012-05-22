/*
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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.service.GroupLocalServiceUtil;
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
 */
public class MDRPortletDataHandlerImpl extends BasePortletDataHandler {

	@Override
	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[] {
			_siteMobileDeviceRuleGroups, _globalMobileDeviceRuleGroups
		};
	}

	@Override
	public PortletDataHandlerControl[] getExportMetadataControls() {
		return super.getExportMetadataControls();
	}

	@Override
	public PortletDataHandlerControl[] getImportControls() {
		return super.getImportControls();
	}

	@Override
	public PortletDataHandlerControl[] getImportMetadataControls() {
		return super.getImportMetadataControls();
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

			MDRRuleGroupInstanceLocalServiceUtil.
				deleteRuleGroupInstancesByGroupId(
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

		Group globalScopeGroup = GroupLocalServiceUtil.getCompanyGroup(
			portletDataContext.getCompanyId());
		long globalScopeGroupId = globalScopeGroup.getGroupId();

		portletDataContext.addPermissions(
			_PERMISSION_NAMESPACE,
			portletDataContext.getScopeGroupId());

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("mobile-device-rules-data");

		Element mdrRuleGroupInstancesElement = rootElement.addElement(
			_MDR_RULE_GROUP_INSTANCES_ELEMENT);
		Element siteMDRRuleGroupsElement = rootElement.addElement(
			_SITE_MDR_RULE_GROUPS_ELEMENT);
		Element globalMDRRuleGroupsElement = rootElement.addElement(
			_GLOBAL_MDR_RULE_GROUPS_ELEMENT);

		boolean exportAllSiteMDRRuleGroups =
			portletDataContext.getBooleanParameter(
				_NAMESPACE, "site-mdr-rule-groups");

		boolean exportAllGlobalMDRRuleGroups =
			portletDataContext.getBooleanParameter(
				_NAMESPACE, "global-mdr-rule-groups");

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Current configuration will force exporting of " +
				"all site device rule groups: " + exportAllSiteMDRRuleGroups);
			_log.debug(
				"Current configuration will force exporting of " +
				"all global device rule groups: " +
					exportAllGlobalMDRRuleGroups);
		}

		//export rule group instances for current scope

		List<MDRRuleGroupInstance> mdrRuleGroupInstances =
			MDRRuleGroupInstanceUtil.findByGroupId(
				portletDataContext.getScopeGroupId());

		for (MDRRuleGroupInstance mdrRuleGroupInstance :
				mdrRuleGroupInstances) {

			MDRRuleGroup mdrRuleGroup = mdrRuleGroupInstance.getRuleGroup();

			Element ruleGroupsElement = null;

			if ((mdrRuleGroup.getGroupId() == portletDataContext.getGroupId()) &&
				(!exportAllSiteMDRRuleGroups)) {

				ruleGroupsElement = siteMDRRuleGroupsElement;
			}
			else if ((mdrRuleGroup.getGroupId() == globalScopeGroupId) &&
				(!exportAllGlobalMDRRuleGroups)) {

				ruleGroupsElement = globalMDRRuleGroupsElement;
			}

			exportMDRRuleGroupInstance(
				portletDataContext, mdrRuleGroupInstance, mdrRuleGroup,
				mdrRuleGroupInstancesElement, ruleGroupsElement);
		}

		//export all rule groups for current scope
		if (exportAllSiteMDRRuleGroups) {

			exportMDRRuleGroups(
				portletDataContext.getGroupId(), portletDataContext,
				siteMDRRuleGroupsElement);
		}

		//export all global rule groups
		if (exportAllGlobalMDRRuleGroups) {

			exportMDRRuleGroups(
				globalScopeGroupId, portletDataContext,
				globalMDRRuleGroupsElement);
		}

		return document.formattedString();
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		Group globalScopeGroup = GroupLocalServiceUtil.getCompanyGroup(
			portletDataContext.getCompanyId());
		long globalScopeGroupId = globalScopeGroup.getGroupId();

		portletDataContext.importPermissions(
			_PERMISSION_NAMESPACE,
			portletDataContext.getSourceGroupId(),
			portletDataContext.getScopeGroupId());

		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();

		//import all global rule groups
		Element globalMDRRuleGroupsElement = rootElement.element(
			_GLOBAL_MDR_RULE_GROUPS_ELEMENT);

		List<Element> globalMDRRuleGroupElements =
			globalMDRRuleGroupsElement.elements(_MDR_RULE_GROUP_ELEMENT);

		importMDRRuleGroups(
			portletDataContext, globalScopeGroupId, globalMDRRuleGroupElements);

		//import all site rule groups
		Element siteMDRRuleGroupsElement = rootElement.element(
			_SITE_MDR_RULE_GROUPS_ELEMENT);

		List<Element> siteMDRRuleGroupElements =
			siteMDRRuleGroupsElement.elements(_MDR_RULE_GROUP_ELEMENT);

		importMDRRuleGroups(
			portletDataContext, portletDataContext.getScopeGroupId(),
			siteMDRRuleGroupElements);

		//import rule group instances into site.
		Element mdrRuleGroupInstancesElement = rootElement.element(
			_MDR_RULE_GROUP_INSTANCES_ELEMENT);

		List<Element> mdrRuleGroupInstanceElements =
			mdrRuleGroupInstancesElement.elements(
				_MDR_RULE_GROUP_INSTANCE_ELEMENT);

		if ((mdrRuleGroupInstanceElements != null) &&
			(!mdrRuleGroupInstanceElements.isEmpty())) {

			for (Element mdrRuleGroupInstanceElement :
					mdrRuleGroupInstanceElements) {

				String mdrRuleGroupInstancePath =
					mdrRuleGroupInstanceElement.attributeValue("path");

				if (!portletDataContext.isPathNotProcessed(
						mdrRuleGroupInstancePath)) {

					continue;
				}

				MDRRuleGroupInstance mdrRuleGroupInstance =
					(MDRRuleGroupInstance)portletDataContext.
						getZipEntryAsObject(mdrRuleGroupInstancePath);

				importMDRRuleGroupInstance(
					portletDataContext, mdrRuleGroupInstancePath,
					mdrRuleGroupInstance, mdrRuleGroupInstanceElement);
			}

		}

		return null;
	}

	protected void exportMDRActions(
			PortletDataContext portletDataContext, Element mdrActionsElement,
			List<MDRAction> mdrActions)
		throws PortalException, SystemException {

		for (MDRAction mdrAction : mdrActions) {

			String mdrActionPath = getMDRActionPath(
				portletDataContext, mdrAction);

			if (!portletDataContext.isPathNotProcessed(mdrActionPath)) {
			    continue;
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Exporting action " + mdrAction.getActionId() +
					" for rule group instance: " +
					mdrAction.getRuleGroupInstanceId());
			}

			Element mdrActionElement = mdrActionsElement.addElement(
				_MDR_ACTION_ELEMENT);

			portletDataContext.addClassedModel(
				mdrActionElement, mdrActionPath, mdrAction, _NAMESPACE);
		}
	}

	protected void exportMDRRuleGroupInstance(
			PortletDataContext portletDataContext,
			MDRRuleGroupInstance mdrRuleGroupInstance,
			MDRRuleGroup mdrRuleGroup, Element mdrRuleGroupInstancesElement,
			Element mdrRuleGroupsElement)
		throws Exception {

		if (!portletDataContext.isWithinDateRange(
			mdrRuleGroupInstance.getModifiedDate())) {

			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Exporting rule group instance: " + mdrRuleGroupInstance);
		}

		//export the rule instance
		String mdrRuleGroupInstancePath = getMDRRuleGroupInstancePath(
			portletDataContext, mdrRuleGroupInstance);

		if (portletDataContext.isPathNotProcessed(mdrRuleGroupInstancePath)) {
			Element mdrRuleGroupInstanceElement =
				mdrRuleGroupInstancesElement.addElement(
					_MDR_RULE_GROUP_INSTANCE_ELEMENT);

			//if the instance tied to a layout, get the layout's uuid
			String className = mdrRuleGroupInstance.getClassName();

			try {
				if (className.equals(Layout.class.getName())) {
					Layout layout = LayoutLocalServiceUtil.getLayout(
						mdrRuleGroupInstance.getClassPK());

					mdrRuleGroupInstanceElement.addAttribute(
						"layout-uuid", layout.getUuid());
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to export desired layout or layoutSet for: " +
						className + ", primaryKey: " +
						mdrRuleGroupInstance.getClassPK() +
						", skipping export of rule group instance.",
						e);
				}

				return;
			}

			portletDataContext.addClassedModel(
				mdrRuleGroupInstanceElement, mdrRuleGroupInstancePath,
				mdrRuleGroupInstance, _NAMESPACE);

			List<MDRAction> mdrActions = mdrRuleGroupInstance.getMDRActions();

			//export actions
			if (!mdrActions.isEmpty()) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Exporting " + mdrActions.size() +
						" actions for rule group instance: " +
						mdrRuleGroupInstance.getRuleGroupInstanceId());
				}

				Element mdrActionsElement =
					mdrRuleGroupInstanceElement.addElement(_MDR_ACTIONS_ELEMENT);

				exportMDRActions(
					portletDataContext, mdrActionsElement, mdrActions);
			}
		}

		//if the ruleGroupsElement is not null,
		//go ahead and export the rule group
		if (mdrRuleGroupsElement != null) {
			exportMDRRuleGroup(
				portletDataContext, mdrRuleGroup, mdrRuleGroupsElement, true);
		}
		else if (_log.isDebugEnabled()) {
			_log.debug(
				"Bypassing exporting of referenced rule group " +
				"since all rule groups will be exported.");
		}
	}

	protected void exportMDRRuleGroup(
			PortletDataContext portletDataContext, MDRRuleGroup mdrRuleGroup,
			Element mdrRuleGroupsElement, boolean ignoreModifiedDate)
		throws Exception {

		if (!ignoreModifiedDate && !portletDataContext.isWithinDateRange(
			mdrRuleGroup.getModifiedDate())) {

			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Exporting rule group: " + mdrRuleGroup);
		}

		//export the rule group
		String mdrRuleGroupPath = getMDRRuleGroupPath(
			portletDataContext, mdrRuleGroup);

		if (portletDataContext.isPathNotProcessed(mdrRuleGroupPath)) {
			Element mdrRuleGroupElement = mdrRuleGroupsElement.addElement(
				_MDR_RULE_GROUP_ELEMENT);

			portletDataContext.addClassedModel(
				mdrRuleGroupElement, mdrRuleGroupPath, mdrRuleGroup,
				_NAMESPACE);

			List<MDRRule> mdrRules = mdrRuleGroup.getRules();

			//export rules for group
			if (!mdrRules.isEmpty()) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Exporting " + mdrRules.size() +
						" rules for rule group: " +
						mdrRuleGroup.getRuleGroupId());
				}

				Element mdrRulesElement = mdrRuleGroupElement.addElement(
					_MDR_RULES_ELEMENT);

				exportMDRRules(portletDataContext, mdrRulesElement, mdrRules);
			}
		}
	}

	protected void exportMDRRuleGroups(
			long groupId, PortletDataContext portletDataContext,
			Element mdrRuleGroupsElement)
		throws Exception {

		List<MDRRuleGroup> mdrRuleGroups = MDRRuleGroupUtil.findByGroupId(
			groupId);

		for (MDRRuleGroup mdrRuleGroup : mdrRuleGroups) {
			exportMDRRuleGroup(
				portletDataContext, mdrRuleGroup, mdrRuleGroupsElement, false);
		}
	}

	protected void exportMDRRules(
			PortletDataContext portletDataContext, Element mdrRulesElement,
			List<MDRRule> mdrRules)
		throws Exception {

		for (MDRRule mdrRule : mdrRules) {

			String mdrRulePath = getMDRRulePath(portletDataContext, mdrRule);

			if (!portletDataContext.isPathNotProcessed(mdrRulePath)) {
			    continue;
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Exporting action " + mdrRule.getRuleId() +
					" for rule group: " + mdrRule.getRuleGroupId());
			}

			Element mdrRuleElement = mdrRulesElement.addElement(
				_MDR_RULE_ELEMENT);

			portletDataContext.addClassedModel(
				mdrRuleElement, mdrRulePath, mdrRule, _NAMESPACE);
		}
	}

	protected void importMDRAction(
			PortletDataContext portletDataContext,
			MDRRuleGroupInstance mdrRuleGroupInstance, String mdrActionPath,
			MDRAction mdrAction)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Importing action: " + mdrAction);
		}

		long userId = portletDataContext.getUserId(mdrAction.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			mdrActionPath, mdrAction, _NAMESPACE);

		serviceContext.setUserId(userId);

		MDRAction importedMDRAction = null;

		if (portletDataContext.isDataStrategyMirror()) {
			MDRAction existingMDRAction = MDRActionUtil.fetchByUUID_G(
				mdrAction.getUuid(), portletDataContext.getScopeGroupId());

			if (existingMDRAction == null) {
				serviceContext.setUuid(mdrAction.getUuid());

				importedMDRAction =
					MDRActionLocalServiceUtil.addAction(
						mdrRuleGroupInstance.getRuleGroupInstanceId(),
						mdrAction.getNameMap(), mdrAction.getDescriptionMap(),
						mdrAction.getType(),
						mdrAction.getTypeSettingsProperties(), serviceContext);
			}
			else {
				importedMDRAction =
					MDRActionLocalServiceUtil.updateAction(
						existingMDRAction.getActionId(), mdrAction.getNameMap(),
						mdrAction.getDescriptionMap(), mdrAction.getType(),
						mdrAction.getTypeSettingsProperties(), serviceContext);
			}
		}
		else {
			importedMDRAction =
				MDRActionLocalServiceUtil.addAction(
					mdrRuleGroupInstance.getRuleGroupInstanceId(),
					mdrAction.getNameMap(), mdrAction.getDescriptionMap(),
					mdrAction.getType(), mdrAction.getTypeSettingsProperties(),
					serviceContext);
		}

		portletDataContext.importClassedModel(
			mdrAction, importedMDRAction, _NAMESPACE);
	}

	protected void importMDRRule(
			PortletDataContext portletDataContext, long groupId,
			MDRRuleGroup mdrRuleGroup, String mdrRulePath, MDRRule mdrRule)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Importing rule: " + mdrRule);
		}

		long userId = portletDataContext.getUserId(mdrRule.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			mdrRulePath, mdrRule, _NAMESPACE);

		serviceContext.setUserId(userId);

		MDRRule importedMDRRule = null;

		if (portletDataContext.isDataStrategyMirror()) {
			MDRRule existingMDRRule = MDRRuleUtil.fetchByUUID_G(
				mdrRule.getUuid(), groupId);

			if (existingMDRRule == null) {
				serviceContext.setUuid(mdrRule.getUuid());

				importedMDRRule =
					MDRRuleLocalServiceUtil.addRule(
						mdrRuleGroup.getRuleGroupId(), mdrRule.getNameMap(),
						mdrRule.getDescriptionMap(), mdrRule.getType(),
						mdrRule.getTypeSettingsProperties(), serviceContext);
			}
			else {
				importedMDRRule =
					MDRRuleLocalServiceUtil.updateRule(
						existingMDRRule.getRuleId(), mdrRule.getNameMap(),
						mdrRule.getDescriptionMap(), mdrRule.getType(),
						mdrRule.getTypeSettingsProperties(), serviceContext);
			}
		}
		else {
			importedMDRRule =
				MDRRuleLocalServiceUtil.addRule(
					mdrRuleGroup.getRuleGroupId(), mdrRule.getNameMap(),
					mdrRule.getDescriptionMap(), mdrRule.getType(),
					mdrRule.getTypeSettingsProperties(), serviceContext);
		}

		portletDataContext.importClassedModel(
			mdrRule, importedMDRRule, _NAMESPACE);
	}

	protected void importMDRRuleGroup(
			PortletDataContext portletDataContext, long groupId,
			String mdrRuleGroupPath, MDRRuleGroup mdrRuleGroup,
			Element mdrRuleGroupElement)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Importing rule group: " + mdrRuleGroup);
		}

		long userId = portletDataContext.getUserId(mdrRuleGroup.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			mdrRuleGroupPath, mdrRuleGroup, _NAMESPACE);

		serviceContext.setUserId(userId);

		MDRRuleGroup importedMDRRuleGroup = null;

		if (portletDataContext.isDataStrategyMirror()) {
			MDRRuleGroup existingMDRRuleGroup = MDRRuleGroupUtil.fetchByUUID_G(
				mdrRuleGroup.getUuid(), groupId);

			if (existingMDRRuleGroup == null) {
				serviceContext.setUuid(mdrRuleGroup.getUuid());

				importedMDRRuleGroup =
					MDRRuleGroupLocalServiceUtil.addRuleGroup(
						groupId, mdrRuleGroup.getNameMap(),
						mdrRuleGroup.getDescriptionMap(), serviceContext);
			}
			else {
				importedMDRRuleGroup =
					MDRRuleGroupLocalServiceUtil.updateRuleGroup(
						existingMDRRuleGroup.getRuleGroupId(),
						mdrRuleGroup.getNameMap(),
						mdrRuleGroup.getDescriptionMap(), serviceContext);
			}
		}
		else {
			importedMDRRuleGroup =
				MDRRuleGroupLocalServiceUtil.addRuleGroup(
					groupId, mdrRuleGroup.getNameMap(),
					mdrRuleGroup.getDescriptionMap(), serviceContext);
		}

		portletDataContext.importClassedModel(
			mdrRuleGroup, importedMDRRuleGroup, _NAMESPACE);

		Element mdrRulesElement = mdrRuleGroupElement.element(
			_MDR_RULES_ELEMENT);

		if (mdrRulesElement != null) {
			List<Element> mdrRuleElements = mdrRulesElement.elements(
				_MDR_RULE_ELEMENT);

			for (Element mdrRuleElement : mdrRuleElements) {
				String mdrRulePath = mdrRuleElement.attributeValue("path");

				if (!portletDataContext.isPathNotProcessed(mdrRulePath)) {
					continue;
				}

				MDRRule mdrRule =
					(MDRRule)portletDataContext.getZipEntryAsObject(
						mdrRulePath);

				importMDRRule(
					portletDataContext, groupId, importedMDRRuleGroup,
					mdrRulePath, mdrRule);
			}
		}
	}

	protected void importMDRRuleGroupInstance(
			PortletDataContext portletDataContext,
			String mdrRuleGroupInstancePath,
			MDRRuleGroupInstance mdrRuleGroupInstance,
			Element mdrRuleGroupInstanceElement)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Importing rule group instance: " + mdrRuleGroupInstance);
		}

		long userId = portletDataContext.getUserId(
			mdrRuleGroupInstance.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			mdrRuleGroupInstancePath, mdrRuleGroupInstance, _NAMESPACE);

		serviceContext.setUserId(userId);

		Map<Long, Long> newMDRRuleGroupIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				MDRRuleGroup.class);

		long newRuleGroupId = newMDRRuleGroupIds.get(
			mdrRuleGroupInstance.getRuleGroupId());

		String layoutUuid = mdrRuleGroupInstanceElement.attributeValue(
			"layout-uuid");

		long classPK = 0;

		try {
			if (Validator.isNotNull(layoutUuid)) {
				Layout layout = LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
					layoutUuid, portletDataContext.getScopeGroupId());

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
				_log.warn(
					"Unable to find desired layout for uuid: " + layoutUuid +
					" in group: " + portletDataContext.getScopeGroupId() +
					", skipping import of rule instance.",
					e);
			}

			return;
		}

		MDRRuleGroupInstance importedMDRRuleGroupInstance = null;

		if (portletDataContext.isDataStrategyMirror()) {
			MDRRuleGroupInstance existingMDRRuleGroupInstance =
				MDRRuleGroupInstanceUtil.fetchByUUID_G(
					mdrRuleGroupInstance.getUuid(),
					portletDataContext.getScopeGroupId());

			if (existingMDRRuleGroupInstance == null) {
				serviceContext.setUuid(mdrRuleGroupInstance.getUuid());

				importedMDRRuleGroupInstance =
					MDRRuleGroupInstanceLocalServiceUtil.addRuleGroupInstance(
						portletDataContext.getScopeGroupId(),
						mdrRuleGroupInstance.getClassName(), classPK,
						newRuleGroupId, mdrRuleGroupInstance.getPriority(),
						serviceContext);
			}
			else {
				importedMDRRuleGroupInstance =
					MDRRuleGroupInstanceLocalServiceUtil.
						updateRuleGroupInstance(
							existingMDRRuleGroupInstance.
								getRuleGroupInstanceId(),
							mdrRuleGroupInstance.getPriority());
			}
		}
		else {
			importedMDRRuleGroupInstance =
				MDRRuleGroupInstanceLocalServiceUtil.addRuleGroupInstance(
					portletDataContext.getScopeGroupId(),
					mdrRuleGroupInstance.getClassName(), classPK,
					newRuleGroupId, mdrRuleGroupInstance.getPriority(),
					serviceContext);
		}

		portletDataContext.importClassedModel(
			mdrRuleGroupInstance, importedMDRRuleGroupInstance, _NAMESPACE);

		Element mdrActionsElement = mdrRuleGroupInstanceElement.element(
			_MDR_ACTIONS_ELEMENT);

		if (mdrActionsElement != null) {
			List<Element> mdrActionElements = mdrActionsElement.elements(
				_MDR_ACTION_ELEMENT);

			for (Element mdrActionElement : mdrActionElements) {
				String mdrActionPath = mdrActionElement.attributeValue("path");

				if (!portletDataContext.isPathNotProcessed(mdrActionPath)) {
					continue;
				}

				MDRAction mdrAction =
					(MDRAction)portletDataContext.getZipEntryAsObject(
						mdrActionPath);

				importMDRAction(
					portletDataContext, importedMDRRuleGroupInstance,
					mdrActionPath, mdrAction);
			}
		}
	}

	protected void importMDRRuleGroups(
			PortletDataContext portletDataContext, long groupId,
			List<Element> mdrRuleGroupElements)
		throws Exception {

		if ((mdrRuleGroupElements == null) || mdrRuleGroupElements.isEmpty()) {
			return;
		}

		for (Element mdrRuleGroupElement : mdrRuleGroupElements) {
			String path = mdrRuleGroupElement.attributeValue("path");

			if (!portletDataContext.isPathNotProcessed(path)) {
				continue;
			}

			MDRRuleGroup mdrRuleGroup =
				(MDRRuleGroup)portletDataContext.getZipEntryAsObject(path);

			importMDRRuleGroup(
				portletDataContext, groupId, path, mdrRuleGroup,
				mdrRuleGroupElement);
		}
	}

	protected String getMDRActionPath(
		PortletDataContext portletDataContext, MDRAction mdrAction) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			portletDataContext.getPortletPath(
				PortletKeys.MOBILE_DEVICE_SITE_ADMIN));

		sb.append("/mdractions/");
		sb.append(mdrAction.getActionId());
		sb.append(".xml");

		return sb.toString();
	}

	protected String getMDRRuleGroupInstancePath(
		PortletDataContext portletDataContext,
		MDRRuleGroupInstance mdrRuleGroupInstance) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			portletDataContext.getPortletPath(
				PortletKeys.MOBILE_DEVICE_SITE_ADMIN));

		sb.append("/mdrrulegroupinstances/");
		sb.append(mdrRuleGroupInstance.getRuleGroupInstanceId());
		sb.append(".xml");

		return sb.toString();
	}

	protected String getMDRRuleGroupPath(
		PortletDataContext portletDataContext, MDRRuleGroup mdrRuleGroup) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			portletDataContext.getPortletPath(
				PortletKeys.MOBILE_DEVICE_SITE_ADMIN));

		sb.append("/mdrrulegroups/");
		sb.append(mdrRuleGroup.getRuleGroupId());
		sb.append(".xml");

		return sb.toString();
	}

	protected String getMDRRulePath(
		PortletDataContext portletDataContext, MDRRule mdrRule) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			portletDataContext.getPortletPath(
				PortletKeys.MOBILE_DEVICE_SITE_ADMIN));

		sb.append("/mdrrules/");
		sb.append(mdrRule.getRuleId());
		sb.append(".xml");

		return sb.toString();
	}

	private static final boolean _ALWAYS_EXPORTABLE = true;
	private static final boolean _ALWAYS_STAGED = true;
	private static final String _GLOBAL_MDR_RULE_GROUPS_ELEMENT =
		"global-mdr-rule-groups";
	private static final String _MDR_ACTION_ELEMENT = "mdr-action";
	private static final String _MDR_ACTIONS_ELEMENT = "mdr-actions";
	private static final String _MDR_RULE_GROUP_ELEMENT = "mdr-rule-group";
	private static final String _MDR_RULE_GROUP_INSTANCE_ELEMENT =
		"mdr-rule-group-instance";
	private static final String _MDR_RULE_GROUP_INSTANCES_ELEMENT =
		"mdr-rule-group-instances";
	private static final String _MDR_RULE_ELEMENT = "mdr-rule";
	private static final String _MDR_RULES_ELEMENT = "mdr-rules";
	private static final String _NAMESPACE = "mobile_device_rules";
	private static final String _PERMISSION_NAMESPACE =
		"com.liferay.portlet.mobiledevicerules";
	private static final boolean _PUBLISH_TO_LIVE_BY_DEFAULT = true;
	private static final String _SITE_MDR_RULE_GROUPS_ELEMENT =
		"site-mdr-rule-groups";

	private static Log _log = LogFactoryUtil.getLog(
		MDRPortletDataHandlerImpl.class);

	private static PortletDataHandlerBoolean _siteMobileDeviceRuleGroups =
		new PortletDataHandlerBoolean(
			_NAMESPACE, "site-mdr-rule-groups", true, false);

	private static PortletDataHandlerBoolean _globalMobileDeviceRuleGroups =
		new PortletDataHandlerBoolean(
			_NAMESPACE, "global-mdr-rule-groups", true, false);

}