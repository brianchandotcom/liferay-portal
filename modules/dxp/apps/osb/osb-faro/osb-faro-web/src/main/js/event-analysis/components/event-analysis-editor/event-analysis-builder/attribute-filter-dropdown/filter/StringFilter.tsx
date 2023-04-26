import ClayButton from '@clayui/button';
import EventAttributeValuesQuery from 'event-analysis/queries/EventAttributeValuesQuery';
import Form, {validateRequired} from 'shared/components/form';
import React from 'react';
import {DataTypes, IFilterProps, Operators} from 'event-analysis/utils/types';
import {
	STRING_OPERATOR_LABELS_MAP,
	STRING_OPTIONS
} from 'event-analysis/utils/utils';
import {useParams} from 'react-router-dom';
import {useStatefulPagination} from 'shared/hooks';

type EventAttributeValuesData = {
	eventAttributeValues: EventAttributeValues;
};

type EventAttributeValues = {
	eventAttributeValues: string[];
	total: number;
};

const StringFilter: React.FC<IFilterProps> = ({
	attributeId,
	attributeOwnerType,
	description,
	displayName,
	eventId,
	filter,
	onSubmit
}) => {
	const getInitialValues = () => {
		if (filter) {
			const {operator, values} = filter;

			return {operator, value: values[0]};
		}

		return {operator: Operators.Contains, value: ''};
	};

	const {channelId} = useParams();

	const {delta, page} = useStatefulPagination();

	return (
		<Form
			enableReinitialize
			initialValues={getInitialValues()}
			onSubmit={({operator, value}) => {
				onSubmit({
					attributeId,
					attributeType: attributeOwnerType,
					dataType: DataTypes.String,
					description,
					displayName,
					operator,
					values: [value]
				});
			}}
		>
			{({
				handleSubmit,
				isValid,
				setValues,
				values: {value, ...otherFields}
			}) => (
				<Form.Form onSubmit={handleSubmit}>
					<div className='options-body'>
						<Form.Group autoFit>
							<Form.GroupItem>
								<Form.Select
									label={Liferay.Language.get('condition')}
									name='operator'
								>
									{STRING_OPTIONS.map(value => (
										<Form.Select.Item
											key={value}
											value={value}
										>
											{STRING_OPERATOR_LABELS_MAP[value]}
										</Form.Select.Item>
									))}
								</Form.Select>
							</Form.GroupItem>
						</Form.Group>

						<Form.Group autoFit>
							<Form.GroupItem>
								<Form.AutocompleteInput
									graphqlQuery={{
										mapResultsToProps: (
											data: EventAttributeValuesData
										) => {
											if (data) {
												return {
													data:
														data
															.eventAttributeValues
															.eventAttributeValues,
													total:
														data
															.eventAttributeValues
															.total
												};
											}

											return {
												data: [],
												total: 0
											};
										},
										query: EventAttributeValuesQuery,
										variables: {
											channelId,
											eventAttributeDefinitionId: attributeId,
											eventDefinitionId: eventId,
											size: delta,
											start: (page - 1) * delta
										}
									}}
									name='value'
									onChange={(value: string) =>
										setValues({value, ...otherFields})
									}
									required
									validate={validateRequired}
									value={value}
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

export default StringFilter;
