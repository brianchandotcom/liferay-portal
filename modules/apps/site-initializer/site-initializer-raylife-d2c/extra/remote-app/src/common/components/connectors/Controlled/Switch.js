/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {Controller} from 'react-hook-form';
import {MoreInfoButton} from '../../../components/fragments/Buttons/MoreInfo';
import {Switch} from '../../../components/fragments/Forms/Switch';

export function ControlledSwitch({
	name,
	label,
	control,
	rules,
	onSelect = () => {},
	moreInfoProps = undefined,
	inputProps = {},
	defaultValue = '',
	...props
}) {
	return (
		<Controller
			control={control}
			defaultValue={defaultValue}
			name={name}
			render={({field, fieldState}) => (
				<Switch
					{...field}
					error={fieldState.error}
					label={label}
					onChange={(value) => {
						field.onChange(value);

						if (typeof onSelect === 'function') {
							onSelect();
						}
					}}
					renderActions={
						moreInfoProps && <MoreInfoButton {...moreInfoProps} />
					}
					required={rules?.required}
					{...inputProps}
				/>
			)}
			rules={rules}
			{...props}
		/>
	);
}
