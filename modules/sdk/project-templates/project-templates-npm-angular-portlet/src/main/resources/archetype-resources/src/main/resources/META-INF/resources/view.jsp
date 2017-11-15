<%@ include file="/init.jsp" %>

<div id="<portlet:namespace />"></div>

<aui:script require="<%= bootstrapRequire %>">
	// Pass the portlet's namespace to the Javascript bootstrap method so that
	// it can attach the boostrap Angular component to the above div tag.
	bootstrapRequire.default('#<portlet:namespace />');
</aui:script>