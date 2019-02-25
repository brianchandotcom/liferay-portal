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
			<aui:col span="<%= 4 %>">
				<portlet:renderURL var="createTextAreaURL">
					<portlet:param name="mvcPath" value="/edit/expando.jsp" />
					<portlet:param name="redirect" value="<%= redirect %>" />
					<portlet:param name="modelResource" value="<%= modelResource %>" />
					<portlet:param name="displayType" value="<%= ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_TEXT_BOX %>" />
					<portlet:param name="type" value="<%= String.valueOf(ExpandoColumnConstants.STRING) %>" />
				</portlet:renderURL>

				<liferay-frontend:horizontal-card
					text='<%= LanguageUtil.get(request, "text-area") %>'
					url="<%= createTextAreaURL %>"
				/>
			</aui:col>

			<aui:col span="<%= 4 %>">
				<portlet:renderURL var="createTextFieldURL">
					<portlet:param name="mvcPath" value="/edit/expando.jsp" />
					<portlet:param name="redirect" value="<%= redirect %>" />
					<portlet:param name="modelResource" value="<%= modelResource %>" />
					<portlet:param name="displayType" value="<%= ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_TEXT_FIELD %>" />
					<portlet:param name="type" value="<%= String.valueOf(ExpandoColumnConstants.STRING) %>" />
				</portlet:renderURL>

				<liferay-frontend:horizontal-card
					text='<%= LanguageUtil.get(request, "text-field") %>'
					url="<%= createTextFieldURL %>"
				/>
			</aui:col>

			<aui:col span="<%= 4 %>">
				<portlet:renderURL var="createNumberURL">
					<portlet:param name="mvcPath" value="/edit/expando.jsp" />
					<portlet:param name="redirect" value="<%= redirect %>" />
					<portlet:param name="modelResource" value="<%= modelResource %>" />
					<portlet:param name="displayType" value="<%= ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_NUMBER %>" />
					<portlet:param name="type" value="<%= String.valueOf(ExpandoColumnConstants.NUMBER) %>" />
				</portlet:renderURL>

				<liferay-frontend:horizontal-card
					text='<%= LanguageUtil.get(request, "number") %>'
					url="<%= createNumberURL %>"
				/>
			</aui:col>

			<aui:col span="<%= 4 %>">
				<portlet:renderURL var="createDropdownURL">
					<portlet:param name="mvcPath" value="/edit/expando.jsp" />
					<portlet:param name="redirect" value="<%= redirect %>" />
					<portlet:param name="modelResource" value="<%= modelResource %>" />
					<portlet:param name="displayType" value="<%= ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_SELECTION_LIST %>" />
					<portlet:param name="type" value="<%= String.valueOf(ExpandoColumnConstants.STRING_ARRAY) %>" />
				</portlet:renderURL>

				<liferay-frontend:horizontal-card
					text='<%= LanguageUtil.get(request, "dropdown") %>'
					url="<%= createDropdownURL %>"
				/>
			</aui:col>

			<aui:col span="<%= 4 %>">
				<portlet:renderURL var="createCheckboxURL">
					<portlet:param name="mvcPath" value="/edit/expando.jsp" />
					<portlet:param name="redirect" value="<%= redirect %>" />
					<portlet:param name="modelResource" value="<%= modelResource %>" />
					<portlet:param name="displayType" value="<%= ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_CHECKBOX %>" />
					<portlet:param name="type" value="<%= String.valueOf(ExpandoColumnConstants.STRING_ARRAY) %>" />
				</portlet:renderURL>

				<liferay-frontend:horizontal-card
					text='<%= LanguageUtil.get(request, "checkbox") %>'
					url="<%= createCheckboxURL %>"
				/>
			</aui:col>

			<aui:col span="<%= 4 %>">
				<portlet:renderURL var="createRadioURL">
					<portlet:param name="mvcPath" value="/edit/expando.jsp" />
					<portlet:param name="redirect" value="<%= redirect %>" />
					<portlet:param name="modelResource" value="<%= modelResource %>" />
					<portlet:param name="displayType" value="<%= ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_RADIO %>" />
					<portlet:param name="type" value="<%= String.valueOf(ExpandoColumnConstants.STRING_ARRAY) %>" />
				</portlet:renderURL>

				<liferay-frontend:horizontal-card
					text='<%= LanguageUtil.get(request, "radio") %>'
					url="<%= createRadioURL %>"
				/>
			</aui:col>

			<aui:col span="<%= 4 %>">
				<portlet:renderURL var="createGeolocationURL">
					<portlet:param name="mvcPath" value="/edit/expando.jsp" />
					<portlet:param name="redirect" value="<%= redirect %>" />
					<portlet:param name="modelResource" value="<%= modelResource %>" />
					<portlet:param name="displayType" value="<%= ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_GEOLOCATION %>" />
					<portlet:param name="type" value="<%= String.valueOf(ExpandoColumnConstants.GEOLOCATION) %>" />
				</portlet:renderURL>

				<liferay-frontend:horizontal-card
					text='<%= LanguageUtil.get(request, "geolocation") %>'
					url="<%= createGeolocationURL %>"
				/>
			</aui:col>

			<aui:col span="<%= 4 %>">
				<portlet:renderURL var="createDateURL">
					<portlet:param name="mvcPath" value="/edit/expando.jsp" />
					<portlet:param name="redirect" value="<%= redirect %>" />
					<portlet:param name="modelResource" value="<%= modelResource %>" />
					<portlet:param name="displayType" value="<%= ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_DATE %>" />
					<portlet:param name="type" value="<%= String.valueOf(ExpandoColumnConstants.DATE) %>" />
				</portlet:renderURL>

				<liferay-frontend:horizontal-card
					text='<%= LanguageUtil.get(request, "date") %>'
					url="<%= createDateURL %>"
				/>
			</aui:col>
		</aui:row>
	</liferay-frontend:edit-form-body>
</liferay-frontend:edit-form>