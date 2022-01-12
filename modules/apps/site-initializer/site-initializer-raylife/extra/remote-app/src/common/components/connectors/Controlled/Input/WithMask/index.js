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

import classNames from 'classnames';
import React from 'react';
import {Controller} from 'react-hook-form';
import {MoreInfoButton} from '../../../../fragments/Buttons/MoreInfo';
import {InputWithMask} from '../../../../fragments/Forms/Input/WithMask';

export function ControlledInputWithMask({
	name,
	label,
	rules,
	control,
	moreInfoProps = undefined,
	inputProps = {},
	renderInput = true,
	...props
}) {
	const newRules = renderInput ? rules : {required: false};

	return (
		<Controller
			control={control}
			defaultValue=""
			name={name}
			render={({field, fieldState}) => (
				<InputWithMask
					className={`${classNames({
						'd-none': !renderInput,
					})} mb-5`}
					error={fieldState.error}
					label={label}
					renderActions={
						moreInfoProps && <MoreInfoButton {...moreInfoProps} />
					}
					required={newRules?.required}
					{...field}
					{...inputProps}
				/>
			)}
			rules={newRules}
			{...props}
		/>
	);
}
