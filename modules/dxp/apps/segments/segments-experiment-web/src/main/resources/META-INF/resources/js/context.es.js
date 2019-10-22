import React from 'react';
import PropTypes from 'prop-types';

const SegmentsExperimentsContext = React.createContext({
	APIService: {
		createExperiment: () => {},
		createVariant: () => {},
		deleteExperiment: () => {},
		deleteVariant: () => {},
		editExperiment: () => {},
		editExperimentStatus: () => {},
		editVariant: () => {},
		runExperiment: () => {}
	},
	assetsPath: '',
	page: {
		classNameId: '',
		classPK: '',
		type: ''
	}
});

SegmentsExperimentsContext.Provider.propTypes = {
	value: PropTypes.shape({
		APIService: PropTypes.shape({
			createExperiment: PropTypes.func,
			createVariant: PropTypes.func,
			deleteExperiment: PropTypes.func,
			deleteVariant: PropTypes.func,
			editExperiment: PropTypes.func,
			editExperimentStatus: PropTypes.func,
			editVariant: PropTypes.func,
			runExperiment: PropTypes.func
		}),
		assetsPath: PropTypes.string.isRequired,
		editVariantLayoutURL: PropTypes.string,
		page: PropTypes.shape({
			classNameId: PropTypes.string.isRequired,
			classPK: PropTypes.string.isRequired,
			type: PropTypes.string.isRequired
		})
	})
};

export default SegmentsExperimentsContext;
