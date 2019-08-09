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

import AddResult from '../add_result/AddResult.es';
import ClayButton from '@clayui/button';
import ClayCheckbox from '@clayui/checkbox';
import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import ItemDropdown from './ItemDropdown.es';
import React, {Component} from 'react';
import {PropTypes} from 'prop-types';
import {sub} from '../../utils/language.es';

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
		fetchDocumentsUrl: PropTypes.string,
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
		} else {
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
		} else {
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
			fetchDocumentsUrl,
			onAddResultSubmit,
			resultIds,
			selectedIds
		} = this.props;

		const classManagementBar = getCN(
			'management-bar',
			this._hasSelectedIds()
				? 'management-bar-primary'
				: 'management-bar-light',
			'navbar',
			'navbar-expand-md'
		);

		return (
			<div className="search-bar-root">
				<nav className={classManagementBar}>
					<div className="container-fluid container-fluid-max-xl">
						<div className="navbar-form navbar-form-autofit navbar-overlay">
							<ul className="navbar-nav">
								<li className="nav-item">
									<ClayCheckbox
										aria-label={Liferay.Language.get(
											'select-all'
										)}
										checked={this._hasSelectedIds()}
										disabled={!resultIds.length}
										indeterminate={
											selectedIds.length > 0 &&
											selectedIds.length !==
												resultIds.length
										}
										onChange={this._handleAllCheckbox}
									/>
								</li>
							</ul>

							{this._hasSelectedIds() && (
								<>
									<ul className="navbar-nav navbar-nav-expand">
										<li className="nav-item">
											<span className="navbar-text">
												<strong>
													{sub(
														Liferay.Language.get(
															'x-of-x-items-selected'
														),
														[
															selectedIds.length,
															resultIds.length
														]
													)}
												</strong>
											</span>
										</li>
									</ul>

									<ul className="navbar-nav">
										<li className="nav-item">
											<div className="nav-link nav-link-monospaced">
												<ClayButton
													className="btn-outline-borderless component-action"
													displayType="secondary"
													onClick={
														this._handleClickHide
													}
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
										</li>

										<li className="nav-item">
											<div className="nav-link nav-link-monospaced">
												<ClayButton
													className="btn-outline-borderless component-action"
													displayType="secondary"
													onClick={
														this._handleClickPin
													}
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
													<ClayIcon
														symbol={
															this._isAnyUnpinned()
																? 'pin'
																: 'unpin'
														}
													/>
												</ClayButton>
											</div>
										</li>

										<li className="nav-item">
											<div className="nav-link nav-link-monospaced">
												<ItemDropdown
													hidden={this._isAnyHidden()}
													itemCount={
														selectedIds.length
													}
													onClickHide={
														this._handleClickHide
													}
													onClickPin={
														this._handleClickPin
													}
													pinned={
														!this._isAnyUnpinned()
													}
												/>
											</div>
										</li>
									</ul>
								</>
							)}

							{!this._hasSelectedIds() && (
								<>
									<div className="navbar-nav navbar-nav-expand">
										{!!resultIds.length && (
											<li className="nav-item">
												<span className="navbar-text">
													<strong>
														{Liferay.Language.get(
															'select-items'
														)}
													</strong>
												</span>
											</li>
										)}
									</div>

									{onAddResultSubmit && (
										<ul className="navbar-nav">
											<AddResult
												fetchDocumentsUrl={
													fetchDocumentsUrl
												}
												onAddResultSubmit={
													onAddResultSubmit
												}
											/>
										</ul>
									)}
								</>
							)}
						</div>
					</div>
				</nav>
			</div>
		);
	}
}

export default SearchBar;
