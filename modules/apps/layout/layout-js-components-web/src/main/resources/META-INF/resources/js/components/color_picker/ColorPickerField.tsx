/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayColorPicker, {useColorPicker} from '@clayui/color-picker';
import {FocusTrap} from '@clayui/core';
import ClayDropDown from '@clayui/drop-down';
import {FocusScope, InternalDispatch} from '@clayui/shared';
import {usePrevious} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import React, {useEffect, useMemo, useRef, useState} from 'react';

import {Color, ColorCategoryMap, Field} from '../../types/ColorPicker';
import ColorPalette from './ColorPalette';
import TokenButton from './TokenButton';

interface Props
	extends Omit<React.InputHTMLAttributes<HTMLInputElement>, 'onChange'> {
	active: boolean;
	colors: string[];
	colorsFromStylebook: ColorCategoryMap;
	disabled?: boolean;
	field: Field;
	name?: string;
	onActiveChange: InternalDispatch<boolean>;
	onBlurInput: (event: React.FocusEvent<HTMLInputElement>) => void;
	onChange: (event: string) => void;
	onClickColorPalette: ({
		label,
		name,
		value,
	}: {
		label: string;
		name: string;
		value: string;
	}) => void;
	onColorChangeEditor: (value: string) => void;
	onColorsChange: (value: Array<string>) => void;
	tokenLabel: string | null;
	tokenValue: string;
	value: string;
}

export default function ColorPickerField({
	active,
	colors,
	colorsFromStylebook,
	disabled,
	field,
	name,
	onActiveChange,
	onBlurInput,
	onChange,
	onClickColorPalette,
	onColorChangeEditor: externalOnColorChangeEditor,
	onColorsChange,
	tokenLabel,
	tokenValue,
	value,
	...otherProps
}: Props) {
	const {
		color,
		customColors,
		customEditorActive,
		dispatch,
		internalActive,
		internalToHex,
		internalValue,
		onChangeEditor,
		onColorChangeEditor,
		onHexChange,
		onSplotchClick,
		setInternalActive,
		setValue,
		state,
		valueInputRef,
	} = useColorPicker({
		active,
		colors,
		externalOnBlur: otherProps.onBlur,
		onActiveChange,
		onChange,
		onColorsChange,
		value,
	});

	const [tab, setTab] = useState<'custom' | 'values'>('custom');

	const dropdownContainerRef = useRef<HTMLDivElement>(null);
	const triggerElementRef = useRef<HTMLDivElement>(null);
	const triggerRef = useRef<HTMLButtonElement>(null);

	const previousInternalActive = usePrevious(internalActive);

	useEffect(() => {
		if (previousInternalActive && !internalActive) {
			triggerRef.current?.focus();
		}
	}, [internalActive, previousInternalActive]);

	return (
		<FocusScope arrowKeysUpDown={false}>
			<div className="clay-color-picker w-100" ref={triggerElementRef}>
				{tokenLabel ? (
					<TokenButton
						inherited={!value && field.inherited}
						label={tokenLabel}
						onClick={onSplotchClick}
						small
						triggerRef={triggerRef}
						value={tokenValue}
					/>
				) : (
					<ClayColorPicker.Field
						ariaLabels={{
							selectColor: Liferay.Language.get('select-a-color'),
							selectionIs: sub(
								Liferay.Language.get('color-selection-is-x'),
								color
							),
						}}
						disabled={disabled}
						name={name}
						onHexBlur={onBlurInput}
						onHexChange={onHexChange}
						onSplotchClick={onSplotchClick}
						setValue={setValue}
						small
						splotchRef={triggerRef}
						value={internalValue}
						valueInputRef={valueInputRef}
						{...otherProps}
					/>
				)}

				<ClayDropDown.Menu
					active={internalActive}
					alignElementRef={triggerElementRef}
					className="clay-color-dropdown-menu"
					containerProps={{
						className: 'cadmin layout__color-picker__dropdown',
					}}
					deps={[internalActive]}
					onActiveChange={setInternalActive}
					ref={dropdownContainerRef}
					triggerRef={triggerRef}
				>
					<FocusTrap active={internalActive} stopPropagation>
						<ClayButton.Group
							className="c-mb-3 c-px-3"
							role="tablist"
						>
							<ClayButton
								aria-controls="tabpanel1"
								aria-selected={tab === 'custom'}
								className={classNames({
									active: tab === 'custom',
								})}
								displayType="secondary"
								onClick={() => setTab('custom')}
								role="tab"
								size="xs"
							>
								{Liferay.Language.get('custom')}
							</ClayButton>

							<ClayButton
								aria-controls="tabpanel2"
								aria-selected={tab === 'values'}
								className={classNames({
									active: tab === 'values',
								})}
								displayType="secondary"
								onClick={() => setTab('values')}
								role="tab"
								size="xs"
							>
								{Liferay.Language.get('value-from-stylebook')}
							</ClayButton>
						</ClayButton.Group>

						<div
							className={classNames('c-px-3', {
								'd-none': tab !== 'custom',
							})}
							id="tabpanel1"
							role="tabpanel"
						>
							{customEditorActive ? (
								<ClayColorPicker.Editor
									color={color}
									colors={customColors}
									hex={state.hex}
									hue={state.hue}
									internalToHex={internalToHex}
									onChange={onChangeEditor}
									onColorChange={(color) => {
										onColorChangeEditor(color);
										externalOnColorChangeEditor(
											color.toHexString().toUpperCase()
										);
									}}
									onHexBlur={externalOnColorChangeEditor}
									onHexChange={(hex: string) =>
										dispatch({hex})
									}
									onHueChange={(hue: number) =>
										dispatch({hue})
									}
								/>
							) : null}
						</div>

						<div
							className={classNames({
								'd-none': tab !== 'values',
							})}
							id="tabpanel2"
							role="tabpanel"
						>
							<ColorPaletteTab
								active={active}
								colors={colorsFromStylebook}
								onActiveChange={onActiveChange}
								onValueChange={onClickColorPalette}
							/>
						</div>
					</FocusTrap>
				</ClayDropDown.Menu>
			</div>
		</FocusScope>
	);
}

type ColorPaletteTabProps = {
	active: boolean;
	colors: ColorCategoryMap;
	onActiveChange: InternalDispatch<boolean>;
	onValueChange?: (color: Omit<Color, 'disabled'>) => void;
};

function ColorPaletteTab({
	active,
	colors,
	onActiveChange,
	onValueChange,
}: ColorPaletteTabProps) {
	const [searchValue, setSearchValue] = useState('');

	const filteredColors = useMemo<ColorCategoryMap>(() => {
		if (!searchValue) {
			return colors;
		}

		const lowerCaseSearchValue = searchValue.toLowerCase();

		const isFoundValue = (value: string) =>
			value.toLowerCase().includes(lowerCaseSearchValue);

		return Object.entries(colors).reduce((acc, [category, tokenSets]) => {
			const newTokenSets = isFoundValue(category)
				? tokenSets
				: Object.entries(tokenSets).reduce(
						(acc, [tokenSet, tokenColors]) => {
							const newColors = isFoundValue(tokenSet)
								? tokenColors
								: tokenColors.filter(
										(color) =>
											isFoundValue(color.label) ||
											isFoundValue(color.value)
									);

							return {
								...acc,
								...(newColors.length && {
									[tokenSet]: newColors,
								}),
							};
						},
						{}
					);

			return {
				...acc,
				...(Object.keys(newTokenSets).length && {
					[category]: newTokenSets,
				}),
			};
		}, {});
	}, [colors, searchValue]);

	useEffect(() => {
		if (!active) {
			setSearchValue('');
		}
	}, [active]);

	return (
		<ColorPalette
			active={active}
			colors={filteredColors}
			onActiveChange={onActiveChange}
			onSetSearchValue={setSearchValue}
			onValueChange={onValueChange}
		/>
	);
}
