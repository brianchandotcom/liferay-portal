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

import {LegacyFormBuilder} from 'data-engine-js-components-web';
import compose from 'data-engine-js-components-web/js/utils/compose.es';
import {PagesVisitor} from 'data-engine-js-components-web/js/utils/visitors.es';
import Component from 'metal-jsx';
import {Config} from 'metal-state';

import {pageStructure} from '../../util/config.es';
import withEditablePageHeader from './withEditablePageHeader.es';
import withMultiplePages from './withMultiplePages.es';

/**
 * Builder.
 * @extends Component
 */

class FormBuilderBase extends Component {
	attached() {
		const formBasicInfo = document.querySelector('.ddm-form-basic-info');
		const translationManager = document.querySelector(
			'.ddm-translation-manager'
		);

		if (formBasicInfo && translationManager) {
			formBasicInfo.classList.remove('hide');
			translationManager.classList.remove('hide');
		}
	}

	preparePagesForRender(pages) {
		const visitor = new PagesVisitor(pages);

		return visitor.mapFields(
			(field) => {
				if (
					field.type === 'select' &&
					!field.dataSourceType.includes('manual')
				) {
					field = {
						...field,
						options: [
							{
								label: Liferay.Language.get(
									'dynamically-loaded-data'
								),
								value: 'dynamic',
							},
						],
						value: 'dynamic',
					};
				}

				return {
					...field,
					readOnly: true,
				};
			},
			true,
			true
		);
	}

	render() {
		const {
			activePage,
			allowInvalidAvailableLocalesForProperty,
			allowNestedFields = true,
			dnd,
			editingLanguageId,
			fieldActions,
			fieldTypes,
			focusedField,
			pages,
			paginationMode,
			portletNamespace,
			sidebarOpen,
			spritemap,
			successPageSettings,
			view,
		} = this.props;

		return (
			<div class="ddm-form-builder-wrapper">
				<div
					class={`container ddm-form-builder ${
						sidebarOpen ? 'ddm-form-builder--sidebar-open' : ''
					}`}
				>
					<LegacyFormBuilder
						activePage={activePage}
						allowInvalidAvailableLocalesForProperty={
							allowInvalidAvailableLocalesForProperty
						}
						allowNestedFields={allowNestedFields}
						dnd={dnd}
						editable={true}
						editingLanguageId={editingLanguageId}
						fieldActions={fieldActions}
						fieldTypes={fieldTypes}
						focusedField={focusedField}
						pages={this.preparePagesForRender(pages)}
						paginationMode={paginationMode}
						portletNamespace={portletNamespace}
						ref="FormRenderer"
						spritemap={spritemap}
						successPageSettings={successPageSettings}
						view={view}
					/>
				</div>
			</div>
		);
	}
}

FormBuilderBase.PROPS = {

	/**
	 * @default
	 * @instance
	 * @memberof FormBuilder
	 * @type {?number}
	 */

	activePage: Config.number().value(0),

	/**
	 * @default undefined
	 * @instance
	 * @memberof FormBuilder
	 * @type {?string}
	 */

	defaultLanguageId: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof FormBuilder
	 * @type {?string}
	 */

	editingLanguageId: Config.string(),

	/**
	 * @default []
	 * @instance
	 * @memberof FormBuilder
	 * @type {?array<object>}
	 */

	pages: Config.arrayOf(pageStructure).value([]),

	/**
	 * @instance
	 * @memberof FormBuilder
	 * @type {string}
	 */

	paginationMode: Config.string().required(),

	/**
	 * @instance
	 * @memberof FormBuilder
	 * @type {string}
	 */

	portletNamespace: Config.string().required(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof FormBuilder
	 * @type {!string}
	 */

	spritemap: Config.string().required(),

	/**
	 * @instance
	 * @memberof FormBuilder
	 * @type {object}
	 */

	successPageSettings: Config.shapeOf({
		body: Config.object(),
		enabled: Config.bool(),
		title: Config.object(),
	}).value({}),

	/**
	 * @default undefined
	 * @instance
	 * @memberof FormBuilder
	 * @type {?string}
	 */

	view: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof FormBuilder
	 * @type {?bool}
	 */

	viewMode: Config.bool(),
};

export default compose(
	withEditablePageHeader,
	withMultiplePages
)(FormBuilderBase);

export {FormBuilderBase};
