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
SitesItemSelectorViewDisplayContext siteItemSelectorViewDisplayContext = (SitesItemSelectorViewDisplayContext)request.getAttribute(SitesItemSelectorWebKeys.SITES_ITEM_SELECTOR_DISPLAY_CONTEXT);
GroupURLProvider groupURLProvider = (GroupURLProvider)request.getAttribute(SiteWebKeys.GROUP_URL_PROVIDER);

String displayStyle = siteItemSelectorViewDisplayContext.getDisplayStyle();
String target = ParamUtil.getString(request, "target");

GroupSearch groupSearch = siteItemSelectorViewDisplayContext.getGroupSearch();
%>

<liferay-frontend:management-bar>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-filters>
			<liferay-frontend:management-bar-navigation
				navigationKeys='<%= new String[] {"all"} %>'
				portletURL="<%= siteItemSelectorViewDisplayContext.getPortletURL() %>"
			/>

			<c:if test="<%= siteItemSelectorViewDisplayContext.isShowSortFilter() %>">
				<liferay-frontend:management-bar-sort
					orderByCol="<%= groupSearch.getOrderByCol() %>"
					orderByType="<%= groupSearch.getOrderByType() %>"
					orderColumns='<%= new String[] {"name", "type"} %>'
					portletURL="<%= siteItemSelectorViewDisplayContext.getPortletURL() %>"
				/>
			</c:if>
		</liferay-frontend:management-bar-filters>

		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list", "descriptive", "icon"} %>'
			portletURL="<%= siteItemSelectorViewDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<aui:form action="<%= siteItemSelectorViewDisplayContext.getPortletURL() %>" cssClass="container-fluid-1280" method="post" name="selectGroupFm">
	<c:if test="<%= siteItemSelectorViewDisplayContext.isShowChildSitesLink() %>">
		<div id="breadcrumb">
			<liferay-ui:breadcrumb showCurrentGroup="<%= false %>" showGuestGroup="<%= false %>" showLayout="<%= false %>" showPortletBreadcrumb="<%= true %>" />
		</div>
	</c:if>

	<liferay-ui:search-container
		searchContainer="<%= groupSearch %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.Group"
			escapedModel="<%= true %>"
			keyProperty="groupId"
			modelVar="group"
			rowIdProperty="friendlyURL"
			rowVar="row"
		>

			<%
			List<Group> childGroups = group.getChildren(true);

			Map<String, Object> data = new HashMap<String, Object>();

			data.put("groupdescriptivename", group.getDescriptiveName(locale));
			data.put("groupid", group.getGroupId());
			data.put("grouptarget", target);
			data.put("grouptype", LanguageUtil.get(resourceBundle, group.getTypeLabel()));
			data.put("url", groupURLProvider.getGroupURL(group, liferayPortletRequest));
			data.put("uuid", group.getUuid());

			String childGroupsHREF = null;

			if (!childGroups.isEmpty()) {
				PortletURL childGroupsURL = siteItemSelectorViewDisplayContext.getPortletURL();

				childGroupsURL.setParameter("groupId", String.valueOf(group.getGroupId()));

				childGroupsHREF = childGroupsURL.toString();
			}
			%>

			<c:choose>
				<c:when test='<%= displayStyle.equals("descriptive") %>'>
					<liferay-ui:search-container-column-icon
						icon="sites"
					/>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>
						<h5>
							<aui:a cssClass="selector-button" data="<%= data %>" href="javascript:;">
								<%= HtmlUtil.escape(siteItemSelectorViewDisplayContext.getGroupName(group)) %>
							</aui:a>

							<aui:a href="<%= groupURLProvider.getGroupURL(group, liferayPortletRequest) %>" target="_blank" />
						</h5>

						<h6 class="text-default">
							<span><%= LanguageUtil.get(request, group.getScopeLabel(themeDisplay)) %></span>
						</h6>

						<c:if test="<%= siteItemSelectorViewDisplayContext.isShowChildSitesLink() %>">
							<h6>
								<aui:a cssClass='<%= !childGroups.isEmpty() ? "text-default" : "disabled text-muted" %>' href="<%= childGroupsHREF %>">
									<liferay-ui:message arguments="<%= String.valueOf(childGroups.size()) %>" key="x-child-sites" />
								</aui:a>
							</h6>
						</c:if>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test='<%= displayStyle.equals("icon") %>'>

					<%
					row.setCssClass("entry-card lfr-asset-item " + row.getCssClass());

					Map<String, Object> linkData = new HashMap<String, Object>();

					linkData.put("prevent-selection", true);
					%>

					<liferay-ui:search-container-column-text>
						<div role="button">
							<c:choose>
								<c:when test="<%= Validator.isNotNull(group.getLogoURL(themeDisplay, false)) %>">
									<liferay-frontend:vertical-card
										imageUrl="<%= group.getLogoURL(themeDisplay, false) %>"
										resultRow="<%= row %>"
										rowChecker="<%= searchContainer.getRowChecker() %>"
										showCheckbox="<%= false %>"
									>
										<liferay-frontend:vertical-card-header>
											<h5>
												<aui:a cssClass="selector-button" data="<%= data %>" href="javascript:;">
													<%= HtmlUtil.escape(siteItemSelectorViewDisplayContext.getGroupName(group)) %>
												</aui:a>

												<aui:a href="<%= groupURLProvider.getGroupURL(group, liferayPortletRequest) %>" target="_blank" />
											</h5>
										</liferay-frontend:vertical-card-header>

										<c:if test="<%= siteItemSelectorViewDisplayContext.isShowChildSitesLink() %>">
											<liferay-frontend:vertical-card-footer>
												<aui:a cssClass='<%= !childGroups.isEmpty() ? "text-default" : "disabled text-muted" %>' data="<%= linkData %>" href="<%= childGroupsHREF %>">
													<liferay-ui:message arguments="<%= String.valueOf(childGroups.size()) %>" key="x-child-sites" />
												</aui:a>
											</liferay-frontend:vertical-card-footer>
										</c:if>
									</liferay-frontend:vertical-card>
								</c:when>
								<c:otherwise>
									<liferay-frontend:icon-vertical-card
										icon="sites"
										resultRow="<%= row %>"
										rowChecker="<%= searchContainer.getRowChecker() %>"
										showCheckbox="<%= false %>"
									>
										<liferay-frontend:vertical-card-header>
											<h5>
												<aui:a cssClass="selector-button" data="<%= data %>" href="javascript:;">
													<%= HtmlUtil.escape(siteItemSelectorViewDisplayContext.getGroupName(group)) %>
												</aui:a>

												<aui:a href="<%= groupURLProvider.getGroupURL(group, liferayPortletRequest) %>" target="_blank" />
											</h5>
										</liferay-frontend:vertical-card-header>

										<liferay-frontend:vertical-card-footer>
											<c:if test="<%= siteItemSelectorViewDisplayContext.isShowChildSitesLink() %>">
												<aui:a cssClass='<%= !childGroups.isEmpty() ? "text-default" : "disabled text-muted" %>' data="<%= linkData %>" href="<%= childGroupsHREF %>">
													<liferay-ui:message arguments="<%= String.valueOf(childGroups.size()) %>" key="x-child-sites" />
												</aui:a>
											</c:if>
										</liferay-frontend:vertical-card-footer>
									</liferay-frontend:icon-vertical-card>
								</c:otherwise>
							</c:choose>
						</div>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test='<%= displayStyle.equals("list") %>'>
					<liferay-ui:search-container-column-text
						name="name"
						truncate="<%= true %>"
					>
						<aui:a cssClass="selector-button" data="<%= data %>" href="javascript:;">
							<%= HtmlUtil.escape(siteItemSelectorViewDisplayContext.getGroupName(group)) %>
						</aui:a>

						<aui:a href="<%= groupURLProvider.getGroupURL(group, liferayPortletRequest) %>" target="_blank" />
					</liferay-ui:search-container-column-text>

					<c:if test="<%= siteItemSelectorViewDisplayContext.isShowChildSitesLink() %>">
						<liferay-ui:search-container-column-text
							name="child-sites"
							truncate="<%= true %>"
						>
							<aui:a cssClass='<%= !childGroups.isEmpty() ? "text-default" : "disabled text-muted" %>' href="<%= childGroupsHREF %>">
								<liferay-ui:message arguments="<%= String.valueOf(childGroups.size()) %>" key="x-child-sites" />
							</aui:a>
						</liferay-ui:search-container-column-text>
					</c:if>

					<liferay-ui:search-container-column-text
						name="type"
						value="<%= LanguageUtil.get(request, group.getScopeLabel(themeDisplay)) %>"
					/>
				</c:when>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<aui:script use="aui-base">
	Liferay.Util.selectEntityHandler('#<portlet:namespace />selectGroupFm', '<%= HtmlUtil.escapeJS(siteItemSelectorViewDisplayContext.getItemSelectedEventName()) %>');
</aui:script>