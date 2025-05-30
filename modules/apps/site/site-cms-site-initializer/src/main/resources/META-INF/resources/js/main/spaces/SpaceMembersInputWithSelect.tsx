/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '../../../css/spaces/SpaceMembersInputWithSelect.scss';

import Autocomplete from '@clayui/autocomplete';
import ClayForm, {ClayInput, ClaySelectWithOption} from '@clayui/form';
import classNames from 'classnames';
import React from 'react';

export enum SelectOptions {
	USERS = 'users',
	GROUPS = 'groups',
}

interface SpaceMembersInputWithSelectProps {
	className?: string;
	inputValue?: string;
	onInputChange?: (value: string) => void;
	onSelectChange?: (value: SelectOptions) => void;
	selectValue?: SelectOptions;
}

export function SpaceMembersInputWithSelect({
	className,
	inputValue,
	onInputChange,
	onSelectChange,
	selectValue,
}: SpaceMembersInputWithSelectProps) {
	return (
		<ClayForm.Group
			className={classNames('space-members-input-with-select', className)}
		>
			<label className="d-block" htmlFor="autocomplete">
				{Liferay.Language.get('add-people-to-collaborate')}
			</label>

			<ClayInput.Group>
				<ClayInput.GroupItem prepend shrink>
					<ClaySelectWithOption
						onChange={(event) => {
							onSelectChange?.(
								event.target.value as SelectOptions
							);
						}}
						options={[
							{
								label: Liferay.Language.get('users'),
								value: 'users',
							},
							{
								label: Liferay.Language.get('groups'),
								value: 'groups',
							},
						]}
						value={selectValue}
					/>
				</ClayInput.GroupItem>

				<ClayInput.GroupItem append>
					<Autocomplete
						id="autocomplete"
						onChange={(value: string) => {
							onInputChange?.(value);
						}}
						placeholder={Liferay.Language.get(
							'enter-name-or-email'
						)}
						value={inputValue}
					>
						<Autocomplete.Item key="user1">User1</Autocomplete.Item>
					</Autocomplete>
				</ClayInput.GroupItem>
			</ClayInput.Group>
		</ClayForm.Group>
	);
}
