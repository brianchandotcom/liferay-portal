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
import ClayLabel from '@clayui/label';
import React, {useEffect, useState} from 'react';

import {fetchPeopleData, updatePeopleData} from '../../utils/api';
import PeopleContextProvider from './Context';
import SelectPanels from './SelectPanels';

const request = async (
	syncAllAccounts: boolean,
	syncAllContacts: boolean
): Promise<void> => {
	await updatePeopleData({syncAllAccounts, syncAllContacts});
};

const People: React.FC = () => {
	const [syncAll, setSyncAll] = useState(false);
	const [syncAllAccounts, setSyncAllAccounts] = useState(false);
	const [syncAllContacts, setSyncAllContacts] = useState(false);

	useEffect(() => {
		const request = async () => {
			const {syncAllAccounts, syncAllContacts} = await fetchPeopleData();

			setSyncAll(syncAllAccounts && syncAllContacts);
			setSyncAllAccounts(syncAllAccounts);
			setSyncAllContacts(syncAllContacts);
		};

		request();
	}, []);

	return (
		<>
			<div className="my-5">
				<ClayToggle
					label={Liferay.Language.get(
						'sync-all-contacts-and-accounts'
					)}
					onToggle={async () => {
						await request(!syncAll, !syncAll);

						setSyncAll(!syncAll);
						setSyncAllAccounts(!syncAll);
						setSyncAllContacts(!syncAll);
					}}
					toggled={syncAll}
				/>

				<ClayLabel className="ml-4" displayType="info">
					{Liferay.Language.get('recomended')}
				</ClayLabel>
			</div>

			<SelectPanels
				onSyncAllAccountsChange={async () => {
					await request(!syncAllAccounts, syncAllContacts);

					setSyncAll(!syncAllAccounts && syncAllContacts);
					setSyncAllAccounts(!syncAllAccounts);
				}}
				onSyncAllContactsChange={async () => {
					await request(!syncAllAccounts, syncAllContacts);

					setSyncAll(syncAllAccounts && !syncAllContacts);
					setSyncAllContacts(!syncAllContacts);
				}}
				syncAllAccounts={syncAllAccounts}
				syncAllContacts={syncAllContacts}
			/>
		</>
	);
};

const PeopleWrapper = () => (
	<PeopleContextProvider>
		<People />
	</PeopleContextProvider>
);

export default PeopleWrapper;
