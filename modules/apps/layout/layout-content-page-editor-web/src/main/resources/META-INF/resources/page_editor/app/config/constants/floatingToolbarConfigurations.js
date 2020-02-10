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

import {ContainerConfigurationPanel} from '../../../app/components/floating-toolbar/ContainerConfigurationPanel';
import {FragmentConfigurationPanel} from '../../../app/components/floating-toolbar/FragmentConfigurationPanel';
import {ImagePropertiesPanel} from '../../../app/components/floating-toolbar/ImagePropertiesPanel';
import LinkPanel from '../../../app/components/floating-toolbar/LinkPanel';
import {MappingPanel} from '../../../app/components/floating-toolbar/MappingPanel';
import {RowConfigurationPanel} from '../../../app/components/floating-toolbar/RowConfigurationPanel';
import {EDITABLE_FLOATING_TOOLBAR_BUTTONS} from './editableFloatingToolbarButtons';
import {LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS} from './layoutDataFloatingToolbarButtons';

const {
	containerConfiguration,
	fragmentConfiguration,
	rowConfiguration
} = LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS;

const {imageProperties, link, map} = EDITABLE_FLOATING_TOOLBAR_BUTTONS;

export const FLOATING_TOOLBAR_CONFIGURATIONS = {
	[containerConfiguration.panelId]: ContainerConfigurationPanel,
	[fragmentConfiguration.panelId]: FragmentConfigurationPanel,
	[imageProperties.panelId]: ImagePropertiesPanel,
	[link.panelId]: LinkPanel,
	[map.panelId]: MappingPanel,
	[rowConfiguration.panelId]: RowConfigurationPanel
};
