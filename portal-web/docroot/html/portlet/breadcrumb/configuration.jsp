<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<aui:layout>
	<aui:column columnWidth="50">
		<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

		<aui:form action="<%= configurationURL %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
			<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

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
				<aui:column columnWidth="50">
					<aui:input name="preferences--showCurrentGroup--" type="checkbox" value="<%= showCurrentGroup %>" />
					<aui:input name="preferences--showCurrentPortlet--" type="checkbox" value="<%= showCurrentPortlet %>" />
					<aui:input name="preferences--showGuestGroup--" type="checkbox" value="<%= showGuestGroup %>" />
				</aui:column>

				<aui:column columnWidth="50">
					<aui:input name="preferences--showLayout--" type="checkbox" value="<%= showLayout %>" />
					<aui:input name="preferences--showParentGroups--" type="checkbox" value="<%= showParentGroups %>" />
					<aui:input name="preferences--showPortletBreadcrumb--" type="checkbox" value="<%= showPortletBreadcrumb %>" />
				</aui:column>
			</aui:fieldset>

			<aui:button-row>
				<aui:button type="submit" />
			</aui:button-row>
		</aui:form>
	</aui:column>
	<aui:column columnWidth="50">
		<liferay-portlet:preview
			portletName="<%= portletResource %>"
			queryString="struts_action=/breadcrumb/view"
			showBorders="<%= true %>"
		/>
	</aui:column>
</aui:layout>

<aui:script use="aui-base">
	var formNode = A.one('#<portlet:namespace />fm');

	var selectDisplayStyle = formNode.one('#<portlet:namespace />displayStyle');

	var toggleCustomFields = function() {
		var data = {
			'_<%= portletResource %>_displayStyle': selectDisplayStyle.val(),
			'_<%= portletResource %>_showCurrentGroup': formNode.one('#<portlet:namespace />showCurrentGroup').val(),
			'_<%= portletResource %>_showCurrentPortlet': formNode.one('#<portlet:namespace />showCurrentPortlet').val(),
			'_<%= portletResource %>_showGuestGroup': formNode.one('#<portlet:namespace />showGuestGroup').val(),
			'_<%= portletResource %>_showLayout': formNode.one('#<portlet:namespace />showLayout').val(),
			'_<%= portletResource %>_showParentGroups': formNode.one('#<portlet:namespace />showParentGroups').val(),
			'_<%= portletResource %>_showPortletBreadcrumb': formNode.one('#<portlet:namespace />showPortletBreadcrumb').val()
		};

		Liferay.Portlet.refresh('#p_p_id_<%= portletResource %>_', data);
	};

	if (selectDisplayStyle) {
		selectDisplayStyle.on('change', toggleCustomFields);

		toggleCustomFields();
	}

	A.one('.checkBoxes').delegate('change', toggleCustomFields, '.aui-field-input-choice');
</aui:script>