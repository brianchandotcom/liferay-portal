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

package com.liferay.portal.kernel.security.permission;

import com.liferay.portal.kernel.exception.NoSuchResourceActionException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.io.InputStream;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Daeyoung Song
 */
public class ResourceActionsUtil {

	public static void checkAction(String name, String actionId)
		throws NoSuchResourceActionException {

		getResourceActions().checkAction(name, actionId);
	}

	public static String getAction(HttpServletRequest request, String action) {
		return getResourceActions().getAction(request, action);
	}

	public static String getAction(Locale locale, String action) {
		return getResourceActions().getAction(locale, action);
	}

	public static String getActionNamePrefix() {
		return getResourceActions().getActionNamePrefix();
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	public static List<String> getActionsNames(
		HttpServletRequest request, List<String> actions) {

		return getResourceActions().getActionsNames(request, actions);
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	public static List<String> getActionsNames(
		HttpServletRequest request, String name, long actionIds) {

		return getResourceActions().getActionsNames(request, name, actionIds);
	}

	public static String getCompositeModelName(String... classNames) {
		return getResourceActions().getCompositeModelName(classNames);
	}

	public static String getCompositeModelNameSeparator() {
		return getResourceActions().getCompositeModelNameSeparator();
	}

	public static List<String> getModelNames() {
		return getResourceActions().getModelNames();
	}

	public static List<String> getModelPortletResources(String name) {
		return getResourceActions().getModelPortletResources(name);
	}

	public static String getModelResource(
		HttpServletRequest request, String name) {

		return getResourceActions().getModelResource(request, name);
	}

	public static String getModelResource(Locale locale, String name) {
		return getResourceActions().getModelResource(locale, name);
	}

	public static List<String> getModelResourceActions(String name) {
		return getResourceActions().getModelResourceActions(name);
	}

	public static List<String> getModelResourceGroupDefaultActions(
		String name) {

		return getResourceActions().getModelResourceGroupDefaultActions(name);
	}

	public static List<String> getModelResourceGuestDefaultActions(
		String name) {

		return getResourceActions().getModelResourceGuestDefaultActions(name);
	}

	public static List<String> getModelResourceGuestUnsupportedActions(
		String name) {

		return getResourceActions().getModelResourceGuestUnsupportedActions(
			name);
	}

	public static String getModelResourceNamePrefix() {
		return getResourceActions().getModelResourceNamePrefix();
	}

	public static List<String> getModelResourceOwnerDefaultActions(
		String name) {

		return getResourceActions().getModelResourceOwnerDefaultActions(name);
	}

	public static Double getModelResourceWeight(String name) {
		return getResourceActions().getModelResourceWeight(name);
	}

	public static String[] getOrganizationModelResources() {
		return getResourceActions().getOrganizationModelResources();
	}

	public static String[] getPortalModelResources() {
		return getResourceActions().getPortalModelResources();
	}

	public static String getPortletBaseResource(String portletName) {
		return getResourceActions().getPortletBaseResource(portletName);
	}

	public static List<String> getPortletModelResources(String portletName) {
		return getResourceActions().getPortletModelResources(portletName);
	}

	public static List<String> getPortletNames() {
		return getResourceActions().getPortletNames();
	}

	public static List<String> getPortletResourceActions(Portlet portlet) {
		return getResourceActions().getPortletResourceActions(portlet);
	}

	public static List<String> getPortletResourceActions(String name) {
		return getResourceActions().getPortletResourceActions(name);
	}

	public static List<String> getPortletResourceGroupDefaultActions(
		String name) {

		return getResourceActions().getPortletResourceGroupDefaultActions(name);
	}

	public static List<String> getPortletResourceGuestDefaultActions(
		String name) {

		return getResourceActions().getPortletResourceGuestDefaultActions(name);
	}

	public static List<String> getPortletResourceGuestUnsupportedActions(
		String name) {

		return getResourceActions().getPortletResourceGuestUnsupportedActions(
			name);
	}

	public static List<String> getPortletResourceLayoutManagerActions(
		String name) {

		return getResourceActions().getPortletResourceLayoutManagerActions(
			name);
	}

	public static String getPortletRootModelResource(String portletName) {
		return getResourceActions().getPortletRootModelResource(portletName);
	}

	public static ResourceActions getResourceActions() {
		PortalRuntimePermission.checkGetBeanProperty(ResourceActionsUtil.class);

		return _resourceActions;
	}

	public static List<String> getResourceActions(String name) {
		return getResourceActions().getResourceActions(name);
	}

	public static List<String> getResourceActions(
		String portletResource, String modelResource) {

		return getResourceActions().getResourceActions(
			portletResource, modelResource);
	}

	public static List<String> getResourceGroupDefaultActions(String name) {
		return getResourceActions().getResourceGroupDefaultActions(name);
	}

	public static List<String> getResourceGuestUnsupportedActions(
		String portletResource, String modelResource) {

		return getResourceActions().getResourceGuestUnsupportedActions(
			portletResource, modelResource);
	}

	public static List<Role> getRoles(
		long companyId, Group group, String modelResource, int[] roleTypes) {

		return getResourceActions().getRoles(
			companyId, group, modelResource, roleTypes);
	}

	public static String[] getRootModelResources() {
		return getResourceActions().getRootModelResources();
	}

	public static boolean hasModelResourceActions(String name) {
		return getResourceActions().hasModelResourceActions(name);
	}

	public static boolean isOrganizationModelResource(String modelResource) {
		return getResourceActions().isOrganizationModelResource(modelResource);
	}

	public static boolean isPortalModelResource(String modelResource) {
		return getResourceActions().isPortalModelResource(modelResource);
	}

	public static boolean isRootModelResource(String modelResource) {
		return getResourceActions().isRootModelResource(modelResource);
	}

	public static void read(
			String servletContextName, ClassLoader classLoader, String source)
		throws Exception {

		getResourceActions().read(servletContextName, classLoader, source);
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	public static void read(String servletContextName, InputStream inputStream)
		throws Exception {

		getResourceActions().read(servletContextName, inputStream);
	}

	public void setResourceActions(ResourceActions resourceActions) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_resourceActions = resourceActions;
	}

	private static ResourceActions _resourceActions;

}