<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<div class="tags-admin-container lfr-app-column-view">
	<div class="lfr-header-row">
		<div class="lfr-header-row-content">
			<div class="toolbar">
				<aui:input cssClass="select-tags aui-state-default" inline="<%= true %>" label="" name="checkAllTags" type="checkbox" title='<%= LanguageUtil.get(pageContext, "check-all-tags") %>' />

				<liferay-ui:icon-menu
					align="left"
					direction="down"
					icon=""
					message="actions"
					showExpanded="<%= false %>"
					showWhenSingleIcon="true"
				>
					<liferay-ui:icon
						id="deleteSelectedTags"
						image="delete"
						url="javascript:;"
					/>

					<liferay-ui:icon
						id="mergeSelectedTags"
						image="../common/all_pages"
						message="merge"
						url="javascript:;"
					/>
				</liferay-ui:icon-menu>

				<aui:button-row cssClass="tags-admin-actions">
					<c:if test="<%= AssetPermission.contains(permissionChecker, themeDisplay.getParentGroupId(), ActionKeys.ADD_TAG) %>">
						<aui:button cssClass="add-tag-button" name="addTagButton" value="add-tag" />
					</c:if>

					<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, themeDisplay.getParentGroupId(), ActionKeys.PERMISSIONS) %>">
						<liferay-security:permissionsURL
							modelResource="com.liferay.portlet.asset"
							modelResourceDescription="<%= themeDisplay.getScopeGroupName() %>"
							resourcePrimKey="<%= String.valueOf(themeDisplay.getParentGroupId()) %>"
							var="permissionsURL"
							windowState="<%= LiferayWindowState.POP_UP.toString() %>"
						/>

						<aui:button data-url="<%= permissionsURL %>" name="tagsPermissionsButton" value="permissions" />
					</c:if>
				</aui:button-row>
			</div>

			<div class="lfr-search-combobox search-button-container tags-search-combobox">
				<aui:input cssClass="first keywords lfr-search-combobox-item tags-admin-search" label="" name="tagsAdminSearchInput" type="text" />
			</div>
		</div>
	</div>

	<div class="tags-admin-content-wrapper">
		<aui:layout cssClass="tags-admin-content">
			<aui:column columnWidth="35" cssClass="tags-admin-list-container">
				<div class="results-header">
					<liferay-ui:message key="tags" />
				</div>

				<div class="tags-admin-list lfr-component"></div>

				<div class="tags-paginator"></div>
			</aui:column>

			<aui:column columnWidth="65" cssClass="tags-admin-edit-tag">
				<div class="results-header">
					<liferay-ui:message key="tag-details" />
				</div>

				<div class="tag-view-container"></div>
			</aui:column>
		</aui:layout>
	</div>
</div>

</aui:form>

<aui:script use="liferay-tags-admin">
	new Liferay.Portlet.AssetTagsAdmin(
		{
			portletId: '<%= portletDisplay.getId() %>',
			tagsPerPage: <%= SearchContainer.DEFAULT_DELTA %>,
			tagsPerPageOptions: [<%= StringUtil.merge(PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES) %>]
		}
	);
</aui:script>