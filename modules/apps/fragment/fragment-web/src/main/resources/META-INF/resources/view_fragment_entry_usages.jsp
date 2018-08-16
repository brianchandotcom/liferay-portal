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
FragmentEntryLinkDisplayContext fragmentEntryLinkDisplayContext = new FragmentEntryLinkDisplayContext(renderRequest, renderResponse);

FragmentEntry fragmentEntry = fragmentEntryLinkDisplayContext.getFragmentEntry();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(fragmentEntryLinkDisplayContext.getRedirect());

renderResponse.setTitle(LanguageUtil.format(request, "usages-and-propagation-x", fragmentEntry.getName()));
%>

<div class="container-fluid container-fluid-max-xl container-form-lg">
	<div class="row">
		<div class="col-lg-3">
			<nav class="menubar menubar-transparent menubar-vertical-expand-lg">
				<ul class="nav nav-nested">
					<li class="nav-item">
						<strong class="text-uppercase">
							<liferay-ui:message key="usages" />
						</strong>

						<ul class="nav nav-stacked">
							<li class="nav-item">

								<%
								PortletURL allNavigationURL = fragmentEntryLinkDisplayContext.getPortletURL();

								allNavigationURL.setParameter("navigation", "all");
								%>

								<a class="nav-link <%= Objects.equals(fragmentEntryLinkDisplayContext.getNavigation(), "all") ? "active" : StringPool.BLANK %>" href="<%= allNavigationURL.toString() %>">
									<liferay-ui:message arguments="<%= fragmentEntryLinkDisplayContext.getAllUsageCount() %>" key="all-x" />
								</a>
							</li>
							<li class="nav-item">

								<%
								PortletURL pagesNavigationURL = fragmentEntryLinkDisplayContext.getPortletURL();

								pagesNavigationURL.setParameter("navigation", "pages");
								%>

								<a class="nav-link <%= Objects.equals(fragmentEntryLinkDisplayContext.getNavigation(), "pages") ? "active" : StringPool.BLANK %>" href="<%= pagesNavigationURL.toString() %>">
									<liferay-ui:message arguments="<%= fragmentEntryLinkDisplayContext.getPagesUsageCount() %>" key="pages-x" />
								</a>
							</li>
							<li class="nav-item">

								<%
								PortletURL pageTemplatesNavigationURL = fragmentEntryLinkDisplayContext.getPortletURL();

								pageTemplatesNavigationURL.setParameter("navigation", "page-templates");
								%>

								<a class="nav-link <%= Objects.equals(fragmentEntryLinkDisplayContext.getNavigation(), "page-templates") ? "active" : StringPool.BLANK %>" href="<%= pageTemplatesNavigationURL.toString() %>">
									<liferay-ui:message arguments="<%= fragmentEntryLinkDisplayContext.getPageTemplatesUsageCount() %>" key="page-templates-x" />
								</a>
							</li>
							<li class="nav-item">

								<%
								PortletURL displayPagesNavigationURL = fragmentEntryLinkDisplayContext.getPortletURL();

								displayPagesNavigationURL.setParameter("navigation", "display-pages");
								%>

								<a class="nav-link <%= Objects.equals(fragmentEntryLinkDisplayContext.getNavigation(), "display-pages") ? "active" : StringPool.BLANK %>" href="<%= displayPagesNavigationURL.toString() %>">
									<liferay-ui:message arguments="<%= fragmentEntryLinkDisplayContext.getDisplayPagesUsageCount() %>" key="display-pages-x" />
								</a>
							</li>
						</ul>
					</li>
				</ul>
			</nav>
		</div>

		<div class="col-lg-9">
			<div class="sheet">
				<h3 class="sheet-title">
					<c:choose>
						<c:when test='<%= Objects.equals(fragmentEntryLinkDisplayContext.getNavigation(), "pages") %>'>
							<liferay-ui:message arguments="<%= fragmentEntryLinkDisplayContext.getPagesUsageCount() %>" key="pages-x" />
						</c:when>
						<c:when test='<%= Objects.equals(fragmentEntryLinkDisplayContext.getNavigation(), "page-templates") %>'>
							<liferay-ui:message arguments="<%= fragmentEntryLinkDisplayContext.getPageTemplatesUsageCount() %>" key="page-templates-x" />
						</c:when>
						<c:when test='<%= Objects.equals(fragmentEntryLinkDisplayContext.getNavigation(), "display-pages") %>'>
							<liferay-ui:message arguments="<%= fragmentEntryLinkDisplayContext.getDisplayPagesUsageCount() %>" key="display-pages-x" />
						</c:when>
						<c:otherwise>
							<liferay-ui:message arguments="<%= fragmentEntryLinkDisplayContext.getAllUsageCount() %>" key="all-x" />
						</c:otherwise>
					</c:choose>
				</h3>

				<clay:management-toolbar
					actionDropdownItems="<%= FragmentPermission.contains(permissionChecker, scopeGroupId, FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES) ? fragmentEntryLinkDisplayContext.getActionItemsDropdownItemList() : null %>"
					componentId="fragmentEntryLinksManagementToolbar"
					disabled="<%= fragmentEntry.getUsageCount() <= 0 %>"
					filterDropdownItems="<%= fragmentEntryLinkDisplayContext.getFilterItemsDropdownItems() %>"
					itemsTotal="<%= fragmentEntry.getUsageCount() %>"
					searchContainerId="fragmentEntryLinks"
					selectable="<%= FragmentPermission.contains(permissionChecker, scopeGroupId, FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES) %>"
					showSearch="<%= false %>"
					sortingOrder="<%= fragmentEntryLinkDisplayContext.getOrderByType() %>"
					sortingURL="<%= fragmentEntryLinkDisplayContext.getSortingURL() %>"
				/>

				<portlet:actionURL name="/fragment/propagate_fragment_entry_changes" var="propagateFragmentEntryChangesURL">
					<portlet:param name="redirect" value="<%= currentURL %>" />
				</portlet:actionURL>

				<aui:form action="<%= propagateFragmentEntryChangesURL %>" name="fm">
					<liferay-ui:search-container
						id="fragmentEntryLinks"
						searchContainer="<%= fragmentEntryLinkDisplayContext.getSearchContainer() %>"
					>
						<liferay-ui:search-container-row
							className="com.liferay.fragment.model.FragmentEntryLink"
							keyProperty="fragmentEntryLinkId"
							modelVar="fragmentEntryLink"
						>
							<liferay-ui:search-container-column-text
								name="name"
								value="<%= fragmentEntryLinkDisplayContext.getFragmentEntryLinkName(fragmentEntryLink) %>"
							/>

							<liferay-ui:search-container-column-text
								name="type"
								translate="<%= true %>"
								value="<%= fragmentEntryLinkDisplayContext.getFragmentEntryLinkTypeLabel(fragmentEntryLink) %>"
							/>

							<liferay-ui:search-container-column-text
								name="using"
							>
								<span class="label <%= fragmentEntryLink.isLatestVersion() ? "label-success" : "label-info" %>">
									<liferay-ui:message key='<%= fragmentEntryLink.isLatestVersion() ? "latest-version" : "a-previous-version" %>' />
								</span>
							</liferay-ui:search-container-column-text>

							<liferay-ui:search-container-column-date
								name="last-propagation"
								value="<%= fragmentEntryLink.getModifiedDate() %>"
							/>
						</liferay-ui:search-container-row>

						<liferay-ui:search-iterator
							displayStyle="list"
							markupView="lexicon"
							searchResultCssClass="show-quick-actions-on-hover table table-autofit"
						/>
					</liferay-ui:search-container>
				</aui:form>
			</div>
		</div>
	</div>
</div>

<aui:script>
	var propagate = function() {
		submitForm(document.querySelector('#<portlet:namespace />fm'));
	};

	var ACTIONS = {
		'propagate': propagate
	};

	Liferay.componentReady('fragmentEntryLinksManagementToolbar').then(
		function(managementToolbar) {
			managementToolbar.on(
				'actionItemClicked',
				function(event) {
					var itemData = event.data.item.data;

					if (itemData && itemData.action && ACTIONS[itemData.action]) {
						ACTIONS[itemData.action]();
					}
				}
			);
		}
	);
</aui:script>