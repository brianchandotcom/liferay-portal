import React from 'react';
import PropTypes from 'prop-types';
import {DragDropContext as dragDropContext} from 'react-dnd';
import HTML5Backend from 'react-dnd-html5-backend';
import CriteriaSidebar from '../criteria_sidebar/CriteriaSidebar.es';
import {sub} from '../../utils/utils.es';
import CriteriaBuilder from './CriteriaBuilder.es';
import {buildQueryString, translateQueryToCriteria} from '../../utils/odata.es';
import {CONJUNCTIONS} from '../../utils/constants.es';
import Conjunction from './Conjunction.es';

class ContributorBuilder extends React.Component {
	constructor(props) {
		super(props);

		const {initialContributors, propertyGroups} = props;

		const contributors = initialContributors && initialContributors.map(
			c => {
				const propertyGroup = propertyGroups &&
					propertyGroups.find(
						propertyGroup => c.propertyKey === propertyGroup.propertyKey
					);

				return {
					conjunctionId: c.conjunctionId || CONJUNCTIONS.AND,
					conjunctionInputId: c.conjunctionInputId,
					criteriaMap: c.initialQuery ?
						translateQueryToCriteria(c.initialQuery) :
						null,
					inputId: c.inputId,
					modelLabel: propertyGroup && propertyGroup.name,
					properties: propertyGroup && propertyGroup.properties,
					propertyKey: c.propertyKey,
					query: c.initialQuery
				};
			}
		);

		this.state = {
			conjunctionName: CONJUNCTIONS.AND,
			contributors,
			editingId: undefined,
			newPropertyKey: propertyGroups.length && propertyGroups[0].propertyKey
		};
	}

	_handleCriteriaChange = (criteriaChange, index) => {
		this.setState(
			prevState => {
				let diffState = null;

				if (prevState.editingId === index) {
					diffState = {
						contributors: prevState.contributors.map(
							(contributor, i) => {
								const {conjunctionId, properties} = contributor;

								return index === i ?
									{
										...contributor,
										criteriaMap: criteriaChange,
										query: buildQueryString([criteriaChange], conjunctionId, properties)
									} :
									contributor;
							}
						)
					};
				}

				return diffState;
			}
		);
	}

	_handleCriteriaEdit = (id, editing) => {
		this.setState(
			{
				editingId: editing ? undefined : id
			}
		);
	}

	_handleSelectorChange = event => {
		const newPropertyKey = event.target.value;

		this.setState(
			{
				newPropertyKey
			}
		);
	}

	_handleRootConjunctionClick = event => {
		event.preventDefault();

		this.setState(
			(prevState, props) => {
				const prevContributors = prevState.contributors;

				const prevConjunction = prevContributors[0] &&
					prevContributors[0].conjunctionId;

				const {supportedConjunctions} = props;

				const conjunctionIndex = supportedConjunctions.findIndex(
					item => item.name === prevConjunction
				);

				const conjunctionSelected = (conjunctionIndex === supportedConjunctions.length - 1) ?
					supportedConjunctions[0].name :
					supportedConjunctions[conjunctionIndex + 1].name;

				const contributors = prevContributors.map(
					contributor => ({
						...contributor,
						conjunctionId: conjunctionSelected
					})
				);

				return {
					contributors
				};
			}
		);
	}

	render() {
		const {
			propertyGroups,
			supportedConjunctions,
			supportedOperators,
			supportedPropertyTypes
		} = this.props;

		const {contributors, editingId} = this.state;

		const selectedContributor = contributors[editingId];

		const selectedProperty = selectedContributor &&
			propertyGroups.find(
				propertyGroup => selectedContributor.propertyKey === propertyGroup.propertyKey
			);

		return (
			<div className="criteria-builder-root">
				<div className="criteria-builder-section-main">
					{contributors.map(
						(criteria, i) => (
							<React.Fragment key={i}>
								<div className="sheet-lg">
									{(i !== 0) &&
										<React.Fragment>
											<Conjunction
												className="ml-0"
												conjunctionName={criteria.conjunctionId}
												editing
												onClick={this._handleRootConjunctionClick}
												supportedConjunctions={supportedConjunctions}
											/>

											<input
												id={criteria.conjunctionInputId}
												readOnly
												type="hidden"
												value={criteria.conjunctionId}
											/>
										</React.Fragment>
									}
								</div>

								<CriteriaBuilder
									criteria={criteria.criteriaMap}
									editing={editingId === i}
									id={i}
									initialQuery={criteria.query}
									inputId={criteria.inputId}
									modelLabel={criteria.modelLabel}
									onChange={this._handleCriteriaChange}
									onEditToggle={this._handleCriteriaEdit}
									propertyKey={criteria.propertyKey}
									supportedConjunctions={supportedConjunctions}
									supportedOperators={supportedOperators}
									supportedProperties={criteria.properties}
									supportedPropertyGroups={propertyGroups.map(
										({name, propertyKey}) => ({
											label: name,
											value: propertyKey
										})
									)}
									supportedPropertyTypes={supportedPropertyTypes}
								/>

								<div className="form-group">
									<input
										className="field form-control"
										data-testid={criteria.inputId}
										id={criteria.inputId}
										name={criteria.inputId}
										readOnly
										type="hidden"
										value={criteria.query}
									/>
								</div>
							</React.Fragment>
						)
					)}
				</div>

				<div className="criteria-builder-section-sidebar">
					<CriteriaSidebar
						propertyKey={selectedProperty && selectedProperty.propertyKey}
						supportedProperties={selectedProperty && selectedProperty.properties}
						title={sub(
							Liferay.Language.get('x-properties'),
							[selectedProperty && selectedProperty.name]
						)}
					/>
				</div>
			</div>
		);
	}
}

const conjunctionShape = PropTypes.shape(
	{
		label: PropTypes.string.isRequired,
		name: PropTypes.string.isRequired
	}
);

const initialContributorShape = PropTypes.shape(
	{
		conjunctionId: PropTypes.string.isRequired,
		conjunctionInputId: PropTypes.string.isRequired,
		initialQuery: PropTypes.string.isRequired,
		inputId: PropTypes.string.isRequired,
		propertyKey: PropTypes.string.isRequired
	}
);

const operatorShape = PropTypes.shape(
	{
		label: PropTypes.string.isRequired,
		name: PropTypes.string.isRequired
	}
);

const propertyShape = PropTypes.shape(
	{
		label: PropTypes.string.isRequired,
		name: PropTypes.string.isRequired,
		type: PropTypes.string.isRequired
	}
);

const propertyGroupShape = PropTypes.shape(
	{
		name: PropTypes.string.isRequired,
		properties: PropTypes.arrayOf(propertyShape),
		propertyKey: PropTypes.string.isRequired
	}
);

const propertyTypeShape = PropTypes.shape(
	{
		boolean: PropTypes.arrayOf(PropTypes.string).isRequired,
		date: PropTypes.arrayOf(PropTypes.string).isRequired,
		double: PropTypes.arrayOf(PropTypes.string).isRequired,
		integer: PropTypes.arrayOf(PropTypes.string).isRequired,
		string: PropTypes.arrayOf(PropTypes.string).isRequired
	}
);

ContributorBuilder.propTypes = {
	initialContributors: PropTypes.arrayOf(initialContributorShape),
	propertyGroups: PropTypes.arrayOf(propertyGroupShape),
	supportedConjunctions: PropTypes.arrayOf(conjunctionShape).isRequired,
	supportedOperators: PropTypes.arrayOf(operatorShape).isRequired,
	supportedPropertyTypes: propertyTypeShape.isRequired
};

export default dragDropContext(HTML5Backend)(ContributorBuilder);