/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Walkthrough from '@liferay/frontend-js-walkthrough-web/components/Walkthrough';
import React from 'react';

const WALKTHROUGH_CONFIG = {
	closeOnClickOutside: false,
	closeable: true,
	pages: {
		[themeDisplay.getLayoutRelativeURL() +
		location.search +
		location.hash]: ['step-1', 'step-2', 'step-3', 'step-4', 'step-5'],
	},
	skippable: true,
	steps: [
		{
			content: '<span>Content 1</span><br/><code>Hello1</code>',
			darkbg: true,
			id: 'step-1',
			nodeToHighlight: '#step1',
			title: 'Title 1',
		},
		{
			content: '<span>Content 2</span><br/><code>Hello2</code>',
			darkbg: true,
			id: 'step-2',
			nodeToHighlight: '#step2',
			positioning: 'top',
			title: 'Title 2',
		},
		{
			content: '<span>Content 3</span><br/><code>Hello3</code>',
			darkbg: true,
			id: 'step-3',
			nodeToHighlight: '#step3',
			title: 'Title 3',
		},
		{
			content: '<span>Content 4</span><br/><code>Hello4</code>',
			id: 'step-4',
			nodeToHighlight: '#step4',
			title: 'Title 4',
		},
		{
			content: '<span>Content 5</span><br/><code>Hello5</code>',
			id: 'step-5',
			nodeToHighlight: '#step5',
			positioning: 'bottom',
			title: 'Title 5',
		},
	],
};

export default function SampleWalkthrough() {
	return <Walkthrough {...WALKTHROUGH_CONFIG} />;
}
