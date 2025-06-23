<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/import/init.jsp" %>
<c:if test='<%= FeatureFlagManagerUtil.isEnabled("LPD-35914") %>'>
	<aui:form method="post" name="fm">
		<%
			String apiURL = "/group/__mocks__/get-import-error-detail";
		%>

		<h1>View Import Error Detail</h1>
	</aui:form>
</c:if>