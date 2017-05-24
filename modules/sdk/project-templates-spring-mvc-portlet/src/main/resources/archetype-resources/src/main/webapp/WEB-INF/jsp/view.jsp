
<%@ include file="/WEB-INF/jsp/init.jsp" %>

<strong><liferay-ui:message key="welcome-to-the-${artifactId}" /></strong>

<portlet:renderURL var="updateURL">
	<portlet:param name="action" value="update" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:renderURL>

<aui:button href="<%= updateURL %>" value="update" />

