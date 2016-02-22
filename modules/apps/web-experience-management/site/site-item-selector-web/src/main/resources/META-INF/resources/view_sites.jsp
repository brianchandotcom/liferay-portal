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

String displayStyle = ParamUtil.getString(request, "displayStyle", "icon");
String target = ParamUtil.getString(request, "target");

PortletURL portletURL = siteItemSelectorViewDisplayContext.getPortletURL();
%>

<liferay-frontend:management-bar>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-filters>
			<liferay-frontend:management-bar-navigation
				navigationKeys='<%= new String[] {"all"} %>'
				portletURL="<%= siteItemSelectorViewDisplayContext.getPortletURL() %>"
			/>
		</liferay-frontend:management-bar-filters>

		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list", "icon"} %>'
			portletURL="<%= siteItemSelectorViewDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="selectGroupFm">
	<c:if test="<%= siteItemSelectorViewDisplayContext.isShowChildSitesLink() %>">
		<div id="breadcrumb">
			<liferay-ui:breadcrumb showCurrentGroup="<%= false %>" showGuestGroup="<%= false %>" showLayout="<%= false %>" showPortletBreadcrumb="<%= true %>" />
		</div>
	</c:if>

	<liferay-ui:search-container
		searchContainer="<%= siteItemSelectorViewDisplayContext.getGroupSearch() %>"
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
				<c:when test='<%= displayStyle.equals("icon") %>'>

					<%
					row.setCssClass("article-entry col-md-2 col-sm-4 col-xs-6 " + row.getCssClass());

					Map<String, Object> linkData = new HashMap<String, Object>();

					linkData.put("prevent-selection", true);
					%>

					<liferay-ui:search-container-column-text>
						<div role="button">
							<c:choose>
								<c:when test="<%= Validator.isNotNull(group.getLogoURL(themeDisplay, false)) %>">
									<liferay-frontend:vertical-card
										cssClass="selector-button"
										data="<%= data %>"
										imageUrl="<%= group.getLogoURL(themeDisplay, false) %>"
										resultRow="<%= row %>"
										rowChecker="<%= searchContainer.getRowChecker() %>"
										showCheckbox="<%= false %>"
										title="<%= siteItemSelectorViewDisplayContext.getGroupName(group) %>"
									>
										<c:if test="<%= siteItemSelectorViewDisplayContext.isShowChildSitesLink() %>">
											<liferay-frontend:vertical-card-footer>
												<aui:a cssClass='<%= !childGroups.isEmpty() ? "text-default" : "disabled" %>' data="<%= linkData %>" href="<%= childGroupsHREF %>">
													<liferay-ui:message arguments="<%= String.valueOf(childGroups.size()) %>" key="x-child-sites" />
												</aui:a>
											</liferay-frontend:vertical-card-footer>
										</c:if>
									</liferay-frontend:vertical-card>
								</c:when>
								<c:otherwise>
									<liferay-frontend:icon-vertical-card
										cssClass="selector-button"
										data="<%= data %>"
										icon="sites"
										resultRow="<%= row %>"
										rowChecker="<%= searchContainer.getRowChecker() %>"
										showCheckbox="<%= false %>"
										title="<%= siteItemSelectorViewDisplayContext.getGroupName(group) %>"
									>
										<liferay-frontend:vertical-card-footer>
											<c:if test="<%= siteItemSelectorViewDisplayContext.isShowChildSitesLink() %>">
												<aui:a cssClass='<%= !childGroups.isEmpty() ? "text-default" : "disabled" %>' data="<%= linkData %>" href="<%= childGroupsHREF %>">
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
				<c:otherwise>
					<liferay-ui:search-container-column-text
						name="name"
					>
						<aui:a cssClass="selector-button" data="<%= data %>" href="javascript:;">
							<%= HtmlUtil.escape(group.getDescriptiveName(locale)) %>
						</aui:a>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						name="type"
						value="<%= LanguageUtil.get(request, group.getScopeLabel(themeDisplay)) %>"
					/>
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<aui:script use="aui-base">
	Liferay.Util.selectEntityHandler('#<portlet:namespace />selectGroupFm', '<%= HtmlUtil.escapeJS(siteItemSelectorViewDisplayContext.getItemSelectedEventName()) %>');
</aui:script>