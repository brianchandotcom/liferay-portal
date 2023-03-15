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