/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayToggle} from '@clayui/form';
import React, {useState} from 'react';

interface IProps {
	ariaDescribedBy: string;
	companyId: number;
	disabled: boolean;
	featureFlagKey: string;
	inputName: string;
	labelOff: string;
	labelOn: string;
	toggled: boolean;
}

const FeatureFlagToggle = ({
	ariaDescribedBy,
	companyId,
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
						companyId,
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
				aria-describedby={ariaDescribedBy}
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
