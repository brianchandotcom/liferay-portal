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

package com.liferay.portal.verify.model;

/**
 * @author Miguel Pastor
 */
public class MBThreadVerifiableModel
	implements VerifiableAuditedModel, VerifiableUUIDModel {

	@Override
	public String getJoinByTableName() {
		return "rootMessageId";
	}

	@Override
	public String getPrimaryKeyColumnName() {
		return "threadId";
	}

	@Override
	public String getRelatedModelName() {
		return "MBMessage";
	}

	@Override
	public String getRelatedPKColumnName() {
		return "messageId";
	}

	@Override
	public String getTableName() {
		return "MBThread";
	}

	@Override
	public boolean isAnonymousUserAllowed() {
		return false;
	}

	@Override
	public boolean isUpdateDates() {
		return true;
	}

}