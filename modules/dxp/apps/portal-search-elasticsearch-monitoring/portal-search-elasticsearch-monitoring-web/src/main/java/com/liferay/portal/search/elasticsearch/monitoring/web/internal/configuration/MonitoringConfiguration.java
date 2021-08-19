/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.elasticsearch.monitoring.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Miguel Angelo Caldas Gallindo
 * @author Artur Aquino
 * @author André de Oliveira
 * @author Bryan Engler
 */
@ExtendedObjectClassDefinition(category = "search")
@Meta.OCD(
	id = "com.liferay.portal.search.elasticsearch.monitoring.web.internal.configuration.MonitoringConfiguration",
	localization = "content/Language",
	name = "monitoring-configuration-name[elasticsearch]"
)
public interface MonitoringConfiguration {

	@Meta.AD(
		deflt = "http://localhost:5601", description = "kibana-url-help",
		name = "kibana-url", required = false
	)
	public String kibanaURL();

	@Meta.AD(
		description = "proxy-servlet-log-enable-help",
		name = "proxy-servlet-log-enable", required = false
	)
	public boolean proxyServletLogEnable();

	@Meta.AD(
		description = "kibana-password-help", name = "kibana-password",
		required = false, type = Meta.Type.Password
	)
	public String kibanaPassword();

	@Meta.AD(
		deflt = "elastic", description = "kibana-username-help",
		name = "kibana-username", required = false
	)
	public String kibanaUserName();

}