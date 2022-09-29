/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
