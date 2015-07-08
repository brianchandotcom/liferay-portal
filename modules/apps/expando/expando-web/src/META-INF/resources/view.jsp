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

		<liferay-ui:search-container-column-text
			href="<%= rowURL %>"
			name="custom-fields"
		>

			<%
			ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(company.getCompanyId(), customAttributesDisplay.getClassName());

			List<String> attributeNames = Collections.list(expandoBridge.getAttributeNames());

			String[] localizedNames = new String[attributeNames.size()];

			int i = 0;

			for (String name : attributeNames) {
				String localizedName = LanguageUtil.get(request, name);

				if (name.equals(localizedName)) {
					localizedName = TextFormatter.format(name, TextFormatter.J);
				}

				localizedNames[i++] = HtmlUtil.escape(localizedName);
			}
			%>

			<%= StringUtil.merge(localizedNames, StringPool.COMMA_AND_SPACE) %>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-jsp
			align="right"
			cssClass="entry-action"
			path="/resource_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator paginate="<%= false %>" />
</liferay-ui:search-container>