/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.util;

import com.liferay.comment.configuration.CommentGroupServiceConfiguration;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.SubscriptionSender;

/**
 * @author Roberto Díaz
 */
public class MBDiscussionSubscriptionSender extends SubscriptionSender {

	public MBDiscussionSubscriptionSender() {
	}

	public MBDiscussionSubscriptionSender(
		CommentGroupServiceConfiguration commentGroupServiceConfiguration) {

		boolean discussionEmailCommentsAddedEnabled = false;

		if (commentGroupServiceConfiguration != null) {
			discussionEmailCommentsAddedEnabled =
				commentGroupServiceConfiguration.
					discussionEmailCommentsAddedEnabled();
		}

		_discussionEmailCommentsAddedEnabled =
			discussionEmailCommentsAddedEnabled;
	}

	@Override
	protected void sendEmailNotification(User user) throws Exception {
		if (_discussionEmailCommentsAddedEnabled) {
			super.sendEmailNotification(user);
		}
	}

	private boolean _discussionEmailCommentsAddedEnabled;

}