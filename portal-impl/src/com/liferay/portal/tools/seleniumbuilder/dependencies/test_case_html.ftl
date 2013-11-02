<html>
	<#include "head_html.ftl">

	<body>
		<div id="title">
			<h2>${seleniumBuilderContext.getTestCaseClassName(testCaseName)}</h2>
		</div>

		<form>
			<input id="actionLog" type="radio" name="log" onchange="radioCheck()" checked>
				<label for="actionLog">Action Command Log</label>
			<input id="seleniumLog" type="radio" name="log" onchange="radioCheck()">
				<label for="seleniumLog">Selenium Command Log</label>
		</form>

		<div id="actionCommandLog" style="display:block">
		</div>

		<div id="seleniumCommandLog" style="display:none">
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