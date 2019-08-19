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

import PropTypes from 'prop-types';

const SegmentsExperimentType = PropTypes.shape({
	description: PropTypes.string,
	goal: PropTypes.string.isRequired,
	goalTarget: PropTypes.string,
	name: PropTypes.string.isRequired,
	segmentsEntryName: PropTypes.string.isRequired,
	segmentsExperienceId: PropTypes.string,
	segmentsExperimentId: PropTypes.string.isRequired,
	status: SegmentsExperimentStatus
});

const SegmentsExperienceType = PropTypes.shape({
	description: PropTypes.string,
	name: PropTypes.string.isRequired,
	segmentsExperienceId: PropTypes.string.isRequired,
	segmentsExperiment: SegmentsExperimentType
});

const InitialSegmentsVariantType = PropTypes.shape({
	name: PropTypes.string.isRequired,
	segmentsExperienceId: PropTypes.string.isRequired,
	segmentsExperimentId: PropTypes.string.isRequired,
	segmentsExperimentRelId: PropTypes.string.isRequired
});

const SegmentsVariantType = PropTypes.shape({
	control: PropTypes.bool.isRequired,
	name: PropTypes.string.isRequired,
	segmentsExperienceId: PropTypes.string.isRequired,
	segmentsExperimentId: PropTypes.string.isRequired,
	segmentsExperimentRelId: PropTypes.string.isRequired
});

const SegmentsExperimentGoal = PropTypes.shape({
	label: PropTypes.string.isRequired,
	value: PropTypes.string.isRequired
});

const SegmentsExperimentStatus = PropTypes.shape({
	label: PropTypes.string.isRequired,
	value: PropTypes.string.isRequired
});

export {
	InitialSegmentsVariantType,
	SegmentsExperienceType,
	SegmentsExperimentGoal,
	SegmentsExperimentStatus,
	SegmentsExperimentType,
	SegmentsVariantType
};
