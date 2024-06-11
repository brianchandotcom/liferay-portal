/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../fixtures/loginTest';
import {searchExperiencesPageTest} from '../../fixtures/searchExperiencesPageTest';

export const test = mergeTests(loginTest(), searchExperiencesPageTest);