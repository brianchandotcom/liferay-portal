/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
type ConfigurationSection =
	| 'Background'
	| 'Borders'
	| 'CSS'
	| 'Effects'
	| 'Frame'
	| 'Hide from Site Search Results'
	| 'Options'
	| 'Spacing'
	| 'Text';

type EditableConfigurationTab = 'Image Source' | 'Link' | 'Mapping';

type FragmentConfigurationTab = 'Advanced' | 'General' | 'Styles';

type ConfigurationTab = EditableConfigurationTab | FragmentConfigurationTab;

type SidebarTab =
	| 'Browser'
	| 'Comments'
	| 'Fragments and Widgets'
	| 'Page Content'
	| 'Page Design Options'
	| 'Page Rules'
	| 'Widgets';

type Viewport = 'Desktop' | 'Landscape Phone' | 'Portrait Phone' | 'Tablet';
