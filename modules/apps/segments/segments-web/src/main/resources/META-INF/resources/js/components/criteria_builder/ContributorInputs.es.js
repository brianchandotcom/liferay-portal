import PropTypes from 'prop-types';
import React from 'react';

function ContributorInputs({contributors}) {
	return contributors.map(
		(criteria, i) => {
			return (<React.Fragment key={i}>
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
			</React.Fragment>);
		}
	);
}

ContributorInputs.propTypes = {
	contributors: PropTypes.arrayOf(
		PropTypes.shape(
			{
				conjunctionId: PropTypes.string,
				conjunctionInputId: PropTypes.string,
				initialQuery: PropTypes.string,
				inputId: PropTypes.string,
				propertyKey: PropTypes.string
			}
		)
	)
};

export default ContributorInputs;