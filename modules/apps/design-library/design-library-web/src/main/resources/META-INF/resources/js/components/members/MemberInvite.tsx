/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayInput, ClaySelectWithOption} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClaySticker from '@clayui/sticker';
import {ItemSelector} from '@liferay/frontend-js-item-selector-web';
import {sub} from 'frontend-js-web';
import React, {useId, useMemo, useState} from 'react';

import {AutocompleteItem, Member, MemberType} from '../../types';

const ENDPOINTS: Record<MemberType, string> = {
	user: `${location.origin}/o/headless-admin-user/v1.0/user-accounts`,
	userGroup: `${location.origin}/o/headless-admin-user/v1.0/user-groups`,
};

export default function MemberInvite({
	invite,
	members,
}: {
	invite: (type: MemberType, item: AutocompleteItem) => Promise<void>;
	members: Member[];
}) {
	const [type, setType] = useState<MemberType>('user');
	const [selectedItems, setSelectedItems] = useState<AutocompleteItem[]>([]);
	const selectId = useId();

	const selectedItem = selectedItems[0];

	const apiURL = useMemo(() => {
		const url = new URL(ENDPOINTS[type]);

		const excludedMembers = members.filter(
			(member) => member.type === type
		);

		if (excludedMembers.length) {
			const filterKey = type === 'user' ? 'id' : 'userGroupId';

			url.searchParams.set(
				'filter',
				excludedMembers
					.map((member) => `${filterKey} ne '${member.id}'`)
					.join(' and ')
			);
		}

		return url.toString();
	}, [members, type]);

	const onTypeChange = (nextType: MemberType) => {
		setType(nextType);
		setSelectedItems([]);
	};

	const onInvite = async () => {
		if (!selectedItem) {
			return;
		}

		await invite(type, selectedItem);

		setSelectedItems([]);
	};

	return (
		<div className="p-4">
			<label htmlFor={selectId}>
				{Liferay.Language.get('add-people-to-collaborate')}
			</label>

			<div className="align-items-end autofit-row c-gap-3">
				<div className="autofit-col autofit-col-expand">
					<ClayInput.Group>
						<ClayInput.GroupItem prepend shrink>
							<ClaySelectWithOption
								aria-label={Liferay.Language.get('type')}
								className="font-weight-semi-bold form-control-select-secondary rounded-left"
								id={selectId}
								onChange={(event) =>
									onTypeChange(
										event.target.value as MemberType
									)
								}
								options={[
									{
										label: Liferay.Language.get('users'),
										value: 'user',
									},
									{
										label: Liferay.Language.get(
											'user-groups'
										),
										value: 'userGroup',
									},
								]}
								value={type}
							/>
						</ClayInput.GroupItem>

						<ClayInput.GroupItem append>
							<ItemSelector<AutocompleteItem>
								apiURL={apiURL}
								className="design-library-member-selector"
								id="memberSelector"
								items={selectedItems}
								key={apiURL}
								onItemsChange={(items: AutocompleteItem[]) =>
									setSelectedItems(items.slice(-1))
								}
								placeholder={Liferay.Language.get(
									'enter-name-or-email'
								)}
							>
								{(item: AutocompleteItem) =>
									type === 'user' ? (
										<ItemSelector.Item
											className="align-items-center d-flex"
											key={item.id}
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
													src={
														item.image ||
														'/image/user_portrait'
													}
												/>
											</ClaySticker>

											<span className="ml-2 text-truncate">
												{item.name}{' '}

												{item.emailAddress
													? `(${item.emailAddress})`
													: ''}
											</span>
										</ItemSelector.Item>
									) : (
										<ItemSelector.Item
											className="align-items-center d-flex"
											key={item.id}
											textValue={item.name}
										>
											<ClaySticker
												displayType="primary"
												shape="circle"
												size="sm"
											>
												<ClayIcon symbol="users" />
											</ClaySticker>

											<span className="ml-2 text-truncate">
												{item.name}{' '}

												{sub(
													Liferay.Language.get(
														'x-members'
													),
													String(item.usersCount ?? 0)
												)}
											</span>
										</ItemSelector.Item>
									)
								}
							</ItemSelector>
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</div>

				<div className="autofit-col">
					<ClayButton disabled={!selectedItem} onClick={onInvite}>
						{Liferay.Language.get('invite')}
					</ClayButton>
				</div>
			</div>
		</div>
	);
}
