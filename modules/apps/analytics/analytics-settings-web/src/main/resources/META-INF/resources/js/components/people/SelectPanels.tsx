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

import {Text} from '@clayui/core';
import {ClayToggle} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import {useModal} from '@clayui/modal';
import ClayPanel from '@clayui/panel';
import {sub} from 'frontend-js-web';
import React from 'react';

import {useData} from './Context';
import ModalAccountGroups from './ModalAccountGroups';
import ModalOrganizations from './ModalOrganizations';
import ModalUserGroups from './ModalUserGroups';

interface IPanelProps {
	onSyncAllAccountsChange: () => void;
	onSyncAllContactsChange: () => void;
	syncAllAccounts: boolean;
	syncAllContacts: boolean;
}

const SelectPanels: React.FC<IPanelProps> = ({
	onSyncAllAccountsChange,
	onSyncAllContactsChange,
	syncAllAccounts,
	syncAllContacts,
}) => {
	const {accountsCount, organizationsCount, usersCount} = useData();

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
				showCollapseIcon
			>
				<ClayPanel.Body>
					<div className="mb-4 mt-3">
						<ClayToggle
							label={Liferay.Language.get('sync-all-contacts')}
							onToggle={onSyncAllContactsChange}
							toggled={syncAllContacts}
						/>
					</div>

					<Text size={3}>
						{Liferay.Language.get(
							'sync-contacts-label-description'
						)}
					</Text>

					<ClayList className="mt-3" showQuickActionsOnHover>
						{userOrganizationList.map(
							({count, icon, onOpenChange, title}) => (
								<ClayList.Item
									action
									className="align-items-center"
									disabled={syncAllContacts}
									flex
									key={title}
									onClick={() =>
										!syncAllContacts && onOpenChange()
									}
								>
									<ClayList.ItemField>
										<ClayIcon symbol={icon} />
									</ClayList.ItemField>

									<ClayList.ItemField expand>
										<ClayList.ItemTitle className="hover-title">
											{title}
										</ClayList.ItemTitle>

										<ClayList.ItemTitle className="font-weight-lighter mt-1 text-secondary">
											{sub(
												Liferay.Language.get(
													'x-selected'
												),
												count
											)}
										</ClayList.ItemTitle>
									</ClayList.ItemField>
								</ClayList.Item>
							)
						)}
					</ClayList>
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
					<div className="mb-4 mt-3">
						<ClayToggle
							label={Liferay.Language.get('sync-all-accounts')}
							onToggle={onSyncAllAccountsChange}
							toggled={syncAllAccounts}
						/>
					</div>

					<Text size={3}>
						{Liferay.Language.get(
							'sync-accounts-label-description'
						)}
					</Text>

					<ClayList className="mt-3" showQuickActionsOnHover>
						<ClayList.Item
							action
							className="align-items-center"
							disabled={syncAllAccounts}
							flex
							onClick={() =>
								!syncAllAccounts && onOpenChangeAccount(true)
							}
						>
							<ClayList.ItemField>
								<ClayIcon symbol="users" />
							</ClayList.ItemField>

							<ClayList.ItemField expand>
								<ClayList.ItemTitle className="hover-title">
									{Liferay.Language.get(
										'sync-by-accounts-groups'
									)}
								</ClayList.ItemTitle>

								<ClayList.ItemTitle className="font-weight-lighter mt-1 text-secondary">
									{sub(
										Liferay.Language.get('x-selected'),
										accountsCount
									)}
								</ClayList.ItemTitle>
							</ClayList.ItemField>
						</ClayList.Item>
					</ClayList>
				</ClayPanel.Body>
			</ClayPanel>

			{openAccount && (
				<ModalAccountGroups
					observer={observerAccountGroups}
					onCloseModal={() => onOpenChangeAccount(false)}
					syncAllAccounts={syncAllAccounts}
					syncAllContacts={syncAllContacts}
				/>
			)}

			{openOrganizations && (
				<ModalOrganizations
					observer={observerOrganizations}
					onCloseModal={() => onOpenChangeOrganizations(false)}
					syncAllAccounts={syncAllAccounts}
					syncAllContacts={syncAllContacts}
				/>
			)}

			{openUser && (
				<ModalUserGroups
					observer={observerUserGroups}
					onCloseModal={() => onOpenChangeUser(false)}
					syncAllAccounts={syncAllAccounts}
					syncAllContacts={syncAllContacts}
				/>
			)}
		</>
	);
};

export default SelectPanels;
