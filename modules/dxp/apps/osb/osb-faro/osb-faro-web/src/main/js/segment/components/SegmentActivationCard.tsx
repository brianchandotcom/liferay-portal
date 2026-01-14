import Card from 'shared/components/Card';
import Label from '@clayui/label';
import List from '@clayui/list';
import React from 'react';
import {formatUTCDateFromUnix} from 'shared/util/date';
import {
	SegmentActivationFrequencyTypes,
	SegmentActivationScheduleTypes
} from 'shared/util/constants';
import {sub} from 'shared/util/lang';

interface ISegmentActivationCardProps {
	segmentActivation: SegmentActivationDetails;
}

export type SegmentActivationDetails = {
	frequencyType: SegmentActivationFrequencyTypes;
	scheduleEndDate?: string;
	scheduleStartDate?: string;
	scheduleType: SegmentActivationScheduleTypes;
	segmentActivationId: string;
};

const data: SegmentActivationDetails = {
	frequencyType: SegmentActivationFrequencyTypes.Batch,
	scheduleType: SegmentActivationScheduleTypes.Indefinitely,
	segmentActivationId: '1'
};

const FREQUENCY_TYPE_LABELS: Record<SegmentActivationFrequencyTypes, string> = {
	[SegmentActivationFrequencyTypes.Batch]: Liferay.Language.get('batch'),
	[SegmentActivationFrequencyTypes.RealTime]: Liferay.Language.get(
		'real-time'
	)
};

const SegmentActivationCard: React.FC<ISegmentActivationCardProps> = ({
	segmentActivation
}) => {
	// Use mocked data
	const {frequencyType, scheduleEndDate, scheduleStartDate, scheduleType} =
		segmentActivation || data;

	const labelMessage =
		scheduleType === SegmentActivationScheduleTypes.Indefinitely
			? sub(Liferay.Language.get('x-sync-will-run-indefinitely'), [
					FREQUENCY_TYPE_LABELS[frequencyType]
			  ])
			: sub(Liferay.Language.get('x-sync-will-run-from-x-to-x'), [
					FREQUENCY_TYPE_LABELS[frequencyType],
					formatUTCDateFromUnix(scheduleStartDate, 'MMM DD, yyyy'),
					formatUTCDateFromUnix(scheduleEndDate, 'MMM DD, yyyy')
			  ]);

	return (
		<Card className='card-root'>
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
								{labelMessage}
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
