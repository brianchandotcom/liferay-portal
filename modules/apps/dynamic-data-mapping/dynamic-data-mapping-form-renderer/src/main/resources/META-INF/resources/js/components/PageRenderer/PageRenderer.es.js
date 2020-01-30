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

import './PaginatedPageRenderer.soy';

import './SimplePageRenderer.soy';

import './TabbedPageRenderer.soy';

import './WizardPageRenderer.soy';

import 'clay-button';

import 'clay-dropdown';

import 'clay-modal';
import core from 'metal';
import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import * as FormSupport from '../FormRenderer/FormSupport.es';
import templates from './PageRenderer.soy';

class PageRenderer extends Component {
	getPage(page) {
		const {editingLanguageId} = this;

		if (core.isObject(page.description)) {
			page = {
				...page,
				description: page.description[editingLanguageId]
			};
		}
		if (core.isObject(page.title)) {
			page = {
				...page,
				title: page.title[editingLanguageId]
			};
		}

		return page;
	}

	isEmptyPage({rows}) {
		let empty = false;

		if (!rows || !rows.length) {
			empty = true;
		} else {
			empty = !rows.some(({columns}) => {
				let hasFields = true;

				if (!columns) {
					hasFields = false;
				} else {
					hasFields = columns.some(column => column.fields.length);
				}
				return hasFields;
			});
		}

		return empty;
	}

	prepareStateForRender(states) {
		return {
			...states,
			empty: this.isEmptyPage(states.page),
			page: this.getPage(states.page)
		};
	}

	_handleFieldBlurred(event) {
		this.emit('fieldBlurred', event);
	}

	_handleFieldClicked({delegateTarget}) {
		const fieldNode = delegateTarget.parentElement.parentElement;
		const indexes = FormSupport.getIndexes(fieldNode);

		this.emit('fieldClicked', {
			...indexes
		});
	}

	_handleFieldEdited(event) {
		this.emit('fieldEdited', event);
	}

	_handleFieldFocused(event) {
		this.emit('fieldFocused', event);
	}
}

PageRenderer.STATE = {
	/**
	 * @instance
	 * @memberof FormPage
	 * @type {?number}
	 */

	activePage: Config.number().value(0),

	/**
	 * @instance
	 * @memberof FormPage
	 * @type {?boolean}
	 */
	editable: Config.bool().value(false),

	/**
	 * @default []
	 * @instance
	 * @memberof FormRenderer
	 * @type {?array<object>}
	 */

	page: Config.object(),

	/**
	 * @default 1
	 * @instance
	 * @memberof FormPage
	 * @type {?number}
	 */

	pageIndex: Config.number().value(0),

	/**
	 * @default undefined
	 * @instance
	 * @memberof FormRenderer
	 * @type {!string}
	 */

	spritemap: Config.string().required()
};

Soy.register(PageRenderer, templates);

export default PageRenderer;
