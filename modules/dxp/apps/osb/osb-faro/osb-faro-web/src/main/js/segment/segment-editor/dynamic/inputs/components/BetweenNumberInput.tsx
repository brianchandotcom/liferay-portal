import Form from 'shared/components/form';
import getCN from 'classnames';
import Input from 'shared/components/Input';
import React, {useEffect, useState} from 'react';
import {isValid} from '../../utils/utils';

export type BetweenNumber = {
	end: string | number;
	start: string | number;
};

interface IBetweenNumberInputProps {
	onChange: (params: {
		value?: number | string | BetweenNumber;
		touched?: boolean;
		valid?: boolean;
	}) => void;
	value: BetweenNumber;
}

const BetweenNumberInput: React.FC<IBetweenNumberInputProps> = ({
	onChange,
	value
}) => {
	const [inputsValid, setInputsValid] = useState({end: false, start: false});
	const [inputsTouched, setInputsTouched] = useState({
		end: false,
		start: false
	});

	useEffect(() => {
		onChange({
			touched: inputsTouched.end && inputsTouched.start,
			valid: inputsValid.end && inputsValid.start,
			value
		});
	}, [inputsTouched, inputsValid]);

	return (
		<>
			<Form.GroupItem
				className={getCN({
					'has-error': !inputsValid.start && inputsTouched.start
				})}
				shrink
			>
				<Input
					className='number-input'
					data-testid='between-number-start-input'
					onBlur={() => {
						setInputsTouched({...inputsTouched, start: true});
					}}
					onChange={event => {
						const {value: start} = event.target;

						let numberVal: string | number = '';

						if (isValid(start)) {
							numberVal = parseInt(start);
						}

						setInputsTouched({...inputsTouched, start: true});
						setInputsValid({
							...inputsValid,
							start: isValid(start)
						});

						onChange({
							value: {end: value.end, start: numberVal}
						});
					}}
					placeholder={Liferay.Language.get('number')}
					type='number'
					value={value.start}
				/>
			</Form.GroupItem>

			<Form.GroupItem
				className={getCN({
					'has-error': !inputsValid.end && inputsTouched.end
				})}
				shrink
			>
				<Input
					className='number-input'
					data-testid='between-number-end-input'
					onBlur={() => {
						setInputsTouched({...inputsTouched, end: true});
					}}
					onChange={event => {
						const {value: end} = event.target;

						let numberVal: string | number = '';

						if (isValid(end)) {
							numberVal = parseInt(end);
						}

						setInputsTouched({...inputsTouched, end: true});
						setInputsValid({
							...inputsValid,
							end: isValid(numberVal)
						});

						onChange({
							value: {end: numberVal, start: value.start}
						});
					}}
					placeholder={Liferay.Language.get('number')}
					type='number'
					value={value.end}
				/>
			</Form.GroupItem>
		</>
	);
};

export default BetweenNumberInput;
