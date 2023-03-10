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
	disabled: boolean;
	featureFlagKey: string;
	inputName: string;
	labelOff: string;
	labelOn: string;
	toggled: boolean;
}

const FeatureFlagToggle = ({
	disabled: initialDisabled,
	featureFlagKey,
	inputName,
	labelOff,
	labelOn,
	toggled: initialToggled,
}: IProps) => {
	const [disabled, setDisabled] = useState(initialDisabled);
	const [toggled, setToggled] = useState(initialToggled);

	async function updateToggled(newToggled: boolean) {
		setDisabled(true);

		try {
			const response = await Liferay.Util.fetch(
				'/o/com-liferay-feature-flag-web/set-enabled',
				{
					body: Liferay.Util.objectToFormData({
						enabled: newToggled,
						key: featureFlagKey,
					}),
					method: 'POST',
				}
			);

			if (response.ok) {
				setToggled(newToggled);
			}
			else {
				Liferay.Util.openToast({
					message: Liferay.Language.get(
						'could-not-update-feature-flag'
					),
					type: 'danger',
				});
			}
		}
		finally {
			setDisabled(false);
		}
	}

	return (
		<>
			<ClayToggle
				disabled={disabled}
				id={inputName}
				label={toggled ? labelOn : labelOff}
				onToggle={updateToggled}
				toggled={toggled}
				type="checkbox"
			/>
		</>
	);
};

export default FeatureFlagToggle;
