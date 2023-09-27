<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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