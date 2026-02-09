import * as API from 'shared/api/individual-segment';
import Button from '@clayui/button';
import Card from 'shared/components/Card';
import DateInput from 'shared/components/DateRangeInput';
import Form from '@clayui/form';
import Label from '@clayui/label';
import List from '@clayui/list';
import Modal, {useModal} from '@clayui/modal';
import React, {useMemo, useState} from 'react';
import {addAlert} from 'shared/actions/alerts';
import {Alert} from 'shared/types';
import {connect, ConnectedProps} from 'react-redux';
import {formatUTCDateFromUnix} from 'shared/util/date';
import {Option, Picker} from '@clayui/core';
import {ReportContainer} from 'shared/components/download-report/DownloadPDFReport';
import {
	SegmentActivationFrequencyTypes,
	SegmentActivationScheduleTypes,
	SegmentTypes
} from 'shared/util/constants';
import {sub} from 'shared/util/lang';
import {Text} from '@clayui/core';
import {useParams} from 'react-router-dom';

type PropsFromRedux = ConnectedProps<typeof connector>;

const connector = connect(null, {
	addAlert
});

export type SegmentActivationDetails = {
	frequencyType: SegmentActivationFrequencyTypes;
	scheduleEndDate?: string;
	scheduleStartDate?: string;
	scheduleType: SegmentActivationScheduleTypes;
};

type IActivationFormValues = {
	scheduleEndDate?: string;
	frequencyType: SegmentActivationFrequencyTypes;
	scheduleType: SegmentActivationScheduleTypes;
	scheduleStartDate?: string;
};

interface IActivationConfigurationModalProps {
	initialValues: IActivationFormValues;
	onSave: (values: IActivationFormValues) => Promise<void>;
	onOpenChange: (open: boolean) => void;
	open: boolean;
	showActivationTypePicker?: boolean;
	observer?: any;
}

interface ISegmentActivationCardProps {
	segmentActivation: any;
	segmentType: SegmentTypes;
}

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

export const ActivationConfigurationModal: React.FC<
	IActivationConfigurationModalProps & PropsFromRedux
> = ({
	addAlert,
	initialValues,
	observer,
	onOpenChange,
	onSave,
	open,
	showActivationTypePicker
}) => {
	const [formState, setFormState] = useState<IActivationFormValues>({
		...initialValues,
		scheduleEndDate:
			initialValues.scheduleEndDate &&
			formatUTCDateFromUnix(initialValues.scheduleEndDate, 'yyyy-MM-DD'),
		scheduleStartDate:
			initialValues.scheduleStartDate &&
			formatUTCDateFromUnix(initialValues.scheduleStartDate, 'yyyy-MM-DD')
	});
	const [isSaving, setIsSaving] = useState(false);

	const isInvalid = useMemo(() => {
		if (
			formState.frequencyType === SegmentActivationFrequencyTypes.Between
		) {
			return !formState.scheduleStartDate || !formState.scheduleEndDate;
		}
		return false;
	}, [formState]);

	const handleFrequencyChange = (value: SegmentActivationFrequencyTypes) => {
		setFormState(prev => ({
			...prev,
			frequencyType: value,
			...(value === SegmentActivationFrequencyTypes.Indefinitely && {
				scheduleEndDate: null,
				scheduleStartDate: null
			})
		}));
	};

	const handleInternalSave = async () => {
		setIsSaving(true);

		try {
			await onSave(formState);
		} catch (error) {
			addAlert({
				alertType: Alert.Types.Error,
				message: Liferay.Language.get(
					'there-was-an-error-processing-your-request.-try-again.-if-the-problem-persists,-please-contact-support'
				)
			});
		} finally {
			setIsSaving(false);
			onOpenChange(false);
		}
	};

	if (!open) return null;

	return (
		<>
			{open && (
				<Modal
					center
					data-testId='segment-activation-modal'
					observer={observer}
					size='lg'
				>
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
									htmlFor='schedule-type-picker'
									id='schedule-type-picker-label'
								>
									{Liferay.Language.get('activation-type')}
								</label>
								<Picker
									aria-labelledby='schedule-type-picker-label'
									className='border-light font-weight-semi-bold'
									id='schedule-type-picker'
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
										maxRange={365}
										onChange={value => {
											setFormState({
												...formState,
												scheduleEndDate: value.end,
												scheduleStartDate: value.start
											});
										}}
										value={{
											end: formState.scheduleEndDate,
											start: formState.scheduleStartDate
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
								<Button
									disabled={isSaving || isInvalid}
									onClick={handleInternalSave}
								>
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

const ConnectedActivationConfigurationModal = connector(
	ActivationConfigurationModal
);

const SegmentActivationCard: React.FC<ISegmentActivationCardProps> = ({
	segmentActivation,
	segmentType
}) => {
	const {
		frequencyType,
		scheduleEndDate,
		scheduleStartDate,
		scheduleType
	} = segmentActivation.toJS();

	const {observer, onOpenChange, open} = useModal();

	const {groupId, id: segmentId} = useParams();

	const handleSave = async (updatedValues: IActivationFormValues) =>
		API.updateSegmentActivation({
			groupId,
			segmentActivation: {
				...updatedValues
			},
			segmentId
		}).then(() => {
			addAlert({
				alertType: Alert.Types.Success,
				message: Liferay.Language.get('changes-to-segment-saved')
			});
		});

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
		<Card
			className='card-root'
			reportContainer={ReportContainer.SegmentActivationCard}
		>
			{open && (
				<ConnectedActivationConfigurationModal
					initialValues={{
						frequencyType,
						scheduleEndDate,
						scheduleStartDate,
						scheduleType
					}}
					observer={observer}
					onOpenChange={onOpenChange}
					onSave={handleSave}
					open={open}
					showActivationTypePicker={
						segmentType === SegmentTypes.RealTime
					}
				/>
			)}

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
									data-testid='edit-activation-button'
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
