/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayBreadcrumb from '@clayui/breadcrumb';
import ClayLayout from '@clayui/layout';
import {IClientExtensionRenderer} from '@liferay/frontend-data-set-web';
import {fetch, openModal, sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {
	API_URL,
	DEFAULT_FETCH_HEADERS,
	OBJECT_RELATIONSHIP,
} from '../../utils/constants';
import openDefaultFailureToast from '../../utils/openDefaultFailureToast';
import openDefaultSuccessToast from '../../utils/openDefaultSuccessToast';
import {
	EFieldFormat,
	EFieldType,
	EFilterType,
	IDateFilter,
	IField,
	IFilter,
	IFilterTypeProps,
	ISelectionFilter,
} from '../../utils/types';
import {IDataSetSectionProps} from '../DataSet';
import ClientExtensionFilterFormContent from './components/ClientExtensionFilter';
import DateRangeFilterFormContent from './components/DateRangeFilter';
import SelectionFilterFormContent from './components/selection_filter/SelectionFilter';

import '../../../css/Filters.scss';
import {IDataSet} from '../../DataSets';
import {FDSViewType} from '../../FDSViews';
import sortItems from '../../utils/sortItems';
import FilterList from './components/FilterList';

const FILTER_MODE = {
	CREATION: 'filter-creation',
	EDITION: 'filter-edition',
	LIST: 'filter-list',
};

const FILTER_TYPES: Record<EFilterType, IFilterTypeProps> = {
	[EFilterType.CLIENT_EXTENSION]: {
		Component: ClientExtensionFilterFormContent,
		availableFieldsFilter: (item: IField) => !!item,
		displayType: Liferay.Language.get('client-extension-filter'),
		fdsViewRelationship:
			OBJECT_RELATIONSHIP.DATA_SET_CLIENT_EXTENSION_FILTER,
		fdsViewRelationshipId:
			OBJECT_RELATIONSHIP.DATA_SET_CLIENT_EXTENSION_FILTER_ID,
		label: Liferay.Language.get('client-extension'),
		url: API_URL.CLIENT_EXTENSION_FILTERS,
	},
	[EFilterType.DATE_RANGE]: {
		Component: DateRangeFilterFormContent,
		availableFieldsFilter: (item: IField) =>
			item.format === EFieldFormat.DATE ||
			item.format === EFieldFormat.DATE_TIME,
		displayType: Liferay.Language.get('date-filter'),
		fdsViewRelationship: OBJECT_RELATIONSHIP.DATA_SET_DATE_FILTER,
		fdsViewRelationshipId: OBJECT_RELATIONSHIP.DATA_SET_DATE_FILTER_ID,
		label: Liferay.Language.get('date-range'),
		url: API_URL.DATE_FILTERS,
	},
	[EFilterType.SELECTION]: {
		Component: SelectionFilterFormContent,
		availableFieldsFilter: (item: IField) =>
			item.type === EFieldType.STRING && !item.format,
		displayType: Liferay.Language.get('dynamic-filter'),
		fdsViewRelationship: OBJECT_RELATIONSHIP.DATA_SET_SELECTION_FILTER,
		fdsViewRelationshipId: OBJECT_RELATIONSHIP.DATA_SET_SELECTION_FILTER_ID,
		label: Liferay.Language.get('selection'),
		url: API_URL.SELECTION_FILTERS,
	},
};

type FilterCollection = Array<IFilter>;

interface IPropsFilterFormComponent {
	dataSet: IDataSet | FDSViewType;
	dismiss: Function;
	fdsFilterClientExtensions?: IClientExtensionRenderer[];
	fieldNames?: string[];
	fields: IField[];
	filter?: IFilter | ISelectionFilter;
	filterType?: EFilterType;
	namespace: string;
	onSave: (newFilter: IFilter) => void;
	resolvedRESTSchemas: string[];
	restApplications: string[];
}

function FilterFormComponent({
	dataSet,
	dismiss,
	fdsFilterClientExtensions = [],
	fieldNames,
	fields,
	filter,
	filterType,
	namespace,
	onSave,
	resolvedRESTSchemas,
	restApplications,
}: IPropsFilterFormComponent) {
	const {Component, displayType, fdsViewRelationshipId} =
		FILTER_TYPES[filterType as EFilterType];

	const saveFDSFilter = async (formData: any) => {
		formData = {
			...formData,
			[fdsViewRelationshipId]: dataSet.id,
		};

		let url = FILTER_TYPES[filterType as EFilterType].url;
		let method = 'POST';

		if (filter) {
			method = 'PUT';
			url = `${url}/${filter.id}`;
		}

		const response = await fetch(url, {
			body: JSON.stringify(formData),
			headers: DEFAULT_FETCH_HEADERS,
			method,
		});

		if (!response.ok) {
			openDefaultFailureToast();

			return null;
		}

		const responseJSON = await response.json();

		openDefaultSuccessToast();

		onSave({...responseJSON, displayType, filterType});
	};

	return (
		<>
			<ClayLayout.SheetHeader className="mb-4">
				<h2>
					{filter &&
						sub(Liferay.Language.get('edit-x-filter'), [
							filter.label,
						])}

					{!filter && <Component.Header />}
				</h2>
			</ClayLayout.SheetHeader>

			<Component.Body
				dismiss={dismiss}
				fdsFilterClientExtensions={fdsFilterClientExtensions}
				fieldNames={fieldNames}
				fields={fields}
				filter={filter}
				namespace={namespace}
				onSave={(formData: any) => saveFDSFilter(formData)}
				resolvedRESTSchemas={resolvedRESTSchemas}
				restApplications={restApplications}
			/>
		</>
	);
}

function Filters({
	dataSet,
	fdsFilterClientExtensions,
	fieldTreeItems: fields,
	namespace,
	resolvedRESTSchemas,
	restApplications,
}: IDataSetSectionProps) {
	const [activeFilter, setActiveFilter] = useState<IFilter | null>(null);
	const [activeFilterType, setActiveFilterType] =
		useState<EFilterType | null>(null);
	const [activeMode, setActiveMode] = useState(FILTER_MODE.LIST);
	const [filters, setFilters] = useState<IFilter[]>([]);
	const [availableFields, setAvailableFields] = useState(fields);
	useEffect(() => {
		const getFilters = async () => {
			const response = await fetch(
				`${API_URL.DATA_SETS}/${
					dataSet.id
				}?nestedFields=${Object.values(FILTER_TYPES)
					.map((filter) => filter.fdsViewRelationship)
					.join(',')}`,
				{
					headers: DEFAULT_FETCH_HEADERS,
				}
			);

			const responseJSON = await response.json();

			let filtersOrdered: FilterCollection = [];

			Object.keys(FILTER_TYPES).forEach((type) => {
				const filtersArray =
					responseJSON[
						FILTER_TYPES[type as EFilterType].fdsViewRelationship
					];

				filtersArray.forEach((filter: any) => {
					filtersOrdered.push({
						...filter,
						displayType:
							FILTER_TYPES[type as EFilterType].displayType,
						filterType: type as EFilterType,
					});
				});
			});

			filtersOrdered = sortItems(
				filtersOrdered,
				responseJSON.fdsFiltersOrder,
				true
			) as FilterCollection;

			setFilters(
				filtersOrdered.map((filter) => {
					return {
						...filter,
						label: filter.label || '',
					};
				})
			);
		};

		getFilters();
	}, [dataSet]);

	const updateFDSFiltersOrder = async ({
		fdsFiltersOrder,
	}: {
		fdsFiltersOrder: string;
	}) => {
		const response = await fetch(
			`${API_URL.DATA_SETS}/by-external-reference-code/${dataSet.externalReferenceCode}`,
			{
				body: JSON.stringify({
					fdsFiltersOrder,
				}),
				headers: DEFAULT_FETCH_HEADERS,
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
			filters &&
			storedFDSFiltersOrder &&
			storedFDSFiltersOrder === fdsFiltersOrder
		) {
			setFilters(
				sortItems(
					filters,
					storedFDSFiltersOrder,
					true
				) as FilterCollection
			);

			openDefaultSuccessToast();
		}
		else {
			openDefaultFailureToast();
		}
	};

	const noFilterClientExtensionsAvailableModal = () => {
		openModal({
			bodyHTML: Liferay.Language.get(
				'no-frontend-data-set-filter-client-extensions-are-available.-add-a-client-extension-first-in-order-to-create-a-filter'
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
			size: 'lg',
			status: 'info',
			title: Liferay.Language.get(
				'no-frontend-data-set-filter-client-extensions-available'
			),
		});
	};

	const onCreationButtonClick = (filterType: EFilterType) => {
		const availableFieldsList = fields.filter((item) =>
			FILTER_TYPES[filterType as EFilterType].availableFieldsFilter(item)
		);

		setAvailableFields(availableFieldsList);

		if (!availableFieldsList.length) {
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
				size: 'lg',
				status: 'info',
				title: Liferay.Language.get('no-fields-available'),
			});
		}
		else if (
			filterType === EFilterType.CLIENT_EXTENSION &&
			!fdsFilterClientExtensions.length
		) {
			noFilterClientExtensionsAvailableModal();
		}
		else {
			setActiveFilterType(filterType);
			setActiveMode(FILTER_MODE.CREATION);
		}
	};

	const onEdit = ({item}: {item: IFilter}) => {
		if (
			item.filterType === EFilterType.CLIENT_EXTENSION &&
			!fdsFilterClientExtensions.length
		) {
			noFilterClientExtensionsAvailableModal();
		}
		else {
			setActiveMode(FILTER_MODE.EDITION);
			setActiveFilter(item);
		}
	};

	const onDelete = async ({item}: {item: IFilter}) => {
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
							FILTER_TYPES[item.filterType as EFilterType].url
						}/${item.id}`;

						fetch(url, {
							headers: DEFAULT_FETCH_HEADERS,
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
			size: 'lg',
			status: 'warning',
			title: Liferay.Language.get('delete-filter'),
		});
	};

	const getBreadcrumbItems = () => {
		const breadcrumbItems: React.ComponentProps<
			typeof ClayBreadcrumb
		>['items'] = [
			{
				active: activeMode === FILTER_MODE.LIST ? true : false,
				label: Liferay.Language.get('filters'),
				onClick: () => {
					setActiveMode(FILTER_MODE.LIST);
				},
			},
		];

		if (activeMode === FILTER_MODE.CREATION) {
			let label = '';
			if (activeFilterType === EFilterType.CLIENT_EXTENSION) {
				label = Liferay.Language.get('new-client-extension-filter');
			}
			if (activeFilterType === EFilterType.DATE_RANGE) {
				label = Liferay.Language.get('new-date-range-filter');
			}
			if (activeFilterType === EFilterType.SELECTION) {
				label = Liferay.Language.get('new-selection-filter');
			}

			breadcrumbItems.push({
				active: true,
				label,
			});
		}

		if (activeMode === FILTER_MODE.EDITION) {
			breadcrumbItems.push({
				active: true,
				label: activeFilter!.label,
			});
		}

		return breadcrumbItems;
	};

	return (
		<ClayLayout.ContainerFluid>
			<ClayBreadcrumb className="my-2" items={getBreadcrumbItems()} />

			{activeMode === FILTER_MODE.CREATION && (
				<ClayLayout.Sheet>
					{activeFilterType && (
						<FilterFormComponent
							dataSet={dataSet}
							dismiss={() => setActiveMode(FILTER_MODE.LIST)}
							fdsFilterClientExtensions={
								fdsFilterClientExtensions
							}
							fieldNames={filters.map(
								(filter) => filter.fieldName
							)}
							fields={availableFields}
							filterType={activeFilterType}
							namespace={namespace}
							onSave={(newfilter) => {
								if (newfilter.label === undefined) {
									newfilter.label = '';
								}
								setFilters([...filters, newfilter]);
								setActiveMode(FILTER_MODE.LIST);
							}}
							restApplications={restApplications}
							resolvedRESTSchemas={resolvedRESTSchemas}
						/>
					)}
				</ClayLayout.Sheet>
			)}

			{activeMode === FILTER_MODE.EDITION && (
				<ClayLayout.Sheet>
					{activeFilter && (
						<FilterFormComponent
							dataSet={dataSet}
							dismiss={() => setActiveMode(FILTER_MODE.LIST)}
							fdsFilterClientExtensions={
								fdsFilterClientExtensions
							}
							fieldNames={filters.map(
								(filter) => filter.fieldName
							)}
							fields={fields}
							filter={activeFilter}
							filterType={activeFilter.filterType}
							namespace={namespace}
							onSave={(newfilter) => {
								const newFilters = filters.map((item) => {
									if (item.id === newfilter.id) {
										if (
											item.filterType ===
											EFilterType.DATE_RANGE
										) {
											(newfilter as IDateFilter).from =
												(newfilter as IDateFilter)
													.from || '';
											(newfilter as IDateFilter).to =
												(newfilter as IDateFilter).to ||
												'';
										}

										return {...item, ...newfilter};
									}

									return item;
								});

								setFilters(newFilters);
								setActiveMode(FILTER_MODE.LIST);
							}}
							restApplications={restApplications}
							resolvedRESTSchemas={resolvedRESTSchemas}
						/>
					)}
				</ClayLayout.Sheet>
			)}

			{activeMode === FILTER_MODE.LIST && (
				<ClayLayout.ContainerFluid className="bg-white mb-4 p-0 rounded-sm">
					<FilterList
						createFilter={onCreationButtonClick}
						deleteFilter={onDelete}
						editFilter={onEdit}
						filterTypes={FILTER_TYPES}
						filters={filters}
						updateFiltersOrder={updateFDSFiltersOrder}
					/>
				</ClayLayout.ContainerFluid>
			)}
		</ClayLayout.ContainerFluid>
	);
}

export default Filters;
