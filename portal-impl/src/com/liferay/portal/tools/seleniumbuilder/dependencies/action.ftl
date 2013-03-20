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

		<#assign commandElements = rootElement.elements("command")>

		<#list commandElements as commandElement>
			<#assign commandName = commandElement.attributeValue("name")>

			public ${seleniumBuilderContext.getFunctionReturnType(commandName)} ${seleniumBuilderFileUtil.getVariableName(commandName)}(

			<#list 1..seleniumBuilderContext.getFunctionLocatorCount(commandName) as i>
				String locator${i}, String locatorKey${i}, String value${i}

				<#if i_has_next>
					,
				</#if>
			</#list>

			) throws Exception {
				<#list 1..seleniumBuilderContext.getFunctionLocatorCount(commandName) as i>
					locator${i} = getLocator(locator${i}, locatorKey${i});
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

								<#if caseElement.attributeValue("locator")??>
									<#assign caseLocator = caseElement.attributeValue("locator")>

									locator1.${caseComparator}("${caseLocator}")
								<#elseif caseElement.attributeValue("locator-key")??>
									<#assign caseLocatorKey = caseElement.attributeValue("locator-key")>

									locatorKey1.${caseComparator}("${caseLocatorKey}")
								<#else>
									false
								</#if>
							<#else>
								false
							</#if>
						) {
							<#assign functionElement = caseElement.element("execute")>

							<#assign functionName = commandName>

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

							<#assign functionName = commandName>

							<#include "function_element.ftl">
						<#else>
							super.${seleniumBuilderFileUtil.getVariableName(commandName)}(

							<#list 1..seleniumBuilderContext.getFunctionLocatorCount(commandName) as i>
								locator${i}, locatorKey${i}, value${i}

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

					<#assign functionName = commandName>

					<#include "function_element.ftl">
				</#if>
			}
		</#list>
	</#if>

}