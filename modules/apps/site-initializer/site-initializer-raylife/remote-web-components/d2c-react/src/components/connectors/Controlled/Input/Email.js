import React from 'react';

import {ControlledInput} from '.';
import {EMAIL_REGEX} from '../../../../utils/patterns';

export const EmailControlledInput = ({rules, ...props}) => {
	return (
		<ControlledInput
			{...props}
			rules={{
				pattern: {
					message: 'Should be a valid email address.',
					value: EMAIL_REGEX,
				},
				...rules,
			}}
		/>
	);
};
