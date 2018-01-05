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
import com.liferay.polls.internal.verify.model.PollsQuestionVerifiableModel;
import com.liferay.polls.internal.verify.model.PollsVoteVerifiableModel;
import com.liferay.polls.service.PollsChoiceLocalService;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.verify.VerifyAuditedModel;
import com.liferay.portal.verify.VerifyGroupedModel;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.portal.verify.VerifyResourcePermissions;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Miguel Pastor
 */
@Component(
	immediate = true,
	property = {"verify.process.name=com.liferay.polls.service"},
	service = VerifyProcess.class
)
public class PollsServiceVerifyProcess extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		verifyAuditedModels();
		verifyGroupedModels();
		verifyResourcedModels();
	}

	@Reference(unbind = "-")
	protected void setPollsChoiceLocalService(
		PollsChoiceLocalService pollsChoiceLocalService) {
	}

	protected void verifyAuditedModels() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			_verifyAuditedModel.verify(new PollsChoiceVerifiableModel());
			_verifyAuditedModel.verify(new PollsVoteVerifiableModel());
		}
	}

	protected void verifyGroupedModels() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			_verifyGroupedModel.verify(new PollsChoiceVerifiableModel());
			_verifyGroupedModel.verify(new PollsVoteVerifiableModel());
		}
	}

	protected void verifyResourcedModels() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			_verifyResourcePermissions.verify(
				new PollsQuestionVerifiableModel());
		}
	}

	private final VerifyAuditedModel _verifyAuditedModel =
		new VerifyAuditedModel();
	private final VerifyGroupedModel _verifyGroupedModel =
		new VerifyGroupedModel();
	private final VerifyResourcePermissions _verifyResourcePermissions =
		new VerifyResourcePermissions();

}