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

package com.liferay.polls.internal.verify;

import com.liferay.polls.internal.verify.model.PollsChoiceVerifiableModel;
import com.liferay.polls.internal.verify.model.PollsVoteVerifiableModel;
import com.liferay.polls.service.PollsChoiceLocalService;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.verify.VerifyGroupedModel;
import com.liferay.portal.verify.VerifyProcess;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author     Miguel Pastor
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 */
@Component(
	immediate = true,
	property = "verify.process.name=com.liferay.polls.service",
	service = VerifyProcess.class
)
@Deprecated
public class PollsServiceVerifyProcess extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		verifyGroupedModels();
	}

	@Reference(unbind = "-")
	protected void setPollsChoiceLocalService(
		PollsChoiceLocalService pollsChoiceLocalService) {
	}

	protected void verifyGroupedModels() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			_verifyGroupedModel.verify(new PollsChoiceVerifiableModel());
			_verifyGroupedModel.verify(new PollsVoteVerifiableModel());
		}
	}

	private final VerifyGroupedModel _verifyGroupedModel =
		new VerifyGroupedModel();

}