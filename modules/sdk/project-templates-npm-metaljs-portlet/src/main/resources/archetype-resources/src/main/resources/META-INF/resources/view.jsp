#parse( "definitions.vm" )

<%@ include file="/init.jsp" %>

<aui:script require="${artifactId}@${pkgJsonVersion}">
	${auiScriptRequireVarName}.default('<portlet:namespace />-button');
</aui:script>

<button id="<portlet:namespace />-button">
	Click me to open a superb modal dialog!
</button>