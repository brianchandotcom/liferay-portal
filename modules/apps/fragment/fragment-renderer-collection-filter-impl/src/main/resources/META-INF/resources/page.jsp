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

<p>
	<%= collectionFilterFragmentRendererDisplayContext.getAssetCategoryTreeNodeTitle() %>
</p>

<c:choose>
	<c:when test="<%= collectionFilterFragmentRendererDisplayContext.isMultipleSelection() %>">
		<div>
			<clay:button
				cssClass="dropdown-toggle form-control-select text-left"
				disabled="<%= true %>"
				displayType="secondary"
				label='<%= LanguageUtil.get(request, "select") %>'
			/>

			<react:component
				module="js/MultiSelectCategory.es"
			/>
		</div>
	</c:when>
	<c:otherwise>
		<clay:dropdown-menu
			cssClass="form-control form-control-select form-control-sm text-left"
			displayType="secondary"
			dropdownItems="<%= collectionFilterFragmentRendererDisplayContext.getDropdownItems() %>"
			label="<%= collectionFilterFragmentRendererDisplayContext.getSelectedAssetCategoryTitle() %>"
			title="<%= collectionFilterFragmentRendererDisplayContext.getAssetCategoryTreeNodeTitle() %>"
		/>
	</c:otherwise>
</c:choose>