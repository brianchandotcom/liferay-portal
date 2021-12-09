import React from 'react';
import {ControlledInput} from '.';

import {WEBSITE_REGEX} from '../../../../utils/patterns';

export function WebsiteControlledInput({rules, ...props}) {
	return (
		<ControlledInput
			{...props}
			inputProps={{
				className: 'mb-5 mr-0 row',
			}}
			rules={{
				pattern: {
					message: 'Should be a valid website address.',
					value: WEBSITE_REGEX,
				},
				...rules,
			}}
		/>
	);
}
