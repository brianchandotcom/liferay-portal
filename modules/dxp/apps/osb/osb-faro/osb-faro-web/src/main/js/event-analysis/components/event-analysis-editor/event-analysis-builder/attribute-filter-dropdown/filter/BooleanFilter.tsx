import Button from 'shared/components/Button';
import Form from 'shared/components/form';
import React from 'react';
import {BOOLEAN_LABELS_MAP, BOOLEAN_OPTIONS} from 'event-analysis/utils/utils';
import {DataTypes, IFilterProps, Operators} from 'event-analysis/utils/types';

const BooleanFilter: React.FC<IFilterProps> = ({
	attributeId,
	attributeOwnerType,
	description,
	displayName,
	filter,
	onSubmit
}) => {
	const getInitialValues = () => {
		if (filter) {
			const {operator, values} = filter;

			return {operator, value: String(values[0])};
		}

		return {operator: Operators.EQ, value: 'true'};
	};

	return (
		<Form
			enableReinitialize
			initialValues={getInitialValues()}
			onSubmit={({operator, value}) => {
				onSubmit({
					attributeId,
					attributeType: attributeOwnerType,
					dataType: DataTypes.Boolean,
					description,
					displayName,
					operator,
					values: [value]
				});
			}}
		>
			{({handleSubmit}) => (
				<Form.Form onSubmit={handleSubmit}>
					<div className='options-body'>
						<Form.Group autoFit>
							<Form.GroupItem>
								<Form.Select
									label={Liferay.Language.get('condition')}
									name='value'
								>
									{BOOLEAN_OPTIONS.map(value => (
										<Form.Select.Item
											key={value}
											value={value}
										>
											{BOOLEAN_LABELS_MAP[value]}
										</Form.Select.Item>
									))}
								</Form.Select>
							</Form.GroupItem>
						</Form.Group>
					</div>

					<div className='options-footer'>
						<Button block display='primary' type='submit'>
							{Liferay.Language.get('done')}
						</Button>
					</div>
				</Form.Form>
			)}
		</Form>
	);
};

export default BooleanFilter;
