<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/upload_progress/init.jsp" %>

<%
String id = (String)request.getAttribute("liferay-document-library:upload-progress:id");
String message = (String)request.getAttribute("liferay-document-library:upload-progress:message");
%>

<div id="<%= id %>Bar"></div>

<aui:script use="liferay-progress">
	A.config.win['<%= id %>'] = new Liferay.Progress({
		boundingBox: '#<%= id %>Bar',
		height: 25,
		id: '<%= id %>',
		label: '<%= UnicodeLanguageUtil.get(request, message) %>',
		sessionKey: '<%= HtmlUtil.escapeJS(ProgressTracker.PERCENT) %>',
	});
</aui:script>