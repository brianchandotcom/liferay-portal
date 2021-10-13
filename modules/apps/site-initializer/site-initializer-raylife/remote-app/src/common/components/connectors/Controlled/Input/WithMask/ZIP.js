import React from 'react';
import {ZIP_REGEX} from '~/common/utils/patterns';

import {ControlledInputWithMask} from '.';

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
