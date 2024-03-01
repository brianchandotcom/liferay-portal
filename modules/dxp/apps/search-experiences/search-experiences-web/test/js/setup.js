/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Extends the timeout session to prevent the following error: 'Timeout - Async
 * callback was not invoked within the 5000ms timeout specified by
 * jest.setTimeout.Error'
 */

jest.setTimeout(30000);
