import PropTypes from 'prop-types';
import React from 'react';
import {initialContributorShape} from '../../utils/types.es';

function ContributorInputs({contributors}) {
	return contributors.map((criteria, i) => {
		return (
			<React.Fragment key={i}>
				<input
					className='field form-control'
					data-testid={criteria.inputId}
					id={criteria.inputId}
					name={criteria.inputId}
					readOnly
					type='hidden'
					value={criteria.query}
				/>
				<input
					id={criteria.conjunctionInputId}
					name={criteria.conjunctionInputId}
					readOnly
					type='hidden'
					value={criteria.conjunctionId}
				/>
			</React.Fragment>
		);
	});
}

ContributorInputs.propTypes = {
	contributors: PropTypes.arrayOf(initialContributorShape)
};

export default ContributorInputs;
