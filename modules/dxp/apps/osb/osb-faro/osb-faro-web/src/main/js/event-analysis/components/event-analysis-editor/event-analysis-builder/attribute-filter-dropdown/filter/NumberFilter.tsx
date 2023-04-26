import ClayButton from '@clayui/button';
import Form, {
	validateIsInteger,
	validateRequired
} from 'shared/components/form';
import React from 'react';
import {DataTypes, IFilterProps, Operators} from 'event-analysis/utils/types';
import {
	NUMBER_OPERATOR_LONGHAND_LABELS_MAP,
	NUMBER_OPTIONS
} from 'event-analysis/utils/utils';
import {sequence} from 'shared/util/promise';

const NumberFilter: React.FC<IFilterProps> = ({
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
				values: [startValue = '', endValue = '']
			} = filter;

			return {endValue, operator, startValue};
		}

		return {
			endValue: '',
			operator: Operators.GT,
			startValue: ''
		};
	};

	return (
		<Form
			enableReinitialize
			initialValues={getInitialValues()}
			onSubmit={({endValue, operator, startValue}) => {
				let values = [startValue];

				if (operator === Operators.Between) {
					values = [...values, endValue];
				}

				onSubmit({
					attributeId,
					attributeType: attributeOwnerType,
					dataType: DataTypes.Number,
					description,
					displayName,
					operator,
					values
				});
			}}
		>
			{({handleSubmit, isValid, values: {operator}}) => (
				<Form.Form onSubmit={handleSubmit}>
					<div className='options-body'>
						<Form.Group autoFit>
							<Form.GroupItem>
								<Form.Select
									label={Liferay.Language.get('condition')}
									name='operator'
								>
									{NUMBER_OPTIONS.map(value => (
										<Form.Select.Item
											key={value}
											value={value}
										>
											{
												NUMBER_OPERATOR_LONGHAND_LABELS_MAP[
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
									name='startValue'
									required
									type='number'
									validate={validateRequired}
								/>
							</Form.GroupItem>

							{operator === Operators.Between && (
								<Form.GroupItem>
									<Form.Input
										name='endValue'
										required
										type='number'
										validate={sequence([
											validateRequired,
											validateIsInteger
										])}
									/>
								</Form.GroupItem>
							)}
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

export default NumberFilter;
