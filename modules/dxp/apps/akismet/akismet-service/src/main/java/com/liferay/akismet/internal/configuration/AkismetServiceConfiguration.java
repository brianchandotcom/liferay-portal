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

package com.liferay.akismet.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Jamie Sammons
 */
@ExtendedObjectClassDefinition(category = "community-tools")
@Meta.OCD(
	id = "com.liferay.akismet.internal.configuration.AkismetServiceConfiguration",
	localization = "content/Language", name = "akismet-configuration-name"
)
public interface AkismetServiceConfiguration {

	@Meta.AD(name = "api-key", required = false)
	public String akismetApiKey();

	@Meta.AD(name = "enable-for-message-boards", required = false)
	public boolean messageBoardsEnabled();

	@Meta.AD(deflt = "30", name = "reportable-time", required = false)
	public int akismetReportableTime();

	@Meta.AD(deflt = "50", name = "check-threshold", required = false)
	public int akismetCheckThreshold();

	@Meta.AD(deflt = "30", name = "retain-spam-time", required = false)
	public int akismetRetainSpamTime();

}