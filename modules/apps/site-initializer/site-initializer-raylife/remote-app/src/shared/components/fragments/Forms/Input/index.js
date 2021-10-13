import React from 'react';

import {InputAreaWithError} from '../InputArea/WithError';
import {Label} from '../Label';

export const Input = React.forwardRef(
	({error, label, name, renderActions, required = false, ...props}, ref) => {
		return (
			<InputAreaWithError error={error}>
				{label && (
					<Label label={label} name={name} required={required}>
						{renderActions}
					</Label>
				)}
				<input {...props} name={name} ref={ref} required={required} />
			</InputAreaWithError>
		);
	}
);
