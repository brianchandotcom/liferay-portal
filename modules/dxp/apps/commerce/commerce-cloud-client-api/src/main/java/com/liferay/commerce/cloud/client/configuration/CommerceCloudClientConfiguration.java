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

package com.liferay.commerce.cloud.client.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.commerce.cloud.client.constants.CommerceCloudClientConstants;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Andrea Di Giorgi
 */
@ExtendedObjectClassDefinition(
	category = "commerce", scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	id = CommerceCloudClientConstants.CONFIGURATION_PID,
	localization = "content/Language",
	name = "commerce-cloud-client-configuration-name"
)
public interface CommerceCloudClientConfiguration {

	@Meta.AD(required = false)
	public boolean forecastingEnabled();

	@Meta.AD(deflt = "60", required = false)
	public int forecastingEntriesCheckInterval();

	@Meta.AD(deflt = "1", required = false)
	public int forecastingOrdersCheckInterval();

	@Meta.AD(deflt = "10", required = false)
	public int forecastingOrderStatus();

	@Meta.AD(required = false)
	public String projectId();

	@Meta.AD(required = false)
	public boolean pushSynchronizationEnabled();

	@Meta.AD(required = false)
	public String serverHost();

}