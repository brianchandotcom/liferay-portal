<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<h3>DROPDOWN MENU</h3>

<blockquote>
	<p>A dropdown is a list of options related to the element that triggers it.</p>
</blockquote>

<clay:row>
	<clay:col
		md="2"
	>
		<clay:dropdown-menu
			dropdownItems="<%= dropdownsDisplayContext.getDefaultDropdownItems() %>"
			label="Default"
		/>
	</clay:col>

	<clay:col
		md="2"
	>
		<clay:dropdown-menu
			dropdownItems="<%= dropdownsDisplayContext.getGroupDropdownItems() %>"
			label="Dividers"
		/>
	</clay:col>

	<clay:col
		md="2"
	>
		<clay:dropdown-menu
			dropdownItems="<%= dropdownsDisplayContext.getInputDropdownItems() %>"
			label="Done"
		/>
	</clay:col>

	<clay:col
		md="2"
	>
		<clay:dropdown-menu
			dropdownItems="<%= dropdownsDisplayContext.getDefaultDropdownItems() %>"
			icon="share"
			label="Icon"
		/>
	</clay:col>

	<clay:col
		md="2"
	>
		<clay:dropdown-menu
			dropdownItems="<%= dropdownsDisplayContext.getIconDropdownItems() %>"
			label="Icons"
		/>
	</clay:col>
</clay:row>

<clay:row>
	<clay:col
		md="4"
	>
		<clay:dropdown-menu
			cssClass="btn-outline-borderless"
			displayType="secondary"
			dropdownItems="<%= dropdownsDisplayContext.getDefaultDropdownItems() %>"
			label="Secondary Borderless"
		/>
	</clay:col>

	<clay:col
		md="2"
	>
		<clay:dropdown-actions
			aria-label="Show Actions"
			dropdownItems="<%= dropdownsDisplayContext.getDefaultDropdownItems() %>"
		/>
	</clay:col>

	<clay:col
		md="2"
	>
		<clay:dropdown-actions
			caption="Showing 4 of 32 Options"
			displayType="secondary"
			dropdownItems="<%= dropdownsDisplayContext.getDefaultDropdownItems() %>"
			helpText="You can customize this menu or see all you have by pressing \"more\"."
			label="More"
		/>
	</clay:col>

	<clay:col
		md="2"
	>
		<clay:dropdown-actions
			caption="Showing 4 of 32 Options"
			cssClass="btn-outline-borderless"
			displayType="secondary"
			dropdownItems="<%= dropdownsDisplayContext.getDefaultDropdownItems() %>"
			helpText="You can customize this menu or see all you have by pressing \"more\"."
			label="More"
		/>
	</clay:col>
</clay:row>