package com.liferay.frontend.js.loader.modules.extender.internal.adapter;


import com.liferay.frontend.js.loader.modules.extender.internal.JSLoaderModule;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author Rodolfo Roza Miranda
 */
public class JSLoaderModuleAdapter implements JSModuleAdapter {

	public JSLoaderModuleAdapter(JSLoaderModule module) {
		_module = module;

		_initialize();
	}

	@Override
	public String getAlias() {
		return _alias;
	}

	@Override
	public Set<String> getDependencies() {
		return _dependencies;
	}

	@Override
	public Map<String, String> getMap() {
		return null;
	}

	private void _initialize() {
		String unversionedConfiguration = _module.getUnversionedConfiguration();

		if (Validator.isNotNull(unversionedConfiguration)) {
			try {
				String jsonString = String.format("{%s}", unversionedConfiguration);

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(jsonString);

				Iterator<String> keys = jsonObject.keys();

				_alias = keys.next();

				JSONObject aliasConfig = jsonObject.getJSONObject(_alias);

				JSONArray dependencies = aliasConfig.getJSONArray("dependencies");

				dependencies.forEach(d -> _dependencies.add((String)d));
			}
			catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	private final JSLoaderModule _module;

	private String _alias = StringPool.BLANK;

	private Set<String> _dependencies = new HashSet<>();
}
