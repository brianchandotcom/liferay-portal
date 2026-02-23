/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * This fixes an error being thrown after upgrading to react-router v7.
 * ReferenceError: TextEncoder is not defined
 */

// eslint-disable-next-line @liferay/no-extraneous-dependencies
import {TextEncoder} from 'util';

// eslint-disable-next-line no-undef
global.TextEncoder = TextEncoder;