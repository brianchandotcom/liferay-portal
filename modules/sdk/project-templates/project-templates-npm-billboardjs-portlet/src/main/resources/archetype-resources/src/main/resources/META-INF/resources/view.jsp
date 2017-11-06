<%@ include file="/init.jsp" %>

<link href="<%= stylesheetURL %>" rel="stylesheet">

<div id="<portlet:namespace />"></div>

<aui:script require="<%= bootstrapRequire %>">
	bootstrapRequire.default('<portlet:namespace />');
</aui:script>