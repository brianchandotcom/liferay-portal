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

import AddResultSearchBar from './AddResultSearchBar.es';
import ClayButton from '@clayui/button';
import ClayEmptyState, {DISPLAY_STATES} from '../shared/ClayEmptyState.es';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal, {useModal} from '@clayui/modal';
import {ClayPaginationWithBar} from '@clayui/pagination';
import getCN from 'classnames';
import Item from '../list/Item.es';
import PropTypes from 'prop-types';
import React, {useContext, useState} from 'react';
import ThemeContext from '../../ThemeContext.es';
import {ClayCheckbox} from '@clayui/form';
import {buildUrl, resultsDataToMap, toggleListItem} from '../../utils/util.es';
import {
	DELTAS,
	DEFAULT_DELTA,
	FETCH_OPTIONS,
	KEY_CODES
} from '../../utils/constants.es';
import {getPluralMessage} from '../../utils/language.es';
import {useResource} from '@clayui/data-provider';

/**
 * A button that opens a modal to be able to search, select, and add results.
 */
function AddResultModal({
	fetchDocumentsSearchUrl,
	onAddResultSubmit,
	onCloseModal
}) {
	const {companyId, namespace, spritemap} = useContext(ThemeContext);

	/**
	 * State flow: resourceNotAsked -> loading -> error/success
	 * Success is implicit when all are false.
	 */
	const [resourceState, setResourceState] = useState(() => ({
		error: false,
		loading: false
	}));

	const {error, loading} = resourceState;

	const [delta, setDelta] = useState(DEFAULT_DELTA.label);
	const [resourceNotAsked, setResourceNotAsked] = useState(true);
	const [page, setPage] = useState(1);
	const [searchQuery, setSearchQuery] = useState('');
	const [selectedIds, setSelectedIds] = useState([]);

	/**
	 * Stores the full object data of selected results. This is used to
	 * transform the `selectedIds` into a the list of objects to send to
	 * `onAddResultSubmit`.
	 */
	const [dataMap, setDataMap] = useState(false);

	const {observer, onClose} = useModal({
		onClose: _handleCloseModal
	});

	const {resource, refetch} = useResource({
		fetchOptions: FETCH_OPTIONS,
		link: buildUrl(fetchDocumentsSearchUrl, {
			[`${namespace}companyId`]: companyId,
			[`${namespace}from`]: page * delta - delta,
			[`${namespace}keywords`]: searchQuery,
			[`${namespace}size`]: delta
		}),
		onNetworkStatusChange: status =>
			setResourceState({
				error: status === 5,
				loading: status < 4
			})
	});

	/**
	 * Deselects all items on the current page.
	 */
	function _deselectAll() {
		setSelectedIds(
			selectedIds.filter(
				resultId => !_getCurrentResultIds().includes(resultId)
			)
		);
	}

	/**
	 * Gets the ids of the current results displayed on the page.
	 * @returns {Array} List of ids
	 */
	function _getCurrentResultIds() {
		return resource.documents.map(result => result.id);
	}

	/**
	 * Gets the selected ids on the current page. Useful for displaying the
	 * proper checkbox and performing the proper action when clicking on the
	 * checkbox.
	 * @returns {Array} List of ids
	 */
	function _getCurrentResultSelectedIds() {
		return selectedIds.filter(resultId =>
			_getCurrentResultIds().includes(resultId)
		);
	}

	/**
	 * Gets the result data object from ids.
	 * @param {Array} ids The list of ids to get.
	 * @returns {Array} List of result data objects.
	 */
	function _getResultData(ids) {
		return resource.documents.filter(({id}) => ids.includes(id));
	}

	/**
	 * Controls the behavior when clicking on the list header checkbox.
	 * If any results on the current page are selected, the items will be
	 * deselected. Otherwise, select all items.
	 */
	function _handleAllCheckbox() {
		if (_getCurrentResultSelectedIds().length > 0) {
			_deselectAll();
		} else {
			_selectAll();
		}
	}

	/**
	 * Removes all selected ids.
	 */
	function _handleClearAllSelected() {
		setSelectedIds([]);
	}

	/**
	 * Closes the modal and reverts back to initial state for the next time the
	 * modal is opened.
	 */
	function _handleCloseModal() {
		onCloseModal();
	}

	/**
	 * Sets the new delta, resets to the first page, and fetches for the new
	 * set of results.
	 * @param {number} newDelta The delta selected.
	 */
	function _handleDeltaChange(newDelta) {
		setDelta(newDelta);
		setPage(1);

		_handleRefetch();
	}

	/**
	 * Sets the new page selected and fetches for the new results.
	 * @param {number} newPage The page selected.
	 */
	function _handlePageChange(newPage) {
		setPage(newPage);

		_handleRefetch();
	}

	/**
	 * Wrapper around the `refetch` function to keep track if it has been
	 * called to be able to show an initial display. This is a workaround
	 * since there isn't currently a way to prevent an initial fetch from
	 * being called when using `useResource`.
	 */
	function _handleRefetch() {
		setResourceNotAsked(false);

		refetch();
	}

	/**
	 * When the 'Enter' key is pressed, the page will reset back to the first
	 * page, selections will be cleared, and a new search will be performed.
	 * If the search query is blank, nothing will happen.
	 * @param {SyntheticEvent} event
	 */
	function _handleSearchKeyDown(event) {
		if (!event.currentTarget.value.trim()) {
			return;
		}

		if (event.key === KEY_CODES.ENTER) {
			setPage(1);
			setSelectedIds([]);

			_handleRefetch();
		}
	}

	/**
	 * Updates the search query input field.
	 * @param {SyntheticEvent} event
	 */
	function _handleSearchQueryChange(event) {
		setSearchQuery(event.target.value);
	}

	/**
	 * Handles when an item's checkbox is clicked on.
	 * @param {String} id The id selected.
	 */
	function _handleSelect(id) {
		const resultDataToSelect = _getResultData([id]);

		setDataMap(resultsDataToMap(resultDataToSelect, dataMap));

		setSelectedIds(toggleListItem(selectedIds, id));
	}

	/**
	 * Primary action of the modal. Pins the selected results to the result
	 * rankings form.
	 * @param {SyntheticEvent} event
	 */
	function _handleSubmit(event) {
		event.preventDefault();

		onAddResultSubmit(selectedIds.map(id => dataMap[id]));

		onClose();
	}

	/**
	 * Checks if there are any results to display.
	 * @returns {boolean} True if there is data.
	 */
	function _hasResultsToShow() {
		return !!(resource && resource.total && resource.documents);
	}

	/**
	 * Selects all items on the current page.
	 */
	function _selectAll() {
		_deselectAll(); // Deselect all items first to avoid adding duplicates.

		const resultDataToSelect = _getResultData(_getCurrentResultIds());

		setDataMap(resultsDataToMap(resultDataToSelect, dataMap));

		setSelectedIds([...selectedIds, ..._getCurrentResultIds()]);
	}

	/**
	 * Renders the empty state. Conditionally shows 3 different empty states:
	 * 1) Initial message with help text.
	 * 2) Empty result message when a search returns no matches.
	 * 3) Error message when a search request fails to resolve.
	 */
	function _renderEmptyState() {
		let emptyState = <ClayEmptyState />;

		if (resourceNotAsked) {
			emptyState = (
				<ClayEmptyState
					description={Liferay.Language.get(
						'search-the-engine-to-display-results'
					)}
					displayState="empty"
					title={Liferay.Language.get('search-the-engine')}
				/>
			);
		} else if (error) {
			emptyState = (
				<ClayEmptyState
					actionLabel={Liferay.Language.get('try-again')}
					description={Liferay.Language.get(
						'an-error-has-occurred-and-we-were-unable-to-load-the-results'
					)}
					displayState={DISPLAY_STATES.EMPTY}
					onClickAction={_handleRefetch}
					title={Liferay.Language.get('unable-to-load-content')}
				/>
			);
		}

		return <div className="add-result-sheet sheet">{emptyState}</div>;
	}

	/**
	 * Displays the search results with a management and pagination bar.
	 */
	function _renderSearchResults() {
		const classManagementBar = getCN(
			'management-bar',
			selectedIds.length > 0
				? 'management-bar-primary'
				: 'management-bar-light',
			'navbar',
			'navbar-expand-md'
		);

		const selectItemsLabel =
			selectedIds.length > 0
				? getPluralMessage(
						Liferay.Language.get('x-item-selected'),
						Liferay.Language.get('x-items-selected'),
						selectedIds.length
				  )
				: Liferay.Language.get('select-items');

		const checked =
			_getCurrentResultSelectedIds().length === resource.documents.length;

		const indeterminate =
			selectedIds.length > 0 &&
			_getCurrentResultSelectedIds().length !== resource.documents.length;

		return (
			<>
				<div className="add-result-sheet sheet">
					<div className={classManagementBar}>
						<div className="container-fluid container-fluid-max-xl">
							<ul className="navbar-nav navbar-nav-expand">
								<li className="nav-item">
									<ClayCheckbox
										aria-label={Liferay.Language.get(
											'select-all'
										)}
										checked={checked}
										indeterminate={indeterminate}
										onChange={_handleAllCheckbox}
									/>
								</li>

								<li className="nav-item">
									<span className="navbar-text">
										{selectItemsLabel}
									</span>
								</li>

								{selectedIds.length > 0 && (
									<li className="nav-item nav-item-shrink">
										<ClayButton
											className="btn-outline-borderless"
											displayType="secondary"
											onClick={_handleClearAllSelected}
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

					<ul className="list-group" data-testid="add-result-items">
						{resource.documents.map((result, index) => (
							<Item.DecoratedComponent
								author={result.author}
								clicks={result.clicks}
								date={result.date}
								extension={result.extension}
								hidden={result.hidden}
								id={result.id}
								index={index}
								key={result.id}
								onSelect={_handleSelect}
								selected={selectedIds.includes(result.id)}
								title={result.title}
								type={result.type}
							/>
						))}
					</ul>
				</div>

				{/*
					Temporarily hide pagination bar until pagination is fixed
					in LPS-96397. (LPS-101090)
				*/}

				{false && (
					<div className="add-result-container">
						<ClayPaginationWithBar
							activeDelta={delta}
							activePage={page}
							deltas={DELTAS}
							ellipsisBuffer={1}
							labels={{
								paginationResults: Liferay.Language.get(
									'showing-x-to-x-of-x-entries'
								),
								perPageItems: Liferay.Language.get('x-items'),
								selectPerPageItems: Liferay.Language.get(
									'x-items'
								)
							}}
							onDeltaChange={_handleDeltaChange}
							onPageChange={_handlePageChange}
							spritemap={spritemap}
							totalItems={resource.total}
						/>
					</div>
				)}
			</>
		);
	}

	return (
		<ClayModal
			className="modal-full-screen-sm-down results-ranking-modal-root"
			observer={observer}
			size="lg"
		>
			<div
				className="add-result-modal-root"
				data-testid="add-result-modal"
			>
				<ClayModal.Header>
					{Liferay.Language.get('add-result')}
				</ClayModal.Header>

				<ClayModal.Body>
					<AddResultSearchBar
						onSearchKeyDown={_handleSearchKeyDown}
						onSearchQueryChange={_handleSearchQueryChange}
						onSearchSubmit={_handleRefetch}
						searchQuery={searchQuery}
					/>

					<div className="add-result-scroller inline-scroller">
						{loading && (
							<div className="add-result-sheet sheet">
								<div className="sheet-title">
									<div className="load-more-container">
										<ClayLoadingIndicator />
									</div>
								</div>
							</div>
						)}

						{!loading &&
							(_hasResultsToShow()
								? _renderSearchResults()
								: _renderEmptyState())}
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
								disabled={selectedIds.length === 0}
								onClick={_handleSubmit}
							>
								{Liferay.Language.get('add')}
							</ClayButton>
						</ClayButton.Group>
					}
				/>
			</div>
		</ClayModal>
	);
}

AddResultModal.propTypes = {
	fetchDocumentsSearchUrl: PropTypes.string.isRequired,
	onAddResultSubmit: PropTypes.func.isRequired,
	onCloseModal: PropTypes.func.isRequired
};

export default AddResultModal;
