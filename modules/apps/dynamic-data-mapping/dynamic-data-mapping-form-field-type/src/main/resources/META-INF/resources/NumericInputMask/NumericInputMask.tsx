/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {ClayInput} from '@clayui/form';
import React, {FocusEventHandler, useEffect, useMemo, useState} from 'react';

// @ts-ignore

import Radio from '../Radio/Radio.es';

// @ts-ignore

import Select from '../Select/Select.es';

// @ts-ignore

import Text from '../Text/Text.es';
import {limitValue} from '../util/numericalOperations';

import type {FieldChangeEventHandler, Locale, LocalizedValue} from '../types';

type DecimalSymbol = ',' | '.';
type ThousandsSeparator = DecimalSymbol | ' ' | "'" | 'none';

const DEFAULT_DECIMAL_PLACES = 2;
const MAX_DECIMAL_PLACES = 10;
const MIN_DECIMAL_PLACES = 1;

interface INumericInputMaskValue {
	append?: string;
	appendType?: 'prefix' | 'suffix';
	decimalSymbol?: DecimalSymbol[];
	symbols: LocalizedValue<ISymbols>;
	thousandsSeparator?: ThousandsSeparator[];
}
interface IProps {
	append?: string;
	appendType?: 'prefix' | 'suffix';
	decimalPlaces: number;
	decimalSymbol: DecimalSymbol[];
	decimalSymbols: ISelectProps<DecimalSymbol>[];
	defaultLanguageId: Locale;
	editingLanguageId: Locale;
	ffDecimalPlacesSettingsEnabled: boolean;
	onBlur: FocusEventHandler<HTMLInputElement>;
	onChange: FieldChangeEventHandler;
	onFocus: FocusEventHandler<HTMLInputElement>;
	readOnly: boolean;
	thousandsSeparator?: ThousandsSeparator[];
	thousandsSeparators: ISelectProps<ThousandsSeparator>[];
	value: INumericInputMaskValue;
	visible: boolean;
}

export interface ISymbols {
	decimalSymbol: DecimalSymbol;
	thousandsSeparator?: ThousandsSeparator | null;
}

interface ISelectProps<T> {
	disabled: boolean;
	label: LocalizedValue<string>;
	reference: string;
	value: T;
}

const NumericInputMask: React.FC<IProps> = ({
	append: appendInitial,
	appendType: appendTypeInitial,
	decimalPlaces: decimalPlacesInitial,
	decimalSymbol: decimalSymbolInitial,
	decimalSymbols: decimalSymbolsProp,
	editingLanguageId,
	onBlur,
	onChange,
	onFocus,
	readOnly,
	thousandsSeparator: thousandsSeparatorInitial,
	thousandsSeparators: thousandsSeparatorsProp,
	value,
	visible,
}) => {
	const [thousandsSeparator, setThousandsSeparator] = useState(
		thousandsSeparatorInitial
	);
	const [decimalPlaces, setDecimalPlaces] = useState(decimalPlacesInitial);
	const [decimalSymbol, setDecimalSymbol] = useState(decimalSymbolInitial);
	const [append, setAppend] = useState(appendInitial);
	const [appendType, setAppendType] = useState(appendTypeInitial);

	const decimalSymbols = useMemo(() => {
		return decimalSymbolsProp.map((item) => {
			return {
				...item,
				disabled: item.reference === thousandsSeparator?.[0],
			};
		});
	}, [decimalSymbolsProp, thousandsSeparator]);

	const thousandsSeparators = useMemo(() => {
		return thousandsSeparatorsProp.map((item) => {
			return {
				...item,
				disabled: item.reference === decimalSymbol?.[0],
				label:
					item.label?.[Liferay.ThemeDisplay.getLanguageId()] ??
					item.label,
			};
		});
	}, [decimalSymbol, thousandsSeparatorsProp]);

	useEffect(() => {
		const newValue =
			typeof value === 'string' ? JSON.parse(value) : {...value};

		setAppend(newValue.append ?? append);

		setAppendType(newValue.appendType ?? appendType);

		setDecimalPlaces(newValue.decimalPlaces ?? decimalPlaces);

		const symbols = newValue.symbols;

		setDecimalSymbol(symbols?.decimalSymbol ?? decimalSymbol);

		setThousandsSeparator(
			symbols?.thousandsSeparator ?? thousandsSeparator
		);
	}, [
		append,
		appendType,
		decimalPlaces,
		decimalSymbol,
		editingLanguageId,
		thousandsSeparator,
		value,
	]);

	const handleChange = (key: string, value: string | number | ISymbols) => {
		onChange({
			target: {
				value: {
					append,
					appendType,
					decimalPlaces,
					// eslint-disable-next-line sort-keys
					symbols: {
						decimalSymbol,
						thousandsSeparator,
					},

					// eslint-disable-next-line sort-keys
					[key]: value,
				},
			},
		});
	};

	return (
		<>
			<div className="align-items-end d-flex position-relative">
				<div className="pr-2 w-50">
					<Select
						label={Liferay.Language.get('thousands-separator')}
						name="thousandsSeparator"
						onBlur={onBlur}
						onChange={(event: any, value: any) => {
							handleChange('symbols', {
								decimalSymbol: decimalSymbol?.[0],
								thousandsSeparator: value[0],
							});

							setThousandsSeparator(value[0]);
						}}
						onFocus={onFocus}
						options={thousandsSeparators}
						placeholder={Liferay.Language.get('choose-an-option')}
						readOnly={readOnly}
						showEmptyOption={false}
						value={thousandsSeparator}
						visible={visible}
					/>
				</div>

				<div className="pl-2 w-50">
					<Select
						label={Liferay.Language.get('decimal-separator')}
						name="decimalSymbol"
						onBlur={onBlur}
						onChange={(event: any, value: any) => {
							handleChange('symbols', {
								decimalSymbol: value[0],
								thousandsSeparator: thousandsSeparator?.includes(
									'none'
								)
									? 'none'
									: thousandsSeparator?.[0],
							});

							setDecimalSymbol(value[0]);
						}}
						onFocus={onFocus}
						options={decimalSymbols}
						placeholder={Liferay.Language.get('choose-an-option')}
						readOnly={readOnly}
						showEmptyOption={false}
						value={decimalSymbol}
						visible={visible}
					/>
				</div>
			</div>
			{visible && (
				<div>
					<div className="form-group">
						<label htmlFor="decimal_places ">
							{Liferay.Language.get('decimal-places')}
						</label>

						<ClayInput
							className="ddm-field-text"
							disabled={readOnly}
							id="decimal_places"
							max={MAX_DECIMAL_PLACES}
							min={MIN_DECIMAL_PLACES}
							name="decimal_places"
							onBlur={(event: any) => {
								let {value: newValue} = event.target;

								newValue = limitValue({
									defaultValue: DEFAULT_DECIMAL_PLACES,
									max: MAX_DECIMAL_PLACES,
									min: MIN_DECIMAL_PLACES,
									value: newValue,
								});

								setDecimalPlaces(newValue);
								handleChange(
									'decimalPlaces',
									parseInt(newValue, 10)
								);
							}}
							onChange={(event: any) => {
								let {value: newValue} = event.target;
								newValue = newValue.includes('-')
									? newValue.replace('-', '')
									: newValue;

								if (newValue <= MAX_DECIMAL_PLACES) {
									newValue =
										newValue === 0
											? ''
											: parseInt(newValue, 10);

									setDecimalPlaces(newValue);

									handleChange('decimalPlaces', newValue);
								}
							}}
							type="number"
							value={decimalPlaces}
						/>
					</div>
				</div>
			)}
			<Text
				label={Liferay.Language.get('prefix-or-suffix')}
				locale={editingLanguageId}
				maxLength={10}
				name="append"
				onBlur={onBlur}
				onChange={(event: any) => {
					handleChange('append', event.target.value);

					setAppend(event.target.value);
				}}
				onFocus={onFocus}
				placeholder={Liferay.Language.get(
					'input-mask-append-placeholder'
				)}
				readOnly={readOnly}
				required={false}
				tip={Liferay.Language.get(
					'the-maximum-length-is-10-characters'
				)}
				value={append}
				visible={visible}
			/>
			{append !== '' && (
				<Radio
					editingLanguageId={editingLanguageId}
					inline={false}
					name="appendType"
					onBlur={onBlur}
					onChange={(event: any) => {
						handleChange('appendType', event.target.value);

						setAppendType(event.target.value);
					}}
					onFocus={onFocus}
					options={[
						{
							label: Liferay.Language.get('prefix'),
							value: 'prefix',
						},
						{
							label: Liferay.Language.get('suffix'),
							value: 'suffix',
						},
					]}
					readOnly={readOnly}
					value={appendType}
					visible={visible}
				/>
			)}
		</>
	);
};

export default NumericInputMask;
