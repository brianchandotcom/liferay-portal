package com.liferay.frontend.taglib.clay.internal;

import com.liferay.frontend.taglib.clay.attribute.provider.ClayComponentAttributeProviderRegistry;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodolfo Roza Miranda
 */
@Component(immediate = true, service = {})
public class ClayComponentAttributeProviderRegistryHelper {

	public ClayComponentAttributeProviderRegistryHelper() {
		_helper = this;
	}

	public static ClayComponentAttributeProviderRegistry getRegistry() {
		if (_helper == null) {
			return null;
		}

		return _helper._registry;
	}

	private static ClayComponentAttributeProviderRegistryHelper _helper;

	@Reference
	private ClayComponentAttributeProviderRegistry _registry;
}
