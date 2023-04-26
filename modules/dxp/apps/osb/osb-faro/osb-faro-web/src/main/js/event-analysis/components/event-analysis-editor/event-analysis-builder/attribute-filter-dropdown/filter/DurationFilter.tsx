import ClayButton from '@clayui/button';
import Form, {validateRequired} from 'shared/components/form';
import React from 'react';
import {DataTypes, IFilterProps, Operators} from 'event-analysis/utils/types';
import {
	DURATION_OPERATOR_LONGHAND_LABELS_MAP,
	DURATION_OPTIONS
} from 'event-analysis/utils/utils';
import {formatTime, getMillisecondsFromTime} from 'shared/util/time';

const DEFAULT_DURATION_BIN = 60000;
const DURATION_MASK = [/\d/, /\d/, ':', /[0-6]/, /\d/, ':', /[0-6]/, /\d/];

const DurationFilter: React.FC<IFilterProps> = ({
	attributeId,
	attributeOwnerType,
	description,
	displayName,
	filter,
	onSubmit
}) => {
	const getInitialValues = () => {
		if (filter) {
			const {
				operator,
				values: [value]
			} = filter;

			return {
				operator,
				value: formatTime(Number(value))
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
			onSubmit={({operator, value}) => {
				onSubmit({
					attributeId,
					attributeType: attributeOwnerType,
					dataType: DataTypes.Duration,
					description,
					displayName,
					operator,
					values: [
						String(
							getMillisecondsFromTime(value.replace(/_/g, '0'))
						)
					]
				});
			}}
		>
			{({handleSubmit, isValid}) => (
				<Form.Form onSubmit={handleSubmit}>
					<div className='options-body'>
						<Form.Group autoFit>
							<Form.GroupItem>
								<Form.Select
									label={Liferay.Language.get('condition')}
									name='operator'
								>
									{DURATION_OPTIONS.map(value => (
										<Form.Select.Item
											key={value}
											value={value}
										>
											{
												DURATION_OPERATOR_LONGHAND_LABELS_MAP[
													value
												]
											}
										</Form.Select.Item>
									))}
								</Form.Select>
							</Form.GroupItem>
						</Form.Group>

						<Form.Group autoFit>
							<Form.GroupItem>
								<Form.Input
									autoComplete='off'
									mask={DURATION_MASK}
									name='value'
									placeholder='HH:MM:SS'
									required
									type='string'
									validate={validateRequired}
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

export default DurationFilter;
