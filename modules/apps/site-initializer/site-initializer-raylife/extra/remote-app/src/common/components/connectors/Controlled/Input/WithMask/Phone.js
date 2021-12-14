import React from 'react';
import {ControlledInputWithMask} from '.';

import {PHONE_REGEX} from '../../../../../utils/patterns';

export function PhoneControlledInput({rules = {}, inputProps = {}, ...props}) {
	return (
		<ControlledInputWithMask
			{...props}
			inputProps={{
				className: 'd-flex mb-5 mr-0',
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
}
