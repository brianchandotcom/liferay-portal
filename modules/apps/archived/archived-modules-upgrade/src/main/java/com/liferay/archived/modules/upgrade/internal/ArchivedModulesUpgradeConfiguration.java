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

package com.liferay.archived.modules.upgrade.internal;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Preston Crary
 */
@ExtendedObjectClassDefinition(category = "upgrades")
@Meta.OCD(
	id = "com.liferay.archived.modules.upgrade.internal.ArchivedModulesUpgradeConfiguration",
	name = "deprecated-modules-upgrade-configuration-name"
)
public interface ArchivedModulesUpgradeConfiguration {

	@Meta.AD(
		deflt = "false", name = "remove-chat-module-data", required = false
	)
	public boolean removeChatModuleData();

	@Meta.AD(
		deflt = "false", name = "remove-invitation-module-data",
		required = false
	)
	public boolean removeInvitationModuleData();

	@Meta.AD(
		deflt = "false", name = "remove-mail-reader-module-data",
		required = false
	)
	public boolean removeMailReaderModuleData();

	@Meta.AD(
		deflt = "false", name = "remove-shopping-module-data", required = false
	)
	public boolean removeShoppingModuleData();

	@Meta.AD(
		deflt = "false", name = "remove-private-messaging-module-data",
		required = false
	)
	public boolean removePrivateMessagingModuleData();

	@Meta.AD(
		deflt = "false", name = "remove-twitter-module-data", required = false
	)
	public boolean removeTwitterModuleData();

}