package com.liferay.frontend.taglib.clay.sample.web.internal.attribute.provider;

import com.liferay.frontend.taglib.clay.attribute.provider.ClayComponentAttributeProvider;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rodolfo Roza Miranda
 */
@Component(
	immediate = true,
	property = "clay.component.attribute.provider.key=SampleNavigationBarAttributeProvider",
	service = ClayComponentAttributeProvider.class
)
public class SampleNavigationBarAttributeProvider
	implements ClayComponentAttributeProvider {

	@Override
	public Map<String, Object> getAttributes() {
		HashMap<String, Object> attributes = new HashMap<>();

		attributes.put("inverted", false);

		return attributes;
	}

}