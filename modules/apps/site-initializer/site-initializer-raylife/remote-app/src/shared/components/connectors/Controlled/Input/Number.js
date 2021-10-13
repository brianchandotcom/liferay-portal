import React from 'react';

import {ControlledInput} from '.';

export const NumberControlledInput = ({...props}) => {
	return (
		<ControlledInput
			{...props}
			inputProps={{
				onWheel: (event) => event.target.blur(),
				type: 'number',
			}}
		/>
	);
};
