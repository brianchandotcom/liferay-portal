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
CommerceOrganizationSearchDisplayContext commerceOrganizationSearchDisplayContext = (CommerceOrganizationSearchDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<h6><%= LanguageUtil.get(resourceBundle, "select-an-account") %></h6>

<div class="organization-container" id="<portlet:namespace />entriesContainer">
	<aui:form action="<%= String.valueOf(commerceOrganizationSearchDisplayContext.getPortletURL()) %>" name="searchFm">
		<div class="input-group">
			<div class="input-group-item">
				<input class="form-control input-group-inset input-group-inset-after"
					id="<portlet:namespace />keywords"
					name="<portlet:namespace />keywords"
					placeholder="<%= LanguageUtil.get(resourceBundle, "search") %>"
					title="<%= LanguageUtil.get(resourceBundle, "search") %>"
					type="text"
					value="<%= HtmlUtil.escapeAttribute(commerceOrganizationSearchDisplayContext.getKeywords()) %>">

				<div class="input-group-inset-item input-group-inset-item-after">
					<button class="btn btn-unstyled d-md-inline-block d-none" type="submit">
						<svg aria-hidden="true" class="lexicon-icon lexicon-icon-search">
							<use xlink:href="<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg#search">
						</svg>
					</button>
				</div>
			</div>
		</div>
	</aui:form>

	<liferay-ui:search-container
		id="organizations"
		searchContainer="<%= commerceOrganizationSearchDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.Organization"
			escapedModel="<%= true %>"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				property="name"
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action-column"
				path="/organization-search/organization_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" />
	</liferay-ui:search-container>

	<aui:script>
		Liferay.Util.focusFormField('#<portlet:namespace />keywords');
	</aui:script>
</div>