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

package com.liferay.polls.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.polls.exception.QuestionChoiceException;
import com.liferay.polls.service.PollsVoteLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.kernel.util.Validator;

/**
 * The extended model interface for the PollsChoice service. Represents a row in the &quot;PollsChoice&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see PollsChoiceModel
 * @see PollsChoiceModelImpl
 * @generated
 */
@ProviderType
public class PollsChoice extends PollsChoiceModelImpl implements PollsChoiceModel {

	protected PollsQuestion _pollsQuestion;

	public PollsChoice() {
		setNew(true);
	}

	public PollsQuestion getPollsQuestion() {
		return _pollsQuestion;
	}

	public static final Accessor<PollsChoice, String> CHOICE_NAME_ACCESSOR = new Accessor<PollsChoice, String>() {

		@Override
		public String get(PollsChoice pollsChoice) {
			return pollsChoice.getName();
		}

		@Override
		public Class<String> getAttributeClass() {
			return String.class;
		}

		@Override
		public Class<PollsChoice> getTypeClass() {
			return PollsChoice.class;
		}
	};

	public int getVotesCount() {
		return PollsVoteLocalServiceUtil.getChoiceVotesCount(getChoiceId());
	}


	public void validate() throws PortalException {
		if (Validator.isNull(getName()) || Validator.isNull(getDescription())) {
			throw new QuestionChoiceException();
		}
	}



}