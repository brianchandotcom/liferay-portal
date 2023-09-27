<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/mime_type_sticker/init.jsp" %>

<%
String cssClass = (String)request.getAttribute("liferay-document-library:mime-type-sticker:cssClass");
DLViewFileVersionDisplayContext dlViewFileVersionDisplayContext = (DLViewFileVersionDisplayContext)request.getAttribute("liferay-document-library:mime-type-sticker:dlViewFileVersionDisplayContext");
%>

<clay:sticker
	cssClass='<%= "sticker-document " + cssClass + " " + dlViewFileVersionDisplayContext.getCssClassFileMimeType() %>'
	icon="<%= dlViewFileVersionDisplayContext.getIconFileMimeType() %>"
/>