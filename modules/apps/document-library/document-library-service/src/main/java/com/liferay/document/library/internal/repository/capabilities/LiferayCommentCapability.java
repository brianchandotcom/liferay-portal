/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.repository.capabilities;

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
 * @author Adolfo Pérez
 */
public class LiferayCommentCapability
	implements CommentCapability, RepositoryEventAware {

	@Override
	public void registerRepositoryEventListeners(
		RepositoryEventRegistry repositoryEventRegistry) {

		repositoryEventRegistry.registerRepositoryEventListener(
			RepositoryEventType.Delete.class, FileEntry.class,
			_REPOSITORY_EVENT_LISTENER);
	}

	private static final RepositoryEventListener
		<RepositoryEventType.Delete, FileEntry> _REPOSITORY_EVENT_LISTENER =
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