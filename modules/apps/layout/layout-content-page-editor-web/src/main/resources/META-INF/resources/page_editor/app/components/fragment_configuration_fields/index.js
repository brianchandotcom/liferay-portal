/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LengthField} from '../../../common/components/LengthField';
import {ButtonGroupField} from './ButtonGroupField';
import CSSClassSelectorField from './CSSClassSelectorField';
import {CategoryTreeNodeSelectorField} from './CategoryTreeNodeSelectorField';
import {CheckboxField} from './CheckboxField';
import {CollectionSelectorField} from './CollectionSelectorField';
import {ColorPaletteField} from './ColorPaletteField';
import {ColorPickerField} from './ColorPickerField';
import CustomCSSField from './CustomCSSField';
import {HideFragmentField} from './HideFragmentField';
import {ImageSelectorField} from './ImageSelectorField';
import {ItemSelectorField} from './ItemSelectorField';
import {NavigationMenuSelectorField} from './NavigationMenuSelectorField';
import {SelectField} from './SelectField';
import {SpacingBoxField} from './SpacingBoxField';
import {TextField} from './TextField';
import URLField from './URLField';
import {VideoSelectorField} from './VideoSelectorField';

export const FRAGMENT_CONFIGURATION_FIELDS = {
	buttonGroup: ButtonGroupField,
	categoryTreeNodeSelector: CategoryTreeNodeSelectorField,
	checkbox: CheckboxField,
	collectionSelector: CollectionSelectorField,
	colorPalette: ColorPaletteField,
	colorPicker: ColorPickerField,
	cssClassSelector: CSSClassSelectorField,
	customCSS: CustomCSSField,
	hideFragment: HideFragmentField,
	imageSelector: ImageSelectorField,
	itemSelector: ItemSelectorField,
	length: LengthField,
	navigationMenuSelector: NavigationMenuSelectorField,
	select: SelectField,
	spacing: SpacingBoxField,
	text: TextField,
	url: URLField,
	videoSelector: VideoSelectorField,
};
