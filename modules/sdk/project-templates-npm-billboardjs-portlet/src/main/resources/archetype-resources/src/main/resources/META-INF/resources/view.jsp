<%@ include file="/init.jsp" %>
#parse ("definitions.vm")

<!-- Temporary workaround to obtain the library stylesheets -->
<link href="/o/${artifactId}/node_modules/billboard.js@1.1.1/dist/billboard.css" rel="stylesheet">

<div id="<portlet:namespace />"></div>

<aui:script require="${artifactId}@${packageJsonVersion}">
	${auiScriptRequireVarName}.default('<portlet:namespace />');
</aui:script>