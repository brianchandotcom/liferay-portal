/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	imagesPath: '',
	page: {
		plid: '',
		type: '',
	},
});

SegmentsExperimentsContext.displayName = 'SegmentsExperimentsContext';

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
		editVariantLayoutURL: PropTypes.string,
		imagesPath: PropTypes.string.isRequired,
		page: PropTypes.shape({
			plid: PropTypes.string.isRequired,
			type: PropTypes.string.isRequired,
		}),
	}),
};

export default SegmentsExperimentsContext;
