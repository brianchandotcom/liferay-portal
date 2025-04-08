/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.servlet.taglib;

import com.liferay.exportimport.kernel.staging.Staging;
import com.liferay.layout.seo.kernel.LayoutSEOLink;
import com.liferay.layout.seo.kernel.LayoutSEOLinkManager;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.content.security.policy.ContentSecurityPolicyNonceProviderUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlag;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManager;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.VirtualLayout;
import com.liferay.portal.kernel.security.auth.AuthToken;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.LayoutPermission;
import com.liferay.portal.kernel.servlet.BrowserMetadata;
import com.liferay.portal.kernel.servlet.PortalWebResourceConstants;
import com.liferay.portal.kernel.servlet.PortalWebResourcesUtil;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.configuration.UploadServletRequestConfigurationProvider;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.UnicodeFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.servlet.BrowserSnifferUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.ShutdownUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;

import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(
	property = "service.ranking:Integer=" + Integer.MAX_VALUE,
	service = DynamicInclude.class
)
public class LiferayGlobalObjectPreAUIDynamicInclude
	extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		PrintWriter printWriter = httpServletResponse.getWriter();

		printWriter.print("<script");
		printWriter.print(
			ContentSecurityPolicyNonceProviderUtil.getNonceAttribute(
				httpServletRequest));
		printWriter.print(" data-senna-track=\"temporary\"");
		printWriter.println(" type=\"text/javascript\">");

		StringBuilder definitionSB = new StringBuilder();

		try {
			_renderLiferayAUI(definitionSB, httpServletRequest);
			_renderLiferayBrowser(definitionSB, httpServletRequest);
			_renderLiferayData(definitionSB, httpServletRequest);
			_renderLiferayFeatureFlags(definitionSB, httpServletRequest);
			_renderLiferayLanguage(definitionSB);
			_renderLiferayPortlet(definitionSB);
			_renderLiferayPortletKeys(definitionSB);
			_renderLiferayPropsValues(definitionSB, httpServletRequest);
			_renderLiferayThemeDisplay(definitionSB, httpServletRequest);
			_renderLiferayUtil(definitionSB);

			_renderValue(
				definitionSB, "authToken",
				_authToken.getToken(httpServletRequest));

			String currentURL = _portal.getCurrentURL(httpServletRequest);

			_renderValue(definitionSB, "currentURL", currentURL);
			_renderValue(
				definitionSB, "currentURLEncoded",
				HtmlUtil.escapeJS(URLCodec.encodeURL(currentURL)));
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to render Liferay global object", portalException);
		}

		String requestURL = String.valueOf(httpServletRequest.getRequestURL());

		printWriter.println(
			StringUtil.replace(
				_LIFERAY_TPL, new String[] {"[$DEFINITION$]", "[$DEV_MODE$]"},
				new Object[] {
					definitionSB, requestURL.startsWith("http://localhost")
				}));

		printWriter.println("</script>");
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"/html/common/themes/top_js.jspf#resources");
	}

	private static String _loadTemplate(String name) {
		try (InputStream inputStream =
				LiferayGlobalObjectPreAUIDynamicInclude.class.
					getResourceAsStream("dependencies/" + name)) {

			return StringUtil.read(inputStream);
		}
		catch (Exception exception) {
			_log.error("Unable to read template " + name, exception);
		}

		return StringPool.BLANK;
	}

	private String _getDateFormatPattern(Locale locale) {
		String languageId = LocaleUtil.toLanguageId(locale);

		String dateFormatPattern = _dateFormatPatterns.get(languageId);

		if (dateFormatPattern != null) {
			return dateFormatPattern;
		}

		SimpleDateFormat simpleDateFormat =
			(SimpleDateFormat)DateFormat.getDateInstance(
				DateFormat.SHORT, locale);

		dateFormatPattern = simpleDateFormat.toPattern();

		String delimiterString = StringPool.FORWARD_SLASH;
		boolean endDelimiter = false;

		for (char dateDelimiter : _DATE_DELIMITERS) {
			if (dateFormatPattern.indexOf(dateDelimiter) != -1) {
				delimiterString = String.valueOf(dateDelimiter);

				endDelimiter = dateFormatPattern.endsWith(delimiterString);

				break;
			}
		}

		int dayIndex = dateFormatPattern.indexOf('d');
		int monthIndex = dateFormatPattern.indexOf('M');
		int yearIndex = dateFormatPattern.indexOf('y');

		if ((yearIndex < dayIndex) && (yearIndex < monthIndex)) {
			dateFormatPattern = StringBundler.concat(
				"%Y", delimiterString, "%m", delimiterString, "%d");
		}
		else if (dayIndex < monthIndex) {
			dateFormatPattern = StringBundler.concat(
				"%d", delimiterString, "%m", delimiterString, "%Y");
		}
		else {
			dateFormatPattern = StringBundler.concat(
				"%m", delimiterString, "%d", delimiterString, "%Y");
		}

		if (endDelimiter) {
			dateFormatPattern += delimiterString;
		}

		_dateFormatPatterns.put(languageId, dateFormatPattern);

		return dateFormatPattern;
	}

	private void _renderLiferayAUI(
		StringBuilder sb, HttpServletRequest httpServletRequest) {

		sb.append("AUI: {");

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		_renderMethod(sb, "getCombine", themeDisplay.isThemeJsFastLoad());

		long jsLastModified = PortalWebResourcesUtil.getLastModified(
			PortalWebResourceConstants.RESOURCE_TYPE_JS);

		String comboURL = _portal.getStaticResourceURL(
			httpServletRequest,
			themeDisplay.getCDNDynamicResourcesHost() +
				themeDisplay.getPathContext() + "/combo/",
			"minifierType=", jsLastModified);

		_renderMethod(sb, "getComboPath", comboURL + "&");

		_renderMethod(
			sb, "getDateFormat",
			_getDateFormatPattern(themeDisplay.getLocale()));
		_renderMethod(
			sb, "getEditorCKEditorPath",
			PortalWebResourcesUtil.getContextPath(
				PortalWebResourceConstants.RESOURCE_TYPE_EDITOR_CKEDITOR));

		String filter = "raw";

		if (themeDisplay.isThemeJsFastLoad()) {
			filter = "min";
		}
		else if (PropsValues.JAVASCRIPT_LOG_ENABLED) {
			filter = "debug";
		}

		_renderMethod(sb, "getFilter", filter);

		String staticResourceURLParams = _portal.getStaticResourceURL(
			httpServletRequest, StringPool.BLANK, "minifierType=",
			jsLastModified);

		if (themeDisplay.isThemeJsFastLoad()) {
			_renderMethod(sb, "getFilterConfig", null);
		}
		else {
			sb.append("getFilterConfig: () => ({");

			_renderMethod(sb, "replaceStr", ".js" + staticResourceURLParams);
			_renderMethod(sb, "searchExp", "\\\\.js$");

			sb.append("}),");
		}

		_renderMethod(
			sb, "getJavaScriptRootPath", themeDisplay.getPathJavaScript());
		_renderMethod(
			sb, "getPortletRootPath",
			themeDisplay.getPathContext() + "/html/portlet");
		_renderMethod(
			sb, "getStaticResourceURLParams", staticResourceURLParams);

		sb.append("},");
	}

	private void _renderLiferayBrowser(
		StringBuilder sb, HttpServletRequest httpServletRequest) {

		sb.append("Browser: {");

		_renderMethod(
			sb, "acceptsGzip",
			BrowserSnifferUtil.acceptsGzip(httpServletRequest));

		String version = BrowserSnifferUtil.getVersion(httpServletRequest);

		_renderMethod(sb, "getMajorVersion", version.isEmpty() ? "0" : version);

		_renderMethod(
			sb, "getRevision",
			BrowserSnifferUtil.getRevision(httpServletRequest));
		_renderMethod(sb, "getVersion", version);

		BrowserMetadata browserMetadata = BrowserSnifferUtil.getBrowserMetadata(
			httpServletRequest);

		_renderMethod(sb, "isAir", browserMetadata.isAir());
		_renderMethod(sb, "isChrome", browserMetadata.isChrome());
		_renderMethod(sb, "isEdge", browserMetadata.isEdge());
		_renderMethod(sb, "isFirefox", browserMetadata.isFirefox());
		_renderMethod(sb, "isGecko", browserMetadata.isGecko());
		_renderMethod(sb, "isIe", browserMetadata.isIe());
		_renderMethod(sb, "isIphone", browserMetadata.isIphone());
		_renderMethod(sb, "isLinux", browserMetadata.isLinux());
		_renderMethod(sb, "isMac", browserMetadata.isMac());
		_renderMethod(sb, "isMobile", browserMetadata.isMobile());
		_renderMethod(sb, "isMozilla", browserMetadata.isMozilla());
		_renderMethod(sb, "isOpera", browserMetadata.isOpera());
		_renderMethod(sb, "isRtf", browserMetadata.isRtf(version));
		_renderMethod(sb, "isSafari", browserMetadata.isSafari());
		_renderMethod(sb, "isSun", browserMetadata.isSun());
		_renderMethod(sb, "isWebKit", browserMetadata.isWebKit());
		_renderMethod(sb, "isWindows", browserMetadata.isWindows());

		sb.append("},");
	}

	private void _renderLiferayData(
			StringBuilder sb, HttpServletRequest httpServletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		sb.append("Data: {");

		_renderValue(sb, "ICONS_INLINE_SVG", true);
		_renderValue(sb, "NAV_SELECTOR", "#navigation");
		_renderValue(sb, "NAV_SELECTOR_MOBILE", "#navigationCollapse");

		LayoutTypePortlet layoutTypePortlet =
			themeDisplay.getLayoutTypePortlet();

		_renderMethod(
			sb, "isCustomizationView",
			layoutTypePortlet.isCustomizable() &&
			_layoutPermission.contains(
				themeDisplay.getPermissionChecker(), themeDisplay.getLayout(),
				ActionKeys.CUSTOMIZE));

		sb.append("notices: [");

		if (ShutdownUtil.isInProcess()) {
			sb.append("{title:'");

			Locale locale = themeDisplay.getLocale();

			sb.append(_language.get(locale, "maintenance-alert"));

			sb.append("<span class=\"mx-2\">");

			Format format = FastDateFormatFactoryUtil.getTime(locale);

			TimeZone timeZone = themeDisplay.getTimeZone();

			sb.append(
				format.format(
					Time.getDate(CalendarFactoryUtil.getCalendar(timeZone))));

			sb.append(StringPool.SPACE);

			sb.append(timeZone.getDisplayName(false, TimeZone.SHORT, locale));

			sb.append("</span>',message:'");

			sb.append(
				_language.format(
					locale,
					_language.get(
						locale,
						"the-portal-will-shutdown-for-maintenance-in-x-" +
							"minutes"),
					new Object[] {
						String.valueOf(
							(ShutdownUtil.getInProcess() / Time.MINUTE) + 1)
					},
					false));

			if (Validator.isNotNull(ShutdownUtil.getMessage())) {
				sb.append("<span class=\"custom-shutdown-message\">");
				sb.append(HtmlUtil.escape(ShutdownUtil.getMessage()));
				sb.append("</span>");
			}

			sb.append("',type:'warning'},");
		}

		sb.append("],");

		sb.append("},");
	}

	private void _renderLiferayFeatureFlags(
		StringBuilder sb, HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		sb.append("FeatureFlags: {");

		for (FeatureFlag featureFlag :
				_featureFlagManager.getFeatureFlags(
					themeDisplay.getCompanyId(), featureFlag -> true)) {

			sb.append(StringPool.APOSTROPHE);
			sb.append(featureFlag.getKey());
			sb.append("':");
			sb.append(featureFlag.isEnabled());
			sb.append(StringPool.COMMA);
		}

		sb.append("},");
	}

	private void _renderLiferayLanguage(StringBuilder sb) {
		sb.append("Language: {");

		sb.append(_LANGUAGE_PROLOG);

		sb.append("available: {");

		for (Locale locale : _language.getAvailableLocales()) {
			String languageId = LocaleUtil.toLanguageId(locale);

			sb.append(StringPool.APOSTROPHE);
			sb.append(languageId);
			sb.append("':'");
			sb.append(HtmlUtil.escapeJS(locale.getDisplayName(locale)));
			sb.append("',");
		}

		sb.append("},");

		sb.append("direction: {");

		for (Locale locale : _language.getAvailableLocales()) {
			String languageId = LocaleUtil.toLanguageId(locale);

			sb.append(StringPool.APOSTROPHE);
			sb.append(languageId);
			sb.append("':'");
			sb.append(_language.get(locale, "lang.dir"));
			sb.append("',");
		}

		sb.append("},");

		sb.append("},");
	}

	private void _renderLiferayPortlet(StringBuilder sb) {
		sb.append("Portlet: {");

		_renderStub(
			sb, "openModal", "frontend-js-components-web", "openPortletModal");
		_renderStub(
			sb, "openWindow", "frontend-js-components-web",
			"openPortletWindow");

		sb.append("},");
	}

	private void _renderLiferayPortletKeys(StringBuilder sb) {
		sb.append("PortletKeys: {");

		_renderValue(sb, "DOCUMENT_LIBRARY", PortletKeys.DOCUMENT_LIBRARY);
		_renderValue(
			sb, "DYNAMIC_DATA_MAPPING",
			"com_liferay_dynamic_data_mapping_web_portlet_DDMPortlet");
		_renderValue(
			sb, "INSTANCE_SETTINGS",
			"com_liferay_configuration_admin_web_portlet_" +
				"InstanceSettingsPortlet");
		_renderValue(sb, "ITEM_SELECTOR", PortletKeys.ITEM_SELECTOR);

		sb.append("},");
	}

	private void _renderLiferayPropsValues(
		StringBuilder sb, HttpServletRequest httpServletRequest) {

		sb.append("PropsValues: {");

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		_renderValue(
			sb, "JAVASCRIPT_SINGLE_PAGE_APPLICATION_TIMEOUT",
			_prefsProps.getInteger(
				themeDisplay.getCompanyId(),
				PropsKeys.JAVASCRIPT_SINGLE_PAGE_APPLICATION_TIMEOUT,
				PropsValues.JAVASCRIPT_SINGLE_PAGE_APPLICATION_TIMEOUT));

		_renderValue(
			sb, "UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE",
			_uploadServletRequestConfigurationProvider.getMaxSize());

		sb.append("},");
	}

	private void _renderLiferayThemeDisplay(
			StringBuilder sb, HttpServletRequest httpServletRequest)
		throws PortalException {

		sb.append("ThemeDisplay: {");

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if (layout != null) {
			_renderMethod(sb, "getLayoutId", layout.getLayoutId());

			Layout controlPanelLayout = themeDisplay.getControlPanelLayout();

			_renderMethod(
				sb, "getLayoutRelativeControlPanelURL",
				_portal.getLayoutRelativeURL(
					new VirtualLayout(
						controlPanelLayout, themeDisplay.getScopeGroup()),
					themeDisplay));

			_renderMethod(
				sb, "getLayoutRelativeURL",
				_portal.getLayoutRelativeURL(layout, themeDisplay));
			_renderMethod(
				sb, "getLayoutURL", _portal.getLayoutURL(layout, themeDisplay));

			_renderMethod(sb, "getParentLayoutId", layout.getParentLayoutId());
			_renderMethod(sb, "isControlPanel", layout.isTypeControlPanel());
			_renderMethod(sb, "isPrivateLayout", layout.isPrivateLayout());
			_renderMethod(
				sb, "isVirtualLayout", layout instanceof VirtualLayout);
		}

		_renderMethod(
			sb, "getBCP47LanguageId",
			_language.getBCP47LanguageId(httpServletRequest));

		String completeURL = _portal.getCurrentCompleteURL(httpServletRequest);

		LayoutSEOLink layoutSEOLink =
			_layoutSEOLinkManager.getCanonicalLayoutSEOLink(
				layout, themeDisplay.getLocale(),
				_portal.getCanonicalURL(
					completeURL, themeDisplay, layout, false, false),
				themeDisplay);

		_renderMethod(
			sb, "getCanonicalURL", HtmlUtil.escapeJS(layoutSEOLink.getHref()));

		_renderMethod(sb, "getCDNBaseURL", themeDisplay.getCDNBaseURL());
		_renderMethod(
			sb, "getCDNDynamicResourcesHost",
			themeDisplay.getCDNDynamicResourcesHost());
		_renderMethod(sb, "getCDNHost", themeDisplay.getCDNHost());
		_renderMethod(
			sb, "getCompanyGroupId", themeDisplay.getCompanyGroupId());
		_renderMethod(sb, "getCompanyId", themeDisplay.getCompanyId());
		_renderMethod(
			sb, "getDefaultLanguageId",
			LocaleUtil.toLanguageId(themeDisplay.getSiteDefaultLocale()));
		_renderMethod(
			sb, "getDoAsUserIdEncoded",
			UnicodeFormatter.toString(themeDisplay.getDoAsUserId()));
		_renderMethod(
			sb, "getLanguageId", _language.getLanguageId(httpServletRequest));
		_renderMethod(sb, "getParentGroupId", themeDisplay.getSiteGroupId());
		_renderMethod(sb, "getPathContext", themeDisplay.getPathContext());
		_renderMethod(sb, "getPathImage", themeDisplay.getPathImage());

		_renderMethod(
			sb, "getPathJavaScript", themeDisplay.getPathJavaScript());
		_renderMethod(sb, "getPathMain", themeDisplay.getPathMain());
		_renderMethod(
			sb, "getPathThemeImages", themeDisplay.getPathThemeImages());
		_renderMethod(sb, "getPathThemeRoot", themeDisplay.getPathThemeRoot());
		_renderMethod(sb, "getPlid", themeDisplay.getPlid());
		_renderMethod(sb, "getPortalURL", themeDisplay.getPortalURL());
		_renderMethod(sb, "getRealUserId", themeDisplay.getRealUserId());
		_renderMethod(sb, "getRemoteAddr", themeDisplay.getRemoteAddr());
		_renderMethod(sb, "getRemoteHost", themeDisplay.getRemoteHost());

		Group scopeGroup = themeDisplay.getScopeGroup();

		_renderMethod(sb, "getScopeGroupId", scopeGroup.getGroupId());

		Group liveGroup = _staging.getLiveGroup(scopeGroup);

		_renderMethod(
			sb, "getScopeGroupIdOrLiveGroupId", liveGroup.getGroupId());

		HttpSession httpSession = httpServletRequest.getSession(true);

		_renderMethod(
			sb, "getSessionId",
			PropsValues.SESSION_ENABLE_URL_WITH_SESSION_ID ?
				httpSession.getId() : StringPool.BLANK);

		_renderMethod(
			sb, "getSiteAdminURL",
			_portal.getSiteAdminURL(themeDisplay, StringPool.BLANK, null));
		_renderMethod(sb, "getSiteGroupId", themeDisplay.getSiteGroupId());

		TimeZone timeZone = themeDisplay.getTimeZone();

		if (timeZone != null) {
			_renderMethod(sb, "getTimeZone", timeZone.getID());
		}
		else {
			TimeZone defaultTimeZone = TimeZone.getDefault();

			_renderMethod(sb, "getTimeZone", defaultTimeZone.getID());
		}

		_renderMethod(
			sb, "getURLControlPanel", themeDisplay.getURLControlPanel());
		_renderMethod(
			sb, "getURLHome", HtmlUtil.escapeJS(themeDisplay.getURLHome()));

		User user = themeDisplay.getUser();

		_renderMethod(
			sb, "getUserEmailAddress",
			themeDisplay.isSignedIn() ?
				HtmlUtil.escapeJS(user.getEmailAddress()) : StringPool.BLANK);

		_renderMethod(sb, "getUserId", themeDisplay.getUserId());
		_renderMethod(
			sb, "getUserName",
			themeDisplay.isSignedIn() ?
				UnicodeFormatter.toString(user.getFullName()) :
					StringPool.BLANK);
		_renderMethod(
			sb, "isAddSessionIdToURL", themeDisplay.isAddSessionIdToURL());
		_renderMethod(sb, "isImpersonated", themeDisplay.isImpersonated());
		_renderMethod(sb, "isSignedIn", themeDisplay.isSignedIn());

		_renderMethod(
			sb, "isStagedPortlet",
			Validator.isNotNull(themeDisplay.getPpid()) ?
				liveGroup.isStagedPortlet(themeDisplay.getPpid()) : false);
		_renderMethod(sb, "isStateExclusive", themeDisplay.isStateExclusive());
		_renderMethod(sb, "isStateMaximized", themeDisplay.isStateMaximized());
		_renderMethod(sb, "isStatePopUp", themeDisplay.isStatePopUp());

		sb.append("},");
	}

	private void _renderLiferayUtil(StringBuilder sb) {
		sb.append("Util: {");

		_renderStub(
			sb, "openAlertModal", "frontend-js-components-web",
			"openAlertModal");
		_renderStub(
			sb, "openConfirmModal", "frontend-js-components-web",
			"openConfirmModal");
		_renderStub(sb, "openModal", "frontend-js-components-web", "openModal");
		_renderStub(
			sb, "openSelectionModal", "frontend-js-components-web",
			"openSelectionModal");
		_renderStub(
			sb, "openSimpleInputModal", "frontend-js-components-web",
			"openSimpleInputModal");
		_renderStub(sb, "openToast", "frontend-js-components-web", "openToast");

		sb.append("},");
	}

	private void _renderMethod(
		StringBuilder sb, String methodName, Object value) {

		sb.append(methodName);
		sb.append(": () => ");

		if (value == null) {
			sb.append("null");
		}
		else if (value instanceof String) {
			sb.append(StringPool.APOSTROPHE);
			sb.append((String)value);
			sb.append(StringPool.APOSTROPHE);
		}
		else {
			sb.append(value.toString());
		}

		sb.append(StringPool.COMMA);
	}

	private void _renderStub(
		StringBuilder sb, String methodName, String contextPath,
		String symbol) {

		sb.append(methodName);
		sb.append(": buildESMStub('");
		sb.append(contextPath);
		sb.append("', '");
		sb.append(symbol);
		sb.append("'),");
	}

	private void _renderValue(
		StringBuilder sb, String fieldName, Object value) {

		sb.append(fieldName);
		sb.append(StringPool.COLON);

		if (value == null) {
			sb.append("null");
		}
		else if (value instanceof String) {
			sb.append(StringPool.APOSTROPHE);
			sb.append((String)value);
			sb.append(StringPool.APOSTROPHE);
		}
		else {
			sb.append(value.toString());
		}

		sb.append(StringPool.COMMA);
	}

	private static final char[] _DATE_DELIMITERS = {
		CharPool.DASH, CharPool.FORWARD_SLASH, CharPool.PERIOD
	};

	private static final String _LANGUAGE_PROLOG;

	private static final String _LIFERAY_TPL;

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayGlobalObjectPreAUIDynamicInclude.class);

	private static final Map<String, String> _dateFormatPatterns =
		new ConcurrentHashMap<>();

	static {
		_LIFERAY_TPL = _loadTemplate("Liferay.tpl");
		_LANGUAGE_PROLOG = _loadTemplate("Language.prolog.tpl");
	}

	@Reference
	private AuthToken _authToken;

	@Reference
	private FeatureFlagManager _featureFlagManager;

	@Reference
	private Language _language;

	@Reference
	private LayoutPermission _layoutPermission;

	@Reference
	private LayoutSEOLinkManager _layoutSEOLinkManager;

	@Reference
	private Portal _portal;

	@Reference
	private PrefsProps _prefsProps;

	@Reference
	private Staging _staging;

	@Reference
	private UploadServletRequestConfigurationProvider
		_uploadServletRequestConfigurationProvider;

}