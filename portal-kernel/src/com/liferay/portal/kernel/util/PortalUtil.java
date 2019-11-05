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

package com.liferay.portal.kernel.util;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutQueryStringComposite;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.InvokerPortlet;
import com.liferay.portal.kernel.portlet.LayoutFriendlyURLSeparatorComposite;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletInstanceFactoryUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.upload.UploadServletRequest;

import java.io.IOException;
import java.io.Serializable;

import java.net.InetAddress;

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
public class PortalUtil {

	/**
	 * Appends the description to the current meta description of the page in
	 * the request.
	 *
	 * @param description the description to append to the current meta
	 *        description
	 * @param httpServletRequest the servlet request for the page
	 */
	public static void addPageDescription(
		String description, HttpServletRequest httpServletRequest) {

		getPortal().addPageDescription(description, httpServletRequest);
	}

	/**
	 * Appends the keywords to the current meta keywords of the page in the
	 * request.
	 *
	 * @param keywords the keywords to add to the current meta keywords
	 *        (comma-separated)
	 * @param httpServletRequest the servlet request for the page
	 */
	public static void addPageKeywords(
		String keywords, HttpServletRequest httpServletRequest) {

		getPortal().addPageKeywords(keywords, httpServletRequest);
	}

	/**
	 * Appends the subtitle to the current subtitle of the page in the request.
	 *
	 * @param subtitle the subtitle to append to the current subtitle
	 * @param httpServletRequest the servlet request for the page
	 */
	public static void addPageSubtitle(
		String subtitle, HttpServletRequest httpServletRequest) {

		getPortal().addPageSubtitle(subtitle, httpServletRequest);
	}

	/**
	 * Appends the title to the current title of the page in the request.
	 *
	 * @param title the title to append to the current title
	 * @param httpServletRequest the servlet request for the page
	 */
	public static void addPageTitle(
		String title, HttpServletRequest httpServletRequest) {

		getPortal().addPageTitle(title, httpServletRequest);
	}

	public static boolean addPortalInetSocketAddressEventListener(
		PortalInetSocketAddressEventListener
			portalInetSocketAddressEventListener) {

		return getPortal().addPortalInetSocketAddressEventListener(
			portalInetSocketAddressEventListener);
	}

	/**
	 * Adds an entry to the portlet breadcrumbs for the page in the request.
	 *
	 * @param httpServletRequest the servlet request for the page
	 * @param title the title of the new breadcrumb entry
	 * @param url the URL of the new breadcrumb entry
	 */
	public static void addPortletBreadcrumbEntry(
		HttpServletRequest httpServletRequest, String title, String url) {

		getPortal().addPortletBreadcrumbEntry(httpServletRequest, title, url);
	}

	/**
	 * Adds an entry to the portlet breadcrumbs for the page in the request.
	 *
	 * @param httpServletRequest the servlet request for the page
	 * @param title the title of the new breadcrumb entry
	 * @param url the URL of the new breadcrumb entry
	 * @param data the HTML5 data parameters of the new breadcrumb entry
	 */
	public static void addPortletBreadcrumbEntry(
		HttpServletRequest httpServletRequest, String title, String url,
		Map<String, Object> data) {

		getPortal().addPortletBreadcrumbEntry(
			httpServletRequest, title, url, data);
	}

	/**
	 * Adds an entry to the portlet breadcrumbs for the page in the request.
	 *
	 * @param httpServletRequest the servlet request for the page
	 * @param title the title of the new breadcrumb entry
	 * @param url the URL of the new breadcrumb entry
	 * @param data the HTML5 data parameters of the new breadcrumb entry
	 * @param portletBreadcrumbEntry whether the entry is a portlet breadcrumb
	 *        entry
	 */
	public static void addPortletBreadcrumbEntry(
		HttpServletRequest httpServletRequest, String title, String url,
		Map<String, Object> data, boolean portletBreadcrumbEntry) {

		getPortal().addPortletBreadcrumbEntry(
			httpServletRequest, title, url, data, portletBreadcrumbEntry);
	}

	/**
	 * Adds the default resource permissions for the portlet to the page in the
	 * request.
	 *
	 * @param  httpServletRequest the servlet request for the page
	 * @param  portlet the portlet
	 * @throws PortalException if a portal exception occurred
	 */
	public static void addPortletDefaultResource(
			HttpServletRequest httpServletRequest, Portlet portlet)
		throws PortalException {

		getPortal().addPortletDefaultResource(httpServletRequest, portlet);
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

	public static String addPreservedParameters(
		ThemeDisplay themeDisplay, String url, boolean typeControlPanel,
		boolean doAsUser) {

		return getPortal().addPreservedParameters(
			themeDisplay, url, typeControlPanel, doAsUser);
	}

	public static void addUserLocaleOptionsMessage(
		HttpServletRequest httpServletRequest) {

		getPortal().addUserLocaleOptionsMessage(httpServletRequest);
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
	 * @param  httpServletRequest the servlet request for the page
	 * @param  input the input string
	 * @return the generated key
	 */
	public static String generateRandomKey(
		HttpServletRequest httpServletRequest, String input) {

		return getPortal().generateRandomKey(httpServletRequest, input);
	}

	public static String getAbsoluteURL(
		HttpServletRequest httpServletRequest, String url) {

		return getPortal().getAbsoluteURL(httpServletRequest, url);
	}

	public static LayoutQueryStringComposite
			getActualLayoutQueryStringComposite(
				long groupId, boolean privateLayout, String friendlyURL,
				Map<String, String[]> params,
				Map<String, Object> requestContext)
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
	 * @throws PortalException if a portal exception occurred
	 */
	public static String getAlternateURL(
			String canonicalURL, ThemeDisplay themeDisplay, Locale locale,
			Layout layout)
		throws PortalException {

		return getPortal().getAlternateURL(
			canonicalURL, themeDisplay, locale, layout);
	}

	public static Map<Locale, String> getAlternateURLs(
			String canonicalURL, ThemeDisplay themeDisplay, Layout layout)
		throws PortalException {

		return getPortal().getAlternateURLs(canonicalURL, themeDisplay, layout);
	}

	public static long[] getAncestorSiteGroupIds(long groupId) {
		return getPortal().getAncestorSiteGroupIds(groupId);
	}

	/**
	 * Returns the base model instance for the resource permission.
	 *
	 * @param  resourcePermission the resource permission
	 * @return the base model instance, or <code>null</code> if the resource
	 *         permission does not have a base model instance (such as if it's a
	 *         portlet)
	 * @throws PortalException if a portal exception occurred
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
	 * @throws PortalException if a portal exception occurred
	 */
	public static BaseModel<?> getBaseModel(String modelName, String primKey)
		throws PortalException {

		return getPortal().getBaseModel(modelName, primKey);
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
	 * @throws PortalException if a portal exception occurred
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
	 * @throws PortalException if a portal exception occurred
	 */
	public static String getCanonicalURL(
			String completeURL, ThemeDisplay themeDisplay, Layout layout,
			boolean forceLayoutFriendlyURL)
		throws PortalException {

		return getPortal().getCanonicalURL(
			completeURL, themeDisplay, layout, forceLayoutFriendlyURL);
	}

	/**
	 * Returns the canonical URL of the page. The canonical URL is often used to
	 * distinguish a preferred page from its translations.
	 *
	 * <p>
	 * A page's canonical URL is the preferred URL to specify for a set of pages
	 * with similar or identical content. The canonical URL is used to inform
	 * search engines that several URLs point to the same page. It is also used
	 * to generate the URLs for site maps, the URLs that social bookmarks
	 * publish (Twitter, Facebook links, etc.), and the URLs in sent email. For
	 * more information, see <a
	 * href="https://support.google.com/webmasters/answer/139394?hl=en">https://support.google.com/webmasters/answer/139394?hl=en</a>.
	 * </p>
	 *
	 * @param  completeURL the complete URL of the page
	 * @param  themeDisplay the theme display
	 * @param  layout the page being requested (optionally <code>null</code>).
	 *         If <code>null</code> is specified, the current page is used.
	 * @param  forceLayoutFriendlyURL whether to add the page's friendly URL to
	 *         the canonical URL
	 * @param  includeQueryString whether to add the URL query string to the
	 *         canonical URL
	 * @return the canonical URL
	 * @throws PortalException if a portal exception occurred
	 */
	public static String getCanonicalURL(
			String completeURL, ThemeDisplay themeDisplay, Layout layout,
			boolean forceLayoutFriendlyURL, boolean includeQueryString)
		throws PortalException {

		return getPortal().getCanonicalURL(
			completeURL, themeDisplay, layout, forceLayoutFriendlyURL,
			includeQueryString);
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

	public static String getCDNHost(HttpServletRequest httpServletRequest)
		throws PortalException {

		return getPortal().getCDNHost(httpServletRequest);
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

	public static Company getCompany(HttpServletRequest httpServletRequest)
		throws PortalException {

		return getPortal().getCompany(httpServletRequest);
	}

	public static Company getCompany(PortletRequest portletRequest)
		throws PortalException {

		return getPortal().getCompany(portletRequest);
	}

	public static long getCompanyId(HttpServletRequest httpServletRequest) {
		return getPortal().getCompanyId(httpServletRequest);
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
		HttpServletRequest httpServletRequest, Group group, String portletId,
		long refererGroupId, long refererPlid, String lifecycle) {

		return getPortal().getControlPanelPortletURL(
			httpServletRequest, group, portletId, refererGroupId, refererPlid,
			lifecycle);
	}

	public static PortletURL getControlPanelPortletURL(
		HttpServletRequest httpServletRequest, String portletId,
		String lifecycle) {

		return getPortal().getControlPanelPortletURL(
			httpServletRequest, portletId, lifecycle);
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
			HttpServletRequest httpServletRequest, ThemeDisplay themeDisplay)
		throws Exception {

		return getPortal().getCreateAccountURL(
			httpServletRequest, themeDisplay);
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

	public static String getCurrentCompleteURL(
		HttpServletRequest httpServletRequest) {

		return getPortal().getCurrentCompleteURL(httpServletRequest);
	}

	public static String getCurrentURL(HttpServletRequest httpServletRequest) {
		return getPortal().getCurrentURL(httpServletRequest);
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
	 * @throws PortalException if a portal exception occurred
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
	 * @throws PortalException if a portal exception occurred
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
	 * @throws PortalException if a portal exception occurred
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
	 * @throws PortalException if a portal exception occurred
	 */
	public static Date getDate(
			int month, int day, int year, TimeZone timeZone,
			Class<? extends PortalException> clazz)
		throws PortalException {

		return getPortal().getDate(month, day, year, timeZone, clazz);
	}

	public static long getDefaultCompanyId() {
		return getPortal().getDefaultCompanyId();
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
			ExpandoBridge expandoBridge, HttpServletRequest httpServletRequest)
		throws PortalException {

		return getPortal().getExpandoBridgeAttributes(
			expandoBridge, httpServletRequest);
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
			HttpServletRequest httpServletRequest, String name, int type,
			String displayType)
		throws PortalException {

		return getPortal().getExpandoValue(
			httpServletRequest, name, type, displayType);
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

	public static String getFirstPageLayoutTypes(
		HttpServletRequest httpServletRequest) {

		return getPortal().getFirstPageLayoutTypes(httpServletRequest);
	}

	public static String getForwardedHost(
		HttpServletRequest httpServletRequest) {

		return getPortal().getForwardedHost(httpServletRequest);
	}

	public static int getForwardedPort(HttpServletRequest httpServletRequest) {
		return getPortal().getForwardedPort(httpServletRequest);
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

	public static String getHomeURL(HttpServletRequest httpServletRequest)
		throws PortalException {

		return getPortal().getHomeURL(httpServletRequest);
	}

	public static String getHost(HttpServletRequest httpServletRequest) {
		return getPortal().getHost(httpServletRequest);
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

	public static String getLayoutFriendlyURL(ThemeDisplay themeDisplay)
		throws PortalException {

		return getPortal().getLayoutFriendlyURL(themeDisplay);
	}

	public static LayoutFriendlyURLSeparatorComposite
			getLayoutFriendlyURLSeparatorComposite(
				long groupId, boolean privateLayout, String friendlyURL,
				Map<String, String[]> params,
				Map<String, Object> requestContext)
		throws PortalException {

		return getPortal().getLayoutFriendlyURLSeparatorComposite(
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

	public static String getLayoutSetDisplayURL(
			LayoutSet layoutSet, boolean secureConnection)
		throws PortalException {

		return getPortal().getLayoutSetDisplayURL(layoutSet, secureConnection);
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

	public static Locale getLocale(HttpServletRequest httpServletRequest) {
		return getPortal().getLocale(httpServletRequest);
	}

	public static Locale getLocale(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, boolean initialize) {

		return getPortal().getLocale(
			httpServletRequest, httpServletResponse, initialize);
	}

	public static Locale getLocale(PortletRequest portletRequest) {
		return getPortal().getLocale(portletRequest);
	}

	public static String getLocalizedFriendlyURL(
		HttpServletRequest httpServletRequest, Layout layout, Locale locale,
		Locale originalLocale) {

		return getPortal().getLocalizedFriendlyURL(
			httpServletRequest, layout, locale, originalLocale);
	}

	public static String getMailId(
		String mx, String popPortletPrefix, Object... ids) {

		return getPortal().getMailId(mx, popPortletPrefix, ids);
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
		HttpServletRequest httpServletRequest) {

		return getPortal().getOriginalServletRequest(httpServletRequest);
	}

	public static String getPathContext() {
		return getPortal().getPathContext();
	}

	public static String getPathContext(HttpServletRequest httpServletRequest) {
		return getPortal().getPathContext(httpServletRequest);
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

	public static Properties getPortalProperties() {
		return getPortal().getPortalProperties();
	}

	public static InetAddress getPortalServerInetAddress(boolean secure) {
		return getPortal().getPortalServerInetAddress(secure);
	}

	public static int getPortalServerPort(boolean secure) {
		return getPortal().getPortalServerPort(secure);
	}

	public static String getPortalURL(HttpServletRequest httpServletRequest) {
		return getPortal().getPortalURL(httpServletRequest);
	}

	public static String getPortalURL(
		HttpServletRequest httpServletRequest, boolean secure) {

		return getPortal().getPortalURL(httpServletRequest, secure);
	}

	public static String getPortalURL(Layout layout, ThemeDisplay themeDisplay)
		throws PortalException {

		return getPortal().getPortalURL(layout, themeDisplay);
	}

	public static String getPortalURL(
		LayoutSet layoutSet, ThemeDisplay themeDisplay) {

		return getPortal().getPortalURL(layoutSet, themeDisplay);
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

	public static String getPortletId(HttpServletRequest httpServletRequest) {
		return getPortal().getPortletId(httpServletRequest);
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
		HttpServletRequest httpServletRequest) {

		return getPortal().getPreferences(httpServletRequest);
	}

	public static PreferencesValidator getPreferencesValidator(
		Portlet portlet) {

		return getPortal().getPreferencesValidator(portlet);
	}

	public static String getRelativeHomeURL(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		return getPortal().getRelativeHomeURL(httpServletRequest);
	}

	public static ResourceBundle getResourceBundle(Locale locale) {
		return getPortal().getResourceBundle(locale);
	}

	public static long getScopeGroupId(HttpServletRequest httpServletRequest)
		throws PortalException {

		return getPortal().getScopeGroupId(httpServletRequest);
	}

	public static long getScopeGroupId(
			HttpServletRequest httpServletRequest, String portletId)
		throws PortalException {

		return getPortal().getScopeGroupId(httpServletRequest, portletId);
	}

	public static long getScopeGroupId(
			HttpServletRequest httpServletRequest, String portletId,
			boolean checkStagingGroup)
		throws PortalException {

		return getPortal().getScopeGroupId(
			httpServletRequest, portletId, checkStagingGroup);
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

	public static User getSelectedUser(HttpServletRequest httpServletRequest)
		throws PortalException {

		return getPortal().getSelectedUser(httpServletRequest);
	}

	public static User getSelectedUser(
			HttpServletRequest httpServletRequest, boolean checkPermission)
		throws PortalException {

		return getPortal().getSelectedUser(httpServletRequest, checkPermission);
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

	public static String getSiteAdminURL(
			ThemeDisplay themeDisplay, String ppid,
			Map<String, String[]> params)
		throws PortalException {

		return getPortal().getSiteAdminURL(themeDisplay, ppid, params);
	}

	public static Locale getSiteDefaultLocale(Group group)
		throws PortalException {

		return getPortal().getSiteDefaultLocale(group);
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
		HttpServletRequest httpServletRequest, String uri) {

		return getPortal().getStaticResourceURL(httpServletRequest, uri);
	}

	public static String getStaticResourceURL(
		HttpServletRequest httpServletRequest, String uri, long timestamp) {

		return getPortal().getStaticResourceURL(
			httpServletRequest, uri, timestamp);
	}

	public static String getStaticResourceURL(
		HttpServletRequest httpServletRequest, String uri, String queryString) {

		return getPortal().getStaticResourceURL(
			httpServletRequest, uri, queryString);
	}

	public static String getStaticResourceURL(
		HttpServletRequest httpServletRequest, String uri, String queryString,
		long timestamp) {

		return getPortal().getStaticResourceURL(
			httpServletRequest, uri, queryString, timestamp);
	}

	public static String getStrutsAction(
		HttpServletRequest httpServletRequest) {

		return getPortal().getStrutsAction(httpServletRequest);
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
		HttpServletRequest httpServletRequest, String namespace, String id) {

		return getPortal().getUniqueElementId(
			httpServletRequest, namespace, id);
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
		HttpServletRequest httpServletRequest) {

		return getPortal().getUploadServletRequest(httpServletRequest);
	}

	public static UploadServletRequest getUploadServletRequest(
		HttpServletRequest httpServletRequest, int fileSizeThreshold,
		String location, long maxRequestSize, long maxFileSize) {

		return getPortal().getUploadServletRequest(
			httpServletRequest, fileSizeThreshold, location, maxRequestSize,
			maxFileSize);
	}

	public static Date getUptime() {
		return getPortal().getUptime();
	}

	public static String getURLWithSessionId(String url, String sessionId) {
		return getPortal().getURLWithSessionId(url, sessionId);
	}

	public static User getUser(HttpServletRequest httpServletRequest)
		throws PortalException {

		return getPortal().getUser(httpServletRequest);
	}

	public static User getUser(PortletRequest portletRequest)
		throws PortalException {

		return getPortal().getUser(portletRequest);
	}

	public static String getUserEmailAddress(long userId) {
		return getPortal().getUserEmailAddress(userId);
	}

	public static long getUserId(HttpServletRequest httpServletRequest) {
		return getPortal().getUserId(httpServletRequest);
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
		long userId, String defaultUserName,
		HttpServletRequest httpServletRequest) {

		return getPortal().getUserName(
			userId, defaultUserName, httpServletRequest);
	}

	public static String getUserName(
		long userId, String defaultUserName, String userAttribute) {

		return getPortal().getUserName(userId, defaultUserName, userAttribute);
	}

	public static String getUserName(
		long userId, String defaultUserName, String userAttribute,
		HttpServletRequest httpServletRequest) {

		return getPortal().getUserName(
			userId, defaultUserName, userAttribute, httpServletRequest);
	}

	public static String getUserPassword(
		HttpServletRequest httpServletRequest) {

		return getPortal().getUserPassword(httpServletRequest);
	}

	public static String getUserPassword(HttpSession session) {
		return getPortal().getUserPassword(session);
	}

	public static String getUserPassword(PortletRequest portletRequest) {
		return getPortal().getUserPassword(portletRequest);
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

	public static String getWidgetURL(
			Portlet portlet, ThemeDisplay themeDisplay)
		throws PortalException {

		return getPortal().getWidgetURL(portlet, themeDisplay);
	}

	public static void initCustomSQL() {
		getPortal().initCustomSQL();
	}

	public static User initUser(HttpServletRequest httpServletRequest)
		throws Exception {

		return getPortal().initUser(httpServletRequest);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), with no direct replacement
	 */
	@Deprecated
	public static void invokeTaglibDiscussionPagination(
			PortletConfig portletConfig, ResourceRequest resourceRequest,
			ResourceResponse resourceResponse)
		throws IOException, PortletException {

		getPortal().invokeTaglibDiscussionPagination(
			portletConfig, resourceRequest, resourceResponse);
	}

	public static boolean isCDNDynamicResourcesEnabled(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		return getPortal().isCDNDynamicResourcesEnabled(httpServletRequest);
	}

	public static boolean isCDNDynamicResourcesEnabled(long companyId) {
		return getPortal().isCDNDynamicResourcesEnabled(companyId);
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

	public static boolean isCustomPortletMode(PortletMode portletMode) {
		return getPortal().isCustomPortletMode(portletMode);
	}

	public static boolean isForwardedSecure(
		HttpServletRequest httpServletRequest) {

		return getPortal().isForwardedSecure(httpServletRequest);
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

	public static boolean isLoginRedirectRequired(
		HttpServletRequest httpServletRequest) {

		return getPortal().isLoginRedirectRequired(httpServletRequest);
	}

	public static boolean isMethodGet(PortletRequest portletRequest) {
		return getPortal().isMethodGet(portletRequest);
	}

	public static boolean isMethodPost(PortletRequest portletRequest) {
		return getPortal().isMethodPost(portletRequest);
	}

	public static boolean isMultipartRequest(
		HttpServletRequest httpServletRequest) {

		return getPortal().isMultipartRequest(httpServletRequest);
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

	public static boolean isRightToLeft(HttpServletRequest httpServletRequest) {
		return getPortal().isRightToLeft(httpServletRequest);
	}

	public static boolean isRSSFeedsEnabled() {
		return getPortal().isRSSFeedsEnabled();
	}

	public static boolean isSecure(HttpServletRequest httpServletRequest) {
		return getPortal().isSecure(httpServletRequest);
	}

	public static boolean isSkipPortletContentRendering(
		Group group, LayoutTypePortlet layoutTypePortlet,
		PortletDisplay portletDisplay, String portletName) {

		return getPortal().isSkipPortletContentRendering(
			group, layoutTypePortlet, portletDisplay, portletName);
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

	public static String resetPortletParameters(String url, String portletId) {
		return getPortal().resetPortletParameters(url, portletId);
	}

	public static void sendError(
			Exception e, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws IOException {

		getPortal().sendError(e, actionRequest, actionResponse);
	}

	public static void sendError(
			Exception e, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		getPortal().sendError(e, httpServletRequest, httpServletResponse);
	}

	public static void sendError(
			int status, Exception e, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws IOException {

		getPortal().sendError(status, e, actionRequest, actionResponse);
	}

	public static void sendError(
			int status, Exception e, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		getPortal().sendError(
			status, e, httpServletRequest, httpServletResponse);
	}

	public static void sendRSSFeedsDisabledError(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		getPortal().sendRSSFeedsDisabledError(
			httpServletRequest, httpServletResponse);
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
		String description, HttpServletRequest httpServletRequest) {

		getPortal().setPageDescription(description, httpServletRequest);
	}

	/**
	 * Sets the keywords for a page. This overrides the existing page keywords.
	 */
	public static void setPageKeywords(
		String keywords, HttpServletRequest httpServletRequest) {

		getPortal().setPageKeywords(keywords, httpServletRequest);
	}

	/**
	 * Sets the subtitle for a page. This overrides the existing page subtitle.
	 */
	public static void setPageSubtitle(
		String subtitle, HttpServletRequest httpServletRequest) {

		getPortal().setPageSubtitle(subtitle, httpServletRequest);
	}

	/**
	 * Sets the whole title for a page. This overrides the existing page whole
	 * title.
	 */
	public static void setPageTitle(
		String title, HttpServletRequest httpServletRequest) {

		getPortal().setPageTitle(title, httpServletRequest);
	}

	public static void setPortalInetSocketAddresses(
		HttpServletRequest httpServletRequest) {

		getPortal().setPortalInetSocketAddresses(httpServletRequest);
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
			HttpServletRequest httpServletRequest)
		throws PortalException {

		return getPortal().updatePortletMode(
			portletId, user, layout, portletMode, httpServletRequest);
	}

	public static String updateRedirect(
		String redirect, String oldPath, String newPath) {

		return getPortal().updateRedirect(redirect, oldPath, newPath);
	}

	public static WindowState updateWindowState(
		String portletId, User user, Layout layout, WindowState windowState,
		HttpServletRequest httpServletRequest) {

		return getPortal().updateWindowState(
			portletId, user, layout, windowState, httpServletRequest);
	}

	public void setPortal(Portal portal) {
		_portal = portal;
	}

	private static Portal _portal;

}