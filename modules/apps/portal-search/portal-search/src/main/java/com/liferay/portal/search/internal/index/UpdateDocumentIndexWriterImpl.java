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

package com.liferay.portal.search.internal.index;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.search.configuration.IndexWriterHelperConfiguration;
import com.liferay.portal.search.index.IndexStatusManager;
import com.liferay.portal.search.index.UpdateDocumentIndexWriter;
import com.liferay.portal.search.permission.SearchPermissionDocumentContributor;

import java.util.Collection;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(
	configurationPid = "com.liferay.portal.search.configuration.IndexWriterHelperConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	service = UpdateDocumentIndexWriter.class
)
public class UpdateDocumentIndexWriterImpl
	implements UpdateDocumentIndexWriter {

	@Override
	public void updateDocument(long companyId, Document document) {
		if (indexStatusManager.isIndexReadOnly() || (document == null)) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Document " + document.toString());
		}

		SearchEngine searchEngine = searchEngineHelper.getSearchEngine();

		IndexWriter indexWriter = searchEngine.getIndexWriter();

		searchPermissionDocumentContributor.addPermissionFields(
			companyId, document);

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);

		_setCommitImmediately(searchContext, true);

		try {
			indexWriter.updateDocument(searchContext, document);
		}
		catch (SearchException searchException) {
			throw new SystemException(searchException);
		}
	}

	@Override
	public void updateDocumentPartially(
		long companyId, Document document, boolean commitImmediately) {

		if (indexStatusManager.isIndexReadOnly() || (document == null)) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Document " + document.toString());
		}

		SearchEngine searchEngine = searchEngineHelper.getSearchEngine();

		IndexWriter indexWriter = searchEngine.getIndexWriter();

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);

		_setCommitImmediately(searchContext, commitImmediately);

		try {
			indexWriter.partiallyUpdateDocument(searchContext, document);
		}
		catch (SearchException searchException) {
			throw new SystemException(searchException);
		}
	}

	@Override
	public void updateDocuments(
		long companyId, Collection<Document> documents,
		boolean commitImmediately) {

		if (indexStatusManager.isIndexReadOnly() || (documents == null) ||
			documents.isEmpty()) {

			return;
		}

		SearchEngine searchEngine = searchEngineHelper.getSearchEngine();

		IndexWriter indexWriter = searchEngine.getIndexWriter();

		for (Document document : documents) {
			if (_log.isDebugEnabled()) {
				_log.debug("Document " + document.toString());
			}

			searchPermissionDocumentContributor.addPermissionFields(
				companyId, document);
		}

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);

		_setCommitImmediately(searchContext, commitImmediately);

		try {
			indexWriter.updateDocuments(searchContext, documents);
		}
		catch (SearchException searchException) {
			throw new SystemException(searchException);
		}
	}

	@Override
	public void updateDocumentsPartially(
		long companyId, Collection<Document> documents,
		boolean commitImmediately) {

		if (indexStatusManager.isIndexReadOnly() || (documents == null) ||
			documents.isEmpty()) {

			return;
		}

		SearchEngine searchEngine = searchEngineHelper.getSearchEngine();

		IndexWriter indexWriter = searchEngine.getIndexWriter();

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);

		_setCommitImmediately(searchContext, commitImmediately);

		try {
			indexWriter.partiallyUpdateDocuments(searchContext, documents);
		}
		catch (SearchException searchException) {
			throw new SystemException(searchException);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		IndexWriterHelperConfiguration indexWriterHelperConfiguration =
			ConfigurableUtil.createConfigurable(
				IndexWriterHelperConfiguration.class, properties);

		_commitImmediately =
			indexWriterHelperConfiguration.indexCommitImmediately();
	}

	@Reference
	protected IndexStatusManager indexStatusManager;

	@Reference
	protected SearchEngineHelper searchEngineHelper;

	@Reference
	protected SearchPermissionDocumentContributor
		searchPermissionDocumentContributor;

	private void _setCommitImmediately(
		SearchContext searchContext, boolean commitImmediately) {

		if (!commitImmediately) {
			searchContext.setCommitImmediately(_commitImmediately);
		}
		else {
			searchContext.setCommitImmediately(true);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpdateDocumentIndexWriterImpl.class);

	private volatile boolean _commitImmediately;

}