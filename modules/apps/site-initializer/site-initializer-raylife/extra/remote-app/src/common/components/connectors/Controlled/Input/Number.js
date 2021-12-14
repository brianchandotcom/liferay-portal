import React from 'react';

import {ControlledInput} from '.';

export function NumberControlledInput({...props}) {
	return (
		<ControlledInput
			{...props}
			inputProps={{
				className: 'mb-4',
				onWheel: (event) => event.target.blur(),
				type: 'number',
			}}
		/>
	);
}
