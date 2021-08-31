import React from 'react';

import {ControlledInputWithMask} from '.';
import {FEIN_REGEX} from '../../../../../utils/patterns';

export const FEINControlledInput = ({
	rules = {},
	inputProps = {},
	...props
}) => {
	return (
		<ControlledInputWithMask
			{...props}
			inputProps={{format: '##-#######', mask: '_', ...inputProps}}
			rules={{
				pattern: {
					message: 'Please enter a valid FEIN.',
					value: FEIN_REGEX,
				},
				...rules,
			}}
		/>
	);
};
