/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../../fixtures/apiHelpersTest';
import {documentLibraryPagesTest} from '../../../../../fixtures/documentLibraryPages.fixtures';
import {featureFlagsTest} from '../../../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../../../fixtures/loginTest';
import {wikiPagesTest} from '../../../../../fixtures/wikiPagesTest';
import getRandomString from '../../../../../utils/getRandomString';
import {blogsPagesTest} from '../../../../blogs-web/main/fixtures/blogsPagesTest';
import {journalPagesTest} from '../../../../journal-web/main/fixtures/journalPagesTest';

const test = mergeTests(
	apiHelpersTest,
	blogsPagesTest,
	documentLibraryPagesTest,
	featureFlagsTest({
		'LPD-35013': {enabled: true},
	}),
	isolatedSiteTest,
	journalPagesTest,
	loginTest(),
	wikiPagesTest
);


test.fixme(
	'DM can format text with editor toolbar',
	{tag: '@LPS-127012'},
	async ({
		documentLibraryEditDocumentTypesPage: _documentLibraryEditDocumentTypesPage,
		documentLibraryPage: _documentLibraryPage,
	}) => {
		// This test requires creating a custom Document Type with a Rich Text
		// field, creating a document of that type, and verifying the editor
		// toolbar is present. The Document Type creation flow with custom
		// fields needs additional page object support.
	}
);
