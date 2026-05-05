<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String virtualHostname = layoutsAdminDisplayContext.getVirtualHostname();
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="robots"
/>

<c:choose>
	<c:when test="<%= Validator.isNotNull(virtualHostname) %>">

		<%
		LayoutSet layoutSet = layoutsAdminDisplayContext.getSelLayoutSet();
		%>

		<p class="text-secondary" id="<portlet:namespace />robotsDescription"><liferay-ui:message key="robots-txt-help" /></p>

		<aui:input aria-describedby="<portlet:namespace />robotsDescription" label="robots" name='<%= "TypeSettingsProperties--" + layoutSet.isPrivateLayout() + "-robots.txt--" %>' placeholder="robots" type="textarea" value="<%= layoutsAdminDisplayContext.getRobots() %>" />

		<%
		String robotsContributions = layoutsAdminDisplayContext.getRobotsContributions();
		%>

		<c:if test="<%= Validator.isNotNull(robotsContributions) %>">
			<clay:alert
				displayType="info"
			>
				<liferay-ui:message key="individual-widget-configurations-contribute-these-robots-txt-entries" />

				<pre class="mb-0 mt-2"><%= HtmlUtil.escape(robotsContributions) %></pre>
			</clay:alert>
		</c:if>
	</c:when>
	<c:otherwise>
		<clay:alert
			displayType="info"
			message="please-set-the-virtual-host-before-you-set-the-robots-txt"
		/>
	</c:otherwise>
</c:choose>