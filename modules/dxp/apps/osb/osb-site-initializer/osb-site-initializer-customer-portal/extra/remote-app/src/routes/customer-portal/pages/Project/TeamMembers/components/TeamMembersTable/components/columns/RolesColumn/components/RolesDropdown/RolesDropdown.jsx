/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
import {useEffect, useState} from 'react';
import {ROLE_TYPES} from '~/common/utils/constants';
import RoleSelectorDropdown from '~/routes/customer-portal/components/RoleSelectorDropdown';
import isSupportSeatRole from '../../../../../../../../../../../../common/utils/isSupportSeatRole';

const partnerMemberRoles = [
	ROLE_TYPES.partnerMarketingUser.key,
	ROLE_TYPES.partnerSalesUser.key,
	ROLE_TYPES.partnerTechnicalUser.key,
];

const RolesDropdown = ({
	accountRoles,
	availableSupportSeatsCount,
	currentRoleBriefName,
	hasAccountSupportSeatRole,
	onClick,
	supportSeatsCount,
}) => {
	const [radioOptions, setRadioOptions] = useState({});
	const [selectedAccountRoleName, setSelectedAccountRoleName] = useState(
		currentRoleBriefName
	);

	useEffect(() => {
		const baseFormatAccount = accountRoles.map((accountRole) => ({
			active: selectedAccountRoleName.includes(accountRole.name),
			disabled: hasAccountSupportSeatRole
				? supportSeatsCount === 1
				: isSupportSeatRole(accountRole.name) &&
				  availableSupportSeatsCount === 0,
			label: accountRole.name,
			raysourceName: accountRole.raysourceName,
			value: accountRole.id,
		}));

		setRadioOptions(
			baseFormatAccount.reduce(
				(previousItem, item) => {
					if (!partnerMemberRoles.includes(item.label)) {
						previousItem[item.label] = item;

						return previousItem;
					}

					previousItem.partnerMemberRoles.roles.push(item);
					previousItem.partnerMemberRoles.active = previousItem
						.partnerMemberRoles.active
						? true
						: item.active;

					return previousItem;
				},
				{
					partnerMemberRoles: {
						active: undefined,
						roles: []
					}
				}
			)
		);
	}, [
		accountRoles,
		availableSupportSeatsCount,
		hasAccountSupportSeatRole,
		selectedAccountRoleName,
		setRadioOptions,
		supportSeatsCount,
	]);

	return (
		<RoleSelectorDropdown
			onClick={onClick}
			radioOptions={radioOptions}
			selectedAccountRoleName={selectedAccountRoleName}
			setRadioOptions={setRadioOptions}
			setSelectedAccountRoleName={setSelectedAccountRoleName}
		/>
	);
};

export default RolesDropdown;
