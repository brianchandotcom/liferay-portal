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
		authorized: false,
		data: null,
		loading: true,
	});

	const [step, setStep] = useState(0);

	const currentStep = steps[step];
	const Component = currentStep.Component;

	useEffect(() => {
		fetch(
			createResourceURL(props.baseResourceURL, {
				p_p_resource_id: '/marketplace_settings/get_configuration',
			}).toString()
		)
			.then((response) => response.json())
			.then((response) =>
				setAuthorization({
					authorized: response.authorized,
					data: response.data,
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

		setStep(0);

		setAuthorization({authorized: false, data: null, loading: false});
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
		<div className="my-4 pb-4 sheet-lg">
			{!authorization.authorized && <Step step={step} steps={steps} />}

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
