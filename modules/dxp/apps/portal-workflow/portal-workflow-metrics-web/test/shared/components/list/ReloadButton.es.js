/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fireEvent, render} from '@testing-library/react';
import React from 'react';

import ReloadButton from '../../../../src/main/resources/META-INF/resources/js/shared/components/list/ReloadButton.es';

describe('ReloadButton', () => {
	test('should call "reload" function when clicked', () => {
		Object.defineProperty(window, 'location', {
			value: {reload: jest.fn()},
			writable: true,
		});

		const {getByText} = render(<ReloadButton />);
		const reloadButton = getByText('reload-page');

		fireEvent.click(reloadButton);

		expect(window.location.reload).toHaveBeenCalled();
	});
});
