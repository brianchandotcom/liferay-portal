/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDropDown from '@clayui/drop-down';
import {ClayInput} from '@clayui/form';
import {FocusScope, InternalDispatch, sub} from '@clayui/shared';
import React, {useRef} from 'react';
import tinycolor from 'tinycolor2';

import Basic from './Basic';
import Custom from './Custom';
import {Editor} from './Editor';
import Splotch from './Splotch';
import useColorPicker from './useColorPicker';

const DEFAULT_COLORS = [
	'000000',
	'5F5F5F',
	'9A9A9A',
	'CBCBCB',
	'E1E1E1',
	'FFFFFF',
	'FF0D0D',
	'FF8A1C',
	'2BA676',
	'006EF8',
	'7F26FF',
	'FF21A0',
	'FF5F5F',
	'FFB46E',
	'50D2A0',
	'4B9BFF',
	'AF78FF',
	'FF73C3',
	'FFB1B1',
	'FFDEC0',
	'91E3C3',
	'9DC8FF',
	'DFCAFF',
	'FFC5E6',
	'FFD9D9',
	'FFF3E8',
	'B1EBD5',
	'C5DFFF',
	'F8F2FF',
	'FFEDF7',
];

const DEFAULT_ARIA_LABELS = {
	selectColor: 'Select a color',
	selectionIs: 'Color selection is {0}',
};

interface IProps
	extends Omit<React.InputHTMLAttributes<HTMLInputElement>, 'onChange'> {

	/**
	 * Property to define whether the DropDown menu is expanded or not
	 * (controlled).
	 */
	active?: boolean;

	/**
	 * Labels for the aria attributes
	 */
	ariaLabels?: {
		selectColor: string;
		selectionIs: string;
	};

	/**
	 * List of color hex values
	 */
	colors?: Array<string>;

	/**
	 * Property to set the default value of `active` (uncontrolled).
	 */
	defaultActive?: boolean;

	/**
	 * Property to set the default value (uncontrolled).
	 */
	defaultValue?: string;

	/**
	 * Flag for adding ColorPicker in disabled state
	 */
	disabled?: boolean;

	/**
	 * Props to add to the DropDown container.
	 */
	dropDownContainerProps?: React.ComponentProps<
		typeof ClayDropDown.Menu
	>['containerProps'];

	/**
	 * The label describing the collection of colors in the menu
	 */
	label?: string;

	/**
	 * Messages for the Color Picker.
	 */
	messages?: {
		close: string;
		customColor: string;
	};

	/**
	 * The input attribute for name
	 */
	name?: string;

	/**
	 * Callback function for when active state changes (controlled).
	 */
	onActiveChange?: InternalDispatch<boolean>;

	/**
	 * Callback that is called when the value changes (controlled).
	 */
	onChange?: InternalDispatch<string>;

	/**
	 * Callback for when the list of colors change
	 */
	onColorsChange?: (val: Array<string>) => void;

	/**
	 * Callback for when the selected color changes
	 * @deprecated since v3.51.0 - use `onChange` instead.
	 */
	onValueChange?: (val: string) => void;

	predefinedColors?: Array<string>;

	/**
	 * Determines if the hex input should render
	 */
	showHex?: boolean;

	/**
	 * Flag for showing and disabling the palette of colors.
	 * This defaults to true
	 */
	showPalette?: boolean;

	showPredefinedColorsWithCustom?: boolean;

	/**
	 * Flag to indicate if `input-group-sm` class should
	 * be applied to `ClayInput.Group`
	 */
	small?: boolean;

	/**
	 * The title of the Main Splotch component
	 */
	splotchTitle?: string;

	/**
	 * Path to the location of the spritemap resource.
	 */
	spritemap?: string;

	/**
	 * Title to describe the color picker form element
	 */
	title?: string;

	/**
	 * Determines if the native color picker should be used
	 */
	useNative?: boolean;

	/**
	 * The value property sets the current value (controlled).
	 */
	value?: string;
}

function ColorPicker({
	active,
	ariaLabels = DEFAULT_ARIA_LABELS,
	colors,
	defaultActive = false,
	defaultValue = 'FFFFFF',
	disabled,
	dropDownContainerProps,
	label,
	messages,
	name,
	onActiveChange,
	onChange,
	onColorsChange,
	onValueChange,
	predefinedColors,
	showHex = true,
	showPalette = true,
	showPredefinedColorsWithCustom = false,
	small,
	splotchTitle,
	spritemap,
	title,
	useNative = false,
	value,
	...otherProps
}: IProps) {
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
		onHexBlur,
		onHexChange,
		setCustomEditorActive,
		setInternalActive,
		setValue,
		splotchRef,
		state,
		valueInputRef,
	} = useColorPicker({
		active,
		colors,
		defaultActive,
		defaultValue,
		externalOnBlur: otherProps.onBlur,
		onActiveChange,
		onChange,
		onColorsChange,
		onValueChange,
		showPalette,
		useNative,
		value,
	});

	const isHex = tinycolor(internalValue).getFormat() === 'hex';

	const dropdownContainerRef = useRef<HTMLDivElement>(null);
	const inputRef = useRef<HTMLInputElement>(null);
	const triggerElementRef = useRef<HTMLDivElement>(null);

	return (
		<FocusScope arrowKeysUpDown={false}>
			<div className="clay-color-picker">
				{title && <label>{title}</label>}

				<ClayInput.Group
					className="clay-color"
					ref={triggerElementRef}
					small={small}
				>
					<ClayInput.GroupItem prepend={showHex} shrink>
						{name && (
							<input
								name={name}
								onChange={(event) =>
									useNative
										? setValue(event.target.value)
										: null
								}
								ref={valueInputRef}
								style={{
									height: 0,
									position: 'absolute',
									visibility: 'hidden',
									width: 0,
								}}
								tabIndex={-1}
								type={useNative ? 'color' : 'text'}
								value={
									internalValue
										? `${isHex ? '#' : ''}${internalValue}`
										: ''
								}
							/>
						)}

						<ClayInput.GroupText>
							<Splotch
								aria-label={ariaLabels.selectColor}
								className="dropdown-toggle"
								disabled={disabled}
								onClick={onClickSplotch}
								ref={splotchRef}
								title={splotchTitle}
								value={internalValue}
							/>
						</ClayInput.GroupText>
					</ClayInput.GroupItem>

					<ClayDropDown.Menu
						active={internalActive}
						alignElementRef={triggerElementRef}
						className="clay-color-dropdown-menu"
						containerProps={dropDownContainerProps}
						deps={[internalActive]}
						onActiveChange={setInternalActive}
						ref={dropdownContainerRef}
						triggerRef={splotchRef}
					>
						{(!onColorsChange ||
							(showPredefinedColorsWithCustom &&
								!customEditorActive)) && (
							<Basic
								colors={
									(showPredefinedColorsWithCustom
										? predefinedColors
										: colors) || DEFAULT_COLORS
								}
								label={label}
								onChange={(newVal) => {
									setValue(newVal);

									setInternalActive(!internalActive);

									if (splotchRef.current) {
										splotchRef.current.focus();
									}
								}}
							/>
						)}

						{onColorsChange && (
							<Custom
								color={color}
								colors={customColors}
								editorActive={customEditorActive}
								label={label}
								messages={messages}
								onChange={(color, hex) => {
									dispatch({
										hex: internalToHex(color),
										hue: color.toHsv().h,
									});

									setValue(hex);
								}}
								onColorsChange={(hex, index) => {
									const newColors = [...customColors];

									newColors[index] = hex;

									onColorsChange(newColors);
								}}
								onEditorActiveChange={setCustomEditorActive}
								onSplotchChange={(splotch) =>
									dispatch({splotch})
								}
								showPalette={showPalette}
								splotch={state.splotch}
								spritemap={spritemap}
							/>
						)}

						{onColorsChange && customEditorActive && (
							<Editor
								color={color}
								colors={customColors}
								hex={state.hex}
								hue={state.hue}
								internalToHex={internalToHex}
								onChange={onChangeEditor}
								onColorChange={onColorChangeEditor}
								onHexChange={(hex) => dispatch({hex})}
								onHueChange={(hue) => dispatch({hue})}
							/>
						)}
					</ClayDropDown.Menu>

					{showHex && (
						<ClayInput.GroupItem
							append
							className="input-group-item-focusable"
						>
							<ClayInput
								{...otherProps}
								aria-label={sub(ariaLabels.selectionIs, [
									internalValue,
								])}
								disabled={disabled}
								insetBefore
								onBlur={onHexBlur}
								onChange={onHexChange}
								ref={inputRef}
								type="text"
								value={internalValue}
							/>

							<ClayInput.GroupInsetItem before tag="label">
								{isHex ? '#' : ''}
							</ClayInput.GroupInsetItem>
						</ClayInput.GroupItem>
					)}
				</ClayInput.Group>
			</div>
		</FocusScope>
	);
}

export default ColorPicker;
