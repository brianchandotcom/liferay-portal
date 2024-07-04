/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../../fixtures/loginTest';
import getRandomString from '../../../../utils/getRandomString';
import {dataSetAdminApiHelpersTest} from '../../fixtures/dataSetAdminApiHelpersTest';
import {
	ACTION_DATA_SET_RELATIONSHIP,
	CARDS_SECTION_DATA_SET_RELATIONSHIP,
	CREATION_ACTION_DATA_SET_RELATIONSHIP,
	DATE_FILTER_DATA_SET_RELATIONSHIP,
	ITEM_ACTION_DATA_SET_RELATIONSHIP,
	LIST_SECTION_DATA_SET_RELATIONSHIP,
	SELECTION_FILTER_DATA_SET_RELATIONSHIP,
	SORT_DATA_SET_RELATIONSHIP,
	TABLE_SECTION_DATA_SET_RELATIONSHIP,
} from '../../utils/dataSetAdminConstants';
import {dataSetManagerSetupTest} from './fixtures/dataSetManagerSetupTest';

export const test = mergeTests(
	dataSetAdminApiHelpersTest,
	featureFlagsTest({
		'LPS-164563': true,
		'LPS-178052': true,
	}),
	loginTest(),
	dataSetManagerSetupTest
);

let dataSetERC: string;

const dataSetLabel: string = getRandomString();
let dataSet: any;

test.beforeEach(async ({dataSetAdminApiHelpers}) => {
	dataSetERC = getRandomString();

	dataSet = await dataSetAdminApiHelpers.createDataSet({
		erc: dataSetERC,
		label: dataSetLabel,
	});

});

test.afterEach(async ({dataSetAdminApiHelpers}) => {
	await dataSetAdminApiHelpers.deleteDataSet({
		erc: dataSetERC,
	});
});

test('Ensure Root Model relationship constraints', async ({
	dataSetAdminApiHelpers,
}) => {
	delete dataSet.externalReferenceCode;

	const assertMissingField = (result, field) => {
		expect(result?.status).toBe('BAD_REQUEST');
		expect(result?.title).toContain(
			'No value was provided for required object field'
		);
		expect(result?.title).toContain(field);
	};

	await test.step('Try to add creation Data Set Action with no data set associated', async () => {
		const result = await dataSetAdminApiHelpers.createDataSetCreationAction({
			dataSet,
			label_i18n: {en_US: 'Invalid dangling creation action'},
		});

		assertMissingField(result, ACTION_DATA_SET_RELATIONSHIP.id);
	});

	await test.step('Try to add creation Data Set Action with no data set associated', async () => {
		const result = await dataSetAdminApiHelpers.createDataSetItemAction({
			dataSet,
			label_i18n: {en_US: 'Invalid dangling item action'},
		});

		assertMissingField(result, ACTION_DATA_SET_RELATIONSHIP.id);
	});

		assertValidationError(result);
	});

	await test.step('Try to add table sections with no data set associated', async () => {
		const result = await dataSetAdminApiHelpers.createDataSetTableSection({
			dataSet,
			fieldName: 'Invalid field',
		});

		assertMissingField(result, TABLE_SECTION_DATA_SET_RELATIONSHIP.id);
	});

	await test.step('Try to add card sections with no data set associated', async () => {
		const result = await dataSetAdminApiHelpers.createDataSetCardsSection({
			dataSet,
			fieldName: 'Invalid field',
		});

		assertMissingField(result, CARDS_SECTION_DATA_SET_RELATIONSHIP.id);
	});

	await test.step('Try to add list sections with no data set associated', async () => {
		const result = await dataSetAdminApiHelpers.createDataSetListSection({
			dataSet,
			fieldName: 'Invalid field',
		});

		assertMissingField(result, LIST_SECTION_DATA_SET_RELATIONSHIP.id);
	});

	await test.step('Try to add date filter with no data set associated', async () => {
		const result = await dataSetAdminApiHelpers.createDataSetDateFilter({
			dataSet,
			fieldName: 'Invalid field',
			type: 'date',
		});

		assertMissingField(result, DATE_FILTER_DATA_SET_RELATIONSHIP.id);
	});

	await test.step('Try to add selection filter with no data set associated', async () => {
		const result =
			await dataSetAdminApiHelpers.createDataSetSelectionFilter({
				dataSet,
				fieldName: 'Invalid field',
				source: 'source',
				sourceType: 'sourceType',
			});

		assertMissingField(result, SELECTION_FILTER_DATA_SET_RELATIONSHIP.id);
	});

	await test.step('Try to add sort with no data set associated', async () => {
		const result = await dataSetAdminApiHelpers.createDataSetSort({
			dataSet,
			fieldName: 'Invalid field',
		});

		assertMissingField(result, SORT_DATA_SET_RELATIONSHIP.id);
	});
});
