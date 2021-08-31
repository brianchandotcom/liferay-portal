import React from 'react';

import {ControlledInputWithMask} from '.';

export const PercentageControlledInput = ({inputProps = {}, ...props}) => {
	return (
		<ControlledInputWithMask
			{...props}
			inputProps={{
				decimalScale: 2,
				mask: '_',
				placeholder: '%',
				suffix: '%',
				...inputProps,
			}}
		/>
	);
};
