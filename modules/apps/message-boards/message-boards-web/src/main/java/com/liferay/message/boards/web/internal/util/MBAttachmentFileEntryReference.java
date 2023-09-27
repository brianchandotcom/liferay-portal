/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.web.internal.util;

import com.liferay.portal.kernel.repository.model.FileEntry;

/**
 * @author Ambrín Chaudhary
 */
public class MBAttachmentFileEntryReference {

	public MBAttachmentFileEntryReference(
		long tempMBAttachmentFileEntryId, FileEntry mbAttachmentFileEntry) {

		_tempMBAttachmentFileEntryId = tempMBAttachmentFileEntryId;
		_mbAttachmentFileEntry = mbAttachmentFileEntry;
	}

	public FileEntry getMBAttachmentFileEntry() {
		return _mbAttachmentFileEntry;
	}

	public long getTempMBAttachmentFileEntryId() {
		return _tempMBAttachmentFileEntryId;
	}

	private final FileEntry _mbAttachmentFileEntry;
	private final long _tempMBAttachmentFileEntryId;

}