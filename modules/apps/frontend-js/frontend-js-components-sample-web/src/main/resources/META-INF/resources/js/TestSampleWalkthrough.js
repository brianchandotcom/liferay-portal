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
