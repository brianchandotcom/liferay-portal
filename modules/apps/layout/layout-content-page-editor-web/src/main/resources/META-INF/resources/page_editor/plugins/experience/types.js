/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';

export const ExperimentStatusType = {
	label: PropTypes.string.isRequired,
	value: PropTypes.number.isRequired,
};

export const ExperienceType = {
	hasLockedSegmentsExperiment: PropTypes.bool.isRequired,
	name: PropTypes.string.isRequired,
	priority: PropTypes.number.isRequired,
	segmentsEntryERC: PropTypes.string.isRequired,
	segmentsEntryGroupId: PropTypes.string.isRequired,
	segmentsEntryName: PropTypes.string,
	segmentsEntryScopeERC: PropTypes.string,
	segmentsExperienceId: PropTypes.string.isRequired,
	segmentsExperimentStatus: PropTypes.shape(ExperimentStatusType),
	segmentsExperimentURL: PropTypes.string,
};

export const SegmentType = {
	name: PropTypes.string.isRequired,
	segmentsEntryERC: PropTypes.string.isRequired,
	segmentsEntryGroupId: PropTypes.string.isRequired,
	segmentsEntryId: PropTypes.string.isRequired,
	segmentsEntryScopeERC: PropTypes.string,
};
