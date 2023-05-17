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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm, {ClayInput, ClaySelectWithOption} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal from '@clayui/modal';
import {fetch, navigate, openModal, openToast} from 'frontend-js-web';
import fuzzy from 'fuzzy';
import React, {useEffect, useState} from 'react';

import {API_URL, FUZZY_OPTIONS, OBJECT_RELATIONSHIP} from '../Constants';
import {IFDSViewSectionInterface} from '../FDSView';
import {FDSViewType} from '../FDSViews';
import {getFields} from '../api';
import OrderableTable from '../components/OrderableTable';
import RequiredMark from '../components/RequiredMark';

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

function alertFailed() {
	openToast({
		message: Liferay.Language.get('your-request-failed-to-complete'),
		type: 'danger',
	});
}

function alertSuccess() {
	openToast({
		message: Liferay.Language.get('your-request-completed-successfully'),
		type: 'success',
	});
}

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
	const [isSubmitting, setIsSubmitting] = useState(false);
	const [selectedField, setSelectedField] = useState<string>();
	const [selectedSortingDirection, setSelectedSortingDirection] = useState<
		string
	>(SORTING_DIRECTION.ASCENDING.value);

	const handleSave = async () => {
		setIsSubmitting(true);

		const field = fields.find(
			(item: IField) => item.name === selectedField
		);

		if (!field) {
			alertFailed();

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
			setIsSubmitting(false);

			alertFailed();

			return;
		}

		const responseJSON = await response.json();

		alertSuccess();

		onSave(responseJSON);

		closeModal();
	};

	return (
		<div className="fds-view-fields-modal">
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
							displayType="secondary"
							onClick={() => closeModal()}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							disabled={isSubmitting || !selectedField}
							onClick={handleSave}
						>
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</div>
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
	const [isSubmitting, setIsSubmitting] = useState(false);
	const [selectedSortingDirection, setSelectedSortingDirection] = useState(
		fdsSort.sortingDirection
	);

	const handleSave = async () => {
		setIsSubmitting(true);

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
			setIsSubmitting(false);

			alertFailed();

			return;
		}

		const editedFDSSort = await response.json();

		closeModal();

		alertSuccess();

		onSave({editedFDSSort});
	};

	const fdsSortFieldNameInputId = `${namespace}fdsSortFieldNameInput`;
	const fdsSortSortingDirectionInputId = `${namespace}fdsSortSortingDirectionInput`;

	return (
		<>
			<ClayModal.Header>
				{Liferay.Util.sub(
					Liferay.Language.get('edit-x'),
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
							displayType="secondary"
							onClick={() => closeModal()}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							disabled={isSubmitting}
							onClick={handleSave}
						>
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</>
	);
};

const Sorting = ({
	fdsView,
	fdsViewsURL,
	namespace,
}: IFDSViewSectionInterface) => {
	const [fields, setFields] = React.useState<IField[]>([]);
	const [fdsSorts, setFDSSorts] = useState<Array<IFDSSort>>([]);
	const [loading, setLoading] = useState(true);
	const [newFDSSortsOrder, setNewFDSSortsOrder] = React.useState<string>('');

	useEffect(() => {
		const getFDSSort = async () => {
			const response = await fetch(
				`${API_URL.FDS_VIEWS}/${fdsView.id}?nestedFields=${OBJECT_RELATIONSHIP.FDS_VIEW_FDS_SORT}`
			);

			const responseJSON = await response.json();

			const storedFDSSorts = responseJSON[
				OBJECT_RELATIONSHIP.FDS_VIEW_FDS_SORT
			] as IFDSSort[];

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

				if (storedFDSSorts.length > fdsSortsOrderArray.length) {
					notOrdered = storedFDSSorts.filter(
						(filter) =>
							!fdsSortsOrderArray.includes(String(filter.id))
					);
				}
			}

			setFDSSorts([...ordered, ...notOrdered]);

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
					displayType: 'warning',
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
							openToast({
								message: Liferay.Language.get(
									'your-request-failed-to-complete'
								),
								type: 'danger',
							});

							return;
						}

						openToast({
							message: Liferay.Language.get(
								'your-request-completed-successfully'
							),
							type: 'success',
						});

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

	const handleSave = async () => {
		const response = await fetch(
			`${API_URL.FDS_VIEWS}/by-external-reference-code/${fdsView.externalReferenceCode}`,
			{
				body: JSON.stringify({
					fdsSortsOrder: newFDSSortsOrder,
				}),
				headers: {
					'Accept': 'application/json',
					'Content-Type': 'application/json',
				},
				method: 'PATCH',
			}
		);

		if (!response.ok) {
			alertFailed();

			return null;
		}

		const responseJSON = await response.json();

		const fdsSortsOrder = responseJSON?.fdsSortsOrder;

		if (fdsSortsOrder && fdsSortsOrder === newFDSSortsOrder) {
			alertSuccess();

			setNewFDSSortsOrder('');
		}
		else {
			alertFailed();
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
						disableSave={!newFDSSortsOrder.length}
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
						onCancelButtonClick={() => navigate(fdsViewsURL)}
						onCreationButtonClick={handleCreation}
						onOrderChange={({
							orderedItems,
						}: {
							orderedItems: IFDSSort[];
						}) => {
							setNewFDSSortsOrder(
								orderedItems
									.map((fdsSort) => fdsSort.id)
									.join(',')
							);
						}}
						onSaveButtonClick={handleSave}
						title={Liferay.Language.get('sorting')}
					/>
				</>
			)}
		</ClayLayout.ContainerFluid>
	);
};

export default Sorting;
