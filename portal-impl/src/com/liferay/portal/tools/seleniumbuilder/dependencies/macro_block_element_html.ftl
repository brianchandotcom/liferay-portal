<#assign elements = macroBlockElement.elements()>

<#list elements as element>
	<#assign lineNumber = element.attributeValue("line-number")>

	<li id="${macroNameStack.peek()}Macro__${lineNumber}">
		<#if element.getName() == "echo">
			<#assign message = element.attributeValue("message")>

			<div>
				<span class="arrow">&lt;</span><span class="tag">echo</span>
				<span class="attribute">message</span><span class="arrow">=</span><span class="quote">&quot;${message}&quot;</span>
				<span class="arrow">/&gt;</span>
			</div>
		<#elseif element.getName() == "execute">
			<#if element.attributeValue("action")??>
				<#assign actionElement = element>

				<#include "action_element_html.ftl">
			<#elseif element.attributeValue("macro")??>
				<#assign macroElement = element>

				<#include "macro_element_html.ftl">
			</#if>
		<#elseif element.getName() == "fail">
			<#assign message = element.attributeValue("message")>

			<div>
				<span class="arrow">&lt;</span><span class="tag">fail</span>
				<span class="attribute">message</span><span class="arrow">=</span><span class="quote">&quot;${message}&quot;</span>
				<span class="arrow">/&gt;</span>
			</div>
		<#elseif element.getName() == "if">
			<div>
				<div id="ExpandToggle__${lineFolds}" class="expandToggle">+</div>
			</div>

			<div>
				<div class="expandLine">
					<span class="arrow">&lt;</span><span class="tag">if</span><span class="arrow">&gt;</span>
				</div>
			</div>

			<ul id="CollapseExpandToggle__${lineFolds}" class="collapse">
				<#assign lineFolds = lineFolds + 1>

				<#assign ifElement = element>

				<#include "macro_if_element_html.ftl">

				<#assign elseifElements = element.elements("elseif")>

				<#list elseifElements as elseifElement>
					<#assign lineNumber = elseifElement.attributeValue("line-number")>

					<li id="${macroNameStack.peek()}Macro__${lineNumber}">
						<div>
							<div id="ExpandToggle__${lineFolds}" class="expandToggle">-</div>
						</div>

						<div>
							<div class="expandLine">
								<span class="arrow">&lt;</span><span class="tag">elseif</span><span class="arrow">&gt;</span>
							</div>
						</div>

						<ul id="CollapseExpandToggle__${lineFolds}">
							<#assign lineFolds = lineFolds + 1>

							<#assign ifElement = elseifElement>

							<#include "macro_if_element_html.ftl">
						</ul>

						<div>
							<span class="arrow">&lt;/</span><span class="tag">elseif</span><span class="arrow">&gt;</span>
						</div>
					</li>
				</#list>

				<#if element.element("else")??>
					<#assign elseElement = element.element("else")>

					<#assign lineNumber = elseElement.attributeValue("line-number")>

					<li id="${macroNameStack.peek()}Macro__${lineNumber}">
						<div>
							<div id="ExpandToggle__${lineFolds}" class="expandToggle">+</div>
						</div>

						<div>
							<div class="expandLine">
								<span class="arrow">&lt;</span><span class="tag">else</span><span class="arrow">&gt;</span>
							</div>
						</div>

						<ul id="CollapseExpandToggle__${lineFolds}" class="collapse">
							<#assign lineFolds = lineFolds + 1>

							<#assign macroBlockElement = element.element("else")>

							<#include "macro_block_element_html.ftl">
						</ul>

						<div>
							<span class="arrow">&lt;/</span><span class="tag">else</span><span class="arrow">&gt;</span>
						</div>
					</li>
				</#if>
			</ul>

			<div>
				<span class="arrow">&lt;/</span><span class="tag">if</span><span class="arrow">&gt;</span>
			</div>
		<#elseif element.getName() == "var">
			<#assign varElement = element>

			<#assign varName = varElement.attributeValue("name")>
			<#assign varValue = varElement.attributeValue("value")>

			<div>
				<span class="arrow">&lt;</span><span class="tag">var</span>
				<span class="attribute">name</span><span class="arrow">=</span><span class="quote">&quot;${varName}&quot;</span>
				<span class="attribute">value</span><span class="arrow">=</span><span class="quote">&quot;${varValue}&quot;</span>
				<span class="arrow">/&gt;</span>
			</div>
		<#elseif element.getName() == "while">
			<div>
				<div id="ExpandToggle__${lineFolds}" class="expandToggle">+</div>
			</div>

			<div>
				<div class="expandLine">
					<span class="arrow">&lt;</span><span class="tag">while</span><span class="arrow">&gt;</span>
				</div>
			</div>

			<ul id="CollapseExpandToggle__${lineFolds}" class="collapse">
				<#assign lineFolds = lineFolds + 1>

				<#assign ifElement = element>

				<#include "macro_if_element_html.ftl">
			</ul>

			<div>
				<span class="arrow">&lt;/</span><span class="tag">while</span><span class="arrow">&gt;</span>
			</div>
		</#if>
	</li>
</#list>