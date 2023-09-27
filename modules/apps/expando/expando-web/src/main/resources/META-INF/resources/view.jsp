<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

<clay:container-fluid
	cssClass="container-view"
>
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
</clay:container-fluid>