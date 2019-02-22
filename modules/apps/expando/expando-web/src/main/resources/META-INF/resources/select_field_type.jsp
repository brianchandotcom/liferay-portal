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
String redirect = ParamUtil.getString(request, "redirect");

String modelResource = ParamUtil.getString(request, "modelResource");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);
%>

<liferay-frontend:edit-form>
	<liferay-frontend:edit-form-body>
		<h2 class="sheet-title">
			<liferay-ui:message key="new-custom-field" />
		</h2>

		<aui:row>

			<%
			for (int type : ExpandoColumnConstants.TYPES) {
				if ((type == ExpandoColumnConstants.BOOLEAN_ARRAY) || (type == ExpandoColumnConstants.DATE_ARRAY) || (type == ExpandoColumnConstants.STRING_ARRAY_LOCALIZED)) {
					continue;
				}
			%>

				<aui:col span="<%= 4 %>">
					<portlet:renderURL var="editExpandoURL">
						<portlet:param name="mvcPath" value="/edit_expando.jsp" />
						<portlet:param name="redirect" value="<%= redirect %>" />
						<portlet:param name="modelResource" value="<%= modelResource %>" />
						<portlet:param name="type" value="<%= String.valueOf(type) %>" />
					</portlet:renderURL>

					<liferay-frontend:horizontal-card
						text="<%= LanguageUtil.get(request, ExpandoColumnConstants.getTypeLabel(type)) %>"
						url="<%= editExpandoURL %>"
					/>
				</aui:col>

			<%
			}
			%>

		</aui:row>
	</liferay-frontend:edit-form-body>
</liferay-frontend:edit-form>