/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Text} from '@clayui/core';
import ClayForm, {ClayToggle} from '@clayui/form';
import React from 'react';

const KEY_EVENT = 'Enter';

type Props = {
	description: string;
	disabled: boolean;
	index: number;
	label: string;
	onChange: (value: boolean) => void;
	value: boolean;
};

const AccessibilitySetting = ({
	description,
	disabled,
	index,
	label,
	onChange,
	value,
}: Props) => (
	<li>
		<ClayForm.Group>
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

			<ClayForm.FeedbackGroup>
				<Text
					color="secondary"
					id={`accessibility-help-${index}`}
					size={3}
				>
					{description}
				</Text>
			</ClayForm.FeedbackGroup>
		</ClayForm.Group>
	</li>
);

export default AccessibilitySetting;
