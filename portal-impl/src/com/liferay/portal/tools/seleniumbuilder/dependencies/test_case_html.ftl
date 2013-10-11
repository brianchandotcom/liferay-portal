<html>
	<#include "head_html.ftl">

	<body>
		<div id="title">
			<h2>${seleniumBuilderContext.getTestCaseClassName(testCaseName)}</h2>
		</div>

		<div id="actionCommandLog">
		</div>

		<div id="seleniumCommandLog">
		</div>

		<div id="errorLog">
			<p><b id="errorCount">0</b> total error(s).</p>

			<p id="errorList">
			</p>
		</div>

		<div id="pageObjectXMLLog">
			<ul onclick="toggle(event);">
				<#assign lineFolds = 0>

				<#include "test_case_element_html.ftl">
			</ul>
		</div>
	</body>
</html>