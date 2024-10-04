<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String netvibesURL = ParamUtil.getString(request, "netvibesURL");
String openSocialURL = ParamUtil.getString(request, "openSocialURL");
String widgetURL = ParamUtil.getString(request, "widgetURL");
%>

<c:choose>
	<c:when test="<%= Validator.isNotNull(widgetURL) %>">
		<p>
			<liferay-ui:message key="share-this-application-on-any-website" />
		</p>

		<textarea class="col-md-12 lfr-textarea" onClick="this.select();" rows="10">
			<iframe frameborder="0" height="100%" src="<%= HtmlUtil.escapeAttribute(widgetURL) %>" width="100%"></iframe>
		</textarea>
	</c:when>
	<c:when test="<%= Validator.isNotNull(netvibesURL) %>">
		<p>
			<aui:a
				href='<%=
			HttpComponentsUtil.addParameter("http://eco.netvibes.com/apps/submit/info", "url", netvibesURL) %>' target="_blank"><liferay-ui:message key="add-this-application-to-netvibes" /></aui:a
			>
		</p>

		<aui:input label="" name="netvibesURL" type="resource" value="<%= netvibesURL %>" />
	</c:when>
	<c:when test="<%= Validator.isNotNull(openSocialURL) %>">
		<p>
			<liferay-ui:message key="share-this-application-on-an-open-social-platform" />
		</p>

		<aui:input label="" name="openSocialURL" type="resource" value="<%= openSocialURL %>" />
	</c:when>
</c:choose>