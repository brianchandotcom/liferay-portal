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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
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
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.GroupServiceUtil;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
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
import java.util.Collection;
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
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 * @author Daeyoung Song
 * @author Raymond Augé
 */
public class ResourceActionsImpl implements ResourceActions {

	public void afterPropertiesSet() {
		try {
			Class<?> clazz = getClass();

			ClassLoader classLoader = clazz.getClassLoader();

			for (String config : PropsValues.RESOURCE_ACTIONS_CONFIGS) {
				read(null, classLoader, config);
			}
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #check(String)}
	 */
	@Deprecated
	@Override
	public void check(Portlet portlet) {
		String portletName = portlet.getPortletId();

		_check(portletName, _getPortletResourceActions(portletName, portlet));
	}

	@Override
	public void check(String portletName) {
		_check(portletName, getPortletResourceActions(portletName));
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public void checkAction(String name, String actionId)
		throws NoSuchResourceActionException {

		List<String> resourceActions = getResourceActions(name);

		if (!resourceActions.contains(actionId)) {
			throw new NoSuchResourceActionException(
				StringBundler.concat(name, StringPool.POUND, actionId));
		}
	}

	@Override
	public String getAction(
		HttpServletRequest httpServletRequest, String action) {

		String key = _ACTION_NAME_PREFIX + action;

		String value = LanguageUtil.get(httpServletRequest, key, null);

		if ((value == null) || value.equals(key)) {
			value = _getResourceBundlesString(httpServletRequest, key);
		}

		if (value == null) {
			value = key;
		}

		return value;
	}

	@Override
	public String getAction(Locale locale, String action) {
		String key = _ACTION_NAME_PREFIX + action;

		String value = LanguageUtil.get(locale, key, null);

		if ((value == null) || value.equals(key)) {
			value = _getResourceBundlesString(locale, key);
		}

		if (value == null) {
			value = key;
		}

		return value;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public String getActionNamePrefix() {
		return _ACTION_NAME_PREFIX;
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
		List<String> modelNames = new ArrayList<>();

		for (String name : _resourceActionsBags.keySet()) {
			if (name.indexOf(CharPool.PERIOD) != -1) {
				modelNames.add(name);
			}
		}

		return modelNames;
	}

	@Override
	public List<String> getModelPortletResources(String name) {
		return new ArrayList<>(_resourceReferences.get(name));
	}

	@Override
	public String getModelResource(
		HttpServletRequest httpServletRequest, String name) {

		String key = getModelResourceNamePrefix() + name;

		String value = LanguageUtil.get(httpServletRequest, key, null);

		if ((value == null) || value.equals(key)) {
			value = _getResourceBundlesString(httpServletRequest, key);
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

		if (value == null) {
			value = _getResourceBundlesString(locale, key);
		}

		if (value == null) {
			value = key;
		}

		return value;
	}

	@Override
	public List<String> getModelResourceActions(String name) {
		ResourceActionsBag modelResourceActionsBag = _getResourceActionsBag(
			name);

		return new ArrayList<>(modelResourceActionsBag.getSupportsActions());
	}

	@Override
	public List<String> getModelResourceGroupDefaultActions(String name) {
		ResourceActionsBag modelResourceActionsBag = _getResourceActionsBag(
			name);

		return new ArrayList<>(
			modelResourceActionsBag.getGroupDefaultActions());
	}

	@Override
	public List<String> getModelResourceGuestDefaultActions(String name) {
		ResourceActionsBag modelResourceActionsBag = _getResourceActionsBag(
			name);

		return new ArrayList<>(
			modelResourceActionsBag.getGuestDefaultActions());
	}

	@Override
	public List<String> getModelResourceGuestUnsupportedActions(String name) {
		ResourceActionsBag modelResourceActionsBag = _getResourceActionsBag(
			name);

		return new ArrayList<>(
			modelResourceActionsBag.getGuestUnsupportedActions());
	}

	@Override
	public String getModelResourceNamePrefix() {
		return _MODEL_RESOURCE_NAME_PREFIX;
	}

	@Override
	public List<String> getModelResourceOwnerDefaultActions(String name) {
		ResourceActionsBag modelResourceActionsBag = _getResourceActionsBag(
			name);

		return new ArrayList<>(
			modelResourceActionsBag.getOwnerDefaultActions());
	}

	@Override
	public Double getModelResourceWeight(String name) {
		return _modelResourceWeights.get(name);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public String[] getOrganizationModelResources() {
		return _organizationModelResources.toArray(new String[0]);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public String[] getPortalModelResources() {
		return _portalModelResources.toArray(new String[0]);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
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
		portletName = PortletIdCodec.decodePortletName(portletName);

		Set<String> resources = _resourceReferences.get(portletName);

		if (resources == null) {
			return new ArrayList<>();
		}

		return new ArrayList<>(resources);
	}

	@Override
	public List<String> getPortletNames() {
		List<String> portletNames = new ArrayList<>();

		for (String name : _resourceActionsBags.keySet()) {
			if (name.indexOf(CharPool.PERIOD) == -1) {
				portletNames.add(name);
			}
		}

		return portletNames;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public List<String> getPortletResourceActions(Portlet portlet) {
		Set<String> actions = new LinkedHashSet<>(
			getPortletResourceActions(portlet.getPortletId()));

		synchronized (this) {
			_checkPortletActions(portlet, actions);
		}

		return new ArrayList<>(actions);
	}

	@Override
	public List<String> getPortletResourceActions(String name) {
		name = PortletIdCodec.decodePortletName(name);

		Portlet portlet = portletLocalService.getPortletById(name);

		return _getPortletResourceActions(name, portlet);
	}

	@Override
	public List<String> getPortletResourceGroupDefaultActions(String name) {

		// This method should always be called only after
		// _getPortletResourceActions has been called at least once to populate
		// the default group actions. Check to make sure this is the case.
		// However, if it is not, that means the methods
		// getPortletResourceGuestDefaultActions and
		// getPortletResourceGuestDefaultActions may not work either.

		name = PortletIdCodec.decodePortletName(name);

		ResourceActionsBag portletResourceActionsBag = _getResourceActionsBag(
			name);

		return new ArrayList<>(
			portletResourceActionsBag.getGroupDefaultActions());
	}

	@Override
	public List<String> getPortletResourceGuestDefaultActions(String name) {
		name = PortletIdCodec.decodePortletName(name);

		ResourceActionsBag portletResourceActionsBag = _getResourceActionsBag(
			name);

		return new ArrayList<>(
			portletResourceActionsBag.getGuestDefaultActions());
	}

	@Override
	public List<String> getPortletResourceGuestUnsupportedActions(String name) {
		name = PortletIdCodec.decodePortletName(name);

		ResourceActionsBag portletResourceActionsBag = _getResourceActionsBag(
			name);

		Set<String> actions =
			portletResourceActionsBag.getGuestUnsupportedActions();

		actions.add(ActionKeys.CONFIGURATION);
		actions.add(ActionKeys.PERMISSIONS);

		return new ArrayList<>(actions);
	}

	@Override
	public List<String> getPortletResourceLayoutManagerActions(String name) {
		name = PortletIdCodec.decodePortletName(name);

		ResourceActionsBag portletResourceActionsBag = _getResourceActionsBag(
			name);

		Set<String> actions =
			portletResourceActionsBag.getLayoutManagerActions();

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
		return _portletRootModelResources.get(
			PortletIdCodec.decodePortletName(portletName));
	}

	@Override
	public List<String> getResourceActions(String name) {
		if (name.indexOf(CharPool.PERIOD) != -1) {
			return getModelResourceActions(name);
		}

		return getPortletResourceActions(name);
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

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public List<String> getResourceGroupDefaultActions(String name) {
		if (name.contains(StringPool.PERIOD)) {
			return getModelResourceGroupDefaultActions(name);
		}

		return getPortletResourceGroupDefaultActions(name);
	}

	@Override
	public List<String> getResourceGuestUnsupportedActions(
		String portletResource, String modelResource) {

		if (Validator.isNull(modelResource)) {
			return getPortletResourceGuestUnsupportedActions(portletResource);
		}
		else if (Validator.isNull(portletResource)) {
			return getModelResourceGuestUnsupportedActions(modelResource);
		}
		else if (_resourceActionsBags.containsKey(modelResource)) {
			return getModelResourceGuestUnsupportedActions(modelResource);
		}
		else if (_resourceActionsBags.containsKey(portletResource)) {
			return getPortletResourceGuestUnsupportedActions(portletResource);
		}

		return Collections.emptyList();
	}

	@Override
	public List<Role> getRoles(
		long companyId, Group group, String modelResource, int[] roleTypes) {

		if (roleTypes == null) {
			roleTypes = _getRoleTypes(group, modelResource);
		}

		return roleLocalService.getRoles(companyId, roleTypes);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public String[] getRootModelResources() {
		Collection<String> rootModelResources =
			_portletRootModelResources.values();

		return ArrayUtil.unique(rootModelResources.toArray(new String[0]));
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean hasModelResourceActions(String name) {
		ResourceActionsBag modelResourceActionsBag = _getResourceActionsBag(
			name);

		Set<String> modelActions = modelResourceActionsBag.getSupportsActions();

		if ((modelActions != null) && !modelActions.isEmpty()) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean isOrganizationModelResource(String modelResource) {
		if (_organizationModelResources.contains(modelResource)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isPortalModelResource(String modelResource) {
		if (_portalModelResources.contains(modelResource)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isRootModelResource(String modelResource) {
		Collection<String> rootModelResources =
			_portletRootModelResources.values();

		if (rootModelResources.contains(modelResource)) {
			return true;
		}

		return false;
	}

	@Override
	public void read(
			String servletContextName, ClassLoader classLoader, String source)
		throws ResourceActionsException {

		_read(servletContextName, classLoader, source, null);
	}

	@Override
	public void read(
			String servletContextName, ClassLoader classLoader,
			String... sources)
		throws ResourceActionsException {

		for (String source : sources) {
			read(servletContextName, classLoader, source);
		}
	}

	@Override
	public void readAndCheck(
			String servletContextName, ClassLoader classLoader,
			String... sources)
		throws ResourceActionsException {

		Set<String> portletNames = new HashSet<>();

		for (String source : sources) {
			_read(servletContextName, classLoader, source, portletNames);
		}

		for (String portletName : portletNames) {
			check(portletName);
		}
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public void removePortletResource(String portletName) {
		ResourceActionsBag portletResourceActionsBag =
			_resourceActionsBags.remove(portletName);

		if (portletResourceActionsBag != null) {
			Set<String> modelResources = _resourceReferences.get(portletName);

			for (String modelResource : modelResources) {
				Set<String> portletResources = _resourceReferences.get(
					modelResource);

				portletResources.remove(portletName);

				if (portletResources.isEmpty()) {
					_resourceActionsBags.remove(modelResource);
				}
			}
		}
	}

	@BeanReference(type = PortletLocalService.class)
	protected PortletLocalService portletLocalService;

	@BeanReference(type = ResourceActionLocalService.class)
	protected ResourceActionLocalService resourceActionLocalService;

	@BeanReference(type = RoleLocalService.class)
	protected RoleLocalService roleLocalService;

	private void _check(
		String portletName, List<String> portletResourceActions) {

		ResourceActionLocalServiceUtil.checkResourceActions(
			portletName, portletResourceActions);

		for (String modelName : getPortletModelResources(portletName)) {
			ResourceActionLocalServiceUtil.checkResourceActions(
				modelName, getModelResourceActions(modelName));
		}
	}

	private void _checkGuestUnsupportedActions(
		Set<String> guestUnsupportedActions, Set<String> guestDefaultActions) {

		// Guest default actions cannot reference guest unsupported actions

		Iterator<String> iterator = guestDefaultActions.iterator();

		while (iterator.hasNext()) {
			String actionId = iterator.next();

			if (guestUnsupportedActions.contains(actionId)) {
				iterator.remove();
			}
		}
	}

	private void _checkPortletActions(Portlet portlet, Set<String> actions) {
		_checkPortletLayoutManagerActions(actions);

		if ((portlet != null) &&
			(portlet.getControlPanelEntryCategory() != null)) {

			actions.add(ActionKeys.ACCESS_IN_CONTROL_PANEL);
		}
	}

	private void _checkPortletGroupDefaultActions(Set<String> actions) {
		if (actions.isEmpty()) {
			actions.add(ActionKeys.VIEW);
		}
	}

	private void _checkPortletGuestDefaultActions(Set<String> actions) {
		if (actions.isEmpty()) {
			actions.add(ActionKeys.VIEW);
		}
	}

	private void _checkPortletLayoutManagerActions(Set<String> actions) {
		if (!actions.contains(ActionKeys.ACCESS_IN_CONTROL_PANEL) &&
			!actions.contains(ActionKeys.ADD_TO_PAGE)) {

			actions.add(ActionKeys.ADD_TO_PAGE);
		}

		actions.add(ActionKeys.CONFIGURATION);
		actions.add(ActionKeys.PERMISSIONS);
		actions.add(ActionKeys.PREFERENCES);
		actions.add(ActionKeys.VIEW);
	}

	private String _getCompositeModelName(Element compositeModelNameElement) {
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

		Iterator<Element> iterator = elements.iterator();

		while (iterator.hasNext()) {
			Element modelNameElement = iterator.next();

			sb.append(modelNameElement.getTextTrim());

			if (iterator.hasNext()) {
				sb.append(_COMPOSITE_MODEL_NAME_SEPARATOR);
			}
		}

		return sb.toString();
	}

	private Element _getPermissionsChildElement(
		Element parentElement, String childElementName) {

		Element permissionsElement = parentElement.element("permissions");

		if (permissionsElement != null) {
			return permissionsElement.element(childElementName);
		}

		return parentElement.element(childElementName);
	}

	private Set<String> _getPortletMimeTypeActions(
		String name, Portlet portlet) {

		Set<String> actions = new LinkedHashSet<>();

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

	private List<String> _getPortletResourceActions(
		String name, Portlet portlet) {

		ResourceActionsBag portletResourceActionsBag = _getResourceActionsBag(
			name);

		Set<String> portletActions =
			portletResourceActionsBag.getSupportsActions();

		if (!portletActions.isEmpty()) {
			return new ArrayList<>(portletActions);
		}

		synchronized (this) {
			portletActions = _getPortletMimeTypeActions(name, portlet);

			if (!name.equals(PortletKeys.PORTAL)) {
				_checkPortletActions(portlet, portletActions);
			}

			_checkPortletGroupDefaultActions(
				portletResourceActionsBag.getGroupDefaultActions());

			_checkPortletGuestDefaultActions(
				portletResourceActionsBag.getGuestDefaultActions());

			_checkPortletLayoutManagerActions(
				portletResourceActionsBag.getLayoutManagerActions());
		}

		return new ArrayList<>(portletActions);
	}

	private ResourceActionsBag _getResourceActionsBag(String name) {
		ResourceActionsBag resourceActionsBag = _resourceActionsBags.get(name);

		if (resourceActionsBag != null) {
			return resourceActionsBag;
		}

		synchronized (_resourceActionsBags) {
			resourceActionsBag = _resourceActionsBags.get(name);

			if (resourceActionsBag != null) {
				return resourceActionsBag;
			}

			resourceActionsBag = new ResourceActionsBag();

			_resourceActionsBags.put(name, resourceActionsBag);
		}

		return resourceActionsBag;
	}

	private String _getResourceBundlesString(
		HttpServletRequest httpServletRequest, String key) {

		Locale locale = null;

		HttpSession session = httpServletRequest.getSession(false);

		if (session != null) {
			locale = (Locale)session.getAttribute(WebKeys.LOCALE);
		}

		if (locale == null) {
			locale = httpServletRequest.getLocale();
		}

		return _getResourceBundlesString(locale, key);
	}

	private String _getResourceBundlesString(Locale locale, String key) {
		if ((locale == null) || (key == null)) {
			return null;
		}

		for (ResourceBundleLoader resourceBundleLoader :
				ResourceBundleLoaderListHolder._resourceBundleLoaders) {

			ResourceBundle resourceBundle =
				resourceBundleLoader.loadResourceBundle(locale);

			if (resourceBundle == null) {
				continue;
			}

			if (resourceBundle.containsKey(key)) {
				return ResourceBundleUtil.getString(resourceBundle, key);
			}
		}

		return null;
	}

	private int[] _getRoleTypes(Group group, String modelResource) {
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
					catch (Exception exception) {
					}
				}

				if (group.isOrganization()) {
					types =
						RoleConstants.TYPES_ORGANIZATION_AND_REGULAR_AND_SITE;
				}
				else if (group.isCompany() || group.isUser() ||
						 group.isUserGroup()) {

					types = RoleConstants.TYPES_REGULAR;
				}
			}
		}

		return types;
	}

	private String _normalizePortletName(
		String servletContextName, String portletName) {

		if (servletContextName != null) {
			portletName = StringBundler.concat(
				portletName, PortletConstants.WAR_SEPARATOR,
				servletContextName);
		}

		return JS.getSafeName(portletName);
	}

	private void _read(
			String servletContextName, ClassLoader classLoader, String source,
			Set<String> portletNames)
		throws ResourceActionsException {

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

		try {
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
				String file = StringUtil.trim(
					resourceElement.attributeValue("file"));

				_read(servletContextName, classLoader, file, portletNames);

				String extFileName = StringUtil.replace(
					file, ".xml", "-ext.xml");

				_read(
					servletContextName, classLoader, extFileName, portletNames);
			}

			read(servletContextName, document, portletNames);

			if (source.endsWith(".xml") && !source.endsWith("-ext.xml")) {
				String extFileName = StringUtil.replace(
					source, ".xml", "-ext.xml");

				_read(
					servletContextName, classLoader, extFileName, portletNames);
			}
		}
		catch (DocumentException documentException) {
			throw new ResourceActionsException(documentException);
		}
	}

	@Override
	public void read(
			String servletContextName, Document document,
			Set<String> portletNames)
		throws ResourceActionsException {

		Element rootElement = document.getRootElement();

		if (PropsValues.RESOURCE_ACTIONS_READ_PORTLET_RESOURCES) {
			for (Element portletResourceElement :
					rootElement.elements("portlet-resource")) {

				String portletName = _normalizePortletName(
					servletContextName,
					portletResourceElement.elementTextTrim("portlet-name"));

				Portlet portlet = portletLocalService.getPortletById(
					portletName);

				Set<String> portletActions = _getPortletMimeTypeActions(
					portletName, portlet);

				if (!portletName.equals(PortletKeys.PORTAL)) {
					_checkPortletActions(portlet, portletActions);
				}

				_readResource(
					portletResourceElement, portletName, portletActions);

				if (portletNames != null) {
					portletNames.add(portletName);
				}
			}
		}

		for (Element modelResourceElement :
				rootElement.elements("model-resource")) {

			String modelName = modelResourceElement.elementTextTrim(
				"model-name");

			if (Validator.isNull(modelName)) {
				modelName = _getCompositeModelName(
					modelResourceElement.element("composite-model-name"));
			}

			if (GetterUtil.getBoolean(
					modelResourceElement.attributeValue("organization"))) {

				_organizationModelResources.add(modelName);
			}

			if (GetterUtil.getBoolean(
					modelResourceElement.attributeValue("portal"))) {

				_portalModelResources.add(modelName);
			}

			Element portletRefElement = modelResourceElement.element(
				"portlet-ref");

			for (Element portletNameElement :
					portletRefElement.elements("portlet-name")) {

				String portletName = _normalizePortletName(
					servletContextName, portletNameElement.getTextTrim());

				// Reference for a portlet to child models

				Set<String> modelResources =
					_resourceReferences.computeIfAbsent(
						portletName, key -> new HashSet<>());

				modelResources.add(modelName);

				// Reference for a model to parent portlets

				Set<String> portletResources =
					_resourceReferences.computeIfAbsent(
						modelName, key -> new HashSet<>());

				portletResources.add(portletName);

				// Reference for a model to root portlets

				boolean root = GetterUtil.getBoolean(
					modelResourceElement.elementText("root"));

				if (root) {
					_portletRootModelResources.put(portletName, modelName);
				}
			}

			double weight = GetterUtil.getDouble(
				modelResourceElement.elementTextTrim("weight"), 100);

			_modelResourceWeights.put(modelName, weight);

			_readResource(
				modelResourceElement, modelName,
				Collections.singleton(ActionKeys.PERMISSIONS));

			if (portletNames != null) {
				portletNames.addAll(_resourceReferences.get(modelName));
			}
		}
	}

	private void _readActionKeys(
		Collection<String> actions, Element parentElement) {

		for (Element actionKeyElement : parentElement.elements("action-key")) {
			String actionKey = actionKeyElement.getTextTrim();

			if (Validator.isNull(actionKey)) {
				continue;
			}

			actions.add(actionKey);
		}
	}

	private void _readResource(
			Element resourceElement, String name,
			Set<String> defaultResourceActions)
		throws ResourceActionsException {

		ResourceActionsBag resourceActionsBag = _getResourceActionsBag(name);

		Set<String> resourceActions = resourceActionsBag.getSupportsActions();

		Element supportsElement = _getPermissionsChildElement(
			resourceElement, "supports");

		_readActionKeys(resourceActions, supportsElement);

		resourceActions.addAll(defaultResourceActions);

		if (resourceActions.size() > 64) {
			throw new ResourceActionsException(
				"There are more than 64 actions for resource " + name);
		}

		Element groupDefaultsElement = _getPermissionsChildElement(
			resourceElement, "site-member-defaults");

		if (groupDefaultsElement == null) {
			groupDefaultsElement = _getPermissionsChildElement(
				resourceElement, "community-defaults");

			if (_log.isWarnEnabled() && (groupDefaultsElement != null)) {
				_log.warn(
					"The community-defaults element is deprecated. Use the " +
						"site-member-defaults element instead.");
			}
		}

		if (groupDefaultsElement != null) {
			Set<String> groupDefaultActions =
				resourceActionsBag.getGroupDefaultActions();

			groupDefaultActions.clear();

			_readActionKeys(groupDefaultActions, groupDefaultsElement);
		}

		Set<String> guestDefaultActions =
			resourceActionsBag.getGuestDefaultActions();

		Element guestDefaultsElement = _getPermissionsChildElement(
			resourceElement, "guest-defaults");

		if (guestDefaultsElement != null) {
			guestDefaultActions.clear();

			_readActionKeys(guestDefaultActions, guestDefaultsElement);
		}

		Element guestUnsupportedElement = _getPermissionsChildElement(
			resourceElement, "guest-unsupported");

		if (guestUnsupportedElement != null) {
			Set<String> guestUnsupportedActions =
				resourceActionsBag.getGuestUnsupportedActions();

			guestUnsupportedActions.clear();

			_readActionKeys(guestUnsupportedActions, guestUnsupportedElement);

			_checkGuestUnsupportedActions(
				guestUnsupportedActions, guestDefaultActions);
		}

		Element ownerDefaultsElement = _getPermissionsChildElement(
			resourceElement, "owner-defaults");

		if (ownerDefaultsElement != null) {
			_readActionKeys(
				resourceActionsBag.getOwnerDefaultActions(),
				ownerDefaultsElement);
		}

		Set<String> layoutManagerActions =
			resourceActionsBag.getLayoutManagerActions();

		Element layoutManagerElement = _getPermissionsChildElement(
			resourceElement, "layout-manager");

		if (layoutManagerElement == null) {
			layoutManagerActions.addAll(resourceActions);

			return;
		}

		layoutManagerActions.clear();

		_readActionKeys(layoutManagerActions, layoutManagerElement);
	}

	private static final String _ACTION_NAME_PREFIX = "action.";

	private static final String _COMPOSITE_MODEL_NAME_SEPARATOR =
		StringPool.DASH;

	private static final String _MODEL_RESOURCE_NAME_PREFIX = "model.resource.";

	private static final Log _log = LogFactoryUtil.getLog(
		ResourceActionsImpl.class);

	private final Map<String, Double> _modelResourceWeights = new HashMap<>();
	private final Set<String> _organizationModelResources = new HashSet<>();
	private final Set<String> _portalModelResources = new HashSet<>();
	private final Map<String, String> _portletRootModelResources =
		new HashMap<>();
	private final Map<String, ResourceActionsBag> _resourceActionsBags =
		new HashMap<>();
	private final Map<String, Set<String>> _resourceReferences =
		new HashMap<>();

	private static class ResourceActionsBag {

		public Set<String> getGroupDefaultActions() {
			return _groupDefaultActions;
		}

		public Set<String> getGuestDefaultActions() {
			return _guestDefaultActions;
		}

		public Set<String> getGuestUnsupportedActions() {
			return _guestUnsupportedActions;
		}

		public Set<String> getLayoutManagerActions() {
			return _layoutManagerActions;
		}

		public Set<String> getOwnerDefaultActions() {
			return _ownerDefaultActions;
		}

		public Set<String> getSupportsActions() {
			return _supportsActions;
		}

		private final Set<String> _groupDefaultActions = new HashSet<>();
		private final Set<String> _guestDefaultActions = new HashSet<>();
		private final Set<String> _guestUnsupportedActions = new HashSet<>();
		private final Set<String> _layoutManagerActions = new HashSet<>();
		private final Set<String> _ownerDefaultActions = new HashSet<>();
		private final Set<String> _supportsActions = new HashSet<>();

	}

	private static class ResourceBundleLoaderListHolder {

		private static final ServiceTrackerList<ResourceBundleLoader>
			_resourceBundleLoaders = ServiceTrackerCollections.openList(
				ResourceBundleLoader.class);

	}

}