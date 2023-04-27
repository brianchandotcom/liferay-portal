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
import ClayLayout from '@clayui/layout';
import ClayModal from '@clayui/modal';
import {fetch, navigate, openModal, openToast} from 'frontend-js-web';
import React from 'react';

import {API_URL, OBJECT_RELATIONSHIP} from '../Constants';
import OrderableTable from '../components/OrderableTable';

function AddFDSFilterModalContent({
	closeModal,
	fdsView,
	fields,
	namespace,
	onSave,
}: any) {
	const [selectedField, setSelectedField] = React.useState<string>();
	const [label, setLabel] = React.useState<string>();

	const handleFilterSave = async () => {
		const field = fields.find((item: any) => item.name === selectedField);

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
							...fields.map((item: any) => ({
								...item,
								value: item.name,
							})),
						]}
						title={Liferay.Language.get('filter-by')}
						value={selectedField}
					/>
				</ClayForm.Group>

				<ClayForm.Group>
					<label htmlFor={namespace + 'label'}>
						{Liferay.Language.get('label')}
					</label>

					<ClayInput
						aria-label={Liferay.Language.get('label')}
						name={namespace + 'label'}
						onChange={(event) => setLabel(event.target.value)}
						value={label}
					/>
				</ClayForm.Group>
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

function Filters({fdsView, fdsViewsURL, namespace}: any) {
	const [fields, setFields] = React.useState<any[]>([]);
	const [filters, setFilters] = React.useState<any[]>([]);

	const newFdsFiltersOrderRef = React.useRef('');

	const updateFDSFiltersOrder = async () => {
		const newFdsFiltersOrder = newFdsFiltersOrderRef.current;

		const response = await fetch(
			`${API_URL.FDS_VIEWS}/by-external-reference-code/${fdsView.externalReferenceCode}`,
			{
				body: JSON.stringify({
					fdsFiltersOrder: newFdsFiltersOrder,
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

		if (fdsFiltersOrder && fdsFiltersOrder === newFdsFiltersOrder) {
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

	const onCreationButtonClick = () =>
		openModal({
			contentComponent: ({closeModal}: {closeModal: Function}) => (
				<AddFDSFilterModalContent
					closeModal={closeModal}
					fdsView={fdsView}
					fields={fields}
					namespace={namespace}
					onSave={(newfilter: any) =>
						setFilters([...filters, newfilter])
					}
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

			let filtersOrderer = responseJSON.items;

			if (fdsView.fdsFiltersOrder) {
				filtersOrderer = fdsView.fdsFiltersOrder
					.split(',')
					.map((fdsFilterId: string) => {
						return filtersOrderer.find(
							(filter: any) => filter.id === Number(fdsFilterId)
						);
					});
			}

			setFilters(filtersOrderer);
		};

		getFields();
		getFilters();
	}, [fdsView]);

	return (
		<ClayLayout.ContainerFluid>
			<OrderableTable
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
				onOrderChange={({orderedItems}) => {
					newFdsFiltersOrderRef.current = orderedItems
						.map((filter: any) => filter.id)
						.join(',');
				}}
				onSaveButtonClick={updateFDSFiltersOrder}
				title={Liferay.Language.get('filters')}
			/>
		</ClayLayout.ContainerFluid>
	);
}

export default Filters;
