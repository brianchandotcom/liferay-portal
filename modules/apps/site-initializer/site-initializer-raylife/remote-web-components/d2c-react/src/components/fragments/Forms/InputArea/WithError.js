import React from 'react';

import {WarningBadge} from '../../Badges/Warning';

export const InputAreaWithError = ({children, error}) => {
	return (
		<div className={`input-area ${!!error && 'invalid'}`}>
			{children}
			{error && <WarningBadge>{error.message}</WarningBadge>}
		</div>
	);
};
