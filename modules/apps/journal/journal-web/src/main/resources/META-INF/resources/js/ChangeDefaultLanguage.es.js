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

import 'clay-dropdown';
import 'clay-label';
import 'metal';
import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './ChangeDefaultLanguage.soy';

/**
 * ChangeDefaultLanguage
 */

class ChangeDefaultLanguage extends Component {
	_itemClicked(event) {
		const defaultLanguage = event.data.item.label;

		this.defaultLanguage = defaultLanguage;
		this.languages = this.languages.map(language => {
			return Object.assign({}, language, {
				checked: language.label === defaultLanguage
			});
		});

		const dropdown = event.target.refs.dropdown.refs.portal.element;
		const item = dropdown.querySelector(
			`li[data-value="${defaultLanguage}"]`
		);

		Liferay.fire('inputLocalized:defaultLocaleChanged', {
			item
		});
	}
}

Soy.register(ChangeDefaultLanguage, templates);

export default ChangeDefaultLanguage;
