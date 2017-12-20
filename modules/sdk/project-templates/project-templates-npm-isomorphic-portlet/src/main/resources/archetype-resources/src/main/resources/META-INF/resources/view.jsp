<%@ include file="/init.jsp" %>

#parse ("definitions.vm")

<pre id="<portlet:namespace />">
</pre>

#if ($version == "7.1")
<aui:script require="<%= bootstrapRequire %>">
#else
<aui:script require="${artifactId}@${packageJsonVersion}">
#end
	var out = document.getElementById('<portlet:namespace />');

	out.innerHTML += 'Portlet main module loaded.\n';
	out.innerHTML += "Invoking portlet's main module default export.\n";
	out.innerHTML += '\n';

#if ($version == "7.1")
	bootstrapRequire.default({
#else
	${auiScriptRequireVarName}.default({
#end
		log: function(msg) {
			out.innerHTML += msg + '\n';
		}
	});
</aui:script>