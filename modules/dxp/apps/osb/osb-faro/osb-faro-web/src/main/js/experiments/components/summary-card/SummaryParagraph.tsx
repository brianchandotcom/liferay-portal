import React from 'react';
import {SummaryTitle} from './SummaryTitle';

export const SummaryParagraph = ({description, title}) => (
	<>
		<SummaryTitle className='mb-4' label={title} />

		{description && (
			<>
				<p className='font-size-sm mb-0'>{description}</p>
			</>
		)}
	</>
);
