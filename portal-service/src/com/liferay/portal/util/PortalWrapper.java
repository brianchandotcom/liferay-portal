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

package com.liferay.portal.util;

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
import com.liferay.portal.model.LayoutFriendlyURLComposite;
import com.liferay.portal.model.LayoutQueryStringComposite;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.IOException;
import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
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
 * @author Neil Griffin
 * @author Kyle Stiemann
 */
public class PortalWrapper implements Portal {

	public PortalWrapper(Portal portal) {
		_portal = portal;
	}

	/**
	 * Appends the description to the current meta description of the page.
	 *
	 * @param description the description to append to the current meta
	 *        description
	 * @param request the servlet request for the page
	 */
	@Override
	public void addPageDescription(
		String description, HttpServletRequest request) {

		_portal.addPageDescription(description, request);
	}

	/**
	 * Appends the keywords to the current meta keywords of the page.
	 *
	 * @param keywords the keywords to add to the current meta keywords
	 *        (comma-separated)
	 * @param request the servlet request for the page
	 */
	@Override
	public void addPageKeywords(String keywords, HttpServletRequest request) {
		_portal.addPageKeywords(keywords, request);
	}

	/**
	 * Appends the subtitle to the current subtitle of the page.
	 *
	 * @param subtitle the subtitle to append to the current subtitle
	 * @param request the servlet request for the page
	 */
	@Override
	public void addPageSubtitle(String subtitle, HttpServletRequest request) {
		_portal.addPageSubtitle(subtitle, request);
	}

	/**
	 * Appends the title to the current title of the page.
	 *
	 * @param title the title to append to the current title
	 * @param request the servlet request for the page
	 */
	@Override
	public void addPageTitle(String title, HttpServletRequest request) {
		_portal.addPageTitle(title, request);
	}

	/**
	 * Adds the portal port event listener to the portal. The listener will be
	 * notified whenever the portal port is set.
	 *
	 * @param portalPortEventListener the portal port event listener to add
	 */
	@Override
	public void addPortalPortEventListener(
		PortalPortEventListener portalPortEventListener) {

		_portal.addPortalPortEventListener(portalPortEventListener);
	}

	/**
	 * Adds an entry to the portlet breadcrumbs for the page.
	 *
	 * @param request the servlet request for the page
	 * @param title the title of the new breakcrumb entry
	 * @param url the URL of the new breadcrumb entry
	 */
	@Override
	public void addPortletBreadcrumbEntry(
		HttpServletRequest request, String title, String url) {

		_portal.addPortletBreadcrumbEntry(request, title, url);
	}

	/**
	 * Adds an entry to the portlet breadcrumbs for the page.
	 *
	 * @param request the servlet request for the page
	 * @param title the title of the new breakcrumb entry
	 * @param url the URL of the new breadcrumb entry
	 * @param data the HTML5 data parameters of the new breadcrumb entry
	 */
	@Override
	public void addPortletBreadcrumbEntry(
		HttpServletRequest request, String title, String url,
		Map<String, Object> data) {

		_portal.addPortletBreadcrumbEntry(request, title, url, data);
	}

	/**
	 * Adds the default resource permissions for the portlet to the page.
	 *
	 * @param  request the servlet request for the page
	 * @param  portlet the portlet
	 * @throws PortalException if adding the default resource permissions failed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addPortletDefaultResource(
			HttpServletRequest request, Portlet portlet)
		throws PortalException, SystemException {

		_portal.addPortletDefaultResource(request, portlet);
	}

	@Override
	public void addPortletDefaultResource(
			long companyId, Layout layout, Portlet portlet)
		throws PortalException, SystemException {

		_portal.addPortletDefaultResource(companyId, layout, portlet);
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
	 * @param  layout the current layout
	 * @param  url the URL
	 * @param  doAsUser whether to include doAsUserId and doAsLanguageId in the
	 *         URL if they are available. If <code>false</code>, doAsUserId and
	 *         doAsUserLanguageId will never be added.
	 * @return the URL with the preserved parameters added
	 */
	@Override
	public String addPreservedParameters(
		ThemeDisplay themeDisplay, Layout layout, String url,
		boolean doAsUser) {

		return _portal.addPreservedParameters(
			themeDisplay, layout, url, doAsUser);
	}

	/**
	 * Adds the preserved parameters doAsUserId, doAsUserLanguageId,
	 * doAsGroupId, refererPlid, and controlPanelCategory to the URL.
	 *
	 * @param  themeDisplay the current theme display
	 * @param  url the URL
	 * @return the URL with the preserved parameters added
	 */
	@Override
	public String addPreservedParameters(
		ThemeDisplay themeDisplay, String url) {

		return _portal.addPreservedParameters(themeDisplay, url);
	}

	@Override
	public void addUserLocaleOptionsMessage(HttpServletRequest request) {
		_portal.addUserLocaleOptionsMessage(request);
	}

	/**
	 * Clears the render parameters in the request if the portlet is in the
	 * action phase.
	 *
	 * @param renderRequest the render request
	 */
	@Override
	public void clearRequestParameters(RenderRequest renderRequest) {
		_portal.clearRequestParameters(renderRequest);
	}

	/**
	 * Copies the request parameters to the render parameters, unless a
	 * parameter with that name already exists in the render parameters.
	 *
	 * @param actionRequest the request from which to get the request parameters
	 * @param actionResponse the response to receive the render parameters
	 */
	@Override
	public void copyRequestParameters(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		_portal.copyRequestParameters(actionRequest, actionResponse);
	}

	@Override
	public boolean equals(Object obj) {
		return _portal.equals(obj);
	}

	/**
	 * Escapes the URL for use in a redirect and checks that security settings
	 * allow the URL is allowed for redirects.
	 *
	 * @param  url the URL to escape
	 * @return the escaped URL, or <code>null</code> if the URL is not an
	 *         allowed for redirects
	 */
	@Override
	public String escapeRedirect(String url) {
		return _portal.escapeRedirect(url);
	}

	/**
	 * Generates a random key to identify the request based on the input string.
	 *
	 * @param  request the servlet request for the page
	 * @param  input the input string
	 * @return the generated key
	 */
	@Override
	public String generateRandomKey(HttpServletRequest request, String input) {
		return _portal.generateRandomKey(request, input);
	}

	@Override
	public String getAbsoluteURL(HttpServletRequest request, String url) {
		return _portal.getAbsoluteURL(request, url);
	}

	@Override
	public LayoutQueryStringComposite getActualLayoutQueryStringComposite(
			long groupId, boolean privateLayout, String friendlyURL,
			Map<String, String[]> params, Map<String, Object> requestContext)
		throws PortalException, SystemException {

		return _portal.getActualLayoutQueryStringComposite(
			groupId, privateLayout, friendlyURL, params, requestContext);
	}

	@Override public String getActualURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException, SystemException {

		return _portal.getActualURL(
			groupId, privateLayout, mainPath, friendlyURL, params,
			requestContext);
	}

	/**
	 * Returns an array with the alternate locales, considering if the page is
	 * showing just a content and the translations of this content.
	 *
	 * @param      request the servlet request for the page
	 * @return     the array of alternate locales
	 * @throws     PortalException if a portal exception occurred
	 * @throws     SystemException if a system exception occurred
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portal.kernel.language.LanguageUtil#getAvailableLocales(
	 *             )}
	 */
	@Override
	public Locale[] getAlternateLocales(HttpServletRequest request)
		throws PortalException, SystemException {

		return _portal.getAlternateLocales(request);
	}

	/**
	 * Returns the alternate URL of the page, to distinguish it from its
	 * canonical URL.
	 *
	 * @param  canonicalURL the canonical URL previously obtained
	 * @param  themeDisplay the theme display
	 * @param  locale the locale of the translated page
	 * @param  layout the layout
	 * @return the alternate URL
	 */
	@Override
	public String getAlternateURL(
		String canonicalURL, ThemeDisplay themeDisplay, Locale locale,
		Layout layout) {

		return _portal.getAlternateURL(
			canonicalURL, themeDisplay, locale, layout);
	}

	/**
	 * Returns the set of struts actions that should not be checked for an
	 * authentication token.
	 *
	 * @return     the set of struts actions that should not be checked for an
	 *             authentication token
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portal.security.auth.AuthTokenWhitelistUtil#getPortletCSRFWhitelistActions(
	 *             )}
	 */
	@Override
	public Set<String> getAuthTokenIgnoreActions() {
		return _portal.getAuthTokenIgnoreActions();
	}

	/**
	 * Returns the set of IDs of portlets that should not be checked for an
	 * authentication token.
	 *
	 * @return     the set of IDs of portlets that should not be checked for an
	 *             authentication token
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portal.security.auth.AuthTokenWhitelistUtil#getPortletCSRFWhitelist(
	 *             )}
	 */
	@Override
	public Set<String> getAuthTokenIgnorePortlets() {
		return _portal.getAuthTokenIgnorePortlets();
	}

	/**
	 * Returns the base model instance for the resource permission.
	 *
	 * @param  resourcePermission the resource permission
	 * @return the base model instance, or <code>null</code> if the resource
	 *         permission does not have a base model instance (such as if its a
	 *         portlet)
	 * @throws PortalException if a base model instance for the resource
	 *         permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public BaseModel<?> getBaseModel(ResourcePermission resourcePermission)
		throws PortalException, SystemException {

		return _portal.getBaseModel(resourcePermission);
	}

	/**
	 * Returns the base model instance for the model name and primary key.
	 *
	 * @param  modelName the fully qualified class name of the model
	 * @param  primKey the primary key of the model instance to get
	 * @return the base model instance, or <code>null</code> if the model does
	 *         not have a base model instance (such as if its a portlet)
	 * @throws PortalException if a base model instance with the primary key
	 *         could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public BaseModel<?> getBaseModel(String modelName, String primKey)
		throws PortalException, SystemException {

		return _portal.getBaseModel(modelName, primKey);
	}

	/**
	 * Returns the user's ID from the HTTP authentication headers after
	 * validating their credentials.
	 *
	 * @param  request the servlet request from which to retrieve the HTTP
	 *         authentication headers
	 * @return the user's ID if HTTP authentication headers are present and
	 *         their credentials are valid; 0 otherwise
	 * @throws PortalException if an authentication exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public long getBasicAuthUserId(HttpServletRequest request)
		throws PortalException, SystemException {

		return _portal.getBasicAuthUserId(request);
	}

	/**
	 * Returns the user's ID from the HTTP authentication headers after
	 * validation their credentials.
	 *
	 * @param  request the servlet request to retrieve the HTTP authentication
	 *         headers from
	 * @param  companyId unused
	 * @return the user's ID if HTTP authentication headers are present and
	 *         their credentials are valid; 0 otherwise
	 * @throws PortalException if an authentication exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public long getBasicAuthUserId(HttpServletRequest request, long companyId)
		throws PortalException, SystemException {

		return _portal.getBasicAuthUserId(request, companyId);
	}

	/**
	 * Returns the canonical URL of the page, to distinguish it among its
	 * translations.
	 *
	 * @param  completeURL the complete URL of the page
	 * @param  themeDisplay the current theme display
	 * @param  layout the layout. If it is <code>null</code>, then it is
	 *         generated for the current layout
	 * @return the canonical URL
	 * @throws PortalException if a friendly URL or the group could not be
	 *         retrieved
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public String getCanonicalURL(
			String completeURL, ThemeDisplay themeDisplay, Layout layout)
		throws PortalException, SystemException {

		return _portal.getCanonicalURL(completeURL, themeDisplay, layout);
	}

	/**
	 * Returns the canonical URL of the page, to distinguish it among its
	 * translations.
	 *
	 * @param  completeURL the complete URL of the page
	 * @param  themeDisplay the current theme display
	 * @param  layout the layout. If it is <code>null</code>, then it is
	 *         generated for the current layout
	 * @param  forceLayoutFriendlyURL adds the page friendly URL to the
	 *         canonical URL even if it is not needed
	 * @return the canonical URL
	 * @throws PortalException if a friendly URL or the group could not be
	 *         retrieved
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public String getCanonicalURL(
			String completeURL, ThemeDisplay themeDisplay, Layout layout,
			boolean forceLayoutFriendlyURL)
		throws PortalException, SystemException {

		return _portal.getCanonicalURL(
			completeURL, themeDisplay, layout, forceLayoutFriendlyURL);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by the more general {@link
	 *             #getCDNHost(boolean)}
	 */
	@Override
	public String getCDNHost() {
		return _portal.getCDNHost();
	}

	/**
	 * Returns the secure (HTTPS) or insecure (HTTP) content distribution
	 * network (CDN) host address for this portal.
	 *
	 * @param  secure whether to get the secure or insecure CDN host address
	 * @return the CDN host address
	 */
	@Override
	public String getCDNHost(boolean secure) {
		return _portal.getCDNHost(secure);
	}

	@Override public String getCDNHost(HttpServletRequest request)
		throws PortalException, SystemException {

		return _portal.getCDNHost(request);
	}

	/**
	 * Returns the insecure (HTTP) content distribution network (CDN) host
	 * address
	 *
	 * @param  companyId the company ID of a site
	 * @return the CDN host address
	 */
	@Override
	public String getCDNHostHttp(long companyId) {
		return _portal.getCDNHostHttp(companyId);
	}

	/**
	 * Returns the secure (HTTPS) content distribution network (CDN) host
	 * address
	 *
	 * @param  companyId the company ID of a site
	 * @return the CDN host address
	 */
	@Override
	public String getCDNHostHttps(long companyId) {
		return _portal.getCDNHostHttps(companyId);
	}

	/**
	 * Returns the fully qualified name of the class from its ID.
	 *
	 * @param  classNameId the ID of the class
	 * @return the fully qualified name of the class
	 */
	@Override
	public String getClassName(long classNameId) {
		return _portal.getClassName(classNameId);
	}

	/**
	 * Returns the ID of the class from its class object.
	 *
	 * @param  clazz the class object
	 * @return the ID of the class
	 */
	@Override
	public long getClassNameId(Class<?> clazz) {
		return _portal.getClassNameId(clazz);
	}

	/**
	 * Returns the ID of the class from its fully qualified name.
	 *
	 * @param  value the fully qualified name of the class
	 * @return the ID of the class
	 */
	@Override
	public long getClassNameId(String value) {
		return _portal.getClassNameId(value);
	}

	/**
	 * Returns the ID of certain portlets from the fully qualified name of one
	 * of their classes. The portlets this method supports are: blogs,
	 * bookmarks, calendar, document library, image gallery, journal, message
	 * boards, and wiki.
	 *
	 * @param  className the fully qualified name of a class in a portlet
	 * @return the ID of the portlet the class is a part of, or an empty string
	 *         if the class is not supported
	 */
	@Override
	public String getClassNamePortletId(String className) {
		return _portal.getClassNamePortletId(className);
	}

	@Override
	public Company getCompany(HttpServletRequest request)
		throws PortalException, SystemException {

		return _portal.getCompany(request);
	}

	@Override
	public Company getCompany(PortletRequest portletRequest)
		throws PortalException, SystemException {

		return _portal.getCompany(portletRequest);
	}

	@Override
	public long getCompanyId(HttpServletRequest requestuest) {
		return _portal.getCompanyId(requestuest);
	}

	@Override
	public long getCompanyId(PortletRequest portletRequest) {
		return _portal.getCompanyId(portletRequest);
	}

	@Override
	public long[] getCompanyIds() {
		return _portal.getCompanyIds();
	}

	@Override
	public String getComputerAddress() {
		return _portal.getComputerAddress();
	}

	@Override
	public String getComputerName() {
		return _portal.getComputerName();
	}

	@Override
	public Map<String, List<Portlet>> getControlPanelCategoriesMap(
			HttpServletRequest request)
		throws SystemException {

		return _portal.getControlPanelCategoriesMap(request);
	}

	@Override
	public String getControlPanelCategory(
			String portletId, ThemeDisplay themeDisplay)
		throws SystemException {

		return _portal.getControlPanelCategory(portletId, themeDisplay);
	}

	@Override
	public String getControlPanelFullURL(
			long scopeGroupId, String ppid, Map<String, String[]> params)
		throws PortalException, SystemException {

		return _portal.getControlPanelFullURL(scopeGroupId, ppid, params);
	}

	@Override
	public long getControlPanelPlid(long companyId)
		throws PortalException, SystemException {

		return _portal.getControlPanelPlid(companyId);
	}

	@Override
	public long getControlPanelPlid(PortletRequest portletRequest)
		throws PortalException, SystemException {

		return _portal.getControlPanelPlid(portletRequest);
	}

	@Override
	public Set<Portlet> getControlPanelPortlets(long companyId, String category)
		throws SystemException {

		return _portal.getControlPanelPortlets(companyId, category);
	}

	@Override
	public List<Portlet> getControlPanelPortlets(
			String category, ThemeDisplay themeDisplay)
		throws SystemException {

		return _portal.getControlPanelPortlets(category, themeDisplay);
	}

	@Override
	public PortletURL getControlPanelPortletURL(
		HttpServletRequest request, String portletId, long referrerPlid,
		String lifecycle) {

		return _portal.getControlPanelPortletURL(
			request, portletId, referrerPlid, lifecycle);
	}

	@Override
	public PortletURL getControlPanelPortletURL(
		PortletRequest portletRequest, String portletId, long referrerPlid,
		String lifecycle) {

		return _portal.getControlPanelPortletURL(
			portletRequest, portletId, referrerPlid, lifecycle);
	}

	@Override
	public String getCreateAccountURL(
			HttpServletRequest request, ThemeDisplay themeDisplay)
		throws Exception {

		return _portal.getCreateAccountURL(request, themeDisplay);
	}

	@Override
	public String getCurrentCompleteURL(HttpServletRequest request) {
		return _portal.getCurrentCompleteURL(request);
	}

	@Override
	public String getCurrentURL(HttpServletRequest request) {
		return _portal.getCurrentURL(request);
	}

	@Override
	public String getCurrentURL(PortletRequest portletRequest) {
		return _portal.getCurrentURL(portletRequest);
	}

	@Override
	public String getCustomSQLFunctionIsNotNull() {
		return _portal.getCustomSQLFunctionIsNotNull();
	}

	@Override
	public String getCustomSQLFunctionIsNull() {
		return _portal.getCustomSQLFunctionIsNull();
	}

	/**
	 * Returns the date object for the specified month, day, and year.
	 *
	 * @param  month the month (0-based, meaning 0 for January)
	 * @param  day the day of the month
	 * @param  year the year
	 * @return the date object
	 */
	@Override
	public Date getDate(int month, int day, int year) {
		return _portal.getDate(month, day, year);
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
	 * @throws PortalException if the date was invalid and <code>pe</code> was
	 *         not <code>null</code>
	 */
	@Override
	public Date getDate(
			int month, int day, int year,
			Class<? extends PortalException> clazz)
		throws PortalException {

		return _portal.getDate(month, day, year, clazz);
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
	 * @throws PortalException if the date was invalid and <code>pe</code> was
	 *         not <code>null</code>
	 */
	@Override
	public Date getDate(
			int month, int day, int year, int hour, int min,
			Class<? extends PortalException> clazz)
		throws PortalException {

		return _portal.getDate(month, day, year, hour, min, clazz);
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
	 * @throws PortalException if the date was invalid and <code>pe</code> was
	 *         not <code>null</code>
	 */
	@Override
	public Date getDate(
			int month, int day, int year, int hour, int min, TimeZone timeZone,
			Class<? extends PortalException> clazz)
		throws PortalException {

		return _portal.getDate(month, day, year, hour, min, timeZone, clazz);
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
	 * @throws PortalException if the date was invalid and <code>pe</code> was
	 *         not <code>null</code>
	 */
	@Override
	public Date getDate(
			int month, int day, int year, TimeZone timeZone,
			Class<? extends PortalException> clazz)
		throws PortalException {

		return _portal.getDate(month, day, year, timeZone, clazz);
	}

	@Override
	public long getDefaultCompanyId() {
		return _portal.getDefaultCompanyId();
	}

	@Override
	public long getDigestAuthUserId(HttpServletRequest request)
		throws PortalException, SystemException {

		return _portal.getDigestAuthUserId(request);
	}

	@Override
	public String getEmailFromAddress(
			PortletPreferences preferences, long companyId, String defaultValue)
		throws SystemException {

		return _portal.getEmailFromAddress(
			preferences, companyId, defaultValue);
	}

	@Override
	public String getEmailFromName(
			PortletPreferences preferences, long companyId, String defaultValue)
		throws SystemException {

		return _portal.getEmailFromName(preferences, companyId, defaultValue);
	}

	@Override
	public Map<String, Serializable> getExpandoBridgeAttributes(
			ExpandoBridge expandoBridge, PortletRequest portletRequest)
		throws PortalException, SystemException {

		return _portal.getExpandoBridgeAttributes(
			expandoBridge, portletRequest);
	}

	@Override
	public Map<String, Serializable> getExpandoBridgeAttributes(
			ExpandoBridge expandoBridge,
			UploadPortletRequest uploadPortletRequest)
		throws PortalException, SystemException {

		return _portal.getExpandoBridgeAttributes(
			expandoBridge, uploadPortletRequest);
	}

	@Override
	public Serializable getExpandoValue(
			PortletRequest portletRequest, String name, int type,
			String displayType)
		throws PortalException, SystemException {

		return _portal.getExpandoValue(portletRequest, name, type, displayType);
	}

	@Override
	public Serializable getExpandoValue(
			UploadPortletRequest uploadPortletRequest, String name, int type,
			String displayType)
		throws PortalException, SystemException {

		return _portal.getExpandoValue(
			uploadPortletRequest, name, type, displayType);
	}

	@Override
	public String getFacebookURL(
			Portlet portlet, String facebookCanvasPageURL,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return _portal.getFacebookURL(
			portlet, facebookCanvasPageURL, themeDisplay);
	}

	@Override
	public Portlet getFirstMyAccountPortlet(ThemeDisplay themeDisplay)
		throws SystemException {

		return _portal.getFirstMyAccountPortlet(themeDisplay);
	}

	@Override
	public String getFirstPageLayoutTypes(PageContext pageContext) {
		return _portal.getFirstPageLayoutTypes(pageContext);
	}

	@Override
	public Portlet getFirstSiteAdministrationPortlet(ThemeDisplay themeDisplay)
		throws SystemException {

		return _portal.getFirstSiteAdministrationPortlet(themeDisplay);
	}

	@Override
	public String getFullName(
		String firstName, String middleName, String lastName) {

		return _portal.getFullName(firstName, middleName, lastName);
	}

	@Override
	public String getGlobalLibDir() {
		return _portal.getGlobalLibDir();
	}

	@Override
	public String getGoogleGadgetURL(Portlet portlet, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return _portal.getGoogleGadgetURL(portlet, themeDisplay);
	}

	@Override
	public String getGroupFriendlyURL(
			Group group, boolean privateLayoutSet, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return _portal.getGroupFriendlyURL(
			group, privateLayoutSet, themeDisplay);
	}

	@Override
	public String getGroupFriendlyURL(
			Group group, boolean privateLayoutSet, ThemeDisplay themeDisplay,
			Locale locale)
		throws PortalException, SystemException {

		return _portal.getGroupFriendlyURL(
			group, privateLayoutSet, themeDisplay, locale);
	}

	@Override
	public int[] getGroupFriendlyURLIndex(String requestURI) {
		return _portal.getGroupFriendlyURLIndex(requestURI);
	}

	@Override
	public String[] getGroupPermissions(HttpServletRequest request) {
		return _portal.getGroupPermissions(request);
	}

	@Override
	public String[] getGroupPermissions(
		HttpServletRequest request, String className) {

		return _portal.getGroupPermissions(request, className);
	}

	@Override
	public String[] getGroupPermissions(PortletRequest portletRequest) {
		return _portal.getGroupPermissions(portletRequest);
	}

	@Override
	public String[] getGroupPermissions(
		PortletRequest portletRequest, String className) {

		return _portal.getGroupPermissions(portletRequest, className);
	}

	@Override
	public String[] getGuestPermissions(HttpServletRequest request) {
		return _portal.getGuestPermissions(request);
	}

	@Override
	public String[] getGuestPermissions(
		HttpServletRequest request, String className) {

		return _portal.getGuestPermissions(request, className);
	}

	@Override
	public String[] getGuestPermissions(PortletRequest portletRequest) {
		return _portal.getGuestPermissions(portletRequest);
	}

	@Override
	public String[] getGuestPermissions(
		PortletRequest portletRequest, String className) {

		return _portal.getGuestPermissions(portletRequest, className);
	}

	@Override
	public String getHomeURL(HttpServletRequest request)
		throws PortalException, SystemException {

		return _portal.getHomeURL(request);
	}

	@Override
	public String getHost(HttpServletRequest request) {
		return _portal.getHost(request);
	}

	@Override
	public String getHost(PortletRequest portletRequest) {
		return _portal.getHost(portletRequest);
	}

	@Override
	public HttpServletRequest getHttpServletRequest(
		PortletRequest portletRequest) {

		return _portal.getHttpServletRequest(portletRequest);
	}

	@Override
	public HttpServletResponse getHttpServletResponse(
		PortletResponse portletResponse) {

		return _portal.getHttpServletResponse(portletResponse);
	}

	@Override
	public String getI18nPathLanguageId(
		Locale locale, String defaultI18nPathLanguageId) {

		return _portal.getI18nPathLanguageId(locale, defaultI18nPathLanguageId);
	}

	@Override
	public String getJournalArticleActualURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException, SystemException {

		return _portal.getJournalArticleActualURL(
			groupId, privateLayout, mainPath, friendlyURL, params,
			requestContext);
	}

	@Override
	public Layout getJournalArticleLayout(
			long groupId, boolean privateLayout, String friendlyURL)
		throws PortalException, SystemException {

		return _portal.getJournalArticleLayout(
			groupId, privateLayout, friendlyURL);
	}

	@Override
	public String getJsSafePortletId(String portletId) {
		return _portal.getJsSafePortletId(portletId);
	}

	@Override
	public String getLayoutActualURL(Layout layout) {
		return _portal.getLayoutActualURL(layout);
	}

	@Override
	public String getLayoutActualURL(Layout layout, String mainPath) {
		return _portal.getLayoutActualURL(layout, mainPath);
	}

	@Override
	public String getLayoutActualURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL)
		throws PortalException, SystemException {

		return _portal.getLayoutActualURL(
			groupId, privateLayout, mainPath, friendlyURL);
	}

	@Override
	public String getLayoutActualURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException, SystemException {

		return _portal.getLayoutActualURL(
			groupId, privateLayout, mainPath, friendlyURL, params,
			requestContext);
	}

	@Override
	public String getLayoutEditPage(Layout layout) {
		return _portal.getLayoutEditPage(layout);
	}

	@Override
	public String getLayoutEditPage(String type) {
		return _portal.getLayoutEditPage(type);
	}

	@Override
	public String getLayoutFriendlyURL(Layout layout, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return _portal.getLayoutFriendlyURL(layout, themeDisplay);
	}

	@Override
	public String getLayoutFriendlyURL(
			Layout layout, ThemeDisplay themeDisplay, Locale locale)
		throws PortalException, SystemException {

		return _portal.getLayoutFriendlyURL(layout, themeDisplay, locale);
	}

	@Override
	public LayoutFriendlyURLComposite getLayoutFriendlyURLComposite(
			long groupId, boolean privateLayout, String friendlyURL,
			Map<String, String[]> params, Map<String, Object> requestContext)
		throws PortalException, SystemException {

		return _portal.getLayoutFriendlyURLComposite(
			groupId, privateLayout, friendlyURL, params, requestContext);
	}

	@Override
	public String getLayoutFullURL(Layout layout, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return _portal.getLayoutFullURL(layout, themeDisplay);
	}

	@Override
	public String getLayoutFullURL(
			Layout layout, ThemeDisplay themeDisplay, boolean doAsUser)
		throws PortalException, SystemException {

		return _portal.getLayoutFullURL(layout, themeDisplay, doAsUser);
	}

	@Override
	public String getLayoutFullURL(long groupId, String portletId)
		throws PortalException, SystemException {

		return _portal.getLayoutFullURL(groupId, portletId);
	}

	@Override
	public String getLayoutFullURL(
			long groupId, String portletId, boolean secure)
		throws PortalException, SystemException {

		return _portal.getLayoutFullURL(groupId, portletId, secure);
	}

	@Override
	public String getLayoutFullURL(ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return _portal.getLayoutFullURL(themeDisplay);
	}

	@Override
	public String getLayoutSetFriendlyURL(
			LayoutSet layoutSet, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return _portal.getLayoutSetFriendlyURL(layoutSet, themeDisplay);
	}

	@Override
	public String getLayoutTarget(Layout layout) {
		return _portal.getLayoutTarget(layout);
	}

	@Override
	public String getLayoutURL(Layout layout, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return _portal.getLayoutURL(layout, themeDisplay);
	}

	@Override
	public String getLayoutURL(
			Layout layout, ThemeDisplay themeDisplay, boolean doAsUser)
		throws PortalException, SystemException {

		return _portal.getLayoutURL(layout, themeDisplay, doAsUser);
	}

	@Override
	public String getLayoutURL(ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return _portal.getLayoutURL(themeDisplay);
	}

	@Override
	public String getLayoutViewPage(Layout layout) {
		return _portal.getLayoutViewPage(layout);
	}

	@Override
	public String getLayoutViewPage(String type) {
		return _portal.getLayoutViewPage(type);
	}

	@Override
	public LiferayPortletRequest getLiferayPortletRequest(
		PortletRequest portletRequest) {

		return _portal.getLiferayPortletRequest(portletRequest);
	}

	@Override
	public LiferayPortletResponse getLiferayPortletResponse(
		PortletResponse portletResponse) {

		return _portal.getLiferayPortletResponse(portletResponse);
	}

	@Override
	public Locale getLocale(HttpServletRequest request) {
		return _portal.getLocale(request);
	}

	@Override
	public Locale getLocale(
		HttpServletRequest request, HttpServletResponse response,
		boolean initialize) {

		return _portal.getLocale(request, response, initialize);
	}

	@Override
	public Locale getLocale(RenderRequest renderRequest) {
		return _portal.getLocale(renderRequest);
	}

	@Override
	public String getLocalizedFriendlyURL(
			HttpServletRequest request, Layout layout, Locale locale)
		throws Exception {

		return _portal.getLocalizedFriendlyURL(request, layout, locale);
	}

	@Override
	public String getMailId(String mx, String popPortletPrefix, Object... ids) {
		return _portal.getMailId(mx, popPortletPrefix, ids);
	}

	@Override
	public String getNetvibesURL(Portlet portlet, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return _portal.getNetvibesURL(portlet, themeDisplay);
	}

	@Override
	public String getNewPortletTitle(
		String portletTitle, String oldScopeName, String newScopeName) {

		return _portal.getNewPortletTitle(
			portletTitle, oldScopeName, newScopeName);
	}

	@Override
	public HttpServletRequest getOriginalServletRequest(
		HttpServletRequest request) {

		return _portal.getOriginalServletRequest(request);
	}

	/**
	 * @deprecated As of 6.2.0 renamed to {@link #getSiteGroupId(long)}
	 */
	@Override
	public long getParentGroupId(long scopeGroupId)
		throws PortalException, SystemException {

		return _portal.getParentGroupId(scopeGroupId);
	}

	@Override
	public String getPathContext() {
		return _portal.getPathContext();
	}

	@Override
	public String getPathFriendlyURLPrivateGroup() {
		return _portal.getPathFriendlyURLPrivateGroup();
	}

	@Override
	public String getPathFriendlyURLPrivateUser() {
		return _portal.getPathFriendlyURLPrivateUser();
	}

	@Override
	public String getPathFriendlyURLPublic() {
		return _portal.getPathFriendlyURLPublic();
	}

	@Override
	public String getPathImage() {
		return _portal.getPathImage();
	}

	@Override
	public String getPathMain() {
		return _portal.getPathMain();
	}

	@Override
	public String getPathModule() {
		return _portal.getPathModule();
	}

	@Override
	public String getPathProxy() {
		return _portal.getPathProxy();
	}

	@Override
	public long getPlidFromFriendlyURL(long companyId, String friendlyURL) {
		return _portal.getPlidFromFriendlyURL(companyId, friendlyURL);
	}

	@Override
	public long getPlidFromPortletId(
			long groupId, boolean privateLayout, String portletId)
		throws PortalException, SystemException {

		return _portal.getPlidFromPortletId(groupId, privateLayout, portletId);
	}

	@Override
	public long getPlidFromPortletId(long groupId, String portletId)
		throws PortalException, SystemException {

		return _portal.getPlidFromPortletId(groupId, portletId);
	}

	@Override
	public String getPortalLibDir() {
		return _portal.getPortalLibDir();
	}

	/**
	 * @deprecated As of 6.2.0, replaced by the more general {@link
	 *             #getPortalPort(boolean)}
	 */
	@Override
	public int getPortalPort() {
		return _portal.getPortalPort();
	}

	@Override
	public int getPortalPort(boolean secure) {
		return _portal.getPortalPort(secure);
	}

	@Override
	public Properties getPortalProperties() {
		return _portal.getPortalProperties();
	}

	@Override
	public String getPortalURL(HttpServletRequest request) {
		return _portal.getPortalURL(request);
	}

	@Override
	public String getPortalURL(HttpServletRequest request, boolean secure) {
		return _portal.getPortalURL(request, secure);
	}

	@Override
	public String getPortalURL(Layout layout, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return _portal.getPortalURL(layout, themeDisplay);
	}

	@Override
	public String getPortalURL(PortletRequest portletRequest) {
		return _portal.getPortalURL(portletRequest);
	}

	@Override
	public String getPortalURL(PortletRequest portletRequest, boolean secure) {
		return _portal.getPortalURL(portletRequest, secure);
	}

	@Override
	public String getPortalURL(
		String serverName, int serverPort, boolean secure) {

		return _portal.getPortalURL(serverName, serverPort, secure);
	}

	@Override
	public String getPortalURL(ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return _portal.getPortalURL(themeDisplay);
	}

	@Override
	public String getPortalWebDir() {
		return _portal.getPortalWebDir();
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portal.security.auth.AuthTokenWhitelistUtil#getPortletInvocationWhitelist(
	 *             )}
	 */
	@Override
	public Set<String> getPortletAddDefaultResourceCheckWhitelist() {
		return _portal.getPortletAddDefaultResourceCheckWhitelist();
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portal.security.auth.AuthTokenWhitelistUtil#getPortletInvocationWhitelistActions(
	 *             )}
	 */
	@Override
	public Set<String> getPortletAddDefaultResourceCheckWhitelistActions() {
		return _portal.getPortletAddDefaultResourceCheckWhitelistActions();
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #getPortletBreadcrumbs(HttpServletRequest)}
	 */
	@Override
	public List<BreadcrumbEntry> getPortletBreadcrumbList(
		HttpServletRequest request) {

		return _portal.getPortletBreadcrumbList(request);
	}

	@Override
	public List<BreadcrumbEntry> getPortletBreadcrumbs(
		HttpServletRequest request) {

		return _portal.getPortletBreadcrumbs(request);
	}

	@Override
	public PortletConfig getPortletConfig(
			long companyId, String portletId, ServletContext servletContext)
		throws PortletException, SystemException {

		return getPortletConfig(companyId, portletId, servletContext);
	}

	@Override
	public String getPortletDescription(
		Portlet portlet, ServletContext servletContext, Locale locale) {

		return _portal.getPortletDescription(portlet, servletContext, locale);
	}

	@Override
	public String getPortletDescription(Portlet portlet, User user) {
		return _portal.getPortletDescription(portlet, user);
	}

	@Override
	public String getPortletDescription(String portletId, Locale locale) {
		return _portal.getPortletDescription(portletId, locale);
	}

	@Override
	public String getPortletDescription(String portletId, String languageId) {
		return _portal.getPortletDescription(portletId, languageId);
	}

	@Override
	public String getPortletDescription(String portletId, User user) {
		return _portal.getPortletDescription(portletId, user);
	}

	@Override
	public String getPortletId(HttpServletRequest request) {
		return _portal.getPortletId(request);
	}

	@Override
	public String getPortletId(PortletRequest portletRequest) {
		return _portal.getPortletId(portletRequest);
	}

	@Override
	public String getPortletLongTitle(Portlet portlet, Locale locale) {
		return _portal.getPortletLongTitle(portlet, locale);
	}

	@Override
	public String getPortletLongTitle(
		Portlet portlet, ServletContext servletContext, Locale locale) {

		return _portal.getPortletLongTitle(portlet, servletContext, locale);
	}

	@Override
	public String getPortletLongTitle(Portlet portlet, String languageId) {
		return _portal.getPortletLongTitle(portlet, languageId);
	}

	@Override
	public String getPortletLongTitle(Portlet portlet, User user) {
		return _portal.getPortletLongTitle(portlet, user);
	}

	@Override
	public String getPortletLongTitle(String portletId, Locale locale) {
		return _portal.getPortletLongTitle(portletId, locale);
	}

	@Override
	public String getPortletLongTitle(String portletId, String languageId) {
		return _portal.getPortletLongTitle(portletId, languageId);
	}

	@Override
	public String getPortletLongTitle(String portletId, User user) {
		return _portal.getPortletLongTitle(portletId, user);
	}

	@Override
	public String getPortletNamespace(String portletId) {
		return _portal.getPortletNamespace(portletId);
	}

	@Override
	public String getPortletTitle(Portlet portlet, Locale locale) {
		return _portal.getPortletTitle(portlet, locale);
	}

	@Override
	public String getPortletTitle(
		Portlet portlet, ServletContext servletContext, Locale locale) {

		return _portal.getPortletTitle(portlet, servletContext, locale);
	}

	@Override
	public String getPortletTitle(Portlet portlet, String languageId) {
		return _portal.getPortletTitle(portlet, languageId);
	}

	@Override
	public String getPortletTitle(Portlet portlet, User user) {
		return _portal.getPortletTitle(portlet, user);
	}

	@Override
	public String getPortletTitle(RenderRequest renderRequest) {
		return _portal.getPortletTitle(renderRequest);
	}

	@Override
	public String getPortletTitle(RenderResponse renderResponse) {
		return _portal.getPortletTitle(renderResponse);
	}

	@Override
	public String getPortletTitle(String portletId, Locale locale) {
		return _portal.getPortletTitle(portletId, locale);
	}

	@Override
	public String getPortletTitle(String portletId, String languageId) {
		return _portal.getPortletTitle(portletId, languageId);
	}

	@Override
	public String getPortletTitle(String portletId, User user) {
		return _portal.getPortletTitle(portletId, user);
	}

	@Override
	public String getPortletXmlFileName() throws SystemException {
		return _portal.getPortletXmlFileName();
	}

	@Override
	public PortletPreferences getPreferences(HttpServletRequest request) {
		return _portal.getPreferences(request);
	}

	@Override
	public PreferencesValidator getPreferencesValidator(Portlet portlet) {
		return _portal.getPreferencesValidator(portlet);
	}

	@Override
	public String getRelativeHomeURL(HttpServletRequest request)
		throws PortalException, SystemException {

		return _portal.getRelativeHomeURL(request);
	}

	@Override
	public long getScopeGroupId(HttpServletRequest request)
		throws PortalException, SystemException {

		return _portal.getScopeGroupId(request);
	}

	@Override
	public long getScopeGroupId(HttpServletRequest request, String portletId)
		throws PortalException, SystemException {

		return _portal.getScopeGroupId(request, portletId);
	}

	@Override
	public long getScopeGroupId(
			HttpServletRequest request, String portletId,
			boolean checkStagingGroup)
		throws PortalException, SystemException {

		return _portal.getScopeGroupId(request, portletId, checkStagingGroup);
	}

	@Override
	public long getScopeGroupId(Layout layout) {
		return _portal.getScopeGroupId(layout);
	}

	@Override
	public long getScopeGroupId(Layout layout, String portletId) {
		return _portal.getScopeGroupId(layout, portletId);
	}

	@Override
	public long getScopeGroupId(long plid) {
		return _portal.getScopeGroupId(plid);
	}

	@Override
	public long getScopeGroupId(PortletRequest portletRequest)
		throws PortalException, SystemException {

		return _portal.getScopeGroupId(portletRequest);
	}

	@Override
	public User getSelectedUser(HttpServletRequest request)
		throws PortalException, SystemException {

		return _portal.getSelectedUser(request);
	}

	@Override
	public User getSelectedUser(
			HttpServletRequest request, boolean checkPermission)
		throws PortalException, SystemException {

		return _portal.getSelectedUser(request, checkPermission);
	}

	@Override
	public User getSelectedUser(PortletRequest portletRequest)
		throws PortalException, SystemException {

		return _portal.getSelectedUser(portletRequest);
	}

	@Override
	public User getSelectedUser(
			PortletRequest portletRequest, boolean checkPermission)
		throws PortalException, SystemException {

		return _portal.getSelectedUser(portletRequest, checkPermission);
	}

	@Override
	public Map<String, List<Portlet>> getSiteAdministrationCategoriesMap(
			HttpServletRequest request)
		throws SystemException {

		return _portal.getSiteAdministrationCategoriesMap(request);
	}

	@Override
	public PortletURL getSiteAdministrationURL(
			HttpServletRequest request, ThemeDisplay themeDisplay)
		throws SystemException {

		return _portal.getSiteAdministrationURL(request, themeDisplay);
	}

	@Override
	public PortletURL getSiteAdministrationURL(
		HttpServletRequest request, ThemeDisplay themeDisplay,
		String portletName) {

		return _portal.getSiteAdministrationURL(
			request, themeDisplay, portletName);
	}

	@Override
	public PortletURL getSiteAdministrationURL(
			PortletResponse portletResponse, ThemeDisplay themeDisplay)
		throws SystemException {

		return _portal.getSiteAdministrationURL(portletResponse, themeDisplay);
	}

	@Override
	public PortletURL getSiteAdministrationURL(
		PortletResponse portletResponse, ThemeDisplay themeDisplay,
		String portletName) {

		return _portal.getSiteAdministrationURL(
			portletResponse, themeDisplay, portletName);
	}

	@Override
	public long[] getSiteAndCompanyGroupIds(long groupId)
		throws PortalException, SystemException {

		return _portal.getSiteAndCompanyGroupIds(groupId);
	}

	@Override
	public long[] getSiteAndCompanyGroupIds(ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return _portal.getSiteAndCompanyGroupIds(themeDisplay);
	}

	@Override
	public Locale getSiteDefaultLocale(long groupId)
		throws PortalException, SystemException {

		return _portal.getSiteDefaultLocale(groupId);
	}

	@Override
	public long getSiteGroupId(long groupId)
		throws PortalException, SystemException {

		return _portal.getSiteGroupId(groupId);
	}

	/**
	 * Returns the URL of the login page for the current site if one is
	 * available.
	 *
	 * @param  themeDisplay the theme display for the current page
	 * @return the URL of the login page for the current site, or
	 *         <code>null</code> if one is not available
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public String getSiteLoginURL(ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return _portal.getSiteLoginURL(themeDisplay);
	}

	@Override
	public String getStaticResourceURL(HttpServletRequest request, String uri) {
		return _portal.getStaticResourceURL(request, uri);
	}

	@Override
	public String getStaticResourceURL(
		HttpServletRequest request, String uri, long timestamp) {

		return _portal.getStaticResourceURL(request, uri, timestamp);
	}

	@Override
	public String getStaticResourceURL(
		HttpServletRequest request, String uri, String queryString) {

		return _portal.getStaticResourceURL(request, uri, queryString);
	}

	@Override
	public String getStaticResourceURL(
		HttpServletRequest request, String uri, String queryString,
		long timestamp) {

		return _portal.getStaticResourceURL(
			request, uri, queryString, timestamp);
	}

	@Override
	public String getStrutsAction(HttpServletRequest request) {
		return _portal.getStrutsAction(request);
	}

	@Override
	public String[] getSystemGroups() {
		return _portal.getSystemGroups();
	}

	@Override
	public String[] getSystemOrganizationRoles() {
		return _portal.getSystemOrganizationRoles();
	}

	@Override
	public String[] getSystemRoles() {
		return _portal.getSystemRoles();
	}

	@Override
	public String[] getSystemSiteRoles() {
		return _portal.getSystemSiteRoles();
	}

	@Override
	public String getUniqueElementId(
		HttpServletRequest request, String namespace, String id) {

		return _portal.getUniqueElementId(request, namespace, id);
	}

	@Override
	public String getUniqueElementId(
		PortletRequest request, String namespace, String id) {

		return _portal.getUniqueElementId(request, namespace, id);
	}

	@Override
	public UploadPortletRequest getUploadPortletRequest(
		PortletRequest portletRequest) {

		return _portal.getUploadPortletRequest(portletRequest);
	}

	@Override
	public UploadServletRequest getUploadServletRequest(
		HttpServletRequest request) {

		return _portal.getUploadServletRequest(request);
	}

	@Override
	public Date getUptime() {
		return _portal.getUptime();
	}

	@Override
	public String getURLWithSessionId(String url, String sessionId) {
		return _portal.getURLWithSessionId(url, sessionId);
	}

	@Override
	public User getUser(HttpServletRequest request)
		throws PortalException, SystemException {

		return _portal.getUser(request);
	}

	@Override
	public User getUser(PortletRequest portletRequest)
		throws PortalException, SystemException {

		return _portal.getUser(portletRequest);
	}

	@Override
	public String getUserEmailAddress(long userId) throws SystemException {
		return _portal.getUserEmailAddress(userId);
	}

	@Override
	public long getUserId(HttpServletRequest request) {
		return _portal.getUserId(request);
	}

	@Override
	public long getUserId(PortletRequest portletRequest) {
		return _portal.getUserId(portletRequest);
	}

	@Override
	public String getUserName(BaseModel<?> baseModel) {
		return _portal.getUserName(baseModel);
	}

	@Override
	public String getUserName(long userId, String defaultUserName) {
		return _portal.getUserName(userId, defaultUserName);
	}

	@Override
	public String getUserName(
		long userId, String defaultUserName, HttpServletRequest request) {

		return _portal.getUserName(userId, defaultUserName, request);
	}

	@Override
	public String getUserName(
		long userId, String defaultUserName, String userAttribute) {

		return _portal.getUserName(userId, defaultUserName, userAttribute);
	}

	@Override
	public String getUserName(
		long userId, String defaultUserName, String userAttribute,
		HttpServletRequest request) {

		return _portal.getUserName(
			userId, defaultUserName, userAttribute, request);
	}

	@Override
	public String getUserPassword(HttpServletRequest request) {
		return _portal.getUserPassword(request);
	}

	@Override
	public String getUserPassword(HttpSession session) {
		return _portal.getUserPassword(session);
	}

	@Override
	public String getUserPassword(PortletRequest portletRequest) {
		return _portal.getUserPassword(portletRequest);
	}

	@Override
	public String getUserValue(long userId, String param, String defaultValue)
		throws SystemException {

		return _portal.getUserValue(userId, param, defaultValue);
	}

	@Override
	public long getValidUserId(long companyId, long userId)
		throws PortalException, SystemException {

		return _portal.getValidUserId(companyId, userId);
	}

	@Override
	public String getVirtualLayoutActualURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException, SystemException {

		return _portal.getVirtualLayoutActualURL(
			groupId, privateLayout, mainPath, friendlyURL, params,
			requestContext);
	}

	@Override
	public LayoutFriendlyURLComposite getVirtualLayoutFriendlyURLComposite(
			boolean privateLayout, String friendlyURL,
			Map<String, String[]> params, Map<String, Object> requestContext)
		throws PortalException, SystemException {

		return _portal.getVirtualLayoutFriendlyURLComposite(
			privateLayout, friendlyURL, params, requestContext);
	}

	@Override
	public String getWidgetURL(Portlet portlet, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return _portal.getWidgetURL(portlet, themeDisplay);
	}

	public Portal getWrappedPortal() {
		return _portal;
	}

	@Override
	public int hashCode() {
		return _portal.hashCode();
	}

	@Override
	public void initCustomSQL() {
		_portal.initCustomSQL();
	}

	@Override
	public User initUser(HttpServletRequest request) throws Exception {
		return _portal.initUser(request);
	}

	@Override
	public void invokeTaglibDiscussion(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		_portal.invokeTaglibDiscussion(
			portletConfig, actionRequest, actionResponse);
	}

	/**
	 * @deprecated As of 6.2.0 with no direct replacement
	 */
	@Override
	public boolean isAllowAddPortletDefaultResource(
			HttpServletRequest request, Portlet portlet)
		throws PortalException, SystemException {

		return _portal.isAllowAddPortletDefaultResource(request, portlet);
	}

	@Override
	public boolean isCDNDynamicResourcesEnabled(HttpServletRequest request)
		throws PortalException, SystemException {

		return _portal.isCDNDynamicResourcesEnabled(request);
	}

	@Override
	public boolean isCDNDynamicResourcesEnabled(long companyId) {
		return _portal.isCDNDynamicResourcesEnabled(companyId);
	}

	/**
	 * @deprecated As of 6.1.0, renamed to {@link #isGroupAdmin(User, long)}
	 */
	@Override
	public boolean isCommunityAdmin(User user, long groupId) throws Exception {
		return _portal.isCommunityAdmin(user, groupId);
	}

	/**
	 * @deprecated As of 6.1.0, renamed to {@link #isGroupOwner(User, long)}
	 */
	@Override
	public boolean isCommunityOwner(User user, long groupId) throws Exception {
		return _portal.isCommunityOwner(user, groupId);
	}

	@Override
	public boolean isCompanyAdmin(User user) throws Exception {
		return _portal.isCompanyAdmin(user);
	}

	@Override
	public boolean isCompanyControlPanelPortlet(
			String portletId, String category, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return _portal.isCompanyControlPanelPortlet(
			portletId, category, themeDisplay);
	}

	@Override
	public boolean isCompanyControlPanelPortlet(
			String portletId, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return _portal.isCompanyControlPanelPortlet(portletId, themeDisplay);
	}

	@Override
	public boolean isCompanyControlPanelVisible(ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return _portal.isCompanyControlPanelVisible(themeDisplay);
	}

	@Override
	public boolean isControlPanelPortlet(
			String portletId, String category, ThemeDisplay themeDisplay)
		throws SystemException {

		return _portal.isControlPanelPortlet(portletId, category, themeDisplay);
	}

	@Override
	public boolean isControlPanelPortlet(
			String portletId, ThemeDisplay themeDisplay)
		throws SystemException {

		return _portal.isControlPanelPortlet(portletId, themeDisplay);
	}

	@Override
	public boolean isGroupAdmin(User user, long groupId) throws Exception {
		return _portal.isGroupAdmin(user, groupId);
	}

	@Override
	public boolean isGroupFriendlyURL(
		String fullURL, String groupFriendlyURL, String layoutFriendlyURL) {

		return _portal.isGroupFriendlyURL(
			fullURL, groupFriendlyURL, layoutFriendlyURL);
	}

	@Override
	public boolean isGroupOwner(User user, long groupId) throws Exception {
		return _portal.isGroupOwner(user, groupId);
	}

	@Override
	public boolean isLayoutDescendant(Layout layout, long layoutId)
		throws PortalException, SystemException {

		return _portal.isLayoutDescendant(layout, layoutId);
	}

	@Override
	public boolean isLayoutFirstPageable(Layout layout) {
		return _portal.isLayoutFirstPageable(layout);
	}

	@Override
	public boolean isLayoutFirstPageable(String type) {
		return _portal.isLayoutFirstPageable(type);
	}

	@Override
	public boolean isLayoutFriendliable(Layout layout) {
		return _portal.isLayoutFriendliable(layout);
	}

	@Override
	public boolean isLayoutFriendliable(String type) {
		return _portal.isLayoutFriendliable(type);
	}

	@Override
	public boolean isLayoutParentable(Layout layout) {
		return _portal.isLayoutParentable(layout);
	}

	@Override
	public boolean isLayoutParentable(String type) {
		return _portal.isLayoutParentable(type);
	}

	@Override
	public boolean isLayoutSitemapable(Layout layout) {
		return _portal.isLayoutSitemapable(layout);
	}

	@Override
	public boolean isMethodGet(PortletRequest portletRequest) {
		return _portal.isMethodGet(portletRequest);
	}

	@Override
	public boolean isMethodPost(PortletRequest portletRequest) {
		return _portal.isMethodPost(portletRequest);
	}

	@Override
	public boolean isMultipartRequest(HttpServletRequest request) {
		return _portal.isMultipartRequest(request);
	}

	@Override
	public boolean isOmniadmin(long userId) {
		return _portal.isOmniadmin(userId);
	}

	@Override
	public boolean isReservedParameter(String name) {
		return _portal.isReservedParameter(name);
	}

	@Override
	public boolean isRSSFeedsEnabled() {
		return _portal.isRSSFeedsEnabled();
	}

	@Override
	public boolean isSecure(HttpServletRequest request) {
		return _portal.isSecure(request);
	}

	@Override
	public boolean isSystemGroup(String groupName) {
		return _portal.isSystemGroup(groupName);
	}

	@Override
	public boolean isSystemRole(String roleName) {
		return _portal.isSystemRole(roleName);
	}

	@Override
	public boolean isUpdateAvailable() throws SystemException {
		return _portal.isUpdateAvailable();
	}

	@Override
	public boolean isValidResourceId(String resourceId) {
		return _portal.isValidResourceId(resourceId);
	}

	@Override
	public void removePortalPortEventListener(
		PortalPortEventListener portalPortEventListener) {

		_portal.removePortalPortEventListener(portalPortEventListener);
	}

	@Override
	public void resetCDNHosts() {
		_portal.resetCDNHosts();
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portal.security.auth.AuthTokenWhitelistUtil#resetPortletInvocationWhitelist(
	 *             )}
	 */
	@Override
	public Set<String> resetPortletAddDefaultResourceCheckWhitelist() {
		return _portal.resetPortletAddDefaultResourceCheckWhitelist();
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portal.security.auth.AuthTokenWhitelistUtil#resetPortletInvocationWhitelistActions(
	 *             )}
	 */
	@Override
	public Set<String> resetPortletAddDefaultResourceCheckWhitelistActions() {
		return _portal.resetPortletAddDefaultResourceCheckWhitelistActions();
	}

	@Override
	public void sendError(
			Exception e, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws IOException {

		_portal.sendError(e, actionRequest, actionResponse);
	}

	@Override
	public void sendError(
			Exception e, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		_portal.sendError(e, request, response);
	}

	@Override
	public void sendError(
			int status, Exception e, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws IOException {

		_portal.sendError(status, e, actionRequest, actionResponse);
	}

	@Override
	public void sendError(
			int status, Exception e, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		_portal.sendError(status, e, request, response);
	}

	@Override
	public void sendRSSFeedsDisabledError(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		_portal.sendRSSFeedsDisabledError(request, response);
	}

	@Override
	public void sendRSSFeedsDisabledError(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws IOException, ServletException {

		_portal.sendRSSFeedsDisabledError(portletRequest, portletResponse);
	}

	/**
	 * Sets the description for the page, overriding the existing page
	 * description.
	 */
	@Override
	public void setPageDescription(
		String description, HttpServletRequest request) {

		_portal.setPageDescription(description, request);
	}

	/**
	 * Sets the keywords for the page, overriding the existing page keywords.
	 */
	@Override
	public void setPageKeywords(String keywords, HttpServletRequest request) {
		_portal.setPageKeywords(keywords, request);
	}

	/**
	 * Sets the subtitle for the page, overriding the existing page subtitle.
	 */
	@Override
	public void setPageSubtitle(String subtitle, HttpServletRequest request) {
		_portal.setPageSubtitle(subtitle, request);
	}

	/**
	 * Sets the whole title for the page, overriding the existing page whole
	 * title.
	 */
	@Override
	public void setPageTitle(String title, HttpServletRequest request) {
		_portal.setPageTitle(title, request);
	}

	/**
	 * Sets the port obtained on the first request to the portal.
	 */
	@Override
	public void setPortalPort(HttpServletRequest request) {
		_portal.setPortalPort(request);
	}

	@Override
	public void storePreferences(PortletPreferences portletPreferences)
		throws IOException, ValidatorException {

		_portal.storePreferences(portletPreferences);
	}

	@Override
	public String[] stripURLAnchor(String url, String separator) {
		return _portal.stripURLAnchor(url, separator);
	}

	@Override
	public String transformCustomSQL(String sql) {
		return _portal.transformCustomSQL(sql);
	}

	@Override
	public String transformSQL(String sql) {
		return _portal.transformSQL(sql);
	}

	@Override
	public PortletMode updatePortletMode(
		String portletId, User user, Layout layout, PortletMode portletMode,
		HttpServletRequest request) {

		return _portal.updatePortletMode(
			portletId, user, layout, portletMode, request);
	}

	@Override
	public String updateRedirect(
		String redirect, String oldPath, String newPath) {

		return _portal.updateRedirect(redirect, oldPath, newPath);
	}

	@Override
	public WindowState updateWindowState(
		String portletId, User user, Layout layout, WindowState windowState,
		HttpServletRequest request) {

		return _portal.updateWindowState(
			portletId, user, layout, windowState, request);
	}

	private Portal _portal;

}