package ${seleniumBuilderContext.getActionPackageName(actionName)};

import com.liferay.portalweb.portal.util.block.action.BaseAction;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

import ${seleniumBuilderContext.getActionClassName("BaseLiferay")};

import org.apache.commons.lang3.StringEscapeUtils;

<#if seleniumBuilderContext.getActionRootElement(actionName)??>
	<#assign rootElement = seleniumBuilderContext.getActionRootElement(actionName)>

	<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(rootElement, "function")>

	<#list childElementAttributeValues as childElementAttributeValue>
		import ${seleniumBuilderContext.getFunctionClassName(childElementAttributeValue)};
	</#list>
</#if>

import java.util.Map;

<#assign rootElement = seleniumBuilderContext.getPathRootElement(actionName)>

<#assign bodyElement = rootElement.element("body")>

<#assign tableElement = bodyElement.element("table")>

<#assign tbodyElement = tableElement.element("tbody")>

<#assign trElements = tbodyElement.elements("tr")>

<#assign extendedPath = "">

<#list trElements as trElement>
	<#assign tdElements = trElement.elements("td")>

	<#if tdElements[0].getText() = "EXTEND_ACTION_PATH">
		<#assign extendedPath = tdElements[1].getText()>
			import ${seleniumBuilderContext.getActionClassName(extendedPath)};
		<#break>
	</#if>
</#list>

<#assign actionSimpleClassName = seleniumBuilderContext.getActionSimpleClassName(actionName)>

public class ${actionSimpleClassName} extends

<#if actionName = "BaseLiferay">
	BaseAction
<#elseif extendedPath != "">
	${extendedPath}Action
<#else>
	BaseLiferayAction
</#if>

{

	public ${seleniumBuilderContext.getActionSimpleClassName(actionName)}(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);

		pathDescriptions = ${seleniumBuilderContext.getPathSimpleClassName(actionName)}.getPathDescriptions();
		pathLocators = ${seleniumBuilderContext.getPathSimpleClassName(actionName)}.getPathLocators();
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

			, Map<String,String> environmentScopeVariables) throws Exception {
				<#list 1..seleniumBuilderContext.getFunctionLocatorCount(functionName) as i>
					locator${i} = getLocator(locator${i}, locatorKey${i}, environmentScopeVariables);
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
								<#elseif caseElement.attributeValue("value1")??>
									<#assign caseValue1 = caseElement.attributeValue("value1")>

									value1.${caseComparator}("${caseValue1}")
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
								locator${i}, locatorKey${i}, value${i}

								<#if i_has_next>
									,
								</#if>
							</#list>

							, environmentScopeVariables);
						</#if>
					}
				<#elseif commandElement.element("default")??>
					<#assign defaultElement = commandElement.element("default")>

					<#assign functionElement = defaultElement.element("execute")>

					<#include "function_element.ftl">
				</#if>
			}

			<#if actionName = "BaseLiferay">
				public void ${commandName}Description(
					<#list 1..seleniumBuilderContext.getFunctionLocatorCount(functionName) as i>
						String locator${i}, String locatorKey${i}, String value${i}

						<#if i_has_next>
							,
						</#if>
					</#list>

					, Map<String,String> environmentScopeVariables) throws Exception {

					<#list 1..seleniumBuilderContext.getFunctionLocatorCount(functionName) as i>
						locator${i} = getLocator(locator${i}, locatorKey${i}, environmentScopeVariables);

						value${i} = StringEscapeUtils.escapeHtml4(value${i});
					</#list>

					<#if commandElement.element("default")??>
						<#assign defaultElement = commandElement.element("default")>

						<#if defaultElement.element("description")??>
							<#assign descriptionElement = defaultElement.element("description")>

							<#assign message = descriptionElement.attributeValue("message")>

							String description = "${seleniumBuilderFileUtil.escapeJava(message)}";

							<#list 1..seleniumBuilderContext.getFunctionLocatorCount(functionName) as i>
								description = getDescription(description, "${i}", locator${i}, locatorKey${i}, value${i}, environmentScopeVariables);
							</#list>

							liferaySelenium.sendActionDescriptionLogger(description);
						</#if>
					</#if>
				}
			</#if>
		</#list>
	</#if>

}