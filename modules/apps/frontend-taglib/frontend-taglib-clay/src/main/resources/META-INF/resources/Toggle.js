/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayToggle} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React, {useState} from 'react';

import './Toggle.scss';

export default function Toggle({
	additionalProps: _additionalProps,
	componentId: _componentId,
	cssClass,
	helpText,
	label,
	locale: _locale,
	name,
	offLabel,
	onLabel,
	portletId: _portletId,
	portletNamespace: _portletNamespace,
	toggled: _initialToggled,
	value,
	...otherProps
}) {
	const [toggled, setToggled] = useState(_initialToggled);

	return (
		<ClayToggle
			className={cssClass}
			label={
				<>
					{(toggled ? onLabel : offLabel) ?? label}

					{helpText && (
						<ClayTooltipProvider>
							<span
								className="help-text-icon ml-2"
								title={helpText}
							>
								<ClayIcon symbol="question-circle-full" />
							</span>
						</ClayTooltipProvider>
					)}
				</>
			}
			name={_portletNamespace + name}
			onToggle={setToggled}
			toggled={toggled}
			value={value ?? toggled ? 'on' : undefined}
			{...otherProps}
		/>
	);
}
