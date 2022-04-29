package com.liferay.layout.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author dnebinger
 */
@ExtendedObjectClassDefinition(category = "pages")
@Meta.OCD(
	description = "layout-crawler-client-configuration-description",
	id = "com.liferay.layout.internal.configuration.LayoutCrawlerClientConfiguration",
	localization = "content/Language",
	name = "layout-crawler-client-configuration-name"
)
public interface LayoutCrawlerClientConfiguration {

	@Meta.AD(
		deflt = "false",
		description = "layout-crawler-client-configuration-enabled-description",
		name = "enabled", required = false
	)
	public boolean enabled();

	@Meta.AD(
		deflt = "localhost",
		description = "layout-crawler-client-configuration-hostname-description",
		name = "hostname", required = false
	)
	public String hostName();

	@Meta.AD(
		deflt = "8080",
		description = "layout-crawler-client-configuration-port-description",
		name = "port", required = false
	)
	public int port();

	@Meta.AD(
		deflt = "false",
		description = "layout-crawler-client-configuration-secure-description",
		name = "secure", required = false
	)
	public boolean secure();

}
