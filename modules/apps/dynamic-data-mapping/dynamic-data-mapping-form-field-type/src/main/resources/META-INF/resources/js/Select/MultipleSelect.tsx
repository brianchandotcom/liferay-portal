/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useFormState} from 'data-engine-js-components-web';
import React, {useCallback, useEffect, useState} from 'react';

import MultipleSelectLocalizedObjectField, {
	MultipleSelectLocalizedObjectFieldProps,
} from '../localizedObjectFields/MultipleSelectLocalizedObjectField';
import {MultipleSelectBase} from './MultipleSelectBase';
import {MultipleSelectBaseProps} from './select.d';

export type MultipleSelectionProps = MultipleSelectBaseProps<string[] | string>;

const MultipleSelection = ({
	errorMessage,
	id,
	label,
	name,
	onChange,
	options,
	readOnly,
	required,
	tip,
	value: values,
	...otherProps
}: MultipleSelectionProps) => {
	const [loading, setLoading] = useState<boolean>();
	const {activeTabTitle, viewMode} = useFormState();

	useEffect(() => {
		if (
			!readOnly &&
			activeTabTitle !== Liferay.Language.get('advanced') &&
			!viewMode
		) {
			setLoading(true);
			setTimeout(() => setLoading(false), 200);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [options]);

	const handleAsyncOptions = useCallback(() => {
		return new Promise((resolve) => {
			resolve(options);
		});
	}, [options]);

	return (
		<MultipleSelectBase
			{...otherProps}
			errorMessage={errorMessage}
			id={id}
			label={label}
			loading={loading}
			name={name}
			onChange={onChange}
			onLoadMore={handleAsyncOptions}
			options={options}
			readOnly={readOnly}
			required={required}
			tip={tip}
			value={values}
		/>
	);
};

const Main = (
	props: MultipleSelectionProps | MultipleSelectLocalizedObjectFieldProps
) => {
	const isLocalizedObjectField: boolean =
		Liferay.FeatureFlags['LPD-32050'] && !!props.localizedObjectField;

	return !isLocalizedObjectField ? (
		<MultipleSelection {...(props as MultipleSelectionProps)} />
	) : (
		<MultipleSelectLocalizedObjectField
			{...(props as MultipleSelectLocalizedObjectFieldProps)}
		/>
	);
};

export default Main;
