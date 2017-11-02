<%@ include file="/init.jsp" %>
#parse ("definitions.vm")

<div id="<portlet:namespace />"></div>

<aui:script require="${artifactId}@${packageJsonVersion}">
	${auiScriptRequireVarName}.default('<portlet:namespace />');
</aui:script>