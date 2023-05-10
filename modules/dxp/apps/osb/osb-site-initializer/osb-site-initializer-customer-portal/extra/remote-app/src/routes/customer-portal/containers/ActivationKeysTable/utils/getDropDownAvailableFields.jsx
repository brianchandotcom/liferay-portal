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
import i18n from '../../../../../common/I18n';
import CheckboxFilter from '../../../components/CheckboxFilter';
import DateFilter from '../../../components/DateFilter';
import ExpirationDateFilter from '../../../components/ExpirationDateFilter';
import KeyTypeFilter from '../../../components/KeyTypeFilter';

export function getDropDownAvailableFields(
	availableFields,
	filters,
	setFilters
) {
	return {
		x0a0: [
			{child: 'x0a1', title: i18n.translate('key-type')},
			{
				child: 'x0a2',
				disabled: !availableFields.environmentTypes.length,
				title: i18n.translate('environment-type'),
			},
			{child: 'x0a4', title: i18n.translate('start-date')},
			{
				child: 'x0a5',
				title: i18n.translate('expiration-date'),
			},
			{
				child: 'x0a6',
				disabled: !availableFields.status.length,
				title: i18n.translate('status'),
			},
			{
				child: 'x0a7',
				disabled: !availableFields.productVersions.length,
				title: i18n.translate('product-version'),
			},
			{
				child: 'x0a8',
				disabled: !availableFields.instanceSizes.length,
				title: i18n.translate('instance-size'),
			},
		],
		x0a1: [
			{
				child: (
					<KeyTypeFilter
						clearInputs={Object.values(filters.keyType.value).every(
							(value) => !value
						)}
						hasCluster={availableFields.hasCluster}
						hasVirtualCluster={availableFields.hasVirtualCluster}
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
						availableItems={availableFields.environmentTypes}
						clearCheckboxes={
							!filters.environmentTypes.value?.length
						}
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
							!filters.startDate.value?.onOrAfter &&
							!filters.startDate.value?.onOrBefore
						}
						updateFilters={(onOrAfter, onOrBefore) =>
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
					<ExpirationDateFilter
						clearInputs={
							!filters.expirationDate.value?.onOrAfter &&
							!filters.expirationDate.value?.onOrBefore
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
						clearCheckboxes={!filters.status.value?.length}
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
						availableItems={availableFields.productVersions}
						clearCheckboxes={!filters.productVersions.value?.length}
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
						availableItems={availableFields.instanceSizes}
						clearCheckboxes={!filters.instanceSizes.value?.length}
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
	};
}
