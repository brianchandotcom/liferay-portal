/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.model;

import com.liferay.portal.kernel.repository.model.FileEntry;

import java.io.Serializable;

import java.util.List;

/**
 * @author Jorge Ferrer
 */
public interface WikiPageDisplay extends Serializable {

	public List<FileEntry> getAttachmentsFileEntries();

	public String getContent();

	public String getFormat();

	public String getFormattedContent();

	public boolean getHead();

	public long getNodeId();

	public String getTitle();

	public long getUserId();

	public double getVersion();

	public boolean isHead();

	public void setAttachmentsFileEntries(
		List<FileEntry> attachmentsFileEntries);

	public void setContent(String content);

	public void setFormat(String format);

	public void setFormattedContent(String formattedContent);

	public void setHead(boolean head);

	public void setNodeId(long nodeId);

	public void setTitle(String title);

	public void setUserId(long userId);

	public void setVersion(double version);

}