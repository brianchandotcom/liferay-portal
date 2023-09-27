/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

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
}: Props) => {
	const ariaDescriptionId = `accessibilityHelp${index}`;

	return (
		<li>
			<ClayForm.Group>
				<ClayToggle
					aria-describedby={ariaDescriptionId}
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
					<ClayForm.Text id={ariaDescriptionId}>
						{description}
					</ClayForm.Text>
				</ClayForm.FeedbackGroup>
			</ClayForm.Group>
		</li>
	);
};

export default AccessibilitySetting;
