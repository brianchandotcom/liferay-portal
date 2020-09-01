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

import ClayIcon from '@clayui/icon';
import ClayPopover from '@clayui/popover';
import React, {useState} from 'react';

import {sub} from '../../../../utils/lang.es';

const translations = {
	'app-builder': Liferay.Language.get('app-builder'),
	'fragments-and-widgets': Liferay.Language.get('fragments-and-widgets'),
	widget: Liferay.Language.get('widget'),
};

export default () => {
	const [isVisible, setVisible] = useState(false);

	return (
		<ClayPopover
			alignPosition="right"
			disableScroll
			header={Liferay.Language.get('add-apps-as-widgets')}
			show={isVisible}
			style={{maxWidth: 362}}
			trigger={
				<ClayIcon
					color="#A7A9BC"
					onMouseOut={() => setVisible(false)}
					onMouseOver={() => setVisible(true)}
					symbol="question-circle-full"
				/>
			}
		>
			<p>
				{sub(
					Liferay.Language.get(
						'when-editing-a-site-page-in-the-right-sidebar-go-to-x-and-then-open-the-x-tab-you-will-find-your-deployed-apps-under-the-x-section'
					),
					[
						<b key={0}>{translations['fragments-and-widgets']}</b>,
						<b key={1}>{translations.widget}</b>,
						<b key={2}>{translations['app-builder']}</b>,
					],
					false
				)}
			</p>
			<img
				alt={translations['fragments-and-widgets']}
				src={`${themeDisplay.getPathThemeImages()}/app_builder/fragment-and-widgets.png`}
				width={336}
			/>
		</ClayPopover>
	);
};
