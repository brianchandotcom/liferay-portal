<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
		<aui:nav>
			<c:if test="<%= AssetPermission.contains(permissionChecker, themeDisplay.getSiteGroupId(), ActionKeys.ADD_TAG) %>">
				<aui:nav-item id="addTagButton" label="add-tag" />
			</c:if>

			<c:if test="<%= AssetPermission.contains(permissionChecker, themeDisplay.getSiteGroupId(), ActionKeys.PERMISSIONS) && GroupPermissionUtil.contains(permissionChecker, themeDisplay.getSiteGroupId(), ActionKeys.PERMISSIONS) %>">
				<liferay-security:permissionsURL
					modelResource="com.liferay.portlet.asset"
					modelResourceDescription="<%= themeDisplay.getScopeGroupName() %>"
					resourcePrimKey="<%= String.valueOf(themeDisplay.getSiteGroupId()) %>"
					var="permissionsURL"
					windowState="<%= LiferayWindowState.POP_UP.toString() %>"
				/>

				<aui:nav-item data-url="<%= permissionsURL %>" id="tagsPermissionsButton" label="permissions" />
			</c:if>


			<aui:nav-item dropdown="<%= true %>" label="actions">
				<aui:nav-item iconClass="icon-trash" id="deleteSelectedTags" label="delete" />

				<aui:nav-item iconClass="icon-random" id="mergeSelectedTags" label="merge" />
			</aui:nav-item>
		</aui:nav>

		<div class="navbar-search pull-right">
			<div class="form-search">
				<input class="search-query span9" id="<portlet:namespace/>tagsAdminSearchInput" name="<portlet:namespace/>tagsAdminSearchInput" type="text" />
			</div>
		</div>
	</aui:nav-bar>

	<div class="tags-admin-container lfr-app-column-view">
		<div class="tags-admin-content-wrapper">
			<aui:row cssClass="tags-admin-content">
				<aui:col cssClass="tags-admin-list-container" width="<%= 35 %>">
					<span>
						<aui:input cssClass="select-tags" inline="<%= true %>" label="" name="checkAllTags" title='<%= LanguageUtil.get(pageContext, "check-all-tags") %>' type="checkbox" />
					</span>

					<h3 class="tags-header"><%= LanguageUtil.get(pageContext, "tags") %></h3>

					<div class="tags-admin-list unstyled"></div>

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