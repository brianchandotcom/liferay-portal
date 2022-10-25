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
RedirectEntriesDisplayContext redirectEntriesDisplayContext = (RedirectEntriesDisplayContext)request.getAttribute(RedirectEntriesDisplayContext.class.getName());

SearchContainer<RedirectEntry> redirectSearchContainer = redirectEntriesDisplayContext.searchContainer();

RedirectEntriesManagementToolbarDisplayContext redirectEntriesManagementToolbarDisplayContext = redirectEntriesDisplayContext.getRedirectManagementToolbarDisplayContext();
%>

<c:if test="<%= !redirectEntriesDisplayContext.isStagingGroup() %>">
	<clay:management-toolbar
		managementToolbarDisplayContext="<%= redirectEntriesManagementToolbarDisplayContext %>"
		propsTransformer="js/RedirectManagementToolbarPropsTransformer"
	/>
</c:if>

<div class="closed redirect-entries sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/redirect/info_panel" var="sidebarPanelURL" />

	<liferay-frontend:sidebar-panel
		resourceURL="<%= sidebarPanelURL %>"
		searchContainerId="<%= redirectEntriesDisplayContext.getSearchContainerId() %>"
	>
		<liferay-util:include page="/info_panel.jsp" servletContext="<%= application %>" />
	</liferay-frontend:sidebar-panel>

	<clay:container-fluid
		cssClass="sidenav-content"
	>
		<c:if test="<%= redirectEntriesDisplayContext.isStagingGroup() %>">
			<div class="lfr-search-container">
				<clay:alert
					displayType="info"
					message="redirections-are-unavailable-in-staged-sites"
				/>
			</div>
		</c:if>

		<aui:form action="<%= redirectSearchContainer.getIteratorURL() %>" cssClass="container-fluid container-fluid-max-xl" name="fm">
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

			<liferay-ui:search-container
				id="<%= redirectEntriesDisplayContext.getSearchContainerId() %>"
				searchContainer="<%= redirectSearchContainer %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.redirect.model.RedirectEntry"
					keyProperty="redirectEntryId"
					modelVar="redirectEntry"
				>

					<%
					row.setData(
						HashMapBuilder.<String, Object>put(
							"actions", redirectEntriesManagementToolbarDisplayContext.getAvailableActions(redirectEntry)
						).build());
					%>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand table-cell-text-truncate-reverse"
						name="source-url"
					>

						<%
						String sourceURL = URLCodec.decodeURL(HtmlUtil.escape(RedirectUtil.getGroupBaseURL(themeDisplay) + StringPool.SLASH + redirectEntry.getSourceURL()));
						%>

						<bdi data-title="<%= HtmlUtil.escapeAttribute(sourceURL) %>">
							<%= sourceURL %>
						</bdi>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand table-cell-text-truncate-reverse"
						name="destination-url"
					>

						<%
						String destinationURL = HtmlUtil.escape(redirectEntry.getDestinationURL());
						%>

						<bdi data-title="<%= HtmlUtil.escapeAttribute(destinationURL) %>">
							<%= destinationURL %>
						</bdi>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand-smallest"
						name="type"
					>
						<liferay-ui:message key='<%= redirectEntry.isPermanent() ? "permanent" : "temporary" %>' />
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand-smallest"
						name="expiration"
					>
						<c:choose>
							<c:when test="<%= Validator.isNull(redirectEntry.getExpirationDate()) %>">
								<%= StringPool.DASH %>
							</c:when>
							<c:when test="<%= DateUtil.compareTo(redirectEntry.getExpirationDate(), DateUtil.newDate()) <= 0 %>">
								<strong><liferay-ui:message key="expired" /></strong>
							</c:when>
							<c:otherwise>
								<%= redirectEntriesDisplayContext.formatExpirationDate(redirectEntry.getExpirationDate()) %>
							</c:otherwise>
						</c:choose>
					</liferay-ui:search-container-column-text>

					<%
					List<DropdownItem> dropdownItems = redirectEntriesDisplayContext.getActionDropdownItems(redirectEntry);
					%>

					<liferay-ui:search-container-column-text>
						<c:if test="<%= ListUtil.isNotEmpty(dropdownItems) %>">
							<clay:dropdown-actions
								aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
								dropdownItems="<%= dropdownItems %>"
							/>
						</c:if>
					</liferay-ui:search-container-column-text>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					markupView="lexicon"
					searchContainer="<%= redirectSearchContainer %>"
				/>
			</liferay-ui:search-container>
		</aui:form>
	</clay:container-fluid>
</div>

<aui:script require="frontend-js-web/index as frontendJsWeb">
	var {delegate} = frontendJsWeb;

	delegate(
		document.querySelector('#<portlet:namespace />fm'),
		'click',
		'.icon-shortcut',
		(event) => {
			var delegateTarget = event.delegateTarget;

			var destinationURL = delegateTarget.dataset.href;

			if (destinationURL) {
				window.open(destinationURL, '_blank');
			}
		}
	);
</aui:script>