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

import {fireEvent, render} from '@testing-library/react';
import React from 'react';

import PageToolbar from '../../../src/main/resources/META-INF/resources/js/components/PageToolbar.es';

import '@testing-library/jest-dom/extend-expect';

describe('PageToolbar', () => {
	it('disables the save button', () => {
		const {getByText} = render(
			<PageToolbar
				active={true}
				onCancel={'cancel'}
				onChangeActive={jest.fn()}
				onPublish={jest.fn()}
				submitDisabled={true}
			/>
		);

		expect(getByText('save')).toBeDisabled();
	});

	it('enables the save button', () => {
		const {getByText} = render(
			<PageToolbar
				active={true}
				onCancel={'cancel'}
				onChangeActive={jest.fn()}
				onPublish={jest.fn()}
				submitDisabled={false}
			/>
		);

		expect(getByText('save')).toBeEnabled();
	});

	it('shows the active state', () => {
		const {getByLabelText} = render(
			<PageToolbar
				active={true}
				onCancel={'cancel'}
				onChangeActive={jest.fn()}
				onPublish={jest.fn()}
				submitDisabled={false}
			/>
		);

		expect(getByLabelText('active')).toHaveAttribute('checked');
	});

	it('shows the inactive state', () => {
		const {getByLabelText} = render(
			<PageToolbar
				active={false}
				onCancel={'cancel'}
				onChangeActive={jest.fn()}
				onPublish={jest.fn()}
				submitDisabled={false}
			/>
		);

		expect(getByLabelText('inactive')).not.toHaveAttribute('checked');
	});

	it('calls the onChangeActive function', () => {
		const onChangeActive = jest.fn();

		const {getByLabelText} = render(
			<PageToolbar
				active={true}
				onCancel={'cancel'}
				onChangeActive={onChangeActive}
				onPublish={jest.fn()}
				submitDisabled={false}
			/>
		);

		fireEvent.click(getByLabelText('active'));

		expect(onChangeActive.mock.calls.length).toBe(1);
	});
});
