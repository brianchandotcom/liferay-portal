package com.liferay.frontend.js.loader.modules.extender.internal.adapter;

import java.util.Map;
import java.util.Set;

/**
 * @author Rodolfo Roza Miranda
 */
public interface JSModuleAdapter {
	public String getAlias();
	public Set<String> getDependencies();
	public Map<String, String> getMap();
}
