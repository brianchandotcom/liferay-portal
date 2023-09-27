/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
