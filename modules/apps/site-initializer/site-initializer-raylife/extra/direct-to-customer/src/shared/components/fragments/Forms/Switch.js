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
				<div className="switch-wrapper">
					<button
						className={`btn switch ${
							value === 'true' && 'selected'
						}`}
						onClick={() => onChange('true')}
						type="button"
					>
						Yes
					</button>

					<button
						className={`btn switch ${
							value === 'false' && 'selected'
						}`}
						onClick={() => onChange('false')}
						type="button"
					>
						No
					</button>
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
