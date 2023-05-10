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

import {ClayInput} from '@clayui/form';
import {useEffect, useMemo} from 'react';
import i18n from '../../../../I18n';
import {Input, Select} from '../../../../components';
import useBannedDomains from '../../../../hooks/useBannedDomains';
import {ROLE_TYPES} from '../../../../utils/constants';
import {isValidEmail} from '../../../../utils/validations.form';

const FETCH_DELAY_AFTER_TYPING = 500;

const TeamMemberInputs = ({
	administratorsAssetsAvailable,
	disableError,
	id,
	invite,
	onSelectRole,
	options,
	placeholderEmail,
	selectOnChange,
}) => {
	const bannedDomains = useBannedDomains(
		invite?.email,
		FETCH_DELAY_AFTER_TYPING
	);

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
						validations={[
							(value) => isValidEmail(value, bannedDomains),
						]}
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
