package ${seleniumBuilderContext.getActionPackageName(actionName)};

import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;
import com.liferay.portalweb2.util.block.action.BaseAction;
import com.liferay.portalweb2.util.block.action.BaseLiferayAction;

<#if seleniumBuilderContext.getActionRootElement(actionName)??>
	<#assign rootElement = seleniumBuilderContext.getActionRootElement(actionName)>

	<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(rootElement, "function")>

	<#list childElementAttributeValues as childElementAttributeValue>
		import ${seleniumBuilderContext.getFunctionClassName(childElementAttributeValue)};
	</#list>
</#if>

public class ${seleniumBuilderContext.getActionSimpleClassName(actionName)} extends

<#if actionName = "BaseLiferay">
	BaseAction
<#else>
	BaseLiferayAction
</#if>

{

	public ${seleniumBuilderContext.getActionSimpleClassName(actionName)}(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);

		paths = ${seleniumBuilderContext.getPathSimpleClassName(actionName)}.getPaths();
	}

	<#if seleniumBuilderContext.getActionRootElement(actionName)??>
		<#assign rootElement = seleniumBuilderContext.getActionRootElement(actionName)>

		<#assign actionCommandElements = rootElement.elements("action-command")>

		<#list actionCommandElements as actionCommandElement>
			<#assign actionCommandName = actionCommandElement.attributeValue("name")>

			public ${seleniumBuilderContext.getFunctionReturnType(actionCommandName)} ${seleniumBuilderFileUtil.getVariableName(actionCommandName)}(

			<#list 1..seleniumBuilderContext.getFunctionLocatorCount(actionCommandName) as i>
				String locator${i}, String locatorKey${i}, String value${i}

				<#if i_has_next>
					,
				</#if>
			</#list>

			) throws Exception {
				<#list 1..seleniumBuilderContext.getFunctionLocatorCount(actionCommandName) as i>
					locator${i} = getLocator(locator${i}, locatorKey${i});
				</#list>

				<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(actionCommandElement, "function")>

				<#list childElementAttributeValues as childElementAttributeValue>
					${childElementAttributeValue}Function ${seleniumBuilderFileUtil.getVariableName(childElementAttributeValue)}Function = new ${childElementAttributeValue}Function(liferaySelenium);
				</#list>

				<#if actionCommandElement.element("case")??>
					<#assign caseElements = actionCommandElement.elements("case")>

					<#list caseElements as caseElement>
						if (false) {
							<#assign functionElement = caseElement.element("execute")>

							<#assign functionName = actionCommandName>

							<#include "function_element.ftl">

							;
						}

						<#if caseElement_has_next>
							else
						</#if>
					</#list>

					else {
						<#if actionCommandElement.element("default")??>
							<#assign defaultElement = actionCommandElement.element("default")>

							<#assign functionElement = defaultElement.element("execute")>

							<#assign functionName = actionCommandName>

							<#include "function_element.ftl">

							;
						<#else>
							super.${seleniumBuilderFileUtil.getVariableName(actionCommandName)}(

							<#list 1..seleniumBuilderContext.getFunctionLocatorCount(actionCommandName) as i>
								locator${i}, locatorKey${i}, value${i}

								<#if i_has_next>
									,
								</#if>
							</#list>

							);
						</#if>
					}
				<#elseif actionCommandElement.element("default")??>
					<#assign defaultElement = actionCommandElement.element("default")>

					<#assign functionElement = defaultElement.element("execute")>

					<#assign functionName = actionCommandName>

					<#include "function_element.ftl">

					;
				</#if>
			}
		</#list>
	</#if>

}