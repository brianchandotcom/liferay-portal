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
import fuzzy from 'fuzzy';
import React, {useCallback, useEffect, useState} from 'react';

import {IDataSet} from '../../DataSets';
import {FDSViewType} from '../../FDSViews';
import OrderableTable from '../../components/OrderableTable';
import RequiredMark from '../../components/RequiredMark';
import {
	API_URL,
	FUZZY_OPTIONS,
	OBJECT_RELATIONSHIP,
} from '../../utils/constants';
import openDefaultFailureToast from '../../utils/openDefaultFailureToast';
import openDefaultSuccessToast from '../../utils/openDefaultSuccessToast';
import sortItems from '../../utils/sortItems';
import {IField, IOrderable} from '../../utils/types';
import {IDataSetSectionProps} from '../DataSet';

interface IContentRendererProps {
	item: IFDSSort;
	query: string;
}

interface IFDSSort extends IOrderable {
	default: boolean;
	externalReferenceCode: string;
	fieldName: string;
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

const LabelComponent = ({item, query}: IContentRendererProps) => {
	const label =
		item.label ||
		item.label_i18n?.[Liferay.ThemeDisplay.getDefaultLanguageId()] ||
		'';

	const fuzzyMatch = fuzzy.match(query, label, FUZZY_OPTIONS);

	return (
		<span className="table-list-title">
			{fuzzyMatch ? (
				<span
					dangerouslySetInnerHTML={{
						__html: fuzzyMatch.rendered,
					}}
				/>
			) : (
				<span>{label}</span>
			)}
		</span>
	);
};

const labelTextMatch = (item: IFDSSort) => {
	return (
		item.label ||
		item.label_i18n[Liferay.ThemeDisplay.getDefaultLanguageId()] ||
		''
	);
};

const AddFDSSortModalContent = ({
	closeModal,
	dataSet,
	fields,
	namespace,
	onSave,
	saveFDSSortURL,
}: {
	closeModal: Function;
	dataSet: IDataSet | FDSViewType;
	fields: IField[];
	namespace: string;
	onSave: Function;
	saveFDSSortURL: string;
}) => {
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

		const formData = new FormData();

		formData.append(`${namespace}dataSetId`, dataSet.id);
		formData.append(
			`${namespace}useAsDefaultSorting`,
			String(useAsDefaultSorting)
		);
		formData.append(`${namespace}fieldName`, selectedFieldName);
		formData.append(`${namespace}labelI18n`, JSON.stringify(labelI18n));
		formData.append(`${namespace}orderType`, selectedOrderType);

		const response = await fetch(saveFDSSortURL, {
			body: formData,
			method: 'POST',
		});

		if (!response.ok) {
			setSaveButtonDisabled(false);

			openDefaultFailureToast();

			return;
		}

		const newFDSSort = await response.json();

		onSave({newFDSSort});

		openDefaultSuccessToast();

		closeModal();
	};

	const fdsSortLabelInput = `${namespace}fdsSortLabelInput`;
	const fdsSortFieldNameInputId = `${namespace}fdsSortFieldNameInput`;
	const fdsSortOrderTypeInputId = `${namespace}fdsSortOrderTypeInput`;

	return (
		<>
			<ClayModal.Header>
				{Liferay.Language.get('new-sorting-option')}
			</ClayModal.Header>

			<ClayModal.Body>
				<p className="text-secondary">
					{Liferay.Language.get(
						'create-a-sorting-option-for-the-dataset-fragment'
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

const EditFDSSortModalContent = ({
	closeModal,
	dataSet,
	fdsSort,
	fields,
	namespace,
	onSave,
	saveFDSSortURL,
}: {
	closeModal: Function;
	dataSet: IDataSet | FDSViewType;
	fdsSort: IFDSSort;
	fields: IField[];
	namespace: string;
	onSave: Function;
	saveFDSSortURL: string;
}) => {
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

		const formData = new FormData();

		formData.append(`${namespace}dataSetId`, dataSet.id);
		formData.append(
			`${namespace}externalReferenceCode`,
			fdsSort.externalReferenceCode
		);
		formData.append(`${namespace}fieldName`, selectedFieldName);
		formData.append(`${namespace}labelI18n`, JSON.stringify(labelI18n));
		formData.append(`${namespace}orderType`, selectedOrderType);
		formData.append(
			`${namespace}useAsDefaultSorting`,
			String(useAsDefaultSorting)
		);

		const response = await fetch(saveFDSSortURL, {
			body: formData,
			method: 'POST',
		});

		if (!response.ok) {
			setSaveButtonDisabled(false);

			openDefaultFailureToast();

			return;
		}

		const newFDSSort = await response.json();

		onSave({newFDSSort});

		openDefaultSuccessToast();

		closeModal();
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
							'create-a-sorting-option-for-the-dataset-fragment'
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

const Sorting = ({
	dataSet,
	fieldTreeItems,
	namespace,
	saveFDSSortURL,
}: IDataSetSectionProps) => {
	const fields = fieldTreeItems.filter((field) => field.sortable);
	const [fdsSorts, setFDSSorts] = useState<Array<IFDSSort>>([]);
	const [loading, setLoading] = useState(true);

	const fetchFDSSorts = useCallback(async () => {
		setLoading(true);

		const response = await fetch(
			`${API_URL.SORTS}?filter=(${OBJECT_RELATIONSHIP.DATA_SET_SORT_ID} eq '${dataSet.id}')&nestedFields=${OBJECT_RELATIONSHIP.DATA_SET_SORT}&sort=dateCreated:asc`,
			{
				headers: {
					'Accept': 'application/json',
					'Accept-Language':
						Liferay.ThemeDisplay.getBCP47LanguageId(),
				},
			}
		);

		const responseJSON = await response.json();

		const storedFDSSorts: IFDSSort[] = responseJSON.items;

		setFDSSorts(
			sortItems(
				storedFDSSorts,

				// @ts-ignore

				storedFDSSorts?.[0]?.[OBJECT_RELATIONSHIP.DATA_SET_SORT]
					?.fdsSortsOrder as string
			) as IFDSSort[]
		);

		setLoading(false);
	}, [dataSet]);

	useEffect(() => {
		fetchFDSSorts();
	}, [fetchFDSSorts]);

	const handleCreation = () =>
		openModal({
			contentComponent: ({closeModal}: {closeModal: Function}) => (
				<AddFDSSortModalContent
					closeModal={closeModal}
					dataSet={dataSet}
					fields={fields}
					namespace={namespace}
					onSave={() => {
						fetchFDSSorts();
					}}
					saveFDSSortURL={saveFDSSortURL}
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

						const url = `${API_URL.SORTS}/${item.id}`;

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
			title: Liferay.Language.get('delete-sorting'),
		});
	};

	const handleEdit = ({item}: {item: IFDSSort}) => {
		openModal({
			contentComponent: ({closeModal}: {closeModal: Function}) => (
				<EditFDSSortModalContent
					closeModal={closeModal}
					dataSet={dataSet}
					fdsSort={item}
					fields={fields}
					namespace={namespace}
					onSave={() => {
						fetchFDSSorts();
					}}
					saveFDSSortURL={saveFDSSortURL}
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
			`${API_URL.DATA_SETS}/by-external-reference-code/${dataSet.externalReferenceCode}`,
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

		if (
			fdsSorts &&
			storedFDSSortsOrder &&
			storedFDSSortsOrder === fdsSortsOrder
		) {
			setFDSSorts(sortItems(fdsSorts, storedFDSSortsOrder) as IFDSSort[]);

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
							'the-hierarchy-of-the-sorting-options-will-be-defined-by-the-vertical-order-of-the-fields'
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
								contentRenderer: {
									component: LabelComponent,
									textMatch: labelTextMatch,
								},
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
							'new-sorting-option'
						)}
						noItemsDescription={Liferay.Language.get(
							'create-a-sorting-option-to-order-the-data-in-the-fragment'
						)}
						noItemsTitle={Liferay.Language.get(
							'no-sorting-created-yet'
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
