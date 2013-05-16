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

import java.util.HashMap;
import java.util.Map;

public class ${seleniumBuilderContext.getMacroSimpleClassName(macroName)} extends BaseMacro {

	public ${seleniumBuilderContext.getMacroSimpleClassName(macroName)}(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);

		<#if rootElement.element("var")??>
			<#assign varElements = rootElement.elements("var")>

			<#list varElements as varElement>
				<#assign varName = varElement.attributeValue("name")>

				<#if varElement.attributeValue("value")??>
					<#assign varValue = varElement.attributeValue("value")>
				<#else>
					<#assign varValue = varElement.getText()>
				</#if>

				definitionScopeVariables.put("${varName}", "${varValue}");
			</#list>
		</#if>
	}

	<#assign commandElements = rootElement.elements("command")>

	<#list commandElements as commandElement>
		<#assign commandName = commandElement.attributeValue("name")>

		public
			<#if commandName?starts_with("is")>
				boolean
			<#else>
				void
			</#if>
		${commandName}(Map<String, String> environmentScopeVariables) throws Exception {
			commandScopeVariables = new HashMap<String, String>();

			commandScopeVariables.putAll(definitionScopeVariables);

			commandScopeVariables.putAll(environmentScopeVariables);

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