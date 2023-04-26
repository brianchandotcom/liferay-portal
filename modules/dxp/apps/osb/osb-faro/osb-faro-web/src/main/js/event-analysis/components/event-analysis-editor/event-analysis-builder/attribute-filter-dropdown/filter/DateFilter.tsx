import ClayButton from '@clayui/button';
import Form, {
	validateDateRangeRequired,
	validateRequired
} from 'shared/components/form';
import React from 'react';
import {DataTypes, IFilterProps, Operators} from 'event-analysis/utils/types';
import {
	DATE_OPERATOR_LONGHAND_LABELS_MAP,
	DATE_OPTIONS
} from 'event-analysis/utils/utils';

const DateFilter: React.FC<IFilterProps> = ({
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
				values: [start, end]
			} = filter;

			return {
				date: operator === Operators.Between ? '' : start,
				dateRange:
					operator === Operators.Between
						? {end, start}
						: {end: '', start: ''},
				operator
			};
		}

		return {
			date: '',
			dateRange: {end: '', start: ''},
			operator: Operators.EQ
		};
	};

	return (
		<Form
			enableReinitialize
			initialValues={getInitialValues()}
			onSubmit={({date, dateRange, operator}) => {
				let dateValue: string[] = [date as string];

				if (operator === Operators.Between) {
					const {end, start} = dateRange;

					dateValue = [start as string, end as string];
				}

				onSubmit({
					attributeId,
					attributeType: attributeOwnerType,
					dataType: DataTypes.Date,
					description,
					displayName,
					operator,
					values: dateValue
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
									{DATE_OPTIONS.map(value => (
										<Form.Select.Item
											key={value}
											value={value}
										>
											{
												DATE_OPERATOR_LONGHAND_LABELS_MAP[
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
								{operator !== Operators.Between && (
									<Form.DateInput
										name='date'
										overlayAlignment='rightCenter'
										usePortal={false}
										validate={validateRequired}
									/>
								)}

								{operator === Operators.Between && (
									<Form.DateRangeInput
										name='dateRange'
										overlayAlignment='rightCenter'
										usePortal={false}
										validate={validateDateRangeRequired}
									/>
								)}
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

export default DateFilter;
