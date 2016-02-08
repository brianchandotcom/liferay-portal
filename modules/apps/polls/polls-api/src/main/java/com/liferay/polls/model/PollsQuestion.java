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

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the PollsQuestion service. Represents a row in the &quot;PollsQuestion&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see PollsQuestionModel
 * @see com.liferay.polls.model.impl.PollsQuestionImpl
 * @see com.liferay.polls.model.impl.PollsQuestionModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.polls.model.impl.PollsQuestionImpl")
@ProviderType
public interface PollsQuestion extends PollsQuestionModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.polls.model.impl.PollsQuestionImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<PollsQuestion, Long> QUESTION_ID_ACCESSOR = new Accessor<PollsQuestion, Long>() {
			@Override
			public Long get(PollsQuestion pollsQuestion) {
				return pollsQuestion.getQuestionId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<PollsQuestion> getTypeClass() {
				return PollsQuestion.class;
			}
		};

	public java.util.List<com.liferay.polls.model.PollsChoice> getChoices();

	public java.util.List<com.liferay.polls.model.PollsVote> getVotes();

	public java.util.List<com.liferay.polls.model.PollsVote> getVotes(
		int start, int end);

	public int getVotesCount();

	public boolean isExpired();

	public boolean isExpired(
		com.liferay.portal.kernel.service.ServiceContext serviceContext,
		java.util.Date defaultCreateDate);
}