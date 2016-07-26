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

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.NoSuchResourceActionException;
import com.liferay.portal.kernel.exception.ResourceActionsException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.ModelResourceActionsBag;
import com.liferay.portal.kernel.security.permission.PortletResourceActionsBag;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.GroupServiceUtil;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentType;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerList;
import com.liferay.util.JS;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.util.RequestUtils;

/**
 * @author Brian Wing Shun Chan
 * @author Daeyoung Song
 * @author Raymond Augé
 */
@DoPrivileged
public class ResourceActionsImpl implements ResourceActions {

	public ResourceActionsImpl() {
		_resourceBundleLoaders = ServiceTrackerCollections.openList(
			ResourceBundleLoader.class);
	}

	public void afterPropertiesSet() {
		_portletResourceActionsBags = new HashMap<>();
		_modelResourceActionsBags = new HashMap<>();

		try {
			Class<?> clazz = getClass();

			ClassLoader classLoader = clazz.getClassLoader();

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

	public void destroy() {
		_resourceBundleLoaders.close();
	}

	@Override
	public String getAction(HttpServletRequest request, String action) {
		String key = getActionNamePrefix() + action;

		String value = LanguageUtil.get(request, key, null);

		if ((value == null) || value.equals(key)) {
			value = getResourceBundlesString(request, key);
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
			value = getResourceBundlesString(locale, key);
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

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public List<String> getActionsNames(
		HttpServletRequest request, List<String> actions) {

		Set<String> actionNames = new LinkedHashSet<>();

		for (String action : actions) {
			actionNames.add(getAction(request, action));
		}

		return new ArrayList<>(actionNames);
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public List<String> getActionsNames(
		HttpServletRequest request, String name, long actionIds) {

		try {
			List<ResourceAction> resourceActions =
				resourceActionLocalService.getResourceActions(name);

			List<String> actions = new ArrayList<>();

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
	public String getCompositeModelName(String... classNames) {
		if (ArrayUtil.isEmpty(classNames)) {
			return StringPool.BLANK;
		}

		Arrays.sort(classNames);

		StringBundler sb = new StringBundler(classNames.length * 2);

		for (String className : classNames) {
			sb.append(className);
			sb.append(getCompositeModelNameSeparator());
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	@Override
	public String getCompositeModelNameSeparator() {
		return _COMPOSITE_MODEL_NAME_SEPARATOR;
	}

	@Override
	public List<String> getModelNames() {
		return ListUtil.fromMapKeys(_modelResourceActionsBags);
	}

	@Override
	public List<String> getModelPortletResources(String name) {
		ModelResourceActionsBag modelResourceActionsBag =
			getModelResourceActionsBag(name);

		return new ArrayList<>(modelResourceActionsBag.getResources());
	}

	@Override
	public String getModelResource(HttpServletRequest request, String name) {
		String key = getModelResourceNamePrefix() + name;

		String value = LanguageUtil.get(request, key, null);

		if ((value == null) || value.equals(key)) {
			value = getResourceBundlesString(request, key);
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
			value = getResourceBundlesString(locale, key);
		}

		if (value == null) {
			value = key;
		}

		return value;
	}

	@Override
	public List<String> getModelResourceActions(String name) {
		ModelResourceActionsBag modelResourceActionsBag =
			getModelResourceActionsBag(name);

		return new ArrayList<>(modelResourceActionsBag.getResourceActions());
	}

	@Override
	public List<String> getModelResourceGroupDefaultActions(String name) {
		ModelResourceActionsBag modelResourceActionsBag =
			getModelResourceActionsBag(name);

		return new ArrayList<>(
			modelResourceActionsBag.getResourceGroupDefaultActions());
	}

	@Override
	public List<String> getModelResourceGuestDefaultActions(String name) {
		ModelResourceActionsBag modelResourceActionsBag =
			getModelResourceActionsBag(name);

		return new ArrayList<>(
			modelResourceActionsBag.getResourceGuestDefaultActions());
	}

	@Override
	public List<String> getModelResourceGuestUnsupportedActions(String name) {
		ModelResourceActionsBag modelResourceActionsBag =
			getModelResourceActionsBag(name);

		return new ArrayList<>(
			modelResourceActionsBag.getResourceGuestUnsupportedActions());
	}

	@Override
	public String getModelResourceNamePrefix() {
		return _MODEL_RESOURCE_NAME_PREFIX;
	}

	@Override
	public List<String> getModelResourceOwnerDefaultActions(String name) {
		ModelResourceActionsBag modelResourceActionsBag =
			getModelResourceActionsBag(name);

		return new ArrayList<>(
			modelResourceActionsBag.getResourceOwnerDefaultActions());
	}

	@Override
	public Double getModelResourceWeight(String name) {
		ModelResourceActionsBag modelResourceActionsBag =
			getModelResourceActionsBag(name);

		Map<String, Double> modelResourceWeights =
			modelResourceActionsBag.getResourceWeights();

		return modelResourceWeights.get(name);
	}

	@Override
	public String[] getOrganizationModelResources() {
		return _organizationModelResources.toArray(
			new String[_organizationModelResources.size()]);
	}

	@Override
	public String[] getPortalModelResources() {
		return _portalModelResources.toArray(
			new String[_portalModelResources.size()]);
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

		PortletResourceActionsBag portletResourceActionsBag =
			getPortletResourceActionsBag(portletName);

		Set<String> resources = portletResourceActionsBag.getResources();

		if (resources == null) {
			return new ArrayList<>();
		}
		else {
			return new ArrayList<>(resources);
		}
	}

	@Override
	public List<String> getPortletNames() {
		return ListUtil.fromMapKeys(_portletResourceActionsBags);
	}

	@Override
	public List<String> getPortletResourceActions(Portlet portlet) {
		Set<String> actions = new LinkedHashSet<>(
			getPortletResourceActions(portlet.getPortletId()));

		synchronized (this) {
			checkPortletActions(portlet, actions);
		}

		return new ArrayList<>(actions);
	}

	@Override
	public List<String> getPortletResourceActions(String name) {
		name = PortletConstants.getRootPortletId(name);

		PortletResourceActionsBag portletResourceActionsBag =
			getPortletResourceActionsBag(name);

		Set<String> actions = portletResourceActionsBag.getResourceActions();

		if (!actions.isEmpty()) {
			return new ArrayList<>(actions);
		}

		synchronized (this) {
			actions = getPortletMimeTypeActions(name);

			if (!name.equals(PortletKeys.PORTAL)) {
				checkPortletActions(name, actions);
			}

			Set<String> groupDefaultActions =
				portletResourceActionsBag.getResourceGroupDefaultActions();

			checkPortletGroupDefaultActions(groupDefaultActions);

			Set<String> guestDefaultActions =
				portletResourceActionsBag.getResourceGuestDefaultActions();

			checkPortletGuestDefaultActions(guestDefaultActions);

			Set<String> layoutManagerActions =
				portletResourceActionsBag.getResourceLayoutManagerActions();

			checkPortletLayoutManagerActions(layoutManagerActions);
		}

		return new ArrayList<>(actions);
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

		PortletResourceActionsBag portletResourceActionsBag =
			getPortletResourceActionsBag(name);

		return new ArrayList<>(
			portletResourceActionsBag.getResourceGroupDefaultActions());
	}

	@Override
	public List<String> getPortletResourceGuestDefaultActions(String name) {
		name = PortletConstants.getRootPortletId(name);

		PortletResourceActionsBag portletResourceActionsBag =
			getPortletResourceActionsBag(name);

		return new ArrayList<>(
			portletResourceActionsBag.getResourceGuestDefaultActions());
	}

	@Override
	public List<String> getPortletResourceGuestUnsupportedActions(String name) {
		name = PortletConstants.getRootPortletId(name);

		PortletResourceActionsBag portletResourceActionsBag =
			getPortletResourceActionsBag(name);

		Set<String> actions =
			portletResourceActionsBag.getResourceGuestUnsupportedActions();

		if (actions.contains(ActionKeys.CONFIGURATION) &&
			actions.contains(ActionKeys.PERMISSIONS)) {

			return new ArrayList<>(actions);
		}

		actions.add(ActionKeys.CONFIGURATION);
		actions.add(ActionKeys.PERMISSIONS);

		return new ArrayList<>(actions);
	}

	@Override
	public List<String> getPortletResourceLayoutManagerActions(String name) {
		name = PortletConstants.getRootPortletId(name);

		PortletResourceActionsBag portletResourceActionsBag =
			getPortletResourceActionsBag(name);

		Set<String> actions =
			portletResourceActionsBag.getResourceLayoutManagerActions();

		// This check can never return an empty list. If the list is empty, it
		// means that the portlet does not have an explicit resource-actions
		// configuration file and should therefore be handled as if it has
		// defaults of CONFIGURATION, PREFERENCES, and VIEW.

		if (actions.isEmpty()) {
			actions = new LinkedHashSet<>();

			actions.add(ActionKeys.CONFIGURATION);
			actions.add(ActionKeys.PREFERENCES);
			actions.add(ActionKeys.VIEW);
		}

		return new ArrayList<>(actions);
	}

	@Override
	public String getPortletRootModelResource(String portletName) {
		portletName = PortletConstants.getRootPortletId(portletName);

		PortletResourceActionsBag portletResourceActionsBag =
			getPortletResourceActionsBag(portletName);

		Map<String, String> portletRootModelResource =
			portletResourceActionsBag.getPortletRootModelResources();

		return portletRootModelResource.get(portletName);
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

	@Override
	public List<Role> getRoles(
		long companyId, Group group, String modelResource, int[] roleTypes) {

		if (roleTypes == null) {
			roleTypes = getRoleTypes(companyId, group, modelResource);
		}

		return roleLocalService.getRoles(companyId, roleTypes);
	}

	@Override
	public String[] getRootModelResources() {
		return _rootModelResources.toArray(
			new String[_rootModelResources.size()]);
	}

	@Override
	public boolean hasModelResourceActions(String name) {
		ModelResourceActionsBag modelResourceActionsBag =
			getModelResourceActionsBag(name);

		Set<String> actions = modelResourceActionsBag.getResourceActions();

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
	public boolean isRootModelResource(String modelResource) {
		if (_rootModelResources.contains(modelResource)) {
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
			if (_log.isInfoEnabled() && !source.endsWith("-ext.xml") &&
				!source.startsWith("META-INF/")) {

				_log.info("Cannot load " + source);
			}

			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Loading " + source);
		}

		Document document = UnsecureSAXReaderUtil.read(inputStream, true);

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

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public void read(String servletContextName, InputStream inputStream)
		throws Exception {

		Document document = UnsecureSAXReaderUtil.read(inputStream, true);

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
		checkPortletLayoutManagerActions(actions);

		if ((portlet != null) &&
			(portlet.getControlPanelEntryCategory() != null) &&
			!actions.contains(ActionKeys.ACCESS_IN_CONTROL_PANEL)) {

			actions.add(ActionKeys.ACCESS_IN_CONTROL_PANEL);
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
		if (!actions.contains(ActionKeys.ACCESS_IN_CONTROL_PANEL) &&
			!actions.contains(ActionKeys.ADD_TO_PAGE)) {

			actions.add(ActionKeys.ADD_TO_PAGE);
		}

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

	protected String getCompositeModelName(Element compositeModelNameElement) {
		StringBundler sb = new StringBundler();

		List<Element> elements = new ArrayList<>(
			compositeModelNameElement.elements("model-name"));

		Collections.sort(
			elements,
			new Comparator<Element>() {

				@Override
				public int compare(Element element1, Element element2) {
					String textTrim1 = GetterUtil.getString(
						element1.getTextTrim());
					String textTrim2 = GetterUtil.getString(
						element2.getTextTrim());

					return textTrim1.compareTo(textTrim2);
				}

			});

		Iterator<Element> itr = elements.iterator();

		while (itr.hasNext()) {
			Element modelNameElement = itr.next();

			sb.append(modelNameElement.getTextTrim());

			if (itr.hasNext()) {
				sb.append(_COMPOSITE_MODEL_NAME_SEPARATOR);
			}
		}

		return sb.toString();
	}

	protected ModelResourceActionsBag getModelResourceActionsBag(
		String modelName) {

		ModelResourceActionsBag modelResourceActionsBag =
			_modelResourceActionsBags.get(modelName);

		if (modelResourceActionsBag != null) {
			return modelResourceActionsBag;
		}

		synchronized(_modelResourceActionsBags) {
			modelResourceActionsBag = _modelResourceActionsBags.get(modelName);

			if (modelResourceActionsBag != null) {
				return modelResourceActionsBag;
			}

			modelResourceActionsBag = new ModelResourceActionsBagImpl();

			_modelResourceActionsBags.put(modelName, modelResourceActionsBag);
		}

		return modelResourceActionsBag;
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
		Set<String> actions = new LinkedHashSet<>();

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

	protected PortletResourceActionsBag getPortletResourceActionsBag(
		String portletName) {

		PortletResourceActionsBag portletResourceActionsBag =
			_portletResourceActionsBags.get(portletName);

		if (portletResourceActionsBag != null) {
			return portletResourceActionsBag;
		}

		synchronized(_portletResourceActionsBags) {
			portletResourceActionsBag = _portletResourceActionsBags.get(
				portletName);

			if (portletResourceActionsBag != null) {
				return portletResourceActionsBag;
			}

			portletResourceActionsBag = new PortletResourceActionsBagImpl();

			_portletResourceActionsBags.put(
				portletName, portletResourceActionsBag);
		}

		return portletResourceActionsBag;
	}

	protected String getResourceBundlesString(
		HttpServletRequest request, String key) {

		Locale locale = RequestUtils.getUserLocale(request, null);

		return getResourceBundlesString(locale, key);
	}

	protected String getResourceBundlesString(Locale locale, String key) {
		if ((locale == null) || (key == null)) {
			return null;
		}

		String languageId = LocaleUtil.toLanguageId(locale);

		for (ResourceBundleLoader resourceBundleLoader :
				_resourceBundleLoaders) {

			ResourceBundle resourceBundle =
				resourceBundleLoader.loadResourceBundle(languageId);

			if (resourceBundle == null) {
				continue;
			}

			if (resourceBundle.containsKey(key)) {
				return ResourceBundleUtil.getString(resourceBundle, key);
			}
		}

		return null;
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
				else if (group.isUser() || group.isCompany() ||
						 group.isUserGroup()) {

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
		List<String> actions = new ArrayList<>();

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
		Element parentElement, Set<String> groupDefaultActions) {

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
	}

	protected void readGuestDefaultActions(
		Element parentElement, Set<String> guestDefaultActions) {

		Element guestDefaultsElement = getPermissionsChildElement(
			parentElement, "guest-defaults");

		guestDefaultActions.addAll(readActionKeys(guestDefaultsElement));
	}

	protected void readGuestUnsupportedActions(
		Element parentElement, Set<String> guestUnsupportedActions,
		Set<String> guestDefaultActions) {

		Element guestUnsupportedElement = getPermissionsChildElement(
			parentElement, "guest-unsupported");

		guestUnsupportedActions.addAll(readActionKeys(guestUnsupportedElement));

		checkGuestUnsupportedActions(
			guestUnsupportedActions, guestDefaultActions);
	}

	protected void readLayoutManagerActions(
		Element parentElement, Set<String> layoutManagerActions,
		Set<String> supportsActions) {

		Element layoutManagerElement = getPermissionsChildElement(
			parentElement, "layout-manager");

		if (layoutManagerElement != null) {
			layoutManagerActions.addAll(readActionKeys(layoutManagerElement));
		}
		else {
			layoutManagerActions.addAll(supportsActions);
		}
	}

	protected void readModelResource(
			String servletContextName, Element modelResourceElement)
		throws Exception {

		String name = modelResourceElement.elementTextTrim("model-name");

		if (Validator.isNull(name)) {
			name = getCompositeModelName(
				modelResourceElement.element("composite-model-name"));
		}

		if (GetterUtil.getBoolean(
				modelResourceElement.attributeValue("organization"))) {

			_organizationModelResources.add(name);
		}

		if (GetterUtil.getBoolean(
				modelResourceElement.attributeValue("portal"))) {

			_portalModelResources.add(name);
		}

		ModelResourceActionsBag modelResourceActionsBag =
			getModelResourceActionsBag(name);

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

			PortletResourceActionsBag portletResourceActionsBag =
				getPortletResourceActionsBag(portletName);

			Set<String> modelResources =
				portletResourceActionsBag.getResources();

			modelResources.add(name);

			// Reference for a model to parent portlets

			Set<String> portletResources =
				modelResourceActionsBag.getResources();

			portletResources.add(portletName);

			// Reference for a model to root portlets

			boolean root = GetterUtil.getBoolean(
				modelResourceElement.elementText("root"));

			if (root) {
				_rootModelResources.add(name);

				Map<String, String> portletRootModelResource =
					portletResourceActionsBag.getPortletRootModelResources();

				portletRootModelResource.put(portletName, name);
			}
		}

		double weight = GetterUtil.getDouble(
			modelResourceElement.elementTextTrim("weight"), 100);

		Map<String, Double> modelResourceWeights =
			modelResourceActionsBag.getResourceWeights();

		modelResourceWeights.put(name, weight);

		Set<String> modelResourceActions =
			modelResourceActionsBag.getResourceActions();

		readSupportsActions(modelResourceElement, modelResourceActions);

		checkModelActions(modelResourceActions);

		if (modelResourceActions.size() > 64) {
			throw new ResourceActionsException(
				"There are more than 64 actions for resource " + name);
		}

		Set<String> modelResourceGroupDefaultActions =
			modelResourceActionsBag.getResourceGroupDefaultActions();

		readGroupDefaultActions(
			modelResourceElement, modelResourceGroupDefaultActions);

		Set<String> modelResourceGuestDefaultActions =
			modelResourceActionsBag.getResourceGuestDefaultActions();

		readGuestDefaultActions(
			modelResourceElement, modelResourceGuestDefaultActions);

		Set<String> modelResourceGuestUnsupportedActions =
			modelResourceActionsBag.getResourceGuestUnsupportedActions();

		readGuestUnsupportedActions(
			modelResourceElement, modelResourceGuestUnsupportedActions,
			modelResourceGuestDefaultActions);

		Set<String> modelResourceOwnerDefaultActions =
			modelResourceActionsBag.getResourceOwnerDefaultActions();

		readOwnerDefaultActions(
			modelResourceElement, modelResourceOwnerDefaultActions);
	}

	protected void readOwnerDefaultActions(
		Element parentElement, Set<String> ownerDefaultActions) {

		Element ownerDefaultsElement = getPermissionsChildElement(
			parentElement, "owner-defaults");

		if (ownerDefaultsElement == null) {
			return;
		}

		ownerDefaultActions.addAll(readActionKeys(ownerDefaultsElement));
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

		PortletResourceActionsBag portletResourceActionsBag =
			getPortletResourceActionsBag(name);

		Set<String> portletResourceActions =
			portletResourceActionsBag.getResourceActions();

		readSupportsActions(portletResourceElement, portletResourceActions);

		portletResourceActions.addAll(getPortletMimeTypeActions(name));

		if (!name.equals(PortletKeys.PORTAL)) {
			checkPortletActions(name, portletResourceActions);
		}

		if (portletResourceActions.size() > 64) {
			throw new ResourceActionsException(
				"There are more than 64 actions for resource " + name);
		}

		Set<String> portletResourceGroupDefaultActions =
			portletResourceActionsBag.getResourceGroupDefaultActions();

		readGroupDefaultActions(
			portletResourceElement, portletResourceGroupDefaultActions);

		Set<String> portletResourceGuestDefaultActions =
			portletResourceActionsBag.getResourceGuestDefaultActions();

		readGuestDefaultActions(
			portletResourceElement, portletResourceGuestDefaultActions);

		Set<String> portletResourceGuestUnsupportedActions =
			portletResourceActionsBag.getResourceGuestUnsupportedActions();

		readGuestUnsupportedActions(
			portletResourceElement, portletResourceGuestUnsupportedActions,
			portletResourceGuestDefaultActions);

		Set<String> portletResourceLayoutManagerActions =
			portletResourceActionsBag.getResourceLayoutManagerActions();

		readLayoutManagerActions(
			portletResourceElement, portletResourceLayoutManagerActions,
			portletResourceActions);
	}

	protected Set<String> readSupportsActions(
		Element parentElement, Set<String> supportsActions) {

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

	private static final String _COMPOSITE_MODEL_NAME_SEPARATOR =
		StringPool.DASH;

	private static final String _MODEL_RESOURCE_NAME_PREFIX = "model.resource.";

	private static final Log _log = LogFactoryUtil.getLog(
		ResourceActionsImpl.class);

	private Map<String, ModelResourceActionsBag> _modelResourceActionsBags;
	private final Set<String> _organizationModelResources = new HashSet<>();
	private final Set<String> _portalModelResources = new HashSet<>();
	private Map<String, PortletResourceActionsBag> _portletResourceActionsBags;
	private final ServiceTrackerList<ResourceBundleLoader>
		_resourceBundleLoaders;
	private final Set<String> _rootModelResources = new HashSet<>();

}