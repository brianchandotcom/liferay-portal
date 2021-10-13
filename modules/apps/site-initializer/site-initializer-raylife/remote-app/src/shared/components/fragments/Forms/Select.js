import ClayIcon from '@clayui/icon';
import React from 'react';

import {InputAreaWithError} from './InputArea/WithError';
import {Label} from './Label';

export const Select = React.forwardRef(
	(
		{
			children,
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
			<InputAreaWithError error={error}>
				{label && (
					<Label label={label} name={name} required={required}>
						{renderActions}
					</Label>
				)}
				<ClayIcon className="select-icon" symbol="caret-bottom" />

				<select
					{...props}
					className="input"
					name={name}
					ref={ref}
					required={required}
				>
					{children}
				</select>
			</InputAreaWithError>
		);
	}
);
