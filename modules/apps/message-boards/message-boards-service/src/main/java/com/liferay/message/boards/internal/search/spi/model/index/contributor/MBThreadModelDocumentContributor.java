/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.search.spi.model.index.contributor;

import com.liferay.message.boards.model.MBDiscussion;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBDiscussionLocalService;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luan Maoski
 */
@Component(
	property = "indexer.class.name=com.liferay.message.boards.model.MBThread",
	service = ModelDocumentContributor.class
)
public class MBThreadModelDocumentContributor
	implements ModelDocumentContributor<MBThread> {

	@Override
	public void contribute(Document document, MBThread mbThread) {
		MBDiscussion discussion =
			mbDiscussionLocalService.fetchThreadDiscussion(
				mbThread.getThreadId());

		if (discussion == null) {
			document.addKeyword("discussion", false);
		}
		else {
			document.addKeyword("discussion", true);
		}

		document.addDate("lastPostDate", mbThread.getLastPostDate());
		document.addKeyword(
			"participantUserIds", mbThread.getParticipantUserIds());
	}

	@Reference
	protected MBDiscussionLocalService mbDiscussionLocalService;

}