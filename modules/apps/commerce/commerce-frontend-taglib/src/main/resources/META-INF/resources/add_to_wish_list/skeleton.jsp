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

<%@ include file="/add_to_wish_list/init.jsp" %>

<%
String buttonClasses = "btn-outline-borderless btn btn-secondary";

if (GetterUtil.getBoolean(large)) {
	buttonClasses = buttonClasses.concat(" btn-lg");
}
else {
	buttonClasses = buttonClasses.concat(" btn-sm");
}
%>

<button class="<%= buttonClasses %> skeleton" type="button">
	<span class="text-truncate-inline">
		<span class="font-weight-normal text-truncate">
			<%= LanguageUtil.get(request, "add-to-list") %>
		</span>
	</span>
	<span class="wish-list-icon">
		<svg class="lexicon-icon lexicon-icon-heart" role="presentation"></svg>
	</span>
</button>