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

import {BackgroundColorConfigurationPanel} from '../../../app/components/BackgroundColorConfigurationPanel';
import {FragmentConfigurationPanel} from '../../../app/components/FragmentConfigurationPanel';
import {LayoutBackgroundImageConfigurationPanel} from '../../../app/components/LayoutBackgroundImageConfigurationPanel';
import {SpacingConfigurationPanel} from '../../../app/components/SpacingConfigurationPanel';
import {LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS} from './layoutDataFloatingToolbarButtons';

const {
	backgroundColor,
	fragmentConfiguration,
	layoutBackgroundImage,
	spacing
} = LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS;

export const FLOATING_TOOLBAR_CONFIGURATIONS = {
	[backgroundColor.panelId]: BackgroundColorConfigurationPanel,
	[fragmentConfiguration.panelId]: FragmentConfigurationPanel,
	[layoutBackgroundImage.panelId]: LayoutBackgroundImageConfigurationPanel,
	[spacing.panelId]: SpacingConfigurationPanel
};
