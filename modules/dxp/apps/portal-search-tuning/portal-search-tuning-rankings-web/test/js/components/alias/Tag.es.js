/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import React from 'react';
import Tag from '../../../../src/main/resources/META-INF/resources/js/components/alias/Tag.es';
import {fireEvent, render} from '@testing-library/react';

describe('Tag', () => {
	it('has a corresponding label', () => {
		const {container} = render(
			<Tag label="one" onClickDelete={jest.fn()} />
		);

		const tag = container.querySelector('.label-item-expand');

		expect(tag).toHaveTextContent('one');
	});

	it('calls the onClickDelete function when it gets clicked on', () => {
		const onClickDelete = jest.fn();

		const {container} = render(
			<Tag label="one" onClickDelete={onClickDelete} />
		);

		fireEvent.click(container.querySelector('button.close'));

		expect(onClickDelete.mock.calls.length).toBe(1);
	});
});
