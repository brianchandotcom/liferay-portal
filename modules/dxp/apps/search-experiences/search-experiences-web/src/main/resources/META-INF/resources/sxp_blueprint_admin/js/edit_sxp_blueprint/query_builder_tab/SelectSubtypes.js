/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Body, Cell, Head, Row, Table} from '@clayui/core';
import ClayEmptyState from '@clayui/empty-state';
import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import ClayList from '@clayui/list';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal, {useModal} from '@clayui/modal';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import {ClayTooltipProvider} from '@clayui/tooltip';
import getCN from 'classnames';
import {ManagementToolbar} from 'frontend-js-components-web';
import {addParams, fetch} from 'frontend-js-web';
import React, {
	useCallback,
	useContext,
	useEffect,
	useMemo,
	useState,
} from 'react';

import ThemeContext from '../../shared/ThemeContext';
import removeDuplicates from '../../utils/functions/remove_duplicates';
import sub from '../../utils/language/sub';

export function SearchableSubtypesModal({
	className,
	observer,
	onClose,
	onDone,
	selectedSubtypes,
}) {
	const [selected, setSelected] = useState(selectedSubtypes);
	const [subtypes, setSubtypes] = useState({assetSubtypes: []});

	const [page, setPage] = useState(1);
	const [pageSize, setPageSize] = useState(10);

	const [error, setError] = useState(false);
	const [loading, setLoading] = useState(false);

	const {getAssetSubtypesURL = '', namespace} = useContext(ThemeContext);

	const getLabel = ({assetSubtypeLocalizedName, groupLocalizedName}) => {
		return groupLocalizedName
			? `${assetSubtypeLocalizedName} (${groupLocalizedName})`
			: assetSubtypeLocalizedName;
	};

	const getValue = ({
		assetSubtypeExternalReferenceCode,
		entryClassName,
		groupExternalReferenceCode,
	}) => {
		return `${entryClassName}&&${groupExternalReferenceCode}&&${assetSubtypeExternalReferenceCode}`;
	};

	const isSelected = useCallback(
		(item) =>
			selected.some((selectedItem) => selectedItem.value === item.value),
		[selected]
	);

	const isInAssetSubtypes = useCallback(
		(item) =>
			subtypes.assetSubtypes?.some(
				(subtypeItem) => subtypeItem.value === item.value
			),
		[subtypes.assetSubtypes]
	);

	const allSubtypesSelected = useMemo(
		() => subtypes.assetSubtypes?.every((item) => isSelected(item)),
		[subtypes.assetSubtypes, isSelected]
	);

	const indeterminateSelected = useMemo(
		() => !allSubtypesSelected && subtypes.assetSubtypes?.some(isSelected),
		[allSubtypesSelected, isSelected, subtypes.assetSubtypes]
	);

	const _handleSelect = (item) => () => {
		if (isSelected(item)) {
			setSelected(
				selected.filter(
					(selectedItem) => selectedItem.value !== item.value
				)
			);
		}
		else {
			setSelected([...selected, {label: item.label, value: item.value}]);
		}
	};

	const _handleSelectAll = () => {
		setSelected(
			allSubtypesSelected
				? selected.filter((item) => !isInAssetSubtypes(item))
				: removeDuplicates(
						[
							...selected,
							...subtypes.assetSubtypes.map(({label, value}) => ({
								label,
								value,
							})),
						],
						'value'
					)
		);
	};

	const _handleClear = () => {
		setSelected([]);
	};

	const _handleFetchSubtypes = useCallback(
		(page, pageSize) => {
			setLoading(true);

			try {
				fetch(
					addParams(
						{
							[`${namespace}cmd`]: 'getAssetSubtypes',
							[`${namespace}entryClassName`]: className,
							[`${namespace}page`]: page,
							[`${namespace}pageSize`]: pageSize,
						},
						getAssetSubtypesURL
					)
				)
					.then((response) => response.json())
					.then((items) => {
						setSubtypes({
							...items,
							assetSubtypes: items.assetSubtypes?.map(
								(subtype) => ({
									...subtype,
									label: getLabel(subtype),
									value: getValue(subtype),
								})
							),
						});
						setError(false);
					})
					.catch(() => {
						setTimeout(() => {
							setError(true);
						}, 1000);
					})
					.finally(() => {
						setLoading(false);
					});
			}
			catch (error) {
				setError(true);
				setLoading(false);
			}
		},
		[className, getAssetSubtypesURL, namespace]
	);

	const _handlePageChange = (newPage) => {
		setPage(newPage);

		_handleFetchSubtypes(newPage, pageSize);
	};

	const _handlePageSizeChange = (newPageSize) => {
		setPageSize(newPageSize);
		setPage(1);

		_handleFetchSubtypes(1, newPageSize);
	};

	useEffect(() => {
		_handleFetchSubtypes(page, pageSize);
	}, [_handleFetchSubtypes, page, pageSize]);

	return (
		<ClayModal observer={observer} size="full-screen">
			<ClayModal.Header>
				{Liferay.Language.get('select-subtypes')}
			</ClayModal.Header>

			<ClayModal.Body>
				{loading ? (
					<ClayLoadingIndicator />
				) : error || !subtypes.assetSubtypes?.length ? (
					<ClayEmptyState
						description={
							error
								? Liferay.Language.get(
										'an-error-has-occurred-and-we-were-unable-to-load-the-results'
									)
								: ''
						}
						imgSrc="/o/admin-theme/images/states/empty_state.svg"
						title={Liferay.Language.get('no-items-were-found')}
					>
						<ClayButton
							displayType="secondary"
							onClick={_handleFetchSubtypes}
						>
							{Liferay.Language.get('refresh')}
						</ClayButton>
					</ClayEmptyState>
				) : (
					<>
						<nav
							className={getCN(
								'management-bar navbar navbar-expand-md',
								{
									'management-bar-light': !(
										allSubtypesSelected &&
										indeterminateSelected
									),
									'management-bar-primary navbar-nowrap':
										allSubtypesSelected ||
										indeterminateSelected,
								},
								'border-light',
								'border',
								'rounded-top',
								'border-bottom-0'
							)}
						>
							<ClayLayout.ContainerFluid size={false}>
								<ManagementToolbar.ItemList expand>
									<ManagementToolbar.Item>
										<ClayCheckbox
											aria-label={Liferay.Language.get(
												'select-all'
											)}
											checked={allSubtypesSelected}
											indeterminate={
												indeterminateSelected
											}
											onChange={_handleSelectAll}
										/>
									</ManagementToolbar.Item>

									<ManagementToolbar.Item>
										{!!selected.length && (
											<span className="c-ml-2 component-text">
												{subtypes.totalCount
													? sub(
															Liferay.Language.get(
																'x-of-x-selected'
															),
															[
																selected.length,
																subtypes.totalCount,
															]
														)
													: sub(
															Liferay.Language.get(
																'x-selected'
															),
															[selected.length]
														)}
											</span>
										)}

										<ClayButton
											displayType="link"
											onClick={_handleSelectAll}
											size="sm"
										>
											<span className="component-text">
												{allSubtypesSelected
													? Liferay.Language.get(
															'deselect-all'
														)
													: Liferay.Language.get(
															'select-all'
														)}
											</span>
										</ClayButton>
									</ManagementToolbar.Item>
								</ManagementToolbar.ItemList>
							</ClayLayout.ContainerFluid>
						</nav>

						<Table
							className="rounded-0 table-bordered"
							columnsVisibility={false}
						>
							<Head
								className="rounded-0"
								items={[
									{
										id: 'name',
										name: Liferay.Language.get('name'),
										width: '30%',
									},
									{
										id: 'site',
										name: Liferay.Language.get('site'),
										width: 'auto',
									},
								]}
							>
								{(column) => (
									<Cell
										className={getCN({
											'table-cell-expand': column.expand,
										})}
										key={column.id}
										width={column.width}
									>
										{column.name}
									</Cell>
								)}
							</Head>

							<Body>
								{subtypes.assetSubtypes.map((item) => {
									return (
										<Row
											key={item.value}
											onClick={_handleSelect(item)}
										>
											<Cell>
												<div className="d-flex">
													<ClayCheckbox
														aria-label={sub(
															Liferay.Language.get(
																'select-x'
															),
															[
																item.assetSubtypeLocalizedName,
															]
														)}
														checked={isSelected(
															item
														)}
														onChange={_handleSelect(
															item
														)}
													/>

													<span className="c-ml-2 table-list-title">
														{
															item.assetSubtypeLocalizedName
														}
													</span>
												</div>
											</Cell>

											<Cell>
												<span>
													{item.groupLocalizedName}
												</span>
											</Cell>
										</Row>
									);
								})}
							</Body>
						</Table>

						<ClayPaginationBarWithBasicItems
							active={page}
							activeDelta={pageSize}
							ellipsisBuffer={3}
							ellipsisProps={{
								'aria-label': Liferay.Language.get('more'),
								'title': Liferay.Language.get('more'),
							}}
							onActiveChange={_handlePageChange}
							onDeltaChange={_handlePageSizeChange}
							totalItems={
								subtypes.totalCount ||
								subtypes.assetSubtypes.length
							}
						/>
					</>
				)}
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							borderless
							displayType="secondary"
							onClick={_handleClear}
						>
							{Liferay.Language.get('clear')}
						</ClayButton>

						<ClayButton displayType="secondary" onClick={onClose}>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton onClick={() => onDone(selected)}>
							{Liferay.Language.get('done')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
}

function SelectSubtypes({
	className,
	onChangeSubtypes,
	onRemoveSubtype,
	selectedSubtypes,
}) {
	const {observer, onOpenChange, open} = useModal();

	const _handleOpen = () => {
		onOpenChange(true);
	};

	const _handleClose = () => {
		onOpenChange(false);
	};

	const _handleModalDone = (newValues) => {
		onOpenChange(false);

		onChangeSubtypes(newValues);
	};

	return (
		<>
			<ClayList.ItemText subtext>
				<span className="align-items-center display-flex">
					<ClayButton
						aria-label={Liferay.Language.get('select-subtypes')}
						className="c-p-0 text-secondary"
						displayType="link"
						onClick={_handleOpen}
						size="sm"
					>
						{Liferay.Language.get('select-subtypes')}
					</ClayButton>

					{open && (
						<SearchableSubtypesModal
							className={className}
							observer={observer}
							onClose={_handleClose}
							onDone={_handleModalDone}
							selectedSubtypes={selectedSubtypes}
						/>
					)}

					<ClayTooltipProvider>
						<span
							className="c-ml-2"
							title={Liferay.Language.get('select-subtypes-help')}
						>
							<ClayIcon symbol="question-circle-full" />
						</span>
					</ClayTooltipProvider>
				</span>
			</ClayList.ItemText>

			{!!selectedSubtypes.length && (
				<ClayList.ItemText className="c-mt-2">
					{selectedSubtypes.map(({label, value}) => (
						<ClayLabel
							closeButtonProps={{
								'aria-label': Liferay.Language.get('close'),
								'id': `close-${value}`,
								'onClick': () => onRemoveSubtype(value),
								'title': Liferay.Language.get('close'),
							}}
							displayType="secondary"
							key={value}
							large
						>
							{label}
						</ClayLabel>
					))}
				</ClayList.ItemText>
			)}
		</>
	);
}

export default SelectSubtypes;
