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
	StorybookAddonActions,
	StorybookAddonKnobs,
	StorybookReact,
} from '@liferay/npm-scripts/src/storybook';
import React from 'react';

import '../../src/main/resources/META-INF/resources/css/main.scss';

import ClayLayout from '@clayui/layout';

import BlueprintForm from '../../src/main/resources/META-INF/resources/js/edit_blueprint';
import AddElementSidebar from '../../src/main/resources/META-INF/resources/js/edit_blueprint/AddElementSidebar';
import PreviewSidebar from '../../src/main/resources/META-INF/resources/js/edit_blueprint/PreviewSidebar';
import QueryBuilder from '../../src/main/resources/META-INF/resources/js/edit_blueprint/tabs/QueryBuilder';
import SelectAssetTypes from '../../src/main/resources/META-INF/resources/js/edit_blueprint/tabs/SelectAssetTypes';
import ElementForm from '../../src/main/resources/META-INF/resources/js/edit_element';
import ErrorBoundary from '../../src/main/resources/META-INF/resources/js/shared/ErrorBoundary';
import JSONElement from '../../src/main/resources/META-INF/resources/js/shared/JSONElement';
import PageToolbar from '../../src/main/resources/META-INF/resources/js/shared/PageToolbar';
import SearchInput from '../../src/main/resources/META-INF/resources/js/shared/SearchInput';
import Element from '../../src/main/resources/META-INF/resources/js/shared/element';
import {DEFAULT_EDIT_ELEMENT} from '../../src/main/resources/META-INF/resources/js/utils/data';
import AddBlueprintModal from '../../src/main/resources/META-INF/resources/js/view_blueprints/AddBlueprintModal';
import {
	ELEMENT_OUTPUTS,
	ENTITY_JSON,
	INDEX_FIELDS,
	INITIAL_CONFIGURATION,
	SEARCHABLE_ASSET_TYPES,
	SELECTED_ELEMENTS,
	mockSearchResults,
} from '../js/mocks/data';

const {addDecorator, storiesOf} = StorybookReact;
const {action} = StorybookAddonActions;
const {withKnobs} = StorybookAddonKnobs;

const CONTEXT = {
	availableLanguages: {
		ar_SA: 'Arabic (Saudi Arabia)',
		ca_ES: 'Catalan (Spain)',
		en_US: 'English (United States)',
		nl_NL: 'Dutch (Netherlands)',
		zh_CN: 'Chinese (China)',
	},
	contextPath: '/o/portal-search-tuning-blueprints-admin-web',
	defaultLocale: 'en_US',
	locale: 'en_US',
	namespace:
		'_com_liferay_portal_search_tuning_gsearch_configuration_web_internal_portlet_SearchConfigurationAdminPortlet_',
	spritemap: STORYBOOK_CONSTANTS.SPRITEMAP_PATH,
};

addDecorator(withKnobs);

addDecorator((storyFn) => {
	return (
		<div className="portlet-blueprints-admin">
			<ClayIconSpriteContext.Provider value={CONTEXT.spritemap}>
				{storyFn()}
			</ClayIconSpriteContext.Provider>
		</div>
	);
});

const withBlueprintsClass = (storyFn) => (
	<ErrorBoundary>
		<div className="edit-blueprint-root">{storyFn()}</div>
	</ErrorBoundary>
);

const withBuilderClass = (storyFn) => (
	<div className="builder">{storyFn()}</div>
);

const withContainer = (storyFn) => (
	<ClayLayout.ContainerFluid size="md" view>
		{storyFn()}
	</ClayLayout.ContainerFluid>
);

const BLUEPRINT_FORM_PROPS = {
	blueprintId: '1',
	blueprintType: 0,
	entityJSON: ENTITY_JSON,
	indexFields: INDEX_FIELDS,
	initialConfigurationString: JSON.stringify(INITIAL_CONFIGURATION),
	initialDescription: {},
	initialTitle: {
		'en-US': 'Test Title',
	},
	queryElements: SELECTED_ELEMENTS,
	redirectURL: '',
	searchableAssetTypes: SEARCHABLE_ASSET_TYPES,
	submitFormURL: '',
};

storiesOf('Pages|BlueprintForm', module)
	.add('default', () => (
		<BlueprintForm
			context={CONTEXT}
			props={{
				...BLUEPRINT_FORM_PROPS,
				initialConfigurationString: JSON.stringify({
					...INITIAL_CONFIGURATION,
					query_configuration: ELEMENT_OUTPUTS,
				}),
				initialSelectedElementsString: JSON.stringify({
					query_configuration: SELECTED_ELEMENTS,
				}),
				searchResultsURL:
					'https://run.mocky.io/v3/aa39bbc9-9cb1-4c16-a26d-9f1d046f55ef',
			}}
		/>
	))
	.add('empty', () => (
		<BlueprintForm
			context={CONTEXT}
			props={{
				...BLUEPRINT_FORM_PROPS,
				initialSelectedElementsString: JSON.stringify({
					query_configuration: [],
				}),
				searchResultsURL:
					'https://run.mocky.io/v3/aa39bbc9-9cb1-4c16-a26d-9f1d046f55ef',
			}}
		/>
	))
	.add('preview with error messages', () => (
		<BlueprintForm
			context={CONTEXT}
			props={{
				...BLUEPRINT_FORM_PROPS,
				initialSelectedElementsString: JSON.stringify({
					query_configuration: [
						SELECTED_ELEMENTS[3],
						SELECTED_ELEMENTS[4],
						SELECTED_ELEMENTS[5],
						SELECTED_ELEMENTS[0],
						SELECTED_ELEMENTS[1],
						SELECTED_ELEMENTS[2],
					],
				}),
				searchResultsURL:
					'https://run.mocky.io/v3/177e9f6b-d921-4b92-9f64-c7eb684b8300',

				// { "errors": [ { "className": "com.liferay.portal.search.tuning.blueprints.engine.internal.condition.visitor.InRangeVisitor", "elementId": "queryElement-0", "localizationKey": "core.error.clause-condition-date-parsing-error", "msg": "Illegal pattern character 'x'", "rootObject": { "evaluation_type": "in_range", "date_format": "yyyyMMddx", "parameter_name": "${time.current_date}", "value": [ 20210228, 20210302 ] }, "rootProperty": null, "rootValue": "[20210228,20210302]", "severity": "ERROR", "throwable": { "cause": null, "localizedMessage": "Illegal pattern character 'x'", "message": "Illegal pattern character 'x'" } } ] }

			}}
		/>
	));

storiesOf('Pages|ElementForm', module).add('default', () => (
	<ElementForm
		context={CONTEXT}
		props={{
			initialConfigurationString: JSON.stringify(DEFAULT_EDIT_ELEMENT),
			initialDescription: {'en-US': 'Description'},
			initialTitle: {
				'en-US': 'Test Title',
			},
			predefinedVariables: [
				{
					categoryName: 'User',
					parameterDefinitions: [
						{
							className:
								'com.liferay.portal.search.tuning.blueprints.engine.parameter.StringParameter',
							description: "User's ID",
							variable: '${user.user_id}',
						},
						{
							className:
								'com.liferay.portal.search.tuning.blueprints.engine.parameter.StringParameter',
							description: "User's First Name",
							variable: '${user.user_first_name}',
						},
					],
				},
				{
					categoryName: 'Context',
					parameterDefinitions: [
						{
							description: 'Company ID',
							variable: '${context.company_id}',
						},
						{
							description: 'Scope Group ID',
							variable: '${context.scope_group_id}',
						},
					],
				},
			],
			redirectURL: '',
			submitFormURL: '',
		}}
	/>
));

storiesOf('Components|AddBlueprintModal', module)
	.addDecorator(withBlueprintsClass)
	.add('AddBlueprintModal', () => (
		<AddBlueprintModal
			closeModal={action('closeModal')}
			contextPath="/o/portal-search-tuning-blueprints-admin-web/"
			dialogTitle="New Search Blueprint"
			initialVisible
			searchableAssetTypesString={JSON.stringify(SEARCHABLE_ASSET_TYPES)}
		/>
	));

storiesOf('Components|Element', module)
	.addDecorator(withBlueprintsClass)
	.addDecorator(withBuilderClass)
	.addDecorator(withContainer)
	.add('Element', () => (
		<Element
			elementTemplateJSON={SELECTED_ELEMENTS[0].elementTemplateJSON}
			onDeleteElement={action('onDeleteElement')}
			onUpdateElement={action('onUpdateElement')}
			uiConfigurationJSON={SELECTED_ELEMENTS[0].uiConfigurationJSON}
			uiConfigurationValues={SELECTED_ELEMENTS[0].uiConfigurationValues}
		/>
	));

storiesOf('Components|Element', module)
	.addDecorator(withBlueprintsClass)
	.addDecorator(withBuilderClass)
	.addDecorator(withContainer)
	.add('JSONElement', () => (
		<JSONElement
			elementTemplateJSON={SELECTED_ELEMENTS[0].elementTemplateJSON}
			error={{}}
			onDeleteElement={action('onDeleteElement')}
			setFieldTouched={() => {}}
			setFieldValue={() => {}}
			uiConfigurationValues={SELECTED_ELEMENTS[0].uiConfigurationValues}
		/>
	));

storiesOf('Components|PageToolbar', module)
	.addDecorator(withBlueprintsClass)
	.add('PageToolbar', () => (
		<PageToolbar
			initialTitle={{
				'en-US': 'Test Title',
			}}
			onCancel=""
			onPublish={action('onPublish')}
			onSubmit={action('onSubmit')}
			tab="query-builder"
			tabs={{
				'query-builder': 'query-builder',
			}}
		/>
	));

storiesOf('Components|QueryBuilder', module)
	.addDecorator(withBlueprintsClass)
	.addDecorator(withContainer)
	.add('QueryBuilder', () => (
		<QueryBuilder
			frameworkConfig={{
				apply_indexer_clauses: true,
				searchable_asset_types: SEARCHABLE_ASSET_TYPES,
			}}
			onDeleteElement={action('buildElement')}
			onUpdateElement={action('onUpdateElement')}
			searchableAssetTypes={SEARCHABLE_ASSET_TYPES}
			selectedElements={SELECTED_ELEMENTS}
		/>
	));

storiesOf('Components|SearchInput', module)
	.addDecorator(withBlueprintsClass)
	.add('SearchInput', () => (
		<SearchInput
			onChange={action('onChange')}
			onSubmit={action('onSubmit')}
		/>
	));

storiesOf('Components|SelectAssetTypes', module)
	.addDecorator(withBlueprintsClass)
	.addDecorator(withContainer)
	.add('SelectAssetTypes', () => (
		<SelectAssetTypes
			searchableAssetTypes={SEARCHABLE_ASSET_TYPES}
			selectedAssetTypes={[]}
			updateSelectedAssetTypes={action('updateSelectedAssetTypes')}
		/>
	));

storiesOf('Components|AddElementSidebar', module)
	.addDecorator(withBlueprintsClass)
	.add('AddElementSidebar', () => (
		<AddElementSidebar
			addElement={action('addElement')}
			elements={SELECTED_ELEMENTS}
			visible={true}
		/>
	));

storiesOf('Components|PreviewSidebar', module)
	.addDecorator(withBlueprintsClass)
	.add('PreviewSidebar', () => (
		<PreviewSidebar
			loading={false}
			onFetchResults={action('onFetchResults')}
			results={mockSearchResults()}
			visible={true}
		/>
	))
	.add('Empty', () => (
		<PreviewSidebar
			loading={false}
			onFetchResults={action('onFetchResults')}
			results={{
				hits: [],
				meta: {
					executionTime: '0.061',
					keywords: 'test',
					totalHits: 0,
				},
				pagination: {activePage: 1, totalPages: 1},
			}}
			visible={true}
		/>
	))
	.add('Error', () => (
		<PreviewSidebar
			loading={false}
			onFetchResults={action('onFetchResults')}
			results={{
				errors: [
					{
						className:
							'com.liferay.portal.search.tuning.blueprints.engine.internal.searchrequest.QuerySearchRequestBodyContributor',
						elementId: 'queryElement-1',
						localizationKey: 'core.error.unknown-occur-value',
						msg:
							'No enum constant com.liferay.portal.search.tuning.blueprints.constants.json.values.Occur.FILTERED',
						rootObject: {
							context: 'query',
							occur: 'filtered',
							query: {
								query: {
									range: {
										createDate: {
											from: '',
											include_lower: true,
											include_upper: true,
											to: '',
										},
									},
								},
							},
							type: 'wrapper',
						},
						rootProperty: null,
						rootValue: 'FILTERED',
						severity: 'ERROR',
						throwable: {
							cause: null,
							localizedMessage:
								'No enum constant com.liferay.portal.search.tuning.blueprints.constants.json.values.Occur.FILTERED',
							message:
								'No enum constant com.liferay.portal.search.tuning.blueprints.constants.json.values.Occur.FILTERED',
						},
					},
					{
						className:
							'com.liferay.portal.search.tuning.blueprints.engine.internal.searchrequest.QuerySearchRequestBodyContributor',
						localizationKey: 'core.error.unknown-clause-context',
						msg:
							'No enum constant com.liferay.portal.search.tuning.blueprints.constants.json.values.ClauseContext.QUERYS',
						rootObject: {
							context: 'querys',
							occur: 'filter',
							query: {},
							type: 'wrapper',
						},
						rootProperty: null,
						rootValue: 'QUERYS',
						severity: 'ERROR',
						throwable: {
							cause: null,
							localizedMessage:
								'No enum constant com.liferay.portal.search.tuning.blueprints.constants.json.values.ClauseContext.QUERYS',
							message:
								'No enum constant com.liferay.portal.search.tuning.blueprints.constants.json.values.ClauseContext.QUERYS',
						},
					},
				],
			}}
			visible={true}
		/>
	));
