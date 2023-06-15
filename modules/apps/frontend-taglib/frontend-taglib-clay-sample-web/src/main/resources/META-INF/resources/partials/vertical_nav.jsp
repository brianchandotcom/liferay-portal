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
	<p>An alternative patterns that displays navigation items in a vertical menu.</p>
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