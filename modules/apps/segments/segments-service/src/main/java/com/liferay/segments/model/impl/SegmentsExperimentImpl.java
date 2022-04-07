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

package com.liferay.segments.model.impl;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperimentRel;
import com.liferay.segments.service.SegmentsEntryLocalServiceUtil;
import com.liferay.segments.service.SegmentsExperienceLocalServiceUtil;
import com.liferay.segments.service.SegmentsExperimentRelLocalServiceUtil;

import java.io.IOException;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * The extended model implementation for the SegmentsExperiment service.
 * Represents a row in the &quot;SegmentsExperiment&quot; database table, with
 * each column mapped to a property of this class.
 *
 * <p>
 * Helper methods and all application logic should be put in this class.
 * Whenever methods are added, rerun ServiceBuilder to copy their definitions
 * into the <code>com.liferay.segments.model.SegmentsExperiment</code>
 * interface.
 * </p>
 *
 * @author Eduardo García
 */
public class SegmentsExperimentImpl extends SegmentsExperimentBaseImpl {

	@Override
	public double getConfidenceLevel() {
		UnicodeProperties typeSettingsUnicodeProperties =
			getTypeSettingsProperties();

		return GetterUtil.getDouble(
			typeSettingsUnicodeProperties.getProperty("confidenceLevel"));
	}

	@Override
	public String getGoal() {
		UnicodeProperties typeSettingsUnicodeProperties =
			getTypeSettingsProperties();

		return GetterUtil.getString(
			typeSettingsUnicodeProperties.getProperty("goal"));
	}

	@Override
	public String getGoalTarget() {
		UnicodeProperties typeSettingsUnicodeProperties =
			getTypeSettingsProperties();

		return GetterUtil.getString(
			typeSettingsUnicodeProperties.getProperty("goalTarget"));
	}

	@Override
	public String getSegmentsEntryName(Locale locale) throws PortalException {
		SegmentsExperience segmentsExperience =
			SegmentsExperienceLocalServiceUtil.getSegmentsExperience(
				getSegmentsExperienceId());

		if (segmentsExperience.getSegmentsEntryId() ==
				SegmentsEntryConstants.ID_DEFAULT) {

			return SegmentsEntryConstants.getDefaultSegmentsEntryName(locale);
		}

		SegmentsEntry segmentsEntry =
			SegmentsEntryLocalServiceUtil.getSegmentsEntry(
				segmentsExperience.getSegmentsEntryId());

		return segmentsEntry.getName(locale);
	}

	@Override
	public String getSegmentsExperienceKey() {
		return Optional.ofNullable(
			SegmentsExperienceLocalServiceUtil.fetchSegmentsExperience(
				getSegmentsExperienceId())
		).map(
			SegmentsExperience::getSegmentsExperienceKey
		).orElse(
			null
		);
	}

	@Override
	public List<SegmentsExperimentRel> getSegmentsExperimentRels() {
		return SegmentsExperimentRelLocalServiceUtil.getSegmentsExperimentRels(
			getSegmentsExperimentId());
	}

	@Override
	public UnicodeProperties getTypeSettingsProperties() {
		if (_typeSettingsUnicodeProperties == null) {
			_typeSettingsUnicodeProperties = new UnicodeProperties(true);

			try {
				_typeSettingsUnicodeProperties.load(super.getTypeSettings());
			}
			catch (IOException ioException) {
				_log.error(ioException);
			}
		}

		return _typeSettingsUnicodeProperties;
	}

	@Override
	public long getWinnerSegmentsExperienceId() {
		UnicodeProperties typeSettingsUnicodeProperties =
			getTypeSettingsProperties();

		return GetterUtil.getLong(
			typeSettingsUnicodeProperties.getProperty(
				"winnerSegmentsExperienceId"),
			-1);
	}

	@Override
	public String getWinnerSegmentsExperienceKey() {
		long winnerSegmentsExperienceId = getWinnerSegmentsExperienceId();

		if (winnerSegmentsExperienceId < 0) {
			return StringPool.BLANK;
		}

		SegmentsExperience winnerSegmentsExperience =
			SegmentsExperienceLocalServiceUtil.fetchSegmentsExperience(
				winnerSegmentsExperienceId);

		return winnerSegmentsExperience.getSegmentsExperienceKey();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsExperimentImpl.class);

	private UnicodeProperties _typeSettingsUnicodeProperties;

}