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
} from 'liferay-npm-scripts/src/storybook';
import React from 'react';

import SynonymSetsForm from '../../src/main/resources/META-INF/resources/js/components/SynonymSetsForm.es';

const {addDecorator, storiesOf} = StorybookReact;
const {withKnobs} = StorybookAddonKnobs;

addDecorator(withKnobs);

addDecorator(storyFn => {
	const context = {
		namespace:
			'_com_liferay_portal_search_synonyms_web_portlet_SynonymsPortlet_',
		spritemap: STORYBOOK_CONSTANTS.SPRITEMAP_PATH,
	};

	return (
		<ClayIconSpriteContext.Provider value={context.spritemap}>
			<div className="synonyms-sets-root">{storyFn()}</div>
		</ClayIconSpriteContext.Provider>
	);
});

const withSheet = storyFn => (
	<div className="sheet sheet-lg" style={{marginTop: '24px'}}>
		{storyFn()}
	</div>
);

storiesOf('Pages|SynonymSetsForm', module)
	.addDecorator(withSheet)
	.add('default', () => (
		<SynonymSetsForm
			formName="testFm"
			inputName="testInput"
			originalInputName="testInputName"
			synonymSets=""
		/>
	))
	.add('with synonyms', () => (
		<SynonymSetsForm
			formName="testFm"
			inputName="testInput"
			originalInputName="testInputName"
			synonymSets="one,two,three"
		/>
	));
