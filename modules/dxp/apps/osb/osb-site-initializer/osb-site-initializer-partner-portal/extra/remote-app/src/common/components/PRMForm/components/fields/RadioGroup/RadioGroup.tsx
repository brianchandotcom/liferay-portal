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

import {ClayRadio} from '@clayui/form';

import WrapperInput from '../common/components/WrapperInput';
import PRMFormFieldProps from '../common/interfaces/prmFormFieldProps';
import PRMFormFieldStateProps from '../common/interfaces/prmFormFieldStateProps';

interface IProps {
	items: (React.OptionHTMLAttributes<HTMLOptionElement> & {
		description?: string;
	})[];
}

const RadioGroup = ({
	field,
	meta,
	items = [],
	label,
	required,
}: IProps & PRMFormFieldProps & PRMFormFieldStateProps<string>) => {
	return (
		<WrapperInput {...meta} label={label} required={required}>
			{items.map((item, index) => (
				<div
					className="border border-neutral-5 mb-4 p-3 rounded-lg"
					key={index}
				>
					<ClayRadio
						{...field}
						checked={field.value === item.value}
						key={`${item.value}-${index}`}
						label={item.label}
						value={item.value as string}
					/>

					{item.description && (
						<div className="ml-4 sheet-text">
							{item.description}
						</div>
					)}
				</div>
			))}
		</WrapperInput>
	);
};
export default RadioGroup;
