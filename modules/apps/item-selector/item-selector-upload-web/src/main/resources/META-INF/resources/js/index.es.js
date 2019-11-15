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

import {ItemSelectorRepositoryEntryBrowser} from 'item-selector-taglib';

export default function(props) {
	const itemSelector = new ItemSelectorRepositoryEntryBrowser({...props});

	itemSelector.on('selectedItem', event => {
		console.log(event);
		Liferay.Util.getOpener().Liferay.fire(props.eventName, event);
		Liferay.Util.getOpener().Liferay.fire(props.eventName + 'AddItem', event);
	});
}
