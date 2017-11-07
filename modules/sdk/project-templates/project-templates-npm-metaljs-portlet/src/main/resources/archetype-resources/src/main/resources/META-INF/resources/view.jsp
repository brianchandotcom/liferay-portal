<%@ include file="/init.jsp" %>

<div id="<portlet:namespace />"></div>

<aui:script require="<%= bootstrapRequire %>">
	bootstrapRequire.default('<portlet:namespace />');
</aui:script>