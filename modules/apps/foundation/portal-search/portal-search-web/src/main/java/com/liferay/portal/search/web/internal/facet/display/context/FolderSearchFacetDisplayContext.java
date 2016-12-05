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

package com.liferay.portal.search.web.internal.facet.display.context;

import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Lino Alves
 */
public class FolderSearchFacetDisplayContext {

	public FolderSearchFacetDisplayContext(
			Facet facet, String fieldParam, int frequencyThreshold,
			int maxTerms, boolean showFrequencies,
			FolderTitleLookup folderTitleLookup)
		throws SearchException {

		_facet = facet;
		_fieldParam = fieldParam;
		_frequencyThreshold = frequencyThreshold;
		_maxTerms = maxTerms;
		_showFrequencies = showFrequencies;
		_folderTitleLookup = folderTitleLookup;

		_folderId = getFolderId(fieldParam);

		_folderSearchFacetTermDisplayContexts = buildTermDisplayContexts();
	}

	public String getFieldParamInputName() {
		return _facet.getFieldId();
	}

	public String getFieldParamInputValue() {
		return _fieldParam;
	}

	public List<FolderSearchFacetTermDisplayContext> getTermDisplayContexts() {
		return _folderSearchFacetTermDisplayContexts;
	}

	public boolean isNothingSelected() {
		if (_folderId == null) {
			return true;
		}

		return false;
	}

	public boolean isRenderNothing() {
		if (!_folderSearchFacetTermDisplayContexts.isEmpty()) {
			return false;
		}

		return true;
	}

	protected List<FolderSearchFacetTermDisplayContext>
		buildTermDisplayContexts() throws SearchException {

		FacetCollector facetCollector = _facet.getFacetCollector();

		List<TermCollector> termCollectors = facetCollector.getTermCollectors();

		if (termCollectors.isEmpty()) {
			return getEmptySearchResultTermDisplayContexts();
		}

		List<FolderSearchFacetTermDisplayContext>
			folderSearchFacetTermDisplayContexts = new ArrayList<>(
				termCollectors.size());

		for (int i = 0; i < termCollectors.size(); i++) {
			if ((_maxTerms > 0) && (i >= _maxTerms)) {
				break;
			}

			TermCollector termCollector = termCollectors.get(i);

			if ((_frequencyThreshold > 0) &&
				(_frequencyThreshold > termCollector.getFrequency())) {

				break;
			}

			FolderSearchFacetTermDisplayContext
				folderSearchFacetTermDisplayContext = getTermDisplayContext(
					termCollector);

			if (folderSearchFacetTermDisplayContext != null) {
				folderSearchFacetTermDisplayContexts.add(
					folderSearchFacetTermDisplayContext);
			}
		}

		return folderSearchFacetTermDisplayContexts;
	}

	protected List<FolderSearchFacetTermDisplayContext>
		getEmptySearchResultTermDisplayContexts() throws SearchException {

		if (_folderId == null) {
			return Collections.emptyList();
		}

		String title = _folderTitleLookup.getFolderTitle(_folderId);

		if (title == null) {
			return Collections.emptyList();
		}

		FolderSearchFacetTermDisplayContext
			folderSearchFacetTermDisplayContext =
				new FolderSearchFacetTermDisplayContext(
					_folderId, title, true, 0, _showFrequencies);

		return Collections.singletonList(folderSearchFacetTermDisplayContext);
	}

	protected Long getFolderId(String fieldParam) {
		long folderId = GetterUtil.getLong(fieldParam);

		if (folderId == 0) {
			return null;
		}

		return folderId;
	}

	protected FolderSearchFacetTermDisplayContext getTermDisplayContext(
			TermCollector termCollector)
		throws SearchException {

		long curFolderId = GetterUtil.getLong(termCollector.getTerm());

		if (curFolderId == 0) {
			return null;
		}

		String title = _folderTitleLookup.getFolderTitle(curFolderId);

		if (title == null) {
			return null;
		}

		int frequency = termCollector.getFrequency();

		boolean selected = false;

		if ((_folderId != null) && _folderId.equals(curFolderId)) {
			selected = true;
		}

		return new FolderSearchFacetTermDisplayContext(
			curFolderId, title, selected, frequency, _showFrequencies);
	}

	private final Facet _facet;
	private final String _fieldParam;
	private final Long _folderId;
	private final List<FolderSearchFacetTermDisplayContext>
		_folderSearchFacetTermDisplayContexts;
	private final FolderTitleLookup _folderTitleLookup;
	private final int _frequencyThreshold;
	private final int _maxTerms;
	private final boolean _showFrequencies;

}