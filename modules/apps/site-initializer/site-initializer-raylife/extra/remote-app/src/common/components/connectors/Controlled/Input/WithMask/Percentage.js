import React from 'react';

import {ControlledInputWithMask} from '.';

export function PercentageControlledInput({inputProps = {}, ...props}) {
	return (
		<ControlledInputWithMask
			{...props}
			inputProps={{
				className: 'mb-5',
				decimalScale: 2,
				mask: '_',
				placeholder: '%',
				suffix: '%',
				...inputProps,
			}}
		/>
	);
}
