import React from 'react';

import {ControlledSelect} from '.';
import {useLocation} from '../../../../hooks/useLocation';
import {STATE_REGEX} from '../../../../utils/patterns';

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
