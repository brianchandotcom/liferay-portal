package ${seleniumBuilderContext.getFunctionPackageName(functionName)};

import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

public class ${seleniumBuilderContext.getFunctionSimpleClassName(functionName)} extends BaseFunction {

	public ${seleniumBuilderContext.getFunctionSimpleClassName(functionName)}(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
	}

	<#assign rootElement = seleniumBuilderContext.getFunctionRootElement(functionName)>

	<#assign functionCommandElements = rootElement.elements("function-command")>

	<#assign functionParameterString = seleniumBuilderContext.getFunctionParameterString(functionName)>

	<#list functionCommandElements as functionCommandElement>
		public ${seleniumBuilderContext.getFunctionReturnType(functionName)} ${functionCommandElement.attributeValue("name")}(${functionParameterString}) {
		}
	</#list>

}