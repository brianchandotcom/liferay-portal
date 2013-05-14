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

package com.liferay.portal.security.permission;

import com.liferay.portal.NoSuchResourceActionException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.SystemException;
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
import com.liferay.portal.kernel.util.UniqueList;
import com.liferay.portal.kernel.util.UnmodifiableList;
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
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.PageContext;

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
		_portletResourceActions = new HashMap<String, List<String>>();
		_portletResourceGroupDefaultActions =
			new HashMap<String, List<String>>();
		_portletResourceGuestDefaultActions =
			new HashMap<String, List<String>>();
		_portletResourceGuestUnsupportedActions =
			new HashMap<String, List<String>>();
		_portletResourceLayoutManagerActions =
			new HashMap<String, List<String>>();
		_modelPortletResources = new HashMap<String, Set<String>>();
		_modelResourceActions = new HashMap<String, List<String>>();
		_modelResourceGroupDefaultActions = new HashMap<String, List<String>>();
		_modelResourceGuestDefaultActions = new HashMap<String, List<String>>();
		_modelResourceGuestUnsupportedActions =
			new HashMap<String, List<String>>();
		_modelResourceOwnerDefaultActions = new HashMap<String, List<String>>();

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
	public String getAction(PageContext pageContext, String action) {
		String key = getActionNamePrefix() + action;

		String value = LanguageUtil.get(pageContext, key, null);

		if ((value == null) || value.equals(key)) {
			value = PortletResourceBundles.getString(pageContext, key);
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
		PageContext pageContext, List<String> actions) {

		List<String> actionNames = new UniqueList<String>();

		for (String action : actions) {
			actionNames.add(getAction(pageContext, action));
		}

		return actionNames;
	}

	@Override
	public List<String> getActionsNames(
		PageContext pageContext, String name, long actionIds) {

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

			return getActionsNames(pageContext, actions);
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
			return new UniqueList<String>();
		}
		else {
			return Collections.list(Collections.enumeration(resources));
		}
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
	public String getModelResource(PageContext pageContext, String name) {
		String key = getModelResourceNamePrefix() + name;

		String value = LanguageUtil.get(pageContext, key, null);

		if ((value == null) || value.equals(key)) {
			value = PortletResourceBundles.getString(pageContext, key);
		}

		if (value == null) {
			value = key;
		}

		return value;
	}

	@Override
	public List<String> getModelResourceActions(String name) {
		return getActions(_modelResourceActions, name);
	}

	@Override
	public List<String> getModelResourceGroupDefaultActions(String name) {
		return getActions(_modelResourceGroupDefaultActions, name);
	}

	@Override
	public List<String> getModelResourceGuestDefaultActions(String name) {
		return getActions(_modelResourceGuestDefaultActions, name);
	}

	@Override
	public List<String> getModelResourceGuestUnsupportedActions(String name) {
		return getActions(_modelResourceGuestUnsupportedActions, name);
	}

	@Override
	public String getModelResourceNamePrefix() {
		return _MODEL_RESOURCE_NAME_PREFIX;
	}

	@Override
	public List<String> getModelResourceOwnerDefaultActions(String name) {
		return getActions(_modelResourceOwnerDefaultActions, name);
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
			return new UniqueList<String>();
		}
		else {
			return Collections.list(Collections.enumeration(resources));
		}
	}

	@Override
	public List<String> getPortletNames() {
		return ListUtil.fromMapKeys(_portletModelResources);
	}

	@Override
	public List<String> getPortletResourceActions(Portlet portlet) {
		List<String> actions = ListUtil.copy(
			getPortletResourceActions(portlet.getPortletId()));

		synchronized (this) {
			checkPortletActions(portlet, actions);

			setActions(
				_portletResourceActions, portlet.getPortletId(), actions);
		}

		return actions;
	}

	@Override
	public List<String> getPortletResourceActions(String name) {
		name = PortletConstants.getRootPortletId(name);

		List<String> actions = getActions(_portletResourceActions, name);

		if (!actions.isEmpty()) {
			return actions;
		}

		synchronized (this) {
			actions = getPortletMimeTypeActions(name);

			if (!name.equals(PortletKeys.PORTAL)) {
				checkPortletActions(name, actions);
			}

			List<String> groupDefaultActions =
				_portletResourceGroupDefaultActions.get(name);

			if (groupDefaultActions == null) {
				groupDefaultActions = new UniqueList<String>();

				checkPortletGroupDefaultActions(groupDefaultActions);

				_portletResourceGroupDefaultActions.put(
					name, new UnmodifiableList<String>(groupDefaultActions));
			}

			List<String> guestDefaultActions =
				_portletResourceGuestDefaultActions.get(name);

			if (guestDefaultActions == null) {
				guestDefaultActions = new UniqueList<String>();

				checkPortletGuestDefaultActions(guestDefaultActions);

				_portletResourceGuestDefaultActions.put(
					name, new UnmodifiableList<String>(guestDefaultActions));
			}

			List<String> layoutManagerActions =
				_portletResourceLayoutManagerActions.get(name);

			if (layoutManagerActions == null) {
				layoutManagerActions = new UniqueList<String>();

				checkPortletLayoutManagerActions(layoutManagerActions);

				_portletResourceLayoutManagerActions.put(
					name, new UnmodifiableList<String>(layoutManagerActions));
			}

			actions = setActions(_portletResourceActions, name, actions);
		}

		return actions;
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

		return getActions(_portletResourceGroupDefaultActions, name);
	}

	@Override
	public List<String> getPortletResourceGuestDefaultActions(String name) {
		name = PortletConstants.getRootPortletId(name);

		return getActions(_portletResourceGuestDefaultActions, name);
	}

	@Override
	public List<String> getPortletResourceGuestUnsupportedActions(String name) {
		name = PortletConstants.getRootPortletId(name);

		List<String> actions = getActions(
			_portletResourceGuestUnsupportedActions, name);

		if (actions.contains(ActionKeys.CONFIGURATION) &&
			actions.contains(ActionKeys.PERMISSIONS)) {

			return actions;
		}

		actions = new UniqueList<String>(actions);

		actions.add(ActionKeys.CONFIGURATION);
		actions.add(ActionKeys.PERMISSIONS);

		setActions(_portletResourceGuestUnsupportedActions, name, actions);

		return actions;
	}

	@Override
	public List<String> getPortletResourceLayoutManagerActions(String name) {
		name = PortletConstants.getRootPortletId(name);

		List<String> actions = getActions(
			_portletResourceLayoutManagerActions, name);

		// This check can never return an empty list. If the list is empty, it
		// means that the portlet does not have an explicit resource-actions
		// configuration file and should therefore be handled as if it has
		// defaults of CONFIGURATION, PREFERENCES, and VIEW.

		if (actions.isEmpty()) {
			actions = new UniqueList<String>();

			actions.add(ActionKeys.CONFIGURATION);
			actions.add(ActionKeys.PREFERENCES);
			actions.add(ActionKeys.VIEW);

			setActions(_portletResourceLayoutManagerActions, name, actions);
		}

		return actions;
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
	@Override
	public List<Role> getRoles(
			long companyId, Group group, String modelResource)
		throws SystemException {

		return getRoles(companyId, group, modelResource, null);
	}

	@Override
	public List<Role> getRoles(
			long companyId, Group group, String modelResource, int[] roleTypes)
		throws SystemException {

		List<Role> allRoles = roleLocalService.getRoles(companyId);

		if (roleTypes == null) {
			roleTypes = getRoleTypes(companyId, group, modelResource);
		}

		List<Role> roles = new ArrayList<Role>();

		for (int roleType : roleTypes) {
			for (Role role : allRoles) {
				if (role.getType() == roleType) {
					roles.add(role);
				}
			}
		}

		return roles;
	}

	@Override
	public boolean hasModelResourceActions(String name) {
		List<String> actions = _modelResourceActions.get(name);

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
		List<String> guestUnsupportedActions,
		List<String> guestDefaultActions) {

		// Guest default actions cannot reference guest unsupported actions

		Iterator<String> itr = guestDefaultActions.iterator();

		while (itr.hasNext()) {
			String actionId = itr.next();

			if (guestUnsupportedActions.contains(actionId)) {
				itr.remove();
			}
		}
	}

	protected void checkModelActions(List<String> actions) {
		if (!actions.contains(ActionKeys.PERMISSIONS)) {
			actions.add(ActionKeys.PERMISSIONS);
		}
	}

	protected void checkPortletActions(Portlet portlet, List<String> actions) {
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

	protected void checkPortletActions(String name, List<String> actions) {
		Portlet portlet = portletLocalService.getPortletById(name);

		checkPortletActions(portlet, actions);
	}

	protected void checkPortletGroupDefaultActions(List<String> actions) {
		if (actions.isEmpty()) {
			actions.add(ActionKeys.VIEW);
		}
	}

	protected void checkPortletGuestDefaultActions(List<String> actions) {
		if (actions.isEmpty()) {
			actions.add(ActionKeys.VIEW);
		}
	}

	protected void checkPortletLayoutManagerActions(List<String> actions) {
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

	protected List<String> getActions(
		Map<String, List<String>> actionsMap, String name) {

		List<String> actions = actionsMap.get(name);

		if (actions == null) {
			actions = new UniqueList<String>();

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

	protected List<String> getPortletMimeTypeActions(String name) {
		List<String> actions = new UniqueList<String>();

		Portlet portlet = portletLocalService.getPortletById(name);

		if (portlet != null) {
			Map<String, Set<String>> portletModes = portlet.getPortletModes();

			Set<String> mimeTypePortletModes = portletModes.get(
				ContentTypes.TEXT_HTML);

			if (mimeTypePortletModes != null) {
				for (String actionId : mimeTypePortletModes) {
					if (actionId.equalsIgnoreCase("edit")) {
						actions.add(ActionKeys.PREFERENCES);
					}
					else if (actionId.equalsIgnoreCase("edit_guest")) {
						actions.add(ActionKeys.GUEST_PREFERENCES);
					}
					else {
						actions.add(actionId.toUpperCase());
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

		int[] types = {
			RoleConstants.TYPE_REGULAR, RoleConstants.TYPE_SITE
		};

		if (isPortalModelResource(modelResource)) {
			if (modelResource.equals(Organization.class.getName()) ||
				modelResource.equals(User.class.getName())) {

				types = new int[] {
					RoleConstants.TYPE_REGULAR, RoleConstants.TYPE_ORGANIZATION
				};
			}
			else {
				types = new int[] {RoleConstants.TYPE_REGULAR};
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
					types = new int[] {
						RoleConstants.TYPE_REGULAR,
						RoleConstants.TYPE_ORGANIZATION, RoleConstants.TYPE_SITE
					};
				}
				else if (group.isUser()) {
					types = new int[] {RoleConstants.TYPE_REGULAR};
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
		Element parentElement, Map<String, List<String>> actionsMap,
		String name) {

		List<String> groupDefaultActions = new UniqueList<String>(
			getActions(actionsMap, name));

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

		setActions(actionsMap, name, groupDefaultActions);
	}

	protected List<String> readGuestDefaultActions(
		Element parentElement, Map<String, List<String>> actionsMap,
		String name) {

		List<String> guestDefaultActions = new UniqueList<String>(
			getActions(actionsMap, name));

		Element guestDefaultsElement = getPermissionsChildElement(
			parentElement, "guest-defaults");

		guestDefaultActions.addAll(readActionKeys(guestDefaultsElement));

		return guestDefaultActions;
	}

	protected void readGuestUnsupportedActions(
		Element parentElement, Map<String, List<String>> actionsMap,
		String name, List<String> guestDefaultActions) {

		List<String> guestUnsupportedActions = new UniqueList<String>(
			getActions(actionsMap, name));

		Element guestUnsupportedElement = getPermissionsChildElement(
			parentElement, "guest-unsupported");

		guestUnsupportedActions.addAll(readActionKeys(guestUnsupportedElement));

		checkGuestUnsupportedActions(
			guestUnsupportedActions, guestDefaultActions);

		setActions(actionsMap, name, guestUnsupportedActions);
	}

	protected void readLayoutManagerActions(
		Element parentElement, Map<String, List<String>> actionsMap,
		String name, List<String> supportsActions) {

		List<String> layoutManagerActions = new UniqueList<String>(
			getActions(actionsMap, name));

		Element layoutManagerElement = getPermissionsChildElement(
			parentElement, "layout-manager");

		if (layoutManagerElement != null) {
			layoutManagerActions.addAll(readActionKeys(layoutManagerElement));
		}
		else {
			layoutManagerActions.addAll(supportsActions);
		}

		setActions(actionsMap, name, layoutManagerActions);
	}

	protected void readModelResource(
		String servletContextName, Element modelResourceElement) {

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
		}

		List<String> supportsActions = readSupportsActions(
			modelResourceElement, _modelResourceActions, name);

		checkModelActions(supportsActions);

		setActions(_modelResourceActions, name, supportsActions);

		readGroupDefaultActions(
			modelResourceElement, _modelResourceGroupDefaultActions, name);

		List<String> guestDefaultActions = readGuestDefaultActions(
			modelResourceElement, _modelResourceGuestDefaultActions, name);

		readGuestUnsupportedActions(
			modelResourceElement, _modelResourceGuestUnsupportedActions, name,
			guestDefaultActions);

		setActions(
			_modelResourceGuestDefaultActions, name, guestDefaultActions);

		readOwnerDefaultActions(
			modelResourceElement, _modelResourceOwnerDefaultActions, name);
	}

	protected void readOwnerDefaultActions(
		Element parentElement, Map<String, List<String>> actionsMap,
		String name) {

		List<String> ownerDefaultActions = new UniqueList<String>(
			getActions(actionsMap, name));

		Element ownerDefaultsElement = getPermissionsChildElement(
			parentElement, "owner-defaults");

		if (ownerDefaultsElement == null) {
			return;
		}

		ownerDefaultActions.addAll(readActionKeys(ownerDefaultsElement));

		setActions(actionsMap, name, ownerDefaultActions);
	}

	protected void readPortletResource(
		String servletContextName, Element portletResourceElement) {

		String name = portletResourceElement.elementTextTrim("portlet-name");

		if (servletContextName != null) {
			name = name.concat(PortletConstants.WAR_SEPARATOR).concat(
				servletContextName);
		}

		name = JS.getSafeName(name);

		List<String> supportsActions = readSupportsActions(
			portletResourceElement, _portletResourceActions, name);

		supportsActions.addAll(getPortletMimeTypeActions(name));

		if (!name.equals(PortletKeys.PORTAL)) {
			checkPortletActions(name, supportsActions);
		}

		supportsActions = setActions(
			_portletResourceActions, name, supportsActions);

		readGroupDefaultActions(
			portletResourceElement, _portletResourceGroupDefaultActions, name);

		List<String> guestDefaultActions = readGuestDefaultActions(
			portletResourceElement, _portletResourceGuestDefaultActions, name);

		readGuestUnsupportedActions(
			portletResourceElement, _portletResourceGuestUnsupportedActions,
			name, guestDefaultActions);

		setActions(
			_portletResourceGuestDefaultActions, name, guestDefaultActions);

		readLayoutManagerActions(
			portletResourceElement, _portletResourceLayoutManagerActions, name,
			supportsActions);
	}

	protected List<String> readSupportsActions(
		Element parentElement, Map<String, List<String>> actionsMap,
		String name) {

		List<String> supportsActions = new UniqueList<String>(
			getActions(actionsMap, name));

		Element supportsElement = getPermissionsChildElement(
			parentElement, "supports");

		supportsActions.addAll(readActionKeys(supportsElement));

		return supportsActions;
	}

	protected List<String> setActions(
		Map<String, List<String>> actionsMap, String name,
		List<String> actions) {

		actions = new UnmodifiableList<String>(actions);

		actionsMap.put(name, actions);

		return actions;
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

	private static Log _log = LogFactoryUtil.getLog(ResourceActionsImpl.class);

	private Map<String, Set<String>> _modelPortletResources;
	private Map<String, List<String>> _modelResourceActions;
	private Map<String, List<String>> _modelResourceGroupDefaultActions;
	private Map<String, List<String>> _modelResourceGuestDefaultActions;
	private Map<String, List<String>> _modelResourceGuestUnsupportedActions;
	private Map<String, List<String>> _modelResourceOwnerDefaultActions;
	private Set<String> _organizationModelResources;
	private Set<String> _portalModelResources;
	private Map<String, Set<String>> _portletModelResources;
	private Map<String, List<String>> _portletResourceActions;
	private Map<String, List<String>> _portletResourceGroupDefaultActions;
	private Map<String, List<String>> _portletResourceGuestDefaultActions;
	private Map<String, List<String>> _portletResourceGuestUnsupportedActions;
	private Map<String, List<String>> _portletResourceLayoutManagerActions;

}