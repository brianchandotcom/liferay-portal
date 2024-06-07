/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClaySelectWithOption} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classnames from 'classnames';
import React, {useCallback, useEffect, useState} from 'react';

// @ts-ignore

import {getListTypeEntries} from '../../data/listType';
import ErrorMessage from '../ErrorMessage';
import {TGenericFieldProps} from '../FieldsWrapper';

const DEFAULT_OPTION: TPicklistOption = {
	key: '',
	label: Liferay.Language.get('select-item'),
};

const _valueToSelection = (
	value: string | undefined,
	options: TPicklistOption[]
): TPicklistOption | undefined =>
	options.find((option) => value && value === option.key);

const Picklist = ({
	disabled,
	id,
	label,
	mode = 'view',
	name,
	namespace,
	onChange,
	originalField: {listTypeDefinitionId},
	readOnly,
	required = false,
	value: keyAsValue,
}: TGenericFieldProps) => {
	const [key] = useState<string | null>(keyAsValue);
	const [selectedOption, setSelectedOption] =
		useState<TPicklistOption | null>(null);
	const [options, setOptions] = useState<TPicklistOption[]>([]);
	const [error, setError] = useState<string | null>(null);

	const fetchSourceItems = useCallback(() => {
		getListTypeEntries(listTypeDefinitionId).then(
			({items: listTypeEntries}: {items: TPartialListTypeEntry[]}) => {
				const languageId = Liferay.ThemeDisplay.getBCP47LanguageId();

				setOptions([
					DEFAULT_OPTION,
					...listTypeEntries.map(
						(entry: TPartialListTypeEntry): TPicklistOption => ({
							key: entry.key,
							label: entry.name_i18n[languageId],
						})
					),
				]);
			}
		);
	}, [listTypeDefinitionId]);

	const onSelection = useCallback(
		({target}) => {
			const option = options.find(({label}) => target?.value === label);

			if (option) {
				setSelectedOption(option);
			}
		},
		[options]
	);

	useEffect(() => {
		if (options.length) {
			const selectedOption = _valueToSelection(key as string, options);

			if (selectedOption) {
				setSelectedOption(selectedOption || DEFAULT_OPTION);
			}
		}
		else {
			fetchSourceItems();
		}
	}, [fetchSourceItems, options, key]);

	useEffect(() => {
		if (mode === 'edit') {
			const hasError = required && !selectedOption?.key;

			onChange({
				hasError,
				name,
				value: selectedOption?.key ?? '',
			});

			setError(
				hasError ? Liferay.Language.get('this-field-is-required') : null
			);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedOption]);

	return mode === 'edit' ? (
		<ClayForm.Group
			className={classnames({
				'has-error': !!error,
			})}
		>
			<label htmlFor={`${namespace}${name}`}>
				{label}

				{required && (
					<ClayIcon
						className="c-ml-1 reference-mark"
						symbol="asterisk"
					/>
				)}
			</label>

			{!!options.length && (
				<ClaySelectWithOption
					aria-label={Liferay.Language.get('select-item')}
					aria-required={required}
					disabled={disabled || readOnly}
					id={`${namespace}${id}`}
					name={`${namespace}${name}`}
					onChange={onSelection}
					options={options}
					value={selectedOption?.label ?? ''}
				/>
			)}

			<ErrorMessage error={error} />
		</ClayForm.Group>
	) : (
		<div key={`${namespace}_${id}`}>
			<div className="sidebar-dt">{label}</div>

			<div className="sidebar-dd text-wrap">
				{selectedOption?.key ? selectedOption.label : '-'}
			</div>
		</div>
	);
};

type TPartialListTypeEntry = {
	key: string;
	name_i18n: {
		[localeCode: string]: string;
	};
};

type TPicklistOption = {
	key: string;
	label: string;
};

export default Picklist;
