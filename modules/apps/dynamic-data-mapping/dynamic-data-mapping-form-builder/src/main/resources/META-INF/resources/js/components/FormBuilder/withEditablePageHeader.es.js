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

import './EditablePageHeader.soy';

import {PagesVisitor} from 'dynamic-data-mapping-form-renderer';
import {EventHandler} from 'metal-events';
import Component from 'metal-jsx';
import {Config} from 'metal-state';

import {focusedFieldStructure, pageStructure} from '../../util/config.es';
import {sub} from '../../util/strings.es';

const withEditablePageHeader = ChildComponent => {
	class EditablePageHeader extends Component {
		attached() {
			this._eventHandler = new EventHandler();

			this._eventHandler.add(
				this.delegate(
					'input',
					'.form-builder-page-header-title',
					this._handlePageTitleChanged.bind(this)
				),
				this.delegate(
					'input',
					'.form-builder-page-header-description',
					this._handlePageDescriptionChanged.bind(this)
				)
			);
		}

		disposed() {
			this._eventHandler.removeAllListeners();
		}

		getPages() {
			const {pages} = this.props;
			const total = pages.length;
			const visitor = new PagesVisitor(pages);

			return visitor.mapPages((page, pageIndex) => {
				return {
					...page,
					headerRenderer: 'editable',
					pageIndex,
					placeholder: sub(
						Liferay.Language.get('untitled-page-x-of-x'),
						[pageIndex + 1, total]
					),
					total
				};
			});
		}

		render() {
			return (
				<div>
					<ChildComponent {...this.props} pages={this.getPages()} />
				</div>
			);
		}

		_handlePageDescriptionChanged(event) {
			const {activePage, editingLanguageId, pages} = this.props;
			const {delegateTarget} = event;
			const {dispatch} = this.context;
			const value = delegateTarget.value;
			const visitor = new PagesVisitor(pages);

			dispatch(
				'pagesUpdated',
				visitor.mapPages((page, pageIndex) => {
					if (pageIndex === activePage) {
						page = {
							...page,
							description: value,
							localizedDescription: {
								...page.localizedDescription,
								[editingLanguageId]: value
							}
						};
					}

					return page;
				})
			);
		}

		_handlePageTitleChanged(event) {
			const {activePage, editingLanguageId, pages} = this.props;
			const {delegateTarget} = event;
			const {dispatch} = this.context;
			const value = delegateTarget.value;
			const visitor = new PagesVisitor(pages);

			dispatch(
				'pagesUpdated',
				visitor.mapPages((page, pageIndex) => {
					if (pageIndex === activePage) {
						page = {
							...page,
							localizedTitle: {
								...page.localizedTitle,
								[editingLanguageId]: value
							},
							title: value
						};
					}

					return page;
				})
			);
		}
	}

	EditablePageHeader.PROPS = {
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
		 * @default undefined
		 * @instance
		 * @memberof FormBuilder
		 * @type {?string}
		 */

		fieldSetDefinitionURL: Config.string(),

		/**
		 * @default []
		 * @instance
		 * @memberof FormBuilder
		 * @type {?(array|undefined)}
		 */

		fieldSets: Config.array().value([]),

		/**
		 * @default []
		 * @instance
		 * @memberof FormBuilder
		 * @type {?(array|undefined)}
		 */

		fieldTypes: Config.array().value([]),

		/**
		 * @default {}
		 * @instance
		 * @memberof FormBuilder
		 * @type {?object}
		 */

		focusedField: focusedFieldStructure.value({}),

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
			title: Config.object()
		}).value({}),

		/**
		 * @default undefined
		 * @instance
		 * @memberof FormBuilder
		 * @type {?string}
		 */

		view: Config.string()
	};

	return EditablePageHeader;
};

export default withEditablePageHeader;
