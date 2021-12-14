import React from 'react';

import {ControlledInput} from '.';

export function NumberControlledInput({...props}) {
	return (
		<ControlledInput
			{...props}
			inputProps={{
				className: 'mb-5',
				onWheel: (event) => event.target.blur(),
				type: 'number',
			}}
		/>
	);
}
