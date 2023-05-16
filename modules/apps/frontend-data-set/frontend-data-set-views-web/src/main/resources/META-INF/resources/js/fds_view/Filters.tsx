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
import ClayForm, {
	ClayInput,
	ClayRadio,
	ClayRadioGroup,
	ClaySelectWithOption,
} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayModal from '@clayui/modal';
import ClayMultiSelect from '@clayui/multi-select';
import classNames from 'classnames';
import {getYear, isBefore} from 'date-fns';
import {fetch, navigate, openModal, openToast} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {API_URL, OBJECT_RELATIONSHIP} from '../Constants';
import {FDSViewType} from '../FDSViews';
import {IPickList, getAllPicklists, getFields} from '../api';
import OrderableTable from '../components/OrderableTable';

interface IField {
	format: string;
	label: string;
	name: string;
	type: string;
}

interface IFilter {
	id: number;
	label: string;
	name: string;
	type: string;
}

interface IDateFilter extends IFilter {
	from: string;
	to: string;
}

interface IPropsAddFDSFilterModalContent {
	closeModal: Function;
	fdsView: FDSViewType;
	fields: IField[];
	namespace: string;
	onSave: (newFilter: IFilter) => void;
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
	const fromFormElementId = `${namespace}From`;
	const formElementId = `${namespace}Form`;
	const toFormElementId = `${namespace}To`;
	const nameFormElementId = `${namespace}Name`;
	const selectedFieldFormElementId = `${namespace}SelectedField`;
	const sourceOptionFormElementId = `${namespace}SourceOption`;
	const multipleFormElementId = `${namespace}Multiple`;
	const includeModeFormElementId = `${namespace}IncludeMode`;
	const preselectedValuesFormElementId = `${namespace}PreselectedValues`;

	const [formValues, setFormValues] = useState<{[key: string]: string}>({
		[fromFormElementId]: '',
		[includeModeFormElementId]: 'true',
		[multipleFormElementId]: 'true',
		[nameFormElementId]: '',
		[preselectedValuesFormElementId]: '',
		[selectedFieldFormElementId]: '',
		[sourceOptionFormElementId]: '',
		[toFormElementId]: '',
	});
	const [multiSelectValues, setMultiSelectValues] = useState<any[]>([]);
	const [picklists, setPicklists] = useState<IPickList[]>([]);

	useEffect(() => {
		getAllPicklists().then((items) => setPicklists(items));
	}, []);

	const handleFilterSave = async (event: any) => {
		event.preventDefault();

		const formData = new FormData(event.target);

		const field = fields.find(
			(item: IField) =>
				item.name === formData.get(selectedFieldFormElementId)
		);

		if (!field) {
			alertFailed();

			return null;
		}

		let body: any = {
			fieldName: field.name,
			name: formData.get(nameFormElementId) || field.label,
		};

		let url: string = '';

		if (field.format === 'date-time') {
			url = API_URL.FDS_DATE_FILTERS;

			body = {
				...body,
				[OBJECT_RELATIONSHIP.FDS_VIEW_FDS_DATE_FILTER_ID]: fdsView.id,
				from: formData.get(fromFormElementId),
				to: formData.get(toFormElementId),
				type: field.format,
			};
		}
		else {
			url = API_URL.FDS_DYNAMIC_FILTERS;

			body = {
				...body,
				[OBJECT_RELATIONSHIP.FDS_VIEW_FDS_DYNAMIC_FILTER_ID]:
					fdsView.id,
				include: formData.get(includeModeFormElementId) === 'true',
				listTypeDefinitionId: formData.get(sourceOptionFormElementId),
				multiple: formData.get(multipleFormElementId) === 'true',
				preselectedValues: formData.getAll(
					preselectedValuesFormElementId
				),
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

	const selectedField = fields.find(
		(item: IField) => item.name === formValues[selectedFieldFormElementId]
	);

	const selectedPicklist = picklists.find(
		(item: IPickList) =>
			String(item.id) === formValues[sourceOptionFormElementId]
	);

	const isValidDateRange =
		!formValues[fromFormElementId] || !formValues[toFormElementId]
			? true
			: isBefore(
					new Date(formValues[fromFormElementId]),
					new Date(formValues[toFormElementId])
			  );

	const isValidSingleMode =
		formValues[multipleFormElementId] === 'true' ||
		(formValues[multipleFormElementId] === 'false' &&
			!(multiSelectValues.length > 1));

	return (
		<>
			<ClayModal.Header>
				{Liferay.Language.get('new-filter')}
			</ClayModal.Header>

			<ClayModal.Body>
				<ClayForm
					id={formElementId}
					onChange={(event: any) => {
						const {name, value} = event?.target;

						if (name) {
							setFormValues({
								...formValues,
								[name]: value,
							});
						}
					}}
					onSubmit={handleFilterSave}
				>
					<ClayForm.Group>
						<label htmlFor={nameFormElementId}>
							{Liferay.Language.get('name')}

							<span
								className="label-icon lfr-portal-tooltip ml-2"
								title={Liferay.Language.get(
									'if-this-value-is-not-provided,-the-name-will-default-to-the-field-name'
								)}
							>
								<ClayIcon symbol="question-circle-full" />
							</span>
						</label>

						<ClayInput
							aria-label={Liferay.Language.get('name')}
							name={nameFormElementId}
							placeholder={
								fields.find(
									(item) =>
										item.name ===
										formValues[selectedFieldFormElementId]
								)?.label || Liferay.Language.get('name')
							}
						/>
					</ClayForm.Group>

					<ClayForm.Group>
						<label htmlFor={selectedFieldFormElementId}>
							{Liferay.Language.get('filter-by')}
						</label>

						<ClaySelectWithOption
							aria-label={Liferay.Language.get('filter-by')}
							name={selectedFieldFormElementId}
							options={[
								{
									disabled: true,
									label: Liferay.Language.get('select'),
									selected: true,
									value: '',
								},
								...fields.map((item) => ({
									label: item.label,
									value: item.name,
								})),
							]}
							title={Liferay.Language.get('filter-by')}
						/>
					</ClayForm.Group>

					{selectedField?.format === 'date-time' && (
						<ClayForm.Group className="form-group-autofit">
							<div
								className={classNames('form-group-item', {
									'has-error': !isValidDateRange,
								})}
							>
								<label htmlFor={fromFormElementId}>
									{Liferay.Language.get('from')}
								</label>

								<ClayDatePicker
									inputName={fromFormElementId}
									placeholder="YYYY-MM-DD"
									years={{
										end: getYear(new Date()) + 5,
										start: getYear(new Date()) - 5,
									}}
								/>

								{!isValidDateRange && (
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
								<label htmlFor={toFormElementId}>
									{Liferay.Language.get('to')}
								</label>

								<ClayDatePicker
									inputName={toFormElementId}
									placeholder="YYYY-MM-DD"
									years={{
										end: getYear(new Date()) + 5,
										start: getYear(new Date()) - 5,
									}}
								/>
							</div>
						</ClayForm.Group>
					)}

					{selectedField?.format === 'string' && (
						<>
							<ClayForm.Group>
								<label htmlFor={sourceOptionFormElementId}>
									{Liferay.Language.get('source-options')}

									<span
										className="label-icon lfr-portal-tooltip ml-2"
										title={Liferay.Language.get(
											'choose-a-picklist-to-associate-with-this-filter'
										)}
									>
										<ClayIcon symbol="question-circle-full" />
									</span>
								</label>

								<ClaySelectWithOption
									aria-label={Liferay.Language.get(
										'source-options'
									)}
									name={sourceOptionFormElementId}
									options={[
										{
											disabled: true,
											label: Liferay.Language.get(
												'select'
											),
											selected: true,
											value: '',
										},
										...picklists.map((item) => ({
											label: item.name,
											value: item.id,
										})),
									]}
									title={Liferay.Language.get(
										'source-options'
									)}
								/>
							</ClayForm.Group>

							{formValues[sourceOptionFormElementId] && (
								<>
									<ClayForm.Group>
										<label htmlFor={multipleFormElementId}>
											{Liferay.Language.get('selection')}

											<span
												className="label-icon lfr-portal-tooltip ml-2"
												title={Liferay.Language.get(
													'determines-how-many-preselected-values-for-the-filter-can-be-added'
												)}
											>
												<ClayIcon symbol="question-circle-full" />
											</span>
										</label>

										<ClayRadioGroup
											defaultValue={
												formValues[
													multipleFormElementId
												]
											}
											name={multipleFormElementId}
										>
											<ClayRadio
												label={Liferay.Language.get(
													'multiple'
												)}
												value="true"
											/>

											<ClayRadio
												label={Liferay.Language.get(
													'single'
												)}
												value="false"
											/>
										</ClayRadioGroup>
									</ClayForm.Group>
									<ClayForm.Group
										className={classNames({
											'has-error': !isValidSingleMode,
										})}
									>
										<label
											htmlFor={
												preselectedValuesFormElementId
											}
										>
											{Liferay.Language.get(
												'preselected-values'
											)}

											<span
												className="label-icon lfr-portal-tooltip ml-2"
												title={Liferay.Language.get(
													'choose-values-to-preselect-for-your-filters-source-option'
												)}
											>
												<ClayIcon symbol="question-circle-full" />
											</span>
										</label>

										<ClayMultiSelect
											allowsCustomLabel={false}
											aria-label={Liferay.Language.get(
												'preselected-values'
											)}
											inputName={
												preselectedValuesFormElementId
											}
											items={multiSelectValues}
											onItemsChange={(items: any) => {
												setMultiSelectValues(items);

												setFormValues({
													...formValues,
													[preselectedValuesFormElementId]: items
														.map(
															(item: any) =>
																item.value
														)
														.join(','),
												});
											}}
											placeholder={Liferay.Language.get(
												'select-a-default-value-for-your-filter'
											)}
											sourceItems={
												!selectedPicklist
													? []
													: selectedPicklist.listTypeEntries.map(
															(item) => ({
																label:
																	item.name,
																value: String(
																	item.id
																),
															})
													  )
											}
										/>

										{!isValidSingleMode && (
											<ClayForm.FeedbackGroup>
												<ClayForm.FeedbackItem>
													<ClayForm.FeedbackIndicator symbol="exclamation-full" />

													{Liferay.Language.get(
														'only-one-value-is-allowed-in-single-selection-mode'
													)}
												</ClayForm.FeedbackItem>
											</ClayForm.FeedbackGroup>
										)}
									</ClayForm.Group>
									<ClayForm.Group>
										<label
											htmlFor={includeModeFormElementId}
										>
											{Liferay.Language.get(
												'filter-mode'
											)}

											<span
												className="label-icon lfr-portal-tooltip ml-2"
												title={Liferay.Language.get(
													'include-returns-only-the-selected-values.-exclude-returns-all-except-the-selected-ones'
												)}
											>
												<ClayIcon symbol="question-circle-full" />
											</span>
										</label>

										<ClayRadioGroup
											defaultValue={
												formValues[
													includeModeFormElementId
												]
											}
											name={includeModeFormElementId}
										>
											<ClayRadio
												label={Liferay.Language.get(
													'include'
												)}
												value="true"
											/>

											<ClayRadio
												label={Liferay.Language.get(
													'exclude'
												)}
												value="false"
											/>
										</ClayRadioGroup>
									</ClayForm.Group>
								</>
							)}
						</>
					)}
				</ClayForm>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton form={formElementId} type="submit">
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
}

interface IProps {
	fdsView: FDSViewType;
	fdsViewsURL: string;
	namespace: string;
}

function Filters({fdsView, fdsViewsURL, namespace}: IProps) {
	const [fields, setFields] = useState<IField[]>([]);
	const [filters, setFilters] = useState<IFilter[]>([]);
	const [newFiltersOrder, setNewFiltersOrder] = useState<string>('');

	useEffect(() => {
		const getFilters = async () => {
			const response = await fetch(
				`${API_URL.FDS_VIEWS}/${fdsView.id}?nestedFields=${OBJECT_RELATIONSHIP.FDS_VIEW_FDS_DATE_FILTER},${OBJECT_RELATIONSHIP.FDS_VIEW_FDS_DYNAMIC_FILTER}`
			);

			const responseJSON = await response.json();

			const dateFiltersOrderer = responseJSON[
				OBJECT_RELATIONSHIP.FDS_VIEW_FDS_DATE_FILTER
			] as IDateFilter[];
			const dynamicFiltersOrderer = responseJSON[
				OBJECT_RELATIONSHIP.FDS_VIEW_FDS_DYNAMIC_FILTER
			] as IFilter[];

			let filtersOrdered = [
				...dateFiltersOrderer.map((item) => ({
					...item,
					type: Liferay.Language.get('date-filter'),
				})),
				...dynamicFiltersOrderer.map((item) => ({
					...item,
					type: Liferay.Language.get('dynamic-filter'),
				})),
			];

			if (fdsView.fdsFiltersOrder) {
				const order = fdsView.fdsFiltersOrder.split(',');

				let notOrdered: IFilter[] = [];

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
					.filter(Boolean) as IFilter[];

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
			className: 'overflow-auto',
			contentComponent: ({closeModal}: {closeModal: Function}) => (
				<AddFDSFilterModalContent
					closeModal={closeModal}
					fdsView={fdsView}
					fields={fields}
					namespace={namespace}
					onSave={(newfilter) => setFilters([...filters, newfilter])}
				/>
			),
			disableAutoClose: true,
		});

	const handleDelete = async ({item}: {item: IFilter}) => {
		openModal({
			bodyHTML: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-this-filter'
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
					onClick: ({processClose}: {processClose: Function}) => {
						processClose();

						const url = `${
							item.type === 'date-time'
								? API_URL.FDS_DATE_FILTERS
								: API_URL.FDS_DYNAMIC_FILTERS
						}/${item.id}`;

						fetch(url, {
							method: 'DELETE',
						})
							.then(() => {
								alertSuccess();

								setFilters(
									filters.filter(
										(filter: IFilter) =>
											filter.id !== item.id
									)
								);
							})
							.catch(() => {
								alertFailed();
							});
					},
				},
			],
			status: 'warning',
			title: Liferay.Language.get('delete-filter'),
		});
	};

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
						label: Liferay.Language.get('name'),
						name: 'name',
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
				onOrderChange={({orderedItems}: {orderedItems: IFilter[]}) => {
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
