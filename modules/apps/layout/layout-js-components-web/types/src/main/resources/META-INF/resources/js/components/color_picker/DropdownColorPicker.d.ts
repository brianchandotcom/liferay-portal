/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Dispatch, SetStateAction} from 'react';
export interface Color {
	disabled?: boolean;
	label: string;
	name: string;
	value: string;
}
export declare type ColorCategory = Record<string, Color[]>;
export declare type ColorCategoryMap = Record<string, ColorCategory>;
declare type Props = {
	active: boolean;
	colors: ColorCategoryMap;
	fieldLabel?: string | null;
	inherited?: boolean;
	label?: string;
	onSetActive: Dispatch<SetStateAction<boolean>>;
	onValueChange?: (color: Omit<Color, 'disabled'>) => void;
	showSelector?: boolean;
	small?: boolean;
	value?: string;
};
export declare function DropdownColorPicker({
	active,
	colors,
	fieldLabel,
	inherited,
	label,
	onSetActive,
	onValueChange,
	showSelector,
	small,
	value,
}: Props): JSX.Element;
export {};
