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

<%@ include file="/html/portal/init.jsp" %>

<%
String sessionKey = ParamUtil.getString(request, "sessionKey");
String progressId = ParamUtil.getString(request, "progressId");

ProgressTracker progressTracker = (ProgressTracker)session.getAttribute(sessionKey);

if (progressTracker == null) {
	progressTracker = (ProgressTracker)session.getAttribute(sessionKey + progressId);
}

String message = StringPool.BLANK;
Integer percent = 0;

if (progressTracker != null) {
	message = progressTracker.getMessage();
	percent = progressTracker.getPercent();
}
%>

<html>
	<body>
		<script type="text/javascript">
			;(function() {
				var progressId = parent['<%= HtmlUtil.escapeJS(progressId) %>'];

				if (progressId && (typeof progressId.set == 'function')) {
					progressId.set('message', '<%= LanguageUtil.get(request, message) %>');
					progressId.set('value', <%= percent %>);
				}
			}());
		</script>
	</body>
</html>