package com.liferay.site.initializer.extender.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

@Meta.OCD(
	factory = true,
	id = "com.liferay.site.initializer.extender.internal.configuration.SiteInitializerConfiguration"
)
public interface SiteInitializerConfiguration {

	@Meta.AD
	public String groupName();

}