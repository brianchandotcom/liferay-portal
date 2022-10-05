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

import {ClayInput} from '@clayui/form';

import WrapperInput from '../common/components/WrapperInput';
import PRMFormFieldProps from '../common/interfaces/prmFormFieldProps';
import PRMFormFieldStateProps from '../common/interfaces/prmFormFieldStateProps';

interface IProps {
	textArea?: boolean;
}

const InputText = ({
	description,
	field,
	label,
	meta,
	required,
	textArea,
}: PRMFormFieldProps & PRMFormFieldStateProps<string> & IProps) => (
	<WrapperInput
		{...meta}
		description={description}
		label={label}
		required={required}
	>
		<ClayInput
			{...field}
			component={textArea ? 'textarea' : 'input'}
			required={required}
			type="text"
			value={field.value || ''}
		/>
	</WrapperInput>
);

export default InputText;
