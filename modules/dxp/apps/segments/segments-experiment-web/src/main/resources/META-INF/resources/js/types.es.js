import PropTypes from 'prop-types';

const SegmentsExperimentType = PropTypes.shape({
	confidenceLevel: PropTypes.number.isRequired,
	description: PropTypes.string,
	editable: PropTypes.bool.isRequired,
	goal: SegmentsExperimentGoal,
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

const SegmentsVariantType = PropTypes.shape({
	control: PropTypes.bool.isRequired,
	name: PropTypes.string.isRequired,
	segmentsExperienceId: PropTypes.string.isRequired,
	segmentsExperimentId: PropTypes.string.isRequired,
	segmentsExperimentRelId: PropTypes.string.isRequired,
	split: PropTypes.number.isRequired,
	winner: PropTypes.bool
});

const SegmentsExperimentGoal = PropTypes.shape({
	label: PropTypes.string.isRequired,
	target: PropTypes.string,
	value: PropTypes.string.isRequired
});

const SegmentsExperimentStatus = PropTypes.shape({
	label: PropTypes.string.isRequired,
	value: PropTypes.string.isRequired
});

export {
	SegmentsExperienceType,
	SegmentsExperimentGoal,
	SegmentsExperimentStatus,
	SegmentsExperimentType,
	SegmentsVariantType
};
