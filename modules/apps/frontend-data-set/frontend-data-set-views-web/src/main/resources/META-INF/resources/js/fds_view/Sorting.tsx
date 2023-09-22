/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm, {ClayInput, ClaySelectWithOption} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal from '@clayui/modal';
import {fetch, openModal} from 'frontend-js-web';
import fuzzy from 'fuzzy';
import React, {useEffect, useState} from 'react';

import {API_URL, FUZZY_OPTIONS, OBJECT_RELATIONSHIP} from '../Constants';
import {IFDSViewSectionProps} from '../FDSView';
import {FDSViewType} from '../FDSViews';
import {getFields} from '../api';
import OrderableTable from '../components/OrderableTable';
import RequiredMark from '../components/RequiredMark';
import openDefaultFailureToast from '../utils/openDefaultFailureToast';
import openDefaultSuccessToast from '../utils/openDefaultSuccessToast';

interface IAddFDSSortModalContentInterface {
	closeModal: Function;
	fdsView: FDSViewType;
	fields: IField[];
	onSave: (newSort: IFDSSort) => void;
}

interface IContentRendererProps {
	item: IFDSSort;
	query: string;
}

interface IFDSSort {
	externalReferenceCode: string;
	fieldName: string;
	id: number;
	sortingDirection: string;
}

interface IField {
	format: string;
	label: string;
	name: string;
	type: string;
}

const SORTING_DIRECTION = {
	ASCENDING: {
		label: Liferay.Language.get('ascending'),
		value: 'asc',
	},
	DESCENDING: {
		label: Liferay.Language.get('descending'),
		value: 'desc',
	},
};

const SORTING_OPTIONS = [
	SORTING_DIRECTION.ASCENDING,
	SORTING_DIRECTION.DESCENDING,
];

const sortingDirectionTextMatch = (item: IFDSSort) => {
	return item.sortingDirection === SORTING_DIRECTION.ASCENDING.value
		? SORTING_DIRECTION.ASCENDING.label
		: SORTING_DIRECTION.DESCENDING.label;
};

const SortingDirectionComponent = ({item, query}: IContentRendererProps) => {
	const itemFieldValue =
		item.sortingDirection === SORTING_DIRECTION.ASCENDING.value
			? SORTING_DIRECTION.ASCENDING.label
			: SORTING_DIRECTION.DESCENDING.label;

	const fuzzyMatch = fuzzy.match(query, itemFieldValue, FUZZY_OPTIONS);

	return (
		<span>
			{fuzzyMatch ? (
				<span
					dangerouslySetInnerHTML={{
						__html: fuzzyMatch.rendered,
					}}
				/>
			) : (
				<span>{itemFieldValue}</span>
			)}
		</span>
	);
};

const AddFDSSortModalContent = ({
	closeModal,
	fdsView,
	fields,
	onSave,
}: IAddFDSSortModalContentInterface) => {
	const [saveButtonDisabled, setSaveButtonDisabled] = useState(false);
	const [selectedField, setSelectedField] = useState<string>();
	const [selectedSortingDirection, setSelectedSortingDirection] = useState<
		string
	>(SORTING_DIRECTION.ASCENDING.value);

	const handleSave = async () => {
		setSaveButtonDisabled(true);

		const field = fields.find(
			(item: IField) => item.name === selectedField
		);

		if (!field) {
			openDefaultFailureToast();

			return;
		}

		const response = await fetch(API_URL.FDS_SORTS, {
			body: JSON.stringify({
				[OBJECT_RELATIONSHIP.FDS_VIEW_FDS_SORT_ID]: fdsView.id,
				fieldName: selectedField,
				sortingDirection: selectedSortingDirection,
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

	return (
		<>
			<ClayModal.Header>
				{Liferay.Language.get('new-default-sort')}
			</ClayModal.Header>

			<ClayModal.Body>
				<ClayForm.Group>
					<label htmlFor="field">
						{Liferay.Language.get('field')}

						<RequiredMark />
					</label>

					<ClaySelectWithOption
						aria-label={Liferay.Language.get('field')}
						defaultValue=""
						name="field"
						onChange={(event) => {
							setSelectedField(event.target.value);
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
						title={Liferay.Language.get('field')}
						value={selectedField}
					/>
				</ClayForm.Group>

				<ClayForm.Group>
					<label htmlFor="sorting">
						{Liferay.Language.get('sorting')}

						<RequiredMark />
					</label>

					<ClaySelectWithOption
						aria-label={Liferay.Language.get('sorting')}
						id="sorting"
						onChange={(event) =>
							setSelectedSortingDirection(event.target.value)
						}
						options={SORTING_OPTIONS}
					/>
				</ClayForm.Group>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							disabled={saveButtonDisabled || !selectedField}
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
	namespace,
	onSave,
}: IEditFDSSortModalContentProps) => {
	const [saveButtonDisabled, setSaveButtonDisabled] = useState(false);
	const [selectedSortingDirection, setSelectedSortingDirection] = useState(
		fdsSort.sortingDirection
	);

	const handleSave = async () => {
		setSaveButtonDisabled(true);

		const response = await fetch(
			`${API_URL.FDS_SORTS}/by-external-reference-code/${fdsSort.externalReferenceCode}`,
			{
				body: JSON.stringify({
					sortingDirection: selectedSortingDirection,
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

	const fdsSortFieldNameInputId = `${namespace}fdsSortFieldNameInput`;
	const fdsSortSortingDirectionInputId = `${namespace}fdsSortSortingDirectionInput`;

	return (
		<>
			<ClayModal.Header>
				{Liferay.Util.sub(
					Liferay.Language.get('edit-x-sorting'),
					fdsSort.fieldName
				)}
			</ClayModal.Header>

			<ClayModal.Body>
				<ClayForm.Group>
					<label
						className="disabled"
						htmlFor={fdsSortFieldNameInputId}
					>
						{Liferay.Language.get('field')}
					</label>

					<ClayInput
						aria-label={Liferay.Language.get('field')}
						disabled
						name={fdsSortFieldNameInputId}
						title={Liferay.Language.get('field')}
						value={fdsSort.fieldName}
					/>
				</ClayForm.Group>

				<ClayForm.Group>
					<label htmlFor={fdsSortSortingDirectionInputId}>
						{Liferay.Language.get('sorting')}

						<RequiredMark />
					</label>

					<ClaySelectWithOption
						aria-label={Liferay.Language.get('sorting')}
						id={fdsSortSortingDirectionInputId}
						onChange={(event) =>
							setSelectedSortingDirection(event.target.value)
						}
						options={SORTING_OPTIONS}
						value={selectedSortingDirection}
					/>
				</ClayForm.Group>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							disabled={saveButtonDisabled}
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
								label: Liferay.Language.get('new-default-sort'),
								onClick: handleCreation,
							},
						]}
						fields={[
							{
								headingTitle: true,
								label: Liferay.Language.get('name'),
								name: 'fieldName',
							},
							{
								contentRenderer: {
									component: SortingDirectionComponent,
									textMatch: sortingDirectionTextMatch,
								},
								label: Liferay.Language.get('value'),
								name: 'sortingDirection',
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
