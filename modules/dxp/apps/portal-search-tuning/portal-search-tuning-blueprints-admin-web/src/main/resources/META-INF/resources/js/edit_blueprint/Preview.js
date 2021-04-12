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
import ClayEmptyState from '@clayui/empty-state';
import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayManagementToolbar from '@clayui/management-toolbar';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import getCN from 'classnames';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import SearchInput from '../shared/SearchInput';
import useDidUpdateEffect from '../utils/useDidUpdateEffect';
import {sub} from './../utils/utils';
import ErrorListItem from './ErrorListItem';
import ResultListItem from './ResultListItem';

function Preview({
	loading,
	onClose,
	onFetchResults,
	onFocusElement,
	results,
	visible,
}) {
	const [value, setValue] = useState('');
	const [activePage, setActivePage] = useState(1);
	const [activeDelta, setActiveDelta] = useState(10);

	const _handleFetch = () => {
		onFetchResults(value, activeDelta, activePage);
	};

	useDidUpdateEffect(() => {
		_handleFetch();
	}, [activeDelta, activePage]);

	const _renderErrors = () => (
		<ClayList className="preview-error-list">
			{results.errors.map((error, index) => (
				<ErrorListItem
					item={error}
					key={index}
					onFocusElement={onFocusElement}
				/>
			))}
		</ClayList>
	);

	const _renderHits = () => (
		<div className="preview-results-list">
			<ClayList>
				{results.hits.map((result) => (
					<ResultListItem item={result} key={result.id} />
				))}
			</ClayList>

			<ClayPaginationBarWithBasicItems
				activeDelta={activeDelta}
				activePage={activePage}
				ellipsisBuffer={1}
				labels={{
					paginationResults: Liferay.Language.get(
						'showing-x-to-x-of-x-entries'
					),
					perPageItems: Liferay.Language.get('x-entries'),
					selectPerPageItems: '{0}',
				}}
				onDeltaChange={(delta) => {
					setActiveDelta(delta);
					setActivePage(1);
				}}
				onPageChange={setActivePage}
				totalItems={results.meta.totalHits}
			/>
		</div>
	);

	const _renderResultsManagementBar = () => (
		<ClayManagementToolbar>
			<ClayManagementToolbar.ItemList>
				<ClayManagementToolbar.Item>
					<span className="component-text text-truncate-inline">
						<span className="text-truncate">
							{sub(Liferay.Language.get('x-results'), [
								results.meta.totalHits,
							])}
						</span>
					</span>
				</ClayManagementToolbar.Item>

				<ClayManagementToolbar.Item>
					<ClayButton
						aria-label={Liferay.Language.get('refresh')}
						disabled={!value || loading}
						displayType="secondary"
						onClick={_handleFetch}
						small
					>
						{Liferay.Language.get('refresh')}
					</ClayButton>
				</ClayManagementToolbar.Item>
			</ClayManagementToolbar.ItemList>
		</ClayManagementToolbar>
	);

	return (
		<div
			className={getCN('sidebar', 'sidebar-light', 'preview', {
				open: visible,
			})}
		>
			<div className="sidebar-header">
				<h4 className="component-title">
					<span className="text-truncate-inline">
						<span className="text-truncate">
							{Liferay.Language.get('preview')}
						</span>
					</span>
				</h4>

				<ClayButton
					aria-label={Liferay.Language.get('dropdown')}
					displayType="unstyled"
					onClick={onClose}
					small
				>
					<ClayIcon symbol="times" />
				</ClayButton>
			</div>

			<nav
				aria-label="preview-searchbar"
				className="component-tbar sidebar-search tbar"
			>
				<div className="container-fluid">
					<SearchInput onChange={setValue} onEnter={_handleFetch} />
				</div>
			</nav>

			{results.meta &&
				(!results.errors || !results.errors.length) &&
				_renderResultsManagementBar()}

			{!loading ? (
				results.errors && results.errors.length ? (
					_renderErrors()
				) : results.hits && results.hits.length ? (
					_renderHits()
				) : results.meta ? (
					<div className="empty-list-message">
						<ClayEmptyState />
					</div>
				) : (
					<div className="search-message">
						{Liferay.Language.get(
							'perform-a-search-to-preview-your-blueprints-search-results'
						)}
					</div>
				)
			) : (
				<ClayLoadingIndicator />
			)}
		</div>
	);
}

Preview.propTypes = {
	loading: PropTypes.bool,
	onClose: PropTypes.func,
	onFetchResults: PropTypes.func,
	results: PropTypes.object,
	visible: PropTypes.bool,
};

export default React.memo(Preview);
