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

<%@ include file="/html/portal/init.jsp" %>

<%
String uploadProgressId = ParamUtil.getString(request, "uploadProgressId");

String fileName = GetterUtil.getString((String)session.getAttribute(LiferayFileUpload.FILE_NAME + uploadProgressId));

if (fileName == null) {
	fileName = GetterUtil.getString((String)session.getAttribute(LiferayFileUpload.FILE_NAME));
}

Integer percent = (Integer)session.getAttribute(LiferayFileUpload.PERCENT + uploadProgressId);

if (percent == null) {
	percent = (Integer)session.getAttribute(LiferayFileUpload.PERCENT);
}
if (percent == null) {
	percent = new Integer(100);
}

if (percent.floatValue() >= 100) {
	session.removeAttribute(LiferayFileUpload.FILE_NAME);
	session.removeAttribute(LiferayFileUpload.FILE_NAME + uploadProgressId);
	session.removeAttribute(LiferayFileUpload.PERCENT);
	session.removeAttribute(LiferayFileUpload.PERCENT + uploadProgressId);
}
%>

<html>

<body>

<script type="text/javascript">
	;(function() {
		var progressId = parent['<%= HtmlUtil.escapeJS(uploadProgressId) %>'];

		if (progressId && typeof progressId.updateBar == 'function') {
			progressId.updateBar(<%= percent.intValue() %>, '<%= HtmlUtil.escapeJS(fileName) %>');
		}

		<c:if test="<%= percent.intValue() < 100 %>">
			setTimeout(window.location.reload, 1000);
		</c:if>
	}());
</script>

</body>

</html>