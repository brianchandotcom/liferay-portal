/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayColorPicker, {useColorPicker} from '@clayui/color-picker';
import ClayDropDown from '@clayui/drop-down';
import {FocusScope, InternalDispatch} from '@clayui/shared';
import React, {useRef, useState} from 'react';

interface Props
	extends Omit<React.InputHTMLAttributes<HTMLInputElement>, 'onChange'> {
	active: boolean;
	colors: string[];
	disabled?: boolean;
	name?: string;
	onActiveChange: InternalDispatch<boolean>;
	onBlurInput: (event: React.FocusEvent<HTMLInputElement>) => void;
	onChange: (event: string) => void;
	onColorChangeEditor: (value: string) => void;
	onColorsChange: (value: Array<string>) => void;
	value: string;
}

export default function ColorPickerField({
	active,
	colors,
	disabled,
	name,
	onActiveChange,
	onBlurInput,
	onChange,
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
						tab === 'custom' ? (
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
								onHexChange={(hex: string) => dispatch({hex})}
								onHueChange={(hue: number) => dispatch({hue})}
							/>
						) : null
					) : null}
				</ClayDropDown.Menu>
			</div>
		</FocusScope>
	);
}
