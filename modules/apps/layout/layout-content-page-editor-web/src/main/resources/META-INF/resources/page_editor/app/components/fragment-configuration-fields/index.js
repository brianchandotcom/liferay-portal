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

import {CheckboxField} from './CheckboxField';
import {CollectionSelectorField} from './CollectionSelectorField';
import {ColorPaletteField} from './ColorPaletteField';
import {ImageSelectorField} from './ImageSelectorField';
import {ItemSelectorField} from './ItemSelectorField';
import {SelectField} from './SelectField';
import {TextField} from './TextField';

export const FRAGMENT_CONFIGURATION_FIELDS = {
	checkbox: CheckboxField,
	collectionSelector: CollectionSelectorField,
	colorPalette: ColorPaletteField,
	imageSelector: ImageSelectorField,
	itemSelector: ItemSelectorField,
	select: SelectField,
	text: TextField,
};
