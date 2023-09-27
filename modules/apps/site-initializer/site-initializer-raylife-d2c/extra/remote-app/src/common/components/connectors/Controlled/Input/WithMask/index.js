/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
