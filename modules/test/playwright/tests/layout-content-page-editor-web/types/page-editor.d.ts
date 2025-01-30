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

type EditableConfigurationTab = 'Action' | 'Image Source' | 'Link' | 'Mapping';

type FragmentConfigurationTab = 'Advanced' | 'General' | 'Styles';

type PageDesignOptionsTab = 'Style Book';

type ConfigurationTab =
	| EditableConfigurationTab
	| FragmentConfigurationTab
	| PageDesignOptionsTab;

type SidebarTab =
	| 'Browser'
	| 'Comments'
	| 'Components'
	| 'Page Content'
	| 'Page Design Options'
	| 'Page Rules'
	| 'Widgets';

type Viewport = 'Desktop' | 'Landscape Phone' | 'Portrait Phone' | 'Tablet';
