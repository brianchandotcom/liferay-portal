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

import com.liferay.portal.kernel.exception.PortalException;
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
 */
@ProviderType
public interface Portal {

	public static final String FRIENDLY_URL_SEPARATOR = "/-/";

	public static final String JSESSIONID = ";jsessionid=";

	public static final String PATH_IMAGE = "/image";

	public static final String PATH_MAIN = "/c";

	public static final String PATH_MODULE = "/o";

	public static final String PATH_PORTAL_LAYOUT = "/portal/layout";

	public static final String PORTAL_REALM = "PortalRealm";

	public static final String PORTLET_XML_FILE_NAME_CUSTOM =
		"portlet-custom.xml";

	public static final String PORTLET_XML_FILE_NAME_STANDARD = "portlet.xml";

	public static final String TEMP_OBFUSCATION_VALUE =
		"TEMP_OBFUSCATION_VALUE";

	/**
	 * Appends the description to the current meta description of the page.
	 *
	 * @param description the description to append to the current meta
	 *        description
	 * @param request the servlet request for the page
	 */
	public void addPageDescription(
		String description, HttpServletRequest request);

	/**
	 * Appends the keywords to the current meta keywords of the page.
	 *
	 * @param keywords the keywords to add to the current meta keywords
	 *        (comma-separated)
	 * @param request the servlet request for the page
	 */
	public void addPageKeywords(String keywords, HttpServletRequest request);

	/**
	 * Appends the subtitle to the current subtitle of the page.
	 *
	 * @param subtitle the subtitle to append to the current subtitle
	 * @param request the servlet request for the page
	 */
	public void addPageSubtitle(String subtitle, HttpServletRequest request);

	/**
	 * Appends the title to the current title of the page.
	 *
	 * @param title the title to append to the current title
	 * @param request the servlet request for the page
	 */
	public void addPageTitle(String title, HttpServletRequest request);

	public boolean addPortalInetSocketAddressEventListener(
		PortalInetSocketAddressEventListener
			portalInetSocketAddressEventListener);

	/**
	 * Adds the portal port event listener to the portal. The listener will be
	 * notified whenever the portal port is set.
	 *
	 * @param      portalPortEventListener the portal port event listener to add
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #addPortalInetSocketAddressEventListener(
	 *             PortalInetSocketAddressEventListener)}
	 */
	@Deprecated
	public void addPortalPortEventListener(
		PortalPortEventListener portalPortEventListener);

	/**
	 * Adds an entry to the portlet breadcrumbs for the page.
	 *
	 * @param request the servlet request for the page
	 * @param title the title of the new breakcrumb entry
	 * @param url the URL of the new breadcrumb entry
	 */
	public void addPortletBreadcrumbEntry(
		HttpServletRequest request, String title, String url);

	/**
	 * Adds an entry to the portlet breadcrumbs for the page.
	 *
	 * @param request the servlet request for the page
	 * @param title the title of the new breakcrumb entry
	 * @param url the URL of the new breadcrumb entry
	 * @param data the HTML5 data parameters of the new breadcrumb entry
	 */
	public void addPortletBreadcrumbEntry(
		HttpServletRequest request, String title, String url,
		Map<String, Object> data);

	/**
	 * Adds the default resource permissions for the portlet to the page.
	 *
	 * @param  request the servlet request for the page
	 * @param  portlet the portlet
	 * @throws PortalException if adding the default resource permissions failed
	 */
	public void addPortletDefaultResource(
			HttpServletRequest request, Portlet portlet)
		throws PortalException;

	public void addPortletDefaultResource(
			long companyId, Layout layout, Portlet portlet)
		throws PortalException;

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
	public String addPreservedParameters(
		ThemeDisplay themeDisplay, Layout layout, String url, boolean doAsUser);

	/**
	 * Adds the preserved parameters doAsUserId, doAsUserLanguageId,
	 * doAsGroupId, refererPlid, and controlPanelCategory to the URL.
	 *
	 * @param  themeDisplay the current theme display
	 * @param  url the URL
	 * @return the URL with the preserved parameters added
	 */
	public String addPreservedParameters(ThemeDisplay themeDisplay, String url);

	public void addUserLocaleOptionsMessage(HttpServletRequest request);

	/**
	 * Clears the render parameters in the request if the portlet is in the
	 * action phase.
	 *
	 * @param renderRequest the render request
	 */
	public void clearRequestParameters(RenderRequest renderRequest);

	/**
	 * Copies the request parameters to the render parameters, unless a
	 * parameter with that name already exists in the render parameters.
	 *
	 * @param actionRequest the request from which to get the request parameters
	 * @param actionResponse the response to receive the render parameters
	 */
	public void copyRequestParameters(
		ActionRequest actionRequest, ActionResponse actionResponse);

	/**
	 * Escapes the URL for use in a redirect and checks that security settings
	 * allow the URL is allowed for redirects.
	 *
	 * @param  url the URL to escape
	 * @return the escaped URL, or <code>null</code> if the URL is not an
	 *         allowed for redirects
	 */
	public String escapeRedirect(String url);

	/**
	 * Generates a random key to identify the request based on the input string.
	 *
	 * @param  request the servlet request for the page
	 * @param  input the input string
	 * @return the generated key
	 */
	public String generateRandomKey(HttpServletRequest request, String input);

	public String getAbsoluteURL(HttpServletRequest request, String url);

	public LayoutQueryStringComposite getActualLayoutQueryStringComposite(
			long groupId, boolean privateLayout, String friendlyURL,
			Map<String, String[]> params, Map<String, Object> requestContext)
		throws PortalException;

	public String getActualURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException;

	/**
	 * Returns an array with the alternate locales, considering if the page is
	 * showing just a content and the translations of this content.
	 *
	 * @param      request the servlet request for the page
	 * @return     the array of alternate locales
	 * @throws     PortalException if a portal exception occurred
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portal.kernel.language.LanguageUtil#getAvailableLocales}
	 */
	@Deprecated
	public Locale[] getAlternateLocales(HttpServletRequest request)
		throws PortalException;

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
	 * @param  locale the locale of the alternate (translated) page
	 * @param  layout the page being requested
	 * @return the alternate URL for the requested canonical URL in the given
	 *         locale
	 * @throws PortalException if a portal exception occurred
	 */
	public String getAlternateURL(
			String canonicalURL, ThemeDisplay themeDisplay, Locale locale,
			Layout layout)
		throws PortalException;

	public long[] getAncestorSiteGroupIds(long groupId) throws PortalException;

	/**
	 * Returns the set of struts actions that should not be checked for an
	 * authentication token.
	 *
	 * @return     the set of struts actions that should not be checked for an
	 *             authentication token
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portal.security.auth.AuthTokenWhitelistUtil#getPortletCSRFWhitelistActions}
	 */
	@Deprecated
	public Set<String> getAuthTokenIgnoreActions();

	/**
	 * Returns the set of IDs of portlets that should not be checked for an
	 * authentication token.
	 *
	 * @return     the set of IDs of portlets that should not be checked for an
	 *             authentication token
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portal.security.auth.AuthTokenWhitelistUtil#getPortletCSRFWhitelist}
	 */
	@Deprecated
	public Set<String> getAuthTokenIgnorePortlets();

	/**
	 * Returns the base model instance for the resource permission.
	 *
	 * @param  resourcePermission the resource permission
	 * @return the base model instance, or <code>null</code> if the resource
	 *         permission does not have a base model instance (such as if its a
	 *         portlet)
	 * @throws PortalException if a base model instance for the resource
	 *         permission could not be found
	 */
	public BaseModel<?> getBaseModel(ResourcePermission resourcePermission)
		throws PortalException;

	/**
	 * Returns the base model instance for the model name and primary key.
	 *
	 * @param  modelName the fully qualified class name of the model
	 * @param  primKey the primary key of the model instance to get
	 * @return the base model instance, or <code>null</code> if the model does
	 *         not have a base model instance (such as if its a portlet)
	 * @throws PortalException if a base model instance with the primary key
	 *         could not be found
	 */
	public BaseModel<?> getBaseModel(String modelName, String primKey)
		throws PortalException;

	/**
	 * Returns the user's ID from the HTTP authentication headers after
	 * validating their credentials.
	 *
	 * @param  request the servlet request from which to retrieve the HTTP
	 *         authentication headers
	 * @return the user's ID if HTTP authentication headers are present and
	 *         their credentials are valid; 0 otherwise
	 * @throws PortalException if an authentication exception occurred
	 */
	public long getBasicAuthUserId(HttpServletRequest request)
		throws PortalException;

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
	 */
	public long getBasicAuthUserId(HttpServletRequest request, long companyId)
		throws PortalException;

	public List<Group> getBrowsableScopeGroups(
			long userId, long companyId, long groupId, String portletId)
		throws PortalException;

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
	 */
	public String getCanonicalURL(
			String completeURL, ThemeDisplay themeDisplay, Layout layout)
		throws PortalException;

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
	 */
	public String getCanonicalURL(
			String completeURL, ThemeDisplay themeDisplay, Layout layout,
			boolean forceLayoutFriendlyURL)
		throws PortalException;

	/**
	 * @deprecated As of 6.2.0, replaced by the more general {@link
	 *             #getCDNHost(boolean)}
	 */
	@Deprecated
	public String getCDNHost();

	/**
	 * Returns the secure (HTTPS) or insecure (HTTP) content distribution
	 * network (CDN) host address for this portal.
	 *
	 * @param  secure whether to get the secure or insecure CDN host address
	 * @return the CDN host address
	 */
	public String getCDNHost(boolean secure);

	public String getCDNHost(HttpServletRequest request) throws PortalException;

	/**
	 * Returns the insecure (HTTP) content distribution network (CDN) host
	 * address
	 *
	 * @param  companyId the company ID of a site
	 * @return the CDN host address
	 */
	public String getCDNHostHttp(long companyId);

	/**
	 * Returns the secure (HTTPS) content distribution network (CDN) host
	 * address
	 *
	 * @param  companyId the company ID of a site
	 * @return the CDN host address
	 */
	public String getCDNHostHttps(long companyId);

	/**
	 * Returns the fully qualified name of the class from its ID.
	 *
	 * @param  classNameId the ID of the class
	 * @return the fully qualified name of the class
	 */
	public String getClassName(long classNameId);

	/**
	 * Returns the ID of the class from its class object.
	 *
	 * @param  clazz the class object
	 * @return the ID of the class
	 */
	public long getClassNameId(Class<?> clazz);

	/**
	 * Returns the ID of the class from its fully qualified name.
	 *
	 * @param  value the fully qualified name of the class
	 * @return the ID of the class
	 */
	public long getClassNameId(String value);

	/**
	 * Returns the ID of certain portlets from the fully qualified name of one
	 * of their classes. The portlets this method supports are: blogs, calendar,
	 * document library, image gallery, journal, message boards, and wiki.
	 *
	 * @param  className the fully qualified name of a class in a portlet
	 * @return the ID of the portlet the class is a part of, or an empty string
	 *         if the class is not supported
	 */
	public String getClassNamePortletId(String className);

	public Company getCompany(HttpServletRequest request)
		throws PortalException;

	public Company getCompany(PortletRequest portletRequest)
		throws PortalException;

	public long getCompanyId(HttpServletRequest requestuest);

	public long getCompanyId(PortletRequest portletRequest);

	public long[] getCompanyIds();

	public String getComputerAddress();

	public String getComputerName();

	public Map<String, List<Portlet>> getControlPanelCategoriesMap(
		HttpServletRequest request);

	public String getControlPanelCategory(
		String portletId, ThemeDisplay themeDisplay);

	public String getControlPanelFullURL(
			long scopeGroupId, String ppid, Map<String, String[]> params)
		throws PortalException;

	public long getControlPanelPlid(long companyId) throws PortalException;

	public long getControlPanelPlid(PortletRequest portletRequest)
		throws PortalException;

	public Set<Portlet> getControlPanelPortlets(
		long companyId, String category);

	public List<Portlet> getControlPanelPortlets(
		String category, ThemeDisplay themeDisplay);

	public PortletURL getControlPanelPortletURL(
		HttpServletRequest request, String portletId, long referrerPlid,
		String lifecycle);

	public PortletURL getControlPanelPortletURL(
		PortletRequest portletRequest, String portletId, long referrerPlid,
		String lifecycle);

	public String getCreateAccountURL(
			HttpServletRequest request, ThemeDisplay themeDisplay)
		throws Exception;

	public long[] getCurrentAndAncestorSiteGroupIds(long groupId)
		throws PortalException;

	public List<Group> getCurrentAndAncestorSiteGroups(long groupId)
		throws PortalException;

	public String getCurrentCompleteURL(HttpServletRequest request);

	public String getCurrentURL(HttpServletRequest request);

	public String getCurrentURL(PortletRequest portletRequest);

	public String getCustomSQLFunctionIsNotNull();

	public String getCustomSQLFunctionIsNull();

	/**
	 * Returns the date object for the specified month, day, and year, or
	 * <code>null</code> if the date is invalid.
	 *
	 * @param  month the month (0-based, meaning 0 for January)
	 * @param  day the day of the month
	 * @param  year the year
	 * @return the date object, or <code>null</code> if the date is invalid
	 */
	public Date getDate(int month, int day, int year);

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
	 * @throws PortalException if the date was invalid and <code>clazz</code>
	 *         was not <code>null</code>
	 */
	public Date getDate(
			int month, int day, int year,
			Class<? extends PortalException> clazz)
		throws PortalException;

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
	 * @throws PortalException if the date was invalid and <code>clazz</code>
	 *         was not <code>null</code>
	 */
	public Date getDate(
			int month, int day, int year, int hour, int min,
			Class<? extends PortalException> clazz)
		throws PortalException;

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
	 * @throws PortalException if the date was invalid and <code>clazz</code>
	 *         was not <code>null</code>
	 */
	public Date getDate(
			int month, int day, int year, int hour, int min, TimeZone timeZone,
			Class<? extends PortalException> clazz)
		throws PortalException;

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
	 * @throws PortalException if the date was invalid and <code>clazz</code>
	 *         was not <code>null</code>
	 */
	public Date getDate(
			int month, int day, int year, TimeZone timeZone,
			Class<? extends PortalException> clazz)
		throws PortalException;

	public long getDefaultCompanyId();

	public long getDigestAuthUserId(HttpServletRequest request)
		throws PortalException;

	public String getEmailFromAddress(
		PortletPreferences preferences, long companyId, String defaultValue);

	public String getEmailFromName(
		PortletPreferences preferences, long companyId, String defaultValue);

	public Map<String, Serializable> getExpandoBridgeAttributes(
			ExpandoBridge expandoBridge, HttpServletRequest request)
		throws PortalException;

	public Map<String, Serializable> getExpandoBridgeAttributes(
			ExpandoBridge expandoBridge, PortletRequest portletRequest)
		throws PortalException;

	public Map<String, Serializable> getExpandoBridgeAttributes(
			ExpandoBridge expandoBridge,
			UploadPortletRequest uploadPortletRequest)
		throws PortalException;

	public Serializable getExpandoValue(
			HttpServletRequest request, String name, int type,
			String displayType)
		throws PortalException;

	public Serializable getExpandoValue(
			PortletRequest portletRequest, String name, int type,
			String displayType)
		throws PortalException;

	public Serializable getExpandoValue(
			UploadPortletRequest uploadPortletRequest, String name, int type,
			String displayType)
		throws PortalException;

	public String getFacebookURL(
			Portlet portlet, String facebookCanvasPageURL,
			ThemeDisplay themeDisplay)
		throws PortalException;

	public Portlet getFirstMyAccountPortlet(ThemeDisplay themeDisplay);

	public String getFirstPageLayoutTypes(HttpServletRequest request);

	public Portlet getFirstSiteAdministrationPortlet(ThemeDisplay themeDisplay);

	public String getFullName(
		String firstName, String middleName, String lastName);

	public String getGlobalLibDir();

	public String getGoogleGadgetURL(Portlet portlet, ThemeDisplay themeDisplay)
		throws PortalException;

	public String getGroupFriendlyURL(
			Group group, boolean privateLayoutSet, ThemeDisplay themeDisplay)
		throws PortalException;

	public String getGroupFriendlyURL(
			Group group, boolean privateLayoutSet, ThemeDisplay themeDisplay,
			Locale locale)
		throws PortalException;

	public int[] getGroupFriendlyURLIndex(String requestURI);

	public String[] getGroupPermissions(HttpServletRequest request);

	public String[] getGroupPermissions(
		HttpServletRequest request, String className);

	public String[] getGroupPermissions(PortletRequest portletRequest);

	public String[] getGroupPermissions(
		PortletRequest portletRequest, String className);

	public String[] getGuestPermissions(HttpServletRequest request);

	public String[] getGuestPermissions(
		HttpServletRequest request, String className);

	public String[] getGuestPermissions(PortletRequest portletRequest);

	public String[] getGuestPermissions(
		PortletRequest portletRequest, String className);

	public String getHomeURL(HttpServletRequest request) throws PortalException;

	public String getHost(HttpServletRequest request);

	public String getHost(PortletRequest portletRequest);

	public HttpServletRequest getHttpServletRequest(
		PortletRequest portletRequest);

	public HttpServletResponse getHttpServletResponse(
		PortletResponse portletResponse);

	public String getI18nPathLanguageId(
		Locale locale, String defaultI18nPathLanguageId);

	public String getJournalArticleActualURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException;

	public Layout getJournalArticleLayout(
			long groupId, boolean privateLayout, String friendlyURL)
		throws PortalException;

	public String getJsSafePortletId(String portletId);

	public String getLayoutActualURL(Layout layout);

	public String getLayoutActualURL(Layout layout, String mainPath);

	public String getLayoutActualURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL)
		throws PortalException;

	public String getLayoutActualURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException;

	public String getLayoutFriendlyURL(Layout layout, ThemeDisplay themeDisplay)
		throws PortalException;

	public String getLayoutFriendlyURL(
			Layout layout, ThemeDisplay themeDisplay, Locale locale)
		throws PortalException;

	public LayoutFriendlyURLComposite getLayoutFriendlyURLComposite(
			long groupId, boolean privateLayout, String friendlyURL,
			Map<String, String[]> params, Map<String, Object> requestContext)
		throws PortalException;

	public String getLayoutFullURL(Layout layout, ThemeDisplay themeDisplay)
		throws PortalException;

	public String getLayoutFullURL(
			Layout layout, ThemeDisplay themeDisplay, boolean doAsUser)
		throws PortalException;

	public String getLayoutFullURL(long groupId, String portletId)
		throws PortalException;

	public String getLayoutFullURL(
			long groupId, String portletId, boolean secure)
		throws PortalException;

	public String getLayoutFullURL(ThemeDisplay themeDisplay)
		throws PortalException;

	public String getLayoutRelativeURL(Layout layout, ThemeDisplay themeDisplay)
		throws PortalException;

	public String getLayoutRelativeURL(
			Layout layout, ThemeDisplay themeDisplay, boolean doAsUser)
		throws PortalException;

	public String getLayoutSetFriendlyURL(
			LayoutSet layoutSet, ThemeDisplay themeDisplay)
		throws PortalException;

	public String getLayoutTarget(Layout layout);

	public String getLayoutURL(Layout layout, ThemeDisplay themeDisplay)
		throws PortalException;

	public String getLayoutURL(
			Layout layout, ThemeDisplay themeDisplay, boolean doAsUser)
		throws PortalException;

	public String getLayoutURL(
			Layout layout, ThemeDisplay themeDisplay, Locale locale)
		throws PortalException;

	public String getLayoutURL(ThemeDisplay themeDisplay)
		throws PortalException;

	public LiferayPortletRequest getLiferayPortletRequest(
		PortletRequest portletRequest);

	public LiferayPortletResponse getLiferayPortletResponse(
		PortletResponse portletResponse);

	public Locale getLocale(HttpServletRequest request);

	public Locale getLocale(
		HttpServletRequest request, HttpServletResponse response,
		boolean initialize);

	public Locale getLocale(PortletRequest portletRequest);

	public String getLocalizedFriendlyURL(
			HttpServletRequest request, Layout layout, Locale locale,
			Locale originalLocale)
		throws Exception;

	public String getMailId(String mx, String popPortletPrefix, Object... ids);

	public String getNetvibesURL(Portlet portlet, ThemeDisplay themeDisplay)
		throws PortalException;

	public String getNewPortletTitle(
		String portletTitle, String oldScopeName, String newScopeName);

	public HttpServletRequest getOriginalServletRequest(
		HttpServletRequest request);

	/**
	 * @deprecated As of 6.2.0 renamed to {@link #getSiteGroupId(long)}
	 */
	@Deprecated
	public long getParentGroupId(long scopeGroupId) throws PortalException;

	public String getPathContext();

	public String getPathContext(HttpServletRequest request);

	public String getPathContext(PortletRequest portletRequest);

	public String getPathContext(String contextPath);

	public String getPathFriendlyURLPrivateGroup();

	public String getPathFriendlyURLPrivateUser();

	public String getPathFriendlyURLPublic();

	public String getPathImage();

	public String getPathMain();

	public String getPathModule();

	public String getPathProxy();

	public long getPlidFromFriendlyURL(long companyId, String friendlyURL);

	public long getPlidFromPortletId(
			long groupId, boolean privateLayout, String portletId)
		throws PortalException;

	public long getPlidFromPortletId(long groupId, String portletId)
		throws PortalException;

	public PortalInetSocketAddressEventListener[]
		getPortalInetSocketAddressEventListeners();

	public String getPortalLibDir();

	public InetAddress getPortalLocalInetAddress(boolean secure);

	public int getPortalLocalPort(boolean secure);

	/**
	 * @deprecated As of 6.2.0, replaced by the more general {@link
	 *             #getPortalPort(boolean)}
	 */
	@Deprecated
	public int getPortalPort();

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getPortalServerPort(boolean)}
	 */
	@Deprecated
	public int getPortalPort(boolean secure);

	public Properties getPortalProperties();

	public InetAddress getPortalServerInetAddress(boolean secure);

	public int getPortalServerPort(boolean secure);

	public String getPortalURL(HttpServletRequest request);

	public String getPortalURL(HttpServletRequest request, boolean secure);

	public String getPortalURL(Layout layout, ThemeDisplay themeDisplay)
		throws PortalException;

	public String getPortalURL(PortletRequest portletRequest);

	public String getPortalURL(PortletRequest portletRequest, boolean secure);

	public String getPortalURL(
		String serverName, int serverPort, boolean secure);

	public String getPortalURL(ThemeDisplay themeDisplay)
		throws PortalException;

	public String getPortalWebDir();

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portal.security.auth.AuthTokenWhitelistUtil#getPortletInvocationWhitelist}
	 */
	@Deprecated
	public Set<String> getPortletAddDefaultResourceCheckWhitelist();

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portal.security.auth.AuthTokenWhitelistUtil#getPortletInvocationWhitelistActions}
	 */
	@Deprecated
	public Set<String> getPortletAddDefaultResourceCheckWhitelistActions();

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #getPortletBreadcrumbs(HttpServletRequest)}
	 */
	@Deprecated
	public List<BreadcrumbEntry> getPortletBreadcrumbList(
		HttpServletRequest request);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbUtil#getPortletBreadcrumbEntries(
	 *             HttpServletRequest)}
	 */
	@Deprecated
	public List<BreadcrumbEntry> getPortletBreadcrumbs(
		HttpServletRequest request);

	public PortletConfig getPortletConfig(
			long companyId, String portletId, ServletContext servletContext)
		throws PortletException;

	public String getPortletDescription(
		Portlet portlet, ServletContext servletContext, Locale locale);

	public String getPortletDescription(Portlet portlet, User user);

	public String getPortletDescription(String portletId, Locale locale);

	public String getPortletDescription(String portletId, String languageId);

	public String getPortletDescription(String portletId, User user);

	public String getPortletId(HttpServletRequest request);

	public String getPortletId(PortletRequest portletRequest);

	public String getPortletLongTitle(Portlet portlet, Locale locale);

	public String getPortletLongTitle(
		Portlet portlet, ServletContext servletContext, Locale locale);

	public String getPortletLongTitle(Portlet portlet, String languageId);

	public String getPortletLongTitle(Portlet portlet, User user);

	public String getPortletLongTitle(String portletId, Locale locale);

	public String getPortletLongTitle(String portletId, String languageId);

	public String getPortletLongTitle(String portletId, User user);

	public String getPortletNamespace(String portletId);

	public String getPortletTitle(Portlet portlet, Locale locale);

	public String getPortletTitle(
		Portlet portlet, ServletContext servletContext, Locale locale);

	public String getPortletTitle(Portlet portlet, String languageId);

	public String getPortletTitle(Portlet portlet, User user);

	public String getPortletTitle(PortletRequest portletRequest);

	public String getPortletTitle(PortletResponse portletResponse);

	public String getPortletTitle(String portletId, Locale locale);

	public String getPortletTitle(String portletId, String languageId);

	public String getPortletTitle(String portletId, User user);

	public String getPortletXmlFileName();

	public PortletPreferences getPreferences(HttpServletRequest request);

	public PreferencesValidator getPreferencesValidator(Portlet portlet);

	public String getRelativeHomeURL(HttpServletRequest request)
		throws PortalException;

	public ResourceBundle getResourceBundle(Locale locale);

	public long getScopeGroupId(HttpServletRequest request)
		throws PortalException;

	public long getScopeGroupId(HttpServletRequest request, String portletId)
		throws PortalException;

	public long getScopeGroupId(
			HttpServletRequest request, String portletId,
			boolean checkStagingGroup)
		throws PortalException;

	public long getScopeGroupId(Layout layout);

	public long getScopeGroupId(Layout layout, String portletId);

	public long getScopeGroupId(long plid);

	public long getScopeGroupId(PortletRequest portletRequest)
		throws PortalException;

	public User getSelectedUser(HttpServletRequest request)
		throws PortalException;

	public User getSelectedUser(
			HttpServletRequest request, boolean checkPermission)
		throws PortalException;

	public User getSelectedUser(PortletRequest portletRequest)
		throws PortalException;

	public User getSelectedUser(
			PortletRequest portletRequest, boolean checkPermission)
		throws PortalException;

	public String getServletContextName();

	public long[] getSharedContentSiteGroupIds(
			long companyId, long groupId, long userId)
		throws PortalException;

	public Map<String, List<Portlet>> getSiteAdministrationCategoriesMap(
		HttpServletRequest request);

	public PortletURL getSiteAdministrationURL(
		HttpServletRequest request, ThemeDisplay themeDisplay);

	public PortletURL getSiteAdministrationURL(
		HttpServletRequest request, ThemeDisplay themeDisplay,
		String portletName);

	public PortletURL getSiteAdministrationURL(
		PortletResponse portletResponse, ThemeDisplay themeDisplay);

	public PortletURL getSiteAdministrationURL(
		PortletResponse portletResponse, ThemeDisplay themeDisplay,
		String portletName);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getCurrentAndAncestorSiteGroupIds(long)}
	 */
	@Deprecated
	public long[] getSiteAndCompanyGroupIds(long groupId)
		throws PortalException;

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getCurrentAndAncestorSiteGroupIds(long)}
	 */
	@Deprecated
	public long[] getSiteAndCompanyGroupIds(ThemeDisplay themeDisplay)
		throws PortalException;

	public Locale getSiteDefaultLocale(long groupId) throws PortalException;

	public long getSiteGroupId(long groupId) throws PortalException;

	/**
	 * Returns the URL of the login page for the current site if one is
	 * available.
	 *
	 * @param  themeDisplay the theme display for the current page
	 * @return the URL of the login page for the current site, or
	 *         <code>null</code> if one is not available
	 * @throws PortalException if a portal exception occurred
	 */
	public String getSiteLoginURL(ThemeDisplay themeDisplay)
		throws PortalException;

	public String getStaticResourceURL(HttpServletRequest request, String uri);

	public String getStaticResourceURL(
		HttpServletRequest request, String uri, long timestamp);

	public String getStaticResourceURL(
		HttpServletRequest request, String uri, String queryString);

	public String getStaticResourceURL(
		HttpServletRequest request, String uri, String queryString,
		long timestamp);

	public String getStrutsAction(HttpServletRequest request);

	public String[] getSystemGroups();

	public String[] getSystemOrganizationRoles();

	public String[] getSystemRoles();

	public String[] getSystemSiteRoles();

	public String getUniqueElementId(
		HttpServletRequest request, String namespace, String id);

	public String getUniqueElementId(
		PortletRequest request, String namespace, String id);

	public UploadPortletRequest getUploadPortletRequest(
		PortletRequest portletRequest);

	public UploadServletRequest getUploadServletRequest(
		HttpServletRequest request);

	public Date getUptime();

	public String getURLWithSessionId(String url, String sessionId);

	public User getUser(HttpServletRequest request) throws PortalException;

	public User getUser(PortletRequest portletRequest) throws PortalException;

	public String getUserEmailAddress(long userId);

	public long getUserId(HttpServletRequest request);

	public long getUserId(PortletRequest portletRequest);

	public String getUserName(BaseModel<?> baseModel);

	public String getUserName(long userId, String defaultUserName);

	public String getUserName(
		long userId, String defaultUserName, HttpServletRequest request);

	public String getUserName(
		long userId, String defaultUserName, String userAttribute);

	public String getUserName(
		long userId, String defaultUserName, String userAttribute,
		HttpServletRequest request);

	public String getUserPassword(HttpServletRequest request);

	public String getUserPassword(HttpSession session);

	public String getUserPassword(PortletRequest portletRequest);

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public String getUserValue(long userId, String param, String defaultValue);

	public String getValidPortalDomain(long companyId, String domain);

	public long getValidUserId(long companyId, long userId)
		throws PortalException;

	public String getVirtualHostname(LayoutSet layoutSet);

	public String getVirtualLayoutActualURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException;

	public LayoutFriendlyURLComposite getVirtualLayoutFriendlyURLComposite(
			boolean privateLayout, String friendlyURL,
			Map<String, String[]> params, Map<String, Object> requestContext)
		throws PortalException;

	public String getWidgetURL(Portlet portlet, ThemeDisplay themeDisplay)
		throws PortalException;

	public void initCustomSQL();

	public User initUser(HttpServletRequest request) throws Exception;

	public void invokeTaglibDiscussion(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception;

	/**
	 * @deprecated As of 6.2.0, with no direct replacement
	 */
	@Deprecated
	public boolean isAllowAddPortletDefaultResource(
			HttpServletRequest request, Portlet portlet)
		throws PortalException;

	public boolean isCDNDynamicResourcesEnabled(HttpServletRequest request)
		throws PortalException;

	public boolean isCDNDynamicResourcesEnabled(long companyId);

	/**
	 * @deprecated As of 6.1.0, renamed to {@link #isGroupAdmin(User, long)}
	 */
	@Deprecated
	public boolean isCommunityAdmin(User user, long groupId) throws Exception;

	/**
	 * @deprecated As of 6.1.0, renamed to {@link #isGroupOwner(User, long)}
	 */
	@Deprecated
	public boolean isCommunityOwner(User user, long groupId) throws Exception;

	public boolean isCompanyAdmin(User user) throws Exception;

	public boolean isCompanyControlPanelPortlet(
			String portletId, String category, ThemeDisplay themeDisplay)
		throws PortalException;

	public boolean isCompanyControlPanelPortlet(
			String portletId, ThemeDisplay themeDisplay)
		throws PortalException;

	public boolean isCompanyControlPanelVisible(ThemeDisplay themeDisplay)
		throws PortalException;

	public boolean isControlPanelPortlet(
		String portletId, String category, ThemeDisplay themeDisplay);

	public boolean isControlPanelPortlet(
		String portletId, ThemeDisplay themeDisplay);

	public boolean isGroupAdmin(User user, long groupId) throws Exception;

	public boolean isGroupFriendlyURL(
		String fullURL, String groupFriendlyURL, String layoutFriendlyURL);

	public boolean isGroupOwner(User user, long groupId) throws Exception;

	public boolean isLayoutDescendant(Layout layout, long layoutId)
		throws PortalException;

	public boolean isLayoutSitemapable(Layout layout);

	public boolean isLoginRedirectRequired(HttpServletRequest request);

	public boolean isMethodGet(PortletRequest portletRequest);

	public boolean isMethodPost(PortletRequest portletRequest);

	public boolean isMultipartRequest(HttpServletRequest request);

	public boolean isOmniadmin(long userId);

	public boolean isOmniadmin(User user);

	public boolean isReservedParameter(String name);

	public boolean isRightToLeft(HttpServletRequest request);

	public boolean isRSSFeedsEnabled();

	public boolean isSecure(HttpServletRequest request);

	public boolean isSystemGroup(String groupName);

	public boolean isSystemRole(String roleName);

	public boolean isUpdateAvailable();

	public boolean isValidResourceId(String resourceId);

	public boolean removePortalInetSocketAddressEventListener(
		PortalInetSocketAddressEventListener
			portalInetSocketAddressEventListener);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #removePortalInetSocketAddressEventListener(
	 *             PortalInetSocketAddressEventListener)}
	 */
	@Deprecated
	public void removePortalPortEventListener(
		PortalPortEventListener portalPortEventListener);

	public void resetCDNHosts();

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portal.security.auth.AuthTokenWhitelistUtil#resetPortletInvocationWhitelist}
	 */
	@Deprecated
	public Set<String> resetPortletAddDefaultResourceCheckWhitelist();

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portal.security.auth.AuthTokenWhitelistUtil#resetPortletInvocationWhitelistActions}
	 */
	@Deprecated
	public Set<String> resetPortletAddDefaultResourceCheckWhitelistActions();

	public String resetPortletParameters(String url, String portletId);

	public void sendError(
			Exception e, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws IOException;

	public void sendError(
			Exception e, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException;

	public void sendError(
			int status, Exception e, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws IOException;

	public void sendError(
			int status, Exception e, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException;

	public void sendRSSFeedsDisabledError(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException;

	public void sendRSSFeedsDisabledError(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws IOException, ServletException;

	/**
	 * Sets the description for the page, overriding the existing page
	 * description.
	 */
	public void setPageDescription(
		String description, HttpServletRequest request);

	/**
	 * Sets the keywords for the page, overriding the existing page keywords.
	 */
	public void setPageKeywords(String keywords, HttpServletRequest request);

	/**
	 * Sets the subtitle for the page, overriding the existing page subtitle.
	 */
	public void setPageSubtitle(String subtitle, HttpServletRequest request);

	/**
	 * Sets the whole title for the page, overriding the existing page whole
	 * title.
	 */
	public void setPageTitle(String title, HttpServletRequest request);

	public void setPortalInetSocketAddresses(HttpServletRequest request);

	/**
	 * Sets the port obtained on the first request to the portal.
	 *
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #setPortalInetSocketAddresses(HttpServletRequest)}
	 */
	@Deprecated
	public void setPortalPort(HttpServletRequest request);

	public void storePreferences(PortletPreferences portletPreferences)
		throws IOException, ValidatorException;

	public String[] stripURLAnchor(String url, String separator);

	public String transformCustomSQL(String sql);

	public String transformSQL(String sql);

	public void updateImageId(
			BaseModel<?> baseModel, boolean image, byte[] bytes,
			String fieldName, long maxSize, int maxHeight, int maxWidth)
		throws PortalException;

	public PortletMode updatePortletMode(
		String portletId, User user, Layout layout, PortletMode portletMode,
		HttpServletRequest request);

	public String updateRedirect(
		String redirect, String oldPath, String newPath);

	public WindowState updateWindowState(
		String portletId, User user, Layout layout, WindowState windowState,
		HttpServletRequest request);

}