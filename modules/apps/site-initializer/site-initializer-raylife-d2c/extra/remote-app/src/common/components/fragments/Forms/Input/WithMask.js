/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import ReactInputMask from 'react-number-format';

import {InputAreaWithError} from '../InputArea/WithError';
import {Label} from '../Label';

export const InputWithMask = React.forwardRef(
	(
		{
			allowNegative = false,
			className,
			error,
			label,
			name,
			renderActions,
			required = false,
			...props
		},
		ref
	) => {
		return (
			<InputAreaWithError className={className} error={error}>
				{label && (
					<Label label={label} name={name} required={required}>
						{renderActions}
					</Label>
				)}

				<ReactInputMask
					{...props}
					allowNegative={allowNegative}
					className="form-control input w-100"
					id={name}
					name={name}
					ref={ref}
					required={required}
				/>
			</InputAreaWithError>
		);
	}
);
