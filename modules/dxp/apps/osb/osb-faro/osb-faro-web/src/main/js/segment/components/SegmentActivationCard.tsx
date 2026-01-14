import Card from 'shared/components/Card';
import Label from '@clayui/label';
import List from '@clayui/list';
import React from 'react';
import {Segment} from 'shared/util/records';

interface ISegmentActivationCardProps {
	segment?: Segment;
}

export type SegmentActivationDetails = {
	scheduleEndDate: string;
	scheduleStartDate: string;
	scheduleType: 'BETWEEN' | 'INDEFINITELY';
	frequencyType: 'BATCH' | 'REAL_TIME';
	segmentActivationId: string;
};

const data: SegmentActivationDetails = {
	frequencyType: 'BATCH',
	scheduleEndDate: '',
	scheduleStartDate: '',
	scheduleType: 'INDEFINITELY',
	segmentActivationId: '1'
};

const FREQUENCY_TYPE_LABELS: Record<'BATCH' | 'REAL_TIME', string> = {
	BATCH: Liferay.Language.get('batch'),
	REAL_TIME: Liferay.Language.get('real-time')
};

const SCHEDULE_TYPE_LABELS: Record<'BETWEEN' | 'INDEFINITELY', string> = {
	BETWEEN: Liferay.Language.get('between'),
	INDEFINITELY: Liferay.Language.get('indefinitely')
};

const SegmentActivationCard: React.FC<ISegmentActivationCardProps> = ({
	segment
}) => {
	// Use mocked data
	const {frequencyType, scheduleType} = segment.activationStatus || data;

	return (
		<Card className='segment-membership-root'>
			<Card.Header className='align-items-center d-flex justify-content-between'>
				<Card.Title>{Liferay.Language.get('activations')}</Card.Title>
			</Card.Header>
			<Card.Body>
				<List showQuickActionsOnHover>
					<List.Item className='px-2' flex>
						<List.ItemField expand>
							<List.ItemTitle className='mb-2'>
								{Liferay.Language.get('liferay-dxp')}
							</List.ItemTitle>
							<List.ItemText className='mb-2'>
								{Liferay.Language.get(
									'syncs-individual-profiles-to-liferay-dxp-to-deliver-personalization-via-pages-collections-a-b-tests-and-recommendations'
								)}
							</List.ItemText>
							<Label
								className='align-self-start'
								displayType='info'
							>
								{`${FREQUENCY_TYPE_LABELS[frequencyType]} sync will run ${SCHEDULE_TYPE_LABELS[scheduleType]}`}
							</Label>
						</List.ItemField>
						<List.ItemField>
							<List.QuickActionMenu>
								<List.QuickActionMenu.Item
									aria-label={Liferay.Language.get('edit')}
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
