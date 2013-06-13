<#assign testCaseRootElement = seleniumBuilderContext.getTestCaseRootElement(testCaseName)>

<#assign testCaseCommandElements = testCaseRootElement.elements("command")>

<#assign lineFolds = 0>

<ul onclick="takeAction(event);">
	<#list testCaseCommandElements as testCaseCommandElement>
		<#assign testCaseCommand = testCaseCommandElement.attributeValue("name")>

		<li id="${testCaseName}TestCase__${testCaseCommand}">
			<div>
				<div id="ExpandToggle__${lineFolds}" class="expandToggle">+</div>
			</div>

			<div>
				<div class="expandLine">
					<h3 class="testCaseCommand">${testCaseName}#${testCaseCommand}</h3>
				</div>
			</div>

			<ul id="CollapseExpandToggle__${lineFolds}" class="collapse">
				<#assign lineFolds = lineFolds + 1>

				<#assign testCaseVarElements = testCaseRootElement.elements("var")>

				<#list testCaseVarElements as testCaseVarElement>
					<#assign lineNumber = testCaseVarElement.attributeValue("line-number")>

					<li id="${testCaseName}TestCase__${lineNumber}">
						<#assign varName = testCaseVarElement.attributeValue("name")>
						<#assign varValue = testCaseVarElement.attributeValue("value")>

						<div>
							<span class="arrow">&lt;</span><span class="tag">var</span>
							<span class="attribute">name</span><span class="arrow">=</span><span class="quote">&quot;${varName}&quot;</span>
							<span class="attribute">value</span><span class="arrow">=</span><span class="quote">&quot;${varValue}&quot;</span>
							<span class="arrow">/&gt;</span>
						</div>
					</li>
				</#list>

				<#if testCaseRootElement.element("set-up")??>
					<#assign testCaseSetupElement = testCaseRootElement.element("set-up")>

					<#assign lineNumber = testCaseSetupElement.attributeValue("line-number")>

					<li id="${testCaseName}TestCase__${lineNumber}">
						<div>
							<div id="ExpandToggle__${lineFolds}" class="expandToggle">+</div>
						</div>

						<div>
							<div class="expandLine">
								<span class="arrow">&lt;</span><span class="tag">set-up</span><span class="arrow">&gt;</span>
							</div>
						</div>

						<#assign testCaseBlockElement = testCaseSetupElement>

						<#include "test_case_block_element_html.ftl">

						<div>
							<span class="arrow">&lt;/</span><span class="tag">set-up</span><span class="arrow">&gt;</span>
						</div>
					</li>
				</#if>

				<#assign lineNumber = testCaseCommandElement.attributeValue("line-number")>

				<li id="${testCaseName}TestCase__${lineNumber}">
					<div>
						<div id="ExpandToggle__${lineFolds}" class="expandToggle">+</div>
					</div>

					<div>
						<div class="expandLine">
							<span class="arrow">&lt;</span><span class="tag">command</span>
							<span class="attribute">name</span><span class="arrow">=</span><span class="quote">&quot;${testCaseCommandElement.attributeValue("name")}&quot;</span>
							<span class="arrow">&gt;</span>
						</div>
					</div>

					<#assign testCaseBlockElement = testCaseCommandElement>

					<#include "test_case_block_element_html.ftl">

					<div>
						<span class="arrow">&lt;/</span><span class="tag">command</span><span class="arrow">&gt;</span>
					</div>
				</li>

				<#if testCaseRootElement.element("tear-down")??>
					<#assign testCaseTearDownElement = testCaseRootElement.element("tear-down")>

					<#assign lineNumber = testCaseTearDownElement.attributeValue("line-number")>

					<li id="${testCaseName}TestCase__${lineNumber}">
						<div>
							<div id="ExpandToggle__${lineFolds}" class="expandToggle">+</div>
						</div>

						<div>
							<div class="expandLine">
								<span class="arrow">&lt;</span><span class="tag">tear-down</span><span class="arrow">&gt;</span>
							</div>
						</div>

						<#assign testCaseBlockElement = testCaseTearDownElement>

						<#include "test_case_block_element_html.ftl">

						<div>
							<span class="arrow">&lt;/</span><span class="tag">tear-down</span><span class="arrow">&gt;</span>
						</div>
					</li>
				</#if>
			</ul>
		</li>
	</#list>
</ul>