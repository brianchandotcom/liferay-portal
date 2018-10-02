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
OrganizationScreenNavigationDisplayContext organizationScreenNavigationDisplayContext = (OrganizationScreenNavigationDisplayContext)request.getAttribute(UsersAdminWebKeys.ORGANIZATION_SCREEN_NAVIGATION_DISPLAY_CONTEXT);

long organizationId = organizationScreenNavigationDisplayContext.getOrganizationId();

List<Website> websites = WebsiteServiceUtil.getWebsites(Organization.class.getName(), organizationId);
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="websites"
/>

<liferay-ui:error key="<%= NoSuchListTypeException.class.getName() + Organization.class.getName() + ListTypeConstants.WEBSITE %>" message="please-select-a-type" />
<liferay-ui:error exception="<%= WebsiteURLException.class %>" message="please-enter-a-valid-url" />

<h3 class="sheet-subtitle">
	<span class="autofit-padded-no-gutters autofit-row">
		<span class="autofit-col autofit-col-expand">
			<span class="heading-text">
				<liferay-ui:message key="websites" />
			</span>
		</span>
		<span class="autofit-col">
			<liferay-ui:icon
				cssClass="modify-website-link"
				data="<%=
					new HashMap<String, Object>() {
						{
							put("title", LanguageUtil.get(request, "add-website"));
						}
					}
				%>"
				label="<%= true %>"
				linkCssClass="btn btn-secondary btn-sm"
				message="add"
				url="javascript:;"
			/>
		</span>
	</span>
</h3>

<liferay-ui:search-container
	compactEmptyResultsMessage="<%= true %>"
	cssClass="lfr-search-container-wrapper"
	curParam="websitesCur"
	deltaParam="websitesDelta"
	emptyResultsMessage="this-organization-does-not-have-any-websites"
	headerNames="website,type,"
	id="websitesSearchContainer"
	iteratorURL="<%= currentURLObj %>"
	total="<%= websites.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= websites.subList(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.model.Website"
		escapedModel="<%= true %>"
		keyProperty="websiteId"
		modelVar="website"
	>
		<liferay-ui:search-container-column-text
			cssClass="table-cell-content"
			name="website"
			property="url"
		/>

		<%
		ListType websiteListType = ListTypeServiceUtil.getListType(website.getTypeId());

		String websiteTypeKey = websiteListType.getName();
		%>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-content"
			name="type"
			value="<%= LanguageUtil.get(request, websiteTypeKey) %>"
		/>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-content"
		>
			<c:if test="<%= website.isPrimary() %>">
				<span class="label label-primary">
					<span class="label-item label-item-expand"><%= StringUtil.toUpperCase(LanguageUtil.get(request, "primary"), locale) %></span>
				</span>
			</c:if>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-jsp
			cssClass="entry-action-column"
			path="/organization/website_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
	/>
</liferay-ui:search-container>

<portlet:renderURL var="editWebsiteRenderURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcPath" value="/organization/edit_website.jsp" />
</portlet:renderURL>

<aui:script require="users-admin-web/js/contact-information.es as ContactInformation">
	ContactInformation.registerContactInformationListener(
		'.modify-website-link a',
		'<%= editWebsiteRenderURL.toString() %>',
		390
	);
</aui:script>