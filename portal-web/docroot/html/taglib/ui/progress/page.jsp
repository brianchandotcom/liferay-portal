<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/taglib/init.jsp" %>

<%
Integer height = (Integer)request.getAttribute("liferay-ui:progress:height");
String id = (String)request.getAttribute("liferay-ui:progress:id");
String message = (String)request.getAttribute("liferay-ui:progress:message");
String sessionKey = (String)request.getAttribute("liferay-ui:progress:sessionKey");

if (Validator.isNull(sessionKey)) {
	sessionKey = LiferayFileUpload.PERCENT;
}
%>

<div id="<%= id %>Bar"></div>

<aui:script use="liferay-progress">
	A.config.win['<%= id %>'] = new Liferay.ProcessProgress(
		{
			boundingBox: '#<%= id %>Bar',

			<c:if test="<%= Validator.isNotNull(height) %>">
				height: <%= height %>,
			</c:if>

			id: '<%= id %>',
			label: '<%= LanguageUtil.get(pageContext, message) %>',
			sessionKey: '<%= HtmlUtil.escapeJS(sessionKey) %>'
		}
	);
</aui:script>