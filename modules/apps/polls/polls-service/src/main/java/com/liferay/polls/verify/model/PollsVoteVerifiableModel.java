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

package com.liferay.polls.verify.model;

import com.liferay.portal.verify.model.VerifiableAuditedModel;
import com.liferay.portal.verify.model.VerifiableGroupedModel;
import com.liferay.portal.verify.model.VerifiableUUIDModel;

/**
 * @author Miguel Pastor
 */
public class PollsVoteVerifiableModel
	implements VerifiableAuditedModel, VerifiableGroupedModel,
			   VerifiableUUIDModel {

	@Override
	public String getJoinByTableName() {
		return "questionId";
	}

	@Override
	public String getPrimaryKeyColumnName() {
		return "voteId";
	}

	@Override
	public String getRelatedModelName() {
		return "PollsQuestion";
	}

	@Override
	public String getRelatedPKColumnName() {
		return "questionId";
	}

	@Override
	public String getRelatedPrimaryKeyColumnName() {
		return "questionId";
	}

	@Override
	public String getRelatedTableName() {
		return "PollsQuestion";
	}

	@Override
	public String getTableName() {
		return "PollsVote";
	}

	@Override
	public boolean isAnonymousUserAllowed() {
		return true;
	}

	@Override
	public boolean isUpdateDates() {
		return true;
	}

}