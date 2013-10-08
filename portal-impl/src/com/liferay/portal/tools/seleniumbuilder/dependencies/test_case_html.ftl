<html>
	<#include "head_html.ftl">

	<body>
		<div id="title">
			<h2>${seleniumBuilderContext.getTestCaseClassName(testCaseName)}</h2>
		</div>

		<div id="log">
		</div>
		<div id="log2">
		</div>

		<div id="code">
			<ul onclick="toggle(event);">
				<#assign lineFolds = 0>

				<#include "test_case_element_html.ftl">
			</ul>
			<div id="summary">
				<hr />

				<b id="errorCount">0</b> total error(s).

				<p id="errorList">
				</p>
			</div>
		</div>
	</body>
</html>