import Button from '@clayui/button';
import Card from 'shared/components/Card';
import DateInput from 'shared/components/DateRangeInput';
import Form from '@clayui/form';
import Label from '@clayui/label';
import List from '@clayui/list';
import Modal, {useModal} from '@clayui/modal';
import React, {useEffect, useState} from 'react';
import {formatUTCDateFromUnix} from 'shared/util/date';
import {Option, Picker} from '@clayui/core';
import {
	SegmentActivationFrequencyTypes,
	SegmentActivationScheduleTypes,
	SegmentTypes
} from 'shared/util/constants';
import {sub} from 'shared/util/lang';
import {Text} from '@clayui/core';

export type SegmentActivationDetails = {
	frequencyType: SegmentActivationFrequencyTypes;
	scheduleEndDate?: string;
	scheduleStartDate?: string;
	scheduleType: SegmentActivationScheduleTypes;
	segmentActivationId: string;
};

type ModalState = {
	scheduleType: SegmentActivationScheduleTypes;
	frequencyType: SegmentActivationFrequencyTypes;
	endDate: string;
	startDate: string;
};

interface IActivationConfigurationModalProps {
	observer: any;
	onOpenChange: (open: boolean) => void;
	open: boolean;
	segmentActivationDetails: SegmentActivationDetails;
	showActivationTypePicker?: boolean;
}

interface ISegmentActivationCardProps {
	segmentActivation: SegmentActivationDetails;
	segmentType: SegmentTypes;
}

const data: SegmentActivationDetails = {
	frequencyType: SegmentActivationFrequencyTypes.Between,
	scheduleEndDate: '1711929600000',
	scheduleStartDate: '1704067200000',
	scheduleType: SegmentActivationScheduleTypes.Batch,
	segmentActivationId: '1'
};

const SCHEDULE_TYPE_LABELS: Record<
	SegmentActivationScheduleTypes,
	{label: string; value: string}
> = {
	[SegmentActivationScheduleTypes.Batch]: {
		label: Liferay.Language.get('batch'),
		value: SegmentActivationScheduleTypes.Batch
	},
	[SegmentActivationScheduleTypes.RealTime]: {
		label: Liferay.Language.get('real-time'),
		value: SegmentActivationScheduleTypes.RealTime
	}
};

const FREQUENCY_TYPE_LABELS: Record<
	SegmentActivationFrequencyTypes,
	{label: string; value: string}
> = {
	[SegmentActivationFrequencyTypes.Indefinitely]: {
		label: Liferay.Language.get('indefinitely').toLowerCase(),
		value: SegmentActivationFrequencyTypes.Indefinitely
	},
	[SegmentActivationFrequencyTypes.Between]: {
		label: Liferay.Language.get('between-fragment').toLowerCase(),
		value: SegmentActivationFrequencyTypes.Between
	}
};

const ActivationConfigurationModal: React.FC<IActivationConfigurationModalProps> = ({
	observer,
	onOpenChange,
	open,
	segmentActivationDetails,
	showActivationTypePicker
}) => {
	const {frequencyType, scheduleEndDate, scheduleStartDate, scheduleType} =
		segmentActivationDetails || data;

	const [formState, setFormState] = useState<ModalState>({
		endDate: scheduleEndDate,
		frequencyType,
		scheduleType,
		startDate: scheduleStartDate
	});

	useEffect(() => {
		if (segmentActivationDetails) {
			setFormState({
				endDate: segmentActivationDetails.scheduleEndDate,
				frequencyType: segmentActivationDetails.frequencyType,
				scheduleType: segmentActivationDetails.scheduleType,
				startDate: segmentActivationDetails.scheduleStartDate
			});
		}
	}, [segmentActivationDetails]);

	const handleFrequencyChange = (value: SegmentActivationFrequencyTypes) => {
		const newFormState = {
			...formState,
			frequencyType: value,
			...(value === SegmentActivationFrequencyTypes.Indefinitely && {
				endDate: null,
				startDate: null
			})
		};

		setFormState(newFormState);
	};

	const handleSave = () => {
		// save;
		onOpenChange(false);
	};

	return (
		<>
			{open && (
				<Modal center observer={observer} size='lg'>
					<Modal.Header>
						{Liferay.Language.get('configure-activation')}
					</Modal.Header>
					<Modal.Body>
						<div className='d-flex flex-column mb-4'>
							<Text weight='semi-bold'>
								{Liferay.Language.get('schedule')}
							</Text>
							<Text color='secondary'>
								{Liferay.Language.get(
									'set-the-sync-frequency-and-duration-for-this-segments-membership-across-all-sites-in-this-property'
								)}
							</Text>
						</div>

						{showActivationTypePicker && (
							<Form.Group className='mb-4'>
								<label
									htmlFor='activation-type-picker'
									id='activation-type-picker-label'
								>
									{Liferay.Language.get('activation-type')}
								</label>
								<Picker
									aria-labelledby='activation-type-picker-label'
									className='border-light font-weight-semi-bold'
									id='activation-type-picker'
									items={[
										SCHEDULE_TYPE_LABELS.BATCH,
										SCHEDULE_TYPE_LABELS.REAL_TIME
									]}
									onSelectionChange={(
										value: SegmentActivationScheduleTypes
									) =>
										setFormState({
											...formState,
											scheduleType: value as SegmentActivationScheduleTypes
										})
									}
									placeholder={
										SCHEDULE_TYPE_LABELS[
											formState.scheduleType
										].label
									}
									shrink
								>
									{item => (
										<Option key={item.value}>
											{item.label}
										</Option>
									)}
								</Picker>
							</Form.Group>
						)}

						<Form.Group>
							<label
								htmlFor='frequency-type-picker'
								id='frequency-type-picker-label'
							>
								{Liferay.Language.get('frequency')}
							</label>
							<div className='d-flex'>
								<Picker
									aria-labelledby='frequency-type-picker-label'
									className='border-light font-weight-semi-bold mr-2'
									id='frequency-type-picker'
									items={[
										FREQUENCY_TYPE_LABELS.BETWEEN,
										FREQUENCY_TYPE_LABELS.INDEFINITELY
									]}
									onSelectionChange={(
										value: SegmentActivationFrequencyTypes
									) => {
										handleFrequencyChange(value);
									}}
									placeholder={
										FREQUENCY_TYPE_LABELS[
											formState.frequencyType
										].label
									}
									shrink
								>
									{item => (
										<Option key={item.value}>
											{item.label}
										</Option>
									)}
								</Picker>
								{formState.frequencyType ===
									SegmentActivationFrequencyTypes.Between && (
									<DateInput
										className='flex-fill'
										limitEndDate={false}
										onChange={value => {
											setFormState({
												...formState,
												endDate: value.end,
												startDate: value.start
											});
										}}
										showRetentionPeriod={false}
										value={{
											end: formState.endDate,
											start: formState.startDate
										}}
									/>
								)}
							</div>
						</Form.Group>
					</Modal.Body>

					<Modal.Footer
						last={
							<Button.Group spaced>
								<Button
									displayType='secondary'
									onClick={() => onOpenChange(false)}
								>
									{Liferay.Language.get('cancel')}
								</Button>
								<Button onClick={handleSave}>
									{Liferay.Language.get('save-configuration')}
								</Button>
							</Button.Group>
						}
					/>
				</Modal>
			)}
		</>
	);
};

const SegmentActivationCard: React.FC<ISegmentActivationCardProps> = ({
	segmentActivation,
	segmentType
}) => {
	const {
		frequencyType,
		scheduleEndDate,
		scheduleStartDate,
		scheduleType
	} = segmentActivation;

	const {observer, onOpenChange, open} = useModal();

	const labelMessage =
		frequencyType === SegmentActivationFrequencyTypes.Indefinitely
			? sub(Liferay.Language.get('x-sync-will-run-indefinitely'), [
					SCHEDULE_TYPE_LABELS[scheduleType].label
			  ])
			: sub(Liferay.Language.get('x-sync-will-run-from-x-to-x'), [
					SCHEDULE_TYPE_LABELS[scheduleType].label,
					formatUTCDateFromUnix(scheduleStartDate, 'MMM DD, yyyy'),
					formatUTCDateFromUnix(scheduleEndDate, 'MMM DD, yyyy')
			  ]);

	return (
		<Card className='card-root'>
			<ActivationConfigurationModal
				observer={observer}
				onOpenChange={onOpenChange}
				open={open}
				segmentActivationDetails={segmentActivation}
				showActivationTypePicker={segmentType === SegmentTypes.RealTime}
			/>
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
									'this-syncs-individual-profiles-to-liferay-dxp-to-deliver-personalization-via-pages-collections-a-b-tests-and-recommendations'
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
									onClick={() => onOpenChange(true)}
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
