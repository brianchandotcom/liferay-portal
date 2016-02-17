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

package com.liferay.polls.verify;

import com.liferay.polls.verify.model.PollsChoiceVerifiableModel;
import com.liferay.polls.verify.model.PollsQuestionVerifiableModel;
import com.liferay.polls.verify.model.PollsVoteVerifiableModel;
import com.liferay.portal.verify.VerifyAuditedModel;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.portal.verify.VerifyResourcePermissions;

import org.osgi.service.component.annotations.Component;

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
		verifyResourcedModels();
	}

	protected void verifyAuditedModels() throws Exception {
		_verifyAuditedModel.verify(new PollsChoiceVerifiableModel());
		_verifyAuditedModel.verify(new PollsVoteVerifiableModel());
	}

	protected void verifyResourcedModels() throws Exception {
		_verifyResourcePermissions.verify(new PollsQuestionVerifiableModel());
	}

	private final VerifyAuditedModel _verifyAuditedModel =
		new VerifyAuditedModel();
	private final VerifyResourcePermissions _verifyResourcePermissions =
		new VerifyResourcePermissions();

}