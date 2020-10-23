/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import PropTypes from 'prop-types';

const SegmentsExperimentGoal = PropTypes.shape({
	label: PropTypes.string.isRequired,
	target: PropTypes.string,
	value: PropTypes.string.isRequired,
});

const SegmentsExperimentStatus = PropTypes.shape({
	label: PropTypes.string.isRequired,
	value: PropTypes.string.isRequired,
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

const SegmentsExperienceType = PropTypes.shape({
	description: PropTypes.string,
	name: PropTypes.string.isRequired,
	segmentsExperienceId: PropTypes.string.isRequired,
	segmentsExperiment: SegmentsExperimentType,
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
	SegmentsExperienceType,
	SegmentsExperimentGoal,
	SegmentsExperimentStatus,
	SegmentsExperimentType,
	SegmentsVariantType,
};
