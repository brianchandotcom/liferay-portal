package com.liferay.frontend.js.loader.modules.extender.internal.resolution.descriptor;

import java.util.Collection;
import java.util.Map;

/**
 * @author Rodolfo Roza Miranda
 */
public interface JSModuleAdapter {
	public String getAlias();
	public Collection<String> getDependencies();
	public Map<String, String> getMap();
	public String getPath();
}
