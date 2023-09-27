<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<blockquote>
	<p>Vertical navigation implements a pattern that displays navigation items in a vertical menu.</p>
</blockquote>

<h3>DEFAULT VERTICAL NAV</h3>

<clay:row
	cssClass="mb-3"
>
	<clay:col>
		<clay:vertical-nav
			verticalNavItems="<%= verticalNavDisplayContext.getVerticalNavItems() %>"
		/>
	</clay:col>
</clay:row>

<h3>LARGE VERTICAL NAV</h3>

<clay:row
	cssClass="mb-3"
>
	<clay:col>
		<clay:vertical-nav
			large="<%= true %>"
			verticalNavItems="<%= verticalNavDisplayContext.getVerticalNavItems() %>"
		/>
	</clay:col>
</clay:row>

<h3>DECORATED VERTICAL NAV</h3>

<clay:row
	cssClass="mb-3"
>
	<clay:col>
		<clay:vertical-nav
			decorated="<%= true %>"
			verticalNavItems="<%= verticalNavDisplayContext.getVerticalNavItems() %>"
		/>
	</clay:col>
</clay:row>

<h3>PREDEFINED ACTIVE VERTICAL NAV</h3>

<clay:row
	cssClass="mb-3"
>
	<clay:col>
		<clay:vertical-nav
			active="id-1"
			verticalNavItems="<%= verticalNavDisplayContext.getVerticalNavItems() %>"
		/>
	</clay:col>
</clay:row>

<h3>PREDEFINED EXPANDED VERTICAL NAV</h3>

<clay:row
	cssClass="mb-3"
>
	<clay:col>
		<clay:vertical-nav
			defaultExpandedKeys="<%= verticalNavDisplayContext.getVerticalNavDefaultExpandedKeys() %>"
			verticalNavItems="<%= verticalNavDisplayContext.getVerticalNavItems() %>"
		/>
	</clay:col>
</clay:row>