import React from 'react';

import {ControlledInput} from '.';
import {WEBSITE_REGEX} from '../../../../utils/patterns';

export const WebsiteControlledInput = ({rules, ...props}) => {
	return (
		<ControlledInput
			{...props}
			rules={{
				pattern: {
					message: 'Should be a valid website address.',
					value: WEBSITE_REGEX,
				},
				...rules,
			}}
		/>
	);
};
