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

import {STATUS_TAG_TYPE_NAMES} from '../../../../utils/constants';

import {getCurrentActiveRoles} from '../../utils/getCurrentActiveRoles';
import {getDropDownAvailableFields} from '../../utils/getDropDownAvailableFields';
import {getSupportSeat} from '../../utils/getSupportSeat';

const MAX_UPDATE = 3;

const TeamMembersFilter = ({
	userAccounts,
	filtersState: [filters, setFilters],
}) => {
	const countFetchUserAccountsRef = useRef(0);

	const [availableFields, setAvailableFields] = useState({
		roles: [],
		status: [],
		supportSeat: [],
	});

	useEffect(() => {
		if (userAccounts) {
			countFetchUserAccountsRef.current = ++countFetchUserAccountsRef.current;
		}
	}, [userAccounts]);

	useEffect(() => {
		if (userAccounts && countFetchUserAccountsRef?.current < MAX_UPDATE) {
			setAvailableFields(() => ({
				roles: getAvailableFieldsCheckboxs(
					userAccounts,
					(userAccount) => getCurrentActiveRoles(userAccount?.roles)
				),
				status: getAvailableFieldsCheckboxs(
					userAccounts,
					(userAccount) =>
						userAccount?.lastLoginDate
							? STATUS_TAG_TYPE_NAMES.active
							: STATUS_TAG_TYPE_NAMES.invited
				),
				supportSeat: getAvailableFieldsCheckboxs(
					userAccounts,
					(userAccount) => getSupportSeat(userAccount?.roles)
				),
			}));
		}
	}, [userAccounts]);

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

export default TeamMembersFilter;
