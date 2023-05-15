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
import ClayForm, {
	ClayCheckbox,
	ClayInput,
	ClaySelectWithOption,
} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal from '@clayui/modal';
import {DataRenderers} from '@liferay/frontend-data-set-web';
import {ManagementToolbar} from 'frontend-js-components-web';
import {fetch, navigate, openModal, openToast} from 'frontend-js-web';
import React, {useEffect, useRef, useState} from 'react';

import {API_URL, OBJECT_RELATIONSHIP} from '../Constants';
import {FDSViewSectionInterface} from '../FDSView';
import {FDSViewType} from '../FDSViews';
import {getFields} from '../api';
import OrderableTable from '../components/OrderableTable';

const DATA_RENDERER_LABELS: {[key: string]: string} = {
	actionLink: Liferay.Language.get('action-link'),
	boolean: Liferay.Language.get('boolean'),
	date: Liferay.Language.get('date'),
	dateTime: Liferay.Language.get('date-and-time'),
	default: Liferay.Language.get('default'),
	image: Liferay.Language.get('image'),
	label: Liferay.Language.get('label'),
	link: Liferay.Language.get('link'),
	list: Liferay.Language.get('list'),
	quantitySelector: Liferay.Language.get('quantity-selector'),
	status: Liferay.Language.get('status'),
};

interface IFDSField {
	externalReferenceCode: string;
	id: number;
	label: string;
	name: string;
	renderer: string;
	sortable: boolean;
	type: string;
}

interface IField {
	id: number | null;
	name: string;
	selected: boolean;
	type: string;
	visible: boolean;
}

interface ISaveFDSFieldsModalContentProps {
	closeModal: Function;
	fdsFields: Array<IFDSField>;
	fdsView: FDSViewType;
	namespace: string;
	onSave: Function;
	saveFDSFieldsURL: string;
}

const SaveFDSFieldsModalContent = ({
	closeModal,
	fdsFields,
	fdsView,
	namespace,
	onSave,
	saveFDSFieldsURL,
}: ISaveFDSFieldsModalContentProps) => {
	const [fields, setFields] = useState<Array<IField> | null>(null);
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

	const saveFDSFields = async () => {
		const creationData: Array<{name: string; type: string}> = [];
		const deletionIds: Array<number> = [];

		fields?.forEach((field) => {
			if (field.selected && !field.id) {
				creationData.push({name: field.name, type: field.type});
			}

			if (!field.selected && field.id) {
				deletionIds.push(field.id);
			}
		});

		const formData = new FormData();

		formData.append(
			`${namespace}creationData`,
			JSON.stringify(creationData)
		);

		deletionIds.forEach((id) => {
			formData.append(`${namespace}deletionIds`, String(id));
		});

		formData.append(`${namespace}fdsViewId`, fdsView.id);

		const response = await fetch(saveFDSFieldsURL, {
			body: formData,
			method: 'POST',
		});

		if (!response.ok) {
			openToast({
				message: Liferay.Language.get(
					'your-request-failed-to-complete'
				),
				type: 'danger',
			});

			return;
		}

		const createdFDSFields: Array<IFDSField> = await response.json();

		closeModal();

		openToast({
			message: Liferay.Language.get(
				'your-request-completed-successfully'
			),
			type: 'success',
		});

		onSave({
			createdFDSFields,
			deletedFDSFieldsIds: deletionIds,
		});
	};

	useEffect(() => {
		getFields(fdsView).then((newFields) => {
			if (!newFields) {
				return;
			}

			setFields(
				newFields.map((field) => {
					const fdsField = fdsFields.find(
						(fdsField) => fdsField.name === field.name
					);

					return {
						id: fdsField?.id || null,
						name: field.name,
						selected: Boolean(fdsField),
						type: field.type,
						visible: true,
					};
				})
			);
		});
	}, [fdsFields, fdsView]);

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
						<ClayButton onClick={() => saveFDSFields()}>
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

interface IEditFDSFieldModalContentProps {
	closeModal: Function;
	fdsField: IFDSField;
	namespace: string;
	onSave: Function;
}

const EditFDSFieldModalContent = ({
	closeModal,
	fdsField,
	namespace,
	onSave,
}: IEditFDSFieldModalContentProps) => {
	const [selectedFDSFieldRenderer, setSelectedFDSFieldRenderer] = useState(
		fdsField.renderer ?? 'default'
	);
	const [fdsFieldSortable, setFSDFieldSortable] = useState<boolean>(
		fdsField.sortable ?? true
	);

	const fdsFieldLabelRef = useRef<HTMLInputElement>(null);

	const editFDSField = async () => {
		const body = {
			label: fdsFieldLabelRef.current?.value,
			renderer: selectedFDSFieldRenderer,
			sortable: fdsFieldSortable,
		};

		const response = await fetch(
			`${API_URL.FDS_FIELDS}/by-external-reference-code/${fdsField.externalReferenceCode}`,
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
		}

		const editedFDSField = await response.json();

		closeModal();

		openToast({
			message: Liferay.Language.get(
				'your-request-completed-successfully'
			),
			type: 'success',
		});

		onSave({editedFDSField});
	};

	const fdsFieldNameInputId = `${namespace}fdsFieldNameInput`;
	const fdsFieldLabelInputId = `${namespace}fdsFieldLabelInput`;
	const fdsFieldRendererSelectId = `${namespace}fdsFieldRendererSelectId`;

	return (
		<>
			<ClayModal.Header>
				{Liferay.Util.sub(
					Liferay.Language.get('edit-x'),
					fdsField.label
				)}
			</ClayModal.Header>

			<ClayModal.Body>
				<ClayForm.Group>
					<label htmlFor={fdsFieldNameInputId}>
						{Liferay.Language.get('name')}
					</label>

					<ClayInput
						disabled
						id={fdsFieldNameInputId}
						type="text"
						value={fdsField.name}
					/>
				</ClayForm.Group>

				<ClayForm.Group>
					<label htmlFor={fdsFieldLabelInputId}>
						{Liferay.Language.get('label')}
					</label>

					<ClayInput
						defaultValue={fdsField.label}
						id={fdsFieldLabelInputId}
						ref={fdsFieldLabelRef}
						type="text"
					/>
				</ClayForm.Group>

				<ClayForm.Group>
					<label htmlFor={fdsFieldRendererSelectId}>
						{Liferay.Language.get('cell-renderer')}
					</label>

					<ClaySelectWithOption
						aria-label={Liferay.Language.get('cell-renderer')}
						id={fdsFieldRendererSelectId}
						onChange={(event) => {
							setSelectedFDSFieldRenderer(event.target.value);
						}}
						options={Object.keys(DataRenderers).map(
							(dataRendererId) => ({
								label: DATA_RENDERER_LABELS[dataRendererId],
								value: dataRendererId,
							})
						)}
						value={selectedFDSFieldRenderer}
					/>
				</ClayForm.Group>

				<ClayForm.Group>
					<ClayCheckbox
						checked={fdsFieldSortable}
						label={Liferay.Language.get('sortable')}
						onChange={({target: {checked}}) =>
							setFSDFieldSortable(checked)
						}
					/>
				</ClayForm.Group>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton onClick={() => editFDSField()}>
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
		</>
	);
};

const Fields = ({
	fdsView,
	fdsViewsURL,
	namespace,
	saveFDSFieldsURL,
}: FDSViewSectionInterface) => {
	const [fdsFields, setFDSFields] = useState<Array<IFDSField> | null>(null);

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

			const orderedFDSFields: Array<IFDSField> = [];

			const orderedFDSFieldIds: Array<number> = [];

			storedOrderedFDSFieldIds.forEach((fdsFieldId: string) => {
				storedFDSFields.forEach((storedFDSField: IFDSField) => {
					if (fdsFieldId === String(storedFDSField.id)) {
						orderedFDSFields.push(storedFDSField);

						orderedFDSFieldIds.push(storedFDSField.id);
					}
				});
			});

			storedFDSFields.forEach((storedFDSField: IFDSField) => {
				if (!orderedFDSFieldIds.includes(storedFDSField.id)) {
					orderedFDSFields.push(storedFDSField);
				}
			});

			fdsFieldsOrderRef.current = orderedFDSFieldIds.join(',');

			setFDSFields(orderedFDSFields);
		}
		else {
			fdsFieldsOrderRef.current = storedFDSFields
				.map((storedFDSField: IFDSField) => storedFDSField.id)
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
				<SaveFDSFieldsModalContent
					closeModal={closeModal}
					fdsFields={fdsFields || []}
					fdsView={fdsView}
					namespace={namespace}
					onSave={({
						createdFDSFields,
						deletedFDSFieldsIds,
					}: {
						createdFDSFields: Array<IFDSField>;
						deletedFDSFieldsIds: Array<number>;
					}) => {
						const newFDSFields: Array<IFDSField> = [];

						fdsFields?.forEach((fdsField) => {
							if (!deletedFDSFieldsIds.includes(fdsField.id)) {
								newFDSFields.push(fdsField);
							}
						});

						createdFDSFields.forEach((fdsField) => {
							newFDSFields.push(fdsField);
						});

						setFDSFields(newFDSFields);
					}}
					saveFDSFieldsURL={saveFDSFieldsURL}
				/>
			),
		});

	const onEditFDSField = ({editedFDSField}: {editedFDSField: IFDSField}) => {
		setFDSFields(
			fdsFields?.map((fdsField) => {
				if (fdsField.id === editedFDSField.id) {
					return editedFDSField;
				}

				return fdsField;
			}) || null
		);
	};

	return (
		<ClayLayout.ContainerFluid>
			{fdsFields ? (
				<OrderableTable
					actions={[
						{
							icon: 'pencil',
							label: Liferay.Language.get('edit'),
							onClick: ({item}: {item: IFDSField}) => {
								openModal({
									contentComponent: ({
										closeModal,
									}: {
										closeModal: Function;
									}) => (
										<EditFDSFieldModalContent
											closeModal={closeModal}
											fdsField={item}
											namespace={namespace}
											onSave={onEditFDSField}
										/>
									),
								});
							},
						},
					]}
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
							label: Liferay.Language.get('cell-renderer'),
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
						orderedItems: Array<IFDSField>;
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
