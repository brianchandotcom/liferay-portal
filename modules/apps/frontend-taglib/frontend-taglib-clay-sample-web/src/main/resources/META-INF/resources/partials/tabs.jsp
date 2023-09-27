<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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