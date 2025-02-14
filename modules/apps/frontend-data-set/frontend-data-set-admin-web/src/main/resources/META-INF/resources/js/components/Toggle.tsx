/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayToggle} from '@clayui/form';
import React from 'react';

import '../../css/components/Toggle.scss';

const ToggleStatus = ({
	disabled = false,
	item,
	toggleChange,
}: {
	disabled: boolean;
	item: any;
	toggleChange: (item: any) => Promise<void>;
}) => {
	const label = item.active
		? Liferay.Language.get('active')
		: Liferay.Language.get('inactive');

	return (
		<div className="dsm-toggle-switch">
			<ClayToggle
				disabled={disabled}
				label={label}
				onToggle={() => toggleChange(item)}
				sizing="sm"
				toggled={item.active}
			/>
		</div>
	);
};

export default ToggleStatus;
