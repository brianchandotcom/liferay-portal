import React from 'react';

import {ControlledInput} from '.';

export const NumberControlledInput = ({...props}) => {
	return (
		<ControlledInput
			{...props}
			inputProps={{
				type: 'number',
			}}
		/>
	);
};
