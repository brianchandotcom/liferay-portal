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

'use strict';

import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

import template from './AutocompleteItem.soy';

class AutocompleteItem extends Component {
	processQuery() {
		const regex = new RegExp(`(.*?)(${this.query})(.*)`, 'gmi');
		const results = regex.exec(this.text);

		if (results) {
			this.updateHighlightedText(
				results.map((element) => element.toString())
			);
		}
		else {
			this.reinitializeTextGroups();
		}

		return !!results;
	}

	reinitializeTextGroups() {
		this.firstGroup = this.text.toString();
		this.secondGroup = null;
		this.thirdGroup = null;

		return false;
	}

	syncQuery() {
		this.processQuery();
	}

	syncText() {
		this.processQuery();
	}

	updateHighlightedText(results) {
		this.firstGroup = results[1] || null;
		this.secondGroup = results[2] || null;
		this.thirdGroup = results[3] || null;

		return true;
	}
}

Soy.register(AutocompleteItem, template);

AutocompleteItem.STATE = {
	firstGroup: Config.string().internal(),
	query: Config.string(),
	secondGroup: Config.string().internal(),
	text: Config.any(),
	thirdGroup: Config.string().internal(),
};

export {AutocompleteItem};
export default AutocompleteItem;
