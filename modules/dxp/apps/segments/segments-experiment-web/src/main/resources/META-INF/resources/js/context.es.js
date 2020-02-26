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
import React from 'react';

const SegmentsExperimentsContext = React.createContext({
	APIService: {
		createExperiment: () => {},
		createVariant: () => {},
		deleteExperiment: () => {},
		deleteVariant: () => {},
		editExperiment: () => {},
		editExperimentStatus: () => {},
		editVariant: () => {},
		runExperiment: () => {},
	},
	assetsPath: '',
	page: {
		classNameId: '',
		classPK: '',
		type: '',
	},
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
			runExperiment: PropTypes.func,
		}),
		assetsPath: PropTypes.string.isRequired,
		editVariantLayoutURL: PropTypes.string,
		page: PropTypes.shape({
			classNameId: PropTypes.string.isRequired,
			classPK: PropTypes.string.isRequired,
			type: PropTypes.string.isRequired,
		}),
	}),
};

export default SegmentsExperimentsContext;
