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
	onItemsChange: (value: Array<any>) => void;
	toggled: boolean;
}

const FeatureFlagToggle = ({
	ariaDescribedBy,
	companyId,
	disabled,
	featureFlagKey,
	inputName,
	labelOff,
	labelOn,
	onItemsChange,
	toggled: initialToggled,
}: IProps) => {
	const [isLoading, setIsLoading] = useState(false);
	const [toggled, setToggled] = useState(initialToggled);

	async function updateToggled(newToggled: boolean) {
		setIsLoading(true);

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
				const responseData = await response.json();
				setToggled(newToggled);

				if (responseData.dependentFeatureFlags.length) {
					onItemsChange(responseData.dependentFeatureFlags);
				}
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
			setIsLoading(false);
		}
	}

	return (
		<>
			<ClayToggle
				aria-describedby={ariaDescribedBy}
				disabled={disabled || isLoading}
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
