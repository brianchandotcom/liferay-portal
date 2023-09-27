/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

				<div className="align-items-center d-flex flex-row justify-content-center justify-content-lg-start justify-content-md-start mb-1 mb-md-4">
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
