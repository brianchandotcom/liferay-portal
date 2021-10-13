import React from 'react';
import {STATE_REGEX} from '~/common/utils/patterns';
import {useLocation} from '~/routes/get-a-quote/hooks/useLocation';

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
