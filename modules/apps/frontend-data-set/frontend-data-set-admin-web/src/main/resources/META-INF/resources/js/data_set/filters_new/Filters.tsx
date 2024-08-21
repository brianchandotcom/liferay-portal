/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import ClayModal from '@clayui/modal';
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
import ClientExtensionFilterModalContent from './components/ClientExtensionFilter';
import DateRangeFilterModalContent from './components/DateRangeFilter';
import SelectionFilterModalContent from './components/selection_filter/SelectionFilter';

import '../../../css/Filters.scss';
import FilterList from './components/FilterList';
import {IDataSet} from '../../DataSets';
import {FDSViewType} from '../../FDSViews';
import sortItems from '../../utils/sortItems';
import ClayBreadcrumb from '@clayui/breadcrumb';

const FILTER_TYPES: Record<EFilterType, IFilterTypeProps> = {
	[EFilterType.CLIENT_EXTENSION]: {
		Component: ClientExtensionFilterModalContent,
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
		Component: DateRangeFilterModalContent,
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
		Component: SelectionFilterModalContent,
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

interface IPropsAddFDSFilterModalContent {
	closeModal: Function;
	dataSet: IDataSet | FDSViewType;
	fdsFilterClientExtensions?: IClientExtensionRenderer[];
	fieldNames?: string[];
	fields: IField[];
	filter?: IFilter | ISelectionFilter;
	filterType?: EFilterType;
	namespace: string;
	onSave: (newFilter: IFilter) => void;
	restApplications: string[];
}

function AddFDSFilterModalContent({
	closeModal,
	dataSet,
	fdsFilterClientExtensions = [],
	fieldNames,
	fields,
	filter,
	filterType,
	namespace,
	onSave,
	restApplications,
}: IPropsAddFDSFilterModalContent) {
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
				fdsFilterClientExtensions={fdsFilterClientExtensions}
				fieldNames={fieldNames}
				fields={fields}
				filter={filter}
				namespace={namespace}
				onSave={(formData: any) => saveFDSFilter(formData)}
				restApplications={restApplications}
			/>
		</>
	);
}

function FiltersNew({
	dataSet,
	fdsFilterClientExtensions,
	fieldTreeItems: fields,
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
				size: 'lg',
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
						dataSet={dataSet}
						fdsFilterClientExtensions={fdsFilterClientExtensions}
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
						restApplications={restApplications}
					/>
				),
				disableAutoClose: true,
				size: 'lg',
			});
		}
	};

	const onEdit = ({item}: {item: IFilter}) =>
		openModal({
			className: 'overflow-auto',
			contentComponent: ({closeModal}: {closeModal: Function}) => (
				<AddFDSFilterModalContent
					closeModal={closeModal}
					dataSet={dataSet}
					fdsFilterClientExtensions={fdsFilterClientExtensions}
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
					restApplications={restApplications}
				/>
			),
			disableAutoClose: true,
			size: 'lg',
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

	return (
		<ClayLayout.ContainerFluid>

			<ClayBreadcrumb className='my-2' items={[]} />

			<FilterList
				createFilter={onCreationButtonClick}
				filterTypes={FILTER_TYPES}
				filters={filters}
				deleteFilter={onDelete}
				editFilter={onEdit}
				updateFiltersOrder={updateFDSFiltersOrder}
			/>

		</ClayLayout.ContainerFluid>
	);
}

export default FiltersNew;
