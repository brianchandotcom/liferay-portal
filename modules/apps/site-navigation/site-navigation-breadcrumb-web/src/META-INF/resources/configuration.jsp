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
						className="<%= BreadcrumbEntry.class.getName() %>"
						displayStyle="<%= breadcrumbDisplayContext.getDisplayStyle() %>"
						displayStyleGroupId="<%= breadcrumbDisplayContext.getDisplayStyleGroupId() %>"
						refreshURL="<%= configurationRenderURL %>"
					/>
				</div>
			</aui:fieldset>

			<aui:fieldset id="checkBoxes">
				<aui:col width="<%= 50 %>">
					<aui:input data-key='<%= "_" + HtmlUtil.escapeJS(breadcrumbDisplayContext.getPortletResource()) + "_showCurrentGroup" %>' label="show-current-site" name="preferences--showCurrentGroup--" type="checkbox" value="<%= breadcrumbDisplayContext.isShowCurrentGroup() %>" />
					<aui:input data-key='<%= "_" + HtmlUtil.escapeJS(breadcrumbDisplayContext.getPortletResource()) + "_showGuestGroup" %>' label="show-guest-site" name="preferences--showGuestGroup--" type="checkbox" value="<%= breadcrumbDisplayContext.isShowGuestGroup() %>" />
				</aui:col>

				<aui:col width="<%= 50 %>">
					<aui:input data-key='<%= "_" + HtmlUtil.escapeJS(breadcrumbDisplayContext.getPortletResource()) + "_showLayout" %>' label="show-page" name="preferences--showLayout--" type="checkbox" value="<%= breadcrumbDisplayContext.isShowLayout() %>" />
					<aui:input data-key='<%= "_" + HtmlUtil.escapeJS(breadcrumbDisplayContext.getPortletResource()) + "_showParentGroups" %>' label="show-parent-sites" name="preferences--showParentGroups--" type="checkbox" value="<%= breadcrumbDisplayContext.isShowParentGroups() %>" />
					<aui:input data-key='<%= "_" + HtmlUtil.escapeJS(breadcrumbDisplayContext.getPortletResource()) + "_showPortletBreadcrumb" %>' label="show-application-breadcrumb" name="preferences--showPortletBreadcrumb--" type="checkbox" value="<%= breadcrumbDisplayContext.isShowPortletBreadcrumb() %>" />
				</aui:col>
			</aui:fieldset>

			<aui:button-row>
				<aui:button type="submit" />
			</aui:button-row>
		</aui:form>
	</aui:col>
	<aui:col width="<%= 50 %>">
		<liferay-portlet:preview
			portletName="<%= breadcrumbDisplayContext.getPortletResource() %>"
			showBorders="<%= true %>"
		/>
	</aui:col>
</aui:row>

<aui:script sandbox="<%= true %>">
	var data = {
		'_<%= HtmlUtil.escapeJS(breadcrumbDisplayContext.getPortletResource()) %>_displayStyle': '<%= breadcrumbDisplayContext.getDisplayStyle() %>',
		'_<%= HtmlUtil.escapeJS(breadcrumbDisplayContext.getPortletResource()) %>_showCurrentGroup': <%= breadcrumbDisplayContext.isShowCurrentGroup() %>,
		'_<%= HtmlUtil.escapeJS(breadcrumbDisplayContext.getPortletResource()) %>_showGuestGroup': <%= breadcrumbDisplayContext.isShowGuestGroup() %>,
		'_<%= HtmlUtil.escapeJS(breadcrumbDisplayContext.getPortletResource()) %>_showLayout': <%= breadcrumbDisplayContext.isShowLayout() %>,
		'_<%= HtmlUtil.escapeJS(breadcrumbDisplayContext.getPortletResource()) %>_showParentGroups': <%= breadcrumbDisplayContext.isShowParentGroups() %>,
		'_<%= HtmlUtil.escapeJS(breadcrumbDisplayContext.getPortletResource()) %>_showPortletBreadcrumb': <%= breadcrumbDisplayContext.isShowPortletBreadcrumb() %>
	};

	var selectDisplayStyle = $('#<portlet:namespace />displayStyle');

	selectDisplayStyle.on(
		'change',
		function(event) {
			if (selectDisplayStyle.prop('selectedIndex') > -1) {
				data['_<%= HtmlUtil.escapeJS(breadcrumbDisplayContext.getPortletResource()) %>_displayStyle'] = selectDisplayStyle.val();

				Liferay.Portlet.refresh('#p_p_id_<%= HtmlUtil.escapeJS(breadcrumbDisplayContext.getPortletResource()) %>_', data);
			}
		}
	);

	$('#<portlet:namespace />checkBoxes').on(
		'change',
		'input[type="checkbox"]',
		function(event) {
			var currentTarget = $(event.currentTarget);

			data[currentTarget.data('key')] = currentTarget.prop('checked');

			Liferay.Portlet.refresh('#p_p_id_<%= HtmlUtil.escapeJS(breadcrumbDisplayContext.getPortletResource()) %>_', data);
		}
	);

	Liferay.Portlet.refresh('#p_p_id_<%= HtmlUtil.escapeJS(breadcrumbDisplayContext.getPortletResource()) %>_', data);
</aui:script>