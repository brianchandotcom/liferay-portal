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

package com.liferay.portlet.messageboards.service.impl;

import com.liferay.message.boards.kernel.model.MBDiscussion;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portlet.messageboards.service.base.MBDiscussionLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 * @deprecated As of 7.0.0, replaced by {@link
 *             com.liferay.message.boards.service.impl.MBDiscussionLocalServiceImpl}
 */
@Deprecated
public class MBDiscussionLocalServiceImpl
	extends MBDiscussionLocalServiceBaseImpl {

	@Override
	public MBDiscussion addDiscussion(
			long userId, long groupId, long classNameId, long classPK,
			long threadId, ServiceContext serviceContext)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBDiscussionLocalServiceImpl");
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #addDiscussion(long, long,
	 *             long, long, long, ServiceContext)}
	 */
	@Deprecated
	@Override
	public MBDiscussion addDiscussion(
			long userId, long classNameId, long classPK, long threadId,
			ServiceContext serviceContext)
		throws PortalException {

		return mbDiscussionLocalService.addDiscussion(
			userId, serviceContext.getScopeGroupId(), classNameId, classPK,
			threadId, serviceContext);
	}

	@Override
	public MBDiscussion fetchDiscussion(long discussionId) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBDiscussionLocalServiceImpl");
	}

	@Override
	public MBDiscussion fetchDiscussion(String className, long classPK) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBDiscussionLocalServiceImpl");
	}

	@Override
	public MBDiscussion fetchThreadDiscussion(long threadId) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBDiscussionLocalServiceImpl");
	}

	@Override
	public MBDiscussion getDiscussion(long discussionId)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBDiscussionLocalServiceImpl");
	}

	@Override
	public MBDiscussion getDiscussion(String className, long classPK)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBDiscussionLocalServiceImpl");
	}

	@Override
	public MBDiscussion getThreadDiscussion(long threadId)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBDiscussionLocalServiceImpl");
	}

	@Override
	public void subscribeDiscussion(
			long userId, long groupId, String className, long classPK)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBDiscussionLocalServiceImpl");
	}

	@Override
	public void unsubscribeDiscussion(
			long userId, String className, long classPK)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBDiscussionLocalServiceImpl");
	}

}