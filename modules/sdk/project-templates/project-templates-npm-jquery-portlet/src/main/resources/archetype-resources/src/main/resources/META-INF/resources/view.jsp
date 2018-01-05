#parse ("definitions.vm")
<%@ include file="/init.jsp" %>

<div id="<portlet:namespace />"></div>

#if (${liferayVersion} == "7.1")
<aui:script require="<%= bootstrapRequire %>">
	bootstrapRequire.default('<portlet:namespace />');
#else
<aui:script require="${artifactId}@${packageJsonVersion}">
	${auiScriptRequireVarName}.default('<portlet:namespace />');
#end
</aui:script>