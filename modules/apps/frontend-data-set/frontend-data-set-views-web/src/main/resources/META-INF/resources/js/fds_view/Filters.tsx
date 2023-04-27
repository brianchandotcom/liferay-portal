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

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput, ClaySelectWithOption} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayModal from '@clayui/modal';
import {fetch, navigate, openModal, openToast} from 'frontend-js-web';
import React from 'react';

import {API_URL, OBJECT_RELATIONSHIP} from '../Constants';
import {FDSViewType} from '../FDSViews';
import OrderableTable from '../components/OrderableTable';

interface Field {
	label: string;
	name: string;
	type: string;
}

interface Filter {
	id: number;
	label: string;
	name: string;
	type: string;
}

interface IPropsAddFDSFilterModalContent {
	closeModal: Function;
	fdsView: FDSViewType;
	fields: Field[];
	namespace: string;
	onSave: (newFilter: Filter) => void;
}

function AddFDSFilterModalContent({
	closeModal,
	fdsView,
	fields,
	namespace,
	onSave,
}: IPropsAddFDSFilterModalContent) {
	const [selectedField, setSelectedField] = React.useState<string>();
	const [label, setLabel] = React.useState<string>();

	const handleFilterSave = async () => {
		const field = fields.find((item: Field) => item.name === selectedField);

		if (!field) {
			openToast({
				message: Liferay.Language.get(
					'your-request-failed-to-complete'
				),
				type: 'danger',
			});

			return null;
		}

		const body = {
			entityFieldName: field.name,
			filterProperties: {},
			label: label || field.label,
			preloadedData: {},
			r_fdsViewFDSFilterRelationship_c_fdsViewId: fdsView.id,
			type: field.type,
		};

		const response = await fetch(API_URL.FDS_FILTERS, {
			body: JSON.stringify(body),
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json',
			},
			method: 'POST',
		});

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

		openToast({
			message: Liferay.Language.get(
				'your-request-completed-successfully'
			),
			type: 'success',
		});

		onSave(responseJSON);

		closeModal();
	};

	return (
		<div className="fds-view-fields-modal">
			<ClayModal.Header>
				{Liferay.Language.get('new-filter')}
			</ClayModal.Header>

			<ClayModal.Body>
				<ClayForm.Group>
					<label htmlFor={namespace + 'type'}>
						{Liferay.Language.get('filter-by')}
					</label>

					<ClaySelectWithOption
						aria-label={Liferay.Language.get('filter-by')}
						name={namespace + 'type'}
						onChange={(event) => {
							setSelectedField(event.target.value);
						}}
						options={[
							{},
							...fields.map((item) => ({
								label: item.label,
								value: item.name,
							})),
						]}
						title={Liferay.Language.get('filter-by')}
						value={selectedField}
					/>
				</ClayForm.Group>

				{selectedField && (
					<ClayForm.Group>
						<label htmlFor={namespace + 'label'}>
							{Liferay.Language.get('label')}

							<span
								className="label-icon lfr-portal-tooltip ml-2"
								title={Liferay.Language.get(
									'if-this-value-is-not-provided-the-label-will-default-to-the-field-name'
								)}
							>
								<ClayIcon symbol="question-circle-full" />
							</span>
						</label>

						<ClayInput
							aria-label={Liferay.Language.get('label')}
							name={namespace + 'label'}
							onChange={(event) => setLabel(event.target.value)}
							placeholder={
								fields.find(
									(item) => item.name === selectedField
								)?.label
							}
							value={label}
						/>
					</ClayForm.Group>
				)}
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton onClick={handleFilterSave}>
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
}

interface IProps {
	fdsView: FDSViewType;
	fdsViewsURL: string;
	namespace: string;
}

function Filters({fdsView, fdsViewsURL, namespace}: IProps) {
	const [fields, setFields] = React.useState<Field[]>([]);
	const [filters, setFilters] = React.useState<Filter[]>([]);
	const [newFiltersOrder, setNewFiltersOrder] = React.useState<string>('');

	const updateFDSFiltersOrder = async () => {
		const response = await fetch(
			`${API_URL.FDS_VIEWS}/by-external-reference-code/${fdsView.externalReferenceCode}`,
			{
				body: JSON.stringify({
					fdsFiltersOrder: newFiltersOrder,
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

		const fdsFiltersOrder = responseJSON?.fdsFiltersOrder;

		if (fdsFiltersOrder && fdsFiltersOrder === newFiltersOrder) {
			openToast({
				message: Liferay.Language.get(
					'your-request-completed-successfully'
				),
				type: 'success',
			});

			setNewFiltersOrder('');
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

	const onCreationButtonClick = () =>
		openModal({
			contentComponent: ({closeModal}: {closeModal: Function}) => (
				<AddFDSFilterModalContent
					closeModal={closeModal}
					fdsView={fdsView}
					fields={fields}
					namespace={namespace}
					onSave={(newfilter) => setFilters([...filters, newfilter])}
				/>
			),
		});

	React.useEffect(() => {
		const getFields = async () => {
			const response = await fetch(
				`${API_URL.FDS_FIELDS}?filter=(${OBJECT_RELATIONSHIP.FDS_VIEW_FDS_FIELD_ID} eq '${fdsView.id}')&nestedFields=${OBJECT_RELATIONSHIP.FDS_VIEW_FDS_FIELD}`
			);

			const responseJSON = await response.json();

			setFields(responseJSON.items);
		};

		const getFilters = async () => {
			const response = await fetch(
				`${API_URL.FDS_FILTERS}?filter=(${OBJECT_RELATIONSHIP.FDS_VIEW_FDS_FILTER_ID} eq '${fdsView.id}')&nestedFields=${OBJECT_RELATIONSHIP.FDS_VIEW_FDS_FILTER}`
			);

			const responseJSON = await response.json();

			let filtersOrderer = responseJSON.items as Filter[];

			if (fdsView.fdsFiltersOrder) {
				filtersOrderer = fdsView.fdsFiltersOrder
					.split(',')
					.map((fdsFilterId) =>
						filtersOrderer.find(
							(filter) => filter.id === Number(fdsFilterId)
						)
					)
					.filter(Boolean) as Filter[];
			}

			setFilters(filtersOrderer);
		};

		getFields();
		getFilters();
	}, [fdsView]);

	return (
		<ClayLayout.ContainerFluid>
			<OrderableTable
				disableSave={!newFiltersOrder.length}
				fields={[
					{
						label: Liferay.Language.get('label'),
						name: 'label',
					},
					{
						label: Liferay.Language.get('Field Name'),
						name: 'entityFieldName',
					},
					{
						label: Liferay.Language.get('type'),
						name: 'type',
					},
				]}
				items={filters}
				noItemsButtonLabel={Liferay.Language.get('create-filter')}
				noItemsDescription={Liferay.Language.get(
					'start-creating-a-filter-to-display-specific-data'
				)}
				noItemsTitle={Liferay.Language.get(
					'no-default-filters-were-created'
				)}
				onCancelButtonClick={() => navigate(fdsViewsURL)}
				onCreationButtonClick={onCreationButtonClick}
				onOrderChange={({orderedItems}: {orderedItems: Filter[]}) => {
					setNewFiltersOrder(
						orderedItems.map((filter) => filter.id).join(',')
					);
				}}
				onSaveButtonClick={updateFDSFiltersOrder}
				title={Liferay.Language.get('filters')}
			/>
		</ClayLayout.ContainerFluid>
	);
}

export default Filters;
