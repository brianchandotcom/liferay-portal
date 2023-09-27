/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
