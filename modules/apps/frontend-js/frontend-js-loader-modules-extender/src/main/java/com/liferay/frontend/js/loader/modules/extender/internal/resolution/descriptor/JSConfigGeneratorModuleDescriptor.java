package com.liferay.frontend.js.loader.modules.extender.internal.resolution.descriptor;

import com.liferay.frontend.js.loader.modules.extender.internal.config.generator.JSConfigGeneratorModule;
import com.liferay.frontend.js.loader.modules.extender.internal.config.generator.JSConfigGeneratorPackage;
import com.liferay.frontend.js.loader.modules.extender.internal.resolution.ModuleDescriptor;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.util.Collection;
import java.util.Map;

/**
 * @author Rodolfo Roza Miranda
 */
public class JSConfigGeneratorModuleDescriptor implements ModuleDescriptor {

	public JSConfigGeneratorModuleDescriptor(
		JSConfigGeneratorModule jsConfigGeneratorModule) {

		_jsConfigGeneratorModule = jsConfigGeneratorModule;

		JSConfigGeneratorPackage jsConfigGeneratorPackage =
			_jsConfigGeneratorModule.getJSConfigGeneratorPackage();

		_name = StringBundler.concat(
			jsConfigGeneratorPackage.getName(), StringPool.AT,
			jsConfigGeneratorPackage.getVersion(), StringPool.SLASH,
			_jsConfigGeneratorModule.getName());
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public Collection<String> getDependencies() {
		return _jsConfigGeneratorModule.getDependencies();
	}

	@Override
	public Map<String, String> getMappings() {
		return null;
	}

	@Override
	public String getPath() {
		return _jsConfigGeneratorModule.getResolvedURL();
	}

	private String _name;
	private final JSConfigGeneratorModule _jsConfigGeneratorModule;

}
