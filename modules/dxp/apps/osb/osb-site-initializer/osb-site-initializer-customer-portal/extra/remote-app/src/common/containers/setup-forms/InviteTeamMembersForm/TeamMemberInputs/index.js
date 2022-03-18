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
import {useMemo} from 'react';
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

	const optionsFormated = useMemo(
		() =>
			options.map((option) => {
				const isAdministratorOrRequestorRole =
					option.label === ROLE_TYPES.requester.name ||
					option.label === ROLE_TYPES.admin.name;

				return {
					...option,
					disabled:
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
		<ClayInput.Group className="m-0">
			<ClayInput.GroupItem className="m-0">
				<Input
					disableError={id === 0 && disableError}
					groupStyle="m-0"
					label="Email"
					name={`invites[${id}].email`}
					placeholder={placeholderEmail}
					type="email"
					validations={[
						(value) => isValidEmail(value, bannedDomains),
					]}
				/>
			</ClayInput.GroupItem>

			<ClayInput.GroupItem className="m-0">
				<Select
					groupStyle="m-0"
					label="Role"
					name={`invites[${id}].role.id`}
					onChange={(event) => selectOnChange(event.target.value)}
					options={optionsFormated}
				/>
			</ClayInput.GroupItem>
		</ClayInput.Group>
	);
};

export default TeamMemberInputs;
