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
import ClayDatePicker from '@clayui/date-picker';
import ClayForm, {ClayInput, ClaySelectWithOption} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayModal from '@clayui/modal';
import classNames from 'classnames';
import {isBefore} from 'date-fns';
import {fetch, navigate, openModal, openToast} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {API_URL, OBJECT_RELATIONSHIP} from '../Constants';
import {FDSViewType} from '../FDSViews';
import {getFields} from '../api';
import OrderableTable from '../components/OrderableTable';

interface Field {
	format: string;
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

interface DateFilter extends Filter {
	from: string;
	to: string;
}

function DateRange({
	from,
	namespace,
	onFromChange,
	onToChange,
	to,
}: {
	from: string;
	namespace: string;
	onFromChange: (from: string) => void;
	onToChange: (to: string) => void;
	to: string;
}) {
	const [isValid, setIsValid] = useState(true);

	useEffect(() => {
		if (from && to) {
			setIsValid(isBefore(new Date(from), new Date(to)));
		}
	}, [from, to]);

	return (
		<>
			<ClayForm.Group className="form-group-autofit">
				<div
					className={classNames('form-group-item', {
						'has-error': !isValid,
					})}
				>
					<label htmlFor={namespace + 'date-range-from'}>
						{Liferay.Language.get('from')}
					</label>

					<ClayDatePicker
						onChange={(value: string) => onFromChange(value)}
						placeholder="YYYY-MM-DD"
						value={from}
					/>

					{!isValid && (
						<ClayForm.FeedbackGroup>
							<ClayForm.FeedbackItem>
								<ClayForm.FeedbackIndicator symbol="exclamation-full" />

								{Liferay.Language.get(
									'date-range-is-invalid.-from-must-be-before-to'
								)}
							</ClayForm.FeedbackItem>
						</ClayForm.FeedbackGroup>
					)}
				</div>

				<div className="form-group-item">
					<label htmlFor={namespace + 'date-range-to'}>
						{Liferay.Language.get('to')}
					</label>

					<ClayDatePicker
						onChange={(value: string) => onToChange(value)}
						placeholder="YYYY-MM-DD"
						value={to}
					/>
				</div>
			</ClayForm.Group>
		</>
	);
}

interface IPropsAddFDSFilterModalContent {
	closeModal: Function;
	fdsView: FDSViewType;
	fields: Field[];
	namespace: string;
	onSave: (newFilter: Filter) => void;
}

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

function AddFDSFilterModalContent({
	closeModal,
	fdsView,
	fields,
	namespace,
	onSave,
}: IPropsAddFDSFilterModalContent) {
	const [selectedField, setSelectedField] = useState<string>();
	const [label, setLabel] = useState<string>();
	const [from, setFrom] = useState<string>('');
	const [to, setTo] = useState<string>('');

	const handleFilterSave = async () => {
		const field = fields.find((item: Field) => item.name === selectedField);

		if (!field) {
			alertFailed();

			return null;
		}

		let body: any = {};
		let url: string = '';

		if (field.format === 'date-time') {
			url = API_URL.FDS_DATE_FILTERS;

			body = {
				[OBJECT_RELATIONSHIP.FDS_VIEW_FDS_DATE_FILTER_ID]: fdsView.id,
				fieldName: field.name,
				from,
				label: label || field.label,
				to,
				type: field.format,
			};
		}
		else {
			url = API_URL.FDS_DYNAMIC_FILTERS;

			body = {
				[OBJECT_RELATIONSHIP.FDS_VIEW_FDS_DYNAMIC_FILTER_ID]:
					fdsView.id,
				fieldName: field.name,
				label: label || field.label,
			};
		}

		const response = await fetch(url, {
			body: JSON.stringify(body),
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

	const field = fields.find((item: Field) => item.name === selectedField);

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
									'if-this-value-is-not-provided,-the-label-will-default-to-the-field-name'
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

				{field?.format === 'date-time' && (
					<DateRange
						from={from}
						namespace={namespace}
						onFromChange={setFrom}
						onToChange={setTo}
						to={to}
					/>
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
	const [fields, setFields] = useState<Field[]>([]);
	const [filters, setFilters] = useState<Filter[]>([]);
	const [newFiltersOrder, setNewFiltersOrder] = useState<string>('');

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
			alertFailed();

			return null;
		}

		const responseJSON = await response.json();

		const fdsFiltersOrder = responseJSON?.fdsFiltersOrder;

		if (fdsFiltersOrder && fdsFiltersOrder === newFiltersOrder) {
			alertSuccess();

			setNewFiltersOrder('');
		}
		else {
			alertFailed();
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

	const handleDelete = async ({item}: {item: Filter}) => {
		const url = `${
			item.type === 'date-time'
				? API_URL.FDS_DATE_FILTERS
				: API_URL.FDS_DYNAMIC_FILTERS
		}/${item.id}`;

		const response = await fetch(url, {
			method: 'DELETE',
		});

		if (!response.ok) {
			alertFailed();

			return null;
		}

		alertSuccess();

		setFilters(filters.filter((filter: Filter) => filter.id !== item.id));
	};

	useEffect(() => {
		const getFilters = async () => {
			const response = await fetch(
				`${API_URL.FDS_VIEWS}/${fdsView.id}?nestedFields=${OBJECT_RELATIONSHIP.FDS_VIEW_FDS_DATE_FILTER},${OBJECT_RELATIONSHIP.FDS_VIEW_FDS_DYNAMIC_FILTER}`
			);

			const responseJSON = await response.json();

			const dateFiltersOrderer = responseJSON[
				OBJECT_RELATIONSHIP.FDS_VIEW_FDS_DATE_FILTER
			] as DateFilter[];
			const dynamicFiltersOrderer = responseJSON[
				OBJECT_RELATIONSHIP.FDS_VIEW_FDS_DYNAMIC_FILTER
			] as Filter[];

			let filtersOrdered = [
				...dateFiltersOrderer,
				...dynamicFiltersOrderer,
			];

			if (fdsView.fdsFiltersOrder) {
				const order = fdsView.fdsFiltersOrder.split(',');

				let notOrdered: Filter[] = [];

				if (filtersOrdered.length > order.length) {
					notOrdered = filtersOrdered.filter(
						(filter) => !order.includes(String(filter.id))
					);
				}

				filtersOrdered = fdsView.fdsFiltersOrder
					.split(',')
					.map((fdsFilterId) =>
						filtersOrdered.find(
							(filter) => filter.id === Number(fdsFilterId)
						)
					)
					.filter(Boolean) as Filter[];

				filtersOrdered = [...filtersOrdered, ...notOrdered];
			}

			setFilters(filtersOrdered);
		};

		getFields(fdsView).then((newFields) => {
			if (newFields) {
				setFields(newFields);
			}
		});

		getFilters();
	}, [fdsView]);

	return (
		<ClayLayout.ContainerFluid>
			<OrderableTable
				actions={[
					{
						icon: 'trash',
						label: Liferay.Language.get('delete'),
						onClick: handleDelete,
					},
				]}
				disableSave={!newFiltersOrder.length}
				fields={[
					{
						label: Liferay.Language.get('label'),
						name: 'label',
					},
					{
						label: Liferay.Language.get('Field Name'),
						name: 'fieldName',
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
