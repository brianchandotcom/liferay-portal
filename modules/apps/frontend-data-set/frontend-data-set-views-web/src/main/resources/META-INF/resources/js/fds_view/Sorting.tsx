/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm, {ClayCheckbox, ClaySelectWithOption} from '@clayui/form';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal from '@clayui/modal';
import {InputLocalized} from 'frontend-js-components-web';
import {fetch, openModal} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {IFDSViewSectionProps} from '../FDSView';
import {FDSViewType} from '../FDSViews';
import {getFields} from '../api';
import OrderableTable from '../components/OrderableTable';
import RequiredMark from '../components/RequiredMark';
import {API_URL, OBJECT_RELATIONSHIP} from '../utils/constants';
import openDefaultFailureToast from '../utils/openDefaultFailureToast';
import openDefaultSuccessToast from '../utils/openDefaultSuccessToast';
import {IField} from '../utils/types';

interface IAddFDSSortModalContentInterface {
	closeModal: Function;
	fdsView: FDSViewType;
	fields: IField[];
	namespace: string;
	onSave: (newSort: IFDSSort) => void;
}

interface IContentRendererProps {
	item: IFDSSort;
	query: string;
}

interface IFDSSort {
	default: boolean;
	externalReferenceCode: string;
	fieldName: string;
	id: number;
	label: string;
	label_i18n: Liferay.Language.LocalizedValue<string>;
	orderType: string;
}

const ORDER_TYPE = {
	ASCENDING: {
		label: Liferay.Language.get('ascending'),
		value: 'asc',
	},
	DESCENDING: {
		label: Liferay.Language.get('descending'),
		value: 'desc',
	},
};

const ORDER_TYPE_OPTIONS = [ORDER_TYPE.ASCENDING, ORDER_TYPE.DESCENDING];

const DefaultComponent = ({item}: IContentRendererProps) => {
	return (
		<ClayLabel displayType={item.default ? 'success' : 'secondary'}>
			{item.default
				? Liferay.Language.get('yes')
				: Liferay.Language.get('no')}
		</ClayLabel>
	);
};

const AddFDSSortModalContent = ({
	closeModal,
	fdsView,
	fields,
	namespace,
	onSave,
}: IAddFDSSortModalContentInterface) => {
	const [labelI18n, setLabelI18n] = useState<
		Liferay.Language.LocalizedValue<string>
	>({});
	const [saveButtonDisabled, setSaveButtonDisabled] = useState(false);
	const [selectedFieldName, setSelectedFieldName] = useState<string>('');
	const [selectedOrderType, setSelectedOrderType] = useState<string>(
		ORDER_TYPE.ASCENDING.value
	);
	const [useAsDefaultSorting, setUseAsDefaultSorting] = useState(false);

	const handleSave = async () => {
		setSaveButtonDisabled(true);

		const field = fields.find(
			(item: IField) => item.name === selectedFieldName
		);

		if (!field) {
			openDefaultFailureToast();

			return;
		}

		const response = await fetch(API_URL.FDS_SORTS, {
			body: JSON.stringify({
				[OBJECT_RELATIONSHIP.FDS_VIEW_FDS_SORT_ID]: fdsView.id,
				default: useAsDefaultSorting,
				fieldName: selectedFieldName,
				label_i18n: labelI18n,
				orderType: selectedOrderType,
			}),
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json',
			},
			method: 'POST',
		});

		if (!response.ok) {
			setSaveButtonDisabled(false);

			openDefaultFailureToast();

			return;
		}

		const responseJSON = await response.json();

		openDefaultSuccessToast();

		onSave(responseJSON);

		closeModal();
	};

	const fdsSortLabelInput = `${namespace}fdsSortLabelInput`;
	const fdsSortFieldNameInputId = `${namespace}fdsSortFieldNameInput`;
	const fdsSortOrderTypeInputId = `${namespace}fdsSortOrderTypeInput`;

	return (
		<>
			<ClayModal.Header>
				{Liferay.Language.get('new-sort')}
			</ClayModal.Header>

			<ClayModal.Body>
				<p className="text-secondary">
					{Liferay.Language.get(
						'create-a-sorting-option-for-the-dataset-fragment.-add-a-label-name-and-choose-a-field-to-be-displayed-in-the-sorting-dropdown'
					)}
				</p>

				<InputLocalized
					id={fdsSortLabelInput}
					label={Liferay.Language.get('label')}
					name="label"
					onChange={setLabelI18n}
					placeholder={Liferay.Language.get('add-a-label')}
					required
					translations={labelI18n}
				/>

				<ClayForm.Group>
					<label htmlFor={fdsSortFieldNameInputId}>
						{Liferay.Language.get('sort-by')}

						<RequiredMark />
					</label>

					<ClaySelectWithOption
						aria-label={Liferay.Language.get('sort-by')}
						name={fdsSortFieldNameInputId}
						onChange={(event) => {
							setSelectedFieldName(event.target.value);
						}}
						options={[
							{
								disabled: true,
								label: Liferay.Language.get('choose-an-option'),
								value: '',
							},
							...fields.map((item) => ({
								label: item.label,
								value: item.name,
							})),
						]}
						title={Liferay.Language.get('sort-by')}
						value={selectedFieldName}
					/>
				</ClayForm.Group>

				<ClayForm.Group>
					<ClayCheckbox
						aria-label={Liferay.Language.get(
							'use-as-default-sorting'
						)}
						checked={useAsDefaultSorting}
						inline
						label={Liferay.Language.get('use-as-default-sorting')}
						onChange={() =>
							setUseAsDefaultSorting((value: boolean) => !value)
						}
					/>
				</ClayForm.Group>

				{useAsDefaultSorting && (
					<ClayForm.Group>
						<label htmlFor={fdsSortOrderTypeInputId}>
							{Liferay.Language.get('order-type')}

							<RequiredMark />
						</label>

						<ClaySelectWithOption
							aria-label={Liferay.Language.get('order-type')}
							id={fdsSortOrderTypeInputId}
							onChange={(event) =>
								setSelectedOrderType(event.target.value)
							}
							options={ORDER_TYPE_OPTIONS}
						/>
					</ClayForm.Group>
				)}
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							disabled={
								saveButtonDisabled ||
								!selectedFieldName ||
								!labelI18n[
									Liferay.ThemeDisplay.getDefaultLanguageId()
								]
							}
							onClick={handleSave}
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
		</>
	);
};

interface IEditFDSSortModalContentProps {
	closeModal: Function;
	fdsSort: IFDSSort;
	fields: IField[];
	namespace: string;
	onSave: Function;
}

const EditFDSSortModalContent = ({
	closeModal,
	fdsSort,
	fields,
	namespace,
	onSave,
}: IEditFDSSortModalContentProps) => {
	const [labelI18n, setLabelI18n] = useState<
		Liferay.Language.LocalizedValue<string>
	>(fdsSort.label_i18n);
	const [saveButtonDisabled, setSaveButtonDisabled] = useState(false);
	const [selectedFieldName, setSelectedFieldName] = useState<string>(
		fdsSort.fieldName
	);
	const [selectedOrderType, setSelectedOrderType] = useState(
		fdsSort.orderType
	);
	const [useAsDefaultSorting, setUseAsDefaultSorting] = useState(
		fdsSort.default
	);

	const handleSave = async () => {
		setSaveButtonDisabled(true);

		const response = await fetch(
			`${API_URL.FDS_SORTS}/by-external-reference-code/${fdsSort.externalReferenceCode}`,
			{
				body: JSON.stringify({
					default: useAsDefaultSorting,
					fieldName: selectedFieldName,
					label_i18n: labelI18n,
					orderType: selectedOrderType,
				}),
				headers: {
					'Accept': 'application/json',
					'Content-Type': 'application/json',
				},
				method: 'PATCH',
			}
		);

		if (!response.ok) {
			setSaveButtonDisabled(false);

			openDefaultFailureToast();

			return;
		}

		const editedFDSSort = await response.json();

		closeModal();

		openDefaultSuccessToast();

		onSave({editedFDSSort});
	};

	const fdsSortLabelInput = `${namespace}fdsSortLabelInput`;
	const fdsSortFieldNameInputId = `${namespace}fdsSortFieldNameInput`;
	const fdsSortOrderTypeInputId = `${namespace}fdsSortOrderTypeInput`;

	return (
		<>
			<ClayModal.Header>
				{Liferay.Util.sub(
					Liferay.Language.get('edit-x-sorting'),
					fdsSort.label
				)}
			</ClayModal.Header>

			<ClayModal.Body>
				<ClayForm.Group>
					<p className="text-secondary">
						{Liferay.Language.get(
							'create-a-sorting-option-for-the-dataset-fragment.-add-a-label-name-and-choose-a-field-to-be-displayed-in-the-sorting-dropdown'
						)}
					</p>

					<InputLocalized
						id={fdsSortLabelInput}
						label={Liferay.Language.get('label')}
						name="label"
						onChange={setLabelI18n}
						placeholder={Liferay.Language.get('add-a-label')}
						required
						translations={labelI18n}
					/>

					<label htmlFor={fdsSortFieldNameInputId}>
						{Liferay.Language.get('sort-by')}

						<RequiredMark />
					</label>

					<ClaySelectWithOption
						aria-label={Liferay.Language.get('sort-by')}
						name={fdsSortFieldNameInputId}
						onChange={(event) => {
							setSelectedFieldName(event.target.value);
						}}
						options={[
							{
								disabled: true,
								label: Liferay.Language.get('choose-an-option'),
								value: '',
							},
							...fields.map((item) => ({
								label: item.label,
								value: item.name,
							})),
						]}
						title={Liferay.Language.get('sort-by')}
						value={selectedFieldName}
					/>
				</ClayForm.Group>

				<ClayForm.Group>
					<ClayCheckbox
						aria-label={Liferay.Language.get(
							'use-as-default-sorting'
						)}
						checked={useAsDefaultSorting}
						inline
						label={Liferay.Language.get('use-as-default-sorting')}
						onChange={() =>
							setUseAsDefaultSorting((value: boolean) => !value)
						}
					/>
				</ClayForm.Group>

				{useAsDefaultSorting && (
					<ClayForm.Group>
						<label htmlFor={fdsSortOrderTypeInputId}>
							{Liferay.Language.get('order-type')}

							<RequiredMark />
						</label>

						<ClaySelectWithOption
							aria-label={Liferay.Language.get('order-type')}
							id={fdsSortOrderTypeInputId}
							onChange={(event) =>
								setSelectedOrderType(event.target.value)
							}
							options={ORDER_TYPE_OPTIONS}
							value={selectedOrderType}
						/>
					</ClayForm.Group>
				)}
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							disabled={
								saveButtonDisabled ||
								!selectedFieldName ||
								!labelI18n[
									Liferay.ThemeDisplay.getDefaultLanguageId()
								]
							}
							onClick={handleSave}
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
		</>
	);
};

const Sorting = ({fdsView, namespace}: IFDSViewSectionProps) => {
	const [fields, setFields] = React.useState<IField[]>([]);
	const [fdsSorts, setFDSSorts] = useState<Array<IFDSSort>>([]);
	const [loading, setLoading] = useState(true);

	useEffect(() => {
		const getFDSSort = async () => {
			const response = await fetch(
				`${API_URL.FDS_SORTS}?filter=(${OBJECT_RELATIONSHIP.FDS_VIEW_FDS_SORT_ID} eq '${fdsView.id}')&nestedFields=${OBJECT_RELATIONSHIP.FDS_VIEW_FDS_SORT}&sort=dateCreated:desc`
			);

			const responseJSON = await response.json();

			const storedFDSSorts: IFDSSort[] = responseJSON.items;

			let ordered = storedFDSSorts;
			let notOrdered: IFDSSort[] = [];

			if (responseJSON.fdsSortsOrder) {
				const fdsSortsOrderArray = responseJSON.fdsSortsOrder.split(
					','
				) as string[];

				ordered = fdsSortsOrderArray
					.map((fdsSortId) =>
						storedFDSSorts.find(
							(fdsSort) => fdsSort.id === Number(fdsSortId)
						)
					)
					.filter(Boolean) as IFDSSort[];

				notOrdered = storedFDSSorts.filter(
					(filter) => !fdsSortsOrderArray.includes(String(filter.id))
				);
			}

			setFDSSorts([...notOrdered, ...ordered]);

			setLoading(false);
		};

		getFDSSort();

		getFields(fdsView).then((newFields) => {
			if (newFields) {
				setFields(newFields);
			}
		});
	}, [fdsView]);

	const handleCreation = () =>
		openModal({
			contentComponent: ({closeModal}: {closeModal: Function}) => (
				<AddFDSSortModalContent
					closeModal={closeModal}
					fdsView={fdsView}
					fields={fields}
					namespace={namespace}
					onSave={(newSort) => setFDSSorts([...fdsSorts, newSort])}
				/>
			),
		});

	const handleDelete = ({item}: {item: IFDSSort}) => {
		openModal({
			bodyHTML: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-this-sorting?-fragments-using-it-will-be-affected'
			),
			buttons: [
				{
					autoFocus: true,
					displayType: 'secondary',
					label: Liferay.Language.get('cancel'),
					type: 'cancel',
				},
				{
					displayType: 'danger',
					label: Liferay.Language.get('delete'),
					onClick: async ({
						processClose,
					}: {
						processClose: Function;
					}) => {
						processClose();

						const url = `${API_URL.FDS_SORTS}/${item.id}`;

						const response = await fetch(url, {
							method: 'DELETE',
						});

						if (!response.ok) {
							openDefaultFailureToast();

							return;
						}

						openDefaultSuccessToast();

						setFDSSorts(
							fdsSorts?.filter(
								(fdsSort: IFDSSort) => fdsSort.id !== item.id
							) || []
						);
					},
				},
			],
			status: 'warning',
			title: Liferay.Language.get('delete-filter'),
		});
	};

	const handleEdit = ({item}: {item: IFDSSort}) => {
		openModal({
			contentComponent: ({closeModal}: {closeModal: Function}) => (
				<EditFDSSortModalContent
					closeModal={closeModal}
					fdsSort={item}
					fields={fields}
					namespace={namespace}
					onSave={({editedFDSSort}: {editedFDSSort: IFDSSort}) => {
						setFDSSorts(
							fdsSorts?.map((fdsSort) => {
								if (fdsSort.id === editedFDSSort.id) {
									return editedFDSSort;
								}

								return fdsSort;
							}) || []
						);
					}}
				/>
			),
		});
	};

	const updateFDSSortsOrder = async ({
		fdsSortsOrder,
	}: {
		fdsSortsOrder: string;
	}) => {
		const response = await fetch(
			`${API_URL.FDS_VIEWS}/by-external-reference-code/${fdsView.externalReferenceCode}`,
			{
				body: JSON.stringify({
					fdsSortsOrder,
				}),
				headers: {
					'Accept': 'application/json',
					'Content-Type': 'application/json',
				},
				method: 'PATCH',
			}
		);

		if (!response.ok) {
			openDefaultFailureToast();

			return;
		}

		const responseJSON = await response.json();

		const storedFDSSortsOrder = responseJSON?.fdsSortsOrder;

		if (storedFDSSortsOrder && storedFDSSortsOrder === fdsSortsOrder) {
			openDefaultSuccessToast();
		}
		else {
			openDefaultFailureToast();
		}
	};

	return (
		<ClayLayout.ContainerFluid>
			{loading ? (
				<ClayLoadingIndicator />
			) : (
				<>
					<ClayAlert className="c-mt-5" displayType="info">
						{Liferay.Language.get(
							'the-hierarchy-of-the-default-sorting-will-be-defined-by-the-vertical-order-of-the-fields'
						)}
					</ClayAlert>

					<OrderableTable
						actions={[
							{
								icon: 'pencil',
								label: Liferay.Language.get('edit'),
								onClick: handleEdit,
							},
							{
								icon: 'trash',
								label: Liferay.Language.get('delete'),
								onClick: handleDelete,
							},
						]}
						creationMenuItems={[
							{
								label: Liferay.Language.get('new-sort'),
								onClick: handleCreation,
							},
						]}
						fields={[
							{
								headingTitle: true,
								label: Liferay.Language.get('label'),
								name: 'label',
							},
							{
								label: Liferay.Language.get('sort-by'),
								name: 'fieldName',
							},
							{
								contentRenderer: {
									component: DefaultComponent,
								},
								label: Liferay.Language.get('default'),
								name: 'default',
							},
						]}
						items={fdsSorts}
						noItemsButtonLabel={Liferay.Language.get(
							'new-default-sort'
						)}
						noItemsDescription={Liferay.Language.get(
							'start-creating-a-sort-to-display-specific-data'
						)}
						noItemsTitle={Liferay.Language.get(
							'no-default-sort-created-yet'
						)}
						onOrderChange={({order}: {order: string}) => {
							updateFDSSortsOrder({fdsSortsOrder: order});
						}}
						title={Liferay.Language.get('sorting')}
					/>
				</>
			)}
		</ClayLayout.ContainerFluid>
	);
};

export default Sorting;
