import Item from '../components/Item';
import React from 'react';
import Row from '../components/Row';
import SummaryCardDraft from 'experiments/components/summary-card-draft';
import SummaryCardRun from 'experiments/components/summary-card-run';
import SummarySection from 'experiments/components/summary-section';

const DraftDescription = props => (
	<span {...props}>
		<div>
			<span className='text-secondary mr-1'>
				{'Step Draft Description'}
			</span>
		</div>
	</span>
);

const MOCK_SUMMARY_CARD_RUN = {
	alert: {
		description: 'We recommend that you publish the winning variant.',
		symbol: 'check-circle',
		title: '[Variant Name] has outperformed control by at least 6%'
	},
	header: {
		cardModals: [
			{
				title: 'action 01'
			},
			{
				title: 'action 02'
			}
		],
		Description: () => 'Header Description',
		modals: [
			{
				title: 'action 01'
			},
			{
				title: 'action 02'
			}
		],
		title: 'Header Title'
	},
	sections: [
		{
			Body: () => (
				<SummarySection title='Test Completion'>
					<SummarySection.Heading value='100%' />
					<SummarySection.ProgressBar value={100} />
				</SummarySection>
			)
		},
		{
			Body: () => (
				<SummarySection title='Days running'>
					<SummarySection.Heading value='7' />
					<SummarySection.Description value='About 13 days left' />
				</SummarySection>
			)
		},
		{
			Body: () => (
				<SummarySection title='Total tests sessions'>
					<SummarySection.Heading value='120.5k' />
				</SummarySection>
			)
		},
		{
			Body: () => (
				<SummarySection title='Test Metric'>
					<SummarySection.MetricType value='Click Event' />
					<SummarySection.Variant lift='3.5%' status='up' />
				</SummarySection>
			)
		}
	],
	summary: {
		description:
			'Lorem ipsum dolor sit amet consectetur adipisicing elit. Ex tenetur unde, dolorem consequuntur similique Possimus atque iure consequuntur a. Vel eos quidem consequatur autem, obcaecati quisquam perferendis. Laboriosam tempore id quis asperiores, maxime expedita dolorum soluta.',
		subtitle: 'Description',
		title: 'Summary'
	}
};

export default () => (
	<>
		<Row>
			<Item>
				<h3>{'Summary Card - Status Draft'}</h3>

				<SummaryCardDraft
					header={{
						cardModals: [
							{
								title: 'action 01'
							},
							{
								title: 'action 02'
							}
						],
						Description: () => 'Header Description',
						modals: [
							{
								title: 'action 01'
							},
							{
								title: 'action 02'
							}
						],
						title: 'Header Title'
					}}
					setup={{
						current: 1,
						steps: [
							{
								buttonProps: {
									label: 'button label',
									symbol: 'dxp-logo'
								},
								Description: DraftDescription,
								title: 'Step Title'
							},
							{
								buttonProps: {
									label: 'button label',
									symbol: 'dxp-logo'
								},
								Description: DraftDescription,
								title: 'Step Title'
							},
							{
								buttonProps: {
									label: 'button label',
									symbol: 'dxp-logo'
								},
								Description: DraftDescription,
								title: 'Step Title'
							}
						]
					}}
					status='draft'
					summary={{
						description:
							'Lorem ipsum dolor sit amet consectetur adipisicing elit. Ex tenetur unde, dolorem consequuntur similique Possimus atque iure consequuntur a. Vel eos quidem consequatur autem, obcaecati quisquam perferendis. Laboriosam tempore id quis asperiores, maxime expedita dolorum soluta.',
						subtitle: 'Description',
						title: 'Summary'
					}}
				/>
			</Item>
		</Row>
		<Row>
			<Item>
				<h3>{'Summary Card - Status running'}</h3>

				<SummaryCardRun {...MOCK_SUMMARY_CARD_RUN} status='running' />
			</Item>
		</Row>
		<Row>
			<Item>
				<h3>{'Summary Card - Status terminated'}</h3>

				<SummaryCardRun
					{...MOCK_SUMMARY_CARD_RUN}
					status='terminated'
				/>
			</Item>
		</Row>
		<Row>
			<Item>
				<h3>{'Summary Card - Status finished (WINNER)'}</h3>

				<SummaryCardRun
					{...MOCK_SUMMARY_CARD_RUN}
					status='finished_winner'
				/>
			</Item>
		</Row>
		<Row>
			<Item>
				<h3>{'Summary Card - Status finished (NO WINNNER)'}</h3>

				<SummaryCardRun
					{...MOCK_SUMMARY_CARD_RUN}
					status='finished_no_winner'
				/>
			</Item>
		</Row>
		<Row>
			<Item>
				<h3>{'Summary Card - Status completed'}</h3>

				<SummaryCardRun {...MOCK_SUMMARY_CARD_RUN} status='completed' />
			</Item>
		</Row>
	</>
);
