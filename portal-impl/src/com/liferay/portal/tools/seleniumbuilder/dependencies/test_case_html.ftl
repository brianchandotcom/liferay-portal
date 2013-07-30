<html>
	<#include "head_html.ftl">

	<body>
		<h2>${seleniumBuilderContext.getTestCaseClassName(testCaseName)}</h2>

		<div id="log">
		</div>

		<ul onclick="toggle(event);">
			<#assign lineFolds = 0>

			<#include "test_case_element_html.ftl">
		</ul>
	</body>
</html>