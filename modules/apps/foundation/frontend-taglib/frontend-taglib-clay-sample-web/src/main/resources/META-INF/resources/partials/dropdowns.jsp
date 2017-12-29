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

<h3>DROPDOWN MENU</h3>

<blockquote><p>A dropdown is a list of options related to the element that triggers it.</p></blockquote>

<div class="row">
	<div class="col-md-2">
		<clay:dropdown-menu
			items="<%= dropdownsDisplayContext.getDefaultDropdownItems() %>"
			label="Default"
		/>
	</div>

	<div class="col-md-2">
		<clay:dropdown-menu
			items="<%= dropdownsDisplayContext.getGroupDropdownItems() %>"
			label="Dividers"
		/>
	</div>

	<div class="col-md-2">
		<clay:dropdown-menu
			buttonLabel="Done"
			items="<%= dropdownsDisplayContext.getInputDropdownItems() %>"
			label="Inputs"
			searchable="<%= true %>"
		/>
	</div>

	<div class="col-md-2">
		<clay:dropdown-menu
			items="<%= dropdownsDisplayContext.getIconDropdownItems() %>"
			itemsIconAlignment="left"
			label="Icons"
		/>
	</div>

	<div class="col-md-2">
		<clay:dropdown-actions
			items="<%= dropdownsDisplayContext.getDefaultDropdownItems() %>"
		/>
	</div>

	<div class="col-md-2">
		<clay:dropdown-actions
			buttonLabel="More"
			buttonStyle="secondary"
			caption="Showing 4 of 32 Options"
			helpText="You can customize this menu or see all you have by pressing \"more\"."
			items="<%= dropdownsDisplayContext.getDefaultDropdownItems() %>"
		/>
	</div>
</div>