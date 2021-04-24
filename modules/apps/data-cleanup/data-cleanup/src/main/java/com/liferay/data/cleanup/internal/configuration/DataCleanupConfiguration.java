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

package com.liferay.data.cleanup.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Preston Crary
 */
@ExtendedObjectClassDefinition(category = "upgrades")
@Meta.OCD(
	id = "com.liferay.data.cleanup.internal.configuration.DataCleanupConfiguration",
	name = "data-cleanup-configuration-name"
)
public interface DataCleanupConfiguration {

	@Meta.AD(
		deflt = "false", name = "clean-up-chat-module-data", required = false
	)
	public boolean cleanUpChatModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-dictionary-module-data",
		required = false
	)
	public boolean cleanUpDictionaryModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-directory-module-data",
		required = false
	)
	public boolean cleanUpDirectoryModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-hello-world-module-data",
		required = false
	)
	public boolean cleanUpHelloWorldModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-image-editor-module-data",
		required = false
	)
	public boolean cleanUpImageEditorModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-invitation-module-data",
		required = false
	)
	public boolean cleanUpInvitationModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-mail-reader-module-data",
		required = false
	)
	public boolean cleanUpMailReaderModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-private-messaging-module-data",
		required = false
	)
	public boolean cleanUpPrivateMessagingModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-shopping-module-data",
		required = false
	)
	public boolean cleanUpShoppingModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-twitter-module-data", required = false
	)
	public boolean cleanUpTwitterModuleData();

}