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
import ClayEmptyState from '@clayui/empty-state';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal from '@clayui/modal';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import ClayTable from '@clayui/table';
import moment from 'moment';
import React, {useEffect, useState} from 'react';

import ManagementToolbar from './ManagementToolbar';

const DELTAS = [10, 20, 30, 50];
const TRUNCATE_LENGTH = 200;

function formatDate(value) {
	return moment(moment(value, 'YYYYMMDDHHmmss'))
		.locale(Liferay.ThemeDisplay.getBCP47LanguageId() || 'en-US')
		.format('lll');
}

function truncateString(value) {
	return value.length > TRUNCATE_LENGTH
		? value.substring(0, TRUNCATE_LENGTH).concat('...')
		: value;
}

const SelectSXPBlueprintModal = ({observer, onClose, onSubmit, selectedId}) => {
	const [activePage, setActivePage] = useState(1);
	const [delta, setDelta] = useState(20);
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

	const {resource} = useResource({
		fetchOptions: {
			credentials: 'include',
			headers: new Headers({'x-csrf-token': Liferay.authToken}),
			method: 'GET',
		},
		fetchRetry: {
			attempts: 0,
		},
		link: `${window.location.origin}/o/search-experiences-rest/v1.0/sxp-blueprints`,
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

	const _handleChangeSortOrder = () => {
		setSortOrder(sortOrder === 'asc' ? 'desc' : 'asc');
	};

	const _handleSearch = (value) => {
		setSearch(value);
	};

	const _handleSubmit = (id, title) => {
		onSubmit(id, title);

		onClose();
	};

	/**
	 * Handles what is displayed depending on loading/error/results/no results.
	 * @return The JSX to be rendered.
	 */
	const _renderModalBody = () => {

		// Loading

		if (networkState.loading) {
			return <ClayLoadingIndicator className="my-7" />;
		}

		// Error

		if (
			networkState.error ||
			resource?.status === 500 ||
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
					<ClayTable>
						<ClayTable.Head>
							<ClayTable.Row>
								<ClayTable.Cell expanded headingCell>
									{Liferay.Language.get('title')}
								</ClayTable.Cell>

								<ClayTable.Cell expanded headingCell>
									{Liferay.Language.get('description')}
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
							</ClayTable.Row>
						</ClayTable.Head>

						<ClayTable.Body>
							{resource?.items?.map((item) => (
								<ClayTable.Row key={item.id}>
									<ClayTable.Cell headingTitle>
										{item.title}
									</ClayTable.Cell>

									<ClayTable.Cell title={item.description}>
										{truncateString(item.description)}
									</ClayTable.Cell>

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
										<ClayButton
											disabled={
												item.id?.toString() ===
												selectedId?.toString()
											}
											displayType="secondary"
											onClick={() =>
												_handleSubmit(
													item.id,
													item.title
												)
											}
										>
											{item.id?.toString() ===
											selectedId?.toString()
												? Liferay.Language.get(
														'selected'
												  )
												: Liferay.Language.get(
														'select'
												  )}
										</ClayButton>
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
			<ClayEmptyState
				imgProps={{
					alt: Liferay.Language.get('no-results-found'),
					title: Liferay.Language.get('no-results-found'),
				}}
				imgSrc="/o/admin-theme/images/states/empty_state.gif"
				title={Liferay.Language.get('no-results-found')}
			/>
		);
	};

	return (
		<ClayModal observer={observer} size="full-screen">
			<ClayModal.Header>
				{Liferay.Language.get('select-blueprint')}
			</ClayModal.Header>

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

			<ClayModal.Body>{_renderModalBody()}</ClayModal.Body>
		</ClayModal>
	);
};

export default SelectSXPBlueprintModal;
