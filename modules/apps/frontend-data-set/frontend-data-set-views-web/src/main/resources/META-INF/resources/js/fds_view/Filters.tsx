/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import ClayModal from '@clayui/modal';
import classNames from 'classnames';
import {format} from 'date-fns';
import {IClientExtensionRenderer, fetch, openModal, sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {API_URL, OBJECT_RELATIONSHIP} from '../Constants';
import {FDSViewType} from '../FDSViews';
import {getAllPicklists, getFields} from '../api';
import OrderableTable from '../components/OrderableTable';
import ValidationFeedback from '../components/ValidationFeedback';
import ClientExtensionFilterModalContent from '../components/modal_content/ClientExtensionFilter';
import DateRangeFilterModalContent from '../components/modal_content/DateRangeFilter';
import SelectionFilterModalContent from '../components/modal_content/SelectionFilter';
import {
	EFieldFormat,
	EFilterType,
	IClientExtensionFilter,
	IDateFilter,
	IField,
	IFilter,
	IPickList,
	ISelectionFilter,
} from '../types';
import openDefaultFailureToast from '../utils/openDefaultFailureToast';
import openDefaultSuccessToast from '../utils/openDefaultSuccessToast';

import '../../css/Filters.scss';

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
					(cx: IClientExtensionRenderer) =>
						cx.externalReferenceCode ===
						(filter as IClientExtensionFilter)
							.fdsFilterClientExtensionERC
			  )
			: undefined
	);
	const [fieldInUseValidationError, setFieldInUseValidationError] = useState<
		boolean
	>();
	const [from, setFrom] = useState<string>(
		(filter as IDateFilter)?.from ?? format(new Date(), 'yyyy-MM-dd')
	);
	const [includeMode, setIncludeMode] = useState<string>(
		filter
			? (filter as ISelectionFilter)?.include
				? 'include'
				: 'exclude'
			: 'include'
	);
	const [isValidDateRange, setIsValidDateRange] = useState<boolean>(true);
	const [saveButtonDisabled, setSaveButtonDisabled] = useState<boolean>();
	const [multiple, setMultiple] = useState<boolean>(
		(filter as ISelectionFilter)?.multiple ?? true
	);
	const [name, setName] = useState(filter?.name || '');
	const [picklists, setPicklists] = useState<IPickList[]>([]);
	const [preselectedValues, setPreselectedValues] = useState<any[]>([]);
	const [selectedField, setSelectedField] = useState<IField | null>(
		fields.find((item) => item.name === filter?.fieldName) || null
	);
	const [selectedPicklist, setSelectedPicklist] = useState<IPickList>();
	const [to, setTo] = useState<string>(
		(filter as IDateFilter)?.to ?? format(new Date(), 'yyyy-MM-dd')
	);

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
							(filter as ISelectionFilter).preselectedValues ||
								'[]'
						).includes(item.id)
					)
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
			name: name || selectedField.label,
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
				listTypeDefinitionId: selectedPicklist?.id,
				multiple,
				preselectedValues: preselectedValues.map((item) => item.id),
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

	const CellRendererDropdown = ({
		cellRenderers,
		inUseFields,
		namespace,
		onItemClick,
	}: {
		cellRenderers: IField[];
		inUseFields: (string | undefined)[];
		namespace: string;
		onItemClick: Function;
	}) => {
		return (
			<ClayDropDown
				closeOnClick
				menuElementAttrs={{
					className: 'fds-cell-renderers-dropdown-menu',
				}}
				trigger={
					<ClayButton
						aria-labelledby={`${namespace}cellRenderersLabel`}
						className="form-control form-control-select form-control-select-secondary"
						displayType="secondary"
					>
						{selectedField
							? selectedField.label
							: Liferay.Language.get('select')}
					</ClayButton>
				}
			>
				<ClayDropDown.ItemList items={cellRenderers} role="listbox">
					{cellRenderers.map((cellRenderer) => (
						<ClayDropDown.Item
							className="align-items-center d-flex justify-content-between"
							disabled={
								!!filter ||
								(filterType === EFilterType.SELECTION &&
									!picklists.length)
							}
							key={cellRenderer.name}
							onClick={() => onItemClick(cellRenderer)}
							roleItem="option"
						>
							{cellRenderer.label}

							{inUseFields.includes(cellRenderer.name) && (
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
					sub(Liferay.Language.get('edit-x-filter'), [filter.name])}

				{!filter && (
					<>
						{filterType === EFilterType.CLIENT_EXTENSION && (
							<ClientExtensionFilterModalContent.Header />
						)}

						{filterType === EFilterType.DATE_RANGE && (
							<DateRangeFilterModalContent.Header />
						)}

						{filterType !== EFilterType.SELECTION && (
							<DateRangeFilterModalContent.Header />
						)}
					</>
				)}
			</ClayModal.Header>

			<ClayModal.Body>
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
							selectedField?.label || Liferay.Language.get('name')
						}
						value={name}
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

					<CellRendererDropdown
						cellRenderers={fields}
						inUseFields={inUseFields}
						namespace={namespace}
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
								onPreselectedValuesChange={setPreselectedValues}
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

			setFilters(filtersOrdered);
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
					item.format === EFieldFormat.STRING) ||
				(filterType === EFilterType.DATE_RANGE &&
					(item.format === EFieldFormat.DATE_TIME ||
						item.format === EFieldFormat.DATE))
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
						onSave={(newfilter) =>
							setFilters([...filters, newfilter])
						}
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
				onOrderChange={({order}: {order: string}) => {
					updateFDSFiltersOrder({fdsFiltersOrder: order});
				}}
				title={Liferay.Language.get('filters')}
			/>
		</ClayLayout.ContainerFluid>
	);
}

export default Filters;
