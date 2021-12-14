import React from 'react';

import {ControlledInputWithMask} from '.';

export function SquareFeatControlledInput({inputProps = {}, ...props}) {
	return (
		<ControlledInputWithMask
			{...props}
			inputProps={{
				className: 'mb-5',
				suffix: ' ft²',
				thousandSeparator: true,
				...inputProps,
			}}
		/>
	);
}
