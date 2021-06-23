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

import React from 'react';

import modalLauncher from '../../../src/main/resources/META-INF/resources/components/modal/entry';
import {OPEN_MODAL} from '../../../src/main/resources/META-INF/resources/utilities/eventsDefinitions';
import launcher from '../../../src/main/resources/META-INF/resources/utilities/launcher';

const props = {
	actions: [
		{
			definition: 'save',
		},
	],
	closeOnSubmit: true,
	id: 'test-modal-id',
	showCancel: true,
	size: 'full-screen',
	spritemap: './assets/clay/icons.svg',
	submitLabel: 'Create',
	title: 'Title',
	url: 'http://localhost:9000/modal-content.html',
};

modalLauncher('modal', 'modal-root', props);

launcher(
	() => (
		<button
			className="btn btn-primary"
			onClick={() => Liferay.fire(OPEN_MODAL, {id: 'test-modal-id'})}
		>
			Open modal
		</button>
	),
	'modal-trigger',
	'modal-trigger-root'
);
