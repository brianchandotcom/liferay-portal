<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/modal/init.jsp" %>

<%
String containerId = randomNamespace + "modal-root";
%>

<div class="modal-root" id="<%= containerId %>"></div>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"containerId", containerId
		).put(
			"id", id
		).put(
			"portletId", portletDisplay.getRootPortletId()
		).put(
			"refreshPageOnClose", refreshPageOnClose
		).put(
			"size", size
		).put(
			"spritemap", spritemap
		).put(
			"title", title
		).put(
			"url", url
		).build()
	%>'
	module="{modal} from commerce-frontend-taglib"
/>