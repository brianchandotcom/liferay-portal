import {ClayInput} from '@clayui/form';
import React from 'react';
import {InputAreaWithError} from '../InputArea/WithError';
import {Label} from '../Label';

export const Input = React.forwardRef(
	(
		{
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

				<ClayInput
					{...props}
					id={name}
					name={name}
					ref={ref}
					required={required}
				/>
			</InputAreaWithError>
		);
	}
);
