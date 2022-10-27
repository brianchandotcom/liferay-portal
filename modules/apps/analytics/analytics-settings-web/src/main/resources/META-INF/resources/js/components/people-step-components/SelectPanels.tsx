/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {ClayToggle} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import {useModal} from '@clayui/modal';
import ClayPanel from '@clayui/panel';
import React from 'react';

import ModalAccountGroups from './ModalAccountGroups';
import ModalOrganizations from './ModalOrganizations';
import ModalUserGroups from './ModalUserGroups';

interface IPanelProps {
	accountsCount: number;
	onSyncAllAccountsChange: () => void;
	onSyncAllContactsChange: () => void;
	organizationsCount: number;
	syncAllAccounts: boolean;
	syncAllContacts: boolean;
	usersCount: number;
}

const SelectPanels: React.FC<IPanelProps> = ({
	accountsCount,
	onSyncAllAccountsChange,
	onSyncAllContactsChange,
	organizationsCount,
	syncAllAccounts,
	syncAllContacts,
	usersCount,
}) => {
	const {
		observer: observerAccountGroups,
		onOpenChange: onOpenChangeAccount,
		open: openAccount,
	} = useModal();
	const {
		observer: observerUserGroups,
		onOpenChange: onOpenChangeUser,
		open: openUser,
	} = useModal();
	const {
		observer: observerOrganizations,
		onOpenChange: onOpenChangeOrganizations,
		open: openOrganizations,
	} = useModal();

	const userOrganizationList = [
		{
			count: usersCount,
			icon: 'users',
			onOpenChange: () => onOpenChangeUser(true),
			title: Liferay.Language.get('user-groups'),
		},
		{
			count: organizationsCount,
			icon: 'organizations',
			onOpenChange: () => onOpenChangeOrganizations(true),
			title: Liferay.Language.get('organizations'),
		},
	];

	return (
		<>
			<ClayPanel
				className="panel-unstyled"
				collapsable
				displayTitle={Liferay.Language.get('select-contacts')}
				displayType="secondary"
				showCollapseIcon={true}
			>
				<ClayPanel.Body>
					<div className="panel-toggle">
						<ClayToggle
							label={Liferay.Language.get('sync-all-contacts')}
							onToggle={() => {
								onSyncAllContactsChange();
							}}
							toggled={syncAllContacts}
						/>
					</div>

					<p className="panel-description">
						{Liferay.Language.get(
							'sync-contacts-label-description'
						)}
					</p>

					{userOrganizationList.map((item) => (
						<ClayList
							className="mb-0"
							key={item.title}
							showQuickActionsOnHover
						>
							<ClayList.Item
								action
								className="align-items-center"
								disabled={syncAllContacts}
								flex
								onClick={() => {
									!syncAllContacts ? item.onOpenChange() : '';
								}}
							>
								<ClayList.ItemField>
									<ClayIcon symbol={item.icon} />
								</ClayList.ItemField>

								<ClayList.ItemField expand>
									<ClayList.ItemTitle>
										{item.title}
									</ClayList.ItemTitle>

									<ClayList.ItemText>
										{`${item.count} ${Liferay.Language.get(
											'selected'
										)}`}
									</ClayList.ItemText>
								</ClayList.ItemField>
							</ClayList.Item>
						</ClayList>
					))}
				</ClayPanel.Body>
			</ClayPanel>

			<ClayPanel
				className="panel-unstyled"
				collapsable
				displayTitle={Liferay.Language.get('select-accounts')}
				displayType="secondary"
				showCollapseIcon={true}
			>
				<ClayPanel.Body>
					<div className="panel-toggle">
						<ClayToggle
							label={Liferay.Language.get('sync-all-accounts')}
							onToggle={() => {
								onSyncAllAccountsChange();
							}}
							toggled={syncAllAccounts}
						/>
					</div>

					<p className="panel-description">
						{Liferay.Language.get(
							'sync-accounts-label-description'
						)}
					</p>

					<ClayList showQuickActionsOnHover>
						<ClayList.Item
							action
							className="align-items-center"
							disabled={syncAllAccounts}
							flex
							onClick={() =>
								!syncAllAccounts
									? onOpenChangeAccount(true)
									: ''
							}
						>
							<ClayList.ItemField>
								<ClayIcon symbol="users" />
							</ClayList.ItemField>

							<ClayList.ItemField expand>
								<ClayList.ItemTitle>
									{Liferay.Language.get(
										'sync-by-accounts-groups'
									)}
								</ClayList.ItemTitle>

								<ClayList.ItemText>
									{`${accountsCount} ${Liferay.Language.get(
										'selected'
									)}`}
								</ClayList.ItemText>
							</ClayList.ItemField>
						</ClayList.Item>
					</ClayList>
				</ClayPanel.Body>
			</ClayPanel>
			{openUser && (
				<ModalUserGroups
					observer={observerUserGroups}
					onCloseModal={() => onOpenChangeUser(false)}
				/>
			)}
			{openOrganizations && (
				<ModalOrganizations
					observer={observerOrganizations}
					onCloseModal={() => onOpenChangeOrganizations(false)}
				/>
			)}
			{openAccount && (
				<ModalAccountGroups
					observer={observerAccountGroups}
					onCloseModal={() => onOpenChangeAccount(false)}
				/>
			)}
		</>
	);
};

export default SelectPanels;
