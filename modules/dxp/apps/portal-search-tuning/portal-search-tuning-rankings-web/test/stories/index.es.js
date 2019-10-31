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

import {
	STORYBOOK_CONSTANTS,
	StorybookAddonActions,
	StorybookAddonKnobs,
	StorybookReact
} from 'liferay-npm-scripts/src/storybook';
import React from 'react';

import '../../src/main/resources/META-INF/resources/css/main.scss';

import {ClayIconSpriteContext} from '@clayui/icon';

import ThemeContext from '../../src/main/resources/META-INF/resources/js/ThemeContext.es';
import PageToolbar from '../../src/main/resources/META-INF/resources/js/components/PageToolbar.es';
import ResultRankingsForm from '../../src/main/resources/META-INF/resources/js/components/ResultRankingsForm.es';
import AddResult from '../../src/main/resources/META-INF/resources/js/components/add_result/AddResult.es';
import AddResultModal from '../../src/main/resources/META-INF/resources/js/components/add_result/AddResultModal.es';
import AddResultSearchBar from '../../src/main/resources/META-INF/resources/js/components/add_result/AddResultSearchBar.es';
import Alias from '../../src/main/resources/META-INF/resources/js/components/alias/Alias.es';
import FilterDisplay from '../../src/main/resources/META-INF/resources/js/components/list/FilterDisplay.es';
import FilterInput from '../../src/main/resources/META-INF/resources/js/components/list/FilterInput.es';
import Item from '../../src/main/resources/META-INF/resources/js/components/list/Item.es';
import ItemDragPreview from '../../src/main/resources/META-INF/resources/js/components/list/ItemDragPreview.es';
import ItemDropdown from '../../src/main/resources/META-INF/resources/js/components/list/ItemDropdown.es';
import List from '../../src/main/resources/META-INF/resources/js/components/list/List.es';
import ClayEmptyState from '../../src/main/resources/META-INF/resources/js/components/shared/ClayEmptyState.es';
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
			'_com_liferay_portal_search_ranking_web_portlet_ResultRankingsPortlet_',
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

storiesOf('Pages|ResultRankingsForm', module).add('default', () => (
	<ResultRankingsForm
		cancelUrl=""
		fetchDocumentsHiddenUrl="http://www.mocky.io/v2/5cd31439310000e29a339bbd"
		fetchDocumentsSearchUrl="http://www.mocky.io/v2/5d698970330000cfc7b68ab9"
		fetchDocumentsVisibleUrl="http://www.mocky.io/v2/5cca1d49310000bf0312ce66"
		formName="testFm"
		initialAliases={['one', 'two', 'three']}
		saveActionUrl="#"
		searchQuery={text('Search Term', 'example')}
		status={1}
	/>
));

storiesOf('Components|AddResult', module)
	.add('AddResult', () => (
		<AddResult
			fetchDocumentsSearchUrl="http://www.mocky.io/v2/5d698970330000cfc7b68ab9"
			onAddResultSubmit={action('onAddResultSubmit')}
		/>
	))
	.add('AddResultModal', () => (
		<AddResultModal
			fetchDocumentsSearchUrl="http://www.mocky.io/v2/5d698970330000cfc7b68ab9"
			onAddResultSubmit={action('onAddResultSubmit')}
			onCloseModal={action('onCloseModal')}
		/>
	))
	.add('AddResultSearchBar', () => (
		<AddResultSearchBar
			onSearchKeyDown={action('onSearchKeyDown')}
			onSearchQueryChange={action('onSearchQueryChange')}
			onSearchSubmit={action('onSearchSubmit')}
			searchQuery={text('Search Query', '')}
		/>
	));

storiesOf('Components|Alias', module)
	.addDecorator(withSheet)
	.add('Alias', () => (
		<Alias
			keywords={array('Keywords', [], ',')}
			onClickDelete={action('onClickDelete')}
			onClickSubmit={action('onClickSubmit')}
		/>
	));

storiesOf('Components|ClayEmptyState', module)
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

storiesOf('Components|PageToolbar', module)
	.add('active', () => (
		<PageToolbar
			active={true}
			onCancel={action('onCancel')}
			onChangeActive={action('onActivate')}
			onPublish={action('onPublish')}
			submitDisabled={boolean('Disabled', false)}
		/>
	))
	.add('inactive', () => (
		<PageToolbar
			active={false}
			onCancel={action('onCancel')}
			onChangeActive={action('onActivate')}
			onPublish={action('onPublish')}
			submitDisabled={boolean('Disabled', false)}
		/>
	));
