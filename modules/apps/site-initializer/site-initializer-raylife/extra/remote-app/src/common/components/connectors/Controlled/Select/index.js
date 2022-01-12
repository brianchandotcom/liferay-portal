/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
