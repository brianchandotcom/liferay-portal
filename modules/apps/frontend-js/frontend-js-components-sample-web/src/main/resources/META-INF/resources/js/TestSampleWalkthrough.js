/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Walkthrough from '@liferay/frontend-js-walkthrough-web/components/Walkthrough';
import React from 'react';

const TEST_WALKTHROUGH_CONFIG = {
	closeOnClickOutside: false,
	closeable: true,
	pages: {
		[themeDisplay.getLayoutRelativeURL() +
		location.search +
		location.hash]: ['teststep1', 'teststep2'],
	},
	skippable: true,
	steps: [
		{
			content: '<span>Content 1</span><br/><code>Hello1</code>',
			darkbg: true,
			id: 'teststep1',
			nodeToHighlight: '#teststep1',
			title: 'Title 1',
		},
		{
			content: '<span>Content 2</span><br/><code>Hello2</code>',
			id: 'teststep2',
			nodeToHighlight: '#teststep2',
			title: 'Title 2',
		},
	],
};

export default function TestSampleWalkthrough(...props) {
	return <Walkthrough {...TEST_WALKTHROUGH_CONFIG} {...props} />;
}
