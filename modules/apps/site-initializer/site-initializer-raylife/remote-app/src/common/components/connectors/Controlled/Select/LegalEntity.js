import React from 'react';
import {useLegalEntity} from '~/routes/get-a-quote/hooks/useLegalEntity';

import {ControlledSelect} from '.';

export function LegalEntityControlledSelect({...props}) {
	const {entities} = useLegalEntity();

	return (
		<ControlledSelect {...props}>
			<option hidden>Select</option>
			{entities.map(({name}) => (
				<option key={name} value={name}>
					{name}
				</option>
			))}
		</ControlledSelect>
	);
}
