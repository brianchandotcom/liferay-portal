/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {useEffect, useRef, useState} from 'react';
import {Button} from '../../../../../../common/components';
import DropDownWithDrillDown from '../../../../components/DropDownWithDrillDown';
import {
	getDoesNotExpire,
	getEnvironmentType,
	getInstanceSize,
	getProductDescription,
	getStatusActivationTag,
	hasVirtualCluster,
} from '../../utils';
import CheckboxFilter from './components/CheckboxFilter';
import DateFilter from './components/DateFilter';
import ExpirationDate from './components/ExpirationDate';
import KeyTypeFilter from './components/KeyType';
import Search from './components/Search';
import getAvailableFieldsCheckboxs from './components/utils/getAvailableFieldsCheckboxs';

const MAX_UPDATE = 3;

const Filter = ({activationKeys, filtersState: [filters, setFilters]}) => {
	const countFetchActivationKeysRef = useRef(0);

	const [availableFields, setAvailableFields] = useState({
		environmentTypes: [],
		hasDNE: false,
		hasVirtualCluster: false,
		instanceSizes: [],
		productVersions: [],
		status: [],
	});

	useEffect(() => {
		if (activationKeys) {
			countFetchActivationKeysRef.current = ++countFetchActivationKeysRef.current;
		}
	}, [activationKeys]);

	useEffect(() => {
		if (
			activationKeys &&
			countFetchActivationKeysRef?.current < MAX_UPDATE
		) {
			setAvailableFields(() => ({
				environmentTypes: [
					...getAvailableFieldsCheckboxs(
						activationKeys,
						({productName}) => getEnvironmentType(productName)
					),
					...getAvailableFieldsCheckboxs(
						activationKeys,
						({complimentary}) =>
							getProductDescription(complimentary)
					),
				],
				hasDNE: activationKeys?.some(({expirationDate}) =>
					getDoesNotExpire(expirationDate)
				),
				hasVirtualCluster: activationKeys?.some(({licenseEntryType}) =>
					hasVirtualCluster(licenseEntryType)
				),
				instanceSizes: getAvailableFieldsCheckboxs(
					activationKeys,
					({sizing}) => +getInstanceSize(sizing)
				),
				productVersions: getAvailableFieldsCheckboxs(
					activationKeys,
					({productVersion}) => productVersion
				),
				status: getAvailableFieldsCheckboxs(
					activationKeys,
					(activationKey) =>
						getStatusActivationTag(activationKey)?.title
				),
			}));
		}
	}, [activationKeys]);

	return (
		<div className="d-flex flex-column">
			<div className="d-flex">
				<Search setFilters={setFilters} />

				<DropDownWithDrillDown
					initialActiveMenu="x0a0"
					menus={{
						x0a0: [
							{child: 'x0a1', title: 'Key Type'},
							{
								child: 'x0a2',
								disabled: !availableFields.environmentTypes
									.length,
								title: 'Environment Type',
							},
							{child: 'x0a4', title: 'Start Date'},
							{
								child: 'x0a5',
								title: 'Expiration Date',
							},
							{
								child: 'x0a6',
								disabled: !availableFields.status.length,
								title: 'Status',
							},
							{
								child: 'x0a7',
								disabled: !availableFields.productVersions
									.length,
								title: 'Product Version',
							},
							{
								child: 'x0a8',
								disabled: !availableFields.instanceSizes.length,
								title: 'Instance Size',
							},
						],
						x0a1: [
							{
								child: (
									<KeyTypeFilter
										clearInputs={Object.values(
											filters.keyType.value
										).every((value) => !value)}
										hasVirtualCluster={
											availableFields.hasVirtualCluster
										}
										setFilters={setFilters}
									/>
								),
								type: 'component',
							},
						],
						x0a2: [
							{
								child: (
									<CheckboxFilter
										availableItems={
											availableFields.environmentTypes
										}
										clearCheckboxes={
											!filters.environmentTypes.value
												?.length
										}
										setFilters={setFilters}
										updateFilters={(checkedItems) =>
											setFilters((previousFilters) => ({
												...previousFilters,
												environmentTypes: {
													...previousFilters.environmentTypes,
													value: checkedItems,
												},
											}))
										}
									/>
								),
								type: 'component',
							},
						],

						x0a4: [
							{
								child: (
									<DateFilter
										clearInputs={
											!filters.startDate.value
												?.onOrAfter &&
											!filters.startDate.value?.onOrBefore
										}
										updateFilters={(
											onOrAfter,
											onOrBefore
										) =>
											setFilters((previousFilters) => ({
												...previousFilters,
												startDate: {
													...previousFilters.startDate,
													value: {
														onOrAfter,
														onOrBefore,
													},
												},
											}))
										}
									/>
								),
								type: 'component',
							},
						],
						x0a5: [
							{
								child: (
									<ExpirationDate
										clearInputs={
											!filters.expirationDate.value
												?.onOrAfter &&
											!filters.expirationDate.value
												?.onOrBefore
										}
										hasDNE={availableFields.hasDNE}
										setFilters={setFilters}
									/>
								),
								type: 'component',
							},
						],
						x0a6: [
							{
								child: (
									<CheckboxFilter
										availableItems={availableFields.status}
										clearCheckboxes={
											!filters.status.value?.length
										}
										updateFilters={(checkedItems) =>
											setFilters((previousFilters) => ({
												...previousFilters,
												status: {
													...previousFilters.status,
													value: checkedItems,
												},
											}))
										}
									/>
								),
								type: 'component',
							},
						],
						x0a7: [
							{
								child: (
									<CheckboxFilter
										availableItems={
											availableFields.productVersions
										}
										clearCheckboxes={
											filters.productVersions.value
												?.length
										}
										updateFilters={(checkedItems) =>
											setFilters((previousFilters) => ({
												...previousFilters,
												productVersions: {
													...previousFilters.productVersions,
													value: checkedItems,
												},
											}))
										}
									/>
								),
								type: 'component',
							},
						],
						x0a8: [
							{
								child: (
									<CheckboxFilter
										availableItems={
											availableFields.instanceSizes
										}
										clearCheckboxes={
											!filters.instanceSizes.value?.length
										}
										updateFilters={(checkedItems) =>
											setFilters((previousFilters) => ({
												...previousFilters,
												instanceSizes: {
													...previousFilters.instanceSizes,
													value: checkedItems,
												},
											}))
										}
									/>
								),
								type: 'component',
							},
						],
					}}
					trigger={
						<Button
							borderless
							className="btn-secondary p-2"
							prependIcon="filter"
						>
							Filter
						</Button>
					}
				/>
			</div>
		</div>
	);
};

export default Filter;
