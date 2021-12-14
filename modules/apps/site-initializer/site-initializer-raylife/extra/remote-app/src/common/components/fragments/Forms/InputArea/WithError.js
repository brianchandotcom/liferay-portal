import classNames from 'classnames';
import React from 'react';
import {WarningBadge} from '../../Badges/Warning';

export function InputAreaWithError({children, className, error}) {
	return (
		<div
			className={classNames('input-area d-block', {
				[className]: className,
				'has-error': error,
			})}
		>
			{children}

			{error?.message && <WarningBadge>{error.message}</WarningBadge>}
		</div>
	);
}
