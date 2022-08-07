/* eslint-disable no-console */
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

import {ClayCheckbox} from '@clayui/form';
import {FieldProps} from 'formik';

import WrapperInput from '../common/components/WrapperInput';
import PRMFormFieldProps from '../common/interfaces/prmFormFieldProps';

interface IProps {
	items: React.OptionHTMLAttributes<HTMLOptionElement>[];
}

const CheckboxGroup = ({
	field,
	form,
	items = [],
	label,
	required,
}: IProps & PRMFormFieldProps & FieldProps<string[]>) => (
	<WrapperInput
		{...form.getFieldMeta(field.name)}
		label={label}
		required={required}
	>
		{items.map((item, index) => (
			<ClayCheckbox
				{...field}
				checked={field.value.includes(item.label as string)}
				key={`${item.value}-${index}`}
				label={item.label}
				value={item.label}
			/>
		))}
	</WrapperInput>
);
export default CheckboxGroup;
