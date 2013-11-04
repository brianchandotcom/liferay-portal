<html>
	<#include "head_html.ftl">

	<body>
		<div id="title">
			<h2>${seleniumBuilderContext.getTestCaseClassName(testCaseName)}</h2>
		</div>

		<form>
			<input checked="checked" id="actionCommandLogButton" name="log" onchange="radioCheck()" type="radio">
				<label for="actionCommandLogButton">Action Command Log</label>
			</input>

			<input id="seleniumCommandLogButton" name="log" onchange="radioCheck()" type="radio">
				<label for="seleniumCommandLogButton">Selenium Command Log</label>
			</input>
		</form>

		<div id="actionCommandLog" style="display: block;">
		</div>

		<div id="seleniumCommandLog" style="display: none;">
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