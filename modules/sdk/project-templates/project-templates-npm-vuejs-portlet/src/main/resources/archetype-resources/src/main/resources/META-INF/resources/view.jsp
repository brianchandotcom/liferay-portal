<%@ include file="/init.jsp" %>

#parse ("definitions.vm")

<div id="<portlet:namespace />">{{text}}</div>

#if ($version == "7.1")
<aui:script require="<%= bootstrapRequire %>">
	bootstrapRequire.default('<portlet:namespace />');
#else
<aui:script require="${artifactId}@${packageJsonVersion}">
	${auiScriptRequireVarName}.default('<portlet:namespace />');
#end
</aui:script>