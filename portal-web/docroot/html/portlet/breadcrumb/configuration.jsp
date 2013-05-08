<%--
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
--%>

<%@ include file="/html/portlet/breadcrumb/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
%>

<aui:row>
	<aui:col width="<%= 50 %>">
		<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

		<aui:form action="<%= configurationURL %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
			<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

			<aui:fieldset cssClass="checkBoxes">
				<aui:col width="<%= 50 %>">
					<aui:input label="show-current-site" name="preferences--showCurrentGroup--" type="checkbox" value="<%= showCurrentGroup %>" />
					<aui:input label="show-current-application" name="preferences--showCurrentPortlet--" type="checkbox" value="<%= showCurrentPortlet %>" />
					<aui:input label="show-guest-site" name="preferences--showGuestGroup--" type="checkbox" value="<%= showGuestGroup %>" />
				</aui:col>

				<aui:col width="<%= 50 %>">
					<aui:input label="show-page" name="preferences--showLayout--" type="checkbox" value="<%= showLayout %>" />
					<aui:input label="show-parent-sites" name="preferences--showParentGroups--" type="checkbox" value="<%= showParentGroups %>" />
					<aui:input label="show-application-breadcrumb" name="preferences--showPortletBreadcrumb--" type="checkbox" value="<%= showPortletBreadcrumb %>" />
				</aui:col>
			</aui:fieldset>

			<aui:button-row>
				<aui:button type="submit" />
			</aui:button-row>
		</aui:form>
	</aui:col>
	<aui:col width="<%= 50 %>">
		<liferay-portlet:preview
			portletName="<%= portletResource %>"
			queryString="struts_action=/breadcrumb/view"
			showBorders="<%= true %>"
		/>
	</aui:col>
</aui:row>

<aui:script use="aui-base">
	var formNode = A.one('#<portlet:namespace />fm');

	var toggleCustomFields = function() {
		var data = {
			'_<%= portletResource %>_showCurrentGroup': formNode.one('#<portlet:namespace />showCurrentGroup').val(),
			'_<%= portletResource %>_showCurrentPortlet': formNode.one('#<portlet:namespace />showCurrentPortlet').val(),
			'_<%= portletResource %>_showGuestGroup': formNode.one('#<portlet:namespace />showGuestGroup').val(),
			'_<%= portletResource %>_showLayout': formNode.one('#<portlet:namespace />showLayout').val(),
			'_<%= portletResource %>_showParentGroups': formNode.one('#<portlet:namespace />showParentGroups').val(),
			'_<%= portletResource %>_showPortletBreadcrumb': formNode.one('#<portlet:namespace />showPortletBreadcrumb').val()
		};

		Liferay.Portlet.refresh('#p_p_id_<%= portletResource %>_', data);
	};

	A.one('.checkBoxes').delegate('change', toggleCustomFields, 'input[type="checkbox"]');
</aui:script>