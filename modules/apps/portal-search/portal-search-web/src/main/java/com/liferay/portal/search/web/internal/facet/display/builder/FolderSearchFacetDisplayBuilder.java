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

package com.liferay.portal.search.web.internal.facet.display.builder;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.internal.facet.display.context.FolderSearchFacetDisplayContext;
import com.liferay.portal.search.web.internal.facet.display.context.FolderSearchFacetTermDisplayContext;
import com.liferay.portal.search.web.internal.facet.display.context.FolderTitleLookup;
import com.liferay.portal.search.web.internal.folder.facet.configuration.FolderFacetPortletInstanceConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.RenderRequest;

/**
 * @author Lino Alves
 */
public class FolderSearchFacetDisplayBuilder {

	public FolderSearchFacetDisplayBuilder(RenderRequest renderRequest)
		throws ConfigurationException {

		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		_folderFacetPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				FolderFacetPortletInstanceConfiguration.class);
	}

	public FolderSearchFacetDisplayContext build() {
		FolderSearchFacetDisplayContext folderSearchFacetDisplayContext =
			new FolderSearchFacetDisplayContext();

		List<FolderSearchFacetTermDisplayContext>
			folderSearchFacetTermDisplayContexts =
				buildFolderSearchFacetTermDisplayContexts();

		folderSearchFacetDisplayContext.setDisplayStyleGroupId(
			getDisplayStyleGroupId());
		folderSearchFacetDisplayContext.setFolderSearchFacetTermDisplayContexts(
			folderSearchFacetTermDisplayContexts);
		folderSearchFacetDisplayContext.
			setFolderFacetPortletInstanceConfiguration(
				_folderFacetPortletInstanceConfiguration);
		folderSearchFacetDisplayContext.setNothingSelected(isNothingSelected());
		folderSearchFacetDisplayContext.setParameterName(_parameterName);
		folderSearchFacetDisplayContext.setParameterValue(
			getFirstParameterValueString());
		folderSearchFacetDisplayContext.setParameterValues(
			getParameterValueStrings());
		folderSearchFacetDisplayContext.setRenderNothing(
			isRenderNothing(
				getTermCollectors(), folderSearchFacetTermDisplayContexts));

		return folderSearchFacetDisplayContext;
	}

	public void setFacet(Facet facet) {
		_facet = facet;
	}

	public void setFolderTitleLookup(FolderTitleLookup folderTitleLookup) {
		_folderTitleLookup = folderTitleLookup;
	}

	public void setFrequenciesVisible(boolean frequenciesVisible) {
		_frequenciesVisible = frequenciesVisible;
	}

	public void setFrequencyThreshold(int frequencyThreshold) {
		_frequencyThreshold = frequencyThreshold;
	}

	public void setMaxTerms(int maxTerms) {
		_maxTerms = maxTerms;
	}

	public void setParameterName(String parameterName) {
		_parameterName = parameterName;
	}

	public void setParameterValue(String parameterValue) {
		setParameterValues(GetterUtil.getString(parameterValue));
	}

	public void setParameterValues(String... parameterValues) {
		Objects.requireNonNull(parameterValues);

		_selectedFolderIds = Stream.of(
			parameterValues
		).map(
			GetterUtil::getLong
		).filter(
			folderId -> folderId > 0
		).collect(
			Collectors.toList()
		);
	}

	protected FolderSearchFacetTermDisplayContext
		buildFolderSearchFacetTermDisplayContext(
			long folderId, String displayName, int frequency,
			boolean selected) {

		FolderSearchFacetTermDisplayContext
			folderSearchFacetTermDisplayContext =
				new FolderSearchFacetTermDisplayContext();

		folderSearchFacetTermDisplayContext.setDisplayName(displayName);
		folderSearchFacetTermDisplayContext.setFolderId(folderId);
		folderSearchFacetTermDisplayContext.setFrequency(frequency);
		folderSearchFacetTermDisplayContext.setFrequencyVisible(
			_frequenciesVisible);
		folderSearchFacetTermDisplayContext.setSelected(selected);

		return folderSearchFacetTermDisplayContext;
	}

	protected FolderSearchFacetTermDisplayContext
		buildFolderSearchFacetTermDisplayContext(TermCollector termCollector) {

		long folderId = GetterUtil.getLong(termCollector.getTerm());

		String displayName = getDisplayName(folderId);

		if ((folderId == 0) || (displayName == null)) {
			return null;
		}

		return buildFolderSearchFacetTermDisplayContext(
			folderId, displayName, termCollector.getFrequency(),
			isSelected(folderId));
	}

	protected List<FolderSearchFacetTermDisplayContext>
		buildFolderSearchFacetTermDisplayContexts() {

		List<TermCollector> termCollectors = getTermCollectors();

		if (termCollectors.isEmpty()) {
			return getEmptyFolderSearchFacetTermDisplayContexts();
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
				folderSearchFacetTermDisplayContext =
					buildFolderSearchFacetTermDisplayContext(termCollector);

			if (folderSearchFacetTermDisplayContext != null) {
				folderSearchFacetTermDisplayContexts.add(
					folderSearchFacetTermDisplayContext);
			}
		}

		return folderSearchFacetTermDisplayContexts;
	}

	protected String getDisplayName(long folderId) {
		String title = _folderTitleLookup.getFolderTitle(folderId);

		if (Validator.isNotNull(title)) {
			return title;
		}

		return null;
	}

	protected long getDisplayStyleGroupId() {
		long displayStyleGroupId =
			_folderFacetPortletInstanceConfiguration.displayStyleGroupId();

		if (displayStyleGroupId <= 0) {
			displayStyleGroupId = _themeDisplay.getScopeGroupId();
		}

		return displayStyleGroupId;
	}

	protected FolderSearchFacetTermDisplayContext
		getEmptyFolderSearchFacetTermDisplayContext(long folderId) {

		return buildFolderSearchFacetTermDisplayContext(
			folderId, getDisplayName(folderId), 0, true);
	}

	protected List<FolderSearchFacetTermDisplayContext>
		getEmptyFolderSearchFacetTermDisplayContexts() {

		Stream<Long> folderIdsStream = _selectedFolderIds.stream();

		Stream<FolderSearchFacetTermDisplayContext>
			folderSearchFacetTermDisplayContextsStream = folderIdsStream.map(
				this::getEmptyFolderSearchFacetTermDisplayContext);

		return folderSearchFacetTermDisplayContextsStream.collect(
			Collectors.toList());
	}

	protected String getFirstParameterValueString() {
		if (_selectedFolderIds.isEmpty()) {
			return StringPool.BLANK;
		}

		return String.valueOf(_selectedFolderIds.get(0));
	}

	protected Long getFolderId(String fieldParam) {
		long folderId = GetterUtil.getLong(fieldParam);

		if (folderId == 0) {
			return null;
		}

		return folderId;
	}

	protected List<String> getParameterValueStrings() {
		Stream<Long> groupIdsStream = _selectedFolderIds.stream();

		Stream<String> parameterValuesStream = groupIdsStream.map(
			String::valueOf);

		return parameterValuesStream.collect(Collectors.toList());
	}

	protected List<TermCollector> getTermCollectors() {
		if (_facet == null) {
			return Collections.emptyList();
		}

		FacetCollector facetCollector = _facet.getFacetCollector();

		if (facetCollector == null) {
			return Collections.emptyList();
		}

		return facetCollector.getTermCollectors();
	}

	protected boolean isNothingSelected() {
		if (_selectedFolderIds.isEmpty()) {
			return true;
		}

		return false;
	}

	protected boolean isRenderNothing(
		List<TermCollector> termCollectors,
		List<FolderSearchFacetTermDisplayContext> termDisplayContexts) {

		if (isNothingSelected() && ListUtil.isEmpty(termCollectors)) {
			return true;
		}

		if (ListUtil.isEmpty(termDisplayContexts)) {
			return true;
		}

		return false;
	}

	protected boolean isSelected(long folderId) {
		if (_selectedFolderIds.contains(folderId)) {
			return true;
		}

		return false;
	}

	private Facet _facet;
	private final FolderFacetPortletInstanceConfiguration
		_folderFacetPortletInstanceConfiguration;
	private FolderTitleLookup _folderTitleLookup;
	private boolean _frequenciesVisible;
	private int _frequencyThreshold;
	private int _maxTerms;
	private String _parameterName;
	private List<Long> _selectedFolderIds = Collections.emptyList();
	private final ThemeDisplay _themeDisplay;

}