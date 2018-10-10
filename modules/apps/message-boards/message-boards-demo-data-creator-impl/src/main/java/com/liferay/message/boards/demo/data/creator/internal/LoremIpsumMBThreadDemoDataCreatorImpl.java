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

package com.liferay.message.boards.demo.data.creator.internal;

import com.liferay.message.boards.demo.data.creator.MBMessageDemoDataCreator;
import com.liferay.message.boards.demo.data.creator.MBThreadDemoDataCreator;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.RandomUtil;

import java.io.IOException;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	property = "source=lorem-ipsum", service = MBThreadDemoDataCreator.class
)
public class LoremIpsumMBThreadDemoDataCreatorImpl
	implements MBThreadDemoDataCreator {

	@Override
	public MBThread create(List<Long> userIds, long categoryId)
		throws IOException, PortalException {

		int firstLevelReplyCount = RandomUtil.nextInt(10);

		if (firstLevelReplyCount <= 0) {
			return null;
		}

		long userId = userIds.get(RandomUtil.nextInt(userIds.size()));

		MBMessage rootMessage = _mbMessageDemoDataCreator.create(
			userId, categoryId);

		MBThread thread = null;

		for (int i = 0; i < firstLevelReplyCount; i++) {
			userId = userIds.get(RandomUtil.nextInt(userIds.size()));

			MBMessage firstLevelReplyMessage = _mbMessageDemoDataCreator.create(
				userId, categoryId, rootMessage.getMessageId());

			int secondLevelReplyCount = RandomUtil.nextInt(5);

			for (int j = 0; j < secondLevelReplyCount; j++) {
				userId = userIds.get(RandomUtil.nextInt(userIds.size()));

				MBMessage secondLevelReplyMessage =
					_mbMessageDemoDataCreator.create(
						userId, categoryId,
						firstLevelReplyMessage.getMessageId());

				int thirdLevelReplyCount = RandomUtil.nextInt(2);

				for (int k = 0; k < thirdLevelReplyCount; k++) {
					userId = userIds.get(RandomUtil.nextInt(userIds.size()));

					_mbMessageDemoDataCreator.create(
						userId, categoryId,
						secondLevelReplyMessage.getMessageId());
				}
			}

			if (thread == null) {
				thread = firstLevelReplyMessage.getThread();
			}
		}

		return thread;
	}

	@Override
	public void delete() throws PortalException {
	}

	@Reference(target = "(source=lorem-ipsum)", unbind = "-")
	protected void setLorenIpsumMBMessageDemoDataCreator(
		MBMessageDemoDataCreator mbMessageDemoDataCreator) {

		_mbMessageDemoDataCreator = mbMessageDemoDataCreator;
	}

	private MBMessageDemoDataCreator _mbMessageDemoDataCreator;

}