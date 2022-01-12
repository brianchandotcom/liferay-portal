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

import ClayButton from '@clayui/button';
import classNames from 'classnames';
import React from 'react';

import {InputAreaWithError} from './InputArea/WithError';
import {Label} from './Label';

export const Switch = React.forwardRef(
	(
		{
			name,
			label,
			renderActions,
			error,
			value = 'true',
			required = false,
			onChange = () => {},
			...props
		},
		ref
	) => {
		return (
			<InputAreaWithError error={error}>
				{label && (
					<Label label={label} name={name} required={required}>
						{renderActions}
					</Label>
				)}

				<div className="align-items-center d-flex flex-row justify-content-start mb-5">
					<ClayButton
						className={classNames(
							'btn-ghost btn-style-primary mr-2 pl-5 pr-5 rounded-pill switch',
							{
								selected: value === 'true',
							}
						)}
						displayType={null}
						onClick={() => onChange('true')}
						type="button"
					>
						Yes
					</ClayButton>

					<ClayButton
						className={classNames(
							'btn-ghost btn-style-primary pl-5 pr-5 rounded-pill switch',
							{
								selected: value === 'false',
							}
						)}
						displayType={null}
						onClick={() => onChange('false')}
						type="button"
					>
						No
					</ClayButton>
				</div>

				<input
					{...props}
					className="hidden"
					name={name}
					onChange={onChange}
					ref={ref}
					value={value}
				/>
			</InputAreaWithError>
		);
	}
);
