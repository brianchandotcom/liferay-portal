/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {normalizeFieldName} from 'data-engine-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useRef} from 'react';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';
import Text from '../Text/Text.es';
import {useSyncValue} from '../hooks/useSyncValue.es';

const KeyValue = ({
	allowSpecialCharacters,
	className,
	disabled,
	onChange,
	value,
	...otherProps
}) => (
	<div className="active form-text key-value-editor">
		<label className="control-label key-value-label">
			{className === 'key-value-reference-input'
				? Liferay.Language.get('field-reference')
				: Liferay.Language.get('field-name')}
			:
		</label>

		<input
			{...otherProps}
			className={`${disabled ? 'disabled ' : ''}${className}`}
			onChange={({target: {value}}) =>
				onChange(
					allowSpecialCharacters ? value : normalizeFieldName(value)
				)
			}
			readOnly={disabled}
			tabIndex={disabled ? '-1' : '0'}
			type="text"
			value={value}
		/>
	</div>
);

const Main = ({
	allowSpecialCharacters,
	editingLanguageId,
	generateKeyword,
	keyword: initialKeyword,
	keywordReadOnly,
	name,
	onBlur,
	onChange,
	onClick,
	onFocus,
	onKeyDown,
	onKeywordBlur,
	onKeywordChange,
	onReferenceBlur,
	onReferenceChange,
	placeholder,
	readOnly,
	reference,
	required,
	showCloseButton,
	showKeyword = false,
	showLabel,
	spritemap,
	value,
	visible,
	...otherProps
}) => {
	const [keyword, setKeyword] = useSyncValue(initialKeyword);

	const generateKeywordRef = useRef(generateKeyword);

	return (
		<FieldBase
			{...otherProps}
			name={name}
			readOnly={readOnly}
			required={required}
			showLabel={showLabel}
			spritemap={spritemap}
			visible={visible}
		>
			<Text
				editingLanguageId={editingLanguageId}
				name={`keyValueLabel${name}`}
				onBlur={onBlur}
				onChange={({target: {value}}) => {
					onChange(value);

					if (generateKeywordRef.current) {
						onKeywordChange(
							allowSpecialCharacters
								? value
								: normalizeFieldName(value),
							true
						);
					}
				}}
				onFocus={onFocus}
				onKeyDown={onKeyDown}
				placeholder={placeholder}
				readOnly={readOnly}
				required={required}
				showLabel={showLabel}
				spritemap={spritemap}
				syncDelay={false}
				value={value}
				visible={visible}
			/>

			{showCloseButton && (
				<button
					aria-label={sub(
						Liferay.Language.get('remove-x-option'),
						keyword
					)}
					className="close close-modal"
					onClick={onClick}
					type="button"
				>
					<ClayIcon symbol="times" />
				</button>
			)}

			{showKeyword && (
				<KeyValue
					allowSpecialCharacters={allowSpecialCharacters}
					className="key-value-input"
					disabled={keywordReadOnly}
					onBlur={onKeywordBlur}
					onChange={(value) => {
						generateKeywordRef.current = false;
						onKeywordChange(value, false);
						setKeyword(value);
					}}
					value={keyword}
				/>
			)}

			<KeyValue
				allowSpecialCharacters={allowSpecialCharacters}
				className="key-value-reference-input"
				onBlur={onReferenceBlur}
				onChange={onReferenceChange}
				value={reference}
			/>
		</FieldBase>
	);
};

Main.displayName = 'KeyValue';

export default Main;
