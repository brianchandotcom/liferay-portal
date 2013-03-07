package ${seleniumBuilderContext.getMacroPackageName(macroName)};

import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;
import com.liferay.portalweb2.util.block.macro.BaseMacro;

public class ${seleniumBuilderContext.getMacroSimpleClassName(macroName)} extends BaseMacro {

	public ${seleniumBuilderContext.getMacroSimpleClassName(macroName)}(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
	}

}