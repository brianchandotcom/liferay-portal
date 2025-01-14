/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {sub} from 'frontend-js-web';
import React from 'react';

import Container from '../components/Container';
import {AccountBrief, Authorization, MyUserAccount} from '../types';

type StatusProps = {
	authorization: Authorization & {
		data: Authorization['data'] & {
			myUserAccount: MyUserAccount;
			selectedAccount: AccountBrief;
		};
	};
	onDisconnect: () => void;
};

export default function Status({
	authorization: {
		data: {settings},
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
							'congratulations-x.-you-have-successfully-connected-x-to-the-marketplace'
						),
						[
							`<strong>${settings?.userAccount?.name}</strong>`,
							`<strong>${settings?.account?.name}</strong>`,
						]
					),
				}}
			/>
		</Container>
	);
}
