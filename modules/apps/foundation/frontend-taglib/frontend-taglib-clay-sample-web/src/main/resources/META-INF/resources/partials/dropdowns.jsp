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
			items="<%= dropdownsDisplayContext.getDefaultDropdownItemList() %>"
			label="Default"
		/>
	</div>

	<div class="col-md-2">
		<clay:dropdown-menu
			items="<%= dropdownsDisplayContext.getGroupDropdownItemList() %>"
			label="Dividers"
		/>
	</div>

	<div class="col-md-2">
		<clay:dropdown-menu
			buttonLabel="Done"
			items="<%= dropdownsDisplayContext.getInputDropdownItemList() %>"
			label="Inputs"
			searchable="<%= true %>"
		/>
	</div>

	<div class="col-md-2">
		<clay:dropdown-menu
			icon="share"
			items="<%= dropdownsDisplayContext.getDefaultDropdownItemList() %>"
			label="Icon"
		/>
	</div>

	<div class="col-md-2">
		<clay:dropdown-menu
			items="<%= dropdownsDisplayContext.getIconDropdownItemList() %>"
			itemsIconAlignment="left"
			label="Icons"
		/>
	</div>
</div>

<div class="row">
	<div class="col-md-4">
		<clay:dropdown-menu
			items="<%= dropdownsDisplayContext.getDefaultDropdownItemList() %>"
			itemsIconAlignment="left"
			label="Secondary Borderless"
			style="secondary"
			triggerCssClasses="btn-outline-borderless"
		/>
	</div>

	<div class="col-md-2">
		<clay:dropdown-actions
			items="<%= dropdownsDisplayContext.getDefaultDropdownItemList() %>"
		/>
	</div>

	<div class="col-md-2">
		<clay:dropdown-actions
			buttonLabel="More"
			buttonStyle="secondary"
			caption="Showing 4 of 32 Options"
			helpText="You can customize this menu or see all you have by pressing \"more\"."
			items="<%= dropdownsDisplayContext.getDefaultDropdownItemList() %>"
		/>
	</div>

	<div class="col-md-2">
		<clay:dropdown-actions
			buttonLabel="More"
			buttonStyle="secondary"
			caption="Showing 4 of 32 Options"
			helpText="You can customize this menu or see all you have by pressing \"more\"."
			items="<%= dropdownsDisplayContext.getDefaultDropdownItemList() %>"
			triggerCssClasses="btn-outline-borderless"
		/>
	</div>
</div>