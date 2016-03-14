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

<%@ include file="/init.jsp" %>

<%
String tabs2 = ParamUtil.getString(request, "tabs2", "user-settings");
%>

<aui:nav-bar markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="userSettingsURL">
			<portlet:param name="tabs2" value="user-settings" />
		</liferay-portlet:renderURL>

		<aui:nav-item
			href="<%= userSettingsURL %>"
			label="user-settings"
			selected='<%= tabs2.equals("user-settings") %>'
		/>

		<c:if test="<%= PortalUtil.isRSSFeedsEnabled() %>">
			<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="rssURL">
				<portlet:param name="tabs2" value="rss" />
			</liferay-portlet:renderURL>

			<aui:nav-item
				href="<%= rssURL %>"
				label="rss"
				selected='<%= tabs2.equals("rss") %>'
			/>
		</c:if>
	</aui:nav>
</aui:nav-bar>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<aui:form action="<%= configurationActionURL %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveConfiguration();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />

	<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL">
		<portlet:param name="tabs2" value="<%= tabs2 %>" />
	</liferay-portlet:renderURL>

	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<c:choose>
		<c:when test='<%= tabs2.equals("user-settings") %>'>
			<%@ include file="/configuration/user_settings.jspf" %>
		</c:when>
		<c:when test='<%= tabs2.equals("rss") %>'>
			<%@ include file="/configuration/rss.jspf" %>
		</c:when>
	</c:choose>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveConfiguration() {
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>