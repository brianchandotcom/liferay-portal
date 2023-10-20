/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayEmptyState from '@clayui/empty-state';
import {ClayCheckbox, ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal, {useModal} from '@clayui/modal';
import ClayTable from '@clayui/table';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {ManagementToolbar} from 'frontend-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

function ContentTypeModal({
	networkStatus,
	initialSelectedTypes = [],
	observer,
	onClose,
	onChange,
	onBlur,
	onSearchableTypesFetch,
	searchableTypes,
}) {
	const [selectedTypes, setSelectedTypes] = useState(initialSelectedTypes);

	const searchableTypesClassNames = searchableTypes.map(
		({className}) => className
	);

	const _handleCancel = () => {
		onClose();
		onBlur();
	};

	const _handleDone = () => {
		onChange(
			searchableTypes.length === selectedTypes.length ? [] : selectedTypes
		);

		_handleCancel();
	};

	const _handleRowCheck = (type) => () => {
		setSelectedTypes(
			selectedTypes.includes(type)
				? selectedTypes.filter((item) => item !== type)
				: [...selectedTypes, type]
		);
	};

	return (
		<ClayModal className="modal-height-xl" observer={observer} size="lg">
			<ClayModal.Header>
				{Liferay.Language.get('select-types')}
			</ClayModal.Header>

			{networkStatus.loading ? (
				<ClayModal.Body className="inline-item">
					<ClayLoadingIndicator displayType="secondary" size="md" />
				</ClayModal.Body>
			) : !!searchableTypes.length && !networkStatus.error ? (
				<>
					<ManagementToolbar.Container
						className={
							!!selectedTypes.length && 'management-bar-primary'
						}
					>
						<div className="navbar-form navbar-form-autofit navbar-overlay">
							<ManagementToolbar.ItemList>
								<ManagementToolbar.Item>
									<ClayCheckbox
										checked={!!selectedTypes.length}
										indeterminate={
											!!selectedTypes.length &&
											selectedTypes.length !==
												searchableTypes.length
										}
										onChange={() =>
											setSelectedTypes(
												!selectedTypes.length
													? searchableTypesClassNames
													: []
											)
										}
									/>
								</ManagementToolbar.Item>

								<ManagementToolbar.Item>
									{selectedTypes.length ? (
										<>
											<span className="component-text">
												{Liferay.Util.sub(
													Liferay.Language.get(
														'x-of-x-selected'
													),
													selectedTypes.length,
													searchableTypes.length
												)}
											</span>

											{selectedTypes.length <
												searchableTypes.length && (
												<ClayButton
													displayType="link"
													onClick={() => {
														setSelectedTypes(
															searchableTypesClassNames
														);
													}}
													small
												>
													{Liferay.Language.get(
														'select-all'
													)}
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

					<ClayModal.Body scrollable>
						<ClayTable>
							<ClayTable.Body>
								{searchableTypes.map(
									({className, displayName}) => {
										const isSelected = selectedTypes.includes(
											className
										);

										return (
											<ClayTable.Row
												active={isSelected}
												key={className}
												onClick={_handleRowCheck(
													className
												)}
											>
												<ClayTable.Cell>
													<ClayCheckbox
														aria-label={Liferay.Util.sub(
															Liferay.Language.get(
																'select-x'
															),
															[displayName]
														)}
														checked={isSelected}
														onChange={_handleRowCheck(
															className
														)}
													/>
												</ClayTable.Cell>

												<ClayTable.Cell
													expanded
													headingTitle
												>
													{displayName}
												</ClayTable.Cell>
											</ClayTable.Row>
										);
									}
								)}
							</ClayTable.Body>
						</ClayTable>
					</ClayModal.Body>
				</>
			) : (
				<ClayModal.Body>
					<ClayEmptyState
						description={Liferay.Language.get(
							'an-error-has-occurred-and-we-were-unable-to-load-the-results'
						)}
						imgSrc="/o/admin-theme/images/states/empty_state.gif"
						title={Liferay.Language.get('no-items-were-found')}
					>
						<ClayButton
							displayType="secondary"
							onClick={onSearchableTypesFetch}
						>
							{Liferay.Language.get('refresh')}
						</ClayButton>
					</ClayEmptyState>
				</ClayModal.Body>
			)}

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={_handleCancel}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							disabled={!selectedTypes.length}
							onClick={_handleDone}
						>
							{Liferay.Language.get('done')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
}

export default function ContentTypeInput({onBlur, onChange, value}) {
	const [searchableTypes, setSearchableTypes] = useState([]);
	const [networkStatus, setNetworkStatus] = useState({
		error: false,
		loading: false,
	});

	const {observer, onOpenChange, open} = useModal();

	const _getInitialSelectedTypes = () =>
		value.length ? value : searchableTypes.map(({className}) => className);

	const _handleClose = () => {
		onOpenChange(false);
	};

	const _handleOpen = () => {
		onOpenChange(true);
	};

	const _handleSelectedTypesFetch = () => {
		setNetworkStatus({error: false, loading: true});

		fetch(
			`/o/search-experiences-rest/v1.0/searchable-asset-names/
            ${Liferay.ThemeDisplay.getBCP47LanguageId()}`,
			{
				headers: new Headers({
					'Accept': 'application/json',
					'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
					'Content-Type': 'application/json',
				}),
				method: 'GET',
			}
		)
			.then((response) =>
				response.json().then((data) => ({
					data,
					ok: response.ok,
				}))
			)
			.then(({data, ok}) => {
				setSearchableTypes(!ok ? [] : data?.items || []);

				setNetworkStatus({error: false, loading: false});
			})
			.catch(() => {
				setNetworkStatus({error: true, loading: false});
			});
	};

	useEffect(() => {
		_handleSelectedTypesFetch();
	}, []);

	return (
		<>
			{open && (
				<ContentTypeModal
					initialSelectedTypes={_getInitialSelectedTypes()}
					networkStatus={networkStatus}
					observer={observer}
					onBlur={onBlur}
					onChange={onChange}
					onClose={_handleClose}
					onSearchableTypesFetch={_handleSelectedTypesFetch}
					searchableTypes={searchableTypes}
				/>
			)}

			<ClayInput.GroupItem>
				<label>
					{Liferay.Language.get('content-type')}

					<ClayTooltipProvider>
						<span
							className="c-ml-2"
							data-tooltip-align="top"
							title={Liferay.Language.get('content-type-help')}
						>
							<ClayIcon symbol="question-circle-full" />
						</span>
					</ClayTooltipProvider>
				</label>

				<ClayInput.Group>
					<ClayButton
						displayType="secondary"
						onClick={_handleOpen}
						size="sm"
					>
						{value.length
							? Liferay.Util.sub(
									Liferay.Language.get('x-selected'),
									value.length
							  )
							: searchableTypes.length
							? Liferay.Util.sub(
									Liferay.Language.get('all-x-selected'),
									searchableTypes.length
							  )
							: Liferay.Language.get('all-selected')}
					</ClayButton>
				</ClayInput.Group>
			</ClayInput.GroupItem>
		</>
	);
}
