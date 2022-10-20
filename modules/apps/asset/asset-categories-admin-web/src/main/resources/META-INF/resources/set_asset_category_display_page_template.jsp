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
String redirect = ParamUtil.getString(request, "redirect");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

long[] categoryIds = ParamUtil.getLongValues(request, "categoryIds");

renderResponse.setTitle(LanguageUtil.format(request, "assign-display-page-template-for-x-categories", categoryIds.length));
%>

<portlet:actionURL name="/asset_categories_admin/set_asset_category_display_page_template" var="setCategoryDisplayPageTemplateURL">
	<portlet:param name="categoryIds" value="<%= StringUtil.merge(categoryIds) %>" />
	<portlet:param name="redirect" value="<%= redirect %>" />
</portlet:actionURL>

<liferay-frontend:edit-form
	action="<%= setCategoryDisplayPageTemplateURL %>"
	name="fm"
>
	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset-group>
			<liferay-frontend:fieldset
				label="display-page"
			>
				<liferay-asset:select-asset-display-page
					classNameId="<%= PortalUtil.getClassNameId(AssetCategory.class) %>"
					classPK="<%= 0 %>"
					classTypeId="<%= 0 %>"
					groupId="<%= scopeGroupId %>"
					parentClassPK='<%= ParamUtil.getLong(request, "parentCategoryId") %>'
					showViewInContextLink="<%= true %>"
				/>
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons
			redirect="<%= redirect %>"
		/>
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>