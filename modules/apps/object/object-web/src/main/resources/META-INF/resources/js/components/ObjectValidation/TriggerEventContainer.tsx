/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Card, Select} from '@liferay/object-js-components-web';
import React from 'react';

interface ITriggerEventProps {
	disabled: boolean;
	eventTypes: {label: string}[];
}

export function TriggerEventContainer({
	disabled,
	eventTypes,
}: ITriggerEventProps) {
	return (
		<Card title={Liferay.Language.get('trigger-event')}>
			<Select
				defaultValue={Liferay.Language.get('on-submission')}
				disabled={disabled}
				label={Liferay.Language.get('event')}
				options={eventTypes}
			/>
		</Card>
	);
}
