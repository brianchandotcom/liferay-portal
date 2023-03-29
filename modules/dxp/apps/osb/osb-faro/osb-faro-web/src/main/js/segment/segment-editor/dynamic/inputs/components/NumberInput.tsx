import BetweenNumberInput, {BetweenNumber} from './BetweenNumberInput';
import Form from 'shared/components/form';
import getCN from 'classnames';
import Input from 'shared/components/Input';
import React from 'react';
import {FunctionalOperators, RelationalOperators} from '../../utils/constants';
import {isValid} from '../../utils/utils';
import {noop} from 'lodash';

interface INumberInputProps {
	className?: string;
	onBlur?: () => void;
	onChange: (params: {
		touched?: boolean;
		valid?: boolean;
		value?: number | string | BetweenNumber;
	}) => void;
	operatorName: RelationalOperators & FunctionalOperators;
	touched: boolean;
	valid: boolean;
	value: number | string | BetweenNumber;
}

const NumberInput: React.FC<INumberInputProps> = ({
	className,
	onBlur = noop,
	onChange,
	operatorName,
	touched,
	valid,
	value,
	...otherProps
}) => {
	if (operatorName === FunctionalOperators.Between) {
		return (
			<BetweenNumberInput
				onChange={onChange}
				value={value as BetweenNumber}
			/>
		);
	}

	return (
		<Form.GroupItem
			className={getCN({
				'has-error': !valid && touched
			})}
			shrink
		>
			<Input
				{...otherProps}
				className={getCN('number-input', className)}
				data-testid='number-input'
				onBlur={() => {
					onChange({touched: true, valid, value});

					onBlur && onBlur();
				}}
				onChange={event => {
					const {value} = event.target;

					let numberVal: string | number = '';

					if (isValid(value)) {
						numberVal = parseInt(value);
					}

					onChange({
						touched: true,
						valid: isValid(value),
						value: numberVal
					});
				}}
				placeholder={Liferay.Language.get('number')}
				type='number'
				value={value}
			/>
		</Form.GroupItem>
	);
};

export default NumberInput;
