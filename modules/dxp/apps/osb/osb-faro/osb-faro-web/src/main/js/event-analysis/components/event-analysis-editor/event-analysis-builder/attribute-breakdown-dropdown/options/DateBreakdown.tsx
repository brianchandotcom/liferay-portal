import ClayButton from '@clayui/button';
import Form, {validateRequired} from 'shared/components/form';
import React from 'react';
import {
	createDateBreakdown,
	DATE_GROUPING_LABELS_MAP,
	DATE_GROUPING_OPTIONS
} from 'event-analysis/utils/utils';
import {DateGroupings, IBreakdownProps} from 'event-analysis/utils/types';

const DateBreakdown: React.FC<IBreakdownProps> = ({
	attributeId,
	attributeOwnerType,
	breakdown,
	description,
	displayName,
	onSubmit
}) => {
	const getInitialValues = () => {
		if (breakdown) {
			const {dateGrouping} = breakdown;

			return {
				dateGrouping
			};
		}

		return {
			dateGrouping: DateGroupings.Month
		};
	};

	return (
		<Form
			enableReinitialize
			initialValues={getInitialValues()}
			isInitialValid
			onSubmit={({dateGrouping}) => {
				onSubmit(
					createDateBreakdown({
						attributeId,
						attributeType: attributeOwnerType,
						dateGrouping,
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
								<Form.Select
									label={Liferay.Language.get(
										'group-dates-by'
									)}
									name='dateGrouping'
									type='string'
									validate={validateRequired}
								>
									{DATE_GROUPING_OPTIONS.map(value => (
										<Form.Select.Item
											key={value}
											value={value}
										>
											{DATE_GROUPING_LABELS_MAP[value]}
										</Form.Select.Item>
									))}
								</Form.Select>
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

export default DateBreakdown;
