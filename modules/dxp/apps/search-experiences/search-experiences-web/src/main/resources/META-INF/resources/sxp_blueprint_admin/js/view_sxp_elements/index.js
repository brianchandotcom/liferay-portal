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
import {useResource} from '@clayui/data-provider';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayEmptyState from '@clayui/empty-state';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayLink from '@clayui/link';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import ClayTable from '@clayui/table';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {DEFAULT_ERROR} from '../utils/constants';
import {sub} from '../utils/language';
import {checkPermission} from '../utils/permissions';
import {formatDate, truncateString} from '../utils/strings';
import {openErrorToast, openSuccessToast} from '../utils/toasts';
import ManagementToolbar from './ManagementToolbar';
import PortletContext from './PortletContext';

const DEFAULT_DELTA = 20;
const DELTAS = [10, 20, 30, 50];
const TRUNCATE_LENGTH = 200;

const ViewSXPElements = ({
	apiURL, // See ViewSXPElementsDisplayContext#getAPIURL
	defaultLocale, // See view_sxp_elements.jsp
	editSXPElementURL, // See view_sxp_elements.jsp
	hasAddSXPElementPermission, // See ViewSXPElementsDisplayContext#hasAddSXPElementPermission
	namespace, // See view_sxp_elements.jsp
}) => {
	const [activePage, setActivePage] = useState(1);
	const [delta, setDelta] = useState(DEFAULT_DELTA);
	const [search, setSearch] = useState('');
	const [sort, setSort] = useState('modifiedDate');
	const [sortOrder, setSortOrder] = useState('desc');

	const [networkState, setNetworkState] = useState(() => ({
		error: false,
		loading: false,
		networkStatus: 4,
	}));

	/**
	 * Immediately show loading spinner whenever a new search is performed.
	 * This is needed otherwise there is a delay before the spinner is shown.
	 */
	useEffect(() => {
		setNetworkState({
			error: false,
			loading: true,
			networkStatus: 4,
		});
	}, [activePage, delta, search, sort, sortOrder]);

	const {refetch, resource} = useResource({
		fetchOptions: {
			credentials: 'include',
			headers: new Headers({'x-csrf-token': Liferay.authToken}),
			method: 'GET',
		},
		fetchRetry: {
			attempts: 0,
		},
		link: `${window.location.origin}${apiURL}`,
		onNetworkStatusChange: (status) => {
			setNetworkState({
				error: status === 5,
				loading: status < 4,
				networkStatus: status,
			});
		},
		variables: {
			page: activePage,
			pageSize: delta,
			search,
			sort: `${sort}:${sortOrder}`,
		},
	});

	const _handleActionDelete = (id, title) => async () => {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			try {
				_setLoading(true);

				await fetch(`${apiURL}/${id}`, {
					method: 'DELETE',
				});

				openSuccessToast({
					message: sub(
						Liferay.Language.get('x-was-deleted-successfully'),
						[title]
					),
				});

				refetch();
			}
			catch {
				openErrorToast();

				_setLoading(false);
			}
		}
	};

	const _handleActionDownload = (id, title) => async () => {
		fetch(`${apiURL}/${id}/export`, {
			method: 'GET',
		})
			.then((response) => {
				if (!response.ok) {
					throw DEFAULT_ERROR;
				}

				return response.blob();
			})
			.then((responseBlob) => {
				const downloadElement = document.createElement('a');

				downloadElement.download = title + '.json';
				downloadElement.href = URL.createObjectURL(responseBlob);

				document.body.appendChild(downloadElement);

				downloadElement.click();

				openSuccessToast();
			})
			.catch(() => {
				openErrorToast();
			});
	};

	const _handleChangeSortOrder = () => {
		setSortOrder(sortOrder === 'asc' ? 'desc' : 'asc');
	};

	const _handleSearch = (value) => {
		setSearch(value);
	};

	const _getEditURL = (id) => {
		const url = new URL(editSXPElementURL);

		url.searchParams.set(`${namespace}sxpElementId`, id);

		return url.toString();
	};

	/**
	 * Used for conditionally rendering dropdown items.
	 * @param {object} item The item returned from the REST API.
	 * @return {Array} items for ClayDropDownWithItems
	 */
	const _getRowActionItems = ({actions, id, readOnly, title}) => {
		const items = [];

		if (checkPermission('update', actions) && !readOnly) {
			items.push({
				href: _getEditURL(id),
				label: Liferay.Language.get('edit'),
				symbolLeft: 'pencil',
			});
		}
		else if (checkPermission('get', actions)) {
			items.push({
				href: _getEditURL(id),
				label: Liferay.Language.get('view'),
				symbolLeft: 'view',
			});
		}

		if (checkPermission('get', actions)) {
			items.push({
				label: Liferay.Language.get('export'),
				onClick: _handleActionDownload(id, title),
				symbolLeft: 'download',
			});
		}

		if (checkPermission('delete', actions)) {
			items.push({
				label: Liferay.Language.get('delete'),
				onClick: _handleActionDelete(id, title),
				symbolLeft: 'trash',
			});
		}

		return items;
	};

	/**
	 * Handles what is displayed depending on loading/error/results/no results.
	 * @return The JSX to be rendered.
	 */
	const _renderDataTable = () => {

		// Loading

		if (networkState.loading) {
			return <ClayLoadingIndicator className="my-7" />;
		}

		// Error

		if (
			networkState.error ||
			resource?.status === 500 ||
			resource?.status === 504 ||
			resource?.status === 400
		) {
			return (
				<ClayEmptyState
					description={Liferay.Language.get(
						'an-error-has-occurred-and-we-were-unable-to-load-the-results'
					)}
					imgProps={{
						alt: Liferay.Language.get('unable-to-load-content'),
						title: Liferay.Language.get('unable-to-load-content'),
					}}
					imgSrc="/o/admin-theme/images/states/empty_state.gif"
					title={Liferay.Language.get('unable-to-load-content')}
				/>
			);
		}

		// Has Results

		if (resource?.totalCount > 0 && resource?.items.length) {
			return (
				<>
					<ClayTable hover={false}>
						<ClayTable.Head>
							<ClayTable.Row>
								<ClayTable.Cell expanded headingCell>
									{Liferay.Language.get('title')}
								</ClayTable.Cell>

								<ClayTable.Cell expanded headingCell>
									{Liferay.Language.get('description')}
								</ClayTable.Cell>

								<ClayTable.Cell headingCell>
									{Liferay.Language.get('id')}
								</ClayTable.Cell>

								<ClayTable.Cell headingCell>
									{Liferay.Language.get('author')}
								</ClayTable.Cell>

								<ClayTable.Cell headingCell>
									{Liferay.Language.get('created')}
								</ClayTable.Cell>

								<ClayTable.Cell headingCell>
									{Liferay.Language.get('modified')}
								</ClayTable.Cell>

								<ClayTable.Cell headingCell></ClayTable.Cell>
							</ClayTable.Row>
						</ClayTable.Head>

						<ClayTable.Body>
							{resource?.items?.map((item) => (
								<ClayTable.Row key={item.id}>
									<ClayTable.Cell headingTitle>
										<ClayLink href={_getEditURL(item.id)}>
											{item.title}
										</ClayLink>
									</ClayTable.Cell>

									{item.description?.length >
									TRUNCATE_LENGTH ? (
										<ClayTable.Cell
											title={item.description}
										>
											{truncateString(
												item.description,
												TRUNCATE_LENGTH
											)}
										</ClayTable.Cell>
									) : (
										<ClayTable.Cell>
											{item.description}
										</ClayTable.Cell>
									)}

									<ClayTable.Cell>{item.id}</ClayTable.Cell>

									<ClayTable.Cell>
										{item.userName}
									</ClayTable.Cell>

									<ClayTable.Cell>
										{formatDate(item.createDate)}
									</ClayTable.Cell>

									<ClayTable.Cell>
										{formatDate(item.modifiedDate)}
									</ClayTable.Cell>

									<ClayTable.Cell align="right">
										<ClayDropDownWithItems
											items={_getRowActionItems(item)}
											trigger={
												<ClayButton
													aria-label={Liferay.Language.get(
														'toggle-dropdown'
													)}
													className="component-action"
													displayType="unstyled"
													monospaced
												>
													<ClayIcon symbol="ellipsis-v" />
												</ClayButton>
											}
										/>
									</ClayTable.Cell>
								</ClayTable.Row>
							))}
						</ClayTable.Body>
					</ClayTable>

					<ClayPaginationBarWithBasicItems
						activeDelta={delta}
						activePage={activePage}
						deltas={DELTAS.map((delta) => ({
							label: delta,
						}))}
						ellipsisBuffer={3}
						onDeltaChange={setDelta}
						onPageChange={setActivePage}
						totalItems={resource?.totalCount || 0}
					/>
				</>
			);
		}

		// No Results

		return (
			<div className="sheet">
				<div className="border-0 pt-0 sheet taglib-empty-result-message">
					<div className="taglib-empty-result-message-header"></div>

					<div className="sheet-text text-center">
						{Liferay.Language.get('no-elements-were-found')}
					</div>
				</div>
			</div>
		);
	};

	const _setLoading = (loading) => {
		setNetworkState({
			error: false,
			loading,
			networkStatus: 4,
		});
	};

	return (
		<PortletContext.Provider
			value={{
				apiURL,
				defaultLocale,
				editSXPElementURL,
				hasAddSXPElementPermission,
				namespace,
			}}
		>
			<ManagementToolbar
				filterItems={[
					{
						items: [
							{
								active: sort === 'modifiedDate',
								label: Liferay.Language.get('modified'),
								onClick: () => setSort('modifiedDate'),
							},
							{
								active: sort === 'createDate',
								label: Liferay.Language.get('created'),
								onClick: () => setSort('createDate'),
							},
						],
						label: Liferay.Language.get('order-by'),
						name: 'order-by',
						type: 'group',
					},
				]}
				loading={networkState.loading}
				onChangeSortOrder={_handleChangeSortOrder}
				onSearch={_handleSearch}
				searchValue={search}
				sortOrder={sortOrder}
				totalCount={resource?.totalCount}
			/>

			<ClayLayout.ContainerFluid view>
				{_renderDataTable()}
			</ClayLayout.ContainerFluid>
		</PortletContext.Provider>
	);
};

export default ViewSXPElements;
