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

import java.util.HashMap;
import java.util.Map;

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

		<#assign commandElements = rootElement.elements("command")>

		<#list commandElements as commandElement>
			<#assign commandName = commandElement.attributeValue("name")>

			<#assign functionName = seleniumBuilderFileUtil.getObjectName(commandName)>

			public ${seleniumBuilderContext.getFunctionReturnType(functionName)} ${commandName}(

			<#list 1..seleniumBuilderContext.getFunctionLocatorCount(functionName) as i>
				String locator${i}, String locatorKey${i}, String value${i}

				<#if i_has_next>
					,
				</#if>
			</#list>

			, Map<String,String> commandScopeVariables) throws Exception {
				<#list 1..seleniumBuilderContext.getFunctionLocatorCount(functionName) as i>
					locator${i} = getLocator(locator${i}, locatorKey${i}, commandScopeVariables);
				</#list>

				<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(commandElement, "function")>

				<#list childElementAttributeValues as childElementAttributeValue>
					${childElementAttributeValue}Function ${seleniumBuilderFileUtil.getVariableName(childElementAttributeValue)}Function = new ${childElementAttributeValue}Function(liferaySelenium);
				</#list>

				<#if commandElement.element("case")??>
					<#assign caseElements = commandElement.elements("case")>

					<#list caseElements as caseElement>
						if (
							<#if caseElement.attributes()?has_content>
								<#if caseElement.attributeValue("comparator")??>
									<#if caseElement.attributeValue("comparator") = "contains">
										<#assign caseComparator = "contains">
									<#elseif caseElement.attributeValue("comparator") = "endsWith">
										<#assign caseComparator = "endsWith">
									<#elseif caseElement.attributeValue("comparator") = "startsWith">
										<#assign caseComparator = "startsWith">
									<#else>
										<#assign caseComparator = "equals">
									</#if>
								<#else>
									<#assign caseComparator = "equals">
								</#if>

								<#if caseElement.attributeValue("locator1")??>
									<#assign caseLocator1 = caseElement.attributeValue("locator1")>

									locator1.${caseComparator}("${caseLocator1}")
								<#elseif caseElement.attributeValue("locator-key1")??>
									<#assign caseLocatorKey1 = caseElement.attributeValue("locator-key1")>

									locatorKey1.${caseComparator}("${caseLocatorKey1}")
								<#else>
									false
								</#if>
							<#else>
								false
							</#if>
						) {
							<#assign functionElement = caseElement.element("execute")>

							<#include "function_element.ftl">
						}

						<#if caseElement_has_next>
							else
						</#if>
					</#list>

					else {
						<#if commandElement.element("default")??>
							<#assign defaultElement = commandElement.element("default")>

							<#assign functionElement = defaultElement.element("execute")>

							<#include "function_element.ftl">
						<#else>
							<#if commandName?starts_with("is")>
								return
							</#if>

							super.${seleniumBuilderFileUtil.getVariableName(functionName)}(

							<#list 1..seleniumBuilderContext.getFunctionLocatorCount(functionName) as i>
								locator${i}, locatorKey${i}, value${i}, commandScopeVariables

								<#if i_has_next>
									,
								</#if>
							</#list>

							);
						</#if>
					}
				<#elseif commandElement.element("default")??>
					<#assign defaultElement = commandElement.element("default")>

					<#assign functionElement = defaultElement.element("execute")>

					<#include "function_element.ftl">
				</#if>
			}
		</#list>
	</#if>

}