/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayCheckbox, ClayInput} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal from '@clayui/modal';
import {ManagementToolbar} from 'frontend-js-components-web';
import {fetch, navigate, openModal, openToast} from 'frontend-js-web';
import React, {useEffect, useRef, useState} from 'react';

import {API_URL, OBJECT_RELATIONSHIP} from '../Constants';
import {FDSViewSectionInterface} from '../FDSView';
import {FDSViewType} from '../FDSViews';
import OrderableTable from '../components/OrderableTable';

type FDSFieldType = {
	label: string;
	name: string;
	renderer: string;
	sortable: boolean;
	type: string;
};

type FieldType = {
	name: string;
	selected: boolean;
	type: string;
	visible: boolean;
};

interface AddFDSFieldsModalContentInterface {
	closeModal: Function;
	fdsView: FDSViewType;
	onSave: Function;
}

const AddFDSFieldsModalContent = ({
	closeModal,
	fdsView,
	onSave,
}: AddFDSFieldsModalContentInterface) => {
	const [fields, setFields] = useState<Array<FieldType> | null>(null);
	const [query, setQuery] = useState('');

	const onSearch = (query: string) => {
		setQuery(query);

		if (!fields) {
			return;
		}

		const regexp = new RegExp(query, 'i');

		setFields(
			fields.map((field) => ({
				...field,
				visible: Boolean(field.name.match(regexp)),
			}))
		);
	};

	useEffect(() => {
		const getFields = async () => {
			const {restApplication, restSchema} = fdsView[
				OBJECT_RELATIONSHIP.FDS_ENTRY_FDS_VIEW
			];

			const response = await fetch(`/o${restApplication}/openapi.json`);

			if (!response.ok) {
				openToast({
					message: Liferay.Language.get(
						'your-request-failed-to-complete'
					),
					type: 'danger',
				});

				return null;
			}

			const responseJSON = await response.json();

			const properties =
				responseJSON?.components?.schemas[restSchema]?.properties;

			if (!properties) {
				openToast({
					message: Liferay.Language.get(
						'your-request-failed-to-complete'
					),
					type: 'danger',
				});

				return null;
			}

			const fieldsArray: Array<FieldType> = [];

			const isObjectSchema =
				responseJSON.components.schemas[restSchema].xml.name ===
				'ObjectEntry';

			Object.keys(properties).forEach((propertyKey) => {
				const propertyValue = properties[propertyKey];

				if (isObjectSchema && !propertyValue.extensions) {
					return;
				}

				if (propertyKey === 'x-class-name') {
					return;
				}

				const type = propertyValue.type;

				if (type === 'object' || type === 'array') {
					return;
				}

				fieldsArray.push({
					name: propertyKey,
					selected: false,
					type,
					visible: true,
				});
			});

			setFields(fieldsArray);
		};

		getFields();
	}, [fdsView]);

	const isSelectAllChecked = () => {
		if (!fields) {
			return false;
		}

		const selectedFields = fields.filter((field) => field.selected);

		const selectedFieldsCount = selectedFields?.length || 0;

		return selectedFieldsCount === fields.length;
	};

	const isSelectAllIndeterminate = () => {
		if (!fields) {
			return false;
		}

		const selectedFieldsCount =
			fields.filter((field) => field.selected)?.length || 0;

		return selectedFieldsCount > 0 && selectedFieldsCount < fields.length;
	};

	const visibleFields = fields?.filter((field) => field.visible) ?? [];

	return (
		<div className="fds-view-fields-modal">
			<ClayModal.Header>
				{Liferay.Language.get('add-fields')}
			</ClayModal.Header>

			<ClayModal.Body>
				{fields === null ? (
					<ClayLoadingIndicator />
				) : (
					<>
						<ManagementToolbar.Container>
							<ManagementToolbar.ItemList expand>
								<ManagementToolbar.Item className="pr-2">
									<ClayCheckbox
										checked={isSelectAllChecked()}
										indeterminate={isSelectAllIndeterminate()}
										onChange={({target: {checked}}) =>
											setFields(
												fields.map((field) => ({
													...field,
													selected: checked,
												}))
											)
										}
									/>
								</ManagementToolbar.Item>

								<ManagementToolbar.Item className="nav-item-expand">
									<ClayInput.Group>
										<ClayInput.GroupItem>
											<ClayInput
												insetAfter
												onChange={(event) =>
													onSearch(event.target.value)
												}
												placeholder={Liferay.Language.get(
													'search'
												)}
												type="text"
												value={query}
											/>

											<ClayInput.GroupInsetItem
												after
												tag="span"
											>
												<ClayButtonWithIcon
													aria-label={Liferay.Language.get(
														'search'
													)}
													displayType="unstyled"
													symbol="search"
												/>
											</ClayInput.GroupInsetItem>
										</ClayInput.GroupItem>
									</ClayInput.Group>
								</ManagementToolbar.Item>
							</ManagementToolbar.ItemList>
						</ManagementToolbar.Container>

						<div className="fields pb-2 pt-2">
							{visibleFields.length ? (
								visibleFields.map(({name, selected}) => (
									<div
										className="pb-2 pl-3 pr-3 pt-2"
										key={name}
									>
										<ClayCheckbox
											checked={selected}
											label={name}
											onChange={({target: {checked}}) => {
												setFields(
													fields.map((field) =>
														field.name === name
															? {
																	...field,
																	selected: checked,
															  }
															: field
													)
												);
											}}
										/>
									</div>
								))
							) : (
								<div className="pb-2 pl-3 pr-3 pt-2 text-3">
									{Liferay.Language.get('no-results-found')}
								</div>
							)}
						</div>
					</>
				)}
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							onClick={() => {
								closeModal();

								onSave();
							}}
						>
							{Liferay.Language.get('save')}
						</ClayButton>

						<ClayButton
							displayType="secondary"
							onClick={() => closeModal()}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</div>
	);
};

const Fields = ({fdsView, fdsViewsURL}: FDSViewSectionInterface) => {
	const [fdsFields, setFDSFields] = useState<Array<FDSFieldType> | null>(
		null
	);

	const fdsFieldsOrderRef = useRef('');

	const getFDSFields = async () => {
		const response = await fetch(
			`${API_URL.FDS_FIELDS}?filter=(${OBJECT_RELATIONSHIP.FDS_VIEW_FDS_FIELD_ID} eq '${fdsView.id}')&nestedFields=${OBJECT_RELATIONSHIP.FDS_VIEW_FDS_FIELD}`
		);

		if (!response.ok) {
			openToast({
				message: Liferay.Language.get(
					'your-request-failed-to-complete'
				),
				type: 'danger',
			});

			return null;
		}

		const responseJSON = await response.json();

		const storedFDSFields = responseJSON?.items;

		if (!storedFDSFields) {
			openToast({
				message: Liferay.Language.get(
					'your-request-failed-to-complete'
				),
				type: 'danger',
			});

			return null;
		}

		const fdsFieldsOrder =
			storedFDSFields?.[0]?.[OBJECT_RELATIONSHIP.FDS_VIEW_FDS_FIELD]
				?.fdsFieldsOrder;

		if (fdsFieldsOrder) {
			fdsFieldsOrderRef.current = fdsFieldsOrder;

			const storedOrderedFDSFieldIds = fdsFieldsOrder.split(',');

			const orderedFDSFields: Array<FDSFieldType> = [];

			const orderedFDSFieldIds: Array<number> = [];

			storedOrderedFDSFieldIds.forEach((fdsFieldId: string) => {
				storedFDSFields.forEach((storedFDSField: any) => {
					if (fdsFieldId === String(storedFDSField.id)) {
						orderedFDSFields.push(storedFDSField);

						orderedFDSFieldIds.push(storedFDSField.id);
					}
				});
			});

			storedFDSFields.forEach((storedFDSField: any) => {
				if (!orderedFDSFieldIds.includes(storedFDSField.id)) {
					orderedFDSFields.push(storedFDSField);
				}
			});

			fdsFieldsOrderRef.current = orderedFDSFieldIds.join(',');

			setFDSFields(orderedFDSFields);
		}
		else {
			fdsFieldsOrderRef.current = storedFDSFields
				.map((storedFDSField: any) => storedFDSField.id)
				.join(',');

			setFDSFields(storedFDSFields);
		}
	};

	const updateFDSFieldsOrder = async () => {
		const body = {
			fdsFieldsOrder: fdsFieldsOrderRef.current,
		};

		const response = await fetch(
			`${API_URL.FDS_VIEWS}/by-external-reference-code/${fdsView.externalReferenceCode}`,
			{
				body: JSON.stringify(body),
				headers: {
					'Accept': 'application/json',
					'Content-Type': 'application/json',
				},
				method: 'PATCH',
			}
		);

		if (!response.ok) {
			openToast({
				message: Liferay.Language.get(
					'your-request-failed-to-complete'
				),
				type: 'danger',
			});

			return null;
		}

		const responseJSON = await response.json();

		const fdsFieldsOrder = responseJSON?.fdsFieldsOrder;

		if (fdsFieldsOrder && fdsFieldsOrder === fdsFieldsOrderRef.current) {
			openToast({
				message: Liferay.Language.get(
					'your-request-completed-successfully'
				),
				type: 'success',
			});
		}
		else {
			openToast({
				message: Liferay.Language.get(
					'your-request-failed-to-complete'
				),
				type: 'danger',
			});
		}
	};

	useEffect(() => {
		getFDSFields();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const onCreationButtonClick = () =>
		openModal({
			contentComponent: ({closeModal}: {closeModal: Function}) => (
				<AddFDSFieldsModalContent
					closeModal={closeModal}
					fdsView={fdsView}
					onSave={() => getFDSFields()}
				/>
			),
		});

	return (
		<ClayLayout.ContainerFluid>
			{fdsFields ? (
				<OrderableTable
					fields={[
						{
							label: Liferay.Language.get('name'),
							name: 'name',
						},
						{
							label: Liferay.Language.get('label'),
							name: 'label',
						},
						{
							label: Liferay.Language.get('type'),
							name: 'type',
						},
						{
							label: Liferay.Language.get('renderer'),
							name: 'renderer',
						},
						{
							label: Liferay.Language.get('sortable'),
							name: 'sortable',
						},
					]}
					items={fdsFields}
					noItemsButtonLabel={Liferay.Language.get('add-fields')}
					noItemsDescription={Liferay.Language.get(
						'add-fields-to-show-in-your-view'
					)}
					noItemsTitle={Liferay.Language.get('no-fields-added-yet')}
					onCancelButtonClick={() => navigate(fdsViewsURL)}
					onCreationButtonClick={onCreationButtonClick}
					onOrderChange={({
						orderedItems,
					}: {
						orderedItems: Array<any>;
					}) => {
						fdsFieldsOrderRef.current = orderedItems
							.map((item) => item.id)
							.join(',');
					}}
					onSaveButtonClick={() => updateFDSFieldsOrder()}
					title={Liferay.Language.get('fields')}
				/>
			) : (
				<ClayLoadingIndicator />
			)}
		</ClayLayout.ContainerFluid>
	);
};

export default Fields;
