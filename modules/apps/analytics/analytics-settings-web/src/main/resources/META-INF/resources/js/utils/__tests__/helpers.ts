/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {screen, waitForElementToBeRemoved} from '@testing-library/react';

export function loadingElement() {
	return waitForElementToBeRemoved(() => screen.getByTestId(/loading/i));
}

export function mockResponse(mock: any) {
	return Promise.resolve(new Response(JSON.stringify(mock)));
}
