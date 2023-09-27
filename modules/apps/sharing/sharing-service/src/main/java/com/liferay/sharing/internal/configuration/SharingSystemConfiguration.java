/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Adolfo Pérez
 */
@ExtendedObjectClassDefinition(category = "sharing")
@Meta.OCD(
	id = "com.liferay.sharing.internal.configuration.SharingSystemConfiguration",
	localization = "content/Language", name = "sharing-configuration-name"
)
public interface SharingSystemConfiguration {

	/**
	 * Sets the interval in minutes of how often to check for expired sharing
	 * entries.
	 */
	@Meta.AD(
		deflt = "60",
		description = "expired-sharing-entries-check-interval-key-description",
		name = "expired-sharing-entries-check-interval", required = false
	)
	public int expiredSharingEntriesCheckInterval();

	/**
	 * Enables sharing.
	 *
	 * @review
	 */
	@Meta.AD(deflt = "true", name = "enabled", required = false)
	public boolean enabled();

}