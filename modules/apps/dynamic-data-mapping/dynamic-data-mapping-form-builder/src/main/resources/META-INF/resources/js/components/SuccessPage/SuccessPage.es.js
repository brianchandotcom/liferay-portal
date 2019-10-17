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

import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import {setValue} from '../../util/i18n.es';
import templates from './SuccessPage.soy.js';

class SucessPage extends Component {
	prepareStateForRender(state) {
		const {store} = this.context;
		const {editingLanguageId} = store.props;
		const {successPageSettings} = this;
		const {body, title} = successPageSettings;

		return {
			...state,
			body: (body && body[editingLanguageId]) || '',
			title: (title && title[editingLanguageId]) || ''
		};
	}

	_handleSuccessPageUpdated(event) {
		const {dispatch, store} = this.context;
		const {editingLanguageId} = store.props;
		const {delegateTarget} = event;
		const {
			dataset: {setting},
			value
		} = delegateTarget;
		const {successPageSettings} = this;

		dispatch(
			'successPageChanged',
			setValue(successPageSettings, editingLanguageId, setting, value)
		);
	}
}

SucessPage.STATE = {
	/**
	 * @instance
	 * @memberof SucessPage
	 * @type {?object}
	 */

	successPageSettings: Config.object().value({
		body: {},
		title: {}
	})
};

Soy.register(SucessPage, templates);

export default SucessPage;
