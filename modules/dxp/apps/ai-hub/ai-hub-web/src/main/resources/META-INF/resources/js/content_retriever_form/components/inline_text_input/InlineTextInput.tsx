/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {InputLocalized} from 'frontend-js-components-web';

import './styles.scss';

import React from 'react';

type InlineTextInputProps = {
	error?: string | boolean;
	name: string;
	onBlur?: React.FocusEventHandler<HTMLInputElement>;
	onChange: (translations: Liferay.Language.LocalizedValue<string>) => void;
	placeholder: string;
	translations?: Liferay.Language.LocalizedValue<string>;
};

export default function InlineTextInput({
	error,
	name,
	onBlur,
	onChange,
	placeholder,
	translations = {},
}: InlineTextInputProps) {
	return (
		<div className="inline-text-border">
			<InputLocalized
				className={`form-control form-control-inline${error ? ' is-invalid' : ''}`}
				label=""
				name={name}
				onBlur={onBlur}
				onChange={onChange}
				placeholder={placeholder}
				translations={translations}
			/>

			{typeof error === 'string' && (
				<div className="d-block invalid-feedback mt-1">{error}</div>
			)}
		</div>
	);
}
