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

import AddResultModal from './AddResultModal.es';
import ClayButton from '@clayui/button';
import ClayEmptyState, {DISPLAY_STATES} from '../shared/ClayEmptyState.es';
import React, {Component} from 'react';
import ThemeContext from '../../ThemeContext.es';
import {fetchDocuments} from '../../utils/api.es';
import {PropTypes} from 'prop-types';
import {resultsDataToMap} from '../../utils/util.es';
import {toggleListItem} from '../../utils/util.es';

const DELTAS = [5, 10, 20, 30, 50];

class AddResult extends Component {
	static contextType = ThemeContext;

	static propTypes = {
		fetchDocumentsSearchUrl: PropTypes.string.isRequired,
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
		selectedDelta: DELTAS[4],
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

		fetchDocuments(this.props.fetchDocumentsSearchUrl, {
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

		return (
			<li className="nav-item">
				<ClayButton
					key="ADD_RESULT_BUTTON"
					onClick={this._handleAddResult}
				>
					{Liferay.Language.get('add-result')}
				</ClayButton>

				{showModal && (
					<AddResultModal
						DELTAS={DELTAS}
						addResultSearchQuery={addResultSearchQuery}
						addResultSelectedIds={addResultSelectedIds}
						dataLoading={dataLoading}
						getCurrentResultSelectedIds={
							this._getCurrentResultSelectedIds
						}
						handleAllCheckbox={this._handleAllCheckbox}
						handleClearAllSelected={this._handleClearAllSelected}
						handleClose={this._handleCloseModal}
						handleDeltaChange={this._handleDeltaChange}
						handlePageChange={this._handlePageChange}
						handleSearchChange={this._handleSearchChange}
						handleSearchEnter={this._handleSearchEnter}
						handleSearchKeyDown={this._handleSearchKeyDown}
						handleSelect={this._handleSelect}
						handleSubmit={this._handleSubmit}
						page={page}
						renderEmptyState={this._renderEmptyState}
						results={results}
						selectedDelta={selectedDelta}
						showModal={showModal}
					/>
				)}
			</li>
		);
	}
}

export default AddResult;
