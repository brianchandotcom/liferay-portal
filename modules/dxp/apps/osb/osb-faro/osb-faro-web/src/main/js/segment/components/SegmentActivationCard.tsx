import Card from 'shared/components/Card';
import Label from '@clayui/label';
import List from '@clayui/list';
import React from 'react';
import {ReportContainer} from 'shared/components/download-report/DownloadPDFReport';
import {Segment} from 'shared/util/records';

interface ISegmentActivationCardProps {
	segment?: Segment;
}

type mock = {
	title: string;
	description: string;
	scheduleType: 'BETWEEN' | 'INDEFINITELY';
	scheduleEndDate: string;
	scheduleStartDate: string;
	frequencyType: 'BATCH' | 'REAL_TIME';
};

const data: mock = {
	description:
		'Syncs individual profiles to Liferay DXP to deliver personalization via pages, collections, A/B tests, and recommendations.',
	frequencyType: 'BATCH',
	scheduleEndDate: '',
	scheduleStartDate: '',
	scheduleType: 'INDEFINITELY',
	title: 'Liferay DXP'
};

const SegmentActivationCard: React.FC<ISegmentActivationCardProps> = ({
	segment = null
}) => {
	const {description, frequencyType, scheduleType, title} = data;

	return (
		<Card
			className='segment-membership-root'
			reportContainer={ReportContainer.SegmentMembershipCard}
		>
			<Card.Header className='align-items-center d-flex justify-content-between'>
				<Card.Title>{'Activations'}</Card.Title>
			</Card.Header>
			<Card.Body>
				<List showQuickActionsOnHover>
					<List.Item className='px-2' flex>
						<List.ItemField className='d-flex' expand>
							<List.ItemTitle className='mb-2'>
								{title}
							</List.ItemTitle>
							<List.ItemText className='mb-2'>
								{description}
							</List.ItemText>
							<Label
								className='align-self-start'
								displayType='info'
							>
								{`${frequencyType} sync will run ${scheduleType}`}
							</Label>
						</List.ItemField>
						<List.ItemField>
							<List.QuickActionMenu>
								<List.QuickActionMenu.Item
									aria-label={Liferay.Language.get('edit')}
									onClick={() => alert('Clicked the pencil!')}
									symbol='pencil'
									title={Liferay.Language.get('edit')}
								/>
							</List.QuickActionMenu>
						</List.ItemField>
					</List.Item>
				</List>
			</Card.Body>
		</Card>
	);
};

export {SegmentActivationCard};
