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

import {ContainerConfigurationPanel} from '../../../app/components/ContainerConfigurationPanel';
import {FragmentConfigurationPanel} from '../../../app/components/FragmentConfigurationPanel';
import LinkConfigurationPanel from '../../../app/components/LinkConfigurationPanel';
import {MapConfigurationPanel} from '../../../app/components/MapConfigurationPanel';
import {RowConfigurationPanel} from '../../../app/components/RowConfigurationPanel';
import {EDITABLE_FLOATING_TOOLBAR_BUTTONS} from './editableFloatingToolbarButtons';
import {LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS} from './layoutDataFloatingToolbarButtons';

const {
	containerConfiguration,
	fragmentConfiguration,
	rowConfiguration
} = LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS;

const {link, map} = EDITABLE_FLOATING_TOOLBAR_BUTTONS;

export const FLOATING_TOOLBAR_CONFIGURATIONS = {
	[containerConfiguration.panelId]: ContainerConfigurationPanel,
	[fragmentConfiguration.panelId]: FragmentConfigurationPanel,
	[link.panelId]: LinkConfigurationPanel,
	[map.panelId]: MapConfigurationPanel,
	[rowConfiguration.panelId]: RowConfigurationPanel
};
