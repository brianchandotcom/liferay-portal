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

import {ClayIconSpriteContext} from '@clayui/icon';
import {
	STORYBOOK_CONSTANTS,
	StorybookAddonKnobs,
	StorybookReact,
} from '@liferay/npm-scripts/src/storybook';
import React from 'react';

import BlueprintsSearch from '../../src/main/resources/META-INF/resources/js/components/BlueprintsSearch';

import '../../src/main/resources/META-INF/resources/css/main.scss';

const {addDecorator, storiesOf} = StorybookReact;
const {withKnobs} = StorybookAddonKnobs;

addDecorator(withKnobs);

addDecorator((storyFn) => {
	const context = {
		namespace:
			'com_liferay_portal_search_tuning_blueprints_web_internal_BlueprintsWebPortlet',
		spritemap: STORYBOOK_CONSTANTS.SPRITEMAP_PATH,
	};

	return (
		<ClayIconSpriteContext.Provider value={context.spritemap}>
			{storyFn()}
		</ClayIconSpriteContext.Provider>
	);
});

const FETCH_URL =
	'https://run.mocky.io/v3/98cd9b49-8fe6-47b9-8e07-62caa7e9c3bd';

//https://designer.mocky.io/manage/delete/98cd9b49-8fe6-47b9-8e07-62caa7e9c3bd/zkkGktbUnpxZeTGAkqGHktZd1jQhkam8REy5

const SUGGEST_URL =
	'https://run.mocky.io/v3/8a9f5981-378f-4e4d-96a1-369d286f8954';

//https://designer.mocky.io/manage/delete/8a9f5981-378f-4e4d-96a1-369d286f8954/FZty6MiQkyL8FB6owdovyjVJbDbfjuLWfCHe

const EMPTY_URL =
	'https://run.mocky.io/v3/f1a9653a-fcac-4722-8fee-64df94df5bf9';

//https://designer.mocky.io/manage/delete/f1a9653a-fcac-4722-8fee-64df94df5bf9/wkN7nAjQk7lkODBz20PYT2kP4AzzygA3j3IE

const EMPTY_SUGGEST_URL =
	'https://run.mocky.io/v3/dce05a29-ff94-43fa-9c66-aaca57d4a6b8';

//https://designer.mocky.io/manage/delete/dce05a29-ff94-43fa-9c66-aaca57d4a6b8/G1A8oEVgo90gTTm3xsPQaoaoRnDLgdVhylNg

const BAD_REQUEST_URL =
	'https://run.mocky.io/v3/8c8ab5a2-c99f-4694-b7dc-12b445163a19';

//https://designer.mocky.io/manage/delete/8c8ab5a2-c99f-4694-b7dc-12b445163a19/CKxgru5fFLRPMMp9DDygnbtItINuNOXzZPwD

const ERROR_URL =
	'https://run.mocky.io/v3/4e507ddb-afa4-4845-9ad6-a10a6ddab801';

//https://designer.mocky.io/manage/delete/4e507ddb-afa4-4845-9ad6-a10a6ddab801/QV8czujaGUho78WzE95yDZ0bHMIi46u14bMl

const withSheet = (storyFn) => (
	<div
		className="portlet-search-tuning-blueprints-web sheet sheet-lg"
		style={{paddingTop: '24px'}}
	>
		{storyFn()}
	</div>
);

storiesOf('Pages|BlueprintsWebApp', module)
	.addDecorator(withSheet)
	.add('default', () => (
		<BlueprintsSearch
			fetchResultsURL={FETCH_URL}
			suggestionsURL={SUGGEST_URL}
		/>
	))
	.add('empty', () => (
		<BlueprintsSearch
			fetchResultsURL={EMPTY_URL}
			suggestionsURL={EMPTY_SUGGEST_URL}
		/>
	))
	.add('bad request', () => (
		<BlueprintsSearch
			fetchResultsURL={BAD_REQUEST_URL}
			suggestionsURL={BAD_REQUEST_URL}
		/>
	))
	.add('error', () => (
		<BlueprintsSearch
			fetchResultsURL={ERROR_URL}
			suggestionsURL={ERROR_URL}
		/>
	));
