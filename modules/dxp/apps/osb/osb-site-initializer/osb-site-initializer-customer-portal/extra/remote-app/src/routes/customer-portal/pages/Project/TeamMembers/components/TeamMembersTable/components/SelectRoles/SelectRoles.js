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
import {ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';

const SelectRole = ({
	accountRoles,
	currentRole,
	selectedRole,
	setSelectedRole,
}) => {
	const handleOnChangeRole = (roleName) => {
		const accountRole = accountRoles?.find(
			(accountRole) => accountRole?.name === roleName
		);

		if (accountRole) {
			setSelectedRole(accountRole.name);
		}
	};

	return (
		<div className="position-relative">
			<ClayIcon className="select-icon" symbol="caret-bottom" />

			<ClaySelect
				className="font-weight-bold"
				onChange={({target}) => {
					handleOnChangeRole(target.value);
				}}
				value={selectedRole || currentRole}
			>
				{accountRoles?.map(({disabled, id, name}) => (
					<ClaySelect.Option
						className="options"
						disabled={disabled}
						key={id}
						label={name}
						value={name}
					/>
				))}
			</ClaySelect>
		</div>
	);
};

export default SelectRole;
