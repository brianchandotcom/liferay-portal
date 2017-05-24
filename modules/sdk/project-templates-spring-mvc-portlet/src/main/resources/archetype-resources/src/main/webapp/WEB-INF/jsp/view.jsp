<%@ include file="/WEB-INF/jsp/init.jsp" %>

<strong><liferay-ui:message key="${artifactId}.caption" /></strong>

<portlet:renderURL var="updateURL">
	<portlet:param name="action" value="update" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:renderURL>

<aui:button href="<%= updateURL %>" value="update" />