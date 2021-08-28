import React from 'react';

import {ControlledInputWithMask} from '.';
import {ZIP_REGEX} from '../../../../../utils/patterns';

export const ZIPControlledInput = ({rules = {}, inputProps = {}, ...props}) => {
	return (
		<ControlledInputWithMask
			{...props}
			inputProps={{format: '#####', mask: '_', ...inputProps}}
			rules={{
				pattern: {
					message: 'Must be a five digit number.',
					value: ZIP_REGEX,
				},
				...rules,
			}}
		/>
	);
};
