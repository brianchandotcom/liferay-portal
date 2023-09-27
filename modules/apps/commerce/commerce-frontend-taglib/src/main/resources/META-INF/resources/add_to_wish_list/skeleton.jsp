<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/add_to_wish_list/init.jsp" %>

<%
String buttonCssClasses = "btn-outline-borderless btn btn-secondary";

if (GetterUtil.getBoolean(large)) {
	buttonCssClasses = buttonCssClasses.concat(" btn-lg");
}
else {
	buttonCssClasses = buttonCssClasses.concat(" btn-sm");
}
%>

<button class="<%= buttonCssClasses %> skeleton" type="button">
	<span class="text-truncate-inline">
		<span class="font-weight-normal text-truncate">
			<liferay-ui:message key="add-to-list" />
		</span>
	</span>
	<span class="wish-list-icon">
		<svg class="lexicon-icon lexicon-icon-heart" role="presentation"></svg>
	</span>
</button>