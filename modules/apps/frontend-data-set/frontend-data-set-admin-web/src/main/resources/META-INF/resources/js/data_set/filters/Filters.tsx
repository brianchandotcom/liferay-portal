/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import ClayModal from '@clayui/modal';
import {IClientExtensionRenderer} from '@liferay/frontend-data-set-web';
import {fetch, openModal, sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import OrderableTable from '../../components/OrderableTable';
import {API_URL, OBJECT_RELATIONSHIP} from '../../utils/constants';
import openDefaultFailureToast from '../../utils/openDefaultFailureToast';
import openDefaultSuccessToast from '../../utils/openDefaultSuccessToast';
import {
	EFieldFormat,
	EFieldType,
	EFilterType,
	IDateFilter,
	IField,
	IFilter,
} from '../../utils/types';
import {IDataSetSectionProps} from '../DataSet';
import ClientExtensionFilterModal from './modals/ClientExtensionFilter';
import DateRangeFilterModal from './modals/DateRangeFilter';
import SelectionFilterModal from './modals/selection_filter/SelectionFilter';

import '../../../css/Filters.scss';
import {IDataSet} from '../../DataSets';
import {FDSViewType} from '../../FDSViews';
import sortItems from '../../utils/sortItems';

const FILTERS_ORDER_OBJECT_FIELD_NAME = Liferay.FeatureFlags['LPD-15729']
	? 'filtersOrder'
	: 'fdsFiltersOrder';

const FILTER_TYPES = {
	[EFilterType.CLIENT_EXTENSION]: {
		Component: ClientExtensionFilterModal,
		availableFieldsFilter: (item: IField) => !!item,
		dataSetRelationship:
			OBJECT_RELATIONSHIP.DATA_SET_CLIENT_EXTENSION_FILTER,
		dataSetRelationshipId:
			OBJECT_RELATIONSHIP.DATA_SET_CLIENT_EXTENSION_FILTER_ID,
		displayType: Liferay.Language.get('client-extension-filter'),
		label: Liferay.Language.get('client-extension'),
		url: API_URL.CLIENT_EXTENSION_FILTERS,
	},
	[EFilterType.DATE_RANGE]: {
		Component: DateRangeFilterModal,
		availableFieldsFilter: (item: IField) =>
			item.format === EFieldFormat.DATE ||
			item.format === EFieldFormat.DATE_TIME,
		dataSetRelationship: OBJECT_RELATIONSHIP.DATA_SET_DATE_FILTER,
		dataSetRelationshipId: OBJECT_RELATIONSHIP.DATA_SET_DATE_FILTER_ID,
		displayType: Liferay.Language.get('date-filter'),
		label: Liferay.Language.get('date-range'),
		url: API_URL.DATE_FILTERS,
	},
	[EFilterType.SELECTION]: {
		Component: SelectionFilterModal,
		availableFieldsFilter: (item: IField) =>
			item.type === EFieldType.STRING && !item.format,
		dataSetRelationship: OBJECT_RELATIONSHIP.DATA_SET_SELECTION_FILTER,
		dataSetRelationshipId: OBJECT_RELATIONSHIP.DATA_SET_SELECTION_FILTER_ID,
		displayType: Liferay.Language.get('dynamic-filter'),
		label: Liferay.Language.get('selection'),
		url: API_URL.SELECTION_FILTERS,
	},
};

type FilterCollection = Array<IFilter>;

export interface IFilterModal {
	closeModal: Function;
	fieldNames?: string[];
	fields: IField[];
	filter?: IFilter;
	filterClientExtensions: IClientExtensionRenderer[];
	namespace: string;
	onSave: Function;
	restApplications?: string[];
}

function NewFilterModalContent({
	closeModal,
	dataSet,
	fieldNames,
	fields,
	filter,
	filterClientExtensions = [],
	filterType,
	namespace,
	onSave,
	restApplications,
}: {
	closeModal: Function;
	dataSet: IDataSet | FDSViewType;
	fieldNames?: string[];
	fields: IField[];
	filter?: IFilter;
	filterClientExtensions?: IClientExtensionRenderer[];
	filterType?: EFilterType;
	namespace: string;
	onSave: (newFilter: IFilter) => void;
	restApplications: string[];
}) {
	const {Component, dataSetRelationshipId, displayType} =
		FILTER_TYPES[filterType as EFilterType];

	const saveFilter = async (formData: any) => {
		formData = {
			...formData,
			[dataSetRelationshipId]: dataSet.id,
		};

		let url = FILTER_TYPES[filterType as EFilterType].url;
		let method = 'POST';

		if (filter) {
			method = 'PUT';
			url = `${url}/${filter.id}`;
		}

		const response = await fetch(url, {
			body: JSON.stringify(formData),
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json',
			},
			method,
		});

		if (!response.ok) {
			openDefaultFailureToast();

			return null;
		}

		const responseJSON = await response.json();

		openDefaultSuccessToast();

		onSave({...responseJSON, displayType, filterType});

		closeModal();
	};

	return (
		<>
			<ClayModal.Header>
				{filter &&
					sub(Liferay.Language.get('edit-x-filter'), [filter.label])}

				{!filter && <Component.Header />}
			</ClayModal.Header>

			<Component.Body
				closeModal={closeModal}
				fieldNames={fieldNames}
				fields={fields}
				filter={filter}
				filterClientExtensions={filterClientExtensions}
				namespace={namespace}
				onSave={(formData: any) => saveFilter(formData)}
				restApplications={restApplications}
			/>
		</>
	);
}

function Filters({
	dataSet,
	fieldTreeItems: fields,
	filterClientExtensions,
	namespace,
	restApplications,
}: IDataSetSectionProps) {
	const [filters, setFilters] = useState<IFilter[]>([]);
	useEffect(() => {
		const getFilters = async () => {
			const response = await fetch(
				`${API_URL.DATA_SETS}/${
					dataSet.id
				}?nestedFields=${Object.values(FILTER_TYPES)
					.map((filter) => filter.dataSetRelationship)
					.join(',')}`
			);

			const responseJSON = await response.json();

			let filtersOrdered: FilterCollection = [];

			Object.keys(FILTER_TYPES).forEach((type) => {
				const filtersArray =
					responseJSON[
						FILTER_TYPES[type as EFilterType].dataSetRelationship
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
				responseJSON[FILTERS_ORDER_OBJECT_FIELD_NAME],
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

	const updateFiltersOrder = async ({
		filtersOrder,
	}: {
		filtersOrder: string;
	}) => {
		const response = await fetch(
			`${API_URL.DATA_SETS}/by-external-reference-code/${dataSet.externalReferenceCode}`,
			{
				body: JSON.stringify({
					[FILTERS_ORDER_OBJECT_FIELD_NAME]: filtersOrder,
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

		const storedFiltersOrder =
			responseJSON[FILTERS_ORDER_OBJECT_FIELD_NAME];

		if (
			filters &&
			storedFiltersOrder &&
			storedFiltersOrder === filtersOrder
		) {
			setFilters(
				sortItems(filters, storedFiltersOrder, true) as FilterCollection
			);

			openDefaultSuccessToast();
		}
		else {
			openDefaultFailureToast();
		}
	};

	const onCreationButtonClick = (filterType: EFilterType) => {
		const availableFields = fields.filter((item) =>
			FILTER_TYPES[filterType as EFilterType].availableFieldsFilter(item)
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
				size: Liferay.FeatureFlags['LPD-10754'] ? 'lg' : 'md',
				status: 'info',
				title: Liferay.Language.get('no-fields-available'),
			});
		}
		else {
			openModal({
				className: 'overflow-auto',
				contentComponent: ({closeModal}: {closeModal: Function}) => (
					<NewFilterModalContent
						closeModal={closeModal}
						dataSet={dataSet}
						fieldNames={filters.map((filter) => filter.fieldName)}
						fields={availableFields}
						filterClientExtensions={filterClientExtensions}
						filterType={filterType}
						namespace={namespace}
						onSave={(newfilter) => {
							if (newfilter.label === undefined) {
								newfilter.label = '';
							}
							setFilters([...filters, newfilter]);
						}}
						restApplications={restApplications}
					/>
				),
				disableAutoClose: true,
				size: Liferay.FeatureFlags['LPD-10754'] ? 'lg' : 'md',
			});
		}
	};

	const onEdit = ({item}: {item: IFilter}) =>
		openModal({
			className: 'overflow-auto',
			contentComponent: ({closeModal}: {closeModal: Function}) => (
				<NewFilterModalContent
					closeModal={closeModal}
					dataSet={dataSet}
					fieldNames={filters.map((filter) => filter.fieldName)}
					fields={fields}
					filter={item}
					filterClientExtensions={filterClientExtensions}
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
					restApplications={restApplications}
				/>
			),
			disableAutoClose: true,
			size: Liferay.FeatureFlags['LPD-10754'] ? 'lg' : 'md',
		});

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
			size: Liferay.FeatureFlags['LPD-10754'] ? 'lg' : 'md',
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
						onClick: onEdit,
					},
					{
						icon: 'trash',
						label: Liferay.Language.get('delete'),
						onClick: onDelete,
					},
				]}
				creationMenuItems={Object.keys(FILTER_TYPES).map((type) => ({
					label: FILTER_TYPES[type as EFilterType].label,
					onClick: () => onCreationButtonClick(type as EFilterType),
				}))}
				creationMenuLabel={Liferay.Language.get('new-filter')}
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
				noItemsButtonLabel={Liferay.Language.get('new-filter')}
				noItemsDescription={Liferay.Language.get(
					'start-creating-a-filter-to-display-specific-data'
				)}
				noItemsTitle={Liferay.Language.get(
					'no-default-filters-were-created'
				)}
				onOrderChange={({order}: {order: string}) => {
					updateFiltersOrder({filtersOrder: order});
				}}
				title={Liferay.Language.get('filters')}
			/>
		</ClayLayout.ContainerFluid>
	);
}

export default Filters;
