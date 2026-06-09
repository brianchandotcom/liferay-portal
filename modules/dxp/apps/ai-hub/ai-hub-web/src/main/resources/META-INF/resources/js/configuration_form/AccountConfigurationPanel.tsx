/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import {FieldBase} from 'frontend-js-components-web';
import React from 'react';

import {Configuration} from './types/Configuration';

interface AccountConfigurationPanelProps {
	setField: <K extends keyof Configuration>(
		field: K,
		value: Configuration[K]
	) => void;
	values: Configuration;
}

export default function AccountConfigurationPanel({
	setField,
	values,
}: AccountConfigurationPanelProps) {
	return (
		<>
			<h2 className="mb-4">
				{Liferay.Language.get('account-configuration')}
			</h2>

			<FieldBase
				id="environmentURLs"
				label={Liferay.Language.get('environment-url')}
			>
				<ClayInput
					id="environmentURLs"
					name="environmentURLs"
					onChange={(event) =>
						setField('environmentURLs', event.target.value)
					}
					type="text"
					value={values.environmentURLs}
				/>
			</FieldBase>
		</>
	);
}
