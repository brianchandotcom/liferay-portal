import ClayButton from '@clayui/button';
import Form, {
	validateMinDuration,
	validateRequired
} from 'shared/components/form';
import React from 'react';
import {createDurationBreakdown} from 'event-analysis/utils/utils';
import {formatTime, getMillisecondsFromTime} from 'shared/util/time';
import {IBreakdownProps, Operators} from 'event-analysis/utils/types';
import {sequence} from 'shared/util/promise';

const DEFAULT_DURATION_BIN = 60000;
const DURATION_MASK = [/\d/, /\d/, ':', /[0-6]/, /\d/, ':', /[0-6]/, /\d/];

const DurationBreakdown: React.FC<IBreakdownProps> = ({
	attributeId,
	attributeOwnerType,
	breakdown,
	description,
	displayName,
	onSubmit
}) => {
	const getInitialValues = () => {
		if (breakdown) {
			const {binSize} = breakdown;

			return {
				binSize: formatTime(binSize)
			};
		}

		return {
			binSize: formatTime(DEFAULT_DURATION_BIN),
			operator: Operators.GT,
			value: ''
		};
	};

	return (
		<Form
			enableReinitialize
			initialValues={getInitialValues()}
			isInitialValid
			onSubmit={({binSize}) => {
				onSubmit(
					createDurationBreakdown({
						attributeId,
						attributeType: attributeOwnerType,
						binSize: getMillisecondsFromTime(
							binSize.replace(/_/g, '0') as string
						),
						description,
						displayName
					})
				);
			}}
		>
			{({handleSubmit, isValid}) => (
				<Form.Form onSubmit={handleSubmit}>
					<div className='options-body'>
						<Form.Group autoFit>
							<Form.GroupItem>
								<Form.Input
									label={Liferay.Language.get(
										'group-duration-by'
									)}
									mask={DURATION_MASK}
									name='binSize'
									placeholder='HH:MM:SS'
									type='string'
									validate={sequence([
										validateRequired,
										validateMinDuration('00:00:01')
									])}
								/>
							</Form.GroupItem>
						</Form.Group>
					</div>

					<div className='options-footer'>
						<ClayButton
							block
							className='button-root'
							disabled={!isValid}
							displayType='primary'
							type='submit'
						>
							{Liferay.Language.get('done')}
						</ClayButton>
					</div>
				</Form.Form>
			)}
		</Form>
	);
};

export default DurationBreakdown;
