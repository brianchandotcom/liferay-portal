/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {pageStructure} from 'dynamic-data-mapping-form-builder/js/util/config.es';
import {FormApp, PagesVisitor} from 'dynamic-data-mapping-form-renderer';
import {delegate} from 'frontend-js-web';
import Component from 'metal-jsx';
import {Config} from 'metal-state';

/**
 * Form.
 * @extends Component
 */

class Form extends Component {
	render() {
		const {
			context,
			...otherProps
		} = this.props;

		const App = FormApp;

		return (
			<App
				{...otherProps}
				{...context}
			/>
		);
	}

	_pagesValueFn() {
		const {context} = this.props;

		return context.pages;
	}

	_paginationModeValueFn() {
		const {context} = this.props;

		return context.paginationMode;
	}

	_setContext(context) {
		const emptyLocalizableValue = {
			[themeDisplay.getLanguageId()]: '',
		};

		if (!context.pages.length) {
			context = {
				...context,
				pages: [
					{
						description: '',
						localizedDescription: emptyLocalizableValue,
						localizedTitle: emptyLocalizableValue,
						rows: [
							{
								columns: [
									{
										fields: [],
										size: 12,
									},
								],
							},
						],
						title: '',
					},
				],
			};
		}

		return {
			...context,
			pages: context.pages.map((page) => {
				let {
					description,
					localizedDescription,
					localizedTitle,
					title,
				} = page;

				if (description && typeof description !== 'string') {
					description = description[themeDisplay.getLanguageId()];
					localizedDescription = {
						[themeDisplay.getLanguageId()]: description,
					};
				}

				if (title && typeof title !== 'string') {
					title = title[themeDisplay.getLanguageId()];
					localizedTitle = {
						[themeDisplay.getLanguageId()]: title,
					};
				}

				return {
					...page,
					description,
					localizedDescription,
					localizedTitle,
					title,
				};
			}),
		};
	}
}

Form.PROPS = {

	/**
	 * @default undefined
	 * @instance
	 * @memberof Form
	 * @type {!string}
	 */

	autocompleteUserURL: Config.string(),

	/**
	 * The context for rendering a layout that represents a form.
	 * @default undefined
	 * @instance
	 * @memberof Form
	 * @type {!array}
	 */

	context: Config.shapeOf({
		dataEngineSidebar: Config.bool(),
		pages: Config.arrayOf(Config.object()),
		paginationMode: Config.string(),
		rules: Config.array(),
		successPageSettings: Config.object(),
	})
		.required()
		.setter('_setContext'),

	/**
	 * The rules of a form.
	 * @default undefined
	 * @instance
	 * @memberof Form
	 * @type {!array}
	 */

	dataProviderInstanceParameterSettingsURL: Config.string().required(),

	/**
	 * The rules of a form.
	 * @default undefined
	 * @instance
	 * @memberof Form
	 * @type {!array}
	 */

	dataProviderInstancesURL: Config.string().required(),

	/**
	 * The default language id of the form.
	 * @default undefined
	 * @instance
	 * @memberof Form
	 * @type {!array}
	 */

	defaultLanguageId: Config.string().value(
		themeDisplay.getDefaultLanguageId()
	),

	/**
	 * The default language id of the form.
	 * @default undefined
	 * @instance
	 * @memberof Form
	 * @type {!array}
	 */

	editingLanguageId: Config.string().value(
		themeDisplay.getDefaultLanguageId()
	),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Form
	 * @type {?string}
	 */

	fieldSetDefinitionURL: Config.string(),

	/**
	 * @default []
	 * @instance
	 * @memberof Form
	 * @type {?(array|undefined)}
	 */

	fieldSets: Config.array().value([]),

	/**
	 * @default []
	 * @instance
	 * @memberof Form
	 * @type {?(array|undefined)}
	 */

	fieldTypes: Config.array().value([]),

	/**
	 * A map with all translated values available as the form description.
	 * @default 0
	 * @instance
	 * @memberof Form
	 * @type {!array}
	 */

	formInstanceId: Config.number().value(0),

	/**
	 * A map with all translated values available as the form name.
	 * @default undefined
	 * @instance
	 * @memberof Form
	 * @type {!array}
	 */

	functionsMetadata: Config.object().value({}),

	/**
	 * A map with all translated values available as the form name.
	 * @default undefined
	 * @instance
	 * @memberof Form
	 * @type {!array}
	 */

	functionsURL: Config.string(),

	/**
	 * A map with all translated values available as the form description.
	 * @default undefined
	 * @instance
	 * @memberof Form
	 * @type {!array}
	 */

	localizedDescription: Config.object().value({}),

	/**
	 * The context for rendering a layout that represents a form.
	 * @default undefined
	 * @instance
	 * @memberof Form
	 * @type {!array}
	 */

	localizedName: Config.object().value({}),

	/**
	 * The namespace of the portlet.
	 * @default undefined
	 * @instance
	 * @memberof Form
	 * @type {!string}
	 */

	namespace: Config.string().required(),

	/**
	 * Whether the form is published or not
	 * @default false
	 * @instance
	 * @memberof Form
	 * @type {!boolean}
	 */

	published: Config.bool().value(false),

	/**
	 * The url to be redirected when canceling the Element Set edition.
	 * @default undefined
	 * @instance
	 * @memberof Form
	 * @type {!string}
	 */

	redirectURL: Config.string(),

	/**
	 * The rules of a form.
	 * @default undefined
	 * @instance
	 * @memberof Form
	 * @type {!array}
	 */

	rolesURL: Config.string(),

	/**
	 * The rules of a form.
	 * @default undefined
	 * @instance
	 * @memberof Form
	 * @type {!array}
	 */

	rules: Config.array().value([]),

	/**
	 * The path to the SVG spritemap file containing the icons.
	 * @default undefined
	 * @instance
	 * @memberof Form
	 * @type {!boolean}
	 */

	saved: Config.bool(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Form
	 * @type {!string}
	 */

	shareFormInstanceURL: Config.string(),

	/**
	 * Whether to show an alert telling the user about the result of the
	 * "Publish" operation.
	 * @default false
	 * @instance
	 * @memberof Form
	 * @type {!boolean}
	 */

	showPublishAlert: Config.bool().value(false),

	/**
	 * The path to the SVG spritemap file containing the icons.
	 * @default undefined
	 * @instance
	 * @memberof Form
	 * @type {!string}
	 */

	spritemap: Config.string().required(),

	view: Config.string(),
};

Form.STATE = {

	/**
	 * Internal mirror of the pages state
	 * @default _pagesValueFn
	 * @instance
	 * @memberof Form
	 * @type {!array}
	 */

	pages: Config.arrayOf(pageStructure).valueFn('_pagesValueFn'),

	/**
	 * @default _paginationModeValueFn
	 * @instance
	 * @memberof Form
	 * @type {!array}
	 */

	paginationMode: Config.string().valueFn('_paginationModeValueFn'),
};

export default Form;
export {Form};
