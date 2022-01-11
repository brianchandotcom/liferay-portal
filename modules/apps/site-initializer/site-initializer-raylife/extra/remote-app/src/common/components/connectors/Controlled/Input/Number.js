import React from 'react';

import {ControlledInputWithMask} from './WithMask';

export function NumberControlledInput({...props}) {
	return (
		<ControlledInputWithMask
			{...props}
			inputProps={{
				className: 'mb-5',
			}}
		/>
	);
}
