#parse ("definitions.vm")
<%@ include file="/init.jsp" %>

<button id="<portlet:namespace />-button">
	Click me to open a superb modal dialog!
</button>

<aui:script require="<%= mainRequire %>">
	main.default('<portlet:namespace />-button');
</aui:script>