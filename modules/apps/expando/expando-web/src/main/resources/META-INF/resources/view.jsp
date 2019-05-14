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
PortletURL portletURL = renderResponse.createRenderURL();

List<String> headerNames = new ArrayList<String>();

headerNames.add("resource");
headerNames.add("custom-fields");

List<CustomAttributesDisplay> customAttributesDisplays = PortletLocalServiceUtil.getCustomAttributesDisplays();

Collections.sort(customAttributesDisplays, new CustomAttributesDisplayComparator(locale));
%>

<div class="container-fluid container-fluid-max-xl container-view">
	<liferay-ui:search-container
		emptyResultsMessage='<%= LanguageUtil.get(request, "custom-fields-are-not-enabled-for-any-resource") %>'
		iteratorURL="<%= portletURL %>"
		total="<%= customAttributesDisplays.size() %>"
	>
		<liferay-ui:search-container-results
			results="<%= customAttributesDisplays %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.expando.kernel.model.CustomAttributesDisplay"
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
				cssClass="table-cell-expand table-cell-ws-nowrap table-title"
				href="<%= rowURL %>"
				name="resource"
			>
				<span class="text-truncate-inline">
					<span class="inline-item inline-item-before">
						<clay:icon
							symbol="<%= customAttributesDisplay.getIconCssClass() %>"
						/>
					</span>
					<span class="inline-item inline-item-after text-truncate">
						<liferay-ui:message key="<%= ResourceActionsUtil.getModelResource(locale, customAttributesDisplay.getClassName()) %>" />
					</span>
				</span>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
			paginate="<%= false %>"
		/>
	</liferay-ui:search-container>
</div>