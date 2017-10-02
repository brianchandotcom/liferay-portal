#parse( "definitions.vm" )

<%@ include file="/init.jsp" %>

<p>
	<b><liferay-ui:message key="${artifactId}.caption"/></b>
</p>

<aui:script require="${artifactId}@${packageJsonVersion}">
	${auiScriptRequireVarName}.default();
</aui:script>

<div id="${artifactId}-root">
</div>