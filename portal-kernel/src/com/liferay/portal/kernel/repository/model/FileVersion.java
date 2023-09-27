/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.repository.model;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.InputStream;

import java.util.Date;

/**
 * @author Alexander Chow
 */
public interface FileVersion extends RepositoryModel<FileVersion> {

	public String getChangeLog();

	@Override
	public long getCompanyId();

	public InputStream getContentStream(boolean incrementCounter)
		throws PortalException;

	@Override
	public Date getCreateDate();

	public String getDescription();

	@Override
	public ExpandoBridge getExpandoBridge();

	public Date getExpirationDate();

	public String getExtension();

	public String getExtraSettings();

	public FileEntry getFileEntry() throws PortalException;

	public long getFileEntryId();

	public String getFileName();

	public long getFileVersionId();

	@Override
	public long getGroupId();

	public String getIcon();

	public String getMimeType();

	public long getRepositoryId();

	public Date getReviewDate();

	public long getSize();

	public int getStatus();

	public long getStatusByUserId();

	public String getStatusByUserName();

	public String getStatusByUserUuid();

	public Date getStatusDate();

	public String getTitle();

	@Override
	public long getUserId();

	@Override
	public String getUserName();

	@Override
	public String getUserUuid();

	@Override
	public String getUuid();

	public String getVersion();

	public boolean isApproved();

	public boolean isDefaultRepository();

	public boolean isDraft();

	public boolean isExpired();

	public boolean isPending();

}