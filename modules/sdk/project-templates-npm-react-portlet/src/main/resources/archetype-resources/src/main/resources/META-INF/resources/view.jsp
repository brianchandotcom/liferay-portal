#parse( "definitions.vm" )

<%@ include file="/init.jsp" %>

<aui:script require="${artifactId}@${pkgJsonVersion}">
	${auiScriptRequireVarName}.default('<portlet:namespace />-root');
</aui:script>

<div id="<portlet:namespace />-root"></div>