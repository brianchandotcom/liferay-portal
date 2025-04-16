/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClaySticker from '@clayui/sticker';
import React from 'react';
import {createRoot} from 'react-dom/client';

/* eslint-disable no-undef */

const root = createRoot(fragmentElement.querySelector('.space-list-name'));

root.render(
	React.createElement(
		React.Fragment,
		null,
		React.createElement(
			ClaySticker,
			{
				displayType:
					'outline-' + (Liferay.ThemeDisplay.getUserId() % 10),
				size: 'sm',
			},
			configuration.spaceName.charAt(0).toUpperCase()
		),
		React.createElement(
			'span',
			{className: 'ml-2'},
			configuration.spaceName
		)
	)
);
