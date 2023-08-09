/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayMultiSelect from '@clayui/multi-select';
import ClaySticker from '@clayui/sticker';
import classNames from 'classnames';
import {useField, useFormikContext} from 'formik';
import {useEffect, useState} from 'react';
import {Badge} from '../..';
import {required, validateEmailsArray} from '../../../utils/validations.form';
import '../../../containers/setup-forms/SetupAnalyticsCloudForm/index.scss';

const MultiSelect = ({
	disableError,
	groupStyle,
	helper,
	items,
	label,
	onChanges,
	sourceItems,
	validations,
	values,
	...props
}) => {
	const handleChange = (item) => {
		return onChanges(item);
	};
	const [emailsAvailable] = useState(sourceItems);
	const formik = useFormikContext();

	if (props.required) {
		validations = validations
			? [...validations, () => required(values.length)]
			: [() => required(values.length)];
	}

	const validateMultiSelect = () => {
		const unfilledField = validations
			.map((validation) => validation(values))
			.filter((error) => !!error);

		const emailErrors = validateEmailsArray(
			values.map((item) => item?.email || item?.label),
			emailsAvailable
		);

		return unfilledField.length ? unfilledField[0] : emailErrors;
	};

	const [field, meta] = useField({
		...props,
		validate: validateMultiSelect,
	});

	useEffect(() => {
		formik.setFieldValue(props.name, values);
		formik.validateField(props.name);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [values]);

	return (
		<>
			<ClayForm.Group
				className={classNames('w-100', {
					groupStyle,
					'has-error': meta.touched && meta.error,
					'has-success': meta.touched && !meta.error,
				})}
			>
				<label>
					{`${label} `}

					{props.required && (
						<span className="inline-item-after reference-mark text-warning">
							<ClayIcon symbol="asterisk" />
						</span>
					)}
				</label>

				<ClayMultiSelect
					{...field}
					{...props}
					items={items}
					onChange={() => handleChange(window.event?.target?.value)}
					sourceItems={sourceItems}
					value={items?.value}
				>
					{(item, index) => (
						<ClayMultiSelect.Item
							key={index}
							textValue={item?.label}
						>
							<div className="autofit-row autofit-row-center">
								<div className="autofit-col mr-3">
									<ClaySticker
										className="sticker-user-icon"
										size="sm"
									>
										<ClayIcon symbol="user" />
									</ClaySticker>
								</div>

								<div className="autofit-col">
									<strong>{item?.label}</strong>

									<span>{item?.email}</span>
								</div>
							</div>
						</ClayMultiSelect.Item>
					)}
				</ClayMultiSelect>

				{(typeof meta.error === 'string' ||
					meta.error instanceof String) &&
				meta.touched &&
				!disableError ? (
					<Badge>
						<span className="pl-1">{meta.error}</span>
					</Badge>
				) : (
					helper && (
						<div className="ml-3 pr-2 text-neutral-6 text-paragraph-sm">
							{helper}
						</div>
					)
				)}
			</ClayForm.Group>
		</>
	);
};

export default MultiSelect;
