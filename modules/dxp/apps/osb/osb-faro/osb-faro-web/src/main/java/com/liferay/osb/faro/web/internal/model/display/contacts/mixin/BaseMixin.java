/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * @author Shinn Lok
 */
public abstract class BaseMixin {

	@JsonIgnore
	public abstract Date getDateModified();

	@JsonIgnore
	public abstract String getOwnerId();

	@JsonIgnore
	public abstract String getOwnerType();

	@JsonIgnore
	private String _ownerId;

	@JsonIgnore
	private String _ownerType;

}