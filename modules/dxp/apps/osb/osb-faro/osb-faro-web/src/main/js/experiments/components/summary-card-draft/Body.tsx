import ClayMultiStep from '../clay-multi-step';
import React from 'react';
import StepBody from './StepBody';
import Subtitle from '../summary-base-card/Subtitle';
import Title from '../summary-base-card/Title';
import {Step} from '../summary-base-card/types';

interface SummaryCardDraftBodyIProps extends React.HTMLAttributes<HTMLElement> {
	current?: number;
	steps: Array<Step>;
	subtitle?: string;
	title: string;
}

const SummaryCardDraftBody: React.FC<SummaryCardDraftBodyIProps> = ({
	current = 0,
	steps,
	subtitle,
	title
}) => (
	<div className='w-100 mt-4'>
		<Title className='mb-4' label={title} />

		{subtitle && <Subtitle label={subtitle} />}

		<ClayMultiStep current={current} showIndicatorLabel={false}>
			{steps &&
				steps.map((step: any, index) => (
					<ClayMultiStep.Item
						Body={props => <StepBody {...props} step={step} />}
						key={index}
					/>
				))}
		</ClayMultiStep>
	</div>
);

export default SummaryCardDraftBody;
