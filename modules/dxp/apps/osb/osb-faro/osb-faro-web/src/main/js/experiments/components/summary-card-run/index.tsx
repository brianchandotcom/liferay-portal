import Alert from './Alert';
import React from 'react';
import Subtitle from '../summary-base-card/Subtitle';
import SummaryBaseCard from '../summary-base-card';
import Title from '../summary-base-card/Title';
import {Alert as AlertType, Summary} from '../summary-base-card/types';
import {CLASSNAME} from '../summary-base-card/constants';

type Section = {
	Body?: React.FC<React.HTMLAttributes<HTMLElement>>;
};

const SummaryCardRunParagraph: React.FC<React.HTMLAttributes<HTMLElement>> = ({
	description,
	subtitle,
	title
}: Summary) => (
	<>
		<Title className='mb-4' label={title} />

		{description && (
			<>
				{subtitle && <Subtitle label={subtitle} />}

				<p className='font-size-sm mb-0'>{description}</p>
			</>
		)}
	</>
);

interface SummaryCardRunIProps extends React.HTMLAttributes<HTMLElement> {
	alert?: AlertType;
	sections?: Array<Section>;
	summary: Summary;
}

const SummaryCardRun: React.FC<SummaryCardRunIProps> = ({
	alert,
	sections,
	summary
}) => (
	<>
		{alert && (
			<Alert symbol={alert.symbol}>
				<Title className='font-weight-bold mb-1' label={alert.title} />
				<strong>{alert.description}</strong>
			</Alert>
		)}

		<SummaryBaseCard.Body>
			<div className='w-100 mt-4'>
				<SummaryCardRunParagraph {...summary} />

				<div className={`${CLASSNAME}-sections`}>
					{sections &&
						!!sections.length &&
						sections.map(({Body}, index) => <Body key={index} />)}
				</div>
			</div>
		</SummaryBaseCard.Body>
	</>
);

export default SummaryCardRun;
