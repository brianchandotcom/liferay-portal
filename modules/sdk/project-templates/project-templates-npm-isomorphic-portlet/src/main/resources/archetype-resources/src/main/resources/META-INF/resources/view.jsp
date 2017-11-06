<%@ include file="/init.jsp" %>

<pre id="<portlet:namespace />">
</pre>

<aui:script require="<%= bootstrapRequire %>">
	var out = document.getElementById('<portlet:namespace />');

	out.innerHTML += 'Portlet main module loaded.\n';
	out.innerHTML += "Invoking portlet's main module default export.\n";
	out.innerHTML += '\n';

	bootstrapRequire.default({
		log: function(msg) {
			out.innerHTML += msg + '\n';
		}
	});
</aui:script>