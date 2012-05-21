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
Integer height = (Integer)request.getAttribute("liferay-ui:upload-progress:height");
String id = (String)request.getAttribute("liferay-ui:upload-progress:id");
String message = (String)request.getAttribute("liferay-ui:upload-progress:message");
%>

<div id="<%= id %>Bar"></div>

<aui:script use="liferay-upload-progress">
	A.config.win['<%= id %>'] = new Liferay.UploadProgressBar(
		{
			boundingBox: '#<%= id %>Bar',

			<c:if test="<%= Validator.isNotNull(height) %>">
				height: <%= height %>,
			</c:if>

			id: '<%= id %>',
			label: '<%= LanguageUtil.get(pageContext, message) %>'
		}
	);
</aui:script>