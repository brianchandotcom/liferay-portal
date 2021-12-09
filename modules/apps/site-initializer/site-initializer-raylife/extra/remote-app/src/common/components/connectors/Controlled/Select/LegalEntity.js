import {ClaySelect} from '@clayui/form';

import React from 'react';
import {ControlledSelect} from '.';

import {useLegalEntity} from '../../../../../routes/get-a-quote/hooks/useLegalEntity';

export function LegalEntityControlledSelect({...props}) {
	const {entities} = useLegalEntity();

	return (
		<ControlledSelect {...props}>
			<ClaySelect.Option hidden label="Select" />

			{entities.map(({name}) => (
				<ClaySelect.Option key={name} label={name} value={name} />
			))}
		</ControlledSelect>
	);
}
