#parse( "definitions.vm" )

<%@ include file="/init.jsp" %>

<div id="${artifactId}-root">
</div>

<aui:script require="${artifactId}@${packageJsonVersion}">
	${auiScriptRequireVarName}.default();
</aui:script>