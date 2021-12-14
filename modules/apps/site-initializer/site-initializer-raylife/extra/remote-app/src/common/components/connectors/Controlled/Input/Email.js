import React from 'react';
import {ControlledInput} from '.';

import {EMAIL_REGEX} from '../../../../utils/patterns';

export function EmailControlledInput({rules, ...props}) {
	return (
		<ControlledInput
			{...props}
			inputProps={{
				className: 'd-flex mb-5 mr-0',
			}}
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
