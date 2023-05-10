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

import ClayForm, {ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {useField} from 'formik';
import {Badge} from '../..';
import {required, validate} from '../../../utils/validations.form';

const Select = ({
	groupStyle,
	helper,
	label,
	options,
	validations,
	...props
}) => {
	if (props.required) {
		validations = validations
			? [...validations, (value) => required(value)]
			: [(value) => required(value)];
	}

	const [field, meta] = useField({
		...props,
		validate: (value) => validate(validations, value),
	});

	const getStyleStatus = () => {
		if (meta.touched) {
			return meta.error ? 'has-error' : 'has-success';
		}

		return;
	};

	return (
		<ClayForm.Group
			className={classNames('w-100', getStyleStatus(), groupStyle)}
		>
			<label>
				{label}

				{props.required && (
					<span className="inline-item-after reference-mark text-warning">
						<ClayIcon symbol="asterisk" />
					</span>
				)}

				<div className="position-relative">
					<ClayIcon className="select-icon" symbol="caret-bottom" />

					<ClaySelect {...field} {...props}>
						{options.map(({disabled, label, value}, index) => (
							<ClaySelect.Option
								disabled={disabled}
								key={`${value}-${index}`}
								label={label}
								value={value}
							/>
						))}
					</ClaySelect>
				</div>
			</label>

			{meta.touched && meta.error && props.required && (
				<Badge>
					<span className="pl-1">{meta.error}</span>
				</Badge>
			)}

			{helper && (
				<div className="ml-3 pl-3 text-neutral-6 text-paragraph-sm">
					{helper}
				</div>
			)}
		</ClayForm.Group>
	);
};

export default Select;
