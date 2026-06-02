/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import {Provider} from '@clayui/provider';
import {openToast} from '@liferay/object-js-components-web';
import React, {useEffect, useState} from 'react';

import Toolbar from '../components/ToolBar';
import {
	getConfiguration,
	putConfiguration,
} from './services/ConfigurationService';
import {Configuration} from './types/Configuration';

export default function ConfigurationForm({
	externalReferenceCode,
}: {
	externalReferenceCode: string;
}) {
	const [formData, setFormData] = useState({
		environmentUrls: '',
		recipientEmailAddress: '',
	});
	const [loading, setLoading] = useState(true);
	const [saving, setSaving] = useState(false);

	useEffect(() => {
		if (!externalReferenceCode) {
			setLoading(false);

			return;
		}

		getConfiguration(externalReferenceCode)
			.then((config) => {
				setFormData({
					environmentUrls: config.environmentUrls || '',
					recipientEmailAddress: config.recipientEmailAddress || '',
				});
			})
			.catch(() => {
				openToast({
					message: Liferay.Language.get(
						'failed-to-load-configuration'
					),
					type: 'danger',
				});
			})
			.finally(() => {
				setLoading(false);
			});
	}, [externalReferenceCode]);

	const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
		const {name, value} = event.target;

		setFormData((prev) => ({...prev, [name]: value}));
	};

	const handleSave = async () => {
		setSaving(true);

		try {
			await putConfiguration(externalReferenceCode, {
				...formData,
				externalReferenceCode,
			} as Configuration);

			openToast({
				message: Liferay.Language.get('configuration-saved'),
				type: 'success',
			});
		}
		catch (error) {
			openToast({
				message:
					error instanceof Error
						? error.message
						: Liferay.Language.get('failed-to-save-configuration'),
				type: 'danger',
			});
		}
		finally {
			setSaving(false);
		}
	};

	if (loading) {
		return null;
	}

	return (
		<Provider spritemap={Liferay.Icons.spritemap}>
			<Toolbar title={Liferay.Language.get('configuration')}>
				<Toolbar.Item>
					<Button
						disabled={saving || !externalReferenceCode}
						displayType="primary"
						onClick={handleSave}
					>
						{Liferay.Language.get('save')}
					</Button>
				</Toolbar.Item>
			</Toolbar>

			<ClayLayout.ContainerFluid className="p-4">
				<ClayForm>
					<ClayForm.Group>
						<label htmlFor="environmentUrls">
							{Liferay.Language.get('environment-urls')}
						</label>

						<ClayInput
							id="environmentUrls"
							name="environmentUrls"
							onChange={handleInputChange}
							type="text"
							value={formData.environmentUrls}
						/>
					</ClayForm.Group>

					<ClayForm.Group>
						<label htmlFor="recipientEmailAddress">
							{Liferay.Language.get('recipient-email-address')}
						</label>

						<ClayInput
							id="recipientEmailAddress"
							name="recipientEmailAddress"
							onChange={handleInputChange}
							type="email"
							value={formData.recipientEmailAddress}
						/>
					</ClayForm.Group>
				</ClayForm>
			</ClayLayout.ContainerFluid>
		</Provider>
	);
}
