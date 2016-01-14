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

<liferay-ui:icon
	id="emptyRecycleBinButton"
	message="empty-the-recycle-bin"
	url="javascript:;"
/>

<portlet:actionURL name="emptyTrash" var="emptyTrashURL">
	<portlet:param name="groupId" value="<%= String.valueOf(themeDisplay.getScopeGroupId()) %>" />
</portlet:actionURL>

<aui:script>
	$('#<portlet:namespace />emptyRecycleBinButton').on(
		'click',
		function(event) {
			if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-empty-the-recycle-bin" />')) {
				submitForm(document.hrefFm, '<%= emptyTrashURL.toString() %>');
			}
		}
	);
</aui:script>