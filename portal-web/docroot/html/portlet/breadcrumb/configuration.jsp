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

<aui:row>
	<aui:col width="<%= 50 %>">
		<liferay-portlet:actionURL portletConfiguration="true" var="configurationActionURL" />

		<liferay-portlet:renderURL portletConfiguration="true" var="configurationRenderURL" />

		<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
			<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

			<aui:fieldset>
				<aui:select name="preferences--displayStyle--">

					<%
					for (String displayStyleOption : PropsValues.BREADCRUMB_DISPLAY_STYLE_OPTIONS) {
					%>

						<aui:option label="<%= displayStyleOption %>" selected="<%= displayStyle.equals(displayStyleOption) %>" />

					<%
					}
					%>

				</aui:select>
			</aui:fieldset>

			<aui:fieldset cssClass="checkBoxes">
				<aui:col width="<%= 50 %>">
					<aui:input data-key='<%= "_" + HtmlUtil.escapeJS(portletResource) + "_showCurrentGroup" %>' label="show-current-site" name="preferences--showCurrentGroup--" type="checkbox" value="<%= showCurrentGroup %>" />
					<aui:input data-key='<%= "_" + HtmlUtil.escapeJS(portletResource) + "_showCurrentPortlet" %>' label="show-current-application" name="preferences--showCurrentPortlet--" type="checkbox" value="<%= showCurrentPortlet %>" />
					<aui:input data-key='<%= "_" + HtmlUtil.escapeJS(portletResource) + "_showGuestGroup" %>' label="show-guest-site" name="preferences--showGuestGroup--" type="checkbox" value="<%= showGuestGroup %>" />
				</aui:col>

				<aui:col width="<%= 50 %>">
					<aui:input data-key='<%= "_" + HtmlUtil.escapeJS(portletResource) + "_showLayout" %>' label="show-page" name="preferences--showLayout--" type="checkbox" value="<%= showLayout %>" />
					<aui:input data-key='<%= "_" + HtmlUtil.escapeJS(portletResource) + "_showParentGroups" %>' label="show-parent-sites" name="preferences--showParentGroups--" type="checkbox" value="<%= showParentGroups %>" />
					<aui:input data-key='<%= "_" + HtmlUtil.escapeJS(portletResource) + "_showPortletBreadcrumb" %>' label="show-application-breadcrumb" name="preferences--showPortletBreadcrumb--" type="checkbox" value="<%= showPortletBreadcrumb %>" />
				</aui:col>
			</aui:fieldset>

			<aui:button-row>
				<aui:button type="submit" />
			</aui:button-row>
		</aui:form>
	</aui:col>
	<aui:col width="<%= 50 %>">

		<%
		List<BreadcrumbEntry> breadcrumbEntries = PortalUtil.getPortletBreadcrumbs(request);

		breadcrumbEntries.clear();
		%>

		<liferay-portlet:preview
			portletName="<%= portletResource %>"
			queryString="struts_action=/breadcrumb/view"
			showBorders="<%= true %>"
		/>
	</aui:col>
</aui:row>

<aui:script use="aui-base">
	var formNode = A.one('#<portlet:namespace />fm');

	var data = {
		'_<%= HtmlUtil.escapeJS(portletResource) %>_showCurrentGroup': <%= showCurrentGroup %>,
		'_<%= HtmlUtil.escapeJS(portletResource) %>_showCurrentPortlet': <%= showCurrentPortlet %>,
		'_<%= HtmlUtil.escapeJS(portletResource) %>_showGuestGroup': <%= showGuestGroup %>,
		'_<%= HtmlUtil.escapeJS(portletResource) %>_showLayout': <%= showLayout %>,
		'_<%= HtmlUtil.escapeJS(portletResource) %>_showParentGroups': <%= showParentGroups %>,
		'_<%= HtmlUtil.escapeJS(portletResource) %>_showPortletBreadcrumb': <%= showPortletBreadcrumb %>
	}

	var toggleCustomFields = function(event) {
		var target = event.currentTarget;

		data[target.attr('data-key')] = target.get('checked');

		Liferay.Portlet.refresh('#p_p_id_<%= HtmlUtil.escapeJS(portletResource) %>_', data);
	};

	A.one('.checkBoxes').delegate('change', toggleCustomFields, 'input[type="checkbox"]');
</aui:script>