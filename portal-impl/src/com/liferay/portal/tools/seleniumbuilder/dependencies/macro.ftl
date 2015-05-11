package ${seleniumBuilderContext.getMacroPackageName(macroName)};

import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.MathUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;
import com.liferay.portalweb.util.RuntimeVariables;
import com.liferay.portalweb.util.TestPropsUtil;
import com.liferay.portalweb.util.TestPropsValues;
import com.liferay.portalweb.util.block.macro.BaseMacro;

<#assign rootElement = seleniumBuilderContext.getMacroRootElement(macroName)>

<#if rootElement.attributeValue("extends")??>
	<#assign extendsName = rootElement.attributeValue("extends")>

	import ${seleniumBuilderContext.getMacroClassName(extendsName)};
</#if>

<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(rootElement, "action")>

<#list childElementAttributeValues as childElementAttributeValue>
	import ${seleniumBuilderContext.getActionClassName(childElementAttributeValue)};
</#list>

<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(rootElement, "function")>

<#list childElementAttributeValues as childElementAttributeValue>
	import ${seleniumBuilderContext.getFunctionClassName(childElementAttributeValue)};
</#list>

<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(rootElement, "macro")>

<#list childElementAttributeValues as childElementAttributeValue>
	import ${seleniumBuilderContext.getMacroClassName(childElementAttributeValue)};
</#list>

<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(rootElement, "locator1")>

<#list childElementAttributeValues as childElementAttributeValue>
	import ${seleniumBuilderContext.getPathClassName(childElementAttributeValue)};
</#list>

<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(rootElement, "locator2")>

<#list childElementAttributeValues as childElementAttributeValue>
	import ${seleniumBuilderContext.getPathClassName(childElementAttributeValue)};
</#list>

import java.util.HashMap;
import java.util.Map;

public class ${seleniumBuilderContext.getMacroSimpleClassName(macroName)}

<#if extendsName??>
	extends ${extendsName}Macro {
<#else>
	extends BaseMacro {
</#if>

	public ${seleniumBuilderContext.getMacroSimpleClassName(macroName)}(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);

		<#if rootElement.element("var")??>
			<#assign varElements = rootElement.elements("var")>

			<#list varElements as varElement>
				<#assign lineNumber = varElement.attributeValue("line-number")>

				liferaySelenium.sendLogger("${macroName?uncap_first}Macro${lineNumber}", "pending");

				<#assign void = variableContextStack.push("definitionScopeVariables")>

				<#include "var_element.ftl">

				<#assign void = variableContextStack.pop()>

				liferaySelenium.sendLogger("${macroName?uncap_first}Macro${lineNumber}", "pass");
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

			<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(commandElement, "function")>

			<#list childElementAttributeValues as childElementAttributeValue>
				${childElementAttributeValue}Function ${seleniumBuilderFileUtil.getVariableName(childElementAttributeValue)}Function = new ${childElementAttributeValue}Function(liferaySelenium);
			</#list>

			<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(commandElement, "macro")>

			<#list childElementAttributeValues as childElementAttributeValue>
				${childElementAttributeValue}Macro ${seleniumBuilderFileUtil.getVariableName(childElementAttributeValue)}Macro = new ${childElementAttributeValue}Macro(liferaySelenium);
			</#list>

			<#assign blockElement = commandElement>

			<#assign blockLevel = "macro">

			<#assign void = variableContextStack.push("commandScopeVariables")>

			<#include "block_element.ftl">

			<#assign void = variableContextStack.pop()>
		}
	</#list>

	private int _whileCount;

}