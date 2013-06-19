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

public class ${seleniumBuilderContext.getPathSimpleClassName(pathName)}

<#if extendedPath != "">
	extends ${seleniumBuilderContext.getPathSimpleClassName(extendedPath)}
</#if>

{

	public static Map<String, String> getPaths() {
		Map<String, String> paths = new HashMap<String, String>();

		paths.put("TOP", "relative=top");

		<#if extendedPath != "">
			paths.putAll(${seleniumBuilderContext.getPathSimpleClassName(extendedPath)}.getPaths());
		</#if>

		<#list trElements as trElement>
			<#assign tdElements = trElement.elements("td")>

			<#if tdElements[0].getText() != "" &&
				 tdElements[0].getText() != "EXTEND_ACTION_PATH">

				paths.put("${tdElements[0].getText()}", "${tdElements[1].getText()}");
			</#if>
		</#list>

		return paths;
	}

}