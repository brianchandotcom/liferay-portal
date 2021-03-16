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
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import ClayLink from '@clayui/link';
import ClayList from '@clayui/list';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayManagementToolbar from '@clayui/management-toolbar';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import getCN from 'classnames';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import CodeMirrorEditor from '../shared/CodeMirrorEditor';
import PreviewModal from '../shared/PreviewModal';
import SearchInput from '../shared/SearchInput';
import {sub} from './../utils/utils';

const ERROR_OMIT_KEYS = ['msg', 'severity', 'localizationKey'];
const RESULTS_DEFAULT_KEYS = ['type', 'description', 'date', 'userName'];

function Preview({loading, onClose, onFetchResults, results, visible}) {
	const [value, setValue] = useState('');
	const [activePage, setActivePage] = useState(1);
	const [activeDelta, setActiveDelta] = useState(10);

	const _handleFetch = () => {
		if (value) {
			onFetchResults(value, activeDelta, activePage);
		}
	};

	useEffect(() => {
		_handleFetch();
	}, [activeDelta, activePage]); //eslint-disable-line

	const _renderErrors = () => (
		<ClayList className="preview-error-list text-danger">
			{results.errors.map((error, idx) => (
				<ErrorListItem item={error} key={idx} />
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

	const ResultsManagementBar = () => (
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

			{results.meta && (!results.errors || !results.errors.length) && (
				<ResultsManagementBar />
			)}

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

function ErrorListItem({item}) {
	const [collapse, setCollapse] = useState(false);

	const itemKeys = Object.keys(item);

	return (
		<ClayList.Item flex key={item.msg}>
			<ClayList.ItemField expand>
				<div className="error-title">
					<ClayList.ItemTitle>
						<ClayLabel displayType="danger">
							{item.severity}
						</ClayLabel>
						<span className="text-danger text-truncate">
							{item.msg}
						</span>
					</ClayList.ItemTitle>
				</div>

				{!collapse && itemKeys.length > ERROR_OMIT_KEYS.length && (
					<div className="error-details text-danger">
						{itemKeys.map(
							(property) =>
								!ERROR_OMIT_KEYS.includes(property) && (
									<ClayLayout.Row
										justify="start"
										key={`${item.msg}_${property}`}
									>
										<ClayLayout.Col
											className="semibold"
											size={4}
										>
											{property}
										</ClayLayout.Col>
										<ClayLayout.Col size={8}>
											{typeof item[property] === 'object'
												? JSON.stringify(item[property])
												: item[property]}
										</ClayLayout.Col>
									</ClayLayout.Row>
								)
						)}
					</div>
				)}
			</ClayList.ItemField>

			{itemKeys.length > ERROR_OMIT_KEYS.length && (
				<ClayList.ItemField>
					<ClayButton
						aria-label={
							collapse
								? Liferay.Language.get('expand')
								: Liferay.Language.get('collapse')
						}
						className="text-danger"
						displayType="unstyled"
						onClick={() => setCollapse(!collapse)}
					>
						<ClayIcon
							symbol={collapse ? 'angle-right' : 'angle-down'}
						/>
					</ClayButton>
				</ClayList.ItemField>
			)}
		</ClayList.Item>
	);
}

function ResultListItem({item}) {
	const [collapse, setCollapse] = useState(true);

	const _renderListRow = (property) => (
		<ClayLayout.Row justify="start" key={`${item.id}_${property}`}>
			<ClayLayout.Col className="semibold" size={4}>
				{property}
			</ClayLayout.Col>
			<ClayLayout.Col
				className={getCN({'text-truncate': collapse})}
				size={8}
			>
				{item[property]}
			</ClayLayout.Col>
		</ClayLayout.Row>
	);

	return (
		<ClayList.Item flex key={item.title}>
			<ClayList.ItemField>
				<PreviewModal
					body={
						<CodeMirrorEditor readOnly value={item.explanation} />
					}
					size="lg"
					title={Liferay.Language.get('score-explanation')}
				>
					<ClayButton className="score" displayType="unstyled" small>
						{item.score.toFixed(2)}
					</ClayButton>
				</PreviewModal>
			</ClayList.ItemField>

			<ClayList.ItemField expand>
				<ClayList.ItemTitle>
					<ClayLink href={item.viewURL} target="_blank">
						{item.title}
						<ClayIcon className="shortcut-icon" symbol="shortcut" />
					</ClayLink>
				</ClayList.ItemTitle>

				{RESULTS_DEFAULT_KEYS.map((property) =>
					_renderListRow(property)
				)}

				{!collapse &&
					Object.keys(item).map((property) => {
						if (!RESULTS_DEFAULT_KEYS.includes(property)) {
							return _renderListRow(property);
						}
					})}
			</ClayList.ItemField>

			<ClayList.ItemField>
				<ClayButton
					aria-label={
						collapse
							? Liferay.Language.get('expand')
							: Liferay.Language.get('collapse')
					}
					displayType="unstyled"
					onClick={() => setCollapse(!collapse)}
				>
					<ClayIcon
						symbol={collapse ? 'angle-right' : 'angle-down'}
					/>
				</ClayButton>
			</ClayList.ItemField>
		</ClayList.Item>
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
