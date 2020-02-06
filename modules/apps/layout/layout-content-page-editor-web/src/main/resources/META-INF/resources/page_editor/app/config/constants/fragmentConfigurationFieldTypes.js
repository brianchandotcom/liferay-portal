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

import {CheckboxField} from '../../components/floating-toolbar/CheckboxField';
import {ColorPaletteField} from '../../components/floating-toolbar/ColorPaletteField';
import {ItemSelectorField} from '../../components/floating-toolbar/ItemSelectorField';
import {SelectField} from '../../components/floating-toolbar/SelectField';
import {TextField} from '../../components/floating-toolbar/TextField';

export const FRAGMENT_CONFIGURATION_FIELD_TYPES = {
	checkbox: CheckboxField,
	colorPalette: ColorPaletteField,
	itemSelector: ItemSelectorField,
	select: SelectField,
	text: TextField
};
