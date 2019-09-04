<%--
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
--%>

<%@ include file="/html/common/themes/init.jsp" %>
<%@ include file="/html/common/themes/top_meta.jspf" %>
<%@ include file="/html/common/themes/top_meta-ext.jsp" %>

<liferay-util:dynamic-include key="/html/common/themes/top_head.jsp#pre" />

<link href="<%= BrowserSnifferUtil.isIe(request) ? StringPool.BLANK : themeDisplay.getPathThemeImages() %>/<%= PropsValues.THEME_SHORTCUT_ICON %>" rel="icon" />

<%-- Available Translations --%>

<%
if (!themeDisplay.isSignedIn() && layout.isPublicLayout()) {
	String completeURL = PortalUtil.getCurrentCompleteURL(request);

	String canonicalURL = PortalUtil.getCanonicalURL(completeURL, themeDisplay, layout, false, false);

	Map<Locale, String> alternateURLs = Collections.emptyMap();

	Set<Locale> availableLocales = LanguageUtil.getAvailableLocales(themeDisplay.getSiteGroupId());

	if (availableLocales.size() > 1) {
		alternateURLs = PortalUtil.getAlternateURLs(canonicalURL, themeDisplay, layout);
	}

	for (LayoutSEOLink layoutSEOLink : LayoutSEOLinkManagerUtil.getLocalizedLayoutSEOLinks(layout, PortalUtil.getLocale(request), canonicalURL, alternateURLs)) {
%>

		<c:choose>
			<c:when test="<%= Validator.isNotNull(layoutSEOLink.getHrefLang()) %>">
				<link data-senna-track="temporary" href="<%= layoutSEOLink.getHref() %>" hreflang="<%= layoutSEOLink.getHrefLang() %>" rel="<%= layoutSEOLink.getRelationship() %>" />
			</c:when>
			<c:otherwise>
				<link data-senna-track="temporary" href="<%= layoutSEOLink.getHref() %>" rel="<%= layoutSEOLink.getRelationship() %>" />
			</c:otherwise>
		</c:choose>

<%
	}
}
%>

<%-- Portal CSS --%>

<link class="lfr-css-file" data-senna-track="temporary" href="<%= HtmlUtil.escapeAttribute(PortalUtil.getStaticResourceURL(request, themeDisplay.getPathThemeCss() + "/clay.css")) %>" id="liferayAUICSS" rel="stylesheet" type="text/css" />

<%
long cssLastModifiedTime = PortalWebResourcesUtil.getLastModified(PortalWebResourceConstants.RESOURCE_TYPE_CSS);
%>

<link data-senna-track="temporary" href="<%= HtmlUtil.escapeAttribute(PortalUtil.getStaticResourceURL(request, themeDisplay.getCDNDynamicResourcesHost() + PortalWebResourcesUtil.getContextPath(PortalWebResourceConstants.RESOURCE_TYPE_CSS) + "/main.css", cssLastModifiedTime)) %>" id="liferayPortalCSS" rel="stylesheet" type="text/css" />

<%
List<Portlet> portlets = null;

if (layoutTypePortlet != null) {
	portlets = layoutTypePortlet.getAllPortlets();
}

if (layout != null) {
	String ppid = ParamUtil.getString(request, "p_p_id");

	if (layout.isTypeEmbedded() || layout.isTypePortlet()) {
		if (themeDisplay.isStateMaximized() || themeDisplay.isStatePopUp() || (layout.isSystem() && Objects.equals(layout.getFriendlyURL(), PropsValues.CONTROL_PANEL_LAYOUT_FRIENDLY_URL))) {
			if (Validator.isNotNull(ppid)) {
				Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), ppid);

				if ((portlet != null) && !portlets.contains(portlet)) {
					portlets.add(portlet);
				}
			}
		}
	}
	else if (layout.isTypeControlPanel() || layout.isTypePanel()) {
		portlets = new ArrayList<Portlet>();

		portlets.addAll(layout.getEmbeddedPortlets());

		if (Validator.isNotNull(ppid)) {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), ppid);

			if ((portlet != null) && !portlets.contains(portlet)) {
				portlets.add(portlet);
			}
		}
	}

	String portletResource = ParamUtil.getString(request, PortalUtil.getPortletNamespace(ppid) + "portletResource");

	if (Validator.isNotNull(portletResource)) {
		Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletResource);

		if ((portlet != null) && !portlets.contains(portlet)) {
			portlets.add(portlet);
		}
	}

	Iterator<Portlet> portletsIterator = portlets.iterator();

	LayoutTypeAccessPolicy layoutTypeAccessPolicy = LayoutTypeAccessPolicyTracker.getLayoutTypeAccessPolicy(layout);

	while (portletsIterator.hasNext()) {
		Portlet portlet = portletsIterator.next();

		try {
			layoutTypeAccessPolicy.checkAccessAllowedToPortlet(request, layout, portlet);
		}
		catch (PrincipalException pe) {
			portletsIterator.remove();
		}
	}

	request.setAttribute(WebKeys.LAYOUT_PORTLETS, portlets);
}
%>

<%-- Portlet CSS References --%>

<%@ include file="/html/common/themes/top_portlet_resources_css.jspf" %>

<%-- Portal JavaScript References --%>

<%@ include file="/html/common/themes/top_js.jspf" %>
<%@ include file="/html/common/themes/top_js-ext.jspf" %>

<%-- Portlet JavaScript References --%>

<%@ include file="/html/common/themes/top_portlet_resources_js.jspf" %>

<%-- Raw Text --%>

<%
List<String> markupHeaders = (List<String>)request.getAttribute(MimeResponse.MARKUP_HEAD_ELEMENT);

if (markupHeaders != null) {
	for (String markupHeader : markupHeaders) {
%>

		<%= markupHeader %>

<%
	}
}

com.liferay.petra.string.StringBundler pageTopSB = OutputTag.getDataSB(request, WebKeys.PAGE_TOP);
%>

<c:if test="<%= pageTopSB != null %>">

	<%
	pageTopSB.writeTo(out);
	%>

</c:if>

<%
boolean portletHubRequired = false;

for (Portlet portlet : portlets) {
	List<PortletDependency> portletDependencies = portlet.getPortletDependencies();

	for (PortletDependency portletDependency : portletDependencies) {
		if (Objects.equals(portletDependency.getName(), "PortletHub") && Objects.equals(portletDependency.getScope(), "javax.portlet")) {
			portletHubRequired = true;

			break;
		}
	}

	if (portletHubRequired) {
		break;
	}
}
%>

<c:if test="<%= portletHubRequired %>">
	<script type="text/javascript">
		var portlet = portlet || {};

		portlet.data = portlet.data || {};

		portlet.data.pageRenderState = <%= RenderStateUtil.generateJSON(request, themeDisplay) %>;
	</script>
</c:if>

<%-- Theme CSS --%>

<link class="lfr-css-file" data-senna-track="temporary" href="<%= HtmlUtil.escapeAttribute(PortalUtil.getStaticResourceURL(request, themeDisplay.getPathThemeCss() + "/main.css")) %>" id="liferayThemeCSS" rel="stylesheet" type="text/css" />

<%-- User Inputted Layout CSS --%>

<c:if test="<%= (layout != null) && Validator.isNotNull(layout.getCssText()) %>">
	<style data-senna-track="temporary" type="text/css">
		<%= _escapeCssBlock(layout.getCssText()) %>
	</style>
</c:if>

<%-- User Inputted Portlet CSS --%>

<c:if test="<%= portlets != null %>">
	<style data-senna-track="temporary" type="text/css">

		<%
		for (Portlet portlet : portlets) {
			PortletPreferences portletSetup = themeDisplay.getStrictLayoutPortletSetup(layout, portlet.getPortletId());

			String portletSetupCss = portletSetup.getValue("portletSetupCss", StringPool.BLANK);
		%>

			<c:if test="<%= Validator.isNotNull(portletSetupCss) %>">

				<%
				try {
				%>

					<%@ include file="/html/common/themes/portlet_css.jspf" %>

				<%
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn(e.getMessage());
					}
				}
				%>

			</c:if>

		<%
		}
		%>

	</style>
</c:if>

<liferay-util:dynamic-include key="/html/common/themes/top_head.jsp#post" />

<%!
private String _escapeCssBlock(String css) {
	return StringUtil.replace(css, new String[] {"<", "expression("}, new String[] {"\\3c", ""});
}

private static Log _log = LogFactoryUtil.getLog("portal_web.docroot.html.common.themes.top_head_jsp");
%>