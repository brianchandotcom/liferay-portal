/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.internal.finder;

import com.liferay.adaptive.media.AMAttribute;
import com.liferay.adaptive.media.AMDistanceComparator;
import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.finder.AMQuery;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.finder.AMImageQueryBuilder;
import com.liferay.adaptive.media.image.internal.util.comparator.AMAttributeDistanceComparator;
import com.liferay.adaptive.media.image.internal.util.comparator.AMPropertyDistanceComparator;
import com.liferay.adaptive.media.processor.AMProcessor;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.Validator;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * @author Adolfo Pérez
 */
public class AMImageQueryBuilderImpl
	implements AMImageQueryBuilder, AMImageQueryBuilder.ConfigurationStep,
			   AMImageQueryBuilder.FuzzySortStep,
			   AMImageQueryBuilder.InitialStep,
			   AMImageQueryBuilder.StrictSortStep {

	public static final AMQuery<FileVersion, AMProcessor<FileVersion>>
		AM_QUERY = new AMQuery<FileVersion, AMProcessor<FileVersion>>() {
		};

	@Override
	public AMQuery<FileVersion, AMProcessor<FileVersion>> done() {
		return AM_QUERY;
	}

	@Override
	public FinalStep forConfiguration(String configurationUuid) {
		if (Validator.isNull(configurationUuid)) {
			throw new IllegalArgumentException("Configuration UUID is null");
		}

		_configurationUuid = configurationUuid;

		return this;
	}

	@Override
	public AMImageQueryBuilder.InitialStep forFileEntry(FileEntry fileEntry) {
		if (fileEntry == null) {
			throw new IllegalArgumentException("File entry is null");
		}

		_fileEntry = fileEntry;

		return this;
	}

	@Override
	public AMImageQueryBuilder.InitialStep forFileVersion(
		FileVersion fileVersion) {

		if (fileVersion == null) {
			throw new IllegalArgumentException("File version is null");
		}

		_fileVersion = fileVersion;

		return this;
	}

	public Map<AMAttribute<AMProcessor<FileVersion>, ?>, Object>
		getAMAttributes() {

		return _amAttributes;
	}

	public AMDistanceComparator<AdaptiveMedia<AMProcessor<FileVersion>>>
		getAMDistanceComparator() {

		if (!_sortCriteria.isEmpty()) {
			return new AMAttributeDistanceComparator(_sortCriteria);
		}

		if (!_amAttributes.isEmpty()) {
			return new AMPropertyDistanceComparator(_amAttributes);
		}

		return (v1, v2) -> 0L;
	}

	public Predicate<AMImageConfigurationEntry> getConfigurationEntryFilter() {
		if (_hasConfiguration()) {
			return amImageConfigurationEntry -> _configurationUuid.equals(
				amImageConfigurationEntry.getUUID());
		}

		return amImageConfigurationEntry -> true;
	}

	public ConfigurationStatus getConfigurationStatus() {
		if (_configurationStatus != null) {
			return _configurationStatus;
		}

		if (_hasConfiguration()) {
			return AMImageQueryBuilder.ConfigurationStatus.ANY;
		}

		return AMImageQueryBuilder.ConfigurationStatus.ENABLED;
	}

	public String getConfigurationUuid() {
		return _configurationUuid;
	}

	public FileVersion getFileVersion() throws PortalException {
		if (_fileVersion != null) {
			return _fileVersion;
		}

		_fileVersion = _fileEntry.getFileVersion();

		return _fileVersion;
	}

	public boolean hasFileVersion() {
		if (_fileEntry == null) {
			return true;
		}

		return false;
	}

	@Override
	public <V> AMImageQueryBuilder.StrictSortStep orderBy(
		AMAttribute<AMProcessor<FileVersion>, V> amAttribute,
		AMImageQueryBuilder.SortOrder sortOrder) {

		if (amAttribute == null) {
			throw new IllegalArgumentException(
				"Adaptive media attribute is null");
		}

		_sortCriteria.put(amAttribute, sortOrder);

		return this;
	}

	@Override
	public <V> AMImageQueryBuilder.FuzzySortStep with(
		AMAttribute<AMProcessor<FileVersion>, V> amAttribute, V value) {

		if (value == null) {
			throw new IllegalArgumentException(
				"Adaptive media attribute value is null");
		}

		_amAttributes.put(amAttribute, value);

		return this;
	}

	@Override
	public AMImageQueryBuilder.InitialStep withConfigurationStatus(
		ConfigurationStatus configurationStatus) {

		if (configurationStatus == null) {
			throw new IllegalArgumentException("Configuration status is null");
		}

		_configurationStatus = configurationStatus;

		return this;
	}

	private boolean _hasConfiguration() {
		if (Validator.isNotNull(_configurationUuid)) {
			return true;
		}

		return false;
	}

	private final Map<AMAttribute<AMProcessor<FileVersion>, ?>, Object>
		_amAttributes = new LinkedHashMap<>();
	private ConfigurationStatus _configurationStatus;
	private String _configurationUuid;
	private FileEntry _fileEntry;
	private FileVersion _fileVersion;
	private final Map<AMAttribute<AMProcessor<FileVersion>, ?>, SortOrder>
		_sortCriteria = new LinkedHashMap<>();

}