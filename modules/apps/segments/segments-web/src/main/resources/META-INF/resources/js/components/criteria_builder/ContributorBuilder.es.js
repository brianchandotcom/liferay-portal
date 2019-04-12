import Conjunction from './Conjunction.es';
import CriteriaBuilder from './CriteriaBuilder.es';
import CriteriaSidebar from '../criteria_sidebar/CriteriaSidebar.es';
import EmptyPlaceholder from './EmptyPlaceholder.es';
import getCN from 'classnames';
import HTML5Backend from 'react-dnd-html5-backend';
import PropTypes from 'prop-types';
import React from 'react';
import {buildQueryString, translateQueryToCriteria} from '../../utils/odata.es';
import {CONJUNCTIONS} from '../../utils/constants.es';
import {DragDropContext as dragDropContext} from 'react-dnd';

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
		entityName: PropTypes.string.isRequired,
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
		id: PropTypes.arrayOf(PropTypes.string).isRequired,
		integer: PropTypes.arrayOf(PropTypes.string).isRequired,
		string: PropTypes.arrayOf(PropTypes.string).isRequired
	}
);

class ContributorBuilder extends React.Component {
	static propTypes = {
		editing: PropTypes.bool.isRequired,
		emptyContributors: PropTypes.bool.isRequired,
		initialContributors: PropTypes.arrayOf(initialContributorShape),
		onQueryChange: PropTypes.func,
		propertyGroups: PropTypes.arrayOf(propertyGroupShape),
		supportedConjunctions: PropTypes.arrayOf(conjunctionShape).isRequired,
		supportedOperators: PropTypes.arrayOf(operatorShape).isRequired,
		supportedPropertyTypes: propertyTypeShape.isRequired
	};

	static defaultProps = {
		onQueryChange: () => {}
	};

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
					entityName: propertyGroup && propertyGroup.entityName,
					inputId: c.inputId,
					modelLabel: propertyGroup && propertyGroup.name,
					properties: propertyGroup && propertyGroup.properties,
					propertyKey: c.propertyKey,
					query: c.initialQuery
				};
			}
		);

		const firstPropertyKey = propertyGroups.length && propertyGroups[0].propertyKey;

		this.state = {
			conjunctionName: CONJUNCTIONS.AND,
			contributors,
			editingId: firstPropertyKey,
			newPropertyKey: firstPropertyKey
		};
	}

	_handleCriteriaChange = (criteriaChange, index) => {
		const {onQueryChange} = this.props;

		this.setState(
			prevState => {
				let diffState = null;

				diffState = {
					contributors: prevState.contributors.map(
						contributor => {
							const {conjunctionId, properties, propertyKey} = contributor;

							return index === propertyKey ?
								{
									...contributor,
									criteriaMap: criteriaChange,
									query: buildQueryString([criteriaChange], conjunctionId, properties)
								} :
								contributor;
						}
					)
				};

				return diffState;
			},
			onQueryChange
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

		const {onQueryChange} = this.props;

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
			},
			onQueryChange
		);
	}

	render() {
		const {
			editing,
			emptyContributors,
			propertyGroups,
			supportedConjunctions,
			supportedOperators,
			supportedPropertyTypes
		} = this.props;

		const {contributors, editingId} = this.state;

		const rootClasses = getCN(
			'contributor-builder-root',
			{
				editing
			}
		);

		return (
			<div className={rootClasses}>
				<div className="criteria-builder-section-sidebar">
					<CriteriaSidebar
						onTitleClicked={this._handleCriteriaEdit}
						propertyGroups={propertyGroups}
						propertyKey={editingId}
					/>
				</div>

				<div className="criteria-builder-section-main">
					<div className="contributor-container">
						<div className="container-fluid container-fluid-max-xl">
							<div className="content-wrapper">
								<div className="sheet">
									<h2 className="sheet-title">{Liferay.Language.get('conditions')}</h2>

									{((emptyContributors && editingId == undefined) || (!editing && emptyContributors)) &&
										<EmptyPlaceholder />
									}

									{contributors.map(
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
									)}

									{contributors.filter(
										criteria => {
											return editingId === criteria.propertyKey || criteria.query != '';
										}
									).map(
										(criteria, i) => {
											const editingCriteria = editing && editingId === criteria.propertyKey;
											return (
												<React.Fragment key={i}>
													{(i !== 0) &&
													<React.Fragment>
														<Conjunction
															className="ml-0"
															conjunctionName={criteria.conjunctionId}
															editing
															onClick={this._handleRootConjunctionClick}
															supportedConjunctions={supportedConjunctions}
														/>
													</React.Fragment>
													}

													<CriteriaBuilder
														criteria={criteria.criteriaMap}
														editing={editing}
														editingCriteria={editingCriteria}
														editingId={editingId}
														emptyContributors={emptyContributors}
														entityName={criteria.entityName}
														id={criteria.propertyKey}
														modelLabel={criteria.modelLabel}
														onChange={this._handleCriteriaChange}
														propertyKey={criteria.propertyKey}
														supportedConjunctions={supportedConjunctions}
														supportedOperators={supportedOperators}
														supportedProperties={criteria.properties}
														supportedPropertyTypes={supportedPropertyTypes}
													/>
												</React.Fragment>
											);
										}
									)}
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		);
	}
}

export default dragDropContext(HTML5Backend)(ContributorBuilder);