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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.backgroundtask.BackgroundTask;

import java.io.Serializable;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface IndexWriterHelper {

	public void addDocument(
			long companyId, Document document, boolean commitImmediately)
		throws SearchException;

	public void addDocuments(
			long companyId, Collection<Document> documents,
			boolean commitImmediately)
		throws SearchException;

	public void commit() throws SearchException;

	public void commit(long companyId) throws SearchException;

	public void deleteDocument(
			long companyId, String uid, boolean commitImmediately)
		throws SearchException;

	public void deleteDocuments(
			long companyId, Collection<String> uids, boolean commitImmediately)
		throws SearchException;

	public void deleteEntityDocuments(
			long companyId, String className, boolean commitImmediately)
		throws SearchException;

	public int getReindexTaskCount(long groupId, boolean completed)
		throws SearchException;

	public void indexKeyword(
			long companyId, String querySuggestion, float weight,
			String keywordType, Locale locale)
		throws SearchException;

	public void indexQuerySuggestionDictionaries(long companyId)
		throws SearchException;

	public void indexQuerySuggestionDictionary(long companyId, Locale locale)
		throws SearchException;

	public void indexSpellCheckerDictionaries(long companyId)
		throws SearchException;

	public void indexSpellCheckerDictionary(long companyId, Locale locale)
		throws SearchException;

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             com.liferay.portal.search.index.IndexStatusManager#isIndexReadOnly}
	 */
	@Deprecated
	public boolean isIndexReadOnly();

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             com.liferay.portal.search.index.IndexStatusManager#isIndexReadOnly(
	 *             String)}
	 */
	@Deprecated
	public boolean isIndexReadOnly(String className);

	public void partiallyUpdateDocument(
			long companyId, Document document, boolean commitImmediately)
		throws SearchException;

	public void partiallyUpdateDocuments(
			long companyId, Collection<Document> documents,
			boolean commitImmediately)
		throws SearchException;

	public BackgroundTask reindex(
			long userId, String jobName, long[] companyIds,
			Map<String, Serializable> taskContextMap)
		throws SearchException;

	public BackgroundTask reindex(
			long userId, String jobName, long[] companyIds, String className,
			Map<String, Serializable> taskContextMap)
		throws SearchException;

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             com.liferay.portal.search.index.IndexStatusManager#setIndexReadOnly(
	 *             boolean)}
	 */
	@Deprecated
	public void setIndexReadOnly(boolean indexReadOnly);

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             com.liferay.portal.search.index.IndexStatusManager#setIndexReadOnly(
	 *             String, boolean)}
	 */
	@Deprecated
	public void setIndexReadOnly(String className, boolean indexReadOnly);

	public void updateDocument(long companyId, Document document)
		throws SearchException;

	public void updateDocuments(
			long companyId, Collection<Document> documents,
			boolean commitImmediately)
		throws SearchException;

	public void updatePermissionFields(String name, String primKey);

}