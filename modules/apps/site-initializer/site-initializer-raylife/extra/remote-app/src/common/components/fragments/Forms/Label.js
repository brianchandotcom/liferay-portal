import classNames from 'classnames';
import React from 'react';

export function Label({children, className, label, name, required = false}) {
	return (
		<label className="align-items-center" htmlFor={name}>
			<h6
				className={classNames(
					'd-inline-block font-weight-bolder',
					className,
					{required}
				)}
			>
				{label}
			</h6>

			{children}
		</label>
	);
}
