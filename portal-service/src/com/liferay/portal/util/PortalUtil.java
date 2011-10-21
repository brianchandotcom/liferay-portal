/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.social.model.SocialEquityActionMapping;

import java.io.IOException;
import java.io.Serializable;

import java.sql.SQLException;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PreferencesValidator;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ValidatorException;
import javax.portlet.WindowState;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 * @author Juan Fernández
 */
public class PortalUtil {

	/**
	 * Adds the description for a page. This appends to the existing page
	 * description.
	 */
	public static void addPageDescription(
		String description, HttpServletRequest request) {

		getPortal().addPageDescription(description, request);
	}

	/**
	 * Adds the keywords for a page. This appends to the existing page keywords.
	 */
	public static void addPageKeywords(
		String keywords, HttpServletRequest request) {

		getPortal().addPageKeywords(keywords, request);
	}

	/**
	 * Adds the subtitle for a page. This appends to the existing page subtitle.
	 */
	public static void addPageSubtitle(
		String subtitle, HttpServletRequest request) {

		getPortal().addPageSubtitle(subtitle, request);
	}

	/**
	 * Adds the whole title for a page. This appends to the existing page whole
	 * title.
	 */
	public static void addPageTitle(String title, HttpServletRequest request) {
		getPortal().addPageTitle(title, request);
	}

	public static void addPortalPortEventListener(
		PortalPortEventListener portalPortEventListener) {

		getPortal().addPortalPortEventListener(portalPortEventListener);
	}

	public static void addPortletBreadcrumbEntry(
		HttpServletRequest request, String title, String url) {

		getPortal().addPortletBreadcrumbEntry(request, title, url);
	}

	public static void addPortletBreadcrumbEntry(
		HttpServletRequest request, String title, String url,
		Map<String, Object> data) {

		getPortal().addPortletBreadcrumbEntry(request, title, url, data);
	}

	public static void addPortletDefaultResource(
			HttpServletRequest request, Portlet portlet)
		throws PortalException, SystemException {

		getPortal().addPortletDefaultResource(request, portlet);
	}

	/**
	 * Adds preserved parameters such as doAsGroupId, doAsUserId,
	 * doAsUserLanguageId, and referrerPlid that should always be preserved as
	 * the user navigates through the portal. If doAsUser is <code>false</code>,
	 * then doAsUserId and doAsUserLanguageId will never be added.
	 */
	public static String addPreservedParameters(
		ThemeDisplay themeDisplay, Layout layout, String url,
		boolean doAsUser) {

		return getPortal().addPreservedParameters(
			themeDisplay, layout, url, doAsUser);
	}

	/**
	 * Adds preserved parameters such as doAsGroupId, doAsUserId,
	 * doAsUserLanguageId, and referrerPlid that should always be preserved as
	 * the user navigates through the portal.
	 */
	public static String addPreservedParameters(
		ThemeDisplay themeDisplay, String url) {

		return getPortal().addPreservedParameters(themeDisplay, url);
	}

	public static void clearRequestParameters(RenderRequest renderRequest) {
		getPortal().clearRequestParameters(renderRequest);
	}

	public static void copyRequestParameters(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		getPortal().copyRequestParameters(actionRequest, actionResponse);
	}

	public static String escapeRedirect(String url) {
		return getPortal().escapeRedirect(url);
	}

	public static String generateRandomKey(
		HttpServletRequest request, String input) {

		return getPortal().generateRandomKey(request, input);
	}

	public static String getActualURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException, SystemException {

		return getPortal().getActualURL(
			groupId, privateLayout, mainPath, friendlyURL, params,
			requestContext);
	}

	public static Set<String> getAuthTokenIgnoreActions() {
		return getPortal().getAuthTokenIgnoreActions();
	}

	public static Set<String> getAuthTokenIgnorePortlets() {
		return getPortal().getAuthTokenIgnorePortlets();
	}

	public static BaseModel<?> getBaseModel(Resource resource)
		throws PortalException, SystemException {

		return getPortal().getBaseModel(resource);
	}

	public static BaseModel<?> getBaseModel(
			ResourcePermission resourcePermission)
		throws PortalException, SystemException {

		return getPortal().getBaseModel(resourcePermission);
	}

	public static BaseModel<?> getBaseModel(String modelName, String primKey)
		throws PortalException, SystemException {

		return getPortal().getBaseModel(modelName, primKey);
	}

	public static long getBasicAuthUserId(HttpServletRequest request)
		throws PortalException, SystemException {

		return getPortal().getBasicAuthUserId(request);
	}

	public static long getDigestAuthUserId(
			HttpServletRequest request)
		throws PortalException, SystemException {

		return getPortal().getDigestAuthUserId(request);
	}

	public static long getBasicAuthUserId(
			HttpServletRequest request, long companyId)
		throws PortalException, SystemException {

		return getPortal().getBasicAuthUserId(request, companyId);
	}

	public static String getCanonicalAlternateURL(
			HttpServletRequest request, String url, Locale locale)
		throws PortalException, SystemException{

		return getPortal().getCanonicalAlternateURL(request, url, locale);
	}

	public static String getCanonicalURL(HttpServletRequest request)
		throws PortalException, SystemException{

		return getPortal().getCanonicalURL(request);
	}

	/**
	 * @deprecated {@link #getCDNHost(boolean)}
	 */
	public static String getCDNHost() {
		return getPortal().getCDNHost();
	}

	public static String getCDNHost(boolean secure) {
		return getPortal().getCDNHost(secure);
	}

	public static String getCDNHostHttp(long companyId) {
		return getPortal().getCDNHostHttp(companyId);
	}

	public static String getCDNHostHttps(long companyId) {
		return getPortal().getCDNHostHttps(companyId);
	}

	public static String getClassName(long classNameId) {
		return getPortal().getClassName(classNameId);
	}

	public static long getClassNameId(Class<?> clazz) {
		return getPortal().getClassNameId(clazz);
	}

	public static long getClassNameId(String value) {
		return getPortal().getClassNameId(value);
	}

	public static String getClassNamePortletId(String className) {
		return getPortal().getClassNamePortletId(className);
	}

	public static Company getCompany(HttpServletRequest request)
		throws PortalException, SystemException {

		return getPortal().getCompany(request);
	}

	public static Company getCompany(PortletRequest portletRequest)
		throws PortalException, SystemException {

		return getPortal().getCompany(portletRequest);
	}

	public static long getCompanyId(HttpServletRequest request) {
		return getPortal().getCompanyId(request);
	}

	public static long getCompanyId(PortletRequest portletRequest) {
		return getPortal().getCompanyId(portletRequest);
	}

	public static long[] getCompanyIds() {
		return getPortal().getCompanyIds();
	}

	public static String getComputerAddress() {
		return getPortal().getComputerAddress();
	}

	public static String getComputerName() {
		return getPortal().getComputerName();
	}

	public static String getControlPanelCategory(
			String portletId, ThemeDisplay themeDisplay)
		throws SystemException {

		return getPortal().getControlPanelCategory(portletId, themeDisplay);
	}

	public static String getControlPanelFullURL(
			long scopeGroupId, String ppid, Map<String, String[]> params)
		throws PortalException, SystemException {

		return getPortal().getControlPanelFullURL(scopeGroupId, ppid, params);
	}

	public static Set<Portlet> getControlPanelPortlets(
			long companyId, String category)
		throws SystemException {

		return getPortal().getControlPanelPortlets(companyId, category);
	}

	public static List<Portlet> getControlPanelPortlets(
			String category, ThemeDisplay themeDisplay)
		throws SystemException {

		return getPortal().getControlPanelPortlets(
			category, themeDisplay);
	}

	public static String getCreateAccountURL(
			HttpServletRequest request, ThemeDisplay themeDisplay)
		throws Exception {

		return getPortal().getCreateAccountURL(request, themeDisplay);
	}

	public static String getCurrentCompleteURL(HttpServletRequest request) {
		return getPortal().getCurrentCompleteURL(request);
	}

	public static String getCurrentURL(HttpServletRequest request) {
		return getPortal().getCurrentURL(request);
	}

	public static String getCurrentURL(PortletRequest portletRequest) {
		return getPortal().getCurrentURL(portletRequest);
	}

	public static String getCustomSQLFunctionIsNotNull() {
		return getPortal().getCustomSQLFunctionIsNotNull();
	}

	public static String getCustomSQLFunctionIsNull() {
		return getPortal().getCustomSQLFunctionIsNull();
	}

	public static Date getDate(int month, int day, int year) {
		return getPortal().getDate(month, day, year);
	}

	public static Date getDate(
			int month, int day, int year, int hour, int min, PortalException pe)
		throws PortalException {

		return getPortal().getDate(month, day, year, hour, min, pe);
	}

	public static Date getDate(
			int month, int day, int year, int hour, int min, TimeZone timeZone,
			PortalException pe)
		throws PortalException {

		return getPortal().getDate(month, day, year, hour, min, timeZone, pe);
	}

	public static Date getDate(int month, int day, int year, PortalException pe)
		throws PortalException {

		return getPortal().getDate(month, day, year, pe);
	}

	public static Date getDate(
			int month, int day, int year, TimeZone timeZone, PortalException pe)
		throws PortalException {

		return getPortal().getDate(month, day, year, timeZone, pe);
	}

	/**
	 * @deprecated {@link DBFactoryUtil#getDB()}
	 */
	public static DB getDB() {
		return DBFactoryUtil.getDB();
	}

	public static long getDefaultCompanyId() {
		return getPortal().getDefaultCompanyId();
	}

	public static String getEmailFromAddress(
			PortletPreferences preferences, long companyId, String defaultValue)
		throws SystemException {

		return getPortal().getEmailFromAddress(
			preferences, companyId, defaultValue);
	}

	public static String getEmailFromName(
			PortletPreferences preferences, long companyId, String defaultValue)
		throws SystemException {

		return getPortal().getEmailFromName(
			preferences, companyId, defaultValue);
	}

	public static Map<String, Serializable> getExpandoBridgeAttributes(
			ExpandoBridge expandoBridge, PortletRequest portletRequest)
		throws PortalException, SystemException {

		return getPortal().getExpandoBridgeAttributes(
			expandoBridge, portletRequest);
	}

	public static Serializable getExpandoValue(
			PortletRequest portletRequest, String name, int type,
			String displayType)
		throws PortalException, SystemException{

		return getPortal().getExpandoValue(
			portletRequest, name, type, displayType);
	}

	public static String getFacebookURL(
			Portlet portlet, String facebookCanvasPageURL,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getPortal().getFacebookURL(
			portlet, facebookCanvasPageURL, themeDisplay);
	}

	public static String getFirstPageLayoutTypes(PageContext pageContext) {
		return getPortal().getFirstPageLayoutTypes(pageContext);
	}

	public static String getGlobalLibDir() {
		return getPortal().getGlobalLibDir();
	}

	public static String getGoogleGadgetURL(
			Portlet portlet, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getPortal().getGoogleGadgetURL(portlet, themeDisplay);
	}

	public static String getGroupFriendlyURL(
			Group group, boolean privateLayoutSet, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getPortal().getGroupFriendlyURL(
			group, privateLayoutSet, themeDisplay);
	}

	public static String[] getGroupPermissions(HttpServletRequest request) {
		return getPortal().getGroupPermissions(request);
	}

	public static String[] getGroupPermissions(
		PortletRequest portletRequest) {

		return getPortal().getGroupPermissions(portletRequest);
	}

	public static String[] getGuestPermissions(HttpServletRequest request) {
		return getPortal().getGuestPermissions(request);
	}

	public static String[] getGuestPermissions(PortletRequest portletRequest) {
		return getPortal().getGuestPermissions(portletRequest);
	}

	public static String getHomeURL(HttpServletRequest request)
		throws PortalException, SystemException {

		return getPortal().getHomeURL(request);
	}

	public static String getHost(HttpServletRequest request) {
		return getPortal().getHost(request);
	}

	public static String getHost(PortletRequest portletRequest) {
		return getPortal().getHost(portletRequest);
	}

	public static HttpServletRequest getHttpServletRequest(
		PortletRequest portletRequest) {

		return getPortal().getHttpServletRequest(portletRequest);
	}

	public static HttpServletResponse getHttpServletResponse(
		PortletResponse portletResponse) {

		return getPortal().getHttpServletResponse(portletResponse);
	}

	public static String getJournalArticleActualURL(
			long groupId, String mainPath, String friendlyURL,
			Map<String, String[]> params, Map<String, Object> requestContext)
		throws PortalException, SystemException {

		return getPortal().getJournalArticleActualURL(
			groupId, mainPath, friendlyURL, params, requestContext);
	}

	public static String getJsSafePortletId(String portletId) {
		return getPortal().getJsSafePortletId(portletId);
	}

	public static String getLayoutActualURL(Layout layout) {
		return getPortal().getLayoutActualURL(layout);
	}

	public static String getLayoutActualURL(Layout layout, String mainPath) {
		return getPortal().getLayoutActualURL(layout, mainPath);
	}

	public static String getLayoutActualURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL)
		throws PortalException, SystemException {

		return getPortal().getLayoutActualURL(
			groupId, privateLayout, mainPath, friendlyURL);
	}

	public static String getLayoutActualURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException, SystemException {

		return getPortal().getLayoutActualURL(
			groupId, privateLayout, mainPath, friendlyURL, params,
			requestContext);
	}

	public static String getLayoutEditPage(Layout layout) {
		return getPortal().getLayoutEditPage(layout);
	}

	public static String getLayoutEditPage(String type) {
		return getPortal().getLayoutEditPage(type);
	}

	public static String getLayoutFriendlyURL(
			Layout layout, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getPortal().getLayoutFriendlyURL(layout, themeDisplay);
	}

	public static String getLayoutFriendlyURL(
			Layout layout, ThemeDisplay themeDisplay, Locale locale)
		throws PortalException, SystemException {

		return getPortal().getLayoutFriendlyURL(layout, themeDisplay, locale);
	}

	public static String getLayoutFullURL(
			Layout layout, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getPortal().getLayoutFullURL(layout, themeDisplay);
	}

	public static String getLayoutFullURL(
			Layout layout, ThemeDisplay themeDisplay, boolean doAsUser)
		throws PortalException, SystemException {

		return getPortal().getLayoutFullURL(layout, themeDisplay, doAsUser);
	}

	public static String getLayoutFullURL(long groupId, String portletId)
		throws PortalException, SystemException {

		return getPortal().getLayoutFullURL(groupId, portletId);
	}

	public static String getLayoutFullURL(
			long groupId, String portletId, boolean secure)
		throws PortalException, SystemException {

		return getPortal().getLayoutFullURL(groupId, portletId, secure);
	}

	public static String getLayoutFullURL(ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getPortal().getLayoutFullURL(themeDisplay);
	}

	public static String getLayoutSetFriendlyURL(
			LayoutSet layoutSet, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getPortal().getLayoutSetFriendlyURL(layoutSet, themeDisplay);
	}

	public static String getLayoutTarget(Layout layout) {
		return getPortal().getLayoutTarget(layout);
	}

	public static String getLayoutURL(
			Layout layout, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getPortal().getLayoutURL(layout, themeDisplay);
	}

	public static String getLayoutURL(
			Layout layout, ThemeDisplay themeDisplay, boolean doAsUser)
		throws PortalException, SystemException {

		return getPortal().getLayoutURL(layout, themeDisplay, doAsUser);
	}

	public static String getLayoutURL(ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getPortal().getLayoutURL(themeDisplay);
	}

	public static String getLayoutViewPage(Layout layout) {
		return getPortal().getLayoutViewPage(layout);
	}

	public static String getLayoutViewPage(String type) {
		return getPortal().getLayoutViewPage(type);
	}

	public static LiferayPortletRequest getLiferayPortletRequest(
		PortletRequest portletRequest) {

		return getPortal().getLiferayPortletRequest(portletRequest);
	}

	public static LiferayPortletResponse getLiferayPortletResponse(
		PortletResponse portletResponse) {

		return getPortal().getLiferayPortletResponse(portletResponse);
	}

	public static Locale getLocale(HttpServletRequest request) {
		return getPortal().getLocale(request);
	}

	public static Locale getLocale(RenderRequest renderRequest) {
		return getPortal().getLocale(renderRequest);
	}

	public static String getMailId(
		String mx, String popPortletPrefix, Object... ids) {

		return getPortal().getMailId(mx, popPortletPrefix, ids);
	}

	/**
	 * @deprecated {@link #getBaseModel(Resource)}
	 */
	public static BaseModel<?> getModel(Resource resource)
		throws PortalException, SystemException {

		return getPortal().getBaseModel(resource);
	}

	/**
	 * @deprecated {@link #getBaseModel(ResourcePermission)}
	 */
	public static BaseModel<?> getModel(ResourcePermission resourcePermission)
		throws PortalException, SystemException {

		return getPortal().getBaseModel(resourcePermission);
	}

	/**
	 * @deprecated {@link #getBaseModel(String, String)}
	 */
	public static BaseModel<?> getModel(String modelName, String primKey)
		throws PortalException, SystemException {

		return getPortal().getBaseModel(modelName, primKey);
	}

	public static String getNetvibesURL(
			Portlet portlet, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getPortal().getNetvibesURL(portlet, themeDisplay);
	}

	public static String getNewPortletTitle(
		String portletTitle, String oldScopeName, String newScopeName) {

		return getPortal().getNewPortletTitle(
			portletTitle, oldScopeName, newScopeName);
	}

	public static HttpServletRequest getOriginalServletRequest(
		HttpServletRequest request) {

		return getPortal().getOriginalServletRequest(request);
	}

	public static String getOuterPortletId(HttpServletRequest request) {
		return getPortal().getOuterPortletId(request);
	}

	public static long getParentGroupId(long scopeGroupId)
		throws PortalException, SystemException {

		return getPortal().getParentGroupId(scopeGroupId);
	}

	public static String getPathContext() {
		return getPortal().getPathContext();
	}

	public static String getPathFriendlyURLPrivateGroup() {
		return getPortal().getPathFriendlyURLPrivateGroup();
	}

	public static String getPathFriendlyURLPrivateUser() {
		return getPortal().getPathFriendlyURLPrivateUser();
	}

	public static String getPathFriendlyURLPublic() {
		return getPortal().getPathFriendlyURLPublic();
	}

	public static String getPathImage() {
		return getPortal().getPathImage();
	}

	public static String getPathMain() {
		return getPortal().getPathMain();
	}

	public static String getPathProxy() {
		return getPortal().getPathProxy();
	}

	public static long getPlidFromFriendlyURL(
		long companyId, String friendlyURL) {

		return getPortal().getPlidFromFriendlyURL(companyId, friendlyURL);
	}

	public static long getPlidFromPortletId(
			long groupId, boolean privateLayout, String portletId)
		throws PortalException, SystemException {

		return getPortal().getPlidFromPortletId(
			groupId, privateLayout, portletId);
	}

	public static long getPlidFromPortletId(long groupId, String portletId)
		throws PortalException, SystemException {

		return getPortal().getPlidFromPortletId(groupId, portletId);
	}

	public static Portal getPortal() {
		return _portal;
	}

	public static String getPortalLibDir() {
		return getPortal().getPortalLibDir();
	}

	/**
	 * @deprecated {@link #getPortalPort(boolean)}
	 */
	public static int getPortalPort() {
		return getPortal().getPortalPort();
	}

	public static int getPortalPort(boolean secure) {
		return getPortal().getPortalPort(secure);
	}

	public static Properties getPortalProperties() {
		return getPortal().getPortalProperties();
	}

	public static String getPortalURL(HttpServletRequest request) {
		return getPortal().getPortalURL(request);
	}

	public static String getPortalURL(
		HttpServletRequest request, boolean secure) {

		return getPortal().getPortalURL(request, secure);
	}

	public static String getPortalURL(PortletRequest portletRequest) {
		return getPortal().getPortalURL(portletRequest);
	}

	public static String getPortalURL(
		PortletRequest portletRequest, boolean secure) {

		return getPortal().getPortalURL(portletRequest, secure);
	}

	public static String getPortalURL(
		String serverName, int serverPort, boolean secure) {

		return getPortal().getPortalURL(serverName, serverPort, secure);
	}

	public static String getPortalURL(Layout layout, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getPortal().getPortalURL(layout, themeDisplay);
	}

	public static String getPortalURL(ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getPortal().getPortalURL(themeDisplay);
	}

	public static String getPortalWebDir() {
		return getPortal().getPortalWebDir();
	}

	public static Set<String> getPortletAddDefaultResourceCheckWhitelist() {
		return getPortal().getPortletAddDefaultResourceCheckWhitelist();
	}

	public static Set<String>
		getPortletAddDefaultResourceCheckWhitelistActions() {

		return getPortal().getPortletAddDefaultResourceCheckWhitelistActions();
	}

	/**
	 * @deprecated {@link #getPortletBreadcrumbs(HttpServletRequest)}
	 */
	public static List<BreadcrumbEntry> getPortletBreadcrumbList(
		HttpServletRequest request) {

		return getPortal().getPortletBreadcrumbList(request);
	}

	public static List<BreadcrumbEntry> getPortletBreadcrumbs(
		HttpServletRequest request) {

		return getPortal().getPortletBreadcrumbs(request);
	}

	public static String getPortletDescription(
		Portlet portlet, ServletContext servletContext, Locale locale) {

		return getPortal().getPortletDescription(
			portlet, servletContext, locale);
	}

	public static String getPortletDescription(Portlet portlet, User user) {
		return getPortal().getPortletDescription(portlet, user);
	}

	public static String getPortletDescription(
		String portletId, Locale locale) {

		return getPortal().getPortletDescription(portletId, locale);
	}

	public static String getPortletDescription(
		String portletId, String languageId) {

		return getPortal().getPortletDescription(portletId, languageId);
	}

	public static String getPortletDescription(String portletId, User user) {
		return getPortal().getPortletDescription(portletId, user);
	}

	public static String getPortletId(HttpServletRequest request) {
		return getPortal().getPortletId(request);
	}

	public static String getPortletId(PortletRequest portletRequest) {
		return getPortal().getPortletId(portletRequest);
	}

	public static String getPortletNamespace(String portletId) {
		return getPortal().getPortletNamespace(portletId);
	}

	public static String getPortletTitle(Portlet portlet, Locale locale) {
		return getPortal().getPortletTitle(portlet, locale);
	}

	public static String getPortletTitle(
		Portlet portlet, ServletContext servletContext, Locale locale) {

		return getPortal().getPortletTitle(portlet, servletContext, locale);
	}

	public static String getPortletTitle(Portlet portlet, String languageId) {
		return getPortal().getPortletTitle(portlet, languageId);
	}

	public static String getPortletTitle(Portlet portlet, User user) {
		return getPortal().getPortletTitle(portlet, user);
	}

	public static String getPortletTitle(RenderResponse renderResponse) {
		return getPortal().getPortletTitle(renderResponse);
	}

	public static String getPortletTitle(String portletId, Locale locale) {
		return getPortal().getPortletTitle(portletId, locale);
	}

	public static String getPortletTitle(String portletId, String languageId) {
		return getPortal().getPortletTitle(portletId, languageId);
	}

	public static String getPortletTitle(String portletId, User user) {
		return getPortal().getPortletTitle(portletId, user);
	}

	public static String getPortletXmlFileName() throws SystemException {
		return getPortal().getPortletXmlFileName();
	}

	public static PortletPreferences getPreferences(
		HttpServletRequest request) {

		return getPortal().getPreferences(request);
	}

	public static PreferencesValidator getPreferencesValidator(
		Portlet portlet) {

		return getPortal().getPreferencesValidator(portlet);
	}

	public static String getRelativeHomeURL(HttpServletRequest request)
		throws PortalException, SystemException {

		return getPortal().getRelativeHomeURL(request);
	}

	public static long getScopeGroupId(HttpServletRequest request)
		throws PortalException, SystemException {

		return getPortal().getScopeGroupId(request);
	}

	public static long getScopeGroupId(
			HttpServletRequest request, String portletId)
		throws PortalException, SystemException {

		return getPortal().getScopeGroupId(request, portletId);
	}

	public static long getScopeGroupId(Layout layout) {
		return getPortal().getScopeGroupId(layout);
	}

	public static long getScopeGroupId(Layout layout, String portletId) {
		return getPortal().getScopeGroupId(layout, portletId);
	}

	public static long getScopeGroupId(long plid) {
		return getPortal().getScopeGroupId(plid);
	}

	public static long getScopeGroupId(PortletRequest portletRequest)
		throws PortalException, SystemException {

		return getPortal().getScopeGroupId(portletRequest);
	}

	public static User getSelectedUser(HttpServletRequest request)
		throws PortalException, SystemException {

		return getPortal().getSelectedUser(request);
	}

	public static User getSelectedUser(
			HttpServletRequest request, boolean checkPermission)
		throws PortalException, SystemException {

		return getPortal().getSelectedUser(request, checkPermission);
	}

	public static User getSelectedUser(PortletRequest portletRequest)
		throws PortalException, SystemException {

		return getPortal().getSelectedUser(portletRequest);
	}

	public static User getSelectedUser(
			PortletRequest portletRequest, boolean checkPermission)
		throws PortalException, SystemException {

		return getPortal().getSelectedUser(portletRequest, checkPermission);
	}

	public static ServletContext getServletContext(
		Portlet portlet, ServletContext servletContext) {

		return getPortal().getServletContext(portlet, servletContext);
	}

	public static String getSiteLoginURL(ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getPortal().getSiteLoginURL(themeDisplay);
	}

	/**
	 * @deprecated {@link
	 *             ResourceActionsUtil#getSocialEquityActionMapping(String,
	 *             String)}
	 */
	public static SocialEquityActionMapping getSocialEquityActionMapping(
		String name, String actionId) {

		return ResourceActionsUtil.getSocialEquityActionMapping(name, actionId);
	}

	/**
	 * @deprecated {@link
	 *             ResourceActionsUtil#getSocialEquityActionMappings(String)}
	 */
	public static List<SocialEquityActionMapping> getSocialEquityActionMappings(
		String name) {

		return ResourceActionsUtil.getSocialEquityActionMappings(name);
	}

	/**
	 * @deprecated {@link ResourceActionsUtil#getSocialEquityClassNames}
	 */
	public static String[] getSocialEquityClassNames() {
		return ResourceActionsUtil.getSocialEquityClassNames();
	}

	public static String getStaticResourceURL(
		HttpServletRequest request, String uri) {

		return getPortal().getStaticResourceURL(request, uri);
	}

	public static String getStaticResourceURL(
		HttpServletRequest request, String uri, long timestamp) {

		return getPortal().getStaticResourceURL(request, uri, timestamp);
	}

	public static String getStaticResourceURL(
		HttpServletRequest request, String uri, String queryString) {

		return getPortal().getStaticResourceURL(request, uri, queryString);
	}

	public static String getStaticResourceURL(
		HttpServletRequest request, String uri, String queryString,
		long timestamp) {

		return getPortal().getStaticResourceURL(
			request, uri, queryString, timestamp);
	}

	public static String getStrutsAction(HttpServletRequest request) {
		return getPortal().getStrutsAction(request);
	}

	public static String[] getSystemGroups() {
		return getPortal().getSystemGroups();
	}

	public static String[] getSystemOrganizationRoles() {
		return getPortal().getSystemOrganizationRoles();
	}

	public static String[] getSystemRoles() {
		return getPortal().getSystemRoles();
	}

	public static String[] getSystemSiteRoles() {
		return getPortal().getSystemSiteRoles();
	}

	public static UploadServletRequest getUploadServletRequest(
		HttpServletRequest request) {

		return getPortal().getUploadServletRequest(request);
	}

	public static UploadPortletRequest getUploadPortletRequest(
		PortletRequest portletRequest) {

		return getPortal().getUploadPortletRequest(portletRequest);
	}

	public static Date getUptime() {
		return getPortal().getUptime();
	}

	public static String getURLWithSessionId(String url, String sessionId) {
		return getPortal().getURLWithSessionId(url, sessionId);
	}

	public static User getUser(HttpServletRequest request)
		throws PortalException, SystemException {

		return getPortal().getUser(request);
	}

	public static User getUser(PortletRequest portletRequest)
		throws PortalException, SystemException {

		return getPortal().getUser(portletRequest);
	}

	public static String getUserEmailAddress(long userId)
		throws SystemException {

		return getPortal().getUserEmailAddress(userId);
	}

	public static long getUserId(HttpServletRequest request) {
		return getPortal().getUserId(request);
	}

	public static long getUserId(PortletRequest portletRequest) {
		return getPortal().getUserId(portletRequest);
	}

	public static String getUserName(long userId, String defaultUserName) {
		return getPortal().getUserName(userId, defaultUserName);
	}

	public static String getUserName(
		long userId, String defaultUserName, HttpServletRequest request) {

		return getPortal().getUserName(userId, defaultUserName, request);
	}

	public static String getUserName(
		long userId, String defaultUserName, String userAttribute) {

		return getPortal().getUserName(userId, defaultUserName, userAttribute);
	}

	public static String getUserName(
		long userId, String defaultUserName, String userAttribute,
		HttpServletRequest request) {

		return getPortal().getUserName(
			userId, defaultUserName, userAttribute, request);
	}

	public static String getUserPassword(HttpServletRequest request) {
		return getPortal().getUserPassword(request);
	}

	public static String getUserPassword(HttpSession session) {
		return getPortal().getUserPassword(session);
	}

	public static String getUserPassword(PortletRequest portletRequest) {
		return getPortal().getUserPassword(portletRequest);
	}

	public static String getUserValue(
			long userId, String param, String defaultValue)
		throws SystemException {

		return getPortal().getUserValue(userId, param, defaultValue);
	}

	public static long getValidUserId(long companyId, long userId)
		throws PortalException, SystemException {

		return getPortal().getValidUserId(companyId, userId);
	}

	public static String getWidgetURL(
			Portlet portlet, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getPortal().getWidgetURL(portlet, themeDisplay);
	}

	public static boolean isAllowAddPortletDefaultResource(
			HttpServletRequest request, Portlet portlet)
		throws PortalException, SystemException {

		return getPortal().isAllowAddPortletDefaultResource(request, portlet);
	}

	/**
	 * @deprecated As of 6.1, renamed to {@link #isGroupAdmin(User, long)}
	 */
	public static boolean isCommunityAdmin(User user, long groupId)
		throws Exception {

		return getPortal().isCommunityAdmin(user, groupId);
	}

	/**
	 * @deprecated As of 6.1, renamed to {@link #isGroupOwner(User, long)}
	 */
	public static boolean isCommunityOwner(User user, long groupId)
		throws Exception {

		return getPortal().isCommunityOwner(user, groupId);
	}

	public static boolean isCompanyAdmin(User user) throws Exception {
		return getPortal().isCompanyAdmin(user);
	}

	public static boolean isCompanyControlPanelPortlet(
			String portletId, String category, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getPortal().isCompanyControlPanelPortlet(
			portletId, category, themeDisplay);
	}

	public static boolean isCompanyControlPanelPortlet(
			String portletId, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getPortal().isCompanyControlPanelPortlet(
			portletId, themeDisplay);
	}

	public static boolean isCompanyControlPanelVisible(
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getPortal().isCompanyControlPanelVisible(themeDisplay);
	}

	public static boolean isControlPanelPortlet(
			String portletId, String category, ThemeDisplay themeDisplay)
		throws SystemException {

		return getPortal().isControlPanelPortlet(
			portletId, category, themeDisplay);
	}

	public static boolean isControlPanelPortlet(
			String portletId, ThemeDisplay themeDisplay)
		throws SystemException {

		return getPortal().isControlPanelPortlet(portletId, themeDisplay);
	}

	public static boolean isGroupAdmin(User user, long groupId)
			throws Exception {

		return getPortal().isGroupAdmin(user, groupId);
	}

	public static boolean isGroupOwner(User user, long groupId)
			throws Exception {

		return getPortal().isGroupOwner(user, groupId);
	}

	public static boolean isLayoutDescendant(Layout layout, long layoutId)
		throws PortalException, SystemException {

		return getPortal().isLayoutDescendant(layout, layoutId);
	}

	public static boolean isLayoutFirstPageable(Layout layout) {
		return getPortal().isLayoutFirstPageable(layout);
	}

	public static boolean isLayoutFirstPageable(String type) {
		return getPortal().isLayoutFirstPageable(type);
	}

	public static boolean isLayoutFriendliable(Layout layout) {
		return getPortal().isLayoutFriendliable(layout);
	}

	public static boolean isLayoutFriendliable(String type) {
		return getPortal().isLayoutFriendliable(type);
	}

	public static boolean isLayoutParentable(Layout layout) {
		return getPortal().isLayoutParentable(layout);
	}

	public static boolean isLayoutParentable(String type) {
		return getPortal().isLayoutParentable(type);
	}

	public static boolean isLayoutSitemapable(Layout layout) {
		return getPortal().isLayoutSitemapable(layout);
	}

	public static boolean isMethodGet(PortletRequest portletRequest) {
		return getPortal().isMethodGet(portletRequest);
	}

	public static boolean isMethodPost(PortletRequest portletRequest) {
		return getPortal().isMethodPost(portletRequest);
	}

	public static boolean isMultipartRequest(HttpServletRequest request) {
		return getPortal().isMultipartRequest(request);
	}

	public static boolean isOmniadmin(long userId) {
		return getPortal().isOmniadmin(userId);
	}

	public static boolean isReservedParameter(String name) {
		return getPortal().isReservedParameter(name);
	}

	public static boolean isSecure(HttpServletRequest request) {
		return getPortal().isSecure(request);
	}

	public static boolean isSystemGroup(String groupName) {
		return getPortal().isSystemGroup(groupName);
	}

	public static boolean isSystemRole(String roleName) {
		return getPortal().isSystemRole(roleName);
	}

	public static boolean isUpdateAvailable() throws SystemException {
		return getPortal().isUpdateAvailable();
	}

	public static boolean isValidResourceId(String resourceId) {
		return getPortal().isValidResourceId(resourceId);
	}

	public void removePortalPortEventListener(
		PortalPortEventListener portalPortEventListener) {

		getPortal().removePortalPortEventListener(portalPortEventListener);
	}

	public static String renderPage(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, String path, boolean writeOutput)
		throws IOException, ServletException {

		return getPortal().renderPage(servletContext, request, response, path);
	}

	public static String renderPortlet(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, Portlet portlet, String queryString,
			boolean writeOutput)
		throws IOException, ServletException {

		return getPortal().renderPortlet(
			servletContext, request, response, portlet, queryString,
			writeOutput);
	}

	public static String renderPortlet(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, Portlet portlet, String queryString,
			String columnId, Integer columnPos, Integer columnCount,
			boolean writeOutput)
		throws IOException, ServletException {

		return getPortal().renderPortlet(
			servletContext, request, response, portlet, queryString, columnId,
			columnPos, columnCount, writeOutput);
	}

	public static String renderPortlet(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, Portlet portlet, String queryString,
			String columnId, Integer columnPos, Integer columnCount,
			String path, boolean writeOutput)
		throws IOException, ServletException {

		return getPortal().renderPortlet(
			servletContext, request, response, portlet, queryString, columnId,
			columnPos, columnCount, path, writeOutput);
	}

	public static void resetCDNHosts() {
		getPortal().resetCDNHosts();
	}

	public static Set<String> resetPortletAddDefaultResourceCheckWhitelist() {
		return getPortal().resetPortletAddDefaultResourceCheckWhitelist();
	}

	public static Set<String>
		resetPortletAddDefaultResourceCheckWhitelistActions() {

		return getPortal().
			resetPortletAddDefaultResourceCheckWhitelistActions();
	}

	/**
	 * @deprecated {@link DB#runSQL(String)}
	 */
	public static void runSQL(String sql) throws IOException, SQLException {
		DBFactoryUtil.getDB().runSQL(sql);
	}

	public static void sendError(
			Exception e, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws IOException {

		getPortal().sendError(e, actionRequest, actionResponse);
	}

	public static void sendError(
			Exception e, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		getPortal().sendError(e, request, response);
	}

	public static void sendError(
			int status, Exception e, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws IOException {

		getPortal().sendError(status, e, actionRequest, actionResponse);
	}

	public static void sendError(
			int status, Exception e, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		getPortal().sendError(status, e, request, response);
	}

	/**
	 * Sets the description for a page. This overrides the existing page
	 * description.
	 */
	public static void setPageDescription(
		String description, HttpServletRequest request) {

		getPortal().setPageDescription(description, request);
	}

	/**
	 * Sets the keywords for a page. This overrides the existing page keywords.
	 */
	public static void setPageKeywords(
		String keywords, HttpServletRequest request) {

		getPortal().setPageKeywords(keywords, request);
	}

	/**
	 * Sets the subtitle for a page. This overrides the existing page subtitle.
	 */
	public static void setPageSubtitle(
		String subtitle, HttpServletRequest request) {

		getPortal().setPageSubtitle(subtitle, request);
	}

	/**
	 * Sets the whole title for a page. This overrides the existing page whole
	 * title.
	 */
	public static void setPageTitle(
		String title, HttpServletRequest request) {

		getPortal().setPageTitle(title, request);
	}

	/**
	 * Sets the port obtained on the first request to the portal.
	 */
	public static void setPortalPort(HttpServletRequest request) {
		getPortal().setPortalPort(request);
	}

	public static void storePreferences(PortletPreferences portletPreferences)
		throws IOException, ValidatorException {

		getPortal().storePreferences(portletPreferences);
	}

	public static String[] stripURLAnchor(String url, String separator) {
		return getPortal().stripURLAnchor(url, separator);
	}

	public static String transformCustomSQL(String sql) {
		return getPortal().transformCustomSQL(sql);
	}

	public static PortletMode updatePortletMode(
		String portletId, User user, Layout layout, PortletMode portletMode,
		HttpServletRequest request) {

		return getPortal().updatePortletMode(
			portletId, user, layout, portletMode, request);
	}

	public static String updateRedirect(
		String redirect, String oldPath, String newPath) {

		return getPortal().updateRedirect(redirect, oldPath, newPath);
	}

	public static WindowState updateWindowState(
		String portletId, User user, Layout layout, WindowState windowState,
		HttpServletRequest request) {

		return getPortal().updateWindowState(
			portletId, user, layout, windowState, request);
	}

	public void setPortal(Portal portal) {
		_portal = portal;
	}

	private static Portal _portal;

}