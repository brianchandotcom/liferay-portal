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

<%@ include file="/html/portlet/breadcrumb/init.jsp" %>

<%
TemplateHandler templateHandler = TemplateHandlerRegistryUtil.getTemplateHandler(Layout.class.getName());
%>

<aui:row>
	<aui:col width="<%= 50 %>">
		<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

		<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

		<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
			<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

			<aui:fieldset>
				<div class="display-template">
					<liferay-ui:ddm-template-selector
						classNameId="<%= PortalUtil.getClassNameId(templateHandler.getClassName()) %>"
						displayStyle="<%= displayStyle %>"
						displayStyleGroupId="<%= displayStyleGroupId %>"
						displayStyles="<%= Arrays.asList(PropsValues.BREADCRUMB_DISPLAY_STYLE_OPTIONS) %>"
						refreshURL="<%= configurationRenderURL %>"
						showEmptyOption="<%= true %>"
					/>
				</div>
			</aui:fieldset>

			<aui:fieldset id="checkBoxes">
				<aui:col width="<%= 50 %>">
					<aui:input data-key='<%= "_" + HtmlUtil.escapeJS(portletResource) + "_showCurrentGroup" %>' label="show-current-site" name="preferences--showCurrentGroup--" type="checkbox" value="<%= showCurrentGroup %>" />
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
		List<BreadcrumbEntry> breadcrumbEntries = BreadcrumbUtil.getPortletBreadcrumbEntries(request);

		breadcrumbEntries.clear();
		%>

		<liferay-portlet:preview
			portletName="<%= portletResource %>"
			queryString="struts_action=/breadcrumb/view"
			showBorders="<%= true %>"
		/>
	</aui:col>
</aui:row>

<aui:script sandbox="<%= true %>">
	var data = {
		'_<%= HtmlUtil.escapeJS(portletResource) %>_displayStyle': '<%= displayStyle %>',
		'_<%= HtmlUtil.escapeJS(portletResource) %>_showCurrentGroup': <%= showCurrentGroup %>,
		'_<%= HtmlUtil.escapeJS(portletResource) %>_showGuestGroup': <%= showGuestGroup %>,
		'_<%= HtmlUtil.escapeJS(portletResource) %>_showLayout': <%= showLayout %>,
		'_<%= HtmlUtil.escapeJS(portletResource) %>_showParentGroups': <%= showParentGroups %>,
		'_<%= HtmlUtil.escapeJS(portletResource) %>_showPortletBreadcrumb': <%= showPortletBreadcrumb %>
	}

	var selectDisplayStyle = $('#<portlet:namespace />displayStyle');

	selectDisplayStyle.on(
		'change',
		function(event) {
			if (selectDisplayStyle.prop('selectedIndex') > -1) {
				data['_<%= HtmlUtil.escapeJS(portletResource) %>_displayStyle'] = selectDisplayStyle.val();

				Liferay.Portlet.refresh('#p_p_id_<%= HtmlUtil.escapeJS(portletResource) %>_', data);
			}
		}
	);

	$('#<portlet:namespace />checkBoxes').on(
		'change',
		'input[type="checkbox"]',
		function(event) {
			var currentTarget = $(event.currentTarget);

			data[currentTarget.data('key')] = currentTarget.prop('checked');

			Liferay.Portlet.refresh('#p_p_id_<%= HtmlUtil.escapeJS(portletResource) %>_', data);
		}
	);
</aui:script>