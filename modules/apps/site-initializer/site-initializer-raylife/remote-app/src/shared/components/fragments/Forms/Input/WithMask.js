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
					className="input"
					name={name}
					ref={ref}
					required={required}
				/>
			</InputAreaWithError>
		);
	}
);
