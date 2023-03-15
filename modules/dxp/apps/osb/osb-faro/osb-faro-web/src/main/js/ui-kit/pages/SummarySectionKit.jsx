import Item from '../components/Item';
import React from 'react';
import Row from '../components/Row';
import SummarySection from 'experiments/components/summary-section';

export default () => (
	<>
		<Row>
			<Item>
				<SummarySection title='Summary Section with Progress'>
					<SummarySection.ProgressBar className='mb-1' value={100} />
					<SummarySection.ProgressBar className='mb-1' value={50} />
					<SummarySection.ProgressBar className='mb-1' value={25} />
					<SummarySection.ProgressBar className='mb-1' value={0} />
				</SummarySection>
			</Item>
		</Row>

		<hr />

		<Row>
			<Item>
				<SummarySection title='Summary Section Title'>
					<SummarySection.Heading value='Heading' />
					<SummarySection.Description value='Description' />
				</SummarySection>
			</Item>
		</Row>

		<hr />

		<Row>
			<Item>
				<SummarySection title='Summary Section (Metric Type)'>
					<SummarySection.MetricType value='Click Event' />
					<SummarySection.Variant lift='3.5%' status='up' />
				</SummarySection>
			</Item>
		</Row>

		<Row>
			<Item>
				<SummarySection title='Summary Section (Metric Type)'>
					<SummarySection.MetricType value='Click Event' />
					<SummarySection.Variant lift='3.5%' status='down' />
				</SummarySection>
			</Item>
		</Row>
	</>
);
