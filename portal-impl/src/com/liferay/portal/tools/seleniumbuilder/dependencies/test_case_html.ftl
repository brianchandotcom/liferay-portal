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

		<div class="options">
			<form>
				<input id="actionCommandLogButton" name="log" onchange="radioCheck()" type="radio">
					<label for="actionCommandLogButton">Action Command Log</label>
				</input>

				<input checked="checked" id="xmlLogButton" name="log" onchange="radioCheck()" type="radio">
					<label for="xmlLogButton">XML Log</label>
				</input>
			</form>
		</div>

		<div class="options">
			<form>
				<input id="enableActionScreenShotButton" name="log" onchange="radioCheck()" type="radio">
					<label for="enableActionScreenShotButton">Enable Screenshots </label>
				</input>

				<input checked="checked" id="disableActionScreenShotButton" name="log" onchange="radioCheck()" type="radio">
					<label for="disableActionScreenShotButton">Disable Screenshots </label>
				</input>
			</form>
		</div>

		<div>
			<form>
				<input id="pausePlayButton" name="log" onchange="pausePlayCheck()" type="checkbox">
					<label for="pausePlayButton" id="pausePlay">Playing </label>
				</input>
			</form>
		</div>

		<div id="actionCommandLog" style="display: none;">
		</div>

		<div id="pageObjectXMLLog" style="display: block;">
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
</html>