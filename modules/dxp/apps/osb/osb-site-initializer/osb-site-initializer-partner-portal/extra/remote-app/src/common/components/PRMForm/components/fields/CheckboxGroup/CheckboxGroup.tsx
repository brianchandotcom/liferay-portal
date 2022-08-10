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
import classNames from 'classnames';

import WrapperInput from '../common/components/WrapperInput';
import PRMFormFieldProps from '../common/interfaces/prmFormFieldProps';
import PRMFormFieldStateProps from '../common/interfaces/prmFormFieldStateProps';

interface IProps {
	items: React.OptionHTMLAttributes<HTMLOptionElement>[];
}

const CheckboxGroup = ({
	field,
	items = [],
	label,
	meta,
	required,
}: IProps & PRMFormFieldProps & PRMFormFieldStateProps<string[]>) => (
	<WrapperInput {...meta} label={label} required={required}>
		<div
			className={classNames('border px-3 pt-3 rounded-lg', {
				'border-danger': meta.error && meta.touched,
				'border-neutral-5': !meta.touched,
				'border-success': !meta.error && meta.touched,
			})}
		>
			{items.map((item, index) => (
				<ClayCheckbox
					{...field}
					checked={field.value?.includes(item.label as string)}
					key={`${item.value}-${index}`}
					label={item.label}
					value={item.label}
				/>
			))}
		</div>
	</WrapperInput>
);
export default CheckboxGroup;
