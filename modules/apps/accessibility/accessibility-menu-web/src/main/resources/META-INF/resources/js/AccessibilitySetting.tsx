/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Text} from '@clayui/core';
import {ClayToggle} from '@clayui/form';
import React from 'react';

const KEY_EVENT = 'Enter';

type Props = {
	className: string;
	description: string;
	disabled: boolean;
	index: number;
	label: string;
	onChange: (value: boolean) => void;
	value: boolean;
};

const AccessibilitySetting = ({
	className,
	description,
	disabled,
	index,
	label,
	onChange,
	value,
}: Props) => (
	<li className={className}>
		<ClayToggle
			aria-describedby={`accessibility-help-${index}`}
			disabled={disabled}
			label={label}
			onKeyDown={(event) => {
				if (!disabled && event.key === KEY_EVENT) {
					onChange(!value);
				}
			}}
			onToggle={onChange}
			toggled={value}
		/>

		<span className="toggle-switch-text">
			<Text color="secondary" id={`accessibility-help-${index}`} size={3}>
				{description}
			</Text>
		</span>
	</li>
);

export default AccessibilitySetting;
