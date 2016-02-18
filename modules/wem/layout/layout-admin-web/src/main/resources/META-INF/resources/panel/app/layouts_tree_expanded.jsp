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

<div class="product-menu product-menu-expanded sidebar-body sidenav-fixed sidenav-menu-slider">
	<div class="panel">
		<div class="nav nav-equal-height">
			<liferay-util:include page="/panel/app/layouts_tree.jsp" servletContext="<%= application %>" />
		</div>
	</div>
</div>

<aui:script use="aui-base">
	var opener = Liferay.Util.getOpener();

	A.one('.product-menu').delegate(
		'click',
		function(event) {
			event.preventDefault();
			opener.document.location.href = event.currentTarget.get('href');
		},
		'a'
	);
</aui:script>