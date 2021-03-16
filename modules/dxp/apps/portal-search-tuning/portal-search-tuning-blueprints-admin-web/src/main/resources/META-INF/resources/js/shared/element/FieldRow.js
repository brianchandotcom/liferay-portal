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

import ClayAutocomplete from '@clayui/autocomplete';
import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayForm, {ClayInput, ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {FocusScope} from '@clayui/shared';
import {ClayTooltipProvider} from '@clayui/tooltip';
import fuzzy from 'fuzzy';
import React, {useContext, useEffect, useRef, useState} from 'react';

import ThemeContext from '../ThemeContext';

function AutocompleteItem({indexField, match = '', onClick}) {
	const fuzzyMatch = fuzzy.match(match, indexField.name, {
		post: '</strong>',
		pre: '<strong>',
	});

	return (
		<ClayDropDown.Item onClick={onClick}>
			{fuzzyMatch ? (
				<span
					dangerouslySetInnerHTML={{
						__html: fuzzyMatch.rendered,
					}}
				/>
			) : (
				indexField.name
			)}

			{indexField.language_id_position > -1 && (
				<ClayIcon symbol="globe" />
			)}

			<span className="type">{indexField.type}</span>
		</ClayDropDown.Item>
	);
}

/**
 * Displays an autocomplete input for selecting an indexed field.
 *
 * Example index field object:
 * {
 * 	language_id_position: -1,
 * 	name: 'ddmTemplateKey',
 * 	type: 'keyword'
 * }
 */
function FieldRow({
	boost = 0,
	defaultValue = [],
	disabled,
	field,
	id,
	index = 0,
	indexFields = [],
	languageIdPosition,
	locale,
	onBlur,
	onChange,
	onDelete,
	showBoost,
}) {
	const {availableLanguages} = useContext(ThemeContext);

	const inputRef = useRef();

	const [filteredIndexFields, setFilteredIndexFields] = useState(
		indexFields.filter((indexField) => indexField.name.includes(field))
	);
	const [showDropDown, setShowDropDown] = useState(false);

	useEffect(() => {
		setFilteredIndexFields(
			indexFields.filter((indexField) => indexField.name.includes(field))
		);
	}, [indexFields, field]);

	const _getIndexField = (fieldName) => {
		return (
			defaultValue.find((item) => item.field === fieldName) ||
			indexFields.find((item) => item.name === fieldName)
		);
	};

	const _handleFieldChange = (event) => {
		const indexField = _getIndexField(event.target.value) || {};

		let languageIdPosition = indexField.language_id_position || -1;

		if (indexField.locale && languageIdPosition === -1) {
			languageIdPosition = event.target.value.length;
		}

		onChange({
			field: event.target.value,
			languageIdPosition,
			locale: languageIdPosition > -1 ? '' : undefined,
		});
	};

	const _handleAutocompleteItemClick = (indexField) => () => {
		onChange({
			field: indexField.name,
			languageIdPosition: indexField.language_id_position,
		});

		inputRef.current.focus();

		setShowDropDown(false);
	};

	const _handleBoostChange = (event) => onChange({boost: event.target.value});

	const _handleLocaleChange = (event) =>
		onChange({locale: event.target.value});

	const _isLocalizable = () =>
		languageIdPosition > -1 || locale !== undefined;

	return (
		<ClayForm.Group>
			<ClayInput.Group small>
				<ClayInput.GroupItem>
					<FocusScope>
						<ClayAutocomplete>
							<ClayAutocomplete.Input
								autoComplete="off"
								disabled={disabled}
								id={id}
								onBlur={onBlur}
								onChange={_handleFieldChange}
								onFocus={() => setShowDropDown(true)}
								onKeyDown={() => setShowDropDown(true)}
								ref={inputRef}
								sizing="sm"
								value={field}
							/>

							<ClayAutocomplete.DropDown
								active={
									showDropDown && filteredIndexFields.length
								}
								onSetActive={setShowDropDown}
							>
								<ClayDropDown.ItemList className="blueprint-field-row-dropdown">
									{filteredIndexFields.map((indexField) => (
										<AutocompleteItem
											indexField={indexField}
											key={indexField.name}
											match={field}
											onClick={_handleAutocompleteItemClick(
												indexField
											)}
										/>
									))}
								</ClayDropDown.ItemList>
							</ClayAutocomplete.DropDown>
						</ClayAutocomplete>
					</FocusScope>
				</ClayInput.GroupItem>

				{_isLocalizable() && (
					<ClayInput.GroupItem>
						<ClaySelect
							aria-label={Liferay.Language.get('locale')}
							className="form-control-sm"
							disabled={disabled}
							id={`${id}_locale`}
							onBlur={onBlur}
							onChange={_handleLocaleChange}
							value={locale}
						>
							<ClaySelect.Option
								key={`no-localization-${index}`}
								label={Liferay.Language.get('no-localization')}
								value=""
							/>

							<ClaySelect.Option
								key={`users-language-${index}`}
								label={Liferay.Language.get('users-language')}
								value={'${context.language_id}'}
							/>

							{Object.keys(availableLanguages).map((locale) => (
								<ClaySelect.Option
									key={`${index}-${locale}`}
									label={availableLanguages[locale]}
									value={locale}
								/>
							))}
						</ClaySelect>
					</ClayInput.GroupItem>
				)}

				{showBoost && (
					<ClayInput.GroupItem shrink>
						<ClayTooltipProvider>
							<ClayInput
								aria-label={Liferay.Language.get('boost')}
								className="field-boost-input"
								data-tooltip-align="top"
								disabled={disabled}
								id={`${id}_boost`}
								min="0"
								onBlur={onBlur}
								onChange={_handleBoostChange}
								title={Liferay.Language.get('boost')}
								type="number"
								value={boost}
							/>
						</ClayTooltipProvider>
					</ClayInput.GroupItem>
				)}

				{onDelete && (
					<ClayInput.GroupItem shrink>
						<ClayButton
							aria-label={Liferay.Language.get('delete')}
							disabled={disabled}
							displayType="unstyled"
							monospaced
							onClick={onDelete}
							small
						>
							<ClayIcon symbol="times-circle" />
						</ClayButton>
					</ClayInput.GroupItem>
				)}
			</ClayInput.Group>
		</ClayForm.Group>
	);
}

export default FieldRow;
