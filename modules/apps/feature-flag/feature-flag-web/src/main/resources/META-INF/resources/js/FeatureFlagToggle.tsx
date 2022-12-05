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

import {ClayToggle} from '@clayui/form';
import React, {useState} from 'react';

interface IProps {
	enabled: boolean;
	featureFlagKey: string;
	inputName: string;
	labelOff: string;
	labelOn: string;
	symbolOff: string;
	symbolOn: string;
}

const FeatureFlagToggle = ({
	enabled: initialEnabled,
	featureFlagKey,
	inputName,
	labelOff,
	labelOn,
	symbolOff,
	symbolOn,
}: IProps) => {
	const [enabled, setEnabled] = useState(initialEnabled);

	async function updateEnabled(newEnabled: boolean) {
		const response = await Liferay.Util.fetch(
			'/o/com-liferay-feature-flag-web/set-enabled',
			{
				body: Liferay.Util.objectToFormData({
					enabled: newEnabled,
					key: featureFlagKey,
				}),
				method: 'POST',
			}
		);

		if (response.ok) {
			setEnabled(newEnabled);
		}
		else {
			Liferay.Util.openToast({
				message: Liferay.Language.get('could-not-update-feature-flag'),
				type: 'danger',
			});
		}
	}

	return (
		<>
			<ClayToggle
				id={inputName}
				label={enabled ? labelOn : labelOff}
				onToggle={updateEnabled}
				symbol={{off: symbolOff, on: symbolOn}}
				toggled={enabled}
				type="checkbox"
			/>
		</>
	);
};

export default FeatureFlagToggle;
