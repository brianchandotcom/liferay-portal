import Body from './Body';
import React from 'react';
import Subtitle from '../summary-base-card/Subtitle';
import SummaryBaseCard from '../summary-base-card';
import Title from '../summary-base-card/Title';
import {Setup, Summary} from '../summary-base-card/types';

interface SummaryCardDraftIProps extends React.HTMLAttributes<HTMLElement> {
	setup: Setup;
	summary: Summary;
}

const SummaryCardDraft: React.FC<SummaryCardDraftIProps> = ({
	setup,
	summary
}) => (
	<>
		<SummaryBaseCard.Body>
			<Body {...setup} />
		</SummaryBaseCard.Body>
		{summary.description && (
			<SummaryBaseCard.Footer>
				<Title className='mb-4' label={summary.title} />

				{summary.subtitle && <Subtitle label={summary.subtitle} />}

				<p className='mb-4'>{summary.description}</p>
			</SummaryBaseCard.Footer>
		)}
	</>
);

export default SummaryCardDraft;
