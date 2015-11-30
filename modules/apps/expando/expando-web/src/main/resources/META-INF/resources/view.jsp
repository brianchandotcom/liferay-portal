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
String displayStyle = ParamUtil.getString(request, "displayStyle", "list");

PortletURL portletURL = renderResponse.createRenderURL();

List<String> headerNames = new ArrayList<String>();

headerNames.add("resource");
headerNames.add("custom-fields");

List<CustomAttributesDisplay> customAttributesDisplays = PortletLocalServiceUtil.getCustomAttributesDisplays();

Collections.sort(customAttributesDisplays, new CustomAttributesDisplayComparator(locale));
%>

<aui:nav-bar markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item label="custom-fields" selected="<%= true %>" />
	</aui:nav>
</aui:nav-bar>

<liferay-frontend:management-bar>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= renderResponse.createRenderURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= renderResponse.createRenderURL() %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
	<liferay-ui:search-container
		emptyResultsMessage='<%= LanguageUtil.get(request, "custom-fields-are-not-enabled-for-any-resource") %>'
		iteratorURL="<%= portletURL %>"
	>
		<liferay-ui:search-container-results
			results="<%= customAttributesDisplays %>"
			total="<%= customAttributesDisplays.size() %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portlet.expando.model.CustomAttributesDisplay"
			modelVar="customAttributesDisplay"
			stringKey="<%= true %>"
		>
			<liferay-ui:search-container-row-parameter
				name="customAttributesDisplay"
				value="<%= customAttributesDisplay %>"
			/>

			<portlet:renderURL var="rowURL">
				<portlet:param name="mvcPath" value="/view_attributes.jsp" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="modelResource" value="<%= customAttributesDisplay.getClassName() %>" />
			</portlet:renderURL>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="resource"
			>
				<liferay-ui:icon
					iconCssClass="<%= customAttributesDisplay.getIconCssClass() %>"
					label="<%= true %>"
					message="<%= ResourceActionsUtil.getModelResource(locale, customAttributesDisplay.getClassName()) %>"
				/>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" paginate="<%= false %>" />
	</liferay-ui:search-container>
</div>