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
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {PropTypes} from 'prop-types';
import React, {PureComponent} from 'react';
import {DragDropContext as dragDropContext} from 'react-dnd';
import HTML5Backend from 'react-dnd-html5-backend';

import {KEY_CODES} from '../../utils/constants.es';
import {isNull, toggleListItem} from '../../utils/util.es';
import ClayEmptyState, {DISPLAY_STATES} from '../shared/ClayEmptyState.es';
import ErrorBoundary from '../shared/ErrorBoundary.es';
import Item from './Item.es';
import ItemDragLayer from './ItemDragLayer.es';
import SearchBar from './SearchBar.es';

class List extends PureComponent {
	static propTypes = {
		dataLoading: PropTypes.bool,
		dataMap: PropTypes.object,
		displayError: PropTypes.bool,
		fetchDocumentsSearchUrl: PropTypes.string,
		onAddResultSubmit: PropTypes.func,
		onClickHide: PropTypes.func,
		onClickPin: PropTypes.func,
		onLoadResults: PropTypes.func,
		onMove: PropTypes.func,
		resultIds: PropTypes.arrayOf(Number),
		resultIdsPinned: PropTypes.arrayOf(Number),
		showLoadMore: PropTypes.bool
	};

	static defaultProps = {
		dataLoading: false,
		resultIds: []
	};

	state = {
		focusIndex: null,
		reorder: false,
		selectedIds: []
	};

	_handleItemBlur = () => {
		this._handleItemFocus(null);
		this._handleReorder(false);
	};

	_handleItemFocus = index => {
		this.setState({focusIndex: index});
	};

	/**
	 * Will trigger the KeyDownFocus as long as focusIndex is defined.
	 */
	_handleKeyDown = event => {
		if (!isNull(this.state.focusIndex)) {
			this._handleKeyDownFocus(event);
		}
	};

	/**
	 * Defines the keyboard shortcuts. If reorder is true, it will move
	 * pinned items up or down. If reorder is false, it will scroll through
	 * all items.
	 */
	_handleKeyDownFocus = event => {
		const {onMove, resultIds, resultIdsPinned} = this.props;

		const {focusIndex, reorder} = this.state;

		const pinLength = resultIdsPinned ? resultIdsPinned.length : 0;

		if (event.key === KEY_CODES.SPACE || event.key == KEY_CODES.ENTER) {
			event.preventDefault();

			this._handleReorder(!reorder && focusIndex < pinLength);
		}
		else if (event.key === KEY_CODES.ARROW_DOWN) {
			event.preventDefault();

			if (focusIndex + 1 < resultIds.length) {
				if (!(reorder && focusIndex + 1 === pinLength)) {
					if (reorder && focusIndex + 1 < pinLength) {
						onMove(focusIndex, focusIndex + 2);
					}

					this._handleItemFocus(focusIndex + 1);
				}
			}
		}
		else if (event.key === KEY_CODES.ARROW_UP) {
			event.preventDefault();

			if (focusIndex > 0) {
				if (reorder) {
					onMove(focusIndex, focusIndex - 1);
				}

				this._handleItemFocus(focusIndex - 1);
			}
		}
	};

	_handleLoadMoreResults = () => {
		this.props.onLoadResults();
	};

	/**
	 * Used in case where pinning/hiding needs to remove itself from the
	 * selected ids list.
	 */
	_handleRemoveSelect = ids => {
		this.setState(state => ({
			selectedIds: state.selectedIds.filter(id => !ids.includes(id))
		}));
	};

	_handleReorder = val => {
		this.setState({reorder: val});
	};

	_handleSelect = id => {
		this.setState(state => ({
			selectedIds: toggleListItem(state.selectedIds, id)
		}));
	};

	/**
	 * Clears the selected items. Useful for clearing the selection after
	 * hiding items.
	 */
	_handleSelectClear = () => {
		this.setState({selectedIds: []});
	};

	_handleSelectAll = () => {
		this.setState({selectedIds: this.props.resultIds});
	};

	_handleTabSelect = (index, lastIndex) => {
		if (index !== lastIndex) {
			this.setState({selectedIds: []});
		}
	};

	/**
	 * Handles the pin action. Updates the focus index to keep the same item
	 * focused.
	 * @param {Array} ids The list of ids to pin.
	 * @param {boolean} pinned Set ids to pin or unpinned.
	 */
	_handleClickPin = (ids, pinned) => {
		this.props.onClickPin(ids, pinned);

		if (!isNull(this.state.focusIndex)) {
			this.setState((state, props) => {
				const newFocusIndex = props.resultIds.indexOf(ids[0]);

				return {focusIndex: newFocusIndex > -1 ? newFocusIndex : null};
			});
		}
	};

	/**
	 * Render the item. If the item id isn't found on the dataMap, nothing
	 * will be rendered for the item.
	 * @param {string} id The item id.
	 * @param {number} index The item's position in the list.
	 */
	_renderItem = (id, index) => {
		const {dataMap, onClickHide, onMove} = this.props;

		const {focusIndex, reorder, selectedIds} = this.state;

		const item = dataMap[id];

		return item ? (
			<Item
				addedResult={item.addedResult}
				author={item.author}
				clicks={item.clicks}
				date={item.date}
				description={item.description}
				focus={index === focusIndex}
				hidden={item.hidden}
				icon={item.icon}
				id={item.id}
				index={index}
				key={item.id}
				onBlur={this._handleItemBlur}
				onClickHide={onClickHide}
				onClickPin={this._handleClickPin}
				onFocus={this._handleItemFocus}
				onMove={onMove}
				onRemoveSelect={this._handleRemoveSelect}
				onSelect={this._handleSelect}
				pinned={item.pinned}
				reorder={index === focusIndex && reorder}
				selected={selectedIds.includes(item.id)}
				title={item.title}
				type={item.type}
			/>
		) : null;
	};

	render() {
		const {
			dataLoading,
			dataMap,
			displayError,
			fetchDocumentsSearchUrl,
			onAddResultSubmit,
			onClickHide,
			onClickPin,
			resultIds,
			showLoadMore
		} = this.props;

		const {selectedIds} = this.state;

		return (
			<div className="result-ranking-list-root">
				<ItemDragLayer />

				<SearchBar
					dataMap={dataMap}
					fetchDocumentsSearchUrl={fetchDocumentsSearchUrl}
					onAddResultSubmit={onAddResultSubmit}
					onClickHide={onClickHide}
					onClickPin={onClickPin}
					onRemoveSelect={this._handleRemoveSelect}
					onSelectAll={this._handleSelectAll}
					onSelectClear={this._handleSelectClear}
					resultIds={resultIds}
					selectedIds={selectedIds}
				/>

				<ErrorBoundary toast>
					{!!resultIds.length && (
						<ul
							className="list-group show-quick-actions-on-hover"
							onKeyDown={this._handleKeyDown}
						>
							{resultIds.map((id, index, arr) =>
								this._renderItem(id, index, arr)
							)}
						</ul>
					)}

					{dataLoading && (
						<div className="load-more-container">
							<ClayLoadingIndicator />
						</div>
					)}

					{!dataLoading && (
						<>
							{!displayError && !resultIds.length && (
								<ClayEmptyState />
							)}

							{displayError && (
								<ClayEmptyState
									actionLabel={Liferay.Language.get(
										'try-again'
									)}
									description={Liferay.Language.get(
										'an-error-has-occurred-and-we-were-unable-to-load-the-results'
									)}
									displayState={DISPLAY_STATES.EMPTY}
									onClickAction={this._handleLoadMoreResults}
									title={Liferay.Language.get(
										'unable-to-load-content'
									)}
								/>
							)}

							{showLoadMore && (
								<div className="load-more-container">
									<ClayButton
										className="load-more-button"
										displayType="secondary"
										onClick={this._handleLoadMoreResults}
									>
										{Liferay.Language.get(
											'load-more-results'
										)}
									</ClayButton>
								</div>
							)}
						</>
					)}
				</ErrorBoundary>
			</div>
		);
	}
}

export default dragDropContext(HTML5Backend)(List);
