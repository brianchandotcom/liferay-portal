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
PanelCategory panelCategory = (PanelCategory)request.getAttribute(ApplicationListWebKeys.PANEL_CATEGORY);

ContentPanelCategoryDisplayContext contentPanelCategoryDisplayContext = new ContentPanelCategoryDisplayContext(renderRequest);
%>

<liferay-application-list:panel-category
	panelCategory="<%= panelCategory %>"
	showBody="<%= false %>"
>

	<%
	Group curSite = themeDisplay.getSiteGroup();

	List<Layout> scopeLayouts = LayoutLocalServiceUtil.getScopeGroupLayouts(curSite.getGroupId());
	%>

	<c:choose>
		<c:when test="<%= scopeLayouts.isEmpty() %>">
			<liferay-application-list:panel-category-body
				panelCategory="<%= panelCategory %>"
			/>
		</c:when>
		<c:otherwise>

			<%
			PanelAppRegistry panelAppRegistry = (PanelAppRegistry)request.getAttribute(ApplicationListWebKeys.PANEL_APP_REGISTRY);

			List<PanelApp> panelApps = panelAppRegistry.getPanelApps(panelCategory, themeDisplay.getPermissionChecker(), themeDisplay.getScopeGroup());
			%>

			<c:if test="<%= !panelApps.isEmpty() %>">
				<div class="scope-selector">

					<%
					Group curScopeGroup = themeDisplay.getScopeGroup();
					%>

					<clay:content-row
						verticalAlign="center"
					>
						<clay:content-col
							expand="<%= true %>"
						>
							<span class="scope-name">
								<c:choose>
									<c:when test="<%= curScopeGroup.isLayout() %>">
										<%= curScopeGroup.getDescriptiveName(locale) %> (<liferay-ui:message key="scope" />)
									</c:when>
									<c:otherwise>
										<liferay-ui:message key="default-scope" />
									</c:otherwise>
								</c:choose>
							</span>
						</clay:content-col>

						<clay:content-col>
							<clay:dropdown-menu
								borderless="<%= true %>"
								cssClass="text-light"
								displayType="secondary"
								dropdownItems="<%= contentPanelCategoryDisplayContext.getScopesDropdownItemList() %>"
								icon="cog"
								monospaced="<%= true %>"
							/>
						</clay:content-col>
					</clay:content-row>

					<liferay-application-list:panel-category-body
						panelCategory="<%= panelCategory %>"
					/>
				</div>
			</c:if>
		</c:otherwise>
	</c:choose>
</liferay-application-list:panel-category>