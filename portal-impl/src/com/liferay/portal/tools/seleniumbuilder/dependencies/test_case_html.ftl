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
					<label for="descriptionLogButton" id="description">Description Log</label>
				</input>

				<input id="errorLogButton" name="log" onchange="radioCheck()" type="radio">
					<label for="errorLogButton">Error Log</label>
				</input>

				<input checked="checked" id="xmlLogButton" name="log" onchange="radioCheck()" type="radio">
					<label for="xmlLogButton">XML Log</label>
				</input>
			</form>
		</div>

		<div>
			<form>
				<input id="pauseButton" name="log" onchange="pauseButtonCheck()" type="checkbox">
					<label for="pauseButton" id="pause">&nbsp;&nbsp;Pause&nbsp;&nbsp;&nbsp;</label>
				</input>

				<input id="pauseErrorButton" name="log" onchange="pauseErrorButtonCheck()" type="checkbox">
					<label for="pauseErrorButton" id="pauseError">Enable Pause After Error&nbsp;</label>
				</input>
			</form>
		</div>

		<div id="actionCommandLog" style="display: none;">
		</div>

		<div id="descriptionLog" style="display: none;">
		</div>

		<div id="errorLog" style="display: none;">
			<p><b id="errorCount">0</b> total error(s).</p>

			<p id="errorList">
			</p>
		</div>

		<div id="pageObjectXMLLog" style="display: block;">
			<ul onclick="toggle(event);">
				<#assign lineFolds = 0>

				<#include "test_case_element_html.ftl">
			</ul>
		</div>
	</body>
</html>