/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.social.activity.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Adolfo Pérez
 */
@ExtendedObjectClassDefinition(category = "user-activity")
@Meta.OCD(
	id = "com.liferay.social.activity.internal.configuration.SocialActivitySystemConfiguration",
	localization = "content/Language",
	name = "social-activity-configuration-name"
)
public interface SocialActivitySystemConfiguration {

	@Meta.AD(
		deflt = "true", name = "enable-user-social-activity-tracking",
		required = false
	)
	public boolean enableUserSocialActivityTracking();

}