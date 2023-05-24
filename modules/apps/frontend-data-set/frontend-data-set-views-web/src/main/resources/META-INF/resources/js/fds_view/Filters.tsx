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
import {format, getYear, isBefore} from 'date-fns';
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
	fieldName: string;
	id: number;
	label: string;
	name: string;
	type: string;
}

interface IDateFilter extends IFilter {
	from: string;
	to: string;
}

interface IDynamicFilter extends IFilter {
	include: boolean;
	listTypeDefinitionId: string;
	multiple: boolean;
	preselectedValues: string;
}

type FilterCollection = Array<IDateFilter | IDynamicFilter>;

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

interface IPropsAddFDSFilterModalContent {
	closeModal: Function;
	fdsView: FDSViewType;
	fields: IField[];
	filter?: IDateFilter | IDynamicFilter;
	namespace: string;
	onSave: (newFilter: IFilter) => void;
}

function AddFDSFilterModalContent({
	closeModal,
	fdsView,
	fields,
	filter,
	namespace,
	onSave,
}: IPropsAddFDSFilterModalContent) {
	const [from, setFrom] = useState<string>(
		(filter as IDateFilter)?.from ?? ''
	);
	const [includeMode, setIncludeMode] = useState<string>(
		filter
			? (filter as IDynamicFilter)?.include
				? 'include'
				: 'exclude'
			: 'include'
	);
	const [isValidDateRange, setIsValidDateRange] = useState(true);
	const [multiple, setMultiple] = useState<boolean>(
		(filter as IDynamicFilter)?.multiple ?? true
	);
	const [name, setName] = useState(filter?.name || '');
	const [picklists, setPicklists] = useState<IPickList[]>([]);
	const [preselectedValues, setPreselectedValues] = useState<any[]>([]);
	const [selectedField, setSelectedField] = useState<IField | null>(
		fields.find((item) => item.name === filter?.fieldName) || null
	);
	const [selectedPicklist, setSelectedPicklist] = useState<IPickList>();
	const [to, setTo] = useState<string>((filter as IDateFilter)?.to ?? '');

	useEffect(() => {
		getAllPicklists().then((items) => {
			setPicklists(items);

			const newVal = items.find(
				(item) =>
					String(item.id) === (filter as any)?.listTypeDefinitionId
			);

			if (newVal) {
				setSelectedPicklist(newVal);

				setPreselectedValues(
					newVal.listTypeEntries.filter((item) =>
						JSON.parse(
							(filter as IDynamicFilter).preselectedValues || '[]'
						).includes(item.id)
					)
				);
			}
		});
	}, [filter]);

	useEffect(() => {
		let isValid = true;

		if (to && from) {
			isValid = isBefore(new Date(from), new Date(to));
		}

		setIsValidDateRange(isValid);
	}, [from, to]);

	const handleFilterSave = async (event: any) => {
		event.preventDefault();

		if (!selectedField) {
			alertFailed();

			return null;
		}

		let body: any = {
			fieldName: selectedField.name,
			name: name || selectedField.label,
		};

		let displayType: string = '';
		let url: string = '';

		if (selectedField.format === 'date-time') {
			url = API_URL.FDS_DATE_FILTERS;

			body = {
				...body,
				[OBJECT_RELATIONSHIP.FDS_VIEW_FDS_DATE_FILTER_ID]: fdsView.id,
				from,
				to,
				type: selectedField.format,
			};

			displayType = Liferay.Language.get('date-filter');
		}
		else {
			url = API_URL.FDS_DYNAMIC_FILTERS;

			body = {
				...body,
				[OBJECT_RELATIONSHIP.FDS_VIEW_FDS_DYNAMIC_FILTER_ID]:
					fdsView.id,
				include: includeMode === 'include',
				listTypeDefinitionId: selectedPicklist?.id,
				multiple,
				preselectedValues: preselectedValues.map((item) => item.id),
			};

			displayType = Liferay.Language.get('dynamic-filter');
		}

		let method = 'POST';

		if (filter) {
			method = 'PUT';
			url = `${url}/${filter.id}`;
		}

		const response = await fetch(url, {
			body: JSON.stringify(body),
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json',
			},
			method,
		});

		if (!response.ok) {
			alertFailed();

			return null;
		}

		const responseJSON = await response.json();

		alertSuccess();

		onSave({...responseJSON, displayType});

		closeModal();
	};

	const isValidSingleMode =
		multiple || (!multiple && !(preselectedValues.length > 1));

	const formElementId = `${namespace}Form`;
	const fromFormElementId = `${namespace}From`;
	const includeModeFormElementId = `${namespace}IncludeMode`;
	const multipleFormElementId = `${namespace}Multiple`;
	const nameFormElementId = `${namespace}Name`;
	const preselectedValuesFormElementId = `${namespace}PreselectedValues`;
	const selectedFieldFormElementId = `${namespace}SelectedField`;
	const sourceOptionFormElementId = `${namespace}SourceOption`;
	const toFormElementId = `${namespace}To`;

	return (
		<>
			<ClayModal.Header>
				{Liferay.Language.get('new-filter')}
			</ClayModal.Header>

			<ClayModal.Body>
				<ClayForm id={formElementId} onSubmit={handleFilterSave}>
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
							onChange={(event) => setName(event.target.value)}
							placeholder={
								selectedField?.label ||
								Liferay.Language.get('name')
							}
							value={name}
						/>
					</ClayForm.Group>

					<ClayForm.Group>
						<label htmlFor={selectedFieldFormElementId}>
							{Liferay.Language.get('filter-by')}
						</label>

						<ClaySelectWithOption
							aria-label={Liferay.Language.get('filter-by')}
							disabled={!!filter}
							onChange={(event) => {
								const newVal = fields.find(
									(item) => item.name === event.target.value
								);

								if (newVal) {
									setSelectedField(newVal);
								}
							}}
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
							value={selectedField?.name}
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
									onChange={setFrom}
									placeholder="YYYY-MM-DD"
									value={format(
										from ? new Date(from) : new Date(),
										'yyyy-MM-dd'
									)}
									years={{
										end: getYear(new Date()) + 25,
										start: getYear(new Date()) - 50,
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
									onChange={setTo}
									placeholder="YYYY-MM-DD"
									value={format(
										to ? new Date(to) : new Date(),
										'yyyy-MM-dd'
									)}
									years={{
										end: getYear(new Date()) + 25,
										start: getYear(new Date()) - 50,
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
									onChange={(event) => {
										setSelectedPicklist(
											picklists.find(
												(item) =>
													String(item.id) ===
													event.target.value
											)
										);

										setPreselectedValues([]);
									}}
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
									value={selectedPicklist?.id}
								/>
							</ClayForm.Group>

							{selectedPicklist && (
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
											name={multipleFormElementId}
											onChange={(newVal: any) => {
												setMultiple(newVal === 'true');
											}}
											value={multiple ? 'true' : 'false'}
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
											items={preselectedValues.map(
												(item) => ({
													label: item.name,
													value: item.id,
												})
											)}
											onItemsChange={(items: any) => {
												setPreselectedValues(
													items.map((item: any) =>
														selectedPicklist.listTypeEntries.find(
															(entry) =>
																String(
																	entry.id
																) ===
																String(
																	item.value
																)
														)
													)
												);
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
											name={includeModeFormElementId}
											onChange={(val: any) =>
												setIncludeMode(val)
											}
											value={includeMode}
										>
											<ClayRadio
												label={Liferay.Language.get(
													'include'
												)}
												value="include"
											/>

											<ClayRadio
												label={Liferay.Language.get(
													'exclude'
												)}
												value="exclude"
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
						<ClayButton
							disabled={
								!selectedField ||
								(!multiple && preselectedValues.length > 1)
							}
							form={formElementId}
							type="submit"
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
			] as IDynamicFilter[];

			let filtersOrdered: FilterCollection = [
				...dateFiltersOrderer.map((item) => ({
					...item,
					displayType: Liferay.Language.get('date-filter'),
				})),
				...dynamicFiltersOrderer.map((item) => ({
					...item,
					displayType: Liferay.Language.get('dynamic-filter'),
				})),
			];

			if (fdsView.fdsFiltersOrder) {
				const order = fdsView.fdsFiltersOrder.split(',');

				let notOrdered: FilterCollection = [];

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
					.filter(Boolean) as FilterCollection;

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

	const handleEdit = ({item}: {item: IDateFilter | IDynamicFilter}) =>
		openModal({
			className: 'overflow-auto',
			contentComponent: ({closeModal}: {closeModal: Function}) => (
				<AddFDSFilterModalContent
					closeModal={closeModal}
					fdsView={fdsView}
					fields={fields}
					filter={item}
					namespace={namespace}
					onSave={(newfilter) => {
						const newFilters = filters.map((item) => {
							if (item.id === newfilter.id) {
								return {...item, ...newfilter};
							}

							return item;
						});

						setFilters(newFilters);
					}}
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
					{
						icon: 'pencil',
						label: Liferay.Language.get('edit'),
						onClick: handleEdit,
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
						name: 'displayType',
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
