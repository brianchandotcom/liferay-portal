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

<blockquote>
	<p>Tabs organize similar content together into individual sections in the same page.</p>
</blockquote>

<h3>DEFAULT TABS</h3>

<clay:row
	cssClass="mb-3"
>
	<clay:col>
		<clay:tabs
			tabsItems="<%= tabsDisplayContext.getDefaultTabsItems() %>"
		>
			<clay:tabs-panel><p class="mt-3">Tab Content 1</p></clay:tabs-panel>
			<clay:tabs-panel><p class="mt-3">Tab Content 2</p></clay:tabs-panel>
			<clay:tabs-panel><p class="mt-3">Tab Content 3</p></clay:tabs-panel>
			<clay:tabs-panel><p class="mt-3">Tab Content 4</p></clay:tabs-panel>
		</clay:tabs>
	</clay:col>
</clay:row>

<h3>BASIC TABS</h3>

<clay:row
	cssClass="mb-3"
>
	<clay:col>
		<clay:tabs
			displayType="basic"
			tabsItems="<%= tabsDisplayContext.getDefaultTabsItems() %>"
		>
			<clay:tabs-panel><p class="mt-3">Tab Content 1</p></clay:tabs-panel>
			<clay:tabs-panel><p class="mt-3">Tab Content 2</p></clay:tabs-panel>
			<clay:tabs-panel><p class="mt-3">Tab Content 3</p></clay:tabs-panel>
			<clay:tabs-panel><p class="mt-3">Tab Content 4</p></clay:tabs-panel>
		</clay:tabs>
	</clay:col>
</clay:row>

<h3>JUSTIFIED TABS</h3>

<clay:row
	cssClass="mb-3"
>
	<clay:col>
		<clay:tabs
			justified="<%= true %>"
			tabsItems="<%= tabsDisplayContext.getDefaultTabsItems() %>"
		>
			<clay:tabs-panel><p class="mt-3">Tab Content 1</p></clay:tabs-panel>
			<clay:tabs-panel><p class="mt-3">Tab Content 2</p></clay:tabs-panel>
			<clay:tabs-panel><p class="mt-3">Tab Content 3</p></clay:tabs-panel>
			<clay:tabs-panel><p class="mt-3">Tab Content 4</p></clay:tabs-panel>
		</clay:tabs>
	</clay:col>
</clay:row>