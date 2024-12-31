/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {createResourceURL, fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import Step from './components/Step';
import Connect from './steps/Connect';
import Status from './steps/Status';
import {Authorization, MarketplaceSettingsProps} from './types';

const steps = [
	{
		Component: Connect,
		title: Liferay.Language.get('connect'),
	},
	{
		Component: Status,
		title: Liferay.Language.get('status'),
	},
];

export function MarketplaceSettings(props: MarketplaceSettingsProps) {
	const [authorization, setAuthorization] = useState<Authorization>({
		data: null,
		hasAuthorization: false,
		loading: true,
	});

	const [step, setStep] = useState(0);

	const currentStep = steps[step];
	const Component = currentStep.Component;

	useEffect(() => {
		fetch(
			createResourceURL(props.baseResourceURL, {
				p_p_resource_id: '/marketplace_settings/get_authorization',
			}).toString()
		)
			.then((response) => response.json())
			.then((response) =>
				setAuthorization({
					data: response.data,
					hasAuthorization: response.authorized,
					loading: false,
				})
			);
	}, [props.baseResourceURL]);

	const onDisconnect = async () => {
		await fetch(
			createResourceURL(props.baseResourceURL, {
				p_p_resource_id: '/marketplace_settings/disconnect',
			}).toString(),
			{
				method: 'POST',
			}
		);

		Liferay.Util.openToast({
			message: Liferay.Language.get(
				'your-request-completed-successfully'
			),
			title: Liferay.Language.get('success'),
			type: 'success',
		});

		setAuthorization({data: null, hasAuthorization: false, loading: false});
	};

	const onNext = () => setStep(step + 1);

	if (authorization.loading) {
		return (
			<ClayLoadingIndicator
				className="c-mb-5"
				displayType="primary"
				shape="squares"
				size="lg"
			/>
		);
	}

	return (
		<div className="my-2 pb-4 sheet-lg">
			{!authorization.hasAuthorization && (
				<Step step={step} steps={steps} />
			)}

			<Component
				authorization={authorization}
				marketplaceSettingsProps={props}
				onDisconnect={onDisconnect}
				onNext={onNext}
				setAuthorization={setAuthorization}
			/>
		</div>
	);
}
