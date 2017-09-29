#parse( "definitions.vm" )

<%@ include file="/init.jsp" %>

<aui:script require="${artifactId}@${packageJsonVersion}">
	${auiScriptRequireVarName}.default('<portlet:namespace />-root');
</aui:script>

<div id="<portlet:namespace />-root" style="cursor:pointer;"></div>