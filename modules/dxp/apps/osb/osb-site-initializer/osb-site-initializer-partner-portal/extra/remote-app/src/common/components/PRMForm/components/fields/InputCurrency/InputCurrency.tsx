/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {IMaskInput} from 'react-imask';

import WrapperInput from '../common/components/WrapperInput';
import PRMFormFieldProps from '../common/interfaces/prmFormFieldProps';
import PRMFormFieldStateProps from '../common/interfaces/prmFormFieldStateProps';

interface IProps {
	onAccept: (value: number) => void;
}

const InputCurrency = ({
	description,
	field,
	label,
	meta,
	onAccept,
	required,
}: PRMFormFieldProps & PRMFormFieldStateProps<number> & IProps) => (
	<WrapperInput
		{...meta}
		description={description}
		label={label}
		required={required}
	>
		<IMaskInput
			className="form-control"
			mapToRadix={['.']}
			mask={Number}
			name={field.name}
			normalizeZeros
			onAccept={(value) => onAccept(value as number)}
			onBlur={(event) =>
				field.onBlur(event as React.FocusEvent<Element, Element>)
			}
			padFractionalZeros
			radix="."
			required={required}
			scale={2}
			thousandsSeparator=","
			unmask="typed"
			value={field.value || ''}
		/>
	</WrapperInput>
);

export default InputCurrency;
