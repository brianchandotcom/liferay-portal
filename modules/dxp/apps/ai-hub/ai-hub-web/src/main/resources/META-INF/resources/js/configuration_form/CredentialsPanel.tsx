/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button, {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import {FieldBase, openToast} from 'frontend-js-components-web';
import React from 'react';

import {useCredential} from './hooks/useCredential';

const SECRET_PLACEHOLDER = '••••••••••••••••••••••••';

async function copyToClipboard(value: string) {
	try {
		await navigator.clipboard.writeText(value);

		openToast({
			message: Liferay.Language.get('copied-to-clipboard'),
			type: 'success',
		});
	}
	catch (error) {
		openToast({
			message: Liferay.Language.get('failed-to-copy-to-clipboard'),
			type: 'danger',
		});
	}
}

export default function CredentialsPanel({clientId}: {clientId: string}) {
	const {clientSecret, hideClientSecret, revealClientSecret, revealing} =
		useCredential();

	return (
		<>
			<h3 className="mb-3">{Liferay.Language.get('api-credentials')}</h3>

			<FieldBase id="clientId" label={Liferay.Language.get('client-id')}>
				<ClayInput.Group>
					<ClayInput.GroupItem>
						<ClayInput
							id="clientId"
							readOnly
							type="text"
							value={clientId}
						/>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem shrink>
						<ClayButtonWithIcon
							aria-label={Liferay.Language.get(
								'copy-to-clipboard'
							)}
							displayType="secondary"
							onClick={() => copyToClipboard(clientId)}
							symbol="paste"
							title={Liferay.Language.get('copy-to-clipboard')}
						/>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</FieldBase>

			<FieldBase
				helpMessage={Liferay.Language.get(
					'shown-only-when-you-choose-to-reveal-it'
				)}
				id="clientSecret"
				label={Liferay.Language.get('client-secret')}
			>
				<ClayInput.Group>
					<ClayInput.GroupItem>
						<ClayInput
							id="clientSecret"
							readOnly
							type="text"
							value={
								clientSecret === null
									? SECRET_PLACEHOLDER
									: clientSecret
							}
						/>
					</ClayInput.GroupItem>

					{clientSecret === null ? (
						<ClayInput.GroupItem shrink>
							<Button
								disabled={revealing}
								displayType="secondary"
								onClick={revealClientSecret}
							>
								{Liferay.Language.get('reveal')}
							</Button>
						</ClayInput.GroupItem>
					) : (
						<>
							<ClayInput.GroupItem shrink>
								<ClayButtonWithIcon
									aria-label={Liferay.Language.get(
										'copy-to-clipboard'
									)}
									displayType="secondary"
									onClick={() =>
										copyToClipboard(clientSecret)
									}
									symbol="paste"
									title={Liferay.Language.get(
										'copy-to-clipboard'
									)}
								/>
							</ClayInput.GroupItem>

							<ClayInput.GroupItem shrink>
								<Button
									displayType="secondary"
									onClick={hideClientSecret}
								>
									{Liferay.Language.get('hide')}
								</Button>
							</ClayInput.GroupItem>
						</>
					)}
				</ClayInput.Group>
			</FieldBase>
		</>
	);
}
