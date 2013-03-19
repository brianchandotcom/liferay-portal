package ${seleniumBuilderContext.getMacroPackageName(macroName)};

import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;
import com.liferay.portalweb2.util.block.macro.BaseMacro;

public class ${seleniumBuilderContext.getMacroSimpleClassName(macroName)} extends BaseMacro {

	public ${seleniumBuilderContext.getMacroSimpleClassName(macroName)}(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
	}

	<#assign rootElement = seleniumBuilderContext.getMacroRootElement(macroName)>

	<#assign macroCommandElements = rootElement.elements("macro-command")>

	<#list macroCommandElements as macroCommandElement>
		<#assign macroCommandName = macroCommandElement.attributeValue("name")>

		public void ${macroCommandName}() throws Exception {
		}
	</#list>

}