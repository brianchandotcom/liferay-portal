/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClaySelect} from '@clayui/form';
import React from 'react';

const options = [
	{
		label: Liferay.Language.get('second'),
		scale: 'second',
	},
	{
		label: Liferay.Language.get('minute'),
		scale: 'minute',
	},
	{
		label: Liferay.Language.get('hour'),
		scale: 'hour',
	},
	{
		label: Liferay.Language.get('day'),
		scale: 'day',
	},
	{
		label: Liferay.Language.get('week'),
		scale: 'week',
	},
	{
		label: Liferay.Language.get('month'),
		scale: 'month',
	},
	{
		label: Liferay.Language.get('year'),
		scale: 'year',
	},
];

const SelectTimeScale = ({setTimerScale, timerScale}) => {
	const handleChange = (target) => {
		setTimerScale(target.value);
	};

	return (
		<ClayForm.Group className="mb-0">
			<ClaySelect
				aria-label="Select"
				defaultValue={timerScale}
				id="scale"
				onChange={({target}) => {
					handleChange(target);
				}}
			>
				{options.map((item) => (
					<ClaySelect.Option
						key={item.scale}
						label={item.label}
						value={item.scale}
					/>
				))}
			</ClaySelect>
		</ClayForm.Group>
	);
};

export default SelectTimeScale;
