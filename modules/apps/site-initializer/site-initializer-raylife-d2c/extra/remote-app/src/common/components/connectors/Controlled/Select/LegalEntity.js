/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClaySelect} from '@clayui/form';

import React from 'react';
import {ControlledSelect} from '.';

import {useLegalEntity} from '../../../../../routes/get-a-quote/hooks/useLegalEntity';

export function LegalEntityControlledSelect({...props}) {
	const {entities} = useLegalEntity();

	return (
		<ControlledSelect {...props}>
			<ClaySelect.Option hidden label="Select" />

			{entities.map(({name}) => (
				<ClaySelect.Option key={name} label={name} value={name} />
			))}
		</ControlledSelect>
	);
}
