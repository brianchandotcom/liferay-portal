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
	var selectDisplayStyle = A.one('#<portlet:namespace />displayStyle');

	var curPortletBoundaryId = '#p_p_id_<%= portletResource %>_';

	var toggleCustomFields = function() {
		var data = {};

		data['_<%= portletResource %>_displayStyle'] = selectDisplayStyle.val();

		Liferay.Portlet.refresh(curPortletBoundaryId, data);
	}

	if (selectDisplayStyle) {
		selectDisplayStyle.on('change', toggleCustomFields);

		toggleCustomFields();
	}
</aui:script>