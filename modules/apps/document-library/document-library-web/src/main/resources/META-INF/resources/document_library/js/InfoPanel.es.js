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

import ClipboardJS from 'clipboard';
import {PortletBase, openToast} from 'frontend-js-web';

/**
 * @class InfoPanel
 */
class InfoPanel extends PortletBase {

	/**
	 * @inheritdoc
	 * @review
	 */
	attached() {
		this._clipboard = new ClipboardJS('.dm-infopanel-copy-clipboard');

		this._clipboard.on('success', this._handleClipboardSuccess.bind(this));
	}

	/**
	 * @inheritdoc
	 * @review
	 */
	disposeInternal() {
		super.disposeInternal();

		this._clipboard.destroy();
	}

	_handleClipboardSuccess() {
		openToast({
			message: Liferay.Language.get('copied-link-to-the-clipboard'),
		});
	}
}

export default InfoPanel;
