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

import React from 'react';
import {
	STORYBOOK_CONSTANTS,
	StorybookAddonActions,
	StorybookAddonKnobs,
	StorybookReact
} from 'liferay-npm-scripts/src/storybook';

import '../../src/main/resources/META-INF/resources/css/main.scss';

import Alias from '../../src/main/resources/META-INF/resources/js/components/alias/Alias.es';
import ClayEmptyState from '../../src/main/resources/META-INF/resources/js/components/shared/ClayEmptyState.es';
import FilterDisplay from '../../src/main/resources/META-INF/resources/js/components/list/FilterDisplay.es';
import FilterInput from '../../src/main/resources/META-INF/resources/js/components/list/FilterInput.es';
import Item from '../../src/main/resources/META-INF/resources/js/components/list/Item.es';
import ItemDragPreview from '../../src/main/resources/META-INF/resources/js/components/list/ItemDragPreview.es';
import ItemDropdown from '../../src/main/resources/META-INF/resources/js/components/list/ItemDropdown.es';
import List from '../../src/main/resources/META-INF/resources/js/components/list/List.es';
import PageToolbar from '../../src/main/resources/META-INF/resources/js/components/PageToolbar.es';
import ResultsRankingForm from '../../src/main/resources/META-INF/resources/js/components/ResultsRankingForm.es';
import ThemeContext from '../../src/main/resources/META-INF/resources/js/ThemeContext.es';
import {ClayIconSpriteContext} from '@clayui/icon';
import {mockDataMap} from './mock-data.es';

const {addDecorator, storiesOf} = StorybookReact;
const {action} = StorybookAddonActions;
const {array, boolean, select, text, withKnobs} = StorybookAddonKnobs;

addDecorator(withKnobs);

addDecorator(storyFn => {
	const context = {
		constants: {
			WORKFLOW_ACTION_PUBLISH: '1',
			WORKFLOW_ACTION_SAVE_DRAFT: '2'
		},
		namespace:
			'_com_liferay_portal_search_ranking_web_portlet_ResultsRankingPortlet_',
		spritemap: STORYBOOK_CONSTANTS.SPRITEMAP_PATH
	};

	return (
		<ClayIconSpriteContext.Provider value={context.spritemap}>
			<ThemeContext.Provider value={context}>
				<div className="results-rankings-root">{storyFn()}</div>
			</ThemeContext.Provider>
		</ClayIconSpriteContext.Provider>
	);
});

const withSheet = storyFn => (
	<div className="sheet sheet-lg" style={{marginTop: '24px'}}>
		{storyFn()}
	</div>
);

storiesOf('Pages|ResultsRankingForm', module).add('default', () => (
	<ResultsRankingForm
		cancelUrl=""
		fetchDocumentsHiddenUrl="http://www.mocky.io/v2/5cd31439310000e29a339bbd"
		fetchDocumentsSearchUrl="http://www.mocky.io/v2/5d698970330000cfc7b68ab9"
		fetchDocumentsVisibleUrl="http://www.mocky.io/v2/5cca1d49310000bf0312ce66"
		formName="testFm"
		initialAliases={['one', 'two', 'three']}
		saveActionUrl="#"
		searchQuery={text('Search Term', 'example')}
	/>
));

storiesOf('Components|Alias', module)
	.addDecorator(withSheet)
	.add('default', () => (
		<Alias
			keywords={array('Keywords', [], ',')}
			onClickDelete={action('onClickDelete')}
			onClickSubmit={action('onClickSubmit')}
		/>
	));

storiesOf('Components|EmptyState', module)
	.addDecorator(withSheet)
	.add('default', () => (
		<ClayEmptyState
			description={text('Description')}
			displayState={select(
				'Display State',
				{
					Empty: 'empty',
					Search: 'search',
					Success: 'success'
				},
				'search'
			)}
			title={text('Title')}
		/>
	))
	.add('with action', () => (
		<ClayEmptyState
			actionLabel="Refresh"
			description={text('Description')}
			displayState="empty"
			onClickAction={action('onClickAction')}
			title={text('Title')}
		/>
	));

storiesOf('Components|List', module)
	.addDecorator(withSheet)
	.add('default', () => (
		<List
			dataLoading={false}
			dataMap={mockDataMap}
			fetchDocumentsSearchUrl=""
			onAddResultSubmit={action('onAddResultSubmit')}
			onClickHide={action('onClickHide')}
			onClickPin={action('onClickPin')}
			onMove={action('onMove')}
			resultIds={['1', '2', '3', '4', '5']}
		/>
	))
	.add('empty', () => (
		<List
			dataLoading={false}
			dataMap={{}}
			fetchDocumentsSearchUrl=""
			onAddResultSubmit={action('onAddResultSubmit')}
		/>
	))
	.add('error', () => (
		<List
			dataLoading={false}
			dataMap={{}}
			displayError
			fetchDocumentsSearchUrl=""
			onAddResultSubmit={action('onAddResultSubmit')}
			onLoadResults={action('load-results')}
		/>
	));

storiesOf('Components|List/Item', module)
	.addDecorator(withSheet)
	.add('Item', () => (
		<div className="list-group">
			<Item.DecoratedComponent {...mockDataMap['1']} />

			<Item.DecoratedComponent
				{...mockDataMap['1']}
				hidden
				pinned={false}
			/>

			<Item.DecoratedComponent />

			<Item.DecoratedComponent
				date="Apr 18 2018, 11:04 AM"
				title="Item with date only"
			/>

			<Item.DecoratedComponent
				author="Test Test"
				date="Apr 18 2018, 11:04 AM"
				title="Item with date and title"
			/>

			<Item.DecoratedComponent
				clicks="100"
				title="Item with title and clicks"
			/>
		</div>
	))
	.add('ItemDragPreview', () => <ItemDragPreview {...mockDataMap['1']} />)
	.add('ItemDropdown', () => (
		<ItemDropdown
			onClickHide={action('onClickHide')}
			onClickPin={action('onClickPin')}
		/>
	));

storiesOf('Components|List/SearchBar', module)
	.addDecorator(withSheet)
	.add('FilterInput', () => (
		<FilterInput disableSearch={false} searchBarTerm="example" />
	))
	.add('FilterDisplay', () => (
		<FilterDisplay searchBarTerm="example" totalResultsCount={100} />
	));

storiesOf('Components|PageToolbar', module).add('default', () => (
	<PageToolbar submitDisabled={boolean('Disabled', false)} />
));
