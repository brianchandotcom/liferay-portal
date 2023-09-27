<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/taglib/ui/progress/init.jsp" %>

<%
Integer height = (Integer)request.getAttribute("liferay-ui:progress:height");
String id = (String)request.getAttribute("liferay-ui:progress:id");
String message = (String)request.getAttribute("liferay-ui:progress:message");
String sessionKey = GetterUtil.getString(request.getAttribute("liferay-ui:progress:sessionKey"), ProgressTracker.PERCENT);
%>

<div id="<%= id %>Bar"></div>

<aui:script use="liferay-progress">
	A.config.win['<%= id %>'] = new Liferay.Progress(
		{
			boundingBox: '#<%= id %>Bar',

			<c:if test="<%= height != null %>">
				height: <%= height %>,
			</c:if>

			id: '<%= id %>',
			label: '<%= UnicodeLanguageUtil.get(resourceBundle, message) %>',
			sessionKey: '<%= HtmlUtil.escapeJS(sessionKey) %>'
		}
	);
</aui:script>