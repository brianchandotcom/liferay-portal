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
import getAvailableFieldsCheckboxs from '../../../../components/CheckboxFilter/utils/getAvailableFieldsCheckboxs';
import DropDownWithDrillDown from '../../../../components/DropDownWithDrillDown';
import SearchBar from '../../../../components/SearchBar';
import {
	getDoesNotExpire,
	getDropDownAvailableFields,
	getEnvironmentType,
	getInstanceSize,
	getProductDescription,
	getStatusActivationTag,
	hasVirtualCluster,
} from '../../utils';

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
				<SearchBar setFilters={setFilters} />

				<DropDownWithDrillDown
					className="align-items-center d-flex"
					initialActiveMenu="x0a0"
					menus={getDropDownAvailableFields(
						availableFields,
						filters,
						setFilters
					)}
					trigger={
						<Button
							borderless
							className="btn-secondary px-3 py-2"
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
