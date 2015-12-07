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
long groupId = layoutsAdminDisplayContext.getGroupId();
long liveGroupId = layoutsAdminDisplayContext.getLiveGroupId();
boolean privateLayout = layoutsAdminDisplayContext.isPrivateLayout();
LayoutSet layoutSet = layoutsAdminDisplayContext.getSelLayoutSet();

Theme selTheme = layoutSet.getTheme();
ColorScheme selColorScheme = layoutSet.getColorScheme();

Theme selWapTheme = layoutSet.getWapTheme();
ColorScheme selWapColorScheme = layoutSet.getWapColorScheme();
%>

<liferay-ui:error-marker key="errorSection" value="look-and-feel" />

<aui:model-context bean="<%= layoutSet %>" model="<%= Layout.class %>" />

<aui:input name="devices" type="hidden" value='<%= PropsValues.MOBILE_DEVICE_STYLING_WAP_ENABLED? "regular,wap" : "regular" %>' />

<c:choose>
	<c:when test="<%= PropsValues.MOBILE_DEVICE_STYLING_WAP_ENABLED %>">

	<liferay-ui:tabs
		names="regular-browsers,mobile-devices"
		refresh="<%= false %>"
	>
		<liferay-ui:section>
			<%@ include file="/layout_set/look_and_feel_regular_browser.jspf" %>
		</liferay-ui:section>

		<liferay-ui:section>
			<%@ include file="/layout_set/look_and_feel_wap_browser.jspf" %>
		</liferay-ui:section>
	</liferay-ui:tabs>
	</c:when>
	<c:otherwise>
		<%@ include file="/layout_set/look_and_feel_regular_browser.jspf" %>
	</c:otherwise>
</c:choose>