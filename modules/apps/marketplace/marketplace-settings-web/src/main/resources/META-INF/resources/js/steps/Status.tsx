/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {sub} from 'frontend-js-web';
import React from 'react';

import Container from '../components/Container';
import {AccountBrief, MyUserAccount} from '../types';

type StatusProps = {
	authorization: {
		data: {
			myUserAccount: MyUserAccount;
			selectedAccount: AccountBrief;
		};
		hasAuthorization: boolean;
		loading: boolean;
	};
	onDisconnect: () => void;
};

export default function Status({
	authorization: {
		data: {myUserAccount, selectedAccount},
	},
	onDisconnect,
}: StatusProps) {
	return (
		<Container
			footer={
				<ClayButton
					borderless
					displayType="secondary"
					onClick={onDisconnect}
					outline
				>
					{Liferay.Language.get('disconnect')}
				</ClayButton>
			}
			title="Status"
		>
			<p
				dangerouslySetInnerHTML={{
					__html: sub(
						Liferay.Language.get(
							'congratulations-x-you-have-successfully-connected-x-to-marketplace-instance'
						),
						[
							`<strong>${myUserAccount.name}</strong>`,
							`<strong>${selectedAccount.name}</strong>`,
						]
					),
				}}
			/>
		</Container>
	);
}
