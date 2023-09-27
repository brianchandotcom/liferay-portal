/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {Field} from '../color_picker/ColorPicker';
import './LengthInput.scss';
declare const UNITS: readonly ['px', '%', 'em', 'rem', 'vw', 'vh', 'custom'];
declare type Unit = typeof UNITS[number];
interface Props {
	className?: string;
	defaultUnit?: Unit;
	field: Field;
	onEnter?: () => {};
	onValueSelect: (fieldName: string, value: string) => void;
	showLabel?: boolean;
	value?: string;
}
export default function LengthInput({
	className,
	defaultUnit,
	field,
	onEnter,
	onValueSelect,
	showLabel,
	value,
}: Props): JSX.Element;
export {};
