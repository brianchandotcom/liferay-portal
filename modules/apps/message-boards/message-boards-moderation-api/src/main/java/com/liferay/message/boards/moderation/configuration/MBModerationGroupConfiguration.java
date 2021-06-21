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

package com.liferay.message.boards.moderation.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Eduardo García
 */
@ExtendedObjectClassDefinition(
	category = "message-boards",
	scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.message.boards.moderation.configuration.MBModerationGroupConfiguration",
	localization = "content/Language",
	name = "message-boards-moderation-workflow-group-configuration-name"
)
public interface MBModerationGroupConfiguration {

	@Meta.AD(
		deflt = "false", description = "enable-message-boards-moderation-help",
		name = "enable-message-boards-moderation", required = false
	)
	public boolean enableMessageBoardsModeration();

	@Meta.AD(
		deflt = "1", description = "minimum-contributed-messages-help",
		min = "1", name = "minimum-contributed-messages", required = false
	)
	public int minimumContributedMessages();

}