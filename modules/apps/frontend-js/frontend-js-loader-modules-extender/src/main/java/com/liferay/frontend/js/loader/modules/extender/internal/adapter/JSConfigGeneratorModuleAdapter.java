package com.liferay.frontend.js.loader.modules.extender.internal.adapter;

import com.liferay.frontend.js.loader.modules.extender.internal.config.generator.JSConfigGeneratorModule;
import com.liferay.frontend.js.loader.modules.extender.internal.config.generator.JSConfigGeneratorPackage;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.util.Collection;
import java.util.Map;

/**
 * @author Rodolfo Roza Miranda
 */
public class JSConfigGeneratorModuleAdapter implements JSModuleAdapter {

	public JSConfigGeneratorModuleAdapter(
		JSConfigGeneratorModule jsConfigGeneratorModule) {

		_jsConfigGeneratorModule = jsConfigGeneratorModule;

		JSConfigGeneratorPackage jsConfigGeneratorPackage =
			_jsConfigGeneratorModule.getJSConfigGeneratorPackage();

		_alias = StringBundler.concat(
			jsConfigGeneratorPackage.getName(), StringPool.AT,
			jsConfigGeneratorPackage.getVersion(), StringPool.SLASH,
			_jsConfigGeneratorModule.getName());
	}

	@Override
	public String getAlias() {
		return _alias;
	}

	@Override
	public Collection<String> getDependencies() {
		return _jsConfigGeneratorModule.getDependencies();
	}

	@Override
	public Map<String, String> getMap() {
		return null;
	}

	@Override
	public String getPath() {
		return _jsConfigGeneratorModule.getResolvedURL();
	}

	private String _alias;
	private final JSConfigGeneratorModule _jsConfigGeneratorModule;

}
