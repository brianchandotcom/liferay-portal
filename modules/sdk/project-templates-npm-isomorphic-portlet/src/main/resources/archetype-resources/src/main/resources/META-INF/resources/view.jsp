<%@ include file="/init.jsp" %>
#parse ("definitions.vm")

<pre id="<portlet:namespace />">
</pre>

<aui:script require="${artifactId}@${packageJsonVersion}">
	var out = document.getElementById('<portlet:namespace />');

	out.innerHTML += 'Portlet main module loaded.\n';
	out.innerHTML += "Invoking portlet's main module default export.\n";
	out.innerHTML += '\n';

	${auiScriptRequireVarName}.default({
			log: function(msg) {
			out.innerHTML += msg + '\n';
		}
	});
</aui:script>