import React from 'react';
import {WEBSITE_REGEX} from '~/common/utils/patterns';

import {ControlledInput} from '.';

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
