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
ViewDisplayContext viewDisplayContext = (ViewDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

ViewManagementToolbarDisplayContext managementToolbarDisplayContext = new ViewManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, viewDisplayContext);
%>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= managementToolbarDisplayContext %>"
/>

<clay:container-fluid
	cssClass="container-view"
>
	<clay:dropdown-menu
		displayType="secondary"
		dropdownItems="<%= managementToolbarDisplayContext.getDropdownItems() %>"
		icon="<%= StringUtil.toLowerCase(TextFormatter.format(viewDisplayContext.getSelectedLanguageId(), TextFormatter.O)) %>"
		label="<%= TextFormatter.format(viewDisplayContext.getSelectedLanguageId(), TextFormatter.O) %>"
		small="<%= true %>"
	/>

	<liferay-ui:search-container
		orderByCol="key"
		searchContainer="<%= viewDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.language.override.web.internal.dto.PLOItemDTO"
			keyProperty="key"
			modelVar="ploItemDTO"
		>
			<portlet:renderURL var="editURL">
				<portlet:param name="mvcPath" value="/edit.jsp" />
				<portlet:param name="backURL" value="<%= currentURL %>" />
				<portlet:param name="key" value="<%= ploItemDTO.getKey() %>" />
				<portlet:param name="selectedLanguageId" value="<%= viewDisplayContext.getSelectedLanguageId() %>" />
			</portlet:renderURL>

			<c:choose>
				<c:when test='<%= Objects.equals("descriptive", viewDisplayContext.getDisplayStyle()) %>'>
					<liferay-ui:search-container-column-text
						colspan="<%= 3 %>"
						href="<%= editURL %>"
					>
						<h5>
							<strong><%= ploItemDTO.getKey() %></strong>
						</h5>

						<h6 class="text-default">
							<%= HtmlUtil.escape(ploItemDTO.getValue()) %>
						</h6>

						<c:if test="<%= ploItemDTO.isOverride() %>">
							<h6>
								<clay:label
									displayType="info"
									label="overridden"
								/>
							</h6>
						</c:if>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-jsp
						path="/actions.jsp"
					/>
				</c:when>
				<c:when test='<%= Objects.equals("list", viewDisplayContext.getDisplayStyle()) %>'>
					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand-small"
						href="<%= editURL %>"
						name="key"
						value="<%= ploItemDTO.getKey() %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand-small"
						href="<%= editURL %>"
						name="current-value"
						value="<%= HtmlUtil.escape(ploItemDTO.getValue()) %>"
					/>

					<liferay-ui:search-container-column-text
						href="<%= editURL %>"
						name="languages-with-override"
						value="<%= StringUtil.merge(ploItemDTO.getOverrideLanguages(), StringPool.COMMA_AND_SPACE) %>"
					/>

					<%
					request.setAttribute("view.jsp-editURL", editURL);
					%>

					<liferay-ui:search-container-column-jsp
						path="/actions.jsp"
					/>
				</c:when>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= viewDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>