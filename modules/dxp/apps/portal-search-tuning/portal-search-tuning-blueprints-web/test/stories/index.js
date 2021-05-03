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
			fetchResultsURL="https://run.mocky.io/v3/fc407a61-2fc5-4cd9-b147-877581580dcc"
			suggestionsURL="https://run.mocky.io/v3/8a9f5981-378f-4e4d-96a1-369d286f8954"
		/>
	))
	.add('empty', () => (
		<BlueprintsSearch
			fetchResultsURL="https://run.mocky.io/v3/89d0381c-be15-4b67-8356-202974a64b67"
			suggestionsURL="https://run.mocky.io/v3/dce05a29-ff94-43fa-9c66-aaca57d4a6b8"
		/>
	))
	.add('error', () => (
		<BlueprintsSearch
			fetchResultsURL="https://run.mocky.io/v3/4e507ddb-afa4-4845-9ad6-a10a6ddab801"
			suggestionsURL="https://run.mocky.io/v3/4e507ddb-afa4-4845-9ad6-a10a6ddab801"
		/>
	));
