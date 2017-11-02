<%@ include file="/init.jsp" %>
#parse ("definitions.vm")

<div id="<portlet:namespace />">
	Click me to open a superb modal dialog!
</div>

<aui:script require="${artifactId}@${packageJsonVersion}">
	${auiScriptRequireVarName}.default('<portlet:namespace />');
</aui:script>