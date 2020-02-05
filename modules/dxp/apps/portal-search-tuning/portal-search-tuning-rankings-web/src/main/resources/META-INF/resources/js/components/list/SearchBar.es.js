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
import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayManagementToolbar from '@clayui/management-toolbar';
import getCN from 'classnames';
import {PropTypes} from 'prop-types';
import React, {Component} from 'react';

import {getPluralMessage} from '../../utils/language.es';
import AddResult from '../add_result/AddResult.es';
import ItemDropdown from './ItemDropdown.es';

class SearchBar extends Component {
	static propTypes = {
		currentlySearching: PropTypes.bool,

		/**
		 * The data map of id to object it represents. Search bar needs to know
		 * about the dataMap to determine which actions are allowed for the
		 * selected items.
		 */
		dataMap: PropTypes.object.isRequired,
		disableSearch: PropTypes.bool,
		fetchDocumentsSearchUrl: PropTypes.string,
		onAddResultSubmit: PropTypes.func,
		onClickHide: PropTypes.func,
		onClickPin: PropTypes.func,
		onRemoveSelect: PropTypes.func,
		onSearchBarEnter: PropTypes.func,
		onSelectAll: PropTypes.func.isRequired,
		onSelectClear: PropTypes.func.isRequired,
		onUpdateSearchBarTerm: PropTypes.func,
		resultIds: PropTypes.arrayOf(String),
		searchBarTerm: PropTypes.string,
		selectedIds: PropTypes.arrayOf(String)
	};

	static defaultProps = {
		resultIds: [],
		selectedIds: []
	};

	_handleAllCheckbox = () => {
		if (this.props.selectedIds.length > 0) {
			this.props.onSelectClear();
		}
		else {
			this.props.onSelectAll();
		}
	};

	_handleClickHide = () => {
		const {onClickHide, onRemoveSelect, selectedIds} = this.props;

		onRemoveSelect(selectedIds);

		onClickHide(selectedIds, !this._isAnyHidden());
	};

	_handleClickPin = () => {
		const {dataMap, onClickPin, onRemoveSelect, selectedIds} = this.props;

		const unpinnedIds = selectedIds.filter(id => !dataMap[id].pinned);

		if (unpinnedIds.length) {
			onRemoveSelect(selectedIds.filter(id => dataMap[id].hidden));

			onClickPin(unpinnedIds, true);
		}
		else {
			onRemoveSelect(selectedIds.filter(id => dataMap[id].addedResult));

			onClickPin(selectedIds, false);
		}
	};

	/**
	 * Checks if there are any items selected.
	 * @returns {boolean} True if there is at least 1 item selected.
	 */
	_hasSelectedIds = () => this.props.selectedIds.length > 0;

	/**
	 * Checks if any selected ids contain any hidden items.
	 * @returns {boolean} True if any selected ids are currently hidden.
	 */
	_isAnyHidden = () => {
		const {dataMap, selectedIds} = this.props;

		return selectedIds.some(id => dataMap[id].hidden);
	};

	/**
	 * Checks if any selected ids contain any unpinned items.
	 * @returns {boolean} True if any selected ids are currently unpinned.
	 */
	_isAnyUnpinned = () => {
		const {dataMap, selectedIds} = this.props;

		return selectedIds.some(id => !dataMap[id].pinned);
	};

	render() {
		const {
			fetchDocumentsSearchUrl,
			onAddResultSubmit,
			resultIds,
			selectedIds
		} = this.props;

		const classManagementBar = getCN(
			this._hasSelectedIds()
				? 'management-bar-primary'
				: 'management-bar-light'
		);

		const classNavBarForm = getCN(
			'navbar-form',
			'navbar-form-autofit',
			'navbar-overlay'
		);

		return (
			<div className="search-bar-root">
				<ClayManagementToolbar className={classManagementBar}>
					<div className={classNavBarForm}>
						<ClayManagementToolbar.ItemList>
							<ClayManagementToolbar.Item>
								<ClayCheckbox
									aria-label={Liferay.Language.get(
										'select-all'
									)}
									checked={this._hasSelectedIds()}
									disabled={!resultIds.length}
									indeterminate={
										selectedIds.length > 0 &&
										selectedIds.length !== resultIds.length
									}
									onChange={this._handleAllCheckbox}
								/>
							</ClayManagementToolbar.Item>
						</ClayManagementToolbar.ItemList>

						{this._hasSelectedIds() && (
							<>
								<ClayManagementToolbar.ItemList expand>
									<ClayManagementToolbar.Item>
										<span className="navbar-text">
											{getPluralMessage(
												Liferay.Language.get(
													'x-item-selected'
												),
												Liferay.Language.get(
													'x-items-selected'
												),
												selectedIds.length
											)}
										</span>
									</ClayManagementToolbar.Item>
								</ClayManagementToolbar.ItemList>

								<ClayManagementToolbar.ItemList>
									<ClayManagementToolbar.Item>
										<div className="nav-link nav-link-monospaced">
											<ClayButton
												className="btn-outline-borderless component-action"
												displayType="secondary"
												onClick={this._handleClickHide}
												title={
													this._isAnyHidden()
														? Liferay.Language.get(
																'show-result'
														  )
														: Liferay.Language.get(
																'hide-result'
														  )
												}
											>
												<ClayIcon
													symbol={
														this._isAnyHidden()
															? 'view'
															: 'hidden'
													}
												/>
											</ClayButton>
										</div>
									</ClayManagementToolbar.Item>

									<ClayManagementToolbar.Item>
										<div className="nav-link nav-link-monospaced">
											<ClayButton
												className="btn-outline-borderless component-action"
												displayType="secondary"
												onClick={this._handleClickPin}
												title={
													this._isAnyUnpinned()
														? Liferay.Language.get(
																'pin-result'
														  )
														: Liferay.Language.get(
																'unpin-result'
														  )
												}
											>
												{this._isAnyUnpinned() ? (
													<ClayIcon
														key="PIN"
														symbol="pin"
													/>
												) : (
													<ClayIcon
														key="UNPIN"
														symbol="unpin"
													/>
												)}
											</ClayButton>
										</div>
									</ClayManagementToolbar.Item>

									<ClayManagementToolbar.Item>
										<div className="nav-link nav-link-monospaced">
											<ItemDropdown
												hidden={this._isAnyHidden()}
												itemCount={selectedIds.length}
												onClickHide={
													this._handleClickHide
												}
												onClickPin={
													this._handleClickPin
												}
												pinned={!this._isAnyUnpinned()}
											/>
										</div>
									</ClayManagementToolbar.Item>
								</ClayManagementToolbar.ItemList>
							</>
						)}

						{!this._hasSelectedIds() && (
							<>
								<ClayManagementToolbar.ItemList expand>
									{!!resultIds.length && (
										<ClayManagementToolbar.Item>
											<span className="component-text navbar-text">
												{Liferay.Language.get(
													'select-items'
												)}
											</span>
										</ClayManagementToolbar.Item>
									)}
								</ClayManagementToolbar.ItemList>

								{onAddResultSubmit && (
									<ClayManagementToolbar.ItemList>
										<ClayManagementToolbar.Item>
											<AddResult
												fetchDocumentsSearchUrl={
													fetchDocumentsSearchUrl
												}
												onAddResultSubmit={
													onAddResultSubmit
												}
											/>
										</ClayManagementToolbar.Item>
									</ClayManagementToolbar.ItemList>
								)}
							</>
						)}
					</div>
				</ClayManagementToolbar>
			</div>
		);
	}
}

export default SearchBar;
