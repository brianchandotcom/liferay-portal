import ClayButton from '../shared/ClayButton.es';
import ClaySpinner from '../shared/ClaySpinner.es';
import Conjunction from './Conjunction.es';
import CriteriaBuilder from './CriteriaBuilder.es';
import CriteriaSidebar from '../criteria_sidebar/CriteriaSidebar.es';
import EmptyPlaceholder from './EmptyPlaceholder.es';
import getCN from 'classnames';
import HTML5Backend from 'react-dnd-html5-backend';
import PropTypes from 'prop-types';
import React from 'react';
import {
	conjunctionShape,
	contributorShape,
	operatorShape,
	propertyGroupShape,
	propertyTypesShape
} from '../../utils/types.es';
import {DragDropContext as dragDropContext} from 'react-dnd';
import {getPluralMessage} from '../../utils/utils.es';

class ContributorBuilder extends React.Component {
	static propTypes = {
		contributors: PropTypes.arrayOf(contributorShape),
		editing: PropTypes.bool.isRequired,
		emptyContributors: PropTypes.bool.isRequired,
		membersCount: PropTypes.number,
		membersCountLoading: PropTypes.bool,
		onConjunctionChange: PropTypes.func,
		onPreviewMembers: PropTypes.func,
		onQueryChange: PropTypes.func,
		previewMembersURL: PropTypes.string,
		propertyGroups: PropTypes.arrayOf(propertyGroupShape),
		supportedConjunctions: PropTypes.arrayOf(conjunctionShape).isRequired,
		supportedOperators: PropTypes.arrayOf(operatorShape).isRequired,
		supportedPropertyTypes: propertyTypesShape.isRequired
	};

	static defaultProps = {
		contributors: [],
		membersCount: 0,
		onConjunctionChange: () => {},
		onPreviewMembers: () => {},
		onQueryChange: () => {},
		membersCountLoading: false
	};

	constructor(props) {
		super(props);

		const {contributors, propertyGroups} = props;

		const firstContributorNotEmpty = contributors.find(
			contributor => contributor.query !== ''
		);

		const propertyKey = firstContributorNotEmpty
			? firstContributorNotEmpty.propertyKey
			: propertyGroups[0].propertyKey;

		this.state = {
			editingId: propertyKey
		};
	}

	_handleCriteriaChange = (criteriaChange, index) => {
		const {onQueryChange} = this.props;

		onQueryChange(criteriaChange, index);
	};

	_handleCriteriaEdit = (id, editing) => {
		this.setState({
			editingId: editing ? undefined : id
		});
	};

	render() {
		const {
			contributors,
			editing,
			emptyContributors,
			membersCount,
			membersCountLoading,
			onConjunctionChange,
			onPreviewMembers,
			propertyGroups,
			supportedConjunctions,
			supportedOperators,
			supportedPropertyTypes
		} = this.props;

		const {editingId} = this.state;

		const rootClasses = getCN('contributor-builder-root', {
			editing
		});

		return (
			<div className={rootClasses}>
				<div className='criteria-builder-section-sidebar'>
					<CriteriaSidebar
						onTitleClicked={this._handleCriteriaEdit}
						propertyGroups={propertyGroups}
						propertyKey={editingId}
					/>
				</div>

				<div className='criteria-builder-section-main'>
					<div className='contributor-container'>
						<div className='container-fluid container-fluid-max-xl'>
							<div className='content-wrapper'>
								<div className='sheet'>
									<div className='d-flex flex-wrap justify-content-between mb-4'>
										<h2 className='sheet-title mb-2'>
											{Liferay.Language.get('conditions')}
										</h2>

										<div className='criterion-string'>
											<div className='btn-group'>
												<div className='btn-group-item inline-item'>
													<ClaySpinner
														className='mr-4'
														loading={
															membersCountLoading
														}
														size='sm'
													/>

													{!membersCountLoading && (
														<span className='mr-4'>
															{Liferay.Language.get(
																'conditions-match'
															)}
															<b className='ml-2'>
																{getPluralMessage(
																	Liferay.Language.get(
																		'x-member'
																	),
																	Liferay.Language.get(
																		'x-members'
																	),
																	membersCount
																)}
															</b>
														</span>
													)}

													<ClayButton
														label={Liferay.Language.get(
															'view-members'
														)}
														onClick={
															onPreviewMembers
														}
														size='sm'
														type='button'
													/>
												</div>
											</div>
										</div>
									</div>

									{emptyContributors &&
										(editingId === undefined ||
											!editing) && <EmptyPlaceholder />}

									{contributors
										.filter(criteria => {
											const editingCriteria =
												editingId ===
													criteria.propertyKey &&
												editing;
											const emptyCriteriaQuery =
												criteria.query === '';

											return (
												editingCriteria ||
												!emptyCriteriaQuery
											);
										})
										.map((criteria, i) => {
											return (
												<React.Fragment key={i}>
													{i !== 0 && (
														<React.Fragment>
															<Conjunction
																className='mb-4 ml-0 mt-4'
																conjunctionName={
																	criteria.conjunctionId
																}
																editing={
																	editing
																}
																onClick={
																	onConjunctionChange
																}
																supportedConjunctions={
																	supportedConjunctions
																}
															/>
														</React.Fragment>
													)}

													<CriteriaBuilder
														criteria={
															criteria.criteriaMap
														}
														editing={editing}
														emptyContributors={
															emptyContributors
														}
														entityName={
															criteria.entityName
														}
														modelLabel={
															criteria.modelLabel
														}
														onChange={
															this
																._handleCriteriaChange
														}
														propertyKey={
															criteria.propertyKey
														}
														supportedConjunctions={
															supportedConjunctions
														}
														supportedOperators={
															supportedOperators
														}
														supportedProperties={
															criteria.properties
														}
														supportedPropertyTypes={
															supportedPropertyTypes
														}
													/>
												</React.Fragment>
											);
										})}
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
