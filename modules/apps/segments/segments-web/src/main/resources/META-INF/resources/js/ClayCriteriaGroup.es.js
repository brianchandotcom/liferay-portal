import React from 'react';
import {PropTypes} from 'prop-types';
import ClayCriteriaRow from './ClayCriteriaRow.es';
import ClayButton from './ClayButton.es';

function insertAtIndex(item, list, index) {
	return [...list.slice(0, index), item, ...list.slice(index, list.length)];
}

class ClayCriteriaGroup extends React.Component {
	render() {
		const {
			criteria,
			editing,
			root,
			supportedConjunctions,
			supportedOperators,
			supportedProperties,
			supportedPropertyTypes
		} = this.props;

		return (
			<div
				className={root ? 'root-criteria-group' : ' criteria-group'}
			>
				{criteria.items && criteria.items.map(
					(criterion, index) => {
						return (
							<div className="criterion" key={index}>
								{index !== 0 && (
									<ClayButton
										className="btn-sm btn btn-secondary conjunction"
										label={this._getConjunctionLabel(
											criteria.conjunctionName
										)}
										onClick={this._handleConjunctionClick}
									/>
								)}

								<div className="criterion-container">
									{criterion.items ? (
										<ClayCriteriaGroup
											criteria={criterion}
											editing={editing}
											onChange={this._updateCriteria(index, criterion)}
											supportedConjunctions={supportedConjunctions}
											supportedOperators={supportedOperators}
											supportedProperties={supportedProperties}
											supportedPropertyTypes={supportedPropertyTypes}
										/>
									) : (
										<ClayCriteriaRow
											criterion={criterion}
											editing={editing}
											onChange={this._updateCriterion(index)}
											root={root}
											supportedConjunctions={supportedConjunctions}
											supportedOperators={supportedOperators}
											supportedProperties={supportedProperties}
											supportedPropertyTypes={supportedPropertyTypes}
										/>
									)}

									{editing &&
										<ClayButton
											className="btn btn-secondary btn-monospaced"
											iconName="plus"
											onClick={this._handleAddCriteria(index)}
										/>
									}
								</div>
							</div>
						);
					}
				)}
			</div>
		);
	}

	_getConjunctionLabel(conjunctionName) {
		const {supportedConjunctions} = this.props;

		const conjunction = supportedConjunctions.find(
			({name}) => name === conjunctionName
		);

		return conjunction ? conjunction.label : undefined;
	}

	_handleAddCriteria = index => event => {
		event.preventDefault();

		const {
			criteria,
			onChange,
			supportedOperators,
			supportedProperties
		} = this.props;

		const emptyItem = {
			operatorName: supportedOperators[0].name,
			propertyName: supportedProperties[0].name,
			value: ''
		};

		onChange(
			{
				...criteria,
				...{
					items: insertAtIndex(emptyItem, criteria.items, index + 1)
				}
			}
		);
	};

	_handleConjunctionClick = event => {
		event.preventDefault();

		const {criteria, onChange, supportedConjunctions} = this.props;

		const index = supportedConjunctions.findIndex(
			item => item.name === criteria.conjunctionName
		);

		const conjunctionSelected = index === supportedConjunctions.length - 1 ?
			supportedConjunctions[0].name :
			supportedConjunctions[index + 1].name;

		onChange(
			{
				...criteria,
				...{
					conjunctionName: conjunctionSelected
				}
			}
		);
	};

	_updateCriterion = index => newCriterion => {
		const {criteria, onChange} = this.props;

		onChange(
			{
				...criteria,
				...{
					items: newCriterion ?
						Object.assign(
							criteria.items,
							{
								[index]: newCriterion
							}
						) :
						criteria.items.filter(
							(fItem, fIndex) => fIndex !== index
						)
				}
			}
		);
	};

	_updateCriteria = (index, criterion) => newCriteria => {
		this._updateCriterion(index)({...criterion, ...newCriteria});
	};
}

ClayCriteriaGroup.propTypes = {
	criteria: PropTypes.object,
	editing: PropTypes.bool,
	onChange: PropTypes.func,
	root: PropTypes.bool,
	spritemap: PropTypes.string,
	supportedConjunctions: PropTypes.array,
	supportedOperators: PropTypes.array,
	supportedProperties: PropTypes.array,
	supportedPropertyTypes: PropTypes.object
};

ClayCriteriaGroup.defaultProps = {
	root: false
};

export default ClayCriteriaGroup;