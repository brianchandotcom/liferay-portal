#parse( "definitions.vm" )

<%@ include file="/init.jsp" %>

<p>
	<b><liferay-ui:message key="${artifactId}.caption"/></b>
</p>

<aui:script require="${artifactId}@${packageJsonVersion}">
	window.out = document.getElementById('<portlet:namespace />-output');

	out.innerHTML += 'Portlet main module loaded.\n';
	out.innerHTML += "Invoking portlet's main module default export.\n";

	${auiScriptRequireVarName}.default();
</aui:script>

<pre id="<portlet:namespace />-output">
</pre>