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

<%@ include file="/html/portlet/asset_tag_admin/init.jsp" %>

<aui:form name="fm">
	<aui:nav-bar>
		<aui:nav cssClass="navbar-nav">
			<c:if test="<%= AssetPermission.contains(permissionChecker, themeDisplay.getSiteGroupId(), ActionKeys.ADD_TAG) %>">
				<aui:nav-item id="addTagButton" label="add-tag" />
			</c:if>

			<c:if test="<%= PropsValues.ASSET_TAG_PERMISSIONS_ENABLED && AssetPermission.contains(permissionChecker, themeDisplay.getSiteGroupId(), ActionKeys.PERMISSIONS) && GroupPermissionUtil.contains(permissionChecker, themeDisplay.getSiteGroupId(), ActionKeys.PERMISSIONS) %>">
				<liferay-security:permissionsURL
					modelResource="com.liferay.portlet.asset"
					modelResourceDescription="<%= themeDisplay.getScopeGroupName() %>"
					resourcePrimKey="<%= String.valueOf(themeDisplay.getSiteGroupId()) %>"
					var="permissionsURL"
					windowState="<%= LiferayWindowState.POP_UP.toString() %>"
				/>

				<aui:nav-item data-url="<%= permissionsURL %>" id="tagsPermissionsButton" label="permissions" />
			</c:if>

			<aui:nav-item cssClass="hide" dropdown="<%= true %>" id="tagsActionsButton" label="actions">
				<aui:nav-item iconCssClass="icon-random" id="mergeSelectedTags" label="merge" />

				<aui:nav-item cssClass="item-remove" iconCssClass="icon-remove" id="deleteSelectedTags" label="delete" />
			</aui:nav-item>
		</aui:nav>

		<aui:nav-bar-search cssClass="pull-right">
			<div class="col-xs-12 form-search">
				<liferay-ui:input-search id="tagsAdminSearchInput" name="tagsAdminSearchInput" showButton="<%= false %>" />
			</div>
		</aui:nav-bar-search>
	</aui:nav-bar>

	<div class="lfr-app-column-view tags-admin-container">
		<div class="tags-admin-content-wrapper">
			<aui:row cssClass="tags-admin-content">
				<aui:col cssClass="tags-admin-list-container" width="<%= 35 %>">
					<div class="hide selected-tags-wrapper">
						<h3 class="tags-header"><%= LanguageUtil.get(pageContext, "selected") %></h3>

						<div class="tag-staging-area">
							<div class="token-container"></div>
						</div>
					</div>

					<div class="available-tags-wrapper">
						<aui:input cssClass="select-tags" inline="<%= true %>" label="" name="checkAllTags" title='<%= LanguageUtil.get(pageContext, "check-all-tags") %>' type="checkbox" />

						<h3 class="tags-header"><%= LanguageUtil.get(pageContext, "available") %></h3>
					</div>

					<div class="list-unstyled tags-admin-list"></div>

					<div class="tags-pagination"></div>
				</aui:col>

				<aui:col cssClass="tags-admin-edit-tag" width="<%= 65 %>">
					<h3><%= LanguageUtil.get(pageContext, "tag-details") %></h3>

					<div class="tag-view-container"></div>
				</aui:col>
			</aui:row>
		</div>
	</div>
</aui:form>

<aui:script use="liferay-tags-admin">
	new Liferay.Portlet.AssetTagsAdmin(
		{
			baseActionURL: '<%= PortletURLFactoryUtil.create(request, portletDisplay.getId(), themeDisplay.getPlid(), PortletRequest.ACTION_PHASE) %>',
			baseRenderURL: '<%= PortletURLFactoryUtil.create(request, portletDisplay.getId(), themeDisplay.getPlid(), PortletRequest.RENDER_PHASE) %>',
			portletId: '<%= portletDisplay.getId() %>',
			tagsPerPage: <%= SearchContainer.DEFAULT_DELTA %>
		}
	);
</aui:script>