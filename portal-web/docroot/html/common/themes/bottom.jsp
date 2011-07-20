<%--
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
--%>

<%@ include file="/html/common/init.jsp" %>

<%@ page import="com.liferay.taglib.aui.ScriptTag" %>

<%-- Portal JavaScript --%>

<%-- Portlet CSS References --%>

<%
List<Portlet> portlets = (List<Portlet>)request.getAttribute(WebKeys.LAYOUT_PORTLETS);
%>

<c:if test="<%= portlets != null %>">

	<%
	Set<String> footerPortalCssSet = new LinkedHashSet<String>();

	for (Portlet portlet : portlets) {
		for (String footerPortalCss : portlet.getFooterPortalCss()) {
			if (!HttpUtil.hasProtocol(footerPortalCss)) {
				Portlet rootPortlet = portlet.getRootPortlet();

				footerPortalCss = PortalUtil.getStaticResourceURL(request, PortalUtil.getPathContext() + footerPortalCss, rootPortlet.getTimestamp());
			}

			if (!footerPortalCssSet.contains(footerPortalCss)) {
				footerPortalCssSet.add(footerPortalCss);
	%>

				<link href="<%= HtmlUtil.escape(footerPortalCss) %>" rel="stylesheet" type="text/css" />

	<%
			}
		}
	}

	Set<String> footerPortletCssSet = new LinkedHashSet<String>();

	for (Portlet portlet : portlets) {
		for (String footerPortletCss : portlet.getFooterPortletCss()) {
			if (!HttpUtil.hasProtocol(footerPortletCss)) {
				Portlet rootPortlet = portlet.getRootPortlet();

				footerPortletCss = PortalUtil.getStaticResourceURL(request, portlet.getStaticResourcePath() + footerPortletCss, rootPortlet.getTimestamp());
			}

			if (!footerPortletCssSet.contains(footerPortletCss)) {
				footerPortletCssSet.add(footerPortletCss);
	%>

				<link href="<%= HtmlUtil.escape(footerPortletCss) %>" rel="stylesheet" type="text/css" />

	<%
			}
		}
	}
	%>

</c:if>

<%-- Portlet JavaScript References --%>

<c:if test="<%= portlets != null %>">

	<%
	Set<String> footerPortalJavaScriptSet = new LinkedHashSet<String>();

	for (Portlet portlet : portlets) {
		for (String footerPortalJavaScript : portlet.getFooterPortalJavaScript()) {
			if (!HttpUtil.hasProtocol(footerPortalJavaScript)) {
				Portlet rootPortlet = portlet.getRootPortlet();

				footerPortalJavaScript = PortalUtil.getStaticResourceURL(request, PortalUtil.getPathContext() + footerPortalJavaScript, rootPortlet.getTimestamp());
			}

			if (!footerPortalJavaScriptSet.contains(footerPortalJavaScript) && !themeDisplay.isIncludedJs(footerPortalJavaScript)) {
				footerPortalJavaScriptSet.add(footerPortalJavaScript);
	%>

				<script src="<%= HtmlUtil.escape(footerPortalJavaScript) %>" type="text/javascript"></script>

	<%
			}
		}
	}

	Set<String> footerPortletJavaScriptSet = new LinkedHashSet<String>();

	for (Portlet portlet : portlets) {
		for (String footerPortletJavaScript : portlet.getFooterPortletJavaScript()) {
			if (!HttpUtil.hasProtocol(footerPortletJavaScript)) {
				Portlet rootPortlet = portlet.getRootPortlet();

				footerPortletJavaScript = PortalUtil.getStaticResourceURL(request, portlet.getStaticResourcePath() + footerPortletJavaScript, rootPortlet.getTimestamp());
			}

			if (!footerPortletJavaScriptSet.contains(footerPortletJavaScript)) {
				footerPortletJavaScriptSet.add(footerPortletJavaScript);
	%>

				<script src="<%= HtmlUtil.escape(footerPortletJavaScript) %>" type="text/javascript"></script>

	<%
			}
		}
	}
	%>

</c:if>

<c:if test="<%= PropsValues.JAVASCRIPT_LOG_ENABLED %>">
	<%@ include file="/html/common/themes/bottom_js_logging.jspf" %>
</c:if>

<%@ include file="/html/common/themes/bottom_js.jspf" %>

<%@ include file="/html/common/themes/session_timeout.jspf" %>

<%
ScriptTag.flushScriptData(pageContext);
%>

<%-- Raw Text --%>

<%
StringBundler pageBottomSB = (StringBundler)request.getAttribute(WebKeys.PAGE_BOTTOM);
%>

<c:if test="<%= pageBottomSB != null %>">

	<%
	pageBottomSB.writeTo(out);
	%>

</c:if>

<%-- Theme JavaScript --%>

<script src="<%= HtmlUtil.escape(PortalUtil.getStaticResourceURL(request, themeDisplay.getPathThemeJavaScript() + "/main.js")) %>" type="text/javascript"></script>

<c:if test="<%= layout != null %>">

	<%-- User Inputted Layout and LayoutSet JavaScript --%>

	<%
	LayoutSet layoutSet = themeDisplay.getLayoutSet();

	UnicodeProperties layoutSetSettings = layoutSet.getSettingsProperties();

	UnicodeProperties layoutTypeSettings = layout.getTypeSettingsProperties();
	%>

	<script type="text/javascript">
		// <![CDATA[
			<%= GetterUtil.getString(layoutSetSettings.getProperty("javascript")) %>

			<%= GetterUtil.getString(layoutTypeSettings.getProperty("javascript")) %>
		// ]]>
	</script>

	<%-- Google Analytics --%>

	<%
	UnicodeProperties groupTypeSettings = layout.getGroup().getTypeSettingsProperties();

	String googleAnalyticsId = groupTypeSettings.getProperty("googleAnalyticsId");

	if (Validator.isNotNull(googleAnalyticsId)) {
	%>

		<script type="text/javascript">
			var _gaq = _gaq || [];

			_gaq.push(['_setAccount', '<%= googleAnalyticsId %>']);
			_gaq.push(['_trackPageview']);

			(function() {
				var ga = document.createElement('script');

				ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
				ga.setAttribute('async', 'true');

				document.documentElement.firstChild.appendChild(ga);
			})();
		</script>

	<%
	}
	%>

</c:if>

<c:if test="<%= PropsValues.MONITORING_PORTAL_REQUEST %>">
	<%@ include file="/html/common/themes/bottom_monitoring.jspf" %>
</c:if>

<liferay-util:include page="/html/common/themes/bottom-ext.jsp" />