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

import WrapperInput from '../common/components/WrapperInput';
import PRMFormFieldProps from '../common/interfaces/prmFormFieldProps';
import PRMFormFieldStateProps from '../common/interfaces/prmFormFieldStateProps';

const DatePicker = ({
	field,
	label,
	meta,
	required,
	...props
}: PRMFormFieldProps & PRMFormFieldStateProps<string>) => (
	<WrapperInput {...meta} label={label} required={required}>
		<input
			className="form-control"
			name={field.name}
			onBlur={field.onBlur}
			onChange={field.onChange}
			type="date"
			value={field.value || ''}
			{...props}
		/>
	</WrapperInput>
);

export default DatePicker;
