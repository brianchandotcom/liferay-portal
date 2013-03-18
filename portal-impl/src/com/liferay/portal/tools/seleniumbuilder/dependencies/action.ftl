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
						if (
							<#if caseElement.attributes()?has_content>
								<#assign caseElementAttributes = caseElement.attributes()>

								<#list caseElementAttributes as caseElementAttribute>
									<#if caseElementAttribute.getQualifiedName() = "locator-contains">
										locator1.contains("${caseElementAttribute.getValue()}")
									<#elseif caseElementAttribute.getQualifiedName() = "locator-ends-with">
										locator1.endsWith("${caseElementAttribute.getValue()}")
									<#elseif caseElementAttribute.getQualifiedName() = "locator-equals">
										locator1.equals("${caseElementAttribute.getValue()}")
									<#elseif caseElementAttribute.getQualifiedName() = "locator-key-contains">
										locatorKey1.contains("${caseElementAttribute.getValue()}")
									<#elseif caseElementAttribute.getQualifiedName() = "locator-key-ends-with">
										locatorKey1.endsWith("${caseElementAttribute.getValue()}")
									<#elseif caseElementAttribute.getQualifiedName() = "locator-key-equals">
										locatorKey1.equals("${caseElementAttribute.getValue()}")
									<#elseif caseElementAttribute.getQualifiedName() = "locator-key-starts-with">
										locatorKey1.startsWith("${caseElementAttribute.getValue()}")
									<#elseif caseElementAttribute.getQualifiedName() = "locator-starts-with">
										locator1.startsWith("${caseElementAttribute.getValue()}")
									<#else>
										false
									</#if>

									<#if caseElementAttribute_has_next>
										&&
									</#if>
								</#list>
							<#else>
								false
							</#if>
						) {
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