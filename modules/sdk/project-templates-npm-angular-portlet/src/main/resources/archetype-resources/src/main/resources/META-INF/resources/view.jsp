#parse( "definitions.vm" )

<%@ include file="/init.jsp" %>

<div id="<portlet:namespace />"></div>

<aui:script require="${artifactId}@${packageJsonVersion}">
	${auiScriptRequireVarName}.default('<portlet:namespace />');
</aui:script>