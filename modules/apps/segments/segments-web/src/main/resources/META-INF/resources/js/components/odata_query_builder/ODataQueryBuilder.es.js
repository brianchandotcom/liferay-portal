import React, {Component} from 'react';
import PropTypes from 'prop-types';
import CriteriaBuilder from '../criteria_builder/CriteriaBuilder.es';
import {buildQueryString, translateQueryToCriteria} from '../../utils/odata.es';
import {
	SUPPORTED_CONJUNCTIONS,
	SUPPORTED_OPERATORS,
	SUPPORTED_PROPERTY_TYPES
} from '../../utils/constants.es';
import '../../libs/odata-parser.js';

class ODataQueryBuilder extends Component {
	static propTypes = {
		initialQuery: PropTypes.string,
		inputId: PropTypes.string,
		modelLabel: PropTypes.string,
		operators: PropTypes.array,
		properties: PropTypes.array
	};

	constructor(props) {
		super(props);

		const {initialQuery} = props;

		this.state = {
			criteriaMap: initialQuery && initialQuery !== '()' ?
				translateQueryToCriteria(initialQuery) :
				null,
			query: initialQuery
		};
	}

	_handleChange = newCriteriaMap => {
		this.setState(
			{
				criteriaMap: newCriteriaMap,
				query: buildQueryString([newCriteriaMap])
			}
		);
	};

	render() {
		const {inputId, modelLabel, properties} = this.props;

		const {criteriaMap} = this.state;

		return (
			<div className="clay-query-builder-root">
				<div className="form-group">
					<CriteriaBuilder
						criteria={criteriaMap}
						modelLabel={modelLabel}
						onChange={this._handleChange}
						supportedConjunctions={SUPPORTED_CONJUNCTIONS}
						supportedOperators={SUPPORTED_OPERATORS}
						supportedProperties={properties}
						supportedPropertyTypes={SUPPORTED_PROPERTY_TYPES}
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
}

export default ODataQueryBuilder;