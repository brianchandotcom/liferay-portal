import Form from 'shared/components/form';
import InfoPopover from 'shared/components/InfoPopover';
import React, {useEffect} from 'react';
import {JOB_RUN_DATA_PERIODS_LIST} from '../../utils/utils';
import {JobRunFrequencies} from 'shared/util/constants';

interface IInteractionsProps {
	includePreviousPeriod: boolean;
	setFieldValue: (
		field: string,
		value: any,
		shouldValidate?: boolean
	) => void;
	runFrequency: JobRunFrequencies;
}

const Interactions: React.FC<IInteractionsProps> = ({
	includePreviousPeriod,
	runFrequency,
	setFieldValue
}) => {
	const manualRunFequency = runFrequency === JobRunFrequencies.Manual;

	useEffect(() => {
		if (manualRunFequency && includePreviousPeriod) {
			setFieldValue('includePreviousPeriod', false);
		}
	}, []);

	const interactionPeriodLabel = (
		<div>
			{Liferay.Language.get('select-interaction-period')}

			<div className='secondary-info'>
				{Liferay.Language.get(
					'the-interaction-period-requires-a-minimum-of-1000-events'
				)}
			</div>
		</div>
	);

	return (
		<div className='interactions-root'>
			<Form.Group>
				<Form.GroupItem>
					<Form.Select
						label={interactionPeriodLabel}
						name='runDataPeriod'
					>
						{JOB_RUN_DATA_PERIODS_LIST.map(({name, value}) => (
							<Form.Select.Item key={value} value={value}>
								{name}
							</Form.Select.Item>
						))}
					</Form.Select>
				</Form.GroupItem>

				<Form.GroupItem>
					<Form.Checkbox
						data-testid='include-previous-period-checkbox'
						disabled={manualRunFequency}
						displayInline
						label={Liferay.Language.get(
							'include-previous-period-in-case-of-insufficient-interactions'
						)}
						name='includePreviousPeriod'
					/>

					<InfoPopover
						className='include-previous-period-help-icon'
						content={Liferay.Language.get(
							'include-previous-interaction-data-if-there-are-less-than-1000-events-during-current-period'
						)}
					/>
				</Form.GroupItem>
			</Form.Group>
		</div>
	);
};

export default Interactions;
