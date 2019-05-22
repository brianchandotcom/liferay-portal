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
			item: item
		});
	}
}

Soy.register(ChangeDefaultLanguage, templates);

export default ChangeDefaultLanguage;
