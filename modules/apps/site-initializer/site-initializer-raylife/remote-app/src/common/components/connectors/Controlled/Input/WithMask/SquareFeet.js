import React from 'react';

import {ControlledInputWithMask} from '.';

export const SquareFeatControlledInput = ({inputProps = {}, ...props}) => {
	return (
		<ControlledInputWithMask
			{...props}
			inputProps={{
				suffix: ' ft²',
				thousandSeparator: true,
				...inputProps,
			}}
		/>
	);
};
