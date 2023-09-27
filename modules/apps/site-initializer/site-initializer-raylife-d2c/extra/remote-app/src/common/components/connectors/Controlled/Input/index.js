/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {Controller} from 'react-hook-form';
import {MoreInfoButton} from '../../../fragments/Buttons/MoreInfo';
import {Input} from '../../../fragments/Forms/Input';

export function ControlledInput({
	name,
	label,
	rules,
	control,
	moreInfoProps = undefined,
	inputProps = {},
	...props
}) {
	return (
		<Controller
			control={control}
			defaultValue=""
			name={name}
			render={({field, fieldState}) => (
				<Input
					{...field}
					error={fieldState.error}
					label={label}
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
