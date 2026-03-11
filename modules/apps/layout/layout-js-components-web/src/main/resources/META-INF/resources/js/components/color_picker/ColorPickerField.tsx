/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayColorPicker, {useColorPicker} from '@clayui/color-picker';
import ClayDropDown from '@clayui/drop-down';
import {FocusScope, InternalDispatch} from '@clayui/shared';
import classNames from 'classnames';
import React, {useEffect, useMemo, useRef, useState} from 'react';

import ColorPalette from './ColorPalette';
import {Color, ColorCategoryMap} from './DropdownColorPicker';

interface Props
	extends Omit<React.InputHTMLAttributes<HTMLInputElement>, 'onChange'> {
	active: boolean;
	colors: string[];
	colorsFromStylebook: ColorCategoryMap;
	disabled?: boolean;
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
	value: string;
}

export default function ColorPickerField({
	active,
	colors,
	colorsFromStylebook,
	disabled,
	name,
	onActiveChange,
	onBlurInput,
	onChange,
	onClickColorPalette,
	onColorChangeEditor: externalOnColorChangeEditor,
	onColorsChange,
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
		onClickSplotch,
		onColorChangeEditor,
		onHexChange,
		setInternalActive,
		setValue,
		state,
		valueInputRef,
	} = useColorPicker({
		active,
		colors,
		onActiveChange,
		onChange,
		onColorsChange,
		value,
	});

	const [tab, setTab] = useState<'custom' | 'values'>('custom');

	const dropdownContainerRef = useRef<HTMLDivElement>(null);
	const splotchRef = useRef<HTMLButtonElement>(null);
	const triggerElementRef = useRef<HTMLDivElement>(null);

	return (
		<FocusScope arrowKeysUpDown={false}>
			<div className="clay-color-picker">
				<ClayColorPicker.Field
					disabled={disabled}
					name={name}
					onClickSplotch={onClickSplotch}
					onHexBlur={onBlurInput}
					onHexChange={onHexChange}
					setValue={setValue}
					small
					splotchRef={splotchRef}
					triggerElementRef={triggerElementRef}
					value={internalValue}
					valueInputRef={valueInputRef}
					{...otherProps}
				/>

				<ClayDropDown.Menu
					active={internalActive}
					alignElementRef={triggerElementRef}
					className="clay-color-dropdown-menu"
					containerProps={{
						className: 'cadmin',
					}}
					deps={[internalActive]}
					onActiveChange={setInternalActive}
					ref={dropdownContainerRef}
					triggerRef={splotchRef}
				>
					<ClayButton.Group className="c-mb-3">
						<ClayButton
							displayType="secondary"
							onClick={() => setTab('custom')}
							size="xs"
						>
							{Liferay.Language.get('custom')}
						</ClayButton>

						<ClayButton
							displayType="secondary"
							onClick={() => setTab('values')}
							size="xs"
						>
							{Liferay.Language.get('value-from-stylebook')}
						</ClayButton>
					</ClayButton.Group>

					{customEditorActive ? (
						<>
							<div
								className={classNames('c-px-3', {
									'd-none': tab !== 'custom',
								})}
							>
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
							</div>
							<div
								className={classNames({
									'd-none': tab !== 'values',
								})}
							>
								<ColorPaletteTab
									active={active}
									colors={colorsFromStylebook}
									onActiveChange={onActiveChange}
									onValueChange={onClickColorPalette}
								/>
							</div>
						</>
					) : null}
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
