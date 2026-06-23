/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayToolbar from '@clayui/toolbar';
import {openToast} from 'frontend-js-components-web';
import React, {useEffect, useState} from 'react';

import RequiredMark from '../health_scan/components/RequiredMark';

import './GooglePageSpeedConfiguration.scss';

const PAGESPEED_VALIDATION_URL =
	'https://www.googleapis.com/pagespeedonline/v5/runPagespeed?url=invalid_url&key=';

interface Props {
	backURL: string;
	description: string;
	domainsURL: string;
	instancesURL: string;
	title: string;
}

export default function GooglePageSpeedConfiguration({
	backURL,
	description,
	domainsURL,
	instancesURL,
	title,
}: Props) {
	const [apiKey, setApiKey] = useState('');
	const [hasDomain, setHasDomain] = useState(false);
	const [instanceIds, setInstanceIds] = useState<number[]>([]);
	const [loading, setLoading] = useState(true);
	const [saving, setSaving] = useState(false);
	const [validationError, setValidationError] = useState('');
	const [visible, setVisible] = useState(false);

	useEffect(() => {
		Promise.all([
			Liferay.Util.fetch(`${domainsURL}?pageSize=1`, {
				headers: {Accept: 'application/json'},
			}).then((response) => {
				if (!response.ok) {
					throw new Error(`HTTP ${response.status}`);
				}

				return response.json();
			}),
			Liferay.Util.fetch(`${instancesURL}?pageSize=100`, {
				headers: {Accept: 'application/json'},
			}).then((response) => {
				if (!response.ok) {
					throw new Error(`HTTP ${response.status}`);
				}

				return response.json();
			}),
		])
			.then(([domainsData, instancesData]) => {
				setHasDomain(!!(domainsData.items || []).length);

				const instances = instancesData.items || [];

				setInstanceIds(
					instances.map((instance: {id: number}) => instance.id)
				);

				const firstInstance = instances.find(
					(instance: {googlePageSpeedAPIKey?: string}) =>
						instance.googlePageSpeedAPIKey
				);

				if (firstInstance) {
					setApiKey(firstInstance.googlePageSpeedAPIKey);
				}
			})
			.catch(() => {
				openToast({
					message: Liferay.Language.get(
						'failed-to-load-configuration'
					),
					type: 'danger',
				});
			})
			.finally(() => setLoading(false));
	}, [domainsURL, instancesURL]);

	const validateApiKey = (key: string): Promise<boolean> =>
		Liferay.Util.fetch(
			`${PAGESPEED_VALIDATION_URL}${encodeURIComponent(key)}`
		)
			.then((response) => response.json())
			.then((data) => {
				const errorDetails = data.error?.details || [];

				const errorInfo = errorDetails.find(
					(detail: {reason?: string}) => detail.reason
				);

				const reason = errorInfo?.reason || '';
				const status = data.error?.status || '';

				return !(reason || status === 'PERMISSION_DENIED');
			})
			.catch(() => false);

	const saveApiKey = (): Promise<boolean> => {
		const requests = instanceIds.map((instanceId) =>
			Liferay.Util.fetch(`${instancesURL}/${instanceId}`, {
				body: JSON.stringify({googlePageSpeedAPIKey: apiKey}),
				headers: {
					'Accept': 'application/json',
					'Content-Type': 'application/json',
				},
				method: 'PATCH',
			}).then((response) => response.ok)
		);

		return Promise.all(requests)
			.then((results) => results.every(Boolean))
			.catch(() => false);
	};

	const handleSave = () => {
		if (!hasDomain) {
			openToast({
				message: Liferay.Language.get(
					'no-domains-found-add-a-domain-first'
				),
				type: 'danger',
			});

			return;
		}

		setSaving(true);
		setValidationError('');

		validateApiKey(apiKey).then((valid) => {
			if (!valid) {
				setSaving(false);

				setValidationError(
					Liferay.Language.get(
						'google-pagespeed-connection-failed-please-verify-your-configuration-and-try-again'
					)
				);

				return;
			}

			saveApiKey().then((saved) => {
				if (!saved) {
					setSaving(false);

					setValidationError(
						Liferay.Language.get('failed-to-save-api-key')
					);

					return;
				}

				sessionStorage.setItem(
					'seoStudioToast',
					Liferay.Language.get('google-pagespeed-api-key-added')
				);

				window.location.assign(backURL);
			});
		});
	};

	return (
		<>
			<ClayToolbar className="bg-white border-bottom px-3">
				<ClayToolbar.Nav>
					<ClayToolbar.Item>
						<a className="component-action" href={backURL}>
							<ClayIcon
								spritemap={Liferay.Icons.spritemap}
								symbol="angle-left"
							/>
						</a>
					</ClayToolbar.Item>

					<ClayToolbar.Item className="text-left" expand>
						<ClayToolbar.Section>
							<span className="font-weight-semi-bold text-dark">
								{Liferay.Language.get('configurations')}
							</span>
						</ClayToolbar.Section>
					</ClayToolbar.Item>

					<ClayToolbar.Item>
						<a
							className="border-0 btn btn-secondary"
							href={backURL}
						>
							{Liferay.Language.get('cancel')}
						</a>
					</ClayToolbar.Item>

					<ClayToolbar.Item>
						<ClayButton
							disabled={!apiKey.trim() || saving || loading}
							onClick={handleSave}
						>
							{saving
								? Liferay.Language.get('validating')
								: Liferay.Language.get('save')}
						</ClayButton>
					</ClayToolbar.Item>
				</ClayToolbar.Nav>
			</ClayToolbar>

			<div className="mt-4 mx-auto px-4 seo-studio-google-pagespeed-configuration-body">
				<h2 className="font-weight-bold seo-studio-google-pagespeed-configuration-title">
					{title}
				</h2>

				<p className="mb-4 text-secondary">{description}</p>

				{validationError && (
					<ClayAlert
						className="mb-4"
						displayType="warning"
						spritemap={Liferay.Icons.spritemap}
					>
						<strong>{validationError}</strong>
					</ClayAlert>
				)}

				<div className="form-group">
					<label htmlFor="googleApiKey">
						{Liferay.Language.get('api-key')}

						<RequiredMark />
					</label>

					<ClayInput.Group>
						<ClayInput.GroupItem prepend>
							<ClayInput
								id="googleApiKey"
								insetAfter
								onChange={(event) => {
									setApiKey(event.target.value);
									setValidationError('');
								}}
								placeholder={Liferay.Language.get('enter-key')}
								type={visible ? 'text' : 'password'}
								value={apiKey}
							/>

							<ClayInput.GroupInsetItem after>
								<ClayButtonWithIcon
									aria-label={Liferay.Language.get(
										'toggle-api-key-visibility'
									)}
									displayType="unstyled"
									onClick={() => setVisible(!visible)}
									spritemap={Liferay.Icons.spritemap}
									symbol={visible ? 'hidden' : 'view'}
								/>
							</ClayInput.GroupInsetItem>
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</div>
			</div>
		</>
	);
}
