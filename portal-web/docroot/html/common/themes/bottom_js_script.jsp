<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/common/init.jsp" %>

<%
String snippet = ParamUtil.getString(request, "snippet");
%>

<c:if test="<%= Validator.isNotNull(snippet) %>">
	<aui:script type="text/javascript">
		// <![CDATA[
			(function() {
				<%= snippet %>
			})();
		// ]]>
	</aui:script>
</c:if>