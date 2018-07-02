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

package com.liferay.portal.repository.capabilities;

import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.portal.kernel.comment.CommentManagerUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.capabilities.CommentCapability;
import com.liferay.portal.kernel.repository.event.RepositoryEventAware;
import com.liferay.portal.kernel.repository.event.RepositoryEventListener;
import com.liferay.portal.kernel.repository.event.RepositoryEventType;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.registry.RepositoryEventRegistry;

/**
 * @author     Adolfo Pérez
 * @deprecated As of Judson (7.1.x), replaced by {@link
 *             com.liferay.document.library.internal.capabilities.LiferayCommentCapability}
 */
@Deprecated
public class LiferayCommentCapability
	implements CommentCapability, RepositoryEventAware {

	@Override
	public void registerRepositoryEventListeners(
		RepositoryEventRegistry repositoryEventRegistry) {

		repositoryEventRegistry.registerRepositoryEventListener(
			RepositoryEventType.Delete.class, FileEntry.class,
			_COMMENT_DELETE_FILE_ENTRY_EVENT_LISTENER);
	}

	private static final RepositoryEventListener
		<RepositoryEventType.Delete, FileEntry>
			_COMMENT_DELETE_FILE_ENTRY_EVENT_LISTENER =
				new RepositoryEventListener
					<RepositoryEventType.Delete, FileEntry>() {

					@Override
					public void execute(FileEntry fileEntry)
						throws PortalException {

						CommentManagerUtil.deleteDiscussion(
							DLFileEntryConstants.getClassName(),
							fileEntry.getFileEntryId());
					}

				};

}