/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton from '@clayui/button';
import ClayEmptyState, {DISPLAY_STATES} from '../shared/ClayEmptyState.es';
import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal from '@clayui/modal';
import getCN from 'classnames';
import Item from '../list/Item.es';
import PaginationBar from './PaginationBar.es';
import React, {Component} from 'react';
import ThemeContext from '../../ThemeContext.es';
import {fetchDocuments} from '../../utils/api.es';
import {PropTypes} from 'prop-types';
import {resultsDataToMap} from '../../utils/util.es';
import {sub} from '../../utils/language.es';
import {toggleListItem} from '../../utils/util.es';

const DELTAS = [5, 10, 20, 40, 50];

class AddResult extends Component {
	static contextType = ThemeContext;

	static propTypes = {
		fetchDocumentsUrl: PropTypes.string.isRequired,
		onAddResultSubmit: PropTypes.func.isRequired
	};

	state = {
		addResultSearchQuery: '',
		addResultSelectedIds: [],
		dataLoading: false,
		dataMap: {},
		displayError: false,
		displayInitialMessage: true,
		page: 1,
		results: {},
		selectedDelta: 10,
		showModal: false
	};

	_clearResultSearch = () => {
		this.setState({
			addResultSearchQuery: '',
			results: {}
		});
	};

	_clearResultSelectedIds = () => {
		this.setState({addResultSelectedIds: []});
	};

	_fetchSearchResults = () => {
		const {addResultSearchQuery, page, selectedDelta} = this.state;

		const {companyId, namespace} = this.context;

		this.setState({
			dataLoading: true,
			displayError: false
		});

		fetchDocuments(this.props.fetchDocumentsUrl, {
			[`${namespace}companyId`]: companyId,
			[`${namespace}from`]: page * selectedDelta - selectedDelta,
			[`${namespace}keywords`]: addResultSearchQuery,
			[`${namespace}size`]: selectedDelta
		})
			.then(({items, total}) => {
				this.setState(state => ({
					dataLoading: false,
					dataMap: {
						...state.dataMap,
						...resultsDataToMap(items)
					},
					displayInitialMessage: false,
					results: {
						items,
						total
					}
				}));
			})
			.catch(() => {
				setTimeout(
					() =>
						this.setState({
							dataLoading: false,
							displayError: true
						}),
					1000
				);
			});
	};

	_getCurrentResultSelectedIds = () => {
		const {addResultSelectedIds, results} = this.state;

		const currentResultIds = results.items.map(result => result.id);

		return addResultSelectedIds.filter(resultId =>
			currentResultIds.includes(resultId)
		);
	};

	_handleAddResult = () => {
		this._clearResultSearch();
		this._clearResultSelectedIds();

		this._handleOpenModal();
	};

	_handleAllCheckbox = () => {
		if (this._getCurrentResultSelectedIds().length > 0) {
			this._handleDeselectAll();
		} else {
			this._handleSelectAll();
		}
	};

	_handleClearAllSelected = () => {
		this._clearResultSelectedIds();
	};

	_handleCloseModal = () => {
		this.setState({
			displayError: false,
			displayInitialMessage: true,
			showModal: false
		});
	};

	_handleDeltaChange = item => {
		this.setState(
			state => ({
				page: Math.ceil(
					(state.page * state.selectedDelta -
						state.selectedDelta +
						1) /
						item
				),
				selectedDelta: item
			}),
			this._fetchSearchResults
		);
	};

	_handleDeselectAll = () => {
		const currentResultIds = this.state.results.items.map(
			result => result.id
		);

		this.setState(state => ({
			addResultSelectedIds: state.addResultSelectedIds.filter(
				resultId => !currentResultIds.includes(resultId)
			)
		}));
	};

	_handleOpenModal = () => {
		this.setState({showModal: true});
	};

	_handlePageChange = item => {
		this.setState({page: item}, this._fetchSearchResults);
	};

	_handleSearchChange = event => {
		event.preventDefault();

		this.setState({addResultSearchQuery: event.target.value});
	};

	_handleSearchEnter = () => {
		this._clearResultSelectedIds();

		this._handlePageChange(1);
	};

	_handleSearchKeyDown = event => {
		if (event.key === 'Enter' && event.currentTarget.value.trim()) {
			this._handleSearchEnter();
		}
	};

	_handleSelect = id => {
		this.setState(state => ({
			addResultSelectedIds: toggleListItem(state.addResultSelectedIds, id)
		}));
	};

	_handleSelectAll = () => {
		this._handleDeselectAll();

		this.setState(state => ({
			addResultSelectedIds: [
				...state.addResultSelectedIds,
				...state.results.items.map(result => result.id)
			]
		}));
	};

	_handleSubmit = onClose => event => {
		event.preventDefault();

		const addResultDataList = this.state.addResultSelectedIds.map(
			id => this.state.dataMap[id]
		);

		this.props.onAddResultSubmit(addResultDataList);

		onClose();
	};

	/**
	 * Renders the empty state. Conditionally shows 3 different empty states:
	 * 1) Initial message with help text.
	 * 2) Empty result message when a search returns no matches.
	 * 3) Error message when a search request fails to resolve.
	 */
	_renderEmptyState = () => {
		const {displayError, displayInitialMessage} = this.state;

		let emptyState = <ClayEmptyState />;

		if (displayError) {
			emptyState = (
				<ClayEmptyState
					actionLabel={Liferay.Language.get('try-again')}
					description={Liferay.Language.get(
						'an-error-has-occurred-and-we-were-unable-to-load-the-results'
					)}
					displayState={DISPLAY_STATES.EMPTY}
					onClickAction={this._handleSearchEnter}
					title={Liferay.Language.get('unable-to-load-content')}
				/>
			);
		} else if (displayInitialMessage) {
			emptyState = (
				<ClayEmptyState
					description={Liferay.Language.get(
						'search-the-engine-to-display-results'
					)}
					displayState="empty"
					title={Liferay.Language.get('search-the-engine')}
				/>
			);
		}

		return <div className="add-result-sheet sheet">{emptyState}</div>;
	};

	render() {
		const {
			addResultSearchQuery,
			addResultSelectedIds,
			dataLoading,
			page,
			results,
			selectedDelta,
			showModal
		} = this.state;

		const start = page * selectedDelta;

		const classManagementBar = getCN(
			'management-bar',
			addResultSelectedIds.length > 0
				? 'management-bar-primary'
				: 'management-bar-light',
			'navbar',
			'navbar-expand-md'
		);

		return (
			<li className="nav-item">
				<ClayButton
					key="ADD_RESULT_BUTTON"
					onClick={this._handleAddResult}
				>
					{Liferay.Language.get('add-result')}
				</ClayButton>

				{showModal && (
					<ClayModal
						className="modal-full-screen-sm-down results-ranking-modal-root"
						onClose={this._handleCloseModal}
						size="lg"
					>
						{onClose => (
							<div
								className="add-result-modal-root"
								data-testid="add-result-modal"
							>
								<ClayModal.Header>
									{Liferay.Language.get('add-result')}
								</ClayModal.Header>

								<ClayModal.Body>
									<div className="add-result-container container-fluid">
										<div className="management-bar navbar-expand-md">
											<div className="navbar-form navbar-form-autofit">
												<div className="input-group">
													<div className="input-group-item">
														<input
															aria-label={Liferay.Language.get(
																'search-the-engine'
															)}
															className="form-control input-group-inset input-group-inset-after"
															onChange={
																this
																	._handleSearchChange
															}
															onKeyDown={
																this
																	._handleSearchKeyDown
															}
															placeholder={Liferay.Language.get(
																'search-the-engine'
															)}
															type="text"
															value={
																addResultSearchQuery
															}
														/>

														<div className="input-group-inset-item input-group-inset-item-after">
															<ClayButton
																displayType="unstyled"
																onClick={
																	this
																		._handleSearchEnter
																}
															>
																<ClayIcon symbol="search" />
															</ClayButton>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>

									<div className="add-result-scroller inline-scroller">
										{dataLoading && (
											<div className="list-group sheet">
												<div className="sheet-title">
													<div className="load-more-container">
														<ClayLoadingIndicator />
													</div>
												</div>
											</div>
										)}

										{!dataLoading &&
											(results.total && results.items ? (
												<>
													<div className="add-result-sheet sheet">
														<div
															className={
																classManagementBar
															}
														>
															<div className="container-fluid container-fluid-max-xl">
																<ul className="navbar-nav navbar-nav-expand">
																	<li className="nav-item">
																		<ClayForm.Checkbox
																			aria-label={Liferay.Language.get(
																				'select-all'
																			)}
																			checked={
																				this._getCurrentResultSelectedIds()
																					.length ===
																				results
																					.items
																					.length
																			}
																			indeterminate={
																				addResultSelectedIds.length >
																					0 &&
																				this._getCurrentResultSelectedIds()
																					.length !==
																					results
																						.items
																						.length
																			}
																			onChange={
																				this
																					._handleAllCheckbox
																			}
																		/>
																	</li>

																	<li className="nav-item">
																		<span className="navbar-text">
																			{addResultSelectedIds.length >
																			0
																				? sub(
																						Liferay.Language.get(
																							'x-items-selected'
																						),
																						[
																							addResultSelectedIds.length
																						]
																				  )
																				: sub(
																						Liferay.Language.get(
																							'x-x-of-x-results'
																						),
																						[
																							start -
																								selectedDelta +
																								1,
																							Math.min(
																								start,
																								results.total
																							),
																							results.total
																						]
																				  )}
																		</span>
																	</li>

																	{addResultSelectedIds.length >
																		0 && (
																		<li className="nav-item nav-item-shrink">
																			<ClayButton
																				className="btn-outline-borderless"
																				displayType="secondary"
																				onClick={
																					this
																						._handleClearAllSelected
																				}
																				small
																			>
																				{Liferay.Language.get(
																					'clear-all-selected'
																				)}
																			</ClayButton>
																		</li>
																	)}
																</ul>
															</div>
														</div>

														<ul
															className="list-group"
															data-testid="add-result-items"
														>
															{results.items.map(
																(
																	result,
																	index
																) => (
																	<Item
																		author={
																			result.author
																		}
																		clicks={
																			result.clicks
																		}
																		date={
																			result.date
																		}
																		extension={
																			result.extension
																		}
																		hidden={
																			result.hidden
																		}
																		id={
																			result.id
																		}
																		index={
																			index
																		}
																		key={
																			result.id
																		}
																		onSelect={
																			this
																				._handleSelect
																		}
																		selected={addResultSelectedIds.includes(
																			result.id
																		)}
																		title={
																			result.title
																		}
																		type={
																			result.type
																		}
																	/>
																)
															)}
														</ul>
													</div>

													<div className="add-result-container">
														<PaginationBar
															deltas={DELTAS}
															onDeltaChange={
																this
																	._handleDeltaChange
															}
															onPageChange={
																this
																	._handlePageChange
															}
															page={page}
															selectedDelta={
																selectedDelta
															}
															totalItems={
																results.total
															}
														/>
													</div>
												</>
											) : (
												this._renderEmptyState()
											))}
									</div>
								</ClayModal.Body>

								<ClayModal.Footer
									last={
										<ClayButton.Group spaced>
											<ClayButton
												className="btn-outline-borderless"
												displayType="secondary"
												onClick={onClose}
											>
												{Liferay.Language.get('cancel')}
											</ClayButton>

											<ClayButton
												disabled={
													addResultSelectedIds.length ===
													0
												}
												onClick={this._handleSubmit(
													onClose
												)}
											>
												{Liferay.Language.get('add')}
											</ClayButton>
										</ClayButton.Group>
									}
								/>
							</div>
						)}
					</ClayModal>
				)}
			</li>
		);
	}
}

export default AddResult;
