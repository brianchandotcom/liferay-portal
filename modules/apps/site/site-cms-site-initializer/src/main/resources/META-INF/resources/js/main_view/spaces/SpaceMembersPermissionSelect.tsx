/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import {ClayCheckbox} from '@clayui/form';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React, {useCallback, useMemo, useState} from 'react';

import {Role} from '../../common/types/Role';

export const SPACE_MEMBER_ROLE_ID = 20397;
export const HIDDEN_MEMBER_ROLES = [20398, 20399]; // [Asset Library Owner, CMS Consumer]

interface SpaceMembersPermissionSelectProps {
	onChange: (selectedRoles: number[]) => void;
	roles: Role[];
	selectedRoles: number[];
}

export function SpaceMembersPermissionSelect({
	onChange,
	roles: rawRoles = [],
	selectedRoles,
}: SpaceMembersPermissionSelectProps) {
	const [active, setActive] = useState(false);
	const roles = useMemo(
		() => rawRoles.filter((role) => !HIDDEN_MEMBER_ROLES.includes(role.id)),
		[rawRoles]
	);

	const getRoleName = useCallback(
		(roleId: number) => {
			const currentLang = Liferay.ThemeDisplay.getBCP47LanguageId();
			const roleFound = roles.find((role) => role.id === roleId);

			return roleFound?.name_i18n[currentLang] || roleFound?.name;
		},
		[roles]
	);

	const handleCheckboxChange = useCallback(
		(roleId: number) => {
			const newSelectedRoles = selectedRoles.includes(roleId)
				? selectedRoles.filter((id) => id !== roleId)
				: [...selectedRoles, roleId];

			onChange(newSelectedRoles);
		},
		[onChange, selectedRoles]
	);

	const triggerText = useMemo(
		() =>
			roles
				.filter((role) => selectedRoles.includes(role.id))
				.map((role) => getRoleName(role.id))
				.join(', '),
		[getRoleName, roles, selectedRoles]
	);

	return (
		<ClayDropDown
			active={active}
			alignmentPosition={3}
			onActiveChange={setActive}
			trigger={
				<ClayButton
					borderless
					className="align-items-center d-flex"
					displayType="secondary"
					size="xs"
				>
					<ClayTooltipProvider>
						<span
							className="permission-select-trigger-text text-truncate"
							data-tooltip-align="top"
							title={triggerText}
						>
							{triggerText}
						</span>
					</ClayTooltipProvider>
				</ClayButton>
			}
			triggerIcon="caret-bottom"
		>
			<ClayDropDown.ItemList>
				{roles.map((role) => (
					<ClayDropDown.Item key={role.id}>
						<ClayCheckbox
							checked={selectedRoles.includes(role.id)}
							disabled={role.id === SPACE_MEMBER_ROLE_ID}
							label={getRoleName(role.id)}
							onChange={() => handleCheckboxChange(role.id)}
						/>
					</ClayDropDown.Item>
				))}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
}
