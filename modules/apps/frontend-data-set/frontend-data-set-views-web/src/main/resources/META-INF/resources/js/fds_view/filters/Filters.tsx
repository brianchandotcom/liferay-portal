/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayForm from '@clayui/form';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import ClayModal from '@clayui/modal';
import {IClientExtensionRenderer} from '@liferay/frontend-data-set-web';
import classNames from 'classnames';
import {InputLocalized} from 'frontend-js-components-web';
import {fetch, openModal, sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {FDSViewType} from '../../FDSViews';
import {getAllPicklists, getFields} from '../../api';
import OrderableTable from '../../components/OrderableTable';
import ValidationFeedback from '../../components/ValidationFeedback';
import {API_URL, OBJECT_RELATIONSHIP} from '../../utils/constants';
import openDefaultFailureToast from '../../utils/openDefaultFailureToast';
import openDefaultSuccessToast from '../../utils/openDefaultSuccessToast';
import {
	EFieldFormat,
	EFieldType,
	EFilterType,
	IClientExtensionFilter,
	IDateFilter,
	IField,
	IFilter,
	IPickList,
	ISelectionFilter,
} from '../../utils/types';
import ClientExtensionFilterModalContent from './modal_content/ClientExtensionFilter';
import DateRangeFilterModalContent from './modal_content/DateRangeFilter';
import SelectionFilterModalContent from './modal_content/SelectionFilter';

import '../../../css/Filters.scss';

type FilterCollection = Array<
	IClientExtensionFilter | IDateFilter | ISelectionFilter
>;

interface IPropsAddFDSFilterModalContent {
	closeModal: Function;
	fdsFilterClientExtensions?: IClientExtensionRenderer[];
	fdsView: FDSViewType;
	fieldNames?: string[];
	fields: IField[];
	filter?: IClientExtensionFilter | IDateFilter | ISelectionFilter;
	filterType?: EFilterType;
	namespace: string;
	onSave: (newFilter: IFilter) => void;
}

function AddFDSFilterModalContent({
	closeModal,
	fdsFilterClientExtensions = [],
	fdsView,
	fieldNames,
	fields,
	filter,
	filterType,
	namespace,
	onSave,
}: IPropsAddFDSFilterModalContent) {
	const [selectedClientExtension, setSelectedClientExtension] = useState<
		IClientExtensionRenderer | undefined
	>(
		filter && filterType === EFilterType.CLIENT_EXTENSION
			? fdsFilterClientExtensions.find(
					(clientExtensionRenderer: IClientExtensionRenderer) =>
						clientExtensionRenderer.externalReferenceCode ===
						(filter as IClientExtensionFilter)
							.fdsFilterClientExtensionERC
			  )
			: undefined
	);
	const [fieldInUseValidationError, setFieldInUseValidationError] = useState<
		boolean
	>();
	const fdsFilterLabelTranslations = filter?.label_i18n ?? {};
	const [from, setFrom] = useState<string>(
		(filter as IDateFilter)?.from ?? ''
	);
	const [i18nFilterLabels, setI18nFilterLabels] = useState(
		fdsFilterLabelTranslations
	);
	const [includeMode, setIncludeMode] = useState<string>('include');
	const [isValidDateRange, setIsValidDateRange] = useState<boolean>(true);
	const [multiple, setMultiple] = useState<boolean>(
		(filter as ISelectionFilter)?.multiple ?? true
	);
	const [picklists, setPicklists] = useState<IPickList[]>([]);
	const [preselectedValues, setPreselectedValues] = useState<any[]>([]);
	const [saveButtonDisabled, setSaveButtonDisabled] = useState<boolean>();
	const [selectedField, setSelectedField] = useState<IField | null>(
		fields.find((item) => item.name === filter?.fieldName) || null
	);
	const [selectedPicklist, setSelectedPicklist] = useState<IPickList>();
	const [to, setTo] = useState<string>((filter as IDateFilter)?.to ?? '');

	useEffect(() => {
		getAllPicklists().then((items) => {
			setPicklists(items);

			const picklist = items.find(
				(item) =>
					String(item.externalReferenceCode) ===
					(filter as any)?.listTypeDefinitionERC
			);

			if (picklist) {
				setSelectedPicklist(picklist);

				const validSavedPreselectedValues = picklist.listTypeEntries.filter(
					(item) =>
						JSON.parse(
							(filter as ISelectionFilter).preselectedValues ||
								'[]'
						).includes(item.externalReferenceCode)
				);

				setPreselectedValues(validSavedPreselectedValues);

				setIncludeMode(
					validSavedPreselectedValues?.length
						? filter && (filter as ISelectionFilter).include
							? 'include'
							: 'exclude'
						: 'include'
				);
			}
		});
	}, [filter]);

	const handleFilterSave = async () => {
		setSaveButtonDisabled(true);

		if (!selectedField) {
			openDefaultFailureToast();

			return null;
		}

		let body: any = {
			fieldName: selectedField.name,
			label_i18n: i18nFilterLabels,
		};

		let displayType: string = '';
		let url: string = '';

		if (filterType === EFilterType.DATE_RANGE) {
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
		else if (filterType === EFilterType.SELECTION) {
			url = API_URL.FDS_DYNAMIC_FILTERS;

			body = {
				...body,
				[OBJECT_RELATIONSHIP.FDS_VIEW_FDS_DYNAMIC_FILTER_ID]:
					fdsView.id,
				include: includeMode === 'include',
				listTypeDefinitionERC: selectedPicklist?.externalReferenceCode,
				multiple,
				preselectedValues: JSON.stringify(
					preselectedValues.map((item) => item.externalReferenceCode)
				),
			};

			displayType = Liferay.Language.get('dynamic-filter');
		}
		else if (
			filterType === EFilterType.CLIENT_EXTENSION &&
			selectedClientExtension
		) {
			url = API_URL.FDS_CLIENT_EXTENSION_FILTERS;

			body = {
				...body,
				fdsFilterClientExtensionERC:
					selectedClientExtension.externalReferenceCode,
				[OBJECT_RELATIONSHIP.FDS_VIEW_FDS_CLIENT_EXTENSION_FILTER_ID]:
					fdsView.id,
			};

			displayType = Liferay.Language.get('client-extension-filter');
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
			setSaveButtonDisabled(false);

			openDefaultFailureToast();

			return null;
		}

		const responseJSON = await response.json();

		openDefaultSuccessToast();

		onSave({...responseJSON, displayType, filterType});

		closeModal();
	};

	const nameFormElementId = `${namespace}Name`;
	const selectedFieldFormElementId = `${namespace}SelectedField`;

	const inUseFields = fields.map((item) =>
		fieldNames?.includes(item.name) ? item.name : undefined
	);

	const FieldNameDropdown = ({
		fields,
		inUseFields,
		onItemClick,
	}: {
		fields: IField[];
		inUseFields: (string | undefined)[];
		onItemClick: Function;
	}) => {
		return (
			<ClayDropDown
				closeOnClick
				menuElementAttrs={{
					className: 'fds-field-name-dropdown-menu',
				}}
				trigger={
					<ClayButton
						className="form-control form-control-select form-control-select-secondary"
						displayType="secondary"
						id={selectedFieldFormElementId}
					>
						{selectedField
							? selectedField.label
							: Liferay.Language.get('select')}
					</ClayButton>
				}
			>
				<ClayDropDown.ItemList items={fields} role="listbox">
					{fields.map((field) => (
						<ClayDropDown.Item
							className="align-items-center d-flex justify-content-between"
							disabled={
								!!filter ||
								(filterType === EFilterType.SELECTION &&
									!picklists.length)
							}
							key={field.name}
							onClick={() => onItemClick(field)}
							roleItem="option"
						>
							{field.label}

							{inUseFields.includes(field.name) && (
								<ClayLabel displayType="info">
									{Liferay.Language.get('in-use')}
								</ClayLabel>
							)}
						</ClayDropDown.Item>
					))}
				</ClayDropDown.ItemList>
			</ClayDropDown>
		);
	};

	return (
		<>
			<ClayModal.Header>
				{filter &&
					sub(Liferay.Language.get('edit-x-filter'), [filter.label])}

				{!filter && (
					<>
						{filterType === EFilterType.CLIENT_EXTENSION && (
							<ClientExtensionFilterModalContent.Header />
						)}

						{filterType === EFilterType.DATE_RANGE && (
							<DateRangeFilterModalContent.Header />
						)}

						{filterType === EFilterType.SELECTION && (
							<SelectionFilterModalContent.Header />
						)}
					</>
				)}
			</ClayModal.Header>

			<ClayModal.Body>
				<ClayForm.Group>
					<InputLocalized
						id={nameFormElementId}
						label={Liferay.Language.get('name')}
						name="label"
						onChange={setI18nFilterLabels}
						translations={i18nFilterLabels}
					/>
				</ClayForm.Group>

				<ClayForm.Group
					className={classNames({
						'has-error': fieldInUseValidationError,
					})}
				>
					<label htmlFor={selectedFieldFormElementId}>
						{Liferay.Language.get('filter-by')}
					</label>

					<FieldNameDropdown
						fields={fields}
						inUseFields={inUseFields}
						onItemClick={(item: IField) => {
							const newVal = fields.find((field) => {
								return field.name === item.label;
							});

							if (newVal) {
								const inUse = inUseFields.includes(newVal.name);

								setFieldInUseValidationError(inUse);
								setSaveButtonDisabled(inUse);

								setSelectedField(newVal);
							}
						}}
					/>

					{fieldInUseValidationError && (
						<ValidationFeedback
							message={Liferay.Language.get(
								'this-field-is-being-used-by-another-filter'
							)}
						/>
					)}
				</ClayForm.Group>

				{!fieldInUseValidationError && (
					<>
						{filterType === EFilterType.CLIENT_EXTENSION && (
							<ClientExtensionFilterModalContent.Body
								fdsFilterClientExtensions={
									fdsFilterClientExtensions
								}
								namespace={namespace}
								onSelectedClientExtensionChange={
									setSelectedClientExtension
								}
								selectedClientExtension={
									selectedClientExtension
								}
							/>
						)}

						{filterType === EFilterType.DATE_RANGE && (
							<DateRangeFilterModalContent.Body
								from={from}
								isValidDateRange={isValidDateRange}
								namespace={namespace}
								onFromChange={setFrom}
								onToChange={setTo}
								onValidDateChange={setIsValidDateRange}
								to={to}
							/>
						)}

						{filterType === EFilterType.SELECTION && (
							<SelectionFilterModalContent.Body
								includeMode={includeMode}
								multiple={multiple}
								namespace={namespace}
								onIncludeModeChange={setIncludeMode}
								onMultipleChange={setMultiple}
								onPreselectedValuesChange={(values) => {
									setPreselectedValues(values);

									setIncludeMode(
										values.length
											? filter &&
											  (filter as ISelectionFilter)
													.include
												? 'include'
												: 'exclude'
											: 'include'
									);
								}}
								onSelectedPicklistChange={setSelectedPicklist}
								picklists={picklists}
								preselectedValues={preselectedValues}
								selectedPicklist={selectedPicklist}
							/>
						)}
					</>
				)}
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							disabled={
								!selectedField ||
								(filterType === EFilterType.CLIENT_EXTENSION &&
									!selectedClientExtension) ||
								(!multiple && preselectedValues.length > 1) ||
								(filterType === EFilterType.DATE_RANGE &&
									!isValidDateRange) ||
								saveButtonDisabled
							}
							onClick={handleFilterSave}
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
	fdsFilterClientExtensions: IClientExtensionRenderer[];
	fdsView: FDSViewType;
	fdsViewsURL: string;
	namespace: string;
}

function Filters({fdsFilterClientExtensions, fdsView, namespace}: IProps) {
	const [fields, setFields] = useState<IField[]>([]);
	const [filters, setFilters] = useState<IFilter[]>([]);

	useEffect(() => {
		const getFilters = async () => {
			const response = await fetch(
				`${API_URL.FDS_VIEWS}/${fdsView.id}?nestedFields=${OBJECT_RELATIONSHIP.FDS_VIEW_FDS_DATE_FILTER},${OBJECT_RELATIONSHIP.FDS_VIEW_FDS_DYNAMIC_FILTER},${OBJECT_RELATIONSHIP.FDS_VIEW_FDS_CLIENT_EXTENSION_FILTER}`
			);

			const responseJSON = await response.json();

			const clientExtensionFiltersOrderer = responseJSON[
				OBJECT_RELATIONSHIP.FDS_VIEW_FDS_CLIENT_EXTENSION_FILTER
			] as IClientExtensionFilter[];
			const dateFiltersOrderer = responseJSON[
				OBJECT_RELATIONSHIP.FDS_VIEW_FDS_DATE_FILTER
			] as IDateFilter[];
			const dynamicFiltersOrderer = responseJSON[
				OBJECT_RELATIONSHIP.FDS_VIEW_FDS_DYNAMIC_FILTER
			] as ISelectionFilter[];

			let filtersOrdered: FilterCollection = [
				...clientExtensionFiltersOrderer.map((item) => ({
					...item,
					displayType: Liferay.Language.get(
						'client-extension-filter'
					),
					filterType: EFilterType.CLIENT_EXTENSION,
				})),
				...dateFiltersOrderer.map((item) => ({
					...item,
					displayType: Liferay.Language.get('date-filter'),
					filterType: EFilterType.DATE_RANGE,
				})),
				...dynamicFiltersOrderer.map((item) => ({
					...item,
					displayType: Liferay.Language.get('dynamic-filter'),
					filterType: EFilterType.SELECTION,
				})),
			];

			if (fdsView.fdsFiltersOrder) {
				const order = fdsView.fdsFiltersOrder.split(',');

				let notOrdered: FilterCollection = [];

				notOrdered = filtersOrdered.filter(
					(filter) => !order.includes(String(filter.id))
				);

				filtersOrdered = fdsView.fdsFiltersOrder
					.split(',')
					.map((fdsFilterId) =>
						filtersOrdered.find(
							(filter) => filter.id === Number(fdsFilterId)
						)
					)
					.filter(Boolean) as FilterCollection;

				filtersOrdered = [...notOrdered, ...filtersOrdered];
			}

			setFilters(
				filtersOrdered.map((filter) => {
					return {
						...filter,
						label: filter.label || '',
					};
				})
			);
		};

		getFields(fdsView).then((newFields) => {
			if (newFields) {
				setFields(newFields as IField[]);
			}
		});

		getFilters();
	}, [fdsView]);

	const updateFDSFiltersOrder = async ({
		fdsFiltersOrder,
	}: {
		fdsFiltersOrder: string;
	}) => {
		const response = await fetch(
			`${API_URL.FDS_VIEWS}/by-external-reference-code/${fdsView.externalReferenceCode}`,
			{
				body: JSON.stringify({
					fdsFiltersOrder,
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

			return null;
		}

		const responseJSON = await response.json();

		const storedFDSFiltersOrder = responseJSON?.fdsFiltersOrder;

		if (
			storedFDSFiltersOrder &&
			storedFDSFiltersOrder === fdsFiltersOrder
		) {
			openDefaultSuccessToast();
		}
		else {
			openDefaultFailureToast();
		}
	};

	const onCreationButtonClick = (filterType: EFilterType) => {
		const availableFields = fields.filter(
			(item) =>
				filterType === EFilterType.CLIENT_EXTENSION ||
				(filterType === EFilterType.SELECTION &&
					item.type === EFieldType.STRING &&
					!item.format) ||
				(filterType === EFilterType.DATE_RANGE &&
					(item.format === EFieldFormat.DATE ||
						item.format === EFieldFormat.DATE_TIME))
		);

		if (!availableFields.length) {
			openModal({
				bodyHTML: Liferay.Language.get(
					'there-are-no-fields-compatible-with-this-type-of-filter'
				),
				buttons: [
					{
						displayType: 'primary',
						label: Liferay.Language.get('close'),
						onClick: ({processClose}: {processClose: Function}) => {
							processClose();
						},
					},
				],
				status: 'info',
				title: Liferay.Language.get('no-fields-available'),
			});
		}
		else {
			openModal({
				className: 'overflow-auto',
				contentComponent: ({closeModal}: {closeModal: Function}) => (
					<AddFDSFilterModalContent
						closeModal={closeModal}
						fdsFilterClientExtensions={fdsFilterClientExtensions}
						fdsView={fdsView}
						fieldNames={filters.map((filter) => filter.fieldName)}
						fields={availableFields}
						filterType={filterType}
						namespace={namespace}
						onSave={(newfilter) => {
							if (newfilter.label === undefined) {
								newfilter.label = '';
							}
							setFilters([...filters, newfilter]);
						}}
					/>
				),
				disableAutoClose: true,
			});
		}
	};

	const handleEdit = ({
		item,
	}: {
		item: IClientExtensionFilter | IDateFilter | ISelectionFilter;
	}) =>
		openModal({
			className: 'overflow-auto',
			contentComponent: ({closeModal}: {closeModal: Function}) => (
				<AddFDSFilterModalContent
					closeModal={closeModal}
					fdsFilterClientExtensions={fdsFilterClientExtensions}
					fdsView={fdsView}
					fieldNames={filters.map((filter) => filter.fieldName)}
					fields={fields}
					filter={item}
					filterType={item.filterType}
					namespace={namespace}
					onSave={(newfilter) => {
						const newFilters = filters.map((item) => {
							if (item.id === newfilter.id) {
								if (
									item.filterType === EFilterType.DATE_RANGE
								) {
									(newfilter as IDateFilter).from =
										(newfilter as IDateFilter).from || '';
									(newfilter as IDateFilter).to =
										(newfilter as IDateFilter).to || '';
								}

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
					displayType: 'danger',
					label: Liferay.Language.get('delete'),
					onClick: ({processClose}: {processClose: Function}) => {
						processClose();

						const url = `${
							item.filterType === EFilterType.DATE_RANGE
								? API_URL.FDS_DATE_FILTERS
								: item.filterType ===
								  EFilterType.CLIENT_EXTENSION
								? API_URL.FDS_CLIENT_EXTENSION_FILTERS
								: API_URL.FDS_DYNAMIC_FILTERS
						}/${item.id}`;

						fetch(url, {
							method: 'DELETE',
						})
							.then(() => {
								openDefaultSuccessToast();

								setFilters(
									filters.filter(
										(filter: IFilter) =>
											filter.id !== item.id
									)
								);
							})
							.catch(openDefaultFailureToast);
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
						label: Liferay.Language.get('client-extension'),
						onClick: () =>
							onCreationButtonClick(EFilterType.CLIENT_EXTENSION),
					},
					{
						label: Liferay.Language.get('date-range'),
						onClick: () =>
							onCreationButtonClick(EFilterType.DATE_RANGE),
					},
					{
						label: Liferay.Language.get('selection'),
						onClick: () =>
							onCreationButtonClick(EFilterType.SELECTION),
					},
				]}
				fields={[
					{
						label: Liferay.Language.get('name'),
						name: 'label',
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
				onOrderChange={({order}: {order: string}) => {
					updateFDSFiltersOrder({fdsFiltersOrder: order});
				}}
				title={Liferay.Language.get('filters')}
			/>
		</ClayLayout.ContainerFluid>
	);
}

export default Filters;
