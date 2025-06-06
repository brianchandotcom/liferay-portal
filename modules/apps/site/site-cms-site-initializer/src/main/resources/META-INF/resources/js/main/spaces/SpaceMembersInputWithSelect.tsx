/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '../../../css/spaces/SpaceMembersInputWithSelect.scss';

import Autocomplete from '@clayui/autocomplete';
import {FetchPolicy, useResource} from '@clayui/data-provider';
import ClayForm, {ClayInput, ClaySelectWithOption} from '@clayui/form';
import ClaySticker from '@clayui/sticker';
import classNames from 'classnames';
import {fetch} from 'frontend-js-web';
import React, {useId, useState} from 'react';

import {UserAccount, UserGroup} from '../../types/UserAccount';

export enum SelectOptions {
	USERS = 'users',
	GROUPS = 'groups',
}

export interface SpaceMembersInputWithSelectProps {
	className?: string;
	inputValue?: string;
	onAutocompleteItemSelected?: (item: UserAccount | UserGroup) => void;
	onInputChange?: (value: string) => void;
	onSelectChange?: (value: SelectOptions) => void;
	selectValue?: SelectOptions;
}

export function SpaceMembersInputWithSelect({
	className,
	onAutocompleteItemSelected,
	onSelectChange,
	selectValue,
}: SpaceMembersInputWithSelectProps) {
	const selectId = useId();

	const endpoint =
		selectValue === SelectOptions.USERS
			? '/o/headless-admin-user/v1.0/user-accounts'
			: '/o/headless-admin-user/v1.0/user-groups';

	const [value, setValue] = useState('');
	const [networkStatus, setNetworkStatus] = useState(4);

	const {refetch, resource} = useResource({
		fetch: async (link, options) => {
			const result = await fetch(link, {
				...options,
				headers: {
					...(options?.headers ? options.headers : {}),
					'x-csrf-token': Liferay.authToken,
				},
			});

			const json = await result.json();

			return {
				cursor: json.next,
				items: json.items,
			};
		},
		fetchPolicy: 'no-cache' as FetchPolicy.NoCache,
		link: `${window.location.origin}${endpoint}`,
		onNetworkStatusChange: setNetworkStatus,
		variables: {search: value},
	});

	const renderAutocompleteItem = () => {
		if (selectValue === SelectOptions.USERS) {
			return (item: UserAccount) => {
				return (
					<Autocomplete.Item
						className="align-items-center d-flex"
						key={item.id}
						onClick={() => {
							onAutocompleteItemSelected?.(item);
							setValue('');
						}}
						textValue={item.name}
					>
						<ClaySticker
							displayType="primary"
							shape="circle"
							size="sm"
						>
							<img
								alt={item.name}
								className="sticker-img"
								src={item.image || '/image/user_portrait'}
							/>
						</ClaySticker>

						<span className="ml-2">
							{item.name} ({item.emailAddress?.split('@')[0]})
						</span>
					</Autocomplete.Item>
				);
			};
		}

		return (item: UserGroup) => {
			return (
				<Autocomplete.Item
					className="align-items-center d-flex"
					key={item.id}
					onClick={() => {
						onAutocompleteItemSelected?.(item);
						setValue('');
					}}
					textValue={item.name}
				>
					<ClaySticker displayType="primary" shape="circle" size="sm">
						<img
							alt={item.name}
							className="sticker-img"
							src="/image/user_portrait"
						/>
					</ClaySticker>

					<span className="ml-2">{item.name}</span>
				</Autocomplete.Item>
			);
		};
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
					<Autocomplete
						id="autocomplete"
						items={(resource?.items ?? []) as any}
						loadingState={networkStatus}
						menuTrigger="focus"
						onChange={(value: string) => {
							setValue(value);
						}}
						onFocus={refetch}
						onItemsChange={(params: any) => {
							console.log('ITEMS CHANGED!!!!', params);
						}}
						placeholder={Liferay.Language.get(
							'enter-name-or-email'
						)}
						value={value}
					>
						{renderAutocompleteItem()}
					</Autocomplete>
				</ClayInput.GroupItem>
			</ClayInput.Group>
		</ClayForm.Group>
	);
}
