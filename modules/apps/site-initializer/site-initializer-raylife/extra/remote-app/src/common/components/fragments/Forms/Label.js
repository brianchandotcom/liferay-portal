import classNames from 'classnames';
import React from 'react';

export function Label({children, label, name, required = false}) {
	return (
		<label htmlFor={name}>
			<h6
				className={classNames(
					`align-items-center d-flex font-weight-bolder`,
					{required}
				)}
			>
				{label}
			</h6>

			{children}
		</label>
	);
}
