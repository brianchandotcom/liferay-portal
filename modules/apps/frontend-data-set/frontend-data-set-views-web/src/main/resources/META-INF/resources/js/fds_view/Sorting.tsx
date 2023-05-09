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
import ClayForm, {ClaySelectWithOption} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal from '@clayui/modal';
import {fetch, navigate, openModal, openToast} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {API_URL, OBJECT_RELATIONSHIP} from '../Constants';
import {FDSViewSectionInterface} from '../FDSView';
import {FDSViewType} from '../FDSViews';
import {getFields} from '../api';
import OrderableTable from '../components/OrderableTable';
import RequiredMark from '../components/RequiredMark';

interface ContentRendererProps {
	item: FDSSort;
}

interface Field {
	format: string;
	label: string;
	name: string;
	type: string;
}

interface FDSSort {
	fieldName: string;
	id: number;
	sortingDirection: string;
}

interface AddFDSSortModalContentInterface {
	closeModal: Function;
	fdsView: FDSViewType;
	fields: Field[];
	onSave: (newSort: FDSSort) => void;
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

const SortingDirectionContentRenderer = ({item}: ContentRendererProps) => {
	return (
		<span>
			{item.sortingDirection === SORTING_DIRECTION.ASCENDING.value
				? SORTING_DIRECTION.ASCENDING.label
				: SORTING_DIRECTION.DESCENDING.label}
		</span>
	);
};

const AddFDSSortModalContent = ({
	closeModal,
	fdsView,
	fields,
	onSave,
}: AddFDSSortModalContentInterface) => {
	const [selectedField, setSelectedField] = useState<string>();
	const [selectedSortingDirection, setSelectedSortingDirection] = useState<
		string
	>(SORTING_DIRECTION.ASCENDING.value);

	const handleSave = async () => {
		const field = fields.find((item: Field) => item.name === selectedField);

		if (!field) {
			alertFailed();

			return null;
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
			alertFailed();

			return null;
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
							disabled={!selectedField}
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

const Sorting = ({fdsView, fdsViewsURL}: FDSViewSectionInterface) => {
	const [fields, setFields] = React.useState<Field[]>([]);
	const [fdsSorts, setFDSSorts] = useState<Array<FDSSort>>([]);
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
			] as FDSSort[];

			let ordered = storedFDSSorts;
			let notOrdered: FDSSort[] = [];

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
					.filter(Boolean) as FDSSort[];

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

	const updateFDSFieldsOrder = async () => {
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

	const onCreationButtonClick = () =>
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
						disableSave={!newFDSSortsOrder.length}
						fields={[
							{
								headingTitle: true,
								label: Liferay.Language.get('name'),
								name: 'fieldName',
							},
							{
								contentRenderer: SortingDirectionContentRenderer,
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
						onCreationButtonClick={onCreationButtonClick}
						onOrderChange={({
							orderedItems,
						}: {
							orderedItems: FDSSort[];
						}) => {
							setNewFDSSortsOrder(
								orderedItems
									.map((fdsSort) => fdsSort.id)
									.join(',')
							);
						}}
						onSaveButtonClick={updateFDSFieldsOrder}
						title={Liferay.Language.get('sorting')}
					/>
				</>
			)}
		</ClayLayout.ContainerFluid>
	);
};

export default Sorting;
