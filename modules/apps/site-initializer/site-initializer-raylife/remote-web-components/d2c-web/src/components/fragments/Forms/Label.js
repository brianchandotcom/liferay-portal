import React from 'react';

export const Label = ({children, label, name, required = false}) => {
	return (
		<label htmlFor={name}>
			<span className={`${required && 'required'}`}>{label}</span>
			{children}
		</label>
	);
};
