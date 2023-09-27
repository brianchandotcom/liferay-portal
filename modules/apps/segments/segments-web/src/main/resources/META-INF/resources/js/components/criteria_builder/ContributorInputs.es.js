/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';

import {initialContributorShape} from '../../utils/types.es';

function ContributorInputs({contributors}) {
	return contributors
		.filter((criteria) => criteria.query)
		.map((criteria, i) => {
			return (
				<React.Fragment key={i}>
					<input
						className="field form-control"
						data-testid={criteria.inputId}
						id={criteria.inputId}
						name={criteria.inputId}
						readOnly
						type="hidden"
						value={criteria.query}
					/>

					<input
						id={criteria.conjunctionInputId}
						name={criteria.conjunctionInputId}
						readOnly
						type="hidden"
						value={criteria.conjunctionId}
					/>
				</React.Fragment>
			);
		});
}

ContributorInputs.propTypes = {
	contributors: PropTypes.arrayOf(initialContributorShape),
};

export default ContributorInputs;
