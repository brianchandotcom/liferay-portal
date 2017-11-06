<%@ include file="/init.jsp" %>

<div id="<portlet:namespace />">{{text}}</div>

<aui:script require="<%= bootstrapRequire %>">
	bootstrapRequire.default('<portlet:namespace />');
</aui:script>