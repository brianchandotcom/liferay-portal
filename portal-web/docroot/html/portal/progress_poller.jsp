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
String progressId = ParamUtil.getString(request, "progressId");

int updatePeriod = ParamUtil.getInteger(request, "updatePeriod", 1000);

String sessionKey = ParamUtil.getString(request, "sessionKey");


String message = StringPool.BLANK;
Integer percent = null;

boolean finished = false;

ProgressTracker progressTracker = (ProgressTracker)session.getAttribute(sessionKey);

if (progressTracker == null) {
	progressTracker = (ProgressTracker)session.getAttribute(sessionKey + progressId);
}

if (progressTracker != null) {
	message = progressTracker.getMessage();
	percent = progressTracker.getPercent();

	if (progressTracker.isFinished()) {
		finished = true;

		progressTracker.finish();
	}
}
else {
	percent = 0;
}
%>

<html>

<body>

<script type="text/javascript">
	;(function() {
		var progressId = parent['<%= HtmlUtil.escapeJS(progressId) %>'];

		if (progressId && (typeof progressId.set == 'function')) {
			progressId.set('message', '<%= LanguageUtil.get(pageContext, message) %>');

			progressId.set('value', <%= percent %>);
		}

		<c:if test="<%= percent < 100 && !finished %>">
			setTimeout(
				function() {
					window.location.reload();
				},
				<%= updatePeriod %>
			);
		</c:if>
	}());
</script>

</body>

</html>