/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {ApiHelpers} from '../../../helpers/ApiHelpers';
import getRandomString from '../../../utils/getRandomString';
import {journalPagesTest} from '../../journal-web/main/fixtures/journalPagesTest';
import getDataStructureDefinition from '../../journal-web/main/utils/getDataStructureDefinition';
import {translationPagesTest} from './fixtures/translationPagesTest';

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	journalPagesTest,
	loginTest(),
	translationPagesTest
);

async function addCustomWebContent(
	apiHelpers: ApiHelpers,
	siteId: string,
	{
		contentFields,
		fields,
		title,
	}: {
		contentFields: {contentFieldValue: {data: string}; name: string}[];
		fields: Parameters<typeof getDataStructureDefinition>[0]['fields'];
		title: string;
	}
) {
	const structure = await apiHelpers.dataEngine.createStructure(
		siteId,
		getDataStructureDefinition({
			defaultLanguageId: 'en_US',
			fields,
			name: getRandomString(),
		})
	);

	await apiHelpers.headlessDelivery.postStructuredContent({
		contentFields,
		contentStructureId: Number(structure.id),
		datePublished: null,
		description: 'WC WebContent Description',
		siteId,
		title,
	});

	return structure;
}

test('Translating the text fields of a customized web content persists them', async ({
	apiHelpers,
	site,
	webContentTranslationPage,
}) => {
	const title = getRandomString();

	// Create a web content with single-line and multi-line text fields

	await addCustomWebContent(apiHelpers, site.id, {
		contentFields: [
			{contentFieldValue: {data: 'Text'}, name: 'Text'},
			{
				contentFieldValue: {data: 'This is a Text Box field'},
				name: 'TextBox',
			},
		],
		fields: [{name: 'Text'}, {displayStyle: 'multiline', name: 'TextBox'}],
		title,
	});

	// Translate every field into Spanish and publish

	await webContentTranslationPage.open(site, title);

	await webContentTranslationPage.changeTargetLocale('es-ES');

	await webContentTranslationPage.translateFields({
		description: 'WC WebContent Descripción',
		title: 'WC WebContent Título',
	});

	await webContentTranslationPage.fillCustomField('Text', 'Mensaje de texto');

	await webContentTranslationPage.fillCustomField(
		'TextBox',
		'Este es un cuadro de texto.'
	);

	await webContentTranslationPage.publish();

	// The translation persists when the editor is reopened

	await webContentTranslationPage.open(site, title);

	await webContentTranslationPage.changeTargetLocale('es-ES');

	await webContentTranslationPage.assertTargetFields({
		description: 'WC WebContent Descripción',
		title: 'WC WebContent Título',
	});

	await webContentTranslationPage.assertCustomFieldValue(
		'Text',
		'Mensaje de texto'
	);

	await webContentTranslationPage.assertCustomFieldValue(
		'TextBox',
		'Este es un cuadro de texto.'
	);
});

test('Translating the numeric fields of a customized web content persists them', async ({
	apiHelpers,
	site,
	webContentTranslationPage,
}) => {
	const title = getRandomString();

	// Create a web content with integer and decimal numeric fields

	await addCustomWebContent(apiHelpers, site.id, {
		contentFields: [
			{contentFieldValue: {data: '100'}, name: 'Number'},
			{contentFieldValue: {data: '0.5'}, name: 'Decimal'},
		],
		fields: [
			{dataType: 'integer', fieldType: 'numeric', name: 'Number'},
			{dataType: 'double', fieldType: 'numeric', name: 'Decimal'},
		],
		title,
	});

	// Translate every field into Spanish and publish

	await webContentTranslationPage.open(site, title);

	await webContentTranslationPage.changeTargetLocale('es-ES');

	await webContentTranslationPage.translateFields({
		description: 'WC WebContent Descripción',
		title: 'WC WebContent Título',
	});

	await webContentTranslationPage.fillCustomField('Number', '200');

	await webContentTranslationPage.fillCustomField('Decimal', '2.2');

	await webContentTranslationPage.publish();

	// The translation persists when the editor is reopened, with the decimal
	// separator rendered for the Spanish locale

	await webContentTranslationPage.open(site, title);

	await webContentTranslationPage.changeTargetLocale('es-ES');

	await webContentTranslationPage.assertTargetFields({
		description: 'WC WebContent Descripción',
		title: 'WC WebContent Título',
	});

	await webContentTranslationPage.assertCustomFieldValue('Number', '200');

	await webContentTranslationPage.assertCustomFieldValue('Decimal', '2,2');
});

test('Translating the rich text field of a customized web content persists it', async ({
	apiHelpers,
	site,
	webContentTranslationPage,
}) => {
	const title = getRandomString();

	// Create a web content with a rich text field

	await addCustomWebContent(apiHelpers, site.id, {
		contentFields: [
			{
				contentFieldValue: {
					data: '<h2 class="text-center">This is a HTML title</h2>',
				},
				name: 'RichText',
			},
		],
		fields: [{fieldType: 'rich_text', name: 'RichText'}],
		title,
	});

	// Translate every field into Spanish and publish

	await webContentTranslationPage.open(site, title);

	await webContentTranslationPage.changeTargetLocale('es-ES');

	await webContentTranslationPage.translateFields({
		description: 'WC WebContent Descripción',
		title: 'WC WebContent Título',
	});

	// The Description is the first rich text editor; the custom field is the
	// second

	await webContentTranslationPage.fillRichText(1, 'Este es un título HTML');

	await webContentTranslationPage.publish();

	// The translation persists when the editor is reopened

	await webContentTranslationPage.open(site, title);

	await webContentTranslationPage.changeTargetLocale('es-ES');

	await webContentTranslationPage.assertTargetFields({
		description: 'WC WebContent Descripción',
		title: 'WC WebContent Título',
	});

	await webContentTranslationPage.assertRichTextValue(
		1,
		'Este es un título HTML'
	);
});

test('Translating a customized web content still works after adding a structure field', async ({
	apiHelpers,
	site,
	webContentTranslationPage,
}) => {
	const title = getRandomString();

	// Create a web content with a single text field

	const structure = await addCustomWebContent(apiHelpers, site.id, {
		contentFields: [{contentFieldValue: {data: 'Text'}, name: 'Text'}],
		fields: [{name: 'Text'}],
		title,
	});

	// Translate the text field into Spanish and publish

	await webContentTranslationPage.open(site, title);

	await webContentTranslationPage.changeTargetLocale('es-ES');

	await webContentTranslationPage.translateFields({
		title: 'WC WebContent Título',
	});

	await webContentTranslationPage.fillCustomField('Text', 'Mensaje de texto');

	await webContentTranslationPage.publish();

	// Add a numeric field to the structure

	const numericField = getDataStructureDefinition({
		defaultLanguageId: 'en_US',
		fields: [{dataType: 'integer', fieldType: 'numeric', name: 'Number'}],
		name: structure.name.en_US,
	});

	const dataLayoutRows =
		structure.defaultDataLayout.dataLayoutPages[0].dataLayoutRows;

	structure.dataDefinitionFields.push(numericField.dataDefinitionFields[0]);

	dataLayoutRows.push(
		numericField.defaultDataLayout.dataLayoutPages[0].dataLayoutRows[0]
	);

	await apiHelpers.dataEngine.updateStructure(structure.id, structure);

	// The existing translation is preserved and the new field can be translated

	await webContentTranslationPage.open(site, title);

	await webContentTranslationPage.changeTargetLocale('es-ES');

	await webContentTranslationPage.assertTargetFields({
		title: 'WC WebContent Título',
	});

	await webContentTranslationPage.assertCustomFieldValue(
		'Text',
		'Mensaje de texto'
	);

	await webContentTranslationPage.fillCustomField('Number', '200');

	await webContentTranslationPage.publish();

	// The new field's translation persists alongside the existing ones

	await webContentTranslationPage.open(site, title);

	await webContentTranslationPage.changeTargetLocale('es-ES');

	await webContentTranslationPage.assertCustomFieldValue('Number', '200');

	await webContentTranslationPage.assertCustomFieldValue(
		'Text',
		'Mensaje de texto'
	);

	await webContentTranslationPage.assertTargetFields({
		title: 'WC WebContent Título',
	});
});
