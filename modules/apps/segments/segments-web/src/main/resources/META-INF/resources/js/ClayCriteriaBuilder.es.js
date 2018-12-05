import React from 'react';
import PropTypes from 'prop-types';
import ClayCriteriaGroup from './ClayCriteriaGroup.es';
import ClayButton from './ClayButton.es';

class ClayCriteriaBuilder extends React.Component {
	constructor(props) {
		super(props);

		this.state = {
			editing: false
		};
	}

	render() {
		const {
			criteria,
			supportedConjunctions,
			supportedOperators,
			supportedProperties,
			supportedPropertyTypes
		} = this.props;

		const {editing} = this.state;

		return (
			<div className="criteria-builder">
				<div className="criteria-builder-toolbar">
					<ClayButton
						label={Liferay.Language.get('edit')}
						onClick={this._handleToggleEdit}
					/>
				</div>

				{this._isCriteriaEmpty() ? (
					<ClayCriteriaGroup
						criteria={criteria}
						editing={editing}
						onChange={this._updateCriteria}
						root
						supportedConjunctions={supportedConjunctions}
						supportedOperators={supportedOperators}
						supportedProperties={supportedProperties}
						supportedPropertyTypes={supportedPropertyTypes}
					/>
				) : (
					<div
						className="empty-state"
						onClick={this._handleNewCriteria}
					>
						{Liferay.Language.get('click-to-start-editing')}
					</div>
				)}
			</div>
		);
	}

	static _buildCriteriaTypes(operators) {
		return operators.reduce(
			(criteriaTypes, {supportedTypes}) => {
				supportedTypes.forEach(
					type => {
						if (!criteriaTypes[type]) {
							criteriaTypes[type] = operators.filter(
								operator =>
									operator.supportedTypes.includes(type)
							);
						}
					}
				);

				return criteriaTypes;
			},
			new Map()
		);
	}

	_cleanCriteria(criterion) {
		const test = criterion
			.filter(
				({items}) => {
					return items ? items.length : true;
				}
			)
			.map(
				item => {
					return item.items ?
						{
							...item,
							...{
								items: this._cleanCriteria(item.items)
							}
						} :
						item;
				}
			);

		return test;
	}

	_handleNewCriteria = () => {
		const {onChange, supportedOperators, supportedProperties} = this.props;

		const emptyItem = {
			operatorName: supportedOperators[0].name,
			propertyName: supportedProperties[0].name,
			value: ''
		};

		onChange(
			{
				conjunctionName: 'and',
				items: [emptyItem]
			}
		);
	}

	_handleToggleEdit = event => {
		event.preventDefault();

		this.setState(
			{
				editing: !this.state.editing
			}
		);
	};

	_isCriteriaEmpty = () => {
		const {criteria} = this.props;

		return criteria ? criteria.items.length : false;
	}

	_updateCriteria = newCriteria => {
		this.props.onChange(this._cleanCriteria([newCriteria]).pop());
	};
}

const CRITERIA_GROUP_SHAPE = {
	conjunctionId: PropTypes.string,
	items: PropTypes.array
};

const CRITERION_SHAPE = {
	operatorName: PropTypes.string,
	propertyName: PropTypes.string,
	value: PropTypes.oneOfType([PropTypes.string, PropTypes.array])
};

ClayCriteriaBuilder.propTypes = {
	criteria: PropTypes.shape(
		{
			conjunctionId: PropTypes.string,
			items: PropTypes.arrayOf(
				PropTypes.oneOfType(
					[
						PropTypes.shape(CRITERIA_GROUP_SHAPE),
						PropTypes.shape(CRITERION_SHAPE)
					]
				)
			)
		}
	),
	onChange: PropTypes.func,
	readOnly: PropTypes.bool,
	supportedConjunctions: PropTypes.arrayOf(
		PropTypes.shape(
			{
				label: PropTypes.string,
				name: PropTypes.string.isRequired
			}
		)
	),
	supportedOperators: PropTypes.array,
	supportedProperties: PropTypes.arrayOf(
		PropTypes.shape(
			{
				entityUrl: PropTypes.string,
				label: PropTypes.string,
				name: PropTypes.string.isRequired,
				options: PropTypes.array,
				type: PropTypes.string.isRequired
			}
		)
	).isRequired,
	supportedPropertyTypes: PropTypes.object
};

ClayCriteriaBuilder.defaultProps = {
	readOnly: false
};

export default ClayCriteriaBuilder;