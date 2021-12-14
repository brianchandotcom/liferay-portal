import React from 'react';

export function Label({children, label, name, required = false}) {
	return (
		<label htmlFor={name}>
			<h6 className={`align-items-center d-flex ${required && 'required'} font-weight-bolder`}>
				{label}
			</h6>

			{children}
		</label>
	);
}
