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

import ClayForm, {ClayInput, ClaySelectWithOption} from '@clayui/form';
import React, {useState, useContext} from 'react';

const SOURCE_TYPES = {
	fromContentField: 'fromContentField',
	manual: 'manual'
};

const SOURCE_TYPES_OPTIONS = [
	{
		label: `${Liferay.Language.get('manual')}`,
		value: SOURCE_TYPES.manual
	},
	{
		label: `${Liferay.Language.get('from-content-field')}`,
		value: SOURCE_TYPES.fromContentField
	}
];

const TARGET_OPTIONS = [
	{
		label: `${Liferay.Language.get('self')}`,
		value: '_self'
	},
	{
		label: `${Liferay.Language.get('blank')}`,
		value: '_blank'
	},
	{
		label: `${Liferay.Language.get('parent')}`,
		value: '_parent'
	},
	{
		label: `${Liferay.Language.get('top')}`,
		value: '_top'
	}
];

export default function LinkConfigurationPanel() {
	const [sourceType, setSourceType] = useState(SOURCE_TYPES.manual);

	return (
		<>
			<ClayForm.Group small>
				<label htmlFor="floatingToolbarLinkSourceOption">
					{Liferay.Language.get('link')}
				</label>
				<ClaySelectWithOption
					id="floatingToolbarLinkSourceOption"
					onChange={event => setSourceType(event.target.value)}
					options={SOURCE_TYPES_OPTIONS}
					type="text"
				/>
			</ClayForm.Group>
			{sourceType === SOURCE_TYPES.manual ? (
				<ClayForm.Group small>
					<label htmlFor="floatingToolbarLinkHrefOption">
						{Liferay.Language.get('url')}
					</label>
					<ClayInput id="floatingToolbarLinkHrefOption" type="text" />
				</ClayForm.Group>
			) : (
				<div>Mapping gege</div>
			)}
			<ClayForm.Group small>
				<label htmlFor="floatingToolbarLinkTargetOption">
					{Liferay.Language.get('target')}
				</label>
				<ClaySelectWithOption
					id="floatingToolbarLinkTargetOption"
					options={TARGET_OPTIONS}
					type="text"
				/>
			</ClayForm.Group>
		</>
	);
}
