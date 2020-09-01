/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.legacy.data.cleanup.internal.upgrade;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Preston Crary
 */
@ExtendedObjectClassDefinition(category = "upgrades")
@Meta.OCD(
	id = "com.liferay.legacy.data.cleanup.internal.upgrade.LegacyDataCleanupConfiguration",
	name = "legacy-data-cleanup-configuration-name"
)
public interface LegacyDataCleanupConfiguration {

	@Meta.AD(
		deflt = "false", name = "cleanup-chat-module-data", required = false
	)
	public boolean removeChatModuleData();

	@Meta.AD(
		deflt = "false", name = "cleanup-dictionary-module-data",
		required = false
	)
	public boolean removeDictionaryModuleData();

	@Meta.AD(
		deflt = "false", name = "cleanup-directory-module-data",
		required = false
	)
	public boolean removeDirectoryModuleData();

	@Meta.AD(
		deflt = "false", name = "cleanup-invitation-module-data",
		required = false
	)
	public boolean removeInvitationModuleData();

	@Meta.AD(
		deflt = "false", name = "cleanup-mail-reader-module-data",
		required = false
	)
	public boolean removeMailReaderModuleData();

	@Meta.AD(
		deflt = "false", name = "cleanup-private-messaging-module-data",
		required = false
	)
	public boolean removePrivateMessagingModuleData();

	@Meta.AD(
		deflt = "false", name = "cleanup-shopping-module-data", required = false
	)
	public boolean removeShoppingModuleData();

	@Meta.AD(
		deflt = "false", name = "cleanup-twitter-module-data", required = false
	)
	public boolean removeTwitterModuleData();

}