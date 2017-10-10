#parse( "definitions.vm" )

<%@ include file="/init.jsp" %>

<div id="<portlet:namespace />">{{text}}</div>

<aui:script require="${artifactId}@${packageJsonVersion}">
	${auiScriptRequireVarName}.default('<portlet:namespace />');
</aui:script>