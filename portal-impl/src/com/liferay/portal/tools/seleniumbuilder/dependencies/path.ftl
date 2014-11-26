package ${seleniumBuilderContext.getPathPackageName(pathName)};

import java.util.HashMap;
import java.util.Map;

<#assign rootElement = seleniumBuilderContext.getPathRootElement(pathName)>

<#assign bodyElement = rootElement.element("body")>

<#assign tableElement = bodyElement.element("table")>

<#assign tbodyElement = tableElement.element("tbody")>

<#assign trElements = tbodyElement.elements("tr")>

<#assign extendedPath = "">

<#list trElements as trElement>
	<#assign tdElements = trElement.elements("td")>

	<#if tdElements[0].getText() = "EXTEND_ACTION_PATH">
		<#assign extendedPath = tdElements[1].getText()>

		import ${seleniumBuilderContext.getPathClassName(extendedPath)};

		<#break>
	</#if>
</#list>

public class ${seleniumBuilderContext.getPathSimpleClassName(pathName)} {

	public static String getPathDescription(String locator) {
		<#list trElements as trElement>
			<#assign tdElements = trElement.elements("td")>

			<#if
				(tdElements[0].getText() != "") &&
				(tdElements[0].getText() != "EXTEND_ACTION_PATH")
				>
				if (locator.equals("${tdElements[0].getText()}")) {
					<#if tdElements[2].getText() = "">
						return "${seleniumBuilderFileUtil.escapeJava(tdElements[0].getText())}";
					<#elseif tdElements[2].getText() != pathName>
						return "${seleniumBuilderFileUtil.escapeJava(tdElements[2].getText())}";
					</#if>
				}
			</#if>
		</#list>

		return null;
	}

	public static Map<String, String> getPathDescriptions() {
		Map<String, String> pathDescriptions = new HashMap<String, String>();

		<#list trElements as trElement>
			<#assign tdElements = trElement.elements("td")>

			<#if
				(tdElements[0].getText() != "") &&
				(tdElements[0].getText() != "EXTEND_ACTION_PATH")
			>
				<#if (tdElements[2].getText() != pathName) && (tdElements[2].getText() != "")>
					pathDescriptions.put("${tdElements[0].getText()}", "${seleniumBuilderFileUtil.escapeJava(tdElements[2].getText())}");
				<#elseif tdElements[2].getText() = "">
					pathDescriptions.put("${tdElements[0].getText()}", "${seleniumBuilderFileUtil.escapeJava(tdElements[0].getText())}");
				</#if>
			</#if>
		</#list>

		return pathDescriptions;
	}

	public static String getPathLocator(String locator) {
		<#list trElements as trElement>
			<#assign tdElements = trElement.elements("td")>

			<#if
				(tdElements[0].getText() != "") &&
				(tdElements[0].getText() != "EXTEND_ACTION_PATH")
				>

				if (locator.equals("${tdElements[0].getText()}")) {
					return "${seleniumBuilderFileUtil.escapeJava(tdElements[1].getText())}";
				}
			</#if>
		</#list>

		return null;
	}

	public static Map<String, String> getPathLocators() {
		Map<String, String> pathLocators = new HashMap<String, String>();

		pathLocators.put("TOP", "relative=top");

		<#if extendedPath != "">
			pathLocators.putAll(${seleniumBuilderContext.getPathSimpleClassName(extendedPath)}.getPathLocators());
		</#if>

		<#list trElements as trElement>
			<#assign tdElements = trElement.elements("td")>

			<#if
				(tdElements[0].getText() != "") &&
				(tdElements[0].getText() != "EXTEND_ACTION_PATH")
			>
				pathLocators.put("${tdElements[0].getText()}", "${seleniumBuilderFileUtil.escapeJava(tdElements[1].getText())}");
			</#if>
		</#list>

		return pathLocators;
	}



}