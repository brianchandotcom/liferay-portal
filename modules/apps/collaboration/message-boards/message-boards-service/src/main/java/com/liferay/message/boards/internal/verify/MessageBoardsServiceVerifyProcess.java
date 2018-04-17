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

package com.liferay.message.boards.internal.verify;

import com.liferay.message.boards.internal.verify.model.MBBanVerifiableModel;
import com.liferay.message.boards.internal.verify.model.MBDiscussionVerifiableModel;
import com.liferay.message.boards.internal.verify.model.MBThreadFlagVerifiableModel;
import com.liferay.message.boards.internal.verify.model.MBThreadVerifiableModel;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.verify.VerifyAuditedModel;
import com.liferay.portal.verify.VerifyGroupedModel;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.portal.verify.VerifyUUID;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Zsolt Berentey
 */
@Component(
	immediate = true,
	property = "verify.process.name=com.liferay.message.boards.service",
	service = VerifyProcess.class
)
public class MessageBoardsServiceVerifyProcess extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		verifyAuditedModels();
		verifyGroupedModels();
		verifyUUIDModels();
	}

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.message.boards.service)(release.schema.version=1.1.0))",
		unbind = "-"
	)
	protected void setRelease(Release release) {
	}

	protected void verifyAuditedModels() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			_verifyAuditedModel.verify(
				new MBDiscussionVerifiableModel(),
				new MBThreadVerifiableModel(),
				new MBThreadFlagVerifiableModel());
		}
	}

	protected void verifyGroupedModels() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			_verifyGroupedModel.verify(
				new MBDiscussionVerifiableModel(),
				new MBThreadFlagVerifiableModel());
		}
	}

	protected void verifyUUIDModels() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			VerifyUUID.verify(
				new MBBanVerifiableModel(), new MBDiscussionVerifiableModel(),
				new MBThreadVerifiableModel(),
				new MBThreadFlagVerifiableModel());
		}
	}

	private final VerifyAuditedModel _verifyAuditedModel =
		new VerifyAuditedModel();
	private final VerifyGroupedModel _verifyGroupedModel =
		new VerifyGroupedModel();

}