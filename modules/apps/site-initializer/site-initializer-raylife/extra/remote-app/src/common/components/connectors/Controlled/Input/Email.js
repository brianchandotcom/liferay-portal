import React from 'react';
import {ControlledInput} from '.';

import {EMAIL_REGEX} from '../../../../utils/patterns';

export function EmailControlledInput({rules, ...props}) {
	return (
		<ControlledInput
			{...props}
			rules={{
				pattern: {
					message: 'Must be a valid email address.',
					value: EMAIL_REGEX,
				},
				...rules,
			}}
		/>
	);
}
