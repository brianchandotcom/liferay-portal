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

<p class="font-weight-semi-bold mb-1 <%= fragmentCollectionFilterCategoryDisplayContext.isShowLabel() ? "" : "sr-only" %>">
	<%= fragmentCollectionFilterCategoryDisplayContext.getLabel() %>
</p>

<div>
	<clay:button
		cssClass="bg-light dropdown-toggle font-weight-normal form-control-select form-control-sm text-left w-100"
		disabled="<%= true %>"
		displayType="secondary"
		label='<%= LanguageUtil.get(request, "select") %>'
		small="<%= true %>"
	/>

	<react:component
		module="js/SelectCategory.es"
		props="<%= fragmentCollectionFilterCategoryDisplayContext.getProps() %>"
	/>
</div>