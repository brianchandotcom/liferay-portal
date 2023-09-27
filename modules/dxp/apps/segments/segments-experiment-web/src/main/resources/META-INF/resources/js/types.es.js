/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';

const SegmentsExperimentGoal = PropTypes.shape({
	label: PropTypes.string.isRequired,
	target: PropTypes.string,
	value: PropTypes.string.isRequired,
});

const SegmentsExperimentStatus = PropTypes.shape({
	label: PropTypes.string,
	value: PropTypes.number,
});

const SegmentsExperimentType = PropTypes.shape({
	confidenceLevel: PropTypes.number.isRequired,
	description: PropTypes.string,
	editable: PropTypes.bool.isRequired,
	goal: SegmentsExperimentGoal,
	name: PropTypes.string.isRequired,
	segmentsEntryName: PropTypes.string.isRequired,
	segmentsExperienceId: PropTypes.string,
	segmentsExperimentId: PropTypes.string.isRequired,
	status: SegmentsExperimentStatus,
});

const SegmentsVariantType = PropTypes.shape({
	control: PropTypes.bool.isRequired,
	name: PropTypes.string.isRequired,
	segmentsExperienceId: PropTypes.string.isRequired,
	segmentsExperimentId: PropTypes.string.isRequired,
	segmentsExperimentRelId: PropTypes.string.isRequired,
	split: PropTypes.number.isRequired,
	winner: PropTypes.bool,
});

export {
	SegmentsExperimentGoal,
	SegmentsExperimentStatus,
	SegmentsExperimentType,
	SegmentsVariantType,
};
