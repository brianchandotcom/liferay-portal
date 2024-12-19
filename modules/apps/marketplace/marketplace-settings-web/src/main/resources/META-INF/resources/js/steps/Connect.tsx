/* eslint-disable @liferay/empty-line-between-elements */

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import {createResourceURL, fetch, objectToFormData, sub} from 'frontend-js-web';
import pkceChallenge from 'pkce-challenge';
import React from 'react';

import Container from '../components/Container';
import {Authorization, MarketplaceSettingsProps} from '../types';

type ConnectProps = {
	authorization: Authorization;
	marketplaceSettingsProps: MarketplaceSettingsProps;
	onDisconnect: () => void;
	onNext: () => void;
	setAuthorization: React.Dispatch<React.SetStateAction<Authorization>>;
};

export default function Connect({
	authorization,
	marketplaceSettingsProps: {baseResourceURL, portletNamespace, ...oAuth2},
	onDisconnect,
	onNext,
	setAuthorization,
}: ConnectProps) {
	const onConnect = async () => {
		const {code_challenge, code_verifier} = pkceChallenge();

		const urlSearchParams = new URLSearchParams({
			client_id: oAuth2.clientId,
			code_challenge,
			code_challenge_method: 'S256',
			redirect_uri: oAuth2.url + oAuth2.redirect,
			response_type: 'code',
			state: JSON.stringify({origin: window.location.origin}),
		});

		const authorizeUrl = `${oAuth2.url}/o/oauth2/authorize?${urlSearchParams.toString()}`;

		const popup = window.open(
			authorizeUrl,
			'OAuth2 Popup',
			'addressbar=no,height=800,location=no,menubar=no,toolbar=no,width=500'
		) as Window;

		const handleMessage = async (event: MessageEvent) => {
			const {code, settings} = event.data;

			const body = {
				[`${portletNamespace}clientId`]: oAuth2.clientId,
				[`${portletNamespace}code`]: code,
				[`${portletNamespace}codeVerifier`]: code_verifier,
				[`${portletNamespace}redirect`]: oAuth2.redirect,
				[`${portletNamespace}settings`]: settings || {},
				[`${portletNamespace}url`]: oAuth2.url,
			};

			await fetch(
				createResourceURL(baseResourceURL, {
					p_p_resource_id: '/marketplace_settings/connect',
				}),
				{
					body: objectToFormData(body),
					method: 'POST',
				}
			);

			setAuthorization((prevAuthorization) => ({
				...prevAuthorization,
				data: event.data,
			}));

			popup.close();

			window.removeEventListener('message', handleMessage);

			onNext();
		};

		window.addEventListener('message', handleMessage);
	};

	if (authorization.hasAuthorization) {
		return (
			<Container
				description={
					<>
						<ClayAlert displayType="success">
							{Liferay.Language.get('connected')}
						</ClayAlert>

						<p>
							{Liferay.Language.get(
								'your-liferay-dxp-platform-is-connected-to-the-marketplace'
							)}
						</p>
					</>
				}
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
				title={Liferay.Language.get('marketplace-connection')}
			/>
		);
	}

	return (
		<Container
			description={Liferay.Language.get(
				'clicking-connect-will-open-a-window-where-you-will-need-to-authenticate-yourself-in-the-marketplace-to-continue'
			)}
			footer={
				<ClayButton displayType="primary" onClick={onConnect}>
					{Liferay.Language.get('connect')}
				</ClayButton>
			}
			title={Liferay.Language.get('connect-marketplace')}
		>
			<h5>{Liferay.Language.get('do-you-need-help')}</h5>

			<div className="mb-4">
				{sub(
					Liferay.Language.get(
						'click-x-to-learn-how-to-connect-liferay-dxp-to-marketplace'
					),
					(
						<a
							href="https://learn.liferay.com/w/dxp/liferay-development/marketplace"
							rel="noopener noreferrer"
							target="_blank"
						>
							{Liferay.Language.get('here')}
						</a>
					) as any
				)}
			</div>
		</Container>
	);
}
