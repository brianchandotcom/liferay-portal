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
SelectThemeDisplayContext selectThemeDisplayContext = new SelectThemeDisplayContext(request, liferayPortletRequest, liferayPortletResponse);
%>

<clay:management-toolbar-v2
	displayContext="<%= new SelectThemeManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, selectThemeDisplayContext) %>"
/>

<c:if test="<%= permissionChecker.isOmniadmin() && PortletLocalServiceUtil.hasPortlet(themeDisplay.getCompanyId(), PortletKeys.MARKETPLACE_STORE) && PropsValues.AUTO_DEPLOY_ENABLED %>">

	<%
	PortletURL marketplaceURL = PortalUtil.getControlPanelPortletURL(request, PortletKeys.MARKETPLACE_STORE, PortletRequest.RENDER_PHASE);
	%>

	<div class="button-holder">
		<aui:button cssClass="manage-layout-set-branches-link" href="<%= marketplaceURL.toString() %>" id="installMore" value="install-more" />
	</div>
</c:if>

<aui:form cssClass="container-fluid container-fluid-max-xl" name="selectThemeFm">
	<liferay-ui:search-container
		id="themes"
		searchContainer="<%= selectThemeDisplayContext.getThemesSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.Theme"
			escapedModel="<%= true %>"
			keyProperty="themeId"
			modelVar="theme"
		>

			<%
			PluginPackage selPluginPackage = theme.getPluginPackage();

			Map<String, Object> data = HashMapBuilder.<String, Object>put(
				"themeid", theme.getThemeId()
			).build();

			String themeId = ParamUtil.getString(request, "themeId");

			if (themeId.equals(theme.getThemeId())) {
				row.setCssClass("active");
			}
			%>

			<c:choose>
				<c:when test='<%= Objects.equals(selectThemeDisplayContext.getDisplayStyle(), "descriptive") %>'>
					<liferay-ui:search-container-column-image
						src='<%= theme.getStaticResourcePath() + theme.getImagesPath() + "/thumbnail.png" %>'
					/>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>
						<h5>
							<aui:a cssClass="selector-button" data="<%= data %>" href="javascript:;">
								<%= theme.getName() %>
							</aui:a>
						</h5>

						<c:if test="<%= (selPluginPackage != null) && Validator.isNotNull(selPluginPackage.getAuthor()) %>">
							<h6 class="text-default">
								<span><%= LanguageUtil.format(request, "by-x", selPluginPackage.getAuthor()) %></span>
							</h6>
						</c:if>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test='<%= Objects.equals(selectThemeDisplayContext.getDisplayStyle(), "icon") %>'>

					<%
					row.setCssClass(row.getCssClass() + " card-page-item card-page-item-asset");
					%>

					<liferay-ui:search-container-column-text>
						<clay:vertical-card
							verticalCard="<%= new SelectThemeVerticalCard(theme, renderRequest) %>"
						/>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test='<%= Objects.equals(selectThemeDisplayContext.getDisplayStyle(), "list") %>'>
					<liferay-ui:search-container-column-text
						name="name"
						truncate="<%= true %>"
					>
						<aui:a cssClass="selector-button" data="<%= data %>" href="javascript:;">
							<%= theme.getName() %>
						</aui:a>
					</liferay-ui:search-container-column-text>

					<%
					String author = StringPool.BLANK;

					if ((selPluginPackage != null) && Validator.isNotNull(selPluginPackage.getAuthor())) {
						author = selPluginPackage.getAuthor();
					}
					%>

					<liferay-ui:search-container-column-text
						name="author"
						value="<%= author %>"
					/>
				</c:when>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= selectThemeDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>