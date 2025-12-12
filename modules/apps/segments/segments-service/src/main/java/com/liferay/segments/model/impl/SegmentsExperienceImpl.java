/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.model.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ScopeUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.segments.constants.SegmentsExperimentConstants;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.service.SegmentsExperienceLocalServiceUtil;
import com.liferay.segments.service.SegmentsExperimentLocalServiceUtil;

import java.io.IOException;

/**
 * @author Eduardo García
 */
public class SegmentsExperienceImpl extends SegmentsExperienceBaseImpl {

	@Override
	public long getSegmentsEntryGroupId() {
		Long groupId = ScopeUtil.getItemGroupId(
			getCompanyId(), getSegmentsEntryScopeERC(), getGroupId());

		if (groupId == null) {
			return 0;
		}

		return groupId;
	}

	@Override
	public UnicodeProperties getTypeSettingsUnicodeProperties() {
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
	public boolean hasSegmentsExperiment() {
		SegmentsExperience segmentsExperience =
			SegmentsExperienceLocalServiceUtil.fetchSegmentsExperience(
				getSegmentsExperienceId());

		SegmentsExperiment segmentsExperiment =
			SegmentsExperimentLocalServiceUtil.fetchSegmentsExperiment(
				getGroupId(), segmentsExperience.getSegmentsExperienceKey(),
				_getPublishedLayoutPlid());

		if ((segmentsExperiment == null) ||
			!ArrayUtil.contains(
				SegmentsExperimentConstants.Status.getLockedStatusValues(),
				segmentsExperiment.getStatus())) {

			return false;
		}

		return true;
	}

	@Override
	public void setTypeSettingsUnicodeProperties(
		UnicodeProperties typeSettingsUnicodeProperties) {

		_typeSettingsUnicodeProperties = typeSettingsUnicodeProperties;

		super.setTypeSettings(_typeSettingsUnicodeProperties.toString());
	}

	private long _getPublishedLayoutPlid() {
		Layout layout = LayoutLocalServiceUtil.fetchLayout(getPlid());

		if ((layout != null) && layout.isDraftLayout()) {
			return layout.getClassPK();
		}

		return getPlid();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsExperienceImpl.class);

	private UnicodeProperties _typeSettingsUnicodeProperties;

}