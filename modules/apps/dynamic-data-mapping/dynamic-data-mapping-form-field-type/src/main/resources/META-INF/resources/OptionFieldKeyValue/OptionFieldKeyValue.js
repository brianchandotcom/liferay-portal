/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayPanel from '@clayui/panel';
import {ClayTooltipProvider} from '@clayui/tooltip';
import classNames from 'classnames';
import {normalizeFieldName} from 'data-engine-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useRef, useState} from 'react';

import FieldBase from '../FieldBase/ReactFieldBase.es';
import Text from '../Text/Text.es';
import {useSyncValue} from '../hooks/useSyncValue.es';

import './OptionFieldKeyValue.scss';

export default function OptionFieldKeyValue({
	allowSpecialCharacters,
	editingLanguageId,
	expandedPanel,
	generateKeyword,
	generateOptionValueUsingOptionLabel,
	invalidField,
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
}) {
	const defatultOptionLabel = Liferay.Language.get('option');

	const [keyword, setKeyword] = useSyncValue(initialKeyword);
	const [panelState, setPanelState] = useState({
		expanded: !!expandedPanel,
		panelDisplayTitle: value ? value : defatultOptionLabel,
	});

	const generateKeywordRef = useRef(generateKeyword);

	return (
		<ClayPanel
			collapsable
			collapseClassNames={classNames({
				expanded: panelState.expanded,
			})}
			collapseHeaderClassNames={classNames({
				'collapsable-option-header': true,
			})}
			displayTitle={panelState.panelDisplayTitle}
			displayType="secondary"
			expanded={panelState.expanded}
			onExpandedChange={(event) => {
				setPanelState((currentState) => ({
					...currentState,
					expanded: value ? event : true,
				}));
			}}
			showCollapseIcon
		>
			<ClayPanel.Body className="collapsable-option-body">
				<label htmlFor={`keyValueDisplayName${name}`}>
					{Liferay.Language.get('display-name')}
				</label>

				<FieldBase
					{...otherProps}
					displayErrors={false}
					name={name}
					readOnly={readOnly}
					required={required}
					showLabel={showLabel}
					spritemap={spritemap}
					visible={visible}
				>
					<Text
						editingLanguageId={editingLanguageId}
						id={`keyValueDisplayName${name}`}
						name={`keyValueDisplayName${name}`}
						onBlur={(event) => {
							setPanelState((currentState) => ({
								...currentState,
								panelDisplayTitle:
									event.target.value !== ''
										? event.target.value
										: defatultOptionLabel,
							}));
							onBlur(event);
						}}
						onChange={({target: {value}}) => {
							onChange(value);

							if (generateKeywordRef.current) {
								const getKeywordValueOnEmptyLabel = (value) => {
									if (generateOptionValueUsingOptionLabel) {
										return value === '' ? reference : value;
									}

									return value;
								};

								onKeywordChange(
									allowSpecialCharacters
										? getKeywordValueOnEmptyLabel(value)
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
				</FieldBase>

				<label
					className="mt-3"
					htmlFor={`keyValueReference${reference}`}
				>
					{Liferay.Language.get('option-reference')}
				</label>

				<FieldBase
					{...otherProps}
					displayErrors={invalidField === 'reference'}
					name={reference}
					readOnly={readOnly}
					required={required}
					showLabel={showLabel}
					spritemap={spritemap}
					visible={visible}
				>
					<Text
						editingLanguageId={editingLanguageId}
						forceValue
						id={`keyValueReference${reference}`}
						name={`keyValueReference${reference}`}
						onBlur={onReferenceBlur}
						onChange={({target: {value}}) => {
							onReferenceChange(value);
						}}
						preventChangeHandlerOnBlur
						value={reference}
						visible={visible}
					/>
				</FieldBase>

				{showKeyword && (
					<>
						<label
							className="mt-3"
							htmlFor={`keyValueName${keyword}`}
						>
							{Liferay.Language.get('option-name')}
						</label>

						<ClayTooltipProvider>
							<span
								data-tooltip-align="top"
								title={Liferay.Language.get(
									'modifying-the-option-name-may-result-in-data-loss'
								)}
							>
								&nbsp;
								<ClayIcon
									className="tooltip-icon"
									symbol="question-circle-full"
								/>
							</span>
						</ClayTooltipProvider>

						<FieldBase
							{...otherProps}
							displayErrors={invalidField === 'value'}
							name={keyword}
							readOnly={keywordReadOnly}
							required={required}
							showLabel={showLabel}
							spritemap={spritemap}
							visible={visible}
						>
							<Text
								editingLanguageId={editingLanguageId}
								id={`keyValueName${keyword}`}
								name={`keyValueName${keyword}`}
								onBlur={onKeywordBlur}
								onChange={({target: {value}}) => {
									value = allowSpecialCharacters
										? value
										: normalizeFieldName(value);
									generateKeywordRef.current = false;
									onKeywordChange(value, false);
									setKeyword(value);
								}}
								preventChangeHandlerOnBlur
								readOnly={keywordReadOnly}
								value={keyword}
								visible={visible}
							/>
						</FieldBase>
					</>
				)}
			</ClayPanel.Body>
		</ClayPanel>
	);
}
