/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Sergio González
 */
@ExtendedObjectClassDefinition(category = "message-boards")
@Meta.OCD(
	id = "com.liferay.message.boards.configuration.MBConfiguration",
	localization = "content/Language", name = "mb-configuration-name"
)
public interface MBConfiguration {

	/**
	 * Enter time in minutes on how often this job is run. If a user's ban is
	 * set to expire at 12:05 PM and the job runs at 2 PM, the expire will occur
	 * during the 2 PM run.
	 */
	@Meta.AD(deflt = "120", name = "expire-ban-job-interval", required = false)
	public int expireBanJobInterval();

}