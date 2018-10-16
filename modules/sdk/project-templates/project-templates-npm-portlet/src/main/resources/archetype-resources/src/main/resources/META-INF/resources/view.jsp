#parse ("definitions.vm")
<%@ include file="/init.jsp" %>

<pre id="<portlet:namespace />-output">
</pre>

<aui:script require="<%= mainRequire %>">
	window.out = document.getElementById('<portlet:namespace />-output');

	out.innerHTML += 'Portlet main module loaded.\n';
	out.innerHTML += "Invoking portlet's main module default export.\n";

	main.default();
</aui:script>