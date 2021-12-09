import ClayButton from '@clayui/button';
import classNames from 'classnames';
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

				<div className="align-items-center d-flex flex-row justify-content-start">
					<ClayButton
						className={classNames('btn btn-ghost btn-variant-primary mr-2 pl-5 pr-5 rounded-pill switch',
							{
								'selected': value === 'true',
							}
						)}
						displayType={null}
						onClick={() => onChange('true')}
						type="button"
					>
						Yes
					</ClayButton>

					<ClayButton
						className={classNames('btn btn-ghost btn-variant-primary pl-5 pr-5 rounded-pill switch',
							{
								'selected': value === 'false',
							}
						)}
						displayType={null}
						onClick={() => onChange('false')}
						type="button"
					>
						No
					</ClayButton>
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
