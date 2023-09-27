/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface KBArticleFinder {

	public int countByUrlTitle(
		long groupId, String kbFolderUrlTitle, String kbArticleUrlTitle,
		int[] status);

	public int filterCountByKeywords(long groupId, String keywords, int status);

	public java.util.List<com.liferay.knowledge.base.model.KBArticle>
		filterFindByKeywords(
			long groupId, String keywords, int status, int start, int end);

	public java.util.List<com.liferay.knowledge.base.model.KBArticle>
		findByUrlTitle(
			long groupId, String kbFolderUrlTitle, String kbArticleUrlTitle,
			int[] status, int start, int end);

}