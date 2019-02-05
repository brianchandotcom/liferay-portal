package com.liferay.frontend.js.loader.modules.extender.internal.resolution;

import java.util.Collection;
import java.util.Map;

/**
 * Unifies config generator and NPMRegistry modules into a single entity to be
 * used in resolutions.
 * @author Rodolfo Roza Miranda
 * @review
 */
public interface ModuleDescriptor {
	public String getName();
	public Collection<String> getDependencies();
	public Map<String, String> getMappings();
	public String getPath();
}
