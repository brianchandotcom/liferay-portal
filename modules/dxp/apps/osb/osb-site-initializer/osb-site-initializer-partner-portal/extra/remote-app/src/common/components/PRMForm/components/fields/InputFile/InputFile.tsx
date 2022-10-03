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

const InputFile = ({
	description,
	field,
	label,
	meta,
	onChange,
	required,
	value,
	...props
}: PRMFormFieldProps &
	PRMFormFieldStateProps<string> &
	React.ComponentProps<typeof ClayInput>) => (
	<WrapperInput
		{...meta}
		description={description}
		label={label}
		required={required}
	>
		<ClayInput
			{...props}
			{...field}
			onChange={onChange || field.onChange}
			required={required}
			type="file"
			value={value || field.value || ''}
		/>
	</WrapperInput>
);

export default InputFile;
