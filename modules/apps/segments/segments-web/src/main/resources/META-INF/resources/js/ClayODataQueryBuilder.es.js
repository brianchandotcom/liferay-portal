import React from 'react';
import PropTypes from 'prop-types';
import ClayCriteriaBuilder from './ClayCriteriaBuilder.es';
import {
	AND,
	BOOLEAN,
	DATE,
	EQ,
	GE,
	GT,
	LE,
	LT,
	NE,
	NUMBER,
	OR,
	STRING
} from './utils/constants.es';
import {buildQueryString, translateToCriteria} from './utils/odata-util.es';
import './libs/odata-parser.js';

const conjunctions = [
	{
		label: Liferay.Language.get('and'),
		name: AND
	},
	{
		label: Liferay.Language.get('or'),
		name: OR
	}
];

const operators = [
	{
		label: Liferay.Language.get('equals'),
		name: EQ,
		supportedTypes: [BOOLEAN, DATE, NUMBER, STRING]
	},
	{
		label: Liferay.Language.get('greater-than-or-equals'),
		name: GE,
		supportedTypes: [DATE, NUMBER]
	},
	{
		label: Liferay.Language.get('greater-than'),
		name: GT,
		supportedTypes: [DATE, NUMBER]
	},
	{
		label: Liferay.Language.get('less-than-or-equals'),
		name: LE,
		supportedTypes: [DATE, NUMBER]
	},
	{
		label: Liferay.Language.get('less-than'),
		name: LT,
		supportedTypes: [DATE, NUMBER]
	},
	{
		label: Liferay.Language.get('not-equals'),
		name: NE,
		supportedTypes: [BOOLEAN, DATE, NUMBER, STRING]
	}
];

class ClayODataQueryBuilder extends React.Component {
	constructor(props) {
		super(props);

		const {initialQuery} = props;

		this.state = {
			criteriaMap: initialQuery && initialQuery !== '()' ?
				translateToCriteria(initialQuery) :
				null,
			query: initialQuery
		};
	}

	render() {
		const {inputId, properties, readOnly} = this.props;

		const {criteriaMap} = this.state;

		return (
			<div className="clay-query-builder-root">
				<div className="form-group">
					<ClayCriteriaBuilder
						conjunctions={conjunctions}
						criteria={criteriaMap}
						onChange={this._updateQuery}
						operators={operators}
						properties={properties}
						readOnly={readOnly}
					/>
				</div>

				<div className="form-group">
					<textarea
						className="field form-control"
						id={inputId}
						name={inputId}
						readOnly
						value={criteriaMap ? buildQueryString([criteriaMap]) : ''}
					/>
				</div>
			</div>
		);
	}

	_updateQuery = newCriteriaMap => {
		this.setState(
			{
				criteriaMap: newCriteriaMap,
				query: buildQueryString([newCriteriaMap])
			}
		);
	};
}

ClayODataQueryBuilder.propTypes = {
	initialQuery: PropTypes.string,
	inputId: PropTypes.string,
	operators: PropTypes.array,
	properties: PropTypes.array,
	readOnly: PropTypes.bool
};

export default ClayODataQueryBuilder;