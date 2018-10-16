#parse ("definitions.vm")
<%@ include file="/init.jsp" %>

<div id="<portlet:namespace />"></div>

<aui:script require="<%= mainRequire %>">
	// Pass the portlet's namespace to the Javascript bootstrap method so that
	// it can attach the boostrap Angular component to the above div tag.
	main.default('#<portlet:namespace />');
</aui:script>