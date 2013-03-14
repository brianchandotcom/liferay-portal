package ${seleniumBuilderContext.getPathPackageName(pathName)};

import java.util.HashMap;
import java.util.Map;

public class ${seleniumBuilderContext.getPathSimpleClassName(pathName)} {

	public static Map<String, String> getPaths() {
		Map<String, String> paths = new HashMap<String, String>();

		paths.put("TOP", "relative=top");

		<#assign rootElement = seleniumBuilderContext.getPathRootElement(pathName)>

		<#assign bodyElement = rootElement.element("body")>

		<#assign tableElement = bodyElement.element("table")>

		<#assign tbodyElement = tableElement.element("tbody")>

		<#assign trElements = tbodyElement.elements("tr")>

		<#list trElements as trElement>
			<#assign tdElements = trElement.elements("td")>

			<#if tdElements[0].getText() != "">
				paths.put("${tdElements[0].getText()}", "${tdElements[1].getText()}");
			</#if>
		</#list>

		return paths;
	}

}