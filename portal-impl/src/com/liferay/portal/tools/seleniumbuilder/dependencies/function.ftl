package ${seleniumBuilderContext.getFunctionPackageName(functionName)};

import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

public class ${seleniumBuilderContext.getFunctionSimpleClassName(functionName)} extends BaseFunction {

	public ${seleniumBuilderContext.getFunctionSimpleClassName(functionName)}(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
	}

	<#assign rootElement = seleniumBuilderContext.getFunctionRootElement(functionName)>

	<#assign functionCommandElements = rootElement.elements("function-command")>

	<#list functionCommandElements as functionCommandElement>
		public ${seleniumBuilderContext.getFunctionReturnType(functionName)} ${functionCommandElement.attributeValue("name")}(

		<#list 1..seleniumBuilderContext.getFunctionTargetCount(functionName) as i>
			String target${i}, String value${i}

			<#if i_has_next>
				,
			</#if>
		</#list>

		) throws Exception {
			<#assign childElementNames = seleniumBuilderFileUtil.getChildElementNames(functionCommandElement)>

			<#list childElementNames as childElementName>
				${childElementName}Function ${seleniumBuilderFileUtil.getVariableName(childElementName)}Function = new ${childElementName}Function(liferaySelenium);
			</#list>

			${functionConverter.convertBlockElement(functionCommandElement)}
		}
	</#list>

}