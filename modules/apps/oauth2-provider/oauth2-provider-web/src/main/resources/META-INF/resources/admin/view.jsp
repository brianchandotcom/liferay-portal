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

<%@ include file="/admin/init.jsp" %>

<%
int oAuth2ApplicationsCount = OAuth2ApplicationServiceUtil.getOAuth2ApplicationsCount(themeDisplay.getCompanyId());

OAuth2ApplicationsManagementToolbarDisplayContext oAuth2ApplicationsManagementToolbarDisplayContext = new OAuth2ApplicationsManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, currentURLObj);

String displayStyle = oAuth2ApplicationsManagementToolbarDisplayContext.getDisplayStyle();
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= oAuth2ApplicationsManagementToolbarDisplayContext.getNavigationItems() %>"
/>

<clay:management-toolbar
	actionDropdownItems="<%= oAuth2ApplicationsManagementToolbarDisplayContext.getActionDropdownItems() %>"
	creationMenu="<%= oAuth2ApplicationsManagementToolbarDisplayContext.getCreationMenu() %>"
	disabled="<%= oAuth2ApplicationsCount == 0 %>"
	filterDropdownItems="<%= oAuth2ApplicationsManagementToolbarDisplayContext.getFilterDropdownItems() %>"
	itemsTotal="<%= oAuth2ApplicationsCount %>"
	namespace="<%= renderResponse.getNamespace() %>"
	searchContainerId="oAuth2ApplicationsSearchContainer"
	selectable="<%= true %>"
	showCreationMenu="<%= oAuth2AdminPortletDisplayContext.hasAddApplicationPermission() %>"
	showSearch="<%= false %>"
	sortingOrder="<%= oAuth2ApplicationsManagementToolbarDisplayContext.getOrderByType() %>"
	sortingURL="<%= String.valueOf(oAuth2ApplicationsManagementToolbarDisplayContext.getSortingURL()) %>"
	viewTypeItems="<%= oAuth2ApplicationsManagementToolbarDisplayContext.getViewTypes() %>"
/>

<div class="closed container-fluid-1280">
	<aui:form action="<%= currentURLObj %>" method="get" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="oAuth2ApplicationIds" type="hidden" />

		<liferay-ui:search-container
			emptyResultsMessage="no-applications-were-found"
			id="oAuth2ApplicationsSearchContainer"
			iteratorURL="<%= currentURLObj %>"
			rowChecker="<%= new EmptyOnClickRowChecker(renderResponse) %>"
			total="<%= oAuth2ApplicationsCount %>"
		>
			<liferay-ui:search-container-results
				results="<%= OAuth2ApplicationServiceUtil.getOAuth2Applications(themeDisplay.getCompanyId(), searchContainer.getStart(), searchContainer.getEnd(), oAuth2ApplicationsManagementToolbarDisplayContext.getOrderByComparator()) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.oauth2.provider.model.OAuth2Application"
				escapedModel="<%= true %>"
				keyProperty="OAuth2ApplicationId"
				modelVar="oAuth2Application"
			>
				<portlet:renderURL var="editURL">
					<portlet:param name="mvcRenderCommandName" value="/admin/update_oauth2_application" />
					<portlet:param name="oAuth2ApplicationId" value="<%= String.valueOf(oAuth2Application.getOAuth2ApplicationId()) %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
				</portlet:renderURL>

				<c:choose>
					<c:when test='<%= displayStyle.equals("descriptive") %>'>

						<%
						row.setCssClass("autofit-row-center");
						%>

						<liferay-ui:search-container-column-image
							src="<%= oAuth2AdminPortletDisplayContext.getThumbnailURL(oAuth2Application) %>"
							toggleRowChecker="<%= true %>"
						/>

						<c:choose>
							<c:when test="<%= oAuth2AdminPortletDisplayContext.hasUpdatePermission(oAuth2Application) %>">
								<liferay-ui:search-container-column-text
									colspan="<%= 2 %>"
									href="<%= editURL %>"
									property="name"
								/>
							</c:when>
							<c:otherwise>
								<liferay-ui:search-container-column-text
									colspan="<%= 2 %>"
									property="name"
								/>
							</c:otherwise>
						</c:choose>

						<liferay-ui:search-container-column-jsp
							align="right"
							path="/admin/application_actions.jsp"
						/>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="<%= oAuth2AdminPortletDisplayContext.hasUpdatePermission(oAuth2Application) %>">
								<liferay-ui:search-container-column-text
									href="<%= editURL %>"
									property="name"
								/>
							</c:when>
							<c:otherwise>
								<liferay-ui:search-container-column-text
									property="name"
								/>
							</c:otherwise>
						</c:choose>

						<liferay-ui:search-container-column-text
							property="description"
						/>

						<liferay-ui:search-container-column-text
							name="client-id"
							property="clientId"
						/>

						<liferay-ui:search-container-column-text
							name="authorizations"
							value="<%= String.valueOf(oAuth2AdminPortletDisplayContext.getOAuth2AuthorizationsCount(oAuth2Application)) %>"
						/>

						<liferay-ui:search-container-column-jsp
							align="right"
							path="/admin/application_actions.jsp"
						/>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				displayStyle="<%= displayStyle %>"
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>

<script>
	function <portlet:namespace />deleteOAuth2Applications() {
		if (
			confirm(
				'<%= HtmlUtil.escapeJS(LanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-entries-this-action-revokes-all-authorizations-and-associated-tokens")) %>'
			)
		) {
			var form = document.<portlet:namespace />fm;

			Liferay.Util.postForm(form, {
				data: {
					oAuth2ApplicationIds: Liferay.Util.listCheckedExcept(
						form,
						'<portlet:namespace />allRowIds'
					)
				},
				url:
					'<portlet:actionURL name="/admin/delete_oauth2_applications" />'
			});
		}
	}
</script>