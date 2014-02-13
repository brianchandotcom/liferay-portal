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
					<label for="actionCommandLogButton">Command Log</label>
				</input>

				<input id="descriptionLogButton" name="log" onchange="radioCheck()" type="radio">
					<label for="descriptionLogButton" id="description">Description</label>
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
				<input id="errorLogSizeButton" name="log" onchange="errorLogSizeCheck()" type="checkbox">
					<label for="errorLogSizeButton" id="errorLogSize">&nbsp;Increase Error Log Size</label>
				</input>

				<input id="pauseButton" name="log" onchange="pauseButtonCheck()" type="checkbox">
					<label for="pauseButton" id="pause">&nbsp;&nbsp;Pause&nbsp;&nbsp;&nbsp;</label>
				</input>
			</form>
		</div>

		<div id="actionCommandLog" style="display: none;">
		</div>

		<div id="descriptionLog" style="display: none;">
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