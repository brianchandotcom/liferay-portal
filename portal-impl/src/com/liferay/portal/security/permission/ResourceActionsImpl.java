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

package com.liferay.portal.security.permission;

import com.liferay.portal.NoSuchResourceActionException;
import com.liferay.portal.ResourceActionsException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentType;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.ResourceAction;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.GroupServiceUtil;
import com.liferay.portal.service.PortletLocalService;
import com.liferay.portal.service.ResourceActionLocalService;
import com.liferay.portal.service.RoleLocalService;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.PortletResourceBundles;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup;
import com.liferay.util.JS;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Daeyoung Song
 * @author Raymond Augé
 */
@DoPrivileged
public class ResourceActionsImpl implements ResourceActions {

	public void afterPropertiesSet() {
		_organizationModelResources = new HashSet<String>();

		for (String resource : getOrganizationModelResources()) {
			_organizationModelResources.add(resource);
		}

		_portalModelResources = new HashSet<String>();

		for (String resource : getPortalModelResources()) {
			_portalModelResources.add(resource);
		}

		_portletModelResources = new HashMap<String, Set<String>>();
		_portletResourceActions = new HashMap<String, Set<String>>();
		_portletResourceGroupDefaultActions =
			new HashMap<String, Set<String>>();
		_portletResourceGuestDefaultActions =
			new HashMap<String, Set<String>>();
		_portletResourceGuestUnsupportedActions =
			new HashMap<String, Set<String>>();
		_portletResourceLayoutManagerActions =
			new HashMap<String, Set<String>>();
		_portletRootModelResource = new HashMap<String, String>();
		_modelPortletResources = new HashMap<String, Set<String>>();
		_modelResourceActions = new HashMap<String, Set<String>>();
		_modelResourceGroupDefaultActions = new HashMap<String, Set<String>>();
		_modelResourceGuestDefaultActions = new HashMap<String, Set<String>>();
		_modelResourceGuestUnsupportedActions =
			new HashMap<String, Set<String>>();
		_modelResourceOwnerDefaultActions = new HashMap<String, Set<String>>();
		_modelResourceWeights = new HashMap<String, Double>();

		try {
			ClassLoader classLoader = getClass().getClassLoader();

			for (String config : PropsValues.RESOURCE_ACTIONS_CONFIGS) {
				read(null, classLoader, config);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public void checkAction(String name, String actionId)
		throws NoSuchResourceActionException {

		List<String> resourceActions = getResourceActions(name);

		if (!resourceActions.contains(actionId)) {
			throw new NoSuchResourceActionException(
				name.concat(StringPool.POUND).concat(actionId));
		}
	}

	@Override
	public String getAction(HttpServletRequest request, String action) {
		String key = getActionNamePrefix() + action;

		String value = LanguageUtil.get(request, key, null);

		if ((value == null) || value.equals(key)) {
			value = PortletResourceBundles.getString(request, key);
		}

		if (value == null) {
			value = key;
		}

		return value;
	}

	@Override
	public String getAction(Locale locale, String action) {
		String key = getActionNamePrefix() + action;

		String value = LanguageUtil.get(locale, key, null);

		if ((value == null) || value.equals(key)) {
			value = PortletResourceBundles.getString(locale, key);
		}

		if (value == null) {
			value = key;
		}

		return value;
	}

	@Override
	public String getActionNamePrefix() {
		return _ACTION_NAME_PREFIX;
	}

	@Override
	public List<String> getActionsNames(
		HttpServletRequest request, List<String> actions) {

		Set<String> actionNames = new LinkedHashSet<String>();

		for (String action : actions) {
			actionNames.add(getAction(request, action));
		}

		return new ArrayList<String>(actionNames);
	}

	@Override
	public List<String> getActionsNames(
		HttpServletRequest request, String name, long actionIds) {

		try {
			List<ResourceAction> resourceActions =
				resourceActionLocalService.getResourceActions(name);

			List<String> actions = new ArrayList<String>();

			for (ResourceAction resourceAction : resourceActions) {
				long bitwiseValue = resourceAction.getBitwiseValue();

				if ((actionIds & bitwiseValue) == bitwiseValue) {
					actions.add(resourceAction.getActionId());
				}
			}

			return getActionsNames(request, actions);
		}
		catch (Exception e) {
			_log.error(e, e);

			return Collections.emptyList();
		}
	}

	@Override
	public List<String> getModelNames() {
		return ListUtil.fromMapKeys(_modelPortletResources);
	}

	@Override
	public List<String> getModelPortletResources(String name) {
		Set<String> resources = _modelPortletResources.get(name);

		if (resources == null) {
			return new ArrayList<String>();
		}
		else {
			return new ArrayList<String>(resources);
		}
	}

	@Override
	public String getModelResource(HttpServletRequest request, String name) {
		String key = getModelResourceNamePrefix() + name;

		String value = LanguageUtil.get(request, key, null);

		if ((value == null) || value.equals(key)) {
			value = PortletResourceBundles.getString(request, key);
		}

		if (value == null) {
			value = key;
		}

		return value;
	}

	@Override
	public String getModelResource(Locale locale, String name) {
		String key = getModelResourceNamePrefix() + name;

		String value = LanguageUtil.get(locale, key, null);

		if ((value == null) || value.equals(key)) {
			value = PortletResourceBundles.getString(locale, key);
		}

		if (value == null) {
			value = key;
		}

		return value;
	}

	@Override
	public List<String> getModelResourceActions(String name) {
		return new ArrayList<String>(getActions(_modelResourceActions, name));
	}

	@Override
	public List<String> getModelResourceGroupDefaultActions(String name) {
		return new ArrayList<String>(
			getActions(_modelResourceGroupDefaultActions, name));
	}

	@Override
	public List<String> getModelResourceGuestDefaultActions(String name) {
		return new ArrayList<String>(
			getActions(_modelResourceGuestDefaultActions, name));
	}

	@Override
	public List<String> getModelResourceGuestUnsupportedActions(String name) {
		return new ArrayList<String>(
			getActions(_modelResourceGuestUnsupportedActions, name));
	}

	@Override
	public String getModelResourceNamePrefix() {
		return _MODEL_RESOURCE_NAME_PREFIX;
	}

	@Override
	public List<String> getModelResourceOwnerDefaultActions(String name) {
		return new ArrayList<String>(
			getActions(_modelResourceOwnerDefaultActions, name));
	}

	@Override
	public Double getModelResourceWeight(String name) {
		return _modelResourceWeights.get(name);
	}

	@Override
	public String[] getOrganizationModelResources() {
		return _ORGANIZATION_MODEL_RESOURCES;
	}

	@Override
	public String[] getPortalModelResources() {
		return _PORTAL_MODEL_RESOURCES;
	}

	@Override
	public String getPortletBaseResource(String portletName) {
		List<String> modelNames = getPortletModelResources(portletName);

		for (String modelName : modelNames) {
			if (!modelName.contains(".model.")) {
				return modelName;
			}
		}

		return null;
	}

	@Override
	public List<String> getPortletModelResources(String portletName) {
		portletName = PortletConstants.getRootPortletId(portletName);

		Set<String> resources = _portletModelResources.get(portletName);

		if (resources == null) {
			return new ArrayList<String>();
		}
		else {
			return new ArrayList<String>(resources);
		}
	}

	@Override
	public List<String> getPortletNames() {
		return ListUtil.fromMapKeys(_portletModelResources);
	}

	@Override
	public List<String> getPortletResourceActions(Portlet portlet) {
		Set<String> actions = new LinkedHashSet<String>(
			getPortletResourceActions(portlet.getPortletId()));

		synchronized (this) {
			checkPortletActions(portlet, actions);

			_portletResourceActions.put(portlet.getPortletId(), actions);
		}

		return new ArrayList<String>(actions);
	}

	@Override
	public List<String> getPortletResourceActions(String name) {
		name = PortletConstants.getRootPortletId(name);

		Set<String> actions = getActions(_portletResourceActions, name);

		if (!actions.isEmpty()) {
			return new ArrayList<String>(actions);
		}

		synchronized (this) {
			actions = getPortletMimeTypeActions(name);

			if (!name.equals(PortletKeys.PORTAL)) {
				checkPortletActions(name, actions);
			}

			Set<String> groupDefaultActions =
				_portletResourceGroupDefaultActions.get(name);

			if (groupDefaultActions == null) {
				groupDefaultActions = new LinkedHashSet<String>();

				checkPortletGroupDefaultActions(groupDefaultActions);

				_portletResourceGroupDefaultActions.put(
					name, groupDefaultActions);
			}

			Set<String> guestDefaultActions =
				_portletResourceGuestDefaultActions.get(name);

			if (guestDefaultActions == null) {
				guestDefaultActions = new LinkedHashSet<String>();

				checkPortletGuestDefaultActions(guestDefaultActions);

				_portletResourceGuestDefaultActions.put(
					name, guestDefaultActions);
			}

			Set<String> layoutManagerActions =
				_portletResourceLayoutManagerActions.get(name);

			if (layoutManagerActions == null) {
				layoutManagerActions = new LinkedHashSet<String>();

				checkPortletLayoutManagerActions(layoutManagerActions);

				_portletResourceLayoutManagerActions.put(
					name, layoutManagerActions);
			}

			_portletResourceActions.put(name, actions);
		}

		return new ArrayList<String>(actions);
	}

	@Override
	public List<String> getPortletResourceGroupDefaultActions(String name) {

		// This method should always be called only after
		// _getPortletResourceActions has been called at least once to populate
		// the default group actions. Check to make sure this is the case.
		// However, if it is not, that means the methods
		// getPortletResourceGuestDefaultActions and
		// getPortletResourceGuestDefaultActions may not work either.

		name = PortletConstants.getRootPortletId(name);

		return new ArrayList<String>(
			getActions(_portletResourceGroupDefaultActions, name));
	}

	@Override
	public List<String> getPortletResourceGuestDefaultActions(String name) {
		name = PortletConstants.getRootPortletId(name);

		return new ArrayList<String>(
			getActions(_portletResourceGuestDefaultActions, name));
	}

	@Override
	public List<String> getPortletResourceGuestUnsupportedActions(String name) {
		name = PortletConstants.getRootPortletId(name);

		Set<String> actions = getActions(
			_portletResourceGuestUnsupportedActions, name);

		if (actions.contains(ActionKeys.CONFIGURATION) &&
			actions.contains(ActionKeys.PERMISSIONS)) {

			return new ArrayList<String>(actions);
		}

		actions = new LinkedHashSet<String>(actions);

		actions.add(ActionKeys.CONFIGURATION);
		actions.add(ActionKeys.PERMISSIONS);

		_portletResourceGuestUnsupportedActions.put(name, actions);

		return new ArrayList<String>(actions);
	}

	@Override
	public List<String> getPortletResourceLayoutManagerActions(String name) {
		name = PortletConstants.getRootPortletId(name);

		Set<String> actions = getActions(
			_portletResourceLayoutManagerActions, name);

		// This check can never return an empty list. If the list is empty, it
		// means that the portlet does not have an explicit resource-actions
		// configuration file and should therefore be handled as if it has
		// defaults of CONFIGURATION, PREFERENCES, and VIEW.

		if (actions.isEmpty()) {
			actions = new LinkedHashSet<String>();

			actions.add(ActionKeys.CONFIGURATION);
			actions.add(ActionKeys.PREFERENCES);
			actions.add(ActionKeys.VIEW);

			_portletResourceLayoutManagerActions.put(name, actions);
		}

		return new ArrayList<String>(actions);
	}

	@Override
	public String getPortletRootModelResource(String portletName) {
		portletName = PortletConstants.getRootPortletId(portletName);

		return _portletRootModelResource.get(portletName);
	}

	@Override
	public List<String> getResourceActions(String name) {
		if (name.indexOf(CharPool.PERIOD) != -1) {
			return getModelResourceActions(name);
		}
		else {
			return getPortletResourceActions(name);
		}
	}

	@Override
	public List<String> getResourceActions(
		String portletResource, String modelResource) {

		List<String> actions = null;

		if (Validator.isNull(modelResource)) {
			actions = getPortletResourceActions(portletResource);
		}
		else {
			actions = getModelResourceActions(modelResource);
		}

		return actions;
	}

	@Override
	public List<String> getResourceGroupDefaultActions(String name) {
		if (name.contains(StringPool.PERIOD)) {
			return getModelResourceGroupDefaultActions(name);
		}
		else {
			return getPortletResourceGroupDefaultActions(name);
		}
	}

	@Override
	public List<String> getResourceGuestUnsupportedActions(
		String portletResource, String modelResource) {

		List<String> actions = null;

		if (Validator.isNull(modelResource)) {
			actions = getPortletResourceGuestUnsupportedActions(
				portletResource);
		}
		else {
			actions = getModelResourceGuestUnsupportedActions(modelResource);
		}

		return actions;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getRoles(long, Group,
	 *             String, int[])}
	 */
	@Deprecated
	@Override
	public List<Role> getRoles(
		long companyId, Group group, String modelResource) {

		return getRoles(companyId, group, modelResource, null);
	}

	@Override
	public List<Role> getRoles(
		long companyId, Group group, String modelResource, int[] roleTypes) {

		if (roleTypes == null) {
			roleTypes = getRoleTypes(companyId, group, modelResource);
		}

		return roleLocalService.getRoles(companyId, roleTypes);
	}

	@Override
	public boolean hasModelResourceActions(String name) {
		Set<String> actions = _modelResourceActions.get(name);

		if ((actions != null) && !actions.isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isOrganizationModelResource(String modelResource) {
		if (_organizationModelResources.contains(modelResource)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isPortalModelResource(String modelResource) {
		if (_portalModelResources.contains(modelResource)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void read(
			String servletContextName, ClassLoader classLoader, String source)
		throws Exception {

		InputStream inputStream = classLoader.getResourceAsStream(source);

		if (inputStream == null) {
			if (_log.isWarnEnabled() && !source.endsWith("-ext.xml")) {
				_log.warn("Cannot load " + source);
			}

			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Loading " + source);
		}

		Document document = SAXReaderUtil.read(inputStream, true);

		DocumentType documentType = document.getDocumentType();

		String publicId = GetterUtil.getString(documentType.getPublicId());

		if (publicId.equals(
				"-//Liferay//DTD Resource Action Mapping 6.0.0//EN")) {

			if (_log.isWarnEnabled()) {
				_log.warn(
					"Please update " + source + " to use the 6.1.0 format");
			}
		}

		Element rootElement = document.getRootElement();

		for (Element resourceElement : rootElement.elements("resource")) {
			String file = resourceElement.attributeValue("file").trim();

			read(servletContextName, classLoader, file);

			String extFile = StringUtil.replace(file, ".xml", "-ext.xml");

			read(servletContextName, classLoader, extFile);
		}

		read(servletContextName, document);
	}

	@Override
	public void read(String servletContextName, InputStream inputStream)
		throws Exception {

		Document document = SAXReaderUtil.read(inputStream, true);

		read(servletContextName, document);
	}

	protected void checkGuestUnsupportedActions(
		Set<String> guestUnsupportedActions, Set<String> guestDefaultActions) {

		// Guest default actions cannot reference guest unsupported actions

		Iterator<String> itr = guestDefaultActions.iterator();

		while (itr.hasNext()) {
			String actionId = itr.next();

			if (guestUnsupportedActions.contains(actionId)) {
				itr.remove();
			}
		}
	}

	protected void checkModelActions(Set<String> actions) {
		if (!actions.contains(ActionKeys.PERMISSIONS)) {
			actions.add(ActionKeys.PERMISSIONS);
		}
	}

	protected void checkPortletActions(Portlet portlet, Set<String> actions) {
		if (!actions.contains(ActionKeys.ACCESS_IN_CONTROL_PANEL) &&
			!actions.contains(ActionKeys.ADD_TO_PAGE)) {

			actions.add(ActionKeys.ADD_TO_PAGE);
		}

		if ((portlet != null) &&
			(portlet.getControlPanelEntryCategory() != null) &&
			!actions.contains(ActionKeys.ACCESS_IN_CONTROL_PANEL)) {

			actions.add(ActionKeys.ACCESS_IN_CONTROL_PANEL);
		}

		if (!actions.contains(ActionKeys.CONFIGURATION)) {
			actions.add(ActionKeys.CONFIGURATION);
		}

		if (!actions.contains(ActionKeys.PERMISSIONS)) {
			actions.add(ActionKeys.PERMISSIONS);
		}

		if (!actions.contains(ActionKeys.VIEW)) {
			actions.add(ActionKeys.VIEW);
		}
	}

	protected void checkPortletActions(String name, Set<String> actions) {
		Portlet portlet = portletLocalService.getPortletById(name);

		checkPortletActions(portlet, actions);
	}

	protected void checkPortletGroupDefaultActions(Set<String> actions) {
		if (actions.isEmpty()) {
			actions.add(ActionKeys.VIEW);
		}
	}

	protected void checkPortletGuestDefaultActions(Set<String> actions) {
		if (actions.isEmpty()) {
			actions.add(ActionKeys.VIEW);
		}
	}

	protected void checkPortletLayoutManagerActions(Set<String> actions) {
		if (!actions.contains(ActionKeys.CONFIGURATION)) {
			actions.add(ActionKeys.CONFIGURATION);
		}

		if (!actions.contains(ActionKeys.PERMISSIONS)) {
			actions.add(ActionKeys.PERMISSIONS);
		}

		if (!actions.contains(ActionKeys.PREFERENCES)) {
			actions.add(ActionKeys.PREFERENCES);
		}

		if (!actions.contains(ActionKeys.VIEW)) {
			actions.add(ActionKeys.VIEW);
		}
	}

	protected Set<String> getActions(
		Map<String, Set<String>> actionsMap, String name) {

		Set<String> actions = actionsMap.get(name);

		if (actions == null) {
			actions = new LinkedHashSet<String>();

			actionsMap.put(name, actions);
		}

		return actions;
	}

	protected Element getPermissionsChildElement(
		Element parentElement, String childElementName) {

		Element permissionsElement = parentElement.element("permissions");

		if (permissionsElement != null) {
			return permissionsElement.element(childElementName);
		}
		else {
			return parentElement.element(childElementName);
		}
	}

	protected Set<String> getPortletMimeTypeActions(String name) {
		Set<String> actions = new LinkedHashSet<String>();

		Portlet portlet = portletLocalService.getPortletById(name);

		if (portlet != null) {
			Map<String, Set<String>> portletModes = portlet.getPortletModes();

			Set<String> mimeTypePortletModes = portletModes.get(
				ContentTypes.TEXT_HTML);

			if (mimeTypePortletModes != null) {
				for (String actionId : mimeTypePortletModes) {
					if (StringUtil.equalsIgnoreCase(actionId, "edit")) {
						actions.add(ActionKeys.PREFERENCES);
					}
					else if (StringUtil.equalsIgnoreCase(
								actionId, "edit_guest")) {

						actions.add(ActionKeys.GUEST_PREFERENCES);
					}
					else {
						actions.add(StringUtil.toUpperCase(actionId));
					}
				}
			}
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to obtain resource actions for unknown portlet " +
						name);
			}
		}

		return actions;
	}

	protected int[] getRoleTypes(
		long companyId, Group group, String modelResource) {

		int[] types = RoleConstants.TYPES_REGULAR_AND_SITE;

		if (isPortalModelResource(modelResource)) {
			if (modelResource.equals(Organization.class.getName()) ||
				modelResource.equals(User.class.getName())) {

				types = RoleConstants.TYPES_ORGANIZATION_AND_REGULAR;
			}
			else {
				types = RoleConstants.TYPES_REGULAR;
			}
		}
		else {
			if (group != null) {
				if (group.isLayout()) {
					try {
						group = GroupServiceUtil.getGroup(
							group.getParentGroupId());
					}
					catch (Exception e) {
					}
				}

				if (group.isOrganization()) {
					types =
						RoleConstants.TYPES_ORGANIZATION_AND_REGULAR_AND_SITE;
				}
				else if (group.isUser()) {
					types = RoleConstants.TYPES_REGULAR;
				}
			}
		}

		return types;
	}

	protected void read(String servletContextName, Document document)
		throws Exception {

		Element rootElement = document.getRootElement();

		if (PropsValues.RESOURCE_ACTIONS_READ_PORTLET_RESOURCES) {
			for (Element portletResourceElement :
					rootElement.elements("portlet-resource")) {

				readPortletResource(servletContextName, portletResourceElement);
			}
		}

		for (Element modelResourceElement :
				rootElement.elements("model-resource")) {

			readModelResource(servletContextName, modelResourceElement);
		}
	}

	protected List<String> readActionKeys(Element parentElement) {
		List<String> actions = new ArrayList<String>();

		for (Element actionKeyElement : parentElement.elements("action-key")) {
			String actionKey = actionKeyElement.getTextTrim();

			if (Validator.isNull(actionKey)) {
				continue;
			}

			actions.add(actionKey);
		}

		return actions;
	}

	protected void readGroupDefaultActions(
		Element parentElement, Map<String, Set<String>> actionsMap,
		String name) {

		Set<String> groupDefaultActions = getActions(actionsMap, name);

		Element groupDefaultsElement = getPermissionsChildElement(
			parentElement, "site-member-defaults");

		if (groupDefaultsElement == null) {
			groupDefaultsElement = getPermissionsChildElement(
				parentElement, "community-defaults");

			if (_log.isWarnEnabled() && (groupDefaultsElement != null)) {
				_log.warn(
					"The community-defaults element is deprecated. Use the " +
						"site-member-defaults element instead.");
			}
		}

		groupDefaultActions.addAll(readActionKeys(groupDefaultsElement));

		actionsMap.put(name, groupDefaultActions);
	}

	protected Set<String> readGuestDefaultActions(
		Element parentElement, Map<String, Set<String>> actionsMap,
		String name) {

		Set<String> guestDefaultActions = getActions(actionsMap, name);

		Element guestDefaultsElement = getPermissionsChildElement(
			parentElement, "guest-defaults");

		guestDefaultActions.addAll(readActionKeys(guestDefaultsElement));

		return guestDefaultActions;
	}

	protected void readGuestUnsupportedActions(
		Element parentElement, Map<String, Set<String>> actionsMap, String name,
		Set<String> guestDefaultActions) {

		Set<String> guestUnsupportedActions = getActions(actionsMap, name);

		Element guestUnsupportedElement = getPermissionsChildElement(
			parentElement, "guest-unsupported");

		guestUnsupportedActions.addAll(readActionKeys(guestUnsupportedElement));

		checkGuestUnsupportedActions(
			guestUnsupportedActions, guestDefaultActions);

		actionsMap.put(name, guestUnsupportedActions);
	}

	protected void readLayoutManagerActions(
		Element parentElement, Map<String, Set<String>> actionsMap, String name,
		Set<String> supportsActions) {

		Set<String> layoutManagerActions = getActions(actionsMap, name);

		Element layoutManagerElement = getPermissionsChildElement(
			parentElement, "layout-manager");

		if (layoutManagerElement != null) {
			layoutManagerActions.addAll(readActionKeys(layoutManagerElement));
		}
		else {
			layoutManagerActions.addAll(supportsActions);
		}

		actionsMap.put(name, layoutManagerActions);
	}

	protected void readModelResource(
			String servletContextName, Element modelResourceElement)
		throws Exception {

		String name = modelResourceElement.elementTextTrim("model-name");

		Element portletRefElement = modelResourceElement.element("portlet-ref");

		for (Element portletNameElement :
				portletRefElement.elements("portlet-name")) {

			String portletName = portletNameElement.getTextTrim();

			if (servletContextName != null) {
				portletName = portletName.concat(
					PortletConstants.WAR_SEPARATOR).concat(servletContextName);
			}

			portletName = JS.getSafeName(portletName);

			// Reference for a portlet to child models

			Set<String> modelResources = _portletModelResources.get(
				portletName);

			if (modelResources == null) {
				modelResources = new HashSet<String>();

				_portletModelResources.put(portletName, modelResources);
			}

			modelResources.add(name);

			// Reference for a model to parent portlets

			Set<String> portletResources = _modelPortletResources.get(name);

			if (portletResources == null) {
				portletResources = new HashSet<String>();

				_modelPortletResources.put(name, portletResources);
			}

			portletResources.add(portletName);

			// Reference for a model to root portlets

			boolean root = GetterUtil.getBoolean(
				modelResourceElement.elementText("root"));

			if (root) {
				_portletRootModelResource.put(portletName, name);
			}
		}

		double weight = GetterUtil.getDouble(
			modelResourceElement.elementTextTrim("weight"), 100);

		_modelResourceWeights.put(name, weight);

		Set<String> supportsActions = readSupportsActions(
			modelResourceElement, _modelResourceActions, name);

		checkModelActions(supportsActions);

		if (supportsActions.size() > 64) {
			throw new ResourceActionsException(
				"There are more than 64 actions for resource " + name);
		}

		_modelResourceActions.put(name, supportsActions);

		readGroupDefaultActions(
			modelResourceElement, _modelResourceGroupDefaultActions, name);

		Set<String> guestDefaultActions = readGuestDefaultActions(
			modelResourceElement, _modelResourceGuestDefaultActions, name);

		readGuestUnsupportedActions(
			modelResourceElement, _modelResourceGuestUnsupportedActions, name,
			guestDefaultActions);

		_modelResourceGuestDefaultActions.put(name, guestDefaultActions);

		readOwnerDefaultActions(
			modelResourceElement, _modelResourceOwnerDefaultActions, name);
	}

	protected void readOwnerDefaultActions(
		Element parentElement, Map<String, Set<String>> actionsMap,
		String name) {

		Set<String> ownerDefaultActions = getActions(actionsMap, name);

		Element ownerDefaultsElement = getPermissionsChildElement(
			parentElement, "owner-defaults");

		if (ownerDefaultsElement == null) {
			return;
		}

		ownerDefaultActions.addAll(readActionKeys(ownerDefaultsElement));

		actionsMap.put(name, ownerDefaultActions);
	}

	protected void readPortletResource(
			String servletContextName, Element portletResourceElement)
		throws Exception {

		String name = portletResourceElement.elementTextTrim("portlet-name");

		if (servletContextName != null) {
			name = name.concat(PortletConstants.WAR_SEPARATOR).concat(
				servletContextName);
		}

		name = JS.getSafeName(name);

		Set<String> supportsActions = readSupportsActions(
			portletResourceElement, _portletResourceActions, name);

		supportsActions.addAll(getPortletMimeTypeActions(name));

		if (!name.equals(PortletKeys.PORTAL)) {
			checkPortletActions(name, supportsActions);
		}

		if (supportsActions.size() > 64) {
			throw new ResourceActionsException(
				"There are more than 64 actions for resource " + name);
		}

		_portletResourceActions.put(name, supportsActions);

		readGroupDefaultActions(
			portletResourceElement, _portletResourceGroupDefaultActions, name);

		Set<String> guestDefaultActions = readGuestDefaultActions(
			portletResourceElement, _portletResourceGuestDefaultActions, name);

		readGuestUnsupportedActions(
			portletResourceElement, _portletResourceGuestUnsupportedActions,
			name, guestDefaultActions);

		_portletResourceGuestDefaultActions.put(name, guestDefaultActions);

		readLayoutManagerActions(
			portletResourceElement, _portletResourceLayoutManagerActions, name,
			supportsActions);
	}

	protected Set<String> readSupportsActions(
		Element parentElement, Map<String, Set<String>> actionsMap,
		String name) {

		Set<String> supportsActions = getActions(actionsMap, name);

		Element supportsElement = getPermissionsChildElement(
			parentElement, "supports");

		supportsActions.addAll(readActionKeys(supportsElement));

		return supportsActions;
	}

	@BeanReference(type = PortletLocalService.class)
	protected PortletLocalService portletLocalService;

	@BeanReference(type = ResourceActionLocalService.class)
	protected ResourceActionLocalService resourceActionLocalService;

	@BeanReference(type = RoleLocalService.class)
	protected RoleLocalService roleLocalService;

	private static final String _ACTION_NAME_PREFIX = "action.";

	private static final String _MODEL_RESOURCE_NAME_PREFIX = "model.resource.";

	private static final String[] _ORGANIZATION_MODEL_RESOURCES = {
		Organization.class.getName(), PasswordPolicy.class.getName(),
		User.class.getName()
	};

	private static final String[] _PORTAL_MODEL_RESOURCES = {
		ExpandoColumn.class.getName(), LayoutPrototype.class.getName(),
		LayoutSetPrototype.class.getName(), MDRRuleGroup.class.getName(),
		Organization.class.getName(), PasswordPolicy.class.getName(),
		Role.class.getName(), User.class.getName(), UserGroup.class.getName()
	};

	private static final Log _log = LogFactoryUtil.getLog(
		ResourceActionsImpl.class);

	private Map<String, Set<String>> _modelPortletResources;
	private Map<String, Set<String>> _modelResourceActions;
	private Map<String, Set<String>> _modelResourceGroupDefaultActions;
	private Map<String, Set<String>> _modelResourceGuestDefaultActions;
	private Map<String, Set<String>> _modelResourceGuestUnsupportedActions;
	private Map<String, Set<String>> _modelResourceOwnerDefaultActions;
	private Map<String, Double> _modelResourceWeights;
	private Set<String> _organizationModelResources;
	private Set<String> _portalModelResources;
	private Map<String, Set<String>> _portletModelResources;
	private Map<String, Set<String>> _portletResourceActions;
	private Map<String, Set<String>> _portletResourceGroupDefaultActions;
	private Map<String, Set<String>> _portletResourceGuestDefaultActions;
	private Map<String, Set<String>> _portletResourceGuestUnsupportedActions;
	private Map<String, Set<String>> _portletResourceLayoutManagerActions;
	private Map<String, String> _portletRootModelResource;

}