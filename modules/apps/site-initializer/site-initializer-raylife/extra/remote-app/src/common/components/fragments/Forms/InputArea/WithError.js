import classNames from 'classnames';
import React from 'react';
import {WarningBadge} from '../../Badges/Warning';

export function InputAreaWithError({children, className, error}) {
	return (
		<div
			className={classNames('input-area', {
				[className]: className,
				invalid: error,
			})}
		>
			{children}

			{error?.message && <WarningBadge>{error.message}</WarningBadge>}
		</div>
	);
}
