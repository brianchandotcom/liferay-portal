<html>
	<#include "head_html.ftl">

	<body>
		<div id="title">
			<h2>
				${seleniumBuilderContext.getTestCaseClassName(testCaseName)}

				<#assign testCaseRootElement = seleniumBuilderContext.getTestCaseRootElement(testCaseName)>

				<#if testCaseRootElement.attributeValue("extends")??>
					<#assign extendedTestCase = testCaseRootElement.attributeValue("extends")>

					(extends ${extendedTestCase}TestCase)
				</#if>
			</h2>
		</div>

		<span class="spanFormat">
			<form>
				<input checked="checked" id="actionCommandLogButton" name="log" onchange="radioCheck()" type="radio">
					<label for="actionCommandLogButton">Action Command Log</label>

				<input id="xmlLogButton" name="log" onchange="radioCheck()" type="radio">
					<label for="xmlLogButton">XML Log</label>
			</form>
		</span>

		<span class="spanFormat">
			<form>
				<input id="actionScreenShotButton" name="log" onchange="radioCheck()" type="radio">
					<label for="actionScreenShotButton">Enable Screenshots </label>

				<input checked="checked" id="nothingButton" name="log" onchange="radioCheck()" type="radio">
					<label for="nothingButton">Disable Screenshots </label>

			</form>
		</span>


		<div id="actionCommandLog" style="display: block;">
		</div>

		<div id="pageObjectXMLLog" style="display: none;">
			<ul onclick="toggle(event);">
				<#assign lineFolds = 0>

				<#include "test_case_element_html.ftl">
			</ul>
		</div>

		<div id="actionScreenShotLog" style="display: none;">
		</div>

		<div id="errorLog">
			<p><b id="errorCount">0</b> total error(s).</p>

			<p id="errorList">
			</p>
		</div>
	</body>
	<#include "tail_html.ftl">
</html>