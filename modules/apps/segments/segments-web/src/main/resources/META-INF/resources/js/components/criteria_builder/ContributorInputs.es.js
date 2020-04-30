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
import React from 'react';

import {CONJUNCTIONS} from '../../utils/constants.es';
import {initialContributorShape} from '../../utils/types.es';

function ContributorInputs({contributors}) {
	return contributors
		.filter((criteria) => criteria.query)
		.map((criteria, i) => {

			/**
			 * First criteria has to be preceded by a `AND` conjunction
			 */
			const conjunction =
				i === 0 ? CONJUNCTIONS.AND : criteria.conjunctionId;

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
						value={conjunction}
					/>
				</React.Fragment>
			);
		});
}

ContributorInputs.propTypes = {
	contributors: PropTypes.arrayOf(initialContributorShape),
};

export default ContributorInputs;
