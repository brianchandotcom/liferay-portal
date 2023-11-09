/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import getCN from 'classnames';
import React, {useState} from 'react';

const TIME_RANGE_TYPES = [
	{
		label: Liferay.Util.sub(Liferay.Language.get('last-x-hours'), ['24']),
		value: '0',
	},
	{
		label: Liferay.Language.get('yesterday') + ` (00:00-23:59)`,
		value: '1',
	},
	{
		label: Liferay.Language.get('last-7-days'),
		value: '7',
	},
	{
		label: Liferay.Util.sub(Liferay.Language.get('last-x-days'), ['28']),
		value: '28',
	},
	{
		label: Liferay.Language.get('last-30-days'),
		value: '20',
	},
];

export default function TimeRangeInput({
	onBlur,
	onChange,
	touched,
	value = '0',
}) {
	const [activeDropdown, setActiveDropdown] = useState(false);

	return (
		<ClayInput.GroupItem
			className={getCN({
				'has-error': !value && touched,
			})}
		>
			<label>
				{Liferay.Language.get('time-range')}

				<span className="reference-mark">
					<ClayIcon symbol="asterisk" />
				</span>

				<ClayTooltipProvider>
					<span
						className="c-ml-2"
						data-tooltip-align="top"
						title={Liferay.Language.get('time-range-help')}
					>
						<ClayIcon symbol="question-circle-full" />
					</span>
				</ClayTooltipProvider>
			</label>

			<ClayInput.Group>
				<ClayInput.Group>
					<ClayDropDown
						active={activeDropdown}
						className="w-100"
						closeOnClick={true}
						closeOnClickOutside={true}
						onActiveChange={setActiveDropdown}
						onBlur={onBlur}
						trigger={
							<ClayButton
								className="form-control form-control-select"
								displayType="secondary"
							>
								{
									TIME_RANGE_TYPES.find(
										(item) => item.value === value
									)?.label
								}
							</ClayButton>
						}
					>
						<ClayDropDown.ItemList items={TIME_RANGE_TYPES}>
							{(item) => (
								<ClayDropDown.Item
									key={item.value}
									name={item.value}
									onClick={() => onChange(item.value)}
								>
									{item.label}
								</ClayDropDown.Item>
							)}
						</ClayDropDown.ItemList>
					</ClayDropDown>
				</ClayInput.Group>
			</ClayInput.Group>
		</ClayInput.GroupItem>
	);
}
