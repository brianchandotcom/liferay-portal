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

import '@testing-library/jest-dom/extend-expect';
import {cleanup, render, waitForElement} from '@testing-library/react';
import React from 'react';
import {act} from 'react-dom/test-utils';

import Checkin from '../../../../src/main/resources/META-INF/resources/document_library/js/checkin/Checkin.es';

const bridgeComponentId = 'bridgeComponentId';
const dlVersionNumberIncreaseValues = {
	MAJOR: 'MAJOR',
	MINOR: 'MINOR',
	NONE: 'NONE'
};

function _renderCheckinComponent({checkedOut = true} = {}) {
	return render(
		<Checkin
			bridgeComponentId={bridgeComponentId}
			checkedOut={checkedOut}
			dlVersionNumberIncreaseValues={dlVersionNumberIncreaseValues}
		/>,
		{
			baseElement: document.body
		}
	);
}

describe('Checkin', () => {
	const components = {};
	Liferay.component = (id, component) => {
		components[id] = component;
	};
	Liferay.componentReady = id => Promise.resolve(components[id]);
	afterEach(cleanup);

	it('register a bridge and get it via Liferay.component and render the form', async () => {
		const {getByRole} = _renderCheckinComponent();

		await act(async () => {
			Liferay.componentReady(bridgeComponentId).then(({open}) => open());

			const form = await waitForElement(() => getByRole('form'));
			expect(form);
		});
	});
});
