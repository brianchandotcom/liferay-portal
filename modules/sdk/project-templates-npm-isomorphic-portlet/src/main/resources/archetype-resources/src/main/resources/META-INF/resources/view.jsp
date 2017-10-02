#parse( "definitions.vm" )

<%@ include file="/init.jsp" %>

<pre id="<portlet:namespace />-output">
</pre>

<aui:script require="${artifactId}@${packageJsonVersion}">
	var out = document.getElementById('<portlet:namespace />-output');

	out.innerHTML += 'Portlet main module loaded.\n';
	out.innerHTML += "Invoking portlet's main module default export.\n";
	out.innerHTML += '\n';

	${auiScriptRequireVarName}.default({
			log: function(msg) {
			out.innerHTML += msg + '\n';
		}
	});
</aui:script>