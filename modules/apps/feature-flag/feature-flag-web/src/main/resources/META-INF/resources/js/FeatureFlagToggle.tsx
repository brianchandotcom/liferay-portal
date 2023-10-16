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
	enabled: boolean;
	featureFlagKey: string;
	inputName: string;
	onItemsChange: (value: Array<any>) => void;
}

const FeatureFlagToggle = ({
	ariaDescribedBy,
	companyId,
	disabled,
	enabled,
	featureFlagKey,
	inputName,
	onItemsChange,
}: IProps) => {
	const [isLoading, setIsLoading] = useState(false);

	const updateToggled = async (newToggled: boolean) => {
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
				const data = await response.json();

				onItemsChange([
					{
						enabled: newToggled,
						key: featureFlagKey,
					},
					...(data.dependentFeatureFlags.length
						? data.dependentFeatureFlags
						: []),
				]);
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
	};

	return (
		<>
			<ClayToggle
				aria-describedby={ariaDescribedBy}
				disabled={disabled || isLoading}
				id={inputName}
				label={
					enabled
						? Liferay.Language.get('enabled')
						: Liferay.Language.get('disabled')
				}
				onToggle={updateToggled}
				toggled={enabled}
				type="checkbox"
			/>
		</>
	);
};

export default FeatureFlagToggle;
