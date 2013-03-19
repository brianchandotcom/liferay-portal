package ${seleniumBuilderContext.getFunctionPackageName(functionName)};

import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

public class ${seleniumBuilderContext.getFunctionSimpleClassName(functionName)} extends BaseFunction {

	public ${seleniumBuilderContext.getFunctionSimpleClassName(functionName)}(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
	}

	<#assign rootElement = seleniumBuilderContext.getFunctionRootElement(functionName)>

	<#assign commandElements = rootElement.elements("command")>

	<#list commandElements as commandElement>
		public ${seleniumBuilderContext.getFunctionReturnType(functionName)} ${commandElement.attributeValue("name")}(

		<#list 1..seleniumBuilderContext.getFunctionLocatorCount(functionName) as i>
			String locator${i}, String value${i}

			<#if i_has_next>
				,
			</#if>
		</#list>

		) throws Exception {
			<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(commandElement, "function")>

			<#list childElementAttributeValues as childElementAttributeValue>
				${childElementAttributeValue}Function ${seleniumBuilderFileUtil.getVariableName(childElementAttributeValue)}Function = new ${childElementAttributeValue}Function(liferaySelenium);
			</#list>

			<#assign blockElement = commandElement>

			<#include "function_block_element.ftl">
		}
	</#list>

}