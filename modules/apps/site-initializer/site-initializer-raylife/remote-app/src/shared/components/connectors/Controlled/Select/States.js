import React from 'react';
import {useLocation} from '~/apps/get-a-quote/hooks/useLocation';
import {STATE_REGEX} from '~/shared/utils/patterns';

import {ControlledSelect} from '.';

export function StatesControlledSelect({rules, ...props}) {
	const {states} = useLocation();

	return (
		<ControlledSelect
			{...props}
			defaultValue="AL"
			rules={{
				pattern: {
					message: 'Should be a two letter word.',
					value: STATE_REGEX,
				},
				...rules,
			}}
		>
			{states.map(({abbreviation}) => (
				<option key={abbreviation} value={abbreviation}>
					{abbreviation}
				</option>
			))}
		</ControlledSelect>
	);
}
