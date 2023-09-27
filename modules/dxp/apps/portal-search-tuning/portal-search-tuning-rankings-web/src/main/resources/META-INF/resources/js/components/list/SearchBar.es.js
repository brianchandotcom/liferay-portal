/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import {ManagementToolbar} from 'frontend-js-components-web';
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
		selectedIds: PropTypes.arrayOf(String),
	};

	static defaultProps = {
		resultIds: [],
		selectedIds: [],
	};

	_handleAllCheckbox = () => {
		if (this.props.selectedIds.length) {
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

		const unpinnedIds = selectedIds.filter((id) => !dataMap[id].pinned);

		if (unpinnedIds.length) {
			onRemoveSelect(selectedIds.filter((id) => dataMap[id].hidden));

			onClickPin(unpinnedIds, true);
		}
		else {
			onRemoveSelect(selectedIds.filter((id) => dataMap[id].addedResult));

			onClickPin(selectedIds, false);
		}
	};

	/**
	 * Checks if there are any items selected.
	 * @returns {boolean} True if there is at least 1 item selected.
	 */
	_hasSelectedIds = () => !!this.props.selectedIds.length;

	/**
	 * Checks if any selected ids contain any hidden items.
	 * @returns {boolean} True if any selected ids are currently hidden.
	 */
	_isAnyHidden = () => {
		const {dataMap, selectedIds} = this.props;

		return selectedIds.some((id) => dataMap[id].hidden);
	};

	/**
	 * Checks if any selected ids contain any unpinned items.
	 * @returns {boolean} True if any selected ids are currently unpinned.
	 */
	_isAnyUnpinned = () => {
		const {dataMap, selectedIds} = this.props;

		return selectedIds.some((id) => !dataMap[id].pinned);
	};

	render() {
		const {
			fetchDocumentsSearchUrl,
			onAddResultSubmit,
			resultIds,
			selectedIds,
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
				<ManagementToolbar.Container className={classManagementBar}>
					<div className={classNavBarForm}>
						<ManagementToolbar.ItemList>
							<ManagementToolbar.Item>
								<ClayCheckbox
									aria-label={Liferay.Language.get(
										'select-all'
									)}
									checked={this._hasSelectedIds()}
									disabled={!resultIds.length}
									indeterminate={
										!!selectedIds.length &&
										selectedIds.length !== resultIds.length
									}
									onChange={this._handleAllCheckbox}
								/>
							</ManagementToolbar.Item>
						</ManagementToolbar.ItemList>

						{this._hasSelectedIds() && (
							<>
								<ManagementToolbar.ItemList expand>
									<ManagementToolbar.Item>
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
									</ManagementToolbar.Item>
								</ManagementToolbar.ItemList>

								<ManagementToolbar.ItemList>
									<ManagementToolbar.Item>
										<div className="nav-link nav-link-monospaced">
											<ClayButton
												aria-label={
													this._isAnyHidden()
														? Liferay.Language.get(
																'show-result'
														  )
														: Liferay.Language.get(
																'hide-result'
														  )
												}
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
									</ManagementToolbar.Item>

									<ManagementToolbar.Item>
										<div className="nav-link nav-link-monospaced">
											<ClayButton
												aria-label={
													this._isAnyUnpinned()
														? Liferay.Language.get(
																'pin-result'
														  )
														: Liferay.Language.get(
																'unpin-result'
														  )
												}
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
									</ManagementToolbar.Item>

									<ManagementToolbar.Item>
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
									</ManagementToolbar.Item>
								</ManagementToolbar.ItemList>
							</>
						)}

						{!this._hasSelectedIds() && (
							<>
								<ManagementToolbar.ItemList expand>
									{!!resultIds.length && (
										<ManagementToolbar.Item>
											<span className="component-text navbar-text">
												{Liferay.Language.get(
													'select-items'
												)}
											</span>
										</ManagementToolbar.Item>
									)}
								</ManagementToolbar.ItemList>

								{onAddResultSubmit && (
									<ManagementToolbar.ItemList>
										<ManagementToolbar.Item>
											<AddResult
												fetchDocumentsSearchUrl={
													fetchDocumentsSearchUrl
												}
												onAddResultSubmit={
													onAddResultSubmit
												}
											/>
										</ManagementToolbar.Item>
									</ManagementToolbar.ItemList>
								)}
							</>
						)}
					</div>
				</ManagementToolbar.Container>
			</div>
		);
	}
}

export default SearchBar;
