import React from 'react';

import {ControlledInputWithMask} from '.';

export function CurrencyControlledInput({inputProps = {}, ...props}) {
	return (
		<ControlledInputWithMask
			{...props}
			inputProps={{
				className: 'mb-5',
				decimalScale: 2,
				fixedDecimalScale: true,
				prefix: '$',
				thousandSeparator: true,
				...inputProps,
			}}
		/>
	);
}
