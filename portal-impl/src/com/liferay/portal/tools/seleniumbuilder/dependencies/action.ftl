package ${seleniumBuilderContext.getActionPackageName(actionName)};

import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;
import com.liferay.portalweb2.util.block.action.BaseAction;

public class ${seleniumBuilderContext.getActionSimpleClassName(actionName)} extends BaseAction {

	public ${seleniumBuilderContext.getActionSimpleClassName(actionName)}(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);

		paths = ${seleniumBuilderContext.getPathSimpleClassName(actionName)}.getPaths();
	}

	<#if seleniumBuilderContext.getActionRootElement(actionName)??>
		<#assign rootElement = seleniumBuilderContext.getActionRootElement(actionName)>

		<#assign functionSelectorElements = rootElement.elements("function-selector")>

		<#list functionSelectorElements as functionSelectorElement>
			<#assign functionName = functionSelectorElement.attributeValue("name")>

			public ${seleniumBuilderContext.getFunctionReturnType(functionName)} ${seleniumBuilderFileUtil.getVariableName(functionName)}(

			<#list 1..seleniumBuilderContext.getFunctionTargetCount(functionName) as i>
				String target${i}, String value${i}

				<#if i_has_next>
					,
				</#if>
			</#list>

			) throws Exception {
			}
		</#list>
	</#if>

}