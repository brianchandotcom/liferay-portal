/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import {useEffect, useMemo} from 'react';
import {useAppPropertiesContext} from '~/common/contexts/AppPropertiesContext';
import useCurrentKoroneikiAccount from '~/common/hooks/useCurrentKoroneikiAccount';
import useProvisioningLicenseKeys from '~/common/hooks/useProvisioningLicenseKeys';
import useUserAccountsByAccountExternalReferenceCode from '~/routes/customer-portal/pages/Project/TeamMembers/components/TeamMembersTable/hooks/useUserAccountsByAccountExternalReferenceCode';
import i18n from '../../../../I18n';
import {Input, Select} from '../../../../components';
import useBannedDomains from '../../../../hooks/useBannedDomains';
import {ROLE_TYPES} from '../../../../utils/constants/';
import {liferayDomains} from '../../../../utils/constants/liferayDomains';
import {
	isLiferayDomain,
	isValidEmail,
} from '../../../../utils/validations.form';

const FETCH_DELAY_AFTER_TYPING = 500;

const TeamMemberInputs = ({
	administratorsAssetsAvailable,
	disableError,
	errors,
	id,
	invite,
	onSelectRole,
	options,
	placeholderEmail,
	selectOnChange,
}) => {
	const {accountSettingsURL, featureFlags} = useAppPropertiesContext();
	const provisioningService = useProvisioningLicenseKeys();

	const bannedDomains = useBannedDomains(
		invite?.email,
		FETCH_DELAY_AFTER_TYPING
	);

	const {data} = useCurrentKoroneikiAccount();
	const koroneikiAccount = data?.koroneikiAccountByExternalReferenceCode;

	const [
		,
		{data: userAccountsData},
	] = useUserAccountsByAccountExternalReferenceCode(
		koroneikiAccount?.accountKey
	);

	const currentDomain = userAccountsData?.accountUserAccountsByExternalReferenceCode.items
		.map(({emailAddress}) => emailAddress.split('@')[1])
		.flat();

	const [, domain] = invite?.email.split('@');

	const mathEmail = currentDomain?.includes(domain) || false;

	const isEmailValid = !!errors.invites?.[id]?.email;

	const warningMessage =
		invite?.email.length > 1 && !mathEmail && !isEmailValid;

	const validateEmail = useMemo(async () => {
		if (isValidEmail(invite?.email, bannedDomains)) {
			return isValidEmail(invite?.email, bannedDomains);
		}

		const hasLiferayDomain = liferayDomains.includes(domain);

		if (hasLiferayDomain) {
			const emailExistsInOkta = await provisioningService.getUserInOkta(
				invite?.email
			);
			if (!emailExistsInOkta) {
				return isLiferayDomain(invite?.email);
			}

			return false;
		}
	}, [bannedDomains, invite?.email, provisioningService]);

	const isAdministratorOrRequestorRoleSelected =
		invite?.role?.name === ROLE_TYPES.requester.name ||
		invite?.role?.name === ROLE_TYPES.admin.name;

	useEffect(() => {
		onSelectRole(isAdministratorOrRequestorRoleSelected);
	}, [onSelectRole, isAdministratorOrRequestorRoleSelected]);

	const optionsFormated = useMemo(
		() =>
			options.map((option) => {
				const isAdministratorOrRequestorRole =
					option.label === ROLE_TYPES.requester.name ||
					option.label === ROLE_TYPES.admin.name;

				return {
					...option,
					disabled:
						administratorsAssetsAvailable !== -1 &&
						administratorsAssetsAvailable === 0 &&
						isAdministratorOrRequestorRole &&
						!isAdministratorOrRequestorRoleSelected,
				};
			}),
		[
			administratorsAssetsAvailable,
			isAdministratorOrRequestorRoleSelected,
			options,
		]
	);

	return (
		<>
			<ClayInput.Group className="m-0">
				<ClayInput.GroupItem className="m-0">
					<Input
						disableError={id === 0 && disableError}
						groupStyle="m-0"
						label={i18n.translate('first-name')}
						name={`invites[${id}].givenName`}
						placeholder={i18n.translate('first-name')}
						required
						type="text"
					/>
				</ClayInput.GroupItem>

				<ClayInput.GroupItem className="m-0">
					<Input
						disableError={id === 0 && disableError}
						groupStyle="m-0"
						label={i18n.translate('last-name')}
						name={`invites[${id}].familyName`}
						placeholder={i18n.translate('last-name')}
						required
						type="text"
					/>
				</ClayInput.GroupItem>
			</ClayInput.Group>

			<ClayInput.Group className="m-0">
				<ClayInput.GroupItem className="m-0">
					<Input
						disableError={id === 0 && disableError}
						groupStyle="m-0"
						label={i18n.translate('email')}
						name={`invites[${id}].email`}
						placeholder={placeholderEmail}
						required
						type="email"
						validations={[() => validateEmail]}
					/>
				</ClayInput.GroupItem>

				<ClayInput.GroupItem className="m-0">
					<Select
						groupStyle="m-0"
						label={i18n.translate('role')}
						name={`invites[${id}].role.id`}
						onChange={(event) => selectOnChange(event.target.value)}
						options={optionsFormated}
						required
					/>
				</ClayInput.GroupItem>
			</ClayInput.Group>
			<hr className="mb-3 mt-2" />
		</>
	);
};

export default TeamMemberInputs;
