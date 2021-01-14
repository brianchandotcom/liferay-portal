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

<%@ include file="/add_to_order/init.jsp" %>

<%
String buttonClasses = "btn btn-add-to-cart";
String selectorClasses = "form-control quantity-selector";
String wrapperClasses = "add-to-cart-wrapper align-items-center d-flex";

if (GetterUtil.getBoolean(block)) {
	buttonClasses = buttonClasses.concat(" btn-block");
	wrapperClasses = wrapperClasses.concat(" flex-column");
}
else {
	buttonClasses = buttonClasses.concat(" btn-lg");
	selectorClasses = selectorClasses.concat(" form-control-lg");
}
%>

<div class="<%= wrapperClasses %>">
	<div class="<%= selectorClasses %> skeleton"></div>

	<button class="<%= buttonClasses %> skeleton">
		<%= LanguageUtil.get(request, "add-to-cart") %>
	</button>
</div>