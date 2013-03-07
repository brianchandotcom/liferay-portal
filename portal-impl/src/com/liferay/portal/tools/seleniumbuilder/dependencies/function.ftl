package ${seleniumBuilderContext.getFunctionPackageName(functionName)};

import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

public class ${seleniumBuilderContext.getFunctionSimpleClassName(functionName)} extends BaseFunction {

	public ${seleniumBuilderContext.getFunctionSimpleClassName(functionName)}(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
	}

	<#list functionCommands as functionCommand>
	public ${seleniumBuilderContext.getFunctionReturnType(functionName)} ${functionCommand.attributeValue("name")}() {
	}
	</#list>

}