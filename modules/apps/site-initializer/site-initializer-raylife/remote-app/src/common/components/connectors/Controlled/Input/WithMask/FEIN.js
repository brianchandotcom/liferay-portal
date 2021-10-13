import React from 'react';
import {FEIN_REGEX} from '~/common/utils/patterns';

import {ControlledInputWithMask} from '.';

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
