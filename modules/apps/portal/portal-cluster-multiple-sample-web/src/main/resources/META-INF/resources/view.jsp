<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ page import="com.liferay.portal.cluster.multiple.sample.web.internal.ClusterSampleData" %>

<portlet:defineObjects />

<%
ClusterSampleData clusterSampleData = new ClusterSampleData();
%>

<h4>Server Data:</h4>

<p>Following data is from the server that generated this response:</p>

<ul>
	<li>
		<b>Computer Name:</b> <%= clusterSampleData.getComputerName() %>
	</li>
	<li>
		<b>Liferay Home:</b> <%= clusterSampleData.getLiferayHome() %>
	</li>
	<li>
		<b>Current timestamp:</b> <%= clusterSampleData.getTimestamp() %>
	</li>
</ul>

<h4>Session Data:</h4>

<%
ClusterSampleData portletSessionClusterSampleData = (ClusterSampleData)portletSession.getAttribute(ClusterSampleData.class.getName());
%>

<c:choose>
	<c:when test="<%= portletSessionClusterSampleData != null %>">
		<p>Following data is stored in the portlet session:</p>

		<ul>
			<li>
				<b>Stored Data:</b> <%= portletSessionClusterSampleData.getData() %>
			</li>
			<li>
				<b>Stored Timestamp:</b> <%= portletSessionClusterSampleData.getTimestamp() %>
			</li>
		</ul>

		<p>The data was stored by:</p>

		<ul>
			<li>
				<b>Computer Name:</b> <%= portletSessionClusterSampleData.getComputerName() %>
			</li>
			<li>
				<b>Liferay Home:</b> <%= portletSessionClusterSampleData.getLiferayHome() %>
			</li>
		</ul>
	</c:when>
	<c:otherwise>

		<%
		portletSession.setAttribute(ClusterSampleData.class.getName(), clusterSampleData);
		%>

		<p>No session data exists, generating a new one with random string: <i><%= clusterSampleData.getData() %></i></p>
	</c:otherwise>
</c:choose>