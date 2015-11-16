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

package com.liferay.portal.util;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutFriendlyURLComposite;
import com.liferay.portal.model.LayoutQueryStringComposite;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.User;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.InvokerPortlet;
import com.liferay.portlet.PortletInstanceFactoryUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.IOException;
import java.io.Serializable;

import java.net.InetAddress;

import java.sql.SQLException;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TimeZone;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.PreferencesValidator;
import javax.portlet.RenderRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ValidatorException;
import javax.portlet.WindowState;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 * @author Juan Fernández
 */
@ProviderType
public class PortalUtil {

	/**
	 * Appends the description to the current meta description of the page in
	 * the request.
	 *
	 * @param description the description to append to the current meta
	 *        description
	 * @param request the servlet request for the page
	 */
	public static void addPageDescription(
		String description, HttpServletRequest request) {

		getPortal().addPageDescription(description, request);
	}

	/**
	 * Appends the keywords to the current meta keywords of the page in the
	 * request.
	 *
	 * @param keywords the keywords to add to the current meta keywords
	 *        (comma-separated)
	 * @param request the servlet request for the page
	 */
	public static void addPageKeywords(
		String keywords, HttpServletRequest request) {

		getPortal().addPageKeywords(keywords, request);
	}

	/**
	 * Appends the subtitle to the current subtitle of the page in the request.
	 *
	 * @param subtitle the subtitle to append to the current subtitle
	 * @param request the servlet request for the page
	 */
	public static void addPageSubtitle(
		String subtitle, HttpServletRequest request) {

		getPortal().addPageSubtitle(subtitle, request);
	}

	/**
	 * Appends the title to the current title of the page in the request.
	 *
	 * @param title the title to append to the current title
	 * @param request the servlet request for the page
	 */
	public static void addPageTitle(String title, HttpServletRequest request) {
		getPortal().addPageTitle(title, request);
	}

	public static boolean addPortalInetSocketAddressEventListener(
		PortalInetSocketAddressEventListener
			portalInetSocketAddressEventListener) {

		return getPortal().addPortalInetSocketAddressEventListener(
			portalInetSocketAddressEventListener);
	}

	/**
	 * Adds the portal port event listener to the portal. The listener is
	 * notified whenever the portal port is set.
	 *
	 * @param      portalPortEventListener the portal port event listener to add
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #addPortalInetSocketAddressEventListener(
	 *             PortalInetSocketAddressEventListener)}
	 */
	@Deprecated
	public static void addPortalPortEventListener(
		PortalPortEventListener portalPortEventListener) {

		getPortal().addPortalPortEventListener(portalPortEventListener);
	}

	/**
	 * Adds an entry to the portlet breadcrumbs for the page in the request.
	 *
	 * @param request the servlet request for the page
	 * @param title the title of the new breadcrumb entry
	 * @param url the URL of the new breadcrumb entry
	 */
	public static void addPortletBreadcrumbEntry(
		HttpServletRequest request, String title, String url) {

		getPortal().addPortletBreadcrumbEntry(request, title, url);
	}

	/**
	 * Adds an entry to the portlet breadcrumbs for the page in the request.
	 *
	 * @param request the servlet request for the page
	 * @param title the title of the new breadcrumb entry
	 * @param url the URL of the new breadcrumb entry
	 * @param data the HTML5 data parameters of the new breadcrumb entry
	 */
	public static void addPortletBreadcrumbEntry(
		HttpServletRequest request, String title, String url,
		Map<String, Object> data) {

		getPortal().addPortletBreadcrumbEntry(request, title, url, data);
	}

	/**
	 * Adds the default resource permissions for the portlet to the page in the
	 * request.
	 *
	 * @param request the servlet request for the page
	 * @param portlet the portlet
	 */
	public static void addPortletDefaultResource(
			HttpServletRequest request, Portlet portlet)
		throws PortalException {

		getPortal().addPortletDefaultResource(request, portlet);
	}

	public static void addPortletDefaultResource(
			long companyId, Layout layout, Portlet portlet)
		throws PortalException {

		getPortal().addPortletDefaultResource(companyId, layout, portlet);
	}

	/**
	 * Adds the preserved parameters doAsGroupId and refererPlid to the URL,
	 * optionally adding doAsUserId and doAsUserLanguageId as well.
	 *
	 * <p>
	 * Preserved parameters are parameters that should be sent with every
	 * request as the user navigates the portal.
	 * </p>
	 *
	 * @param  themeDisplay the current theme display
	 * @param  layout the current page
	 * @param  url the URL
	 * @param  doAsUser whether to include doAsUserId and doAsLanguageId in the
	 *         URL if they are available. If <code>false</code>, doAsUserId and
	 *         doAsUserLanguageId will never be added.
	 * @return the URL with the preserved parameters added
	 */
	public static String addPreservedParameters(
		ThemeDisplay themeDisplay, Layout layout, String url,
		boolean doAsUser) {

		return getPortal().addPreservedParameters(
			themeDisplay, layout, url, doAsUser);
	}

	/**
	 * Adds the preserved parameters doAsUserId, doAsUserLanguageId,
	 * doAsGroupId, and refererPlid to the URL.
	 *
	 * @param  themeDisplay the current theme display
	 * @param  url the URL
	 * @return the URL with the preserved parameters added
	 */
	public static String addPreservedParameters(
		ThemeDisplay themeDisplay, String url) {

		return getPortal().addPreservedParameters(themeDisplay, url);
	}

	public static void addUserLocaleOptionsMessage(HttpServletRequest request) {
		getPortal().addUserLocaleOptionsMessage(request);
	}

	/**
	 * Clears the render parameters in the request if the portlet is in the
	 * action phase.
	 *
	 * @param renderRequest the render request
	 */
	public static void clearRequestParameters(RenderRequest renderRequest) {
		getPortal().clearRequestParameters(renderRequest);
	}

	/**
	 * Copies the request parameters to the render parameters, unless a
	 * parameter with that name already exists in the render parameters.
	 *
	 * @param actionRequest the request from which to get the request parameters
	 * @param actionResponse the response to receive the render parameters
	 */
	public static void copyRequestParameters(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		getPortal().copyRequestParameters(actionRequest, actionResponse);
	}

	/**
	 * Escapes the URL for use in a redirect and checks that security settings
	 * allow the URL is allowed for redirects.
	 *
	 * @param  url the URL to escape
	 * @return the escaped URL, or <code>null</code> if the URL is not allowed
	 *         for redirects
	 */
	public static String escapeRedirect(String url) {
		return getPortal().escapeRedirect(url);
	}

	/**
	 * Generates a random key to identify the request based on the input string.
	 *
	 * @param  request the servlet request for the page
	 * @param  input the input string
	 * @return the generated key
	 */
	public static String generateRandomKey(
		HttpServletRequest request, String input) {

		return getPortal().generateRandomKey(request, input);
	}

	public static String getAbsoluteURL(
		HttpServletRequest request, String url) {

		return getPortal().getAbsoluteURL(request, url);
	}

	public static LayoutQueryStringComposite
		getActualLayoutQueryStringComposite(
			long groupId, boolean privateLayout, String friendlyURL,
			Map<String, String[]> params, Map<String, Object> requestContext)
		throws PortalException {

		return getPortal().getActualLayoutQueryStringComposite(
			groupId, privateLayout, friendlyURL, params, requestContext);
	}

	public static String getActualURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		return getPortal().getActualURL(
			groupId, privateLayout, mainPath, friendlyURL, params,
			requestContext);
	}

	/**
	 * Returns the alternate URL for the requested canonical URL in the given
	 * locale.
	 *
	 * <p>
	 * The alternate URL lets search engines know that an equivalent page is
	 * available for the given locale. For more information, see <a
	 * href="https://support.google.com/webmasters/answer/189077?hl=en">https://support.google.com/webmasters/answer/189077?hl=en</a>.
	 * </p>
	 *
	 * @param  canonicalURL the canonical URL being requested. For more
	 *         information, see {@link #getCanonicalURL}.
	 * @param  themeDisplay the theme display
	 * @param  locale the locale of the alternate URL being generated
	 * @param  layout the page being requested
	 * @return the alternate URL for the requested canonical URL in the given
	 *         locale
	 */
	public static String getAlternateURL(
			String canonicalURL, ThemeDisplay themeDisplay, Locale locale,
			Layout layout)
		throws PortalException {

		return getPortal().getAlternateURL(
			canonicalURL, themeDisplay, locale, layout);
	}

	public static long[] getAncestorSiteGroupIds(long groupId)
		throws PortalException {

		return getPortal().getAncestorSiteGroupIds(groupId);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portal.security.auth.AuthTokenWhitelistUtil#getPortletCSRFWhitelistActions}
	 */
	@Deprecated
	public static Set<String> getAuthTokenIgnoreActions() {
		return getPortal().getAuthTokenIgnoreActions();
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portal.security.auth.AuthTokenWhitelistUtil#getPortletCSRFWhitelist}
	 */
	@Deprecated
	public static Set<String> getAuthTokenIgnorePortlets() {
		return getPortal().getAuthTokenIgnorePortlets();
	}

	/**
	 * Returns the base model instance for the resource permission.
	 *
	 * @param  resourcePermission the resource permission
	 * @return the base model instance, or <code>null</code> if the resource
	 *         permission does not have a base model instance (such as if it's a
	 *         portlet)
	 */
	public static BaseModel<?> getBaseModel(
			ResourcePermission resourcePermission)
		throws PortalException {

		return getPortal().getBaseModel(resourcePermission);
	}

	/**
	 * Returns the base model instance for the model name and primary key.
	 *
	 * @param  modelName the fully qualified class name of the model
	 * @param  primKey the primary key of the model instance to get
	 * @return the base model instance, or <code>null</code> if the model does
	 *         not have a base model instance (such as if it's a portlet)
	 */
	public static BaseModel<?> getBaseModel(String modelName, String primKey)
		throws PortalException {

		return getPortal().getBaseModel(modelName, primKey);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.portal.kernel.security.auth.http.HttpAuthManagerUtil#getBasicUserId(
	 *             HttpServletRequest)}
	 */
	@Deprecated
	public static long getBasicAuthUserId(HttpServletRequest request)
		throws PortalException {

		return getPortal().getBasicAuthUserId(request);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.portal.kernel.security.auth.http.HttpAuthManagerUtil#getBasicUserId(
	 *             HttpServletRequest)}
	 */
	@Deprecated
	public static long getBasicAuthUserId(
			HttpServletRequest request, long companyId)
		throws PortalException {

		return getPortal().getBasicAuthUserId(request, companyId);
	}

	public static List<Group> getBrowsableScopeGroups(
			long userId, long companyId, long groupId, String portletId)
		throws PortalException {

		return getPortal().getBrowsableScopeGroups(
			userId, companyId, groupId, portletId);
	}

	/**
	 * Returns the canonical URL for the page. The canonical URL is often used
	 * to distinguish a preferred page from its translations.
	 *
	 * <p>
	 * A canonical URL for the page is the preferred URL to specify for a set of
	 * pages with similar or identical content. The canonical URL is used to
	 * inform search engines that several URLs point to the same page. It is
	 * also used to generate the URLs for site maps, the URLs that social
	 * bookmarks publish (Twitter, Facebook links, etc.), and the URLs in sent
	 * email. For more information, see <a
	 * href="https://support.google.com/webmasters/answer/139394?hl=en">https://support.google.com/webmasters/answer/139394?hl=en</a>.
	 * </p>
	 *
	 * @param  completeURL the complete URL of the page
	 * @param  themeDisplay the theme display
	 * @param  layout the page being requested (optionally <code>null</code>).
	 *         If <code>null</code> is specified, the current page is used.
	 * @return the canonical URL for the page
	 */
	public static String getCanonicalURL(
			String completeURL, ThemeDisplay themeDisplay, Layout layout)
		throws PortalException {

		return getPortal().getCanonicalURL(completeURL, themeDisplay, layout);
	}

	/**
	 * Returns the canonical URL of the page, optionally including the page's
	 * friendly URL. The canonical URL is often used to distinguish a preferred
	 * page from its translations.
	 *
	 * <p>
	 * A canonical URL for the page is the preferred URL to specify for a set of
	 * pages with similar or identical content. The canonical URL is used to
	 * inform search engines that several URLs point to the same page. It is
	 * also used to generate the URLs for site maps, the URLs that social
	 * bookmarks publish (Twitter, Facebook links, etc.), and the URLs in sent
	 * email. For more information, see <a
	 * href="https://support.google.com/webmasters/answer/139394?hl=en">https://support.google.com/webmasters/answer/139394?hl=en</a>.
	 * </p>
	 *
	 * @param  completeURL the complete URL of the page
	 * @param  themeDisplay the current theme display
	 * @param  layout the page. If it is <code>null</code>, then it is generated
	 *         for the current page.
	 * @param  forceLayoutFriendlyURL whether to add the page's friendly URL to
	 *         the canonical URL
	 * @return the canonical URL of the page
	 */
	public static String getCanonicalURL(
			String completeURL, ThemeDisplay themeDisplay, Layout layout,
			boolean forceLayoutFriendlyURL)
		throws PortalException {

		return getPortal().getCanonicalURL(
			completeURL, themeDisplay, layout, forceLayoutFriendlyURL);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getCDNHost(boolean)}
	 */
	@Deprecated
	public static String getCDNHost() {
		return getPortal().getCDNHost();
	}

	/**
	 * Returns the secure (HTTPS) or insecure (HTTP) content distribution
	 * network (CDN) host address for this portal.
	 *
	 * @param  secure whether to get the secure CDN host address
	 * @return the CDN host address
	 */
	public static String getCDNHost(boolean secure) {
		return getPortal().getCDNHost(secure);
	}

	public static String getCDNHost(HttpServletRequest request)
		throws PortalException {

		return getPortal().getCDNHost(request);
	}

	/**
	 * Returns the insecure (HTTP) content distribution network (CDN) host
	 * address
	 *
	 * @param  companyId the company ID of a site
	 * @return the CDN host address
	 */
	public static String getCDNHostHttp(long companyId) {
		return getPortal().getCDNHostHttp(companyId);
	}

	/**
	 * Returns the secure (HTTPS) content distribution network (CDN) host
	 * address
	 *
	 * @param  companyId the company ID of a site
	 * @return the CDN host address
	 */
	public static String getCDNHostHttps(long companyId) {
		return getPortal().getCDNHostHttps(companyId);
	}

	/**
	 * Returns the fully qualified name of the class from its ID.
	 *
	 * @param  classNameId the ID of the class
	 * @return the fully qualified name of the class
	 */
	public static String getClassName(long classNameId) {
		return getPortal().getClassName(classNameId);
	}

	/**
	 * Returns the ID of the class from its class object.
	 *
	 * @param  clazz the class object
	 * @return the ID of the class
	 */
	public static long getClassNameId(Class<?> clazz) {
		return getPortal().getClassNameId(clazz);
	}

	/**
	 * Returns the ID of the class from its fully qualified name.
	 *
	 * @param  value the fully qualified name of the class
	 * @return the ID of the class
	 */
	public static long getClassNameId(String value) {
		return getPortal().getClassNameId(value);
	}

	public static Company getCompany(HttpServletRequest request)
		throws PortalException {

		return getPortal().getCompany(request);
	}

	public static Company getCompany(PortletRequest portletRequest)
		throws PortalException {

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

	public static Set<String> getComputerAddresses() {
		return getPortal().getComputerAddresses();
	}

	public static String getComputerName() {
		return getPortal().getComputerName();
	}

	public static String getControlPanelFullURL(
			long scopeGroupId, String ppid, Map<String, String[]> params)
		throws PortalException {

		return getPortal().getControlPanelFullURL(scopeGroupId, ppid, params);
	}

	public static long getControlPanelPlid(long companyId)
		throws PortalException {

		return getPortal().getControlPanelPlid(companyId);
	}

	public static long getControlPanelPlid(PortletRequest portletRequest)
		throws PortalException {

		return getPortal().getControlPanelPlid(portletRequest);
	}

	public static PortletURL getControlPanelPortletURL(
		HttpServletRequest request, Group group, String portletId,
		long refererGroupId, long refererPlid, String lifecycle) {

		return getPortal().getControlPanelPortletURL(
			request, group, portletId, refererGroupId, refererPlid, lifecycle);
	}

	public static PortletURL getControlPanelPortletURL(
		HttpServletRequest request, String portletId, String lifecycle) {

		return getPortal().getControlPanelPortletURL(
			request, portletId, lifecycle);
	}

	public static PortletURL getControlPanelPortletURL(
		PortletRequest portletRequest, Group group, String portletId,
		long refererGroupId, long refererPlid, String lifecycle) {

		return getPortal().getControlPanelPortletURL(
			portletRequest, group, portletId, refererGroupId, refererPlid,
			lifecycle);
	}

	public static PortletURL getControlPanelPortletURL(
		PortletRequest portletRequest, String portletId, String lifecycle) {

		return getPortal().getControlPanelPortletURL(
			portletRequest, portletId, lifecycle);
	}

	public static String getCreateAccountURL(
			HttpServletRequest request, ThemeDisplay themeDisplay)
		throws Exception {

		return getPortal().getCreateAccountURL(request, themeDisplay);
	}

	public static long[] getCurrentAndAncestorSiteGroupIds(long groupId)
		throws PortalException {

		return getPortal().getCurrentAndAncestorSiteGroupIds(groupId);
	}

	public static long[] getCurrentAndAncestorSiteGroupIds(
			long groupId, boolean checkContentSharingWithChildrenEnabled)
		throws PortalException {

		return getPortal().getCurrentAndAncestorSiteGroupIds(
			groupId, checkContentSharingWithChildrenEnabled);
	}

	public static long[] getCurrentAndAncestorSiteGroupIds(long[] groupIds)
		throws PortalException {

		return getPortal().getCurrentAndAncestorSiteGroupIds(groupIds);
	}

	public static long[] getCurrentAndAncestorSiteGroupIds(
			long[] groupIds, boolean checkContentSharingWithChildrenEnabled)
		throws PortalException {

		return getPortal().getCurrentAndAncestorSiteGroupIds(
			groupIds, checkContentSharingWithChildrenEnabled);
	}

	public static List<Group> getCurrentAndAncestorSiteGroups(long groupId)
		throws PortalException {

		return getPortal().getCurrentAndAncestorSiteGroups(groupId);
	}

	public static List<Group> getCurrentAndAncestorSiteGroups(
			long groupId, boolean checkContentSharingWithChildrenEnabled)
		throws PortalException {

		return getPortal().getCurrentAndAncestorSiteGroups(
			groupId, checkContentSharingWithChildrenEnabled);
	}

	public static List<Group> getCurrentAndAncestorSiteGroups(long[] groupIds)
		throws PortalException {

		return getPortal().getCurrentAndAncestorSiteGroups(groupIds);
	}

	public static List<Group> getCurrentAndAncestorSiteGroups(
			long[] groupIds, boolean checkContentSharingWithChildrenEnabled)
		throws PortalException {

		return getPortal().getCurrentAndAncestorSiteGroups(
			groupIds, checkContentSharingWithChildrenEnabled);
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

	/**
	 * Returns the date object for the specified month, day, and year, or
	 * <code>null</code> if the date is invalid.
	 *
	 * @param  month the month (0-based, meaning 0 for January)
	 * @param  day the day of the month
	 * @param  year the year
	 * @return the date object, or <code>null</code> if the date is invalid
	 */
	public static Date getDate(int month, int day, int year) {
		return getPortal().getDate(month, day, year);
	}

	/**
	 * Returns the date object for the specified month, day, and year,
	 * optionally throwing an exception if the date is invalid.
	 *
	 * @param  month the month (0-based, meaning 0 for January)
	 * @param  day the day of the month
	 * @param  year the year
	 * @param  clazz the exception class to throw if the date is invalid. If
	 *         <code>null</code>, no exception will be thrown for an invalid
	 *         date.
	 * @return the date object, or <code>null</code> if the date is invalid and
	 *         no exception to throw was provided
	 */
	public static Date getDate(
			int month, int day, int year,
			Class<? extends PortalException> clazz)
		throws PortalException {

		return getPortal().getDate(month, day, year, clazz);
	}

	/**
	 * Returns the date object for the specified month, day, year, hour, and
	 * minute, optionally throwing an exception if the date is invalid.
	 *
	 * @param  month the month (0-based, meaning 0 for January)
	 * @param  day the day of the month
	 * @param  year the year
	 * @param  hour the hour (0-24)
	 * @param  min the minute of the hour
	 * @param  clazz the exception class to throw if the date is invalid. If
	 *         <code>null</code>, no exception will be thrown for an invalid
	 *         date.
	 * @return the date object, or <code>null</code> if the date is invalid and
	 *         no exception to throw was provided
	 */
	public static Date getDate(
			int month, int day, int year, int hour, int min,
			Class<? extends PortalException> clazz)
		throws PortalException {

		return getPortal().getDate(month, day, year, hour, min, clazz);
	}

	/**
	 * Returns the date object for the specified month, day, year, hour, minute,
	 * and time zone, optionally throwing an exception if the date is invalid.
	 *
	 * @param  month the month (0-based, meaning 0 for January)
	 * @param  day the day of the month
	 * @param  year the year
	 * @param  hour the hour (0-24)
	 * @param  min the minute of the hour
	 * @param  timeZone the time zone of the date
	 * @param  clazz the exception class to throw if the date is invalid. If
	 *         <code>null</code>, no exception will be thrown for an invalid
	 *         date.
	 * @return the date object, or <code>null</code> if the date is invalid and
	 *         no exception to throw was provided
	 */
	public static Date getDate(
			int month, int day, int year, int hour, int min, TimeZone timeZone,
			Class<? extends PortalException> clazz)
		throws PortalException {

		return getPortal().getDate(
			month, day, year, hour, min, timeZone, clazz);
	}

	/**
	 * Returns the date object for the specified month, day, year, and time
	 * zone, optionally throwing an exception if the date is invalid.
	 *
	 * @param  month the month (0-based, meaning 0 for January)
	 * @param  day the day of the month
	 * @param  year the year
	 * @param  timeZone the time zone of the date
	 * @param  clazz the exception class to throw if the date is invalid. If
	 *         <code>null</code>, no exception will be thrown for an invalid
	 *         date.
	 * @return the date object, or <code>null</code> if the date is invalid and
	 *         no exception to throw was provided
	 */
	public static Date getDate(
			int month, int day, int year, TimeZone timeZone,
			Class<? extends PortalException> clazz)
		throws PortalException {

		return getPortal().getDate(month, day, year, timeZone, clazz);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link DBFactoryUtil#getDB()}
	 */
	@Deprecated
	public static DB getDB() {
		return DBFactoryUtil.getDB();
	}

	public static long getDefaultCompanyId() {
		return getPortal().getDefaultCompanyId();
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.portal.kernel.security.auth.http.HttpAuthManagerUtil#getDigestUserId(
	 *             HttpServletRequest)}
	 */
	@Deprecated
	public static long getDigestAuthUserId(HttpServletRequest request)
		throws PortalException {

		return getPortal().getDigestAuthUserId(request);
	}

	public static String getEmailFromAddress(
		PortletPreferences preferences, long companyId, String defaultValue) {

		return getPortal().getEmailFromAddress(
			preferences, companyId, defaultValue);
	}

	public static String getEmailFromName(
		PortletPreferences preferences, long companyId, String defaultValue) {

		return getPortal().getEmailFromName(
			preferences, companyId, defaultValue);
	}

	public static Map<String, Serializable> getExpandoBridgeAttributes(
			ExpandoBridge expandoBridge, HttpServletRequest request)
		throws PortalException {

		return getPortal().getExpandoBridgeAttributes(expandoBridge, request);
	}

	public static Map<String, Serializable> getExpandoBridgeAttributes(
			ExpandoBridge expandoBridge, PortletRequest portletRequest)
		throws PortalException {

		return getPortal().getExpandoBridgeAttributes(
			expandoBridge, portletRequest);
	}

	public static Map<String, Serializable> getExpandoBridgeAttributes(
			ExpandoBridge expandoBridge,
			UploadPortletRequest uploadPortletRequest)
		throws PortalException {

		return getPortal().getExpandoBridgeAttributes(
			expandoBridge, uploadPortletRequest);
	}

	public static Serializable getExpandoValue(
			HttpServletRequest request, String name, int type,
			String displayType)
		throws PortalException {

		return getPortal().getExpandoValue(request, name, type, displayType);
	}

	public static Serializable getExpandoValue(
			PortletRequest portletRequest, String name, int type,
			String displayType)
		throws PortalException {

		return getPortal().getExpandoValue(
			portletRequest, name, type, displayType);
	}

	public static Serializable getExpandoValue(
			UploadPortletRequest uploadPortletRequest, String name, int type,
			String displayType)
		throws PortalException {

		return getPortal().getExpandoValue(
			uploadPortletRequest, name, type, displayType);
	}

	public static String getFacebookURL(
			Portlet portlet, String facebookCanvasPageURL,
			ThemeDisplay themeDisplay)
		throws PortalException {

		return getPortal().getFacebookURL(
			portlet, facebookCanvasPageURL, themeDisplay);
	}

	public static String getFirstPageLayoutTypes(HttpServletRequest request) {
		return getPortal().getFirstPageLayoutTypes(request);
	}

	public static String getFullName(
		String firstName, String middleName, String lastName) {

		return getPortal().getFullName(firstName, middleName, lastName);
	}

	public static String getGlobalLibDir() {
		return getPortal().getGlobalLibDir();
	}

	public static String getGoogleGadgetURL(
			Portlet portlet, ThemeDisplay themeDisplay)
		throws PortalException {

		return getPortal().getGoogleGadgetURL(portlet, themeDisplay);
	}

	public static String getGroupFriendlyURL(
			LayoutSet layoutSet, ThemeDisplay themeDisplay)
		throws PortalException {

		return getPortal().getGroupFriendlyURL(layoutSet, themeDisplay);
	}

	public static String getGroupFriendlyURL(
			LayoutSet layoutSet, ThemeDisplay themeDisplay, Locale locale)
		throws PortalException {

		return getPortal().getGroupFriendlyURL(layoutSet, themeDisplay, locale);
	}

	public static int[] getGroupFriendlyURLIndex(String requestURI) {
		return getPortal().getGroupFriendlyURLIndex(requestURI);
	}

	public static String[] getGroupPermissions(HttpServletRequest request) {
		return getPortal().getGroupPermissions(request);
	}

	public static String[] getGroupPermissions(
		HttpServletRequest request, String className) {

		return getPortal().getGroupPermissions(request, className);
	}

	public static String[] getGroupPermissions(PortletRequest portletRequest) {
		return getPortal().getGroupPermissions(portletRequest);
	}

	public static String[] getGroupPermissions(
		PortletRequest portletRequest, String className) {

		return getPortal().getGroupPermissions(portletRequest, className);
	}

	public static String[] getGuestPermissions(HttpServletRequest request) {
		return getPortal().getGuestPermissions(request);
	}

	public static String[] getGuestPermissions(
		HttpServletRequest request, String className) {

		return getPortal().getGuestPermissions(request, className);
	}

	public static String[] getGuestPermissions(PortletRequest portletRequest) {
		return getPortal().getGuestPermissions(portletRequest);
	}

	public static String[] getGuestPermissions(
		PortletRequest portletRequest, String className) {

		return getPortal().getGuestPermissions(portletRequest, className);
	}

	public static String getHomeURL(HttpServletRequest request)
		throws PortalException {

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

	public static String getI18nPathLanguageId(
		Locale locale, String defaultI18nPathLanguageId) {

		return getPortal().getI18nPathLanguageId(
			locale, defaultI18nPathLanguageId);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static String getJournalArticleActualURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		return getPortal().getJournalArticleActualURL(
			groupId, privateLayout, mainPath, friendlyURL, params,
			requestContext);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static Layout getJournalArticleLayout(
			long groupId, boolean privateLayout, String friendlyURL)
		throws PortalException {

		return getPortal().getJournalArticleLayout(
			groupId, privateLayout, friendlyURL);
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
		throws PortalException {

		return getPortal().getLayoutActualURL(
			groupId, privateLayout, mainPath, friendlyURL);
	}

	public static String getLayoutActualURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		return getPortal().getLayoutActualURL(
			groupId, privateLayout, mainPath, friendlyURL, params,
			requestContext);
	}

	public static String getLayoutFriendlyURL(
			Layout layout, ThemeDisplay themeDisplay)
		throws PortalException {

		return getPortal().getLayoutFriendlyURL(layout, themeDisplay);
	}

	public static String getLayoutFriendlyURL(
			Layout layout, ThemeDisplay themeDisplay, Locale locale)
		throws PortalException {

		return getPortal().getLayoutFriendlyURL(layout, themeDisplay, locale);
	}

	public static LayoutFriendlyURLComposite getLayoutFriendlyURLComposite(
			long groupId, boolean privateLayout, String friendlyURL,
			Map<String, String[]> params, Map<String, Object> requestContext)
		throws PortalException {

		return getPortal().getLayoutFriendlyURLComposite(
			groupId, privateLayout, friendlyURL, params, requestContext);
	}

	public static String getLayoutFullURL(
			Layout layout, ThemeDisplay themeDisplay)
		throws PortalException {

		return getPortal().getLayoutFullURL(layout, themeDisplay);
	}

	public static String getLayoutFullURL(
			Layout layout, ThemeDisplay themeDisplay, boolean doAsUser)
		throws PortalException {

		return getPortal().getLayoutFullURL(layout, themeDisplay, doAsUser);
	}

	public static String getLayoutFullURL(long groupId, String portletId)
		throws PortalException {

		return getPortal().getLayoutFullURL(groupId, portletId);
	}

	public static String getLayoutFullURL(
			long groupId, String portletId, boolean secure)
		throws PortalException {

		return getPortal().getLayoutFullURL(groupId, portletId, secure);
	}

	public static String getLayoutFullURL(ThemeDisplay themeDisplay)
		throws PortalException {

		return getPortal().getLayoutFullURL(themeDisplay);
	}

	public static String getLayoutRelativeURL(
			Layout layout, ThemeDisplay themeDisplay)
		throws PortalException {

		return getPortal().getLayoutRelativeURL(layout, themeDisplay);
	}

	public static String getLayoutRelativeURL(
			Layout layout, ThemeDisplay themeDisplay, boolean doAsUser)
		throws PortalException {

		return getPortal().getLayoutRelativeURL(layout, themeDisplay, doAsUser);
	}

	public static String getLayoutSetFriendlyURL(
			LayoutSet layoutSet, ThemeDisplay themeDisplay)
		throws PortalException {

		return getPortal().getLayoutSetFriendlyURL(layoutSet, themeDisplay);
	}

	public static String getLayoutTarget(Layout layout) {
		return getPortal().getLayoutTarget(layout);
	}

	public static String getLayoutURL(Layout layout, ThemeDisplay themeDisplay)
		throws PortalException {

		return getPortal().getLayoutURL(layout, themeDisplay);
	}

	public static String getLayoutURL(
			Layout layout, ThemeDisplay themeDisplay, boolean doAsUser)
		throws PortalException {

		return getPortal().getLayoutURL(layout, themeDisplay, doAsUser);
	}

	public static String getLayoutURL(
			Layout layout, ThemeDisplay themeDisplay, Locale locale)
		throws PortalException {

		return getPortal().getLayoutURL(layout, themeDisplay, locale);
	}

	public static String getLayoutURL(ThemeDisplay themeDisplay)
		throws PortalException {

		return getPortal().getLayoutURL(themeDisplay);
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

	public static Locale getLocale(
		HttpServletRequest request, HttpServletResponse response,
		boolean initialize) {

		return getPortal().getLocale(request, response, initialize);
	}

	public static Locale getLocale(PortletRequest portletRequest) {
		return getPortal().getLocale(portletRequest);
	}

	public static String getLocalizedFriendlyURL(
			HttpServletRequest request, Layout layout, Locale locale,
			Locale originalLocale)
		throws Exception {

		return getPortal().getLocalizedFriendlyURL(
			request, layout, locale, originalLocale);
	}

	public static String getMailId(
		String mx, String popPortletPrefix, Object... ids) {

		return getPortal().getMailId(mx, popPortletPrefix, ids);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link
	 *             #getBaseModel(ResourcePermission)}
	 */
	@Deprecated
	public static BaseModel<?> getModel(ResourcePermission resourcePermission)
		throws PortalException {

		return getPortal().getBaseModel(resourcePermission);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getBaseModel(String,
	 *             String)}
	 */
	@Deprecated
	public static BaseModel<?> getModel(String modelName, String primKey)
		throws PortalException {

		return getPortal().getBaseModel(modelName, primKey);
	}

	public static String getNetvibesURL(
			Portlet portlet, ThemeDisplay themeDisplay)
		throws PortalException {

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

	/**
	 * @deprecated As of 6.2.0 renamed to {@link #getSiteGroupId(long)}
	 */
	@Deprecated
	public static long getParentGroupId(long scopeGroupId) {
		return getPortal().getParentGroupId(scopeGroupId);
	}

	public static String getPathContext() {
		return getPortal().getPathContext();
	}

	public static String getPathContext(HttpServletRequest request) {
		return getPortal().getPathContext(request);
	}

	public static String getPathContext(PortletRequest portletRequest) {
		return getPortal().getPathContext(portletRequest);
	}

	public static String getPathContext(String contextPath) {
		return getPortal().getPathContext(contextPath);
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

	public static String getPathModule() {
		return getPortal().getPathModule();
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
		throws PortalException {

		return getPortal().getPlidFromPortletId(
			groupId, privateLayout, portletId);
	}

	public static long getPlidFromPortletId(long groupId, String portletId)
		throws PortalException {

		return getPortal().getPlidFromPortletId(groupId, portletId);
	}

	public static Portal getPortal() {
		PortalRuntimePermission.checkGetBeanProperty(PortalUtil.class);

		return _portal;
	}

	public static PortalInetSocketAddressEventListener[]
		getPortalInetSocketAddressEventListeners() {

		return getPortal().getPortalInetSocketAddressEventListeners();
	}

	public static String getPortalLibDir() {
		return getPortal().getPortalLibDir();
	}

	public static InetAddress getPortalLocalInetAddress(boolean secure) {
		return getPortal().getPortalLocalInetAddress(secure);
	}

	public static int getPortalLocalPort(boolean secure) {
		return getPortal().getPortalLocalPort(secure);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getPortalPort(boolean)}
	 */
	@Deprecated
	public static int getPortalPort() {
		return getPortal().getPortalPort();
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getPortalServerPort(boolean)}
	 */
	@Deprecated
	public static int getPortalPort(boolean secure) {
		return getPortal().getPortalPort(secure);
	}

	public static Properties getPortalProperties() {
		return getPortal().getPortalProperties();
	}

	public static InetAddress getPortalServerInetAddress(boolean secure) {
		return getPortal().getPortalServerInetAddress(secure);
	}

	public static int getPortalServerPort(boolean secure) {
		return getPortal().getPortalServerPort(secure);
	}

	public static String getPortalURL(HttpServletRequest request) {
		return getPortal().getPortalURL(request);
	}

	public static String getPortalURL(
		HttpServletRequest request, boolean secure) {

		return getPortal().getPortalURL(request, secure);
	}

	public static String getPortalURL(Layout layout, ThemeDisplay themeDisplay)
		throws PortalException {

		return getPortal().getPortalURL(layout, themeDisplay);
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

	public static String getPortalURL(ThemeDisplay themeDisplay)
		throws PortalException {

		return getPortal().getPortalURL(themeDisplay);
	}

	public static String getPortalWebDir() {
		return getPortal().getPortalWebDir();
	}

	/**
	 * @deprecated As of 6.2.0 replaced by {@link
	 *             com.liferay.portal.security.auth.AuthTokenWhitelistUtil#getPortletInvocationWhitelist}
	 */
	@Deprecated
	public static Set<String> getPortletAddDefaultResourceCheckWhitelist() {
		return getPortal().getPortletAddDefaultResourceCheckWhitelist();
	}

	/**
	 * @deprecated As of 6.2.0 replaced by {@link
	 *             com.liferay.portal.security.auth.AuthTokenWhitelistUtil#getPortletInvocationWhitelistActions}
	 */
	@Deprecated
	public static Set<String>
		getPortletAddDefaultResourceCheckWhitelistActions() {

		return getPortal().getPortletAddDefaultResourceCheckWhitelistActions();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link
	 *             #getPortletBreadcrumbs(HttpServletRequest)}
	 */
	@Deprecated
	public static List<BreadcrumbEntry> getPortletBreadcrumbList(
		HttpServletRequest request) {

		return getPortal().getPortletBreadcrumbList(request);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbUtil#getPortletBreadcrumbEntries(
	 *             HttpServletRequest)}
	 */
	@Deprecated
	public static List<BreadcrumbEntry> getPortletBreadcrumbs(
		HttpServletRequest request) {

		return getPortal().getPortletBreadcrumbs(request);
	}

	public static PortletConfig getPortletConfig(
			long companyId, String portletId, ServletContext servletContext)
		throws PortletException {

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			companyId, portletId);

		InvokerPortlet invokerPortlet = PortletInstanceFactoryUtil.create(
			portlet, servletContext);

		return invokerPortlet.getPortletConfig();
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

	public static String getPortletLongTitle(Portlet portlet, Locale locale) {
		return getPortal().getPortletLongTitle(portlet, locale);
	}

	public static String getPortletLongTitle(
		Portlet portlet, ServletContext servletContext, Locale locale) {

		return getPortal().getPortletLongTitle(portlet, servletContext, locale);
	}

	public static String getPortletLongTitle(
		Portlet portlet, String languageId) {

		return getPortal().getPortletLongTitle(portlet, languageId);
	}

	public static String getPortletLongTitle(Portlet portlet, User user) {
		return getPortal().getPortletLongTitle(portlet, user);
	}

	public static String getPortletLongTitle(String portletId, Locale locale) {
		return getPortal().getPortletLongTitle(portletId, locale);
	}

	public static String getPortletLongTitle(
		String portletId, String languageId) {

		return getPortal().getPortletLongTitle(portletId, languageId);
	}

	public static String getPortletLongTitle(String portletId, User user) {
		return getPortal().getPortletLongTitle(portletId, user);
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

	public static String getPortletTitle(PortletRequest portletRequest) {
		return getPortal().getPortletTitle(portletRequest);
	}

	public static String getPortletTitle(PortletResponse portletResponse) {
		return getPortal().getPortletTitle(portletResponse);
	}

	public static String getPortletTitle(String portletId, Locale locale) {
		return getPortal().getPortletTitle(portletId, locale);
	}

	public static String getPortletTitle(
		String portletId, ResourceBundle resourceBundle) {

		return getPortal().getPortletTitle(portletId, resourceBundle);
	}

	public static String getPortletTitle(String portletId, String languageId) {
		return getPortal().getPortletTitle(portletId, languageId);
	}

	public static String getPortletTitle(String portletId, User user) {
		return getPortal().getPortletTitle(portletId, user);
	}

	public static String getPortletXmlFileName() {
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
		throws PortalException {

		return getPortal().getRelativeHomeURL(request);
	}

	public static ResourceBundle getResourceBundle(Locale locale) {
		return getPortal().getResourceBundle(locale);
	}

	public static long getScopeGroupId(HttpServletRequest request)
		throws PortalException {

		return getPortal().getScopeGroupId(request);
	}

	public static long getScopeGroupId(
			HttpServletRequest request, String portletId)
		throws PortalException {

		return getPortal().getScopeGroupId(request, portletId);
	}

	public static long getScopeGroupId(
			HttpServletRequest request, String portletId,
			boolean checkStagingGroup)
		throws PortalException {

		return getPortal().getScopeGroupId(
			request, portletId, checkStagingGroup);
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
		throws PortalException {

		return getPortal().getScopeGroupId(portletRequest);
	}

	public static User getSelectedUser(HttpServletRequest request)
		throws PortalException {

		return getPortal().getSelectedUser(request);
	}

	public static User getSelectedUser(
			HttpServletRequest request, boolean checkPermission)
		throws PortalException {

		return getPortal().getSelectedUser(request, checkPermission);
	}

	public static User getSelectedUser(PortletRequest portletRequest)
		throws PortalException {

		return getPortal().getSelectedUser(portletRequest);
	}

	public static User getSelectedUser(
			PortletRequest portletRequest, boolean checkPermission)
		throws PortalException {

		return getPortal().getSelectedUser(portletRequest, checkPermission);
	}

	public static String getServletContextName() {
		return getPortal().getServletContextName();
	}

	public static long[] getSharedContentSiteGroupIds(
			long companyId, long groupId, long userId)
		throws PortalException {

		return getPortal().getSharedContentSiteGroupIds(
			companyId, groupId, userId);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getControlPanelPortletURL(PortletRequest, Group, String,
	 *             long, String)}
	 */
	@Deprecated
	public static PortletURL getSiteAdministrationURL(
		HttpServletRequest request, ThemeDisplay themeDisplay,
		String portletId) {

		return getPortal().getSiteAdministrationURL(
			request, themeDisplay, portletId);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getControlPanelPortletURL(PortletRequest, Group, String,
	 *             long, String)}
	 */
	@Deprecated
	public static PortletURL getSiteAdministrationURL(
		PortletResponse portletResponse, ThemeDisplay themeDisplay,
		String portletName) {

		return getPortal().getSiteAdministrationURL(
			portletResponse, themeDisplay, portletName);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getCurrentAndAncestorSiteGroupIds(long)}
	 */
	@Deprecated
	public static long[] getSiteAndCompanyGroupIds(long groupId)
		throws PortalException {

		return getPortal().getSiteAndCompanyGroupIds(groupId);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getCurrentAndAncestorSiteGroupIds(long)}
	 */
	@Deprecated
	public static long[] getSiteAndCompanyGroupIds(ThemeDisplay themeDisplay)
		throws PortalException {

		return getPortal().getSiteAndCompanyGroupIds(themeDisplay);
	}

	public static Locale getSiteDefaultLocale(long groupId)
		throws PortalException {

		return getPortal().getSiteDefaultLocale(groupId);
	}

	public static long getSiteGroupId(long scopeGroupId) {
		return getPortal().getSiteGroupId(scopeGroupId);
	}

	public static String getSiteLoginURL(ThemeDisplay themeDisplay)
		throws PortalException {

		return getPortal().getSiteLoginURL(themeDisplay);
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

	public static String getUniqueElementId(
		HttpServletRequest request, String namespace, String id) {

		return getPortal().getUniqueElementId(request, namespace, id);
	}

	public static String getUniqueElementId(
		PortletRequest request, String namespace, String id) {

		return getPortal().getUniqueElementId(request, namespace, id);
	}

	public static UploadPortletRequest getUploadPortletRequest(
		PortletRequest portletRequest) {

		return getPortal().getUploadPortletRequest(portletRequest);
	}

	public static UploadServletRequest getUploadServletRequest(
		HttpServletRequest request) {

		return getPortal().getUploadServletRequest(request);
	}

	public static Date getUptime() {
		return getPortal().getUptime();
	}

	public static String getURLWithSessionId(String url, String sessionId) {
		return getPortal().getURLWithSessionId(url, sessionId);
	}

	public static User getUser(HttpServletRequest request)
		throws PortalException {

		return getPortal().getUser(request);
	}

	public static User getUser(PortletRequest portletRequest)
		throws PortalException {

		return getPortal().getUser(portletRequest);
	}

	public static String getUserEmailAddress(long userId) {
		return getPortal().getUserEmailAddress(userId);
	}

	public static long getUserId(HttpServletRequest request) {
		return getPortal().getUserId(request);
	}

	public static long getUserId(PortletRequest portletRequest) {
		return getPortal().getUserId(portletRequest);
	}

	public static String getUserName(BaseModel<?> baseModel) {
		return getPortal().getUserName(baseModel);
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

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static String getUserValue(
		long userId, String param, String defaultValue) {

		return getPortal().getUserValue(userId, param, defaultValue);
	}

	public static String getValidPortalDomain(long companyId, String domain) {
		return getPortal().getValidPortalDomain(companyId, domain);
	}

	public static long getValidUserId(long companyId, long userId)
		throws PortalException {

		return getPortal().getValidUserId(companyId, userId);
	}

	public static String getVirtualHostname(LayoutSet layoutSet) {
		return getPortal().getVirtualHostname(layoutSet);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static String getVirtualLayoutActualURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		return getPortal().getVirtualLayoutActualURL(
			groupId, privateLayout, mainPath, friendlyURL, params,
			requestContext);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static LayoutFriendlyURLComposite
		getVirtualLayoutFriendlyURLComposite(
			boolean privateLayout, String friendlyURL,
			Map<String, String[]> params, Map<String, Object> requestContext)
		throws PortalException {

		return getPortal().getVirtualLayoutFriendlyURLComposite(
			privateLayout, friendlyURL, params, requestContext);
	}

	public static String getWidgetURL(
			Portlet portlet, ThemeDisplay themeDisplay)
		throws PortalException {

		return getPortal().getWidgetURL(portlet, themeDisplay);
	}

	public static void initCustomSQL() {
		getPortal().initCustomSQL();
	}

	public static User initUser(HttpServletRequest request) throws Exception {
		return getPortal().initUser(request);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static void invokeTaglibDiscussion(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		getPortal().invokeTaglibDiscussion(
			portletConfig, actionRequest, actionResponse);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static void invokeTaglibDiscussionPagination(
			PortletConfig portletConfig, ResourceRequest resourceRequest,
			ResourceResponse resourceResponse)
		throws IOException, PortletException {

		getPortal().invokeTaglibDiscussionPagination(
			portletConfig, resourceRequest, resourceResponse);
	}

	/**
	 * @deprecated As of 6.2.0, with no direct replacement
	 */
	@Deprecated
	public static boolean isAllowAddPortletDefaultResource(
			HttpServletRequest request, Portlet portlet)
		throws PortalException {

		return getPortal().isAllowAddPortletDefaultResource(request, portlet);
	}

	public static boolean isCDNDynamicResourcesEnabled(
			HttpServletRequest request)
		throws PortalException {

		return getPortal().isCDNDynamicResourcesEnabled(request);
	}

	public static boolean isCDNDynamicResourcesEnabled(long companyId) {
		return getPortal().isCDNDynamicResourcesEnabled(companyId);
	}

	/**
	 * @deprecated As of 6.1.0, renamed to {@link #isGroupAdmin(User, long)}
	 */
	@Deprecated
	public static boolean isCommunityAdmin(User user, long groupId)
		throws Exception {

		return getPortal().isCommunityAdmin(user, groupId);
	}

	/**
	 * @deprecated As of 6.1.0, renamed to {@link #isGroupOwner(User, long)}
	 */
	@Deprecated
	public static boolean isCommunityOwner(User user, long groupId)
		throws Exception {

		return getPortal().isCommunityOwner(user, groupId);
	}

	public static boolean isCompanyAdmin(User user) throws Exception {
		return getPortal().isCompanyAdmin(user);
	}

	public static boolean isCompanyControlPanelPortlet(
			String portletId, String category, ThemeDisplay themeDisplay)
		throws PortalException {

		return getPortal().isCompanyControlPanelPortlet(
			portletId, category, themeDisplay);
	}

	public static boolean isCompanyControlPanelPortlet(
			String portletId, ThemeDisplay themeDisplay)
		throws PortalException {

		return getPortal().isCompanyControlPanelPortlet(
			portletId, themeDisplay);
	}

	public static boolean isControlPanelPortlet(
		String portletId, String category, ThemeDisplay themeDisplay) {

		return getPortal().isControlPanelPortlet(
			portletId, category, themeDisplay);
	}

	public static boolean isControlPanelPortlet(
		String portletId, ThemeDisplay themeDisplay) {

		return getPortal().isControlPanelPortlet(portletId, themeDisplay);
	}

	public static boolean isGroupAdmin(User user, long groupId)
		throws Exception {

		return getPortal().isGroupAdmin(user, groupId);
	}

	public static boolean isGroupFriendlyURL(
		String fullURL, String groupFriendlyURL, String layoutFriendlyURL) {

		return getPortal().isGroupFriendlyURL(
			fullURL, groupFriendlyURL, layoutFriendlyURL);
	}

	public static boolean isGroupOwner(User user, long groupId)
		throws Exception {

		return getPortal().isGroupOwner(user, groupId);
	}

	public static boolean isLayoutDescendant(Layout layout, long layoutId)
		throws PortalException {

		return getPortal().isLayoutDescendant(layout, layoutId);
	}

	public static boolean isLayoutSitemapable(Layout layout) {
		return getPortal().isLayoutSitemapable(layout);
	}

	public static boolean isLoginRedirectRequired(HttpServletRequest request) {
		return getPortal().isLoginRedirectRequired(request);
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

	public static boolean isOmniadmin(User user) {
		return getPortal().isOmniadmin(user);
	}

	public static boolean isReservedParameter(String name) {
		return getPortal().isReservedParameter(name);
	}

	public static boolean isRightToLeft(HttpServletRequest request) {
		return getPortal().isRightToLeft(request);
	}

	public static boolean isRSSFeedsEnabled() {
		return getPortal().isRSSFeedsEnabled();
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

	public static boolean isUpdateAvailable() {
		return getPortal().isUpdateAvailable();
	}

	public static boolean isValidResourceId(String resourceId) {
		return getPortal().isValidResourceId(resourceId);
	}

	public static boolean removePortalEventListener(
		PortalInetSocketAddressEventListener
			portalInetSocketAddressEventListener) {

		return getPortal().removePortalInetSocketAddressEventListener(
			portalInetSocketAddressEventListener);
	}

	public static void resetCDNHosts() {
		getPortal().resetCDNHosts();
	}

	/**
	 * @deprecated As of 6.2.0 replaced by {@link
	 *             com.liferay.portal.security.auth.AuthTokenWhitelistUtil#resetPortletInvocationWhitelist}
	 */
	@Deprecated
	public static Set<String> resetPortletAddDefaultResourceCheckWhitelist() {
		return getPortal().resetPortletAddDefaultResourceCheckWhitelist();
	}

	/**
	 * @deprecated As of 6.2.0 replaced by {@link
	 *             com.liferay.portal.security.auth.AuthTokenWhitelistUtil#resetPortletInvocationWhitelistActions}
	 */
	@Deprecated
	public static Set<String>
		resetPortletAddDefaultResourceCheckWhitelistActions() {

		return getPortal().
			resetPortletAddDefaultResourceCheckWhitelistActions();
	}

	public static String resetPortletParameters(String url, String portletId) {
		return getPortal().resetPortletParameters(url, portletId);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link DB#runSQL(String)}
	 */
	@Deprecated
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

	public static void sendRSSFeedsDisabledError(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		getPortal().sendRSSFeedsDisabledError(request, response);
	}

	public static void sendRSSFeedsDisabledError(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws IOException, ServletException {

		getPortal().sendRSSFeedsDisabledError(portletRequest, portletResponse);
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
	public static void setPageTitle(String title, HttpServletRequest request) {
		getPortal().setPageTitle(title, request);
	}

	public static void setPortalInetSocketAddresses(
		HttpServletRequest request) {

		getPortal().setPortalInetSocketAddresses(request);
	}

	/**
	 * Sets the port obtained on the first request to the portal.
	 *
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #setPortalInetSocketAddresses(HttpServletRequest)}
	 */
	@Deprecated
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

	public static String transformSQL(String sql) {
		return getPortal().transformSQL(sql);
	}

	public static void updateImageId(
			BaseModel<?> baseModel, boolean hasImage, byte[] bytes,
			String fieldName, long maxSize, int maxHeight, int maxWidth)
		throws PortalException {

		getPortal().updateImageId(
			baseModel, hasImage, bytes, fieldName, maxSize, maxHeight,
			maxWidth);
	}

	public static PortletMode updatePortletMode(
			String portletId, User user, Layout layout, PortletMode portletMode,
			HttpServletRequest request)
		throws PortalException {

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

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #removePortalEventListener(
	 *             PortalInetSocketAddressEventListener)}
	 */
	@Deprecated
	public void removePortalPortEventListener(
		PortalPortEventListener portalPortEventListener) {

		getPortal().removePortalPortEventListener(portalPortEventListener);
	}

	public void setPortal(Portal portal) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_portal = portal;
	}

	private static Portal _portal;

}