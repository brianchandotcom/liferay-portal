package ${seleniumBuilderContext.getMacroPackageName(macroName)};

import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;
import com.liferay.portalweb2.util.block.macro.BaseMacro;

<#assign rootElement = seleniumBuilderContext.getMacroRootElement(macroName)>

<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(rootElement, "action")>

<#list childElementAttributeValues as childElementAttributeValue>
	import ${seleniumBuilderContext.getActionClassName(childElementAttributeValue)};
</#list>

<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(rootElement, "macro")>

<#list childElementAttributeValues as childElementAttributeValue>
	import ${seleniumBuilderContext.getMacroClassName(childElementAttributeValue)};
</#list>

public class ${seleniumBuilderContext.getMacroSimpleClassName(macroName)} extends BaseMacro {

	public ${seleniumBuilderContext.getMacroSimpleClassName(macroName)}(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
	}

	<#assign commandElements = rootElement.elements("command")>

	<#list commandElements as commandElement>
		<#assign commandName = commandElement.attributeValue("name")>

		public void ${commandName}() throws Exception {
			<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(commandElement, "action")>

			<#list childElementAttributeValues as childElementAttributeValue>
				${childElementAttributeValue}Action ${seleniumBuilderFileUtil.getVariableName(childElementAttributeValue)}Action = new ${childElementAttributeValue}Action(liferaySelenium);
			</#list>

			<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(commandElement, "macro")>

			<#list childElementAttributeValues as childElementAttributeValue>
				${childElementAttributeValue}Macro ${seleniumBuilderFileUtil.getVariableName(childElementAttributeValue)}Macro = new ${childElementAttributeValue}Macro(liferaySelenium);
			</#list>

			<#assign blockElement = commandElement>

			<#include "macro_block_element.ftl">
		}
	</#list>

}