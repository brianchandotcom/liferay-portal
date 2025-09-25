/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '../../../css/spaces/SpaceMembersInputWithSelect.scss';

import ClayForm, {ClayInput, ClaySelectWithOption} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClaySticker from '@clayui/sticker';
import classNames from 'classnames';
import {ItemSelector} from 'frontend-js-item-selector-web';
import React, {useId, useState} from 'react';

import {UserAccount, UserGroup} from '../../common/types/UserAccount';

export enum SelectOptions {
	USERS = 'users',
	GROUPS = 'groups',
}

type HeadlessAdminUserGroup = Pick<
	UserGroup,
	'id' | 'externalReferenceCode' | 'name'
> & {
	usersCount: number;
};

export interface SpaceMembersInputWithSelectProps {
	className?: string;
	disabled: boolean;
	onAutocompleteItemSelected?: (item: UserAccount | UserGroup) => void;
	onSelectChange?: (value: SelectOptions) => void;
	selectValue?: SelectOptions;
}

const endpoints = {
	[SelectOptions.USERS]: `${location.origin}/o/headless-admin-user/v1.0/user-accounts`,
	[SelectOptions.GROUPS]: `${location.origin}/o/headless-admin-user/v1.0/user-groups`,
} as const;

export function SpaceMembersInputWithSelect({
	className,
	disabled,
	onAutocompleteItemSelected,
	onSelectChange,
	selectValue,
}: SpaceMembersInputWithSelectProps) {
	const selectId = useId();
	const [value, setValue] = useState('');

	const renderUserAccountItem = (item: UserAccount) => {
		return (
			<ItemSelector.Item
				className="align-items-center d-flex text-truncate"
				key={item.id}
				onClick={() => {
					onAutocompleteItemSelected?.(item);
					setTimeout(() => setValue(''), 0);
				}}
				textValue={item.name}
			>
				<ClaySticker displayType="primary" shape="circle" size="sm">
					<img
						alt={item.name}
						className="sticker-img"
						src={item.image || '/image/user_portrait'}
					/>
				</ClaySticker>

				<span className="ml-2 text-truncate">
					{item.name} ({item.emailAddress?.split('@')[0]})
				</span>
			</ItemSelector.Item>
		);
	};

	const renderUserGroupItem = (item: HeadlessAdminUserGroup) => {
		const groupCount = item.usersCount || 0;

		return (
			<ItemSelector.Item
				className="align-items-center d-flex text-truncate"
				key={item.id}
				onClick={() => {
					onAutocompleteItemSelected?.({
						...item,
						numberOfUserAccounts: String(groupCount),
						roles: [],
					});
					setTimeout(() => setValue(''), 0);
				}}
				textValue={item.name}
			>
				<ClaySticker displayType="primary" shape="circle" size="sm">
					<ClayIcon
						className="text-secondary"
						fontSize="24px"
						symbol="users"
					/>
				</ClaySticker>

				<span className="ml-2 text-truncate">{item.name}</span>

				<span className="ml-1">
					(
					{Liferay.Util.sub(
						Liferay.Language.get('x-members'),
						groupCount
					)}
					)
				</span>
			</ItemSelector.Item>
		);
	};

	return (
		<ClayForm.Group
			className={classNames('space-members-input-with-select', className)}
		>
			<label className="d-block" htmlFor={selectId}>
				{Liferay.Language.get('add-people-to-collaborate')}
			</label>

			<ClayInput.Group>
				<ClayInput.GroupItem prepend shrink>
					<ClaySelectWithOption
						className="font-weight-semi-bold form-control form-control-select-secondary rounded-left"
						id={selectId}
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
					{disabled ? (
						<ClayInput
							disabled
							placeholder={Liferay.Language.get(
								'enter-name-or-email'
							)}
						/>
					) : selectValue === SelectOptions.USERS ? (
						<ItemSelector<UserAccount>
							apiURL={endpoints[SelectOptions.USERS]}
							id="autocomplete"
							key="select-user"
							locator={{
								id: 'id',
								label: 'name',
								value: 'id',
							}}
							onChange={setValue}
							placeholder={Liferay.Language.get(
								'enter-name-or-email'
							)}
							value={value}
						>
							{renderUserAccountItem}
						</ItemSelector>
					) : (
						<ItemSelector<HeadlessAdminUserGroup>
							apiURL={endpoints[SelectOptions.GROUPS]}
							id="autocomplete"
							key="select-group"
							locator={{
								id: 'id',
								label: 'name',
								value: 'id',
							}}
							onChange={setValue}
							placeholder={Liferay.Language.get(
								'enter-name-or-email'
							)}
							value={value}
						>
							{renderUserGroupItem}
						</ItemSelector>
					)}
				</ClayInput.GroupItem>
			</ClayInput.Group>
		</ClayForm.Group>
	);
}
