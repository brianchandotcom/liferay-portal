/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayEmptyState from '@clayui/empty-state';
import {ClayCheckbox} from '@clayui/form';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal, {useModal} from '@clayui/modal';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import ClayTable from '@clayui/table';
import {ManagementToolbar} from 'frontend-js-components-web';
import React, {useContext, useEffect, useState} from 'react';

import {DEFAULT_DELTAS} from '../utils/constants';
import addParams from '../utils/fetch/add_params';
import fetchData from '../utils/fetch/fetch_data';
import sub from '../utils/language/sub';
import ThemeContext from './ThemeContext';

const SelectorModal = ({
	deltas,
	fetchItemsURL,
	initialSelected,
	locator,
	multiple,
	namespaceParams,
	observer,
	onClose,
	onSubmit,
	title,
}) => {
	const [activePage, setActivePage] = useState(1);
	const [delta, setDelta] = useState(10);
	const [error, setError] = useState('');
	const [loading, setLoading] = useState(false);
	const [resource, setResource] = useState({items: []});
	const [selected, setSelected] = useState(initialSelected || []);

	const {namespace} = useContext(ThemeContext);

	useEffect(() => {
		setLoading(true);

		const paramPrefix = namespaceParams ? namespace : '';

		fetchData(
			addParams(fetchItemsURL, {
				[`${paramPrefix}page`]: activePage,
				[`${paramPrefix}pageSize`]: delta,
			})
		)
			.then((response) => setResource(response))
			.catch((error) => {
				setError(error);
			})
			.finally(() => {
				setLoading(false);
			});
	}, [activePage, delta, fetchItemsURL, namespace, namespaceParams]);

	const _handleItemsToggle = (items, state) => {
		if (state) {
			const newSelected = items.filter((item) => !_isItemSelected(item));

			setSelected([...selected, ...newSelected]);
		}
		else {
			const itemValues = items.map((item) => String(item[locator.value]));

			setSelected(
				selected.filter(
					(selectedItem) =>
						!itemValues.includes(
							String(selectedItem[locator.value])
						)
				)
			);
		}
	};

	const _handleModalDone = () => {
		onClose();

		onSubmit(selected);
	};

	const _handleRowCheck = (item) => {
		if (multiple) {
			_handleItemsToggle([item], !_isItemSelected(item));
		}
		else {
			onSubmit(item);

			onClose();
		}
	};

	const _isItemSelected = (item) =>
		selected.some(
			(selectedItem) =>
				String(selectedItem[locator.value]) ===
				String(item[locator.value])
		);

	const _renderManagementToolbar = () => {
		if (loading || error) {
			return;
		}

		const itemsOnPageSelected = resource.items.filter((item) =>
			_isItemSelected(item)
		);

		return (
			<ManagementToolbar.Container
				className={!!selected.length && 'management-bar-primary'}
			>
				<div className="navbar-form navbar-form-autofit navbar-overlay">
					<ManagementToolbar.ItemList>
						<ManagementToolbar.Item>
							<ClayCheckbox
								checked={
									itemsOnPageSelected.length ===
									resource.items.length
								}
								indeterminate={
									!!itemsOnPageSelected.length &&
									itemsOnPageSelected.length <
										resource.items.length
								}
								onChange={() =>
									_handleItemsToggle(
										resource.items,
										!itemsOnPageSelected.length
									)
								}
							/>
						</ManagementToolbar.Item>

						<ManagementToolbar.Item>
							{selected.length ? (
								<>
									<span className="component-text">
										{sub(
											Liferay.Language.get(
												'x-of-x-selected'
											),
											[
												selected.length,
												resource[locator.total],
											],
											false
										)}
									</span>

									{itemsOnPageSelected.length <
										resource.items.length && (
										<ClayButton
											displayType="link"
											onClick={() =>
												_handleItemsToggle(
													resource.items,
													true
												)
											}
											small
										>
											{Liferay.Language.get('select-all')}
										</ClayButton>
									)}
								</>
							) : (
								<span className="component-text">
									{Liferay.Language.get('select-all')}
								</span>
							)}
						</ManagementToolbar.Item>
					</ManagementToolbar.ItemList>
				</div>
			</ManagementToolbar.Container>
		);
	};

	/**
	 * Handles what is displayed depending on loading/error/results/no results.
	 * @return The JSX to be rendered.
	 */
	const _renderModalBody = () => {

		// Loading

		if (loading) {
			return <ClayLoadingIndicator className="my-7" />;
		}

		// Error

		if (error) {
			return (
				<ClayEmptyState
					description={Liferay.Language.get(
						'an-error-has-occurred-and-we-were-unable-to-load-the-results'
					)}
					imgProps={{
						alt: Liferay.Language.get('unable-to-load-content'),
						title: Liferay.Language.get('unable-to-load-content'),
					}}
					imgSrc={`${Liferay.ThemeDisplay.getPathThemeImages()}/states/empty_state.gif`}
					title={Liferay.Language.get('unable-to-load-content')}
				/>
			);
		}

		// Has Results

		if (resource.items?.length) {
			return (
				<>
					<ClayTable>
						<ClayTable.Head>
							<ClayTable.Row>
								{multiple && <ClayTable.Cell />}

								<ClayTable.Cell expanded headingCell>
									{Liferay.Language.get('name')}
								</ClayTable.Cell>
							</ClayTable.Row>
						</ClayTable.Head>

						<ClayTable.Body>
							{resource.items?.map((item) => {
								const isSelected = _isItemSelected(item);

								return (
									<ClayTable.Row
										active={isSelected}
										key={item[locator.value]}
										onClick={() => _handleRowCheck(item)}
										style={{cursor: 'pointer'}}
									>
										{multiple && (
											<ClayTable.Cell>
												<ClayCheckbox
													aria-label={sub(
														Liferay.Language.get(
															'select-x'
														),
														[item[locator.label]]
													)}
													checked={isSelected}
													onChange={() =>
														_handleRowCheck(item)
													}
												/>
											</ClayTable.Cell>
										)}

										<ClayTable.Cell expanded headingTitle>
											{item[locator.label]}
										</ClayTable.Cell>

										{!multiple && (
											<ClayTable.Cell align="right">
												<ClayButton
													disabled={isSelected}
													displayType="secondary"
													onClick={() =>
														_handleRowCheck(item)
													}
												>
													{isSelected
														? Liferay.Language.get(
																'selected'
														  )
														: Liferay.Language.get(
																'select'
														  )}
												</ClayButton>
											</ClayTable.Cell>
										)}
									</ClayTable.Row>
								);
							})}
						</ClayTable.Body>
					</ClayTable>

					<ClayPaginationBarWithBasicItems
						active={activePage}
						activeDelta={delta}
						deltas={deltas}
						ellipsisBuffer={3}
						onActiveChange={setActivePage}
						onDeltaChange={setDelta}
						totalItems={resource[locator.total] || 0}
					/>
				</>
			);
		}

		// No Results

		return (
			<ClayEmptyState
				description={Liferay.Language.get(
					'sorry,-no-results-were-found'
				)}
				imgProps={{
					alt: Liferay.Language.get('no-results-found'),
					title: Liferay.Language.get('no-results-found'),
				}}
				imgSrc={`${Liferay.ThemeDisplay.getPathThemeImages()}/states/empty_state.gif`}
				title={Liferay.Language.get('no-results-found')}
			/>
		);
	};

	return (
		<ClayModal observer={observer} size={multiple ? 'lg' : 'full-screen'}>
			<ClayModal.Header>{title}</ClayModal.Header>

			{multiple && _renderManagementToolbar()}

			<ClayModal.Body>{_renderModalBody()}</ClayModal.Body>

			{multiple && (
				<ClayModal.Footer
					last={
						<ClayButton.Group spaced>
							<ClayButton
								displayType="secondary"
								onClick={onClose}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton onClick={_handleModalDone}>
								{Liferay.Language.get('done')}
							</ClayButton>
						</ClayButton.Group>
					}
				/>
			)}
		</ClayModal>
	);
};

export default function ({
	children,
	deltas = DEFAULT_DELTAS,
	fetchItemsURL,
	initialSelected,
	locator,
	namespaceParams = true,
	multiple = false,
	onSubmit,
	title,
}) {
	const {observer, onOpenChange, open} = useModal();

	const _handleClick = () => {
		if (fetchItemsURL) {
			onOpenChange(true);
		}
	};

	const _handleSubmit = (item) => {
		onSubmit(item);

		onOpenChange(false);
	};

	return (
		<>
			{open && (
				<SelectorModal
					deltas={deltas}
					fetchItemsURL={fetchItemsURL}
					initialSelected={initialSelected}
					locator={locator}
					multiple={multiple}
					namespaceParams={namespaceParams}
					observer={observer}
					onClose={() => onOpenChange(false)}
					onSubmit={_handleSubmit}
					title={title}
				/>
			)}

			<span onClick={_handleClick}>{children}</span>
		</>
	);
}
