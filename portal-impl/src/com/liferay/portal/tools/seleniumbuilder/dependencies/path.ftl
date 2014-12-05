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

	public static String getPathDescription(String locatorKey) {
		if (_pathDescriptions.containsKey(locatorKey)) {
			return _pathDescriptions.get(locatorKey);
		}

		return locatorKey;
	}

	public static Map<String, String> getPathDescriptions() {
		Map<String, String> pathDescriptions = new HashMap<String, String>();

		pathDescriptions.putAll(_pathDescriptions);

		return pathDescriptions;
	}

	public static String getPathLocator(String locatorKey) {
		if (_pathLocators.containsKey(locatorKey)) {
			return _pathLocators.get(locatorKey);
		}

		return locatorKey;
	}

	public static Map<String, String> getPathLocators() {
		Map<String, String> pathLocators = new HashMap<String, String>();

		pathLocators.putAll(_pathLocators);

		return pathLocators;
	}

	private static Map<String, String> _pathDescriptions = new HashMap<String, String>();
	private static Map<String, String> _pathLocators = new HashMap<String, String>();

	static {
		_pathLocators.put("TOP", "relative=top");

		<#if extendedPath != "">
			_pathDescriptions.putAll(${seleniumBuilderContext.getPathSimpleClassName(extendedPath)}.getPathDescriptions());
			_pathLocators.putAll(${seleniumBuilderContext.getPathSimpleClassName(extendedPath)}.getPathLocators());
		</#if>

		<#list trElements as trElement>
			<#assign tdElements = trElement.elements("td")>

			<#if
				(tdElements[0].getText() != "") &&
				(tdElements[0].getText() != "EXTEND_ACTION_PATH")
			>
				<#if (tdElements[2].getText() != pathName) && (tdElements[2].getText() != "")>
					_pathDescriptions.put("${tdElements[0].getText()}", "${seleniumBuilderFileUtil.escapeJava(tdElements[2].getText())}");
				<#elseif tdElements[2].getText() = "">
					_pathDescriptions.put("${tdElements[0].getText()}", "${seleniumBuilderFileUtil.escapeJava(tdElements[0].getText())}");
				</#if>

				_pathLocators.put("${tdElements[0].getText()}", "${seleniumBuilderFileUtil.escapeJava(tdElements[1].getText())}");
			</#if>
		</#list>
	}

}