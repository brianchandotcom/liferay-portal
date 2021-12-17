import {ClaySelect} from '@clayui/form';

import React from 'react';
import {InputAreaWithError} from './InputArea/WithError';
import {Label} from './Label';

export const Select = React.forwardRef(
	(
		{
			children,
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

				<ClaySelect
					{...props}
					id={name}
					name={name}
					ref={ref}
					required={required}
				>
					{children}
				</ClaySelect>
			</InputAreaWithError>
		);
	}
);
