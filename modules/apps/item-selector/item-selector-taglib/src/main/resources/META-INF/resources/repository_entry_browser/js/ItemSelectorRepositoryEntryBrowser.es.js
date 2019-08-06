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
import {Config} from 'metal-state';

class ItemSelectorRepositoryEntryBrowser extends Component {
	attached() {
		console.log('attached');

		AUI().use('liferay-item-selector-uploader,liferay-item-viewer', A => {
			this._itemViewer = new A.LiferayItemViewer({
				btnCloseCaption: this.closeCaption,
				editItemURL: this.editItemURL,
				links: '', //instance.all('.item-preview'),
				uploadItemURL: this.uploadItemURL
			});

			this._uploadItemViewer = new A.LiferayItemViewer({
				btnCloseCaption: this.closeCaption,
				links: '',
				uploadItemURL: this.uploadItemURL
			});

			this._itemSelectorUploader = new A.LiferayItemSelectorUploader({
				rootNode: this.rootNode
			});

			this._bindEvents();
		});
	}

	_bindEvents() {
		console.log(this._itemViewer);
	}

	_convertMaxFileSize(maxFileSize) {
		return parseInt(maxFileSize);
	}
}

ItemSelectorRepositoryEntryBrowser.STATE = {
	closeCaption: Config.string(),

	editItemURL: Config.string(),

	maxFileSize: Config.oneOfType([Config.number(), Config.string()])
		.setter('_convertMaxFileSize')
		.value(Liferay.PropsValues.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE),

	rootNode: Config.string(),

	uploadItemReturnType: Config.string(),

	uploadItemURL: Config.string(),

	validExtensions: Config.string().value('*')
};

export default ItemSelectorRepositoryEntryBrowser;
