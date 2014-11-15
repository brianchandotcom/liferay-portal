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

<%@ include file="/html/portlet/blogs/init.jsp" %>

<%
blogsPortletInstanceSettings = BlogsPortletInstanceSettings.getInstance(layout, portletId, request.getParameterMap());
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL">
	<liferay-portlet:param name="settingsScope" value="portletInstance" />
</liferay-portlet:actionURL>

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<%
	String tabs2Names = "display-settings";

	if (PortalUtil.isRSSFeedsEnabled()) {
		tabs2Names += ",rss";
	}
	%>

	<liferay-ui:tabs
		names="<%= tabs2Names %>"
		refresh="<%= false %>"
	>

		<liferay-ui:section>
			<%@ include file="/html/portlet/blogs/display_settings.jspf" %>
		</liferay-ui:section>

		<c:if test="<%= PortalUtil.isRSSFeedsEnabled() %>">
			<liferay-ui:section>
				<liferay-ui:rss-settings
					delta="<%= blogsPortletInstanceSettings.getRssDelta() %>"
					displayStyle="<%= blogsPortletInstanceSettings.getRssDisplayStyle() %>"
					enabled="<%= blogsPortletInstanceSettings.isEnableRSS() %>"
					feedType="<%= blogsPortletInstanceSettings.getRssFeedType() %>"
				/>
			</liferay-ui:section>
		</c:if>
	</liferay-ui:tabs>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>