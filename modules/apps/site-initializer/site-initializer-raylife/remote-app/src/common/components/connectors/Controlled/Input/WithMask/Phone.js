import React from 'react';
import {PHONE_REGEX} from '~/common/utils/patterns';

import {ControlledInputWithMask} from '.';

export const PhoneControlledInput = ({
	rules = {},
	inputProps = {},
	...props
}) => {
	return (
		<ControlledInputWithMask
			{...props}
			inputProps={{
				format: '(###) ###-####',
				mask: '_',
				...inputProps,
				placeholder: '(___) ___-____',
			}}
			rules={{
				pattern: {
					message: 'Must be a valid phone number.',
					value: PHONE_REGEX,
				},
				...rules,
			}}
		/>
	);
};
