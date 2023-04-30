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
import React, {useEffect, useRef, useState} from 'react';

import {API_URL, OBJECT_RELATIONSHIP} from '../Constants';
import {FDSViewSectionInterface} from '../FDSView';
import {FDSViewType} from '../FDSViews';
import {getFields} from '../api';
import OrderableTable from '../components/OrderableTable';
import RequiredMark from '../components/RequiredMark';

interface Field {
	format: string;
	label: string;
	name: string;
	type: string;
}

interface FDSSortType {
	id: number;
	name: string;
	value: string;
}

interface SortingType {
	label: string;
	value: string;
}

interface AddFDSSortingModalContentInterface {
	closeModal: Function;
	fdsView: FDSViewType;
	fields: Field[];
	onSave: Function;
}

const SORTING = {
	ASCENDING: {
		label: Liferay.Language.get('ascending'),
		value: 'asc',
	},
	DESCENDING: {
		label: Liferay.Language.get('descending'),
		value: 'desc',
	},
};

const SORTING_OPTIONS = [SORTING.ASCENDING, SORTING.DESCENDING];

const AddFDSSortingModalContent = ({
	closeModal,
	fdsView,
	fields,
	onSave,
}: AddFDSSortingModalContentInterface) => {
	const [selectedField, setSelectedField] = useState<string>();
	const [selectedSorting, setSelectedSorting] = useState<string>(
		SORTING.ASCENDING.value
	);

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
						name="field"
						onChange={(event) => {
							setSelectedField(event.target.value);
						}}
						options={[
							{
								disabled: true,
								label: Liferay.Language.get('choose-an-option'),
								selected: true,
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
							setSelectedSorting(event.target.value)
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
							onClick={() => {
								closeModal();

								onSave();
							}}
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
	const [fdsSorting, setFDSSorting] = useState<Array<FDSSortType> | null>(
		null
	);

	const fdsSortingRef = useRef('');

	const getFDSSorting = async () => {
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

		const storedFDSSorting = responseJSON?.items;

		if (!storedFDSSorting) {
			openToast({
				message: Liferay.Language.get(
					'your-request-failed-to-complete'
				),
				type: 'danger',
			});

			return null;
		}

		// @TODO: Get persisted sorting values.

		setFDSSorting([]);
	};

	const updateFDSFieldsOrder = async () => {
		const response = await fetch(
			`${API_URL.FDS_VIEWS}/by-external-reference-code/${fdsView.externalReferenceCode}`,
			{
				body: JSON.stringify({
					fdsFieldsOrder: fdsSortingRef.current,
				}),
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

		if (fdsFieldsOrder && fdsFieldsOrder === fdsSortingRef.current) {
			openToast({
				message: Liferay.Language.get(
					'your-request-completed-successfully'
				),
				type: 'success',
			});
		} else {
			openToast({
				message: Liferay.Language.get(
					'your-request-failed-to-complete'
				),
				type: 'danger',
			});
		}
	};

	useEffect(() => {
		getFDSSorting();

		getFields(fdsView).then((newFields) => {
			if (newFields) {
				setFields(newFields);
			}
		});

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const onCreationButtonClick = () =>
		openModal({
			contentComponent: ({closeModal}: {closeModal: Function}) => (
				<AddFDSSortingModalContent
					closeModal={closeModal}
					fdsView={fdsView}
					fields={fields}
					onSave={() => getFDSSorting()}
				/>
			),
		});

	return (
		<ClayLayout.ContainerFluid>
			<ClayAlert className="c-mt-5" displayType="info">
				{Liferay.Language.get(
					'the-hierarchy-of-the-default-sorting-will-be-defined-by-the-vertical-order-of-the-fields'
				)}
			</ClayAlert>

			{fdsSorting ? (
				<OrderableTable
					fields={[
						{
							label: Liferay.Language.get('name'),
							name: 'name',
						},
						{
							label: Liferay.Language.get('value'),
							name: 'value',
						},
					]}
					items={fdsSorting}
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
						orderedItems: Array<FDSSortType>;
					}) => {
						fdsSortingRef.current = orderedItems
							.map((item) => item.id)
							.join(',');
					}}
					onSaveButtonClick={() => updateFDSFieldsOrder}
					title={Liferay.Language.get('sorting')}
				/>
			) : (
				<ClayLoadingIndicator />
			)}
		</ClayLayout.ContainerFluid>
	);
};

export default Sorting;
