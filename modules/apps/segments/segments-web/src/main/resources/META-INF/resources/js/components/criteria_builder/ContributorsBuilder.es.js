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
import {CONJUNCTIONS} from '../../utils/constants.es';
import {debounce} from 'metal-debounce';
import {DragDropContext as dragDropContext} from 'react-dnd';
import {getPluralMessage, sub} from '../../utils/utils.es';

const conjunctionShape = PropTypes.shape({
	label: PropTypes.string.isRequired,
	name: PropTypes.string.isRequired
});

const propertyShape = PropTypes.shape({
	label: PropTypes.string.isRequired,
	name: PropTypes.string.isRequired,
	type: PropTypes.string.isRequired
});

const contributorsShape = PropTypes.shape({
	conjunctionId: PropTypes.string,
	conjunctionInputId: PropTypes.string,
	criteriaMap: PropTypes.shape({
		conjunctionName: PropTypes.string,
		groupId: PropTypes.string,
		items: PropTypes.arrayOf(PropTypes.object)
	}),
	entityName: PropTypes.string,
	inputId: PropTypes.string,
	modelLabel: PropTypes.string,
	properties: PropTypes.arrayOf(propertyShape),
	propertyKey: PropTypes.string,
	query: PropTypes.string
});

const operatorShape = PropTypes.shape({
	label: PropTypes.string.isRequired,
	name: PropTypes.string.isRequired
});

const propertyGroupShape = PropTypes.shape({
	entityName: PropTypes.string.isRequired,
	name: PropTypes.string.isRequired,
	properties: PropTypes.arrayOf(propertyShape),
	propertyKey: PropTypes.string.isRequired
});

const propertyTypeShape = PropTypes.shape({
	boolean: PropTypes.arrayOf(PropTypes.string).isRequired,
	date: PropTypes.arrayOf(PropTypes.string).isRequired,
	double: PropTypes.arrayOf(PropTypes.string).isRequired,
	id: PropTypes.arrayOf(PropTypes.string).isRequired,
	integer: PropTypes.arrayOf(PropTypes.string).isRequired,
	string: PropTypes.arrayOf(PropTypes.string).isRequired
});

class ContributorBuilder extends React.Component {
	static propTypes = {
		contributors: PropTypes.arrayOf(contributorsShape),
		editing: PropTypes.bool.isRequired,
		emptyContributors: PropTypes.bool.isRequired,
		formId: PropTypes.string,
		membersCount: PropTypes.number,
		onConjunctionChange: PropTypes.func,
		onQueryChange: PropTypes.func,
		previewMembersURL: PropTypes.string,
		propertyGroups: PropTypes.arrayOf(propertyGroupShape),
		requestMembersCountURL: PropTypes.string,
		segmentName: PropTypes.string,
		supportedConjunctions: PropTypes.arrayOf(conjunctionShape).isRequired,
		supportedOperators: PropTypes.arrayOf(operatorShape).isRequired,
		supportedPropertyTypes: propertyTypeShape.isRequired
	};

	static defaultProps = {
		contributors: [],
		membersCount: 0,
		onConjunctionChange: () => {},
		onQueryChange: () => {}
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
			conjunctionName: CONJUNCTIONS.AND,
			editingId: propertyKey,
			membersCount: props.membersCount,
			membersCountLoading: false
		};

		this._debouncedFetchMembersCount = debounce(
			this._fetchMembersCount,
			500
		);
	}

	_fetchMembersCount = () => {
		const formElement = document.getElementById(this.props.formId);

		const formData = new FormData(formElement);

		fetch(this.props.requestMembersCountURL, {
			body: formData,
			method: 'POST'
		})
			.then(response => response.json())
			.then(membersCount => {
				this.setState({
					membersCount,
					membersCountLoading: false
				});
			})
			.catch(() => {
				this.setState({membersCountLoading: false});

				Liferay.Util.openToast({
					message: Liferay.Language.get(
						'an-unexpected-error-occurred'
					),
					title: Liferay.Language.get('error'),
					type: 'danger'
				});
			});
	};

	_handleCriteriaChange = (criteriaChange, index) => {
		const {onQueryChange} = this.props;

		this.setState(
			{
				membersCountLoading: true
			},
			() => {
				onQueryChange(criteriaChange, index);
				this._debouncedFetchMembersCount();
			}
		);
	};

	_handleCriteriaEdit = (id, editing) => {
		this.setState({
			editingId: editing ? undefined : id
		});
	};

	_handlePreviewClick = url => () => {
		Liferay.Util.openWindow({
			dialog: {
				destroyOnHide: true
			},
			id: 'segment-members-dialog',
			title: sub(Liferay.Language.get('x-members'), [
				this.props.segmentName
			]),
			uri: url
		});
	};

	_handleRootConjunctionClick = event => {
		event.preventDefault();

		const {onConjunctionChange} = this.props;

		this.setState(
			{
				membersCountLoading: true
			},
			() => {
				onConjunctionChange();
				this._debouncedFetchMembersCount();
			}
		);
	};

	render() {
		const {
			contributors,
			editing,
			emptyContributors,
			previewMembersURL,
			propertyGroups,
			supportedConjunctions,
			supportedOperators,
			supportedPropertyTypes
		} = this.props;

		const {editingId, membersCount, membersCountLoading} = this.state;

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
														onClick={this._handlePreviewClick(
															previewMembersURL
														)}
														size='sm'
														type='button'
													/>
												</div>
											</div>
										</div>
									</div>

									{emptyContributors &&
										(editingId == undefined ||
											!editing) && <EmptyPlaceholder />}

									{contributors
										.filter(criteria => {
											const editingCriteria =
												editingId ===
													criteria.propertyKey &&
												editing;
											const emptyCriteriaQuery =
												criteria.query == '';

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
																	this
																		._handleRootConjunctionClick
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
