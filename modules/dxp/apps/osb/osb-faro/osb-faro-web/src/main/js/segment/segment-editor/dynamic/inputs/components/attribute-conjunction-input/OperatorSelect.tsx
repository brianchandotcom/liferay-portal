import Form from 'shared/components/form';
import React from 'react';
import {ClaySelectWithOption} from '@clayui/select';
import {Criterion} from '../../../utils/types';
import {DataTypes} from 'event-analysis/utils/types';
import {
	FunctionalOperators,
	RelationalOperators
} from '../../../utils/constants';
import {getDefaultAttributeValue, getOperatorOptions} from './utils';

interface IOperatorSelectProps {
	dataType: DataTypes;
	onChange: (params: {criterion: Criterion}) => void;
	operatorName: Criterion['operatorName'];
}

const OperatorSelect: React.FC<IOperatorSelectProps> = ({
	dataType,
	onChange,
	operatorName
}) => {
	if (dataType === DataTypes.Boolean) {
		return (
			<Form.GroupItem className='conjunction' label shrink>
				{Liferay.Language.get('is')}
			</Form.GroupItem>
		);
	}

	return (
		<Form.GroupItem shrink>
			<ClaySelectWithOption
				className='operator-input'
				onChange={event => {
					const {value: newOperatorName} = event.target;

					let criterion: Criterion = {
						operatorName:
							newOperatorName as Criterion['operatorName']
					};

					if (
						(newOperatorName === FunctionalOperators.Between &&
							operatorName !== FunctionalOperators.Between) ||
						(newOperatorName !== FunctionalOperators.Between &&
							operatorName === FunctionalOperators.Between)
					) {
						criterion = {
							...criterion,
							value: getDefaultAttributeValue(
								dataType,
								newOperatorName as
									| FunctionalOperators
									| RelationalOperators
							)
						};
					}

					onChange({criterion});
				}}
				options={getOperatorOptions(dataType)}
				value={operatorName}
			/>
		</Form.GroupItem>
	);
};

export default OperatorSelect;
