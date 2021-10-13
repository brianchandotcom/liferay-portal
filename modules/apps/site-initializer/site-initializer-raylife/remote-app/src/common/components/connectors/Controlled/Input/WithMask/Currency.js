import React from 'react';

import {ControlledInputWithMask} from '.';

export const CurrencyControlledInput = ({inputProps = {}, ...props}) => {
	return (
		<ControlledInputWithMask
			{...props}
			inputProps={{
				decimalScale: 2,
				fixedDecimalScale: true,
				prefix: '$',
				thousandSeparator: true,
				...inputProps,
			}}
		/>
	);
};
