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

import ErrorListItem from '../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/shared/ErrorListItem';

describe('ErrorListItem', () => {
	it('does not have an expand icon if there are no properties to display', () => {
		const {queryByLabelText} = render(
			<ErrorListItem
				error={{
					msg: 'Test message',
				}}
			/>
		);

		expect(queryByLabelText('expand')).toBeNull();
	});

	it('does not render empty properties', () => {
		const {getByLabelText, queryByText} = render(
			<ErrorListItem
				error={{
					exceptionClass: '',
					exceptionTrace: 'java.lang.RuntimeException',
				}}
			/>
		);

		fireEvent.click(getByLabelText('expand'));

		expect(queryByText('exceptionClass')).toBeNull();
	});
});
