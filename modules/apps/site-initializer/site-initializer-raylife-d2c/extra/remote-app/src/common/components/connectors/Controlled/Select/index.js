/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {Controller} from 'react-hook-form';
import {MoreInfoButton} from '../../../../../common/components/fragments/Buttons/MoreInfo';
import {Select} from '../../../../../common/components/fragments/Forms/Select';

export function ControlledSelect({
	name,
	label,
	control,
	rules,
	children,
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
				<Select
					{...field}
					error={fieldState.error}
					label={label}
					renderActions={
						moreInfoProps && <MoreInfoButton {...moreInfoProps} />
					}
					required={rules?.required}
					{...inputProps}
				>
					{children}
				</Select>
			)}
			rules={rules}
			{...props}
		/>
	);
}
