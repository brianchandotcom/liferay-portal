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
import {FieldProps} from 'formik';

import WrapperInput from '../common/components/WrapperInput';
import PRMFormFieldProps from '../common/interfaces/prmFormFieldProps';

const InputText = ({
	field,
	form,
	label,
	required,
	...props
}: PRMFormFieldProps &
	FieldProps<string> &
	React.ComponentProps<typeof ClayInput>) => (
	<WrapperInput
		{...form.getFieldMeta(field.name)}
		label={label}
		required={required}
	>
		<ClayInput
			{...props}
			name={field.name}
			onBlur={field.onBlur}
			onChange={field.onChange}
		/>
	</WrapperInput>
);

export default InputText;
