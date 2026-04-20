/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClipboardJS from 'clipboard';
import {openToast} from 'frontend-js-components-web';
import {PortletBase} from 'frontend-js-web';

/**
 * @class InfoPanel
 */
class InfoPanel extends PortletBase {

	/**
	 * @inheritdoc
	 * @review
	 */
	attached() {
		this._destroyClipboard();

		const selector = '.dm-infopanel-copy-clipboard';

		const container = this.rootNode;

		if (!container) {
			return;
		}

		this._clipboard = new ClipboardJS(container.querySelectorAll(selector));

		this._clipboard.on('success', this._handleClipboardSuccess.bind(this));
	}

	/**
	 * @inheritdoc
	 * @review
	 */
	disposeInternal() {
		super.disposeInternal();
		this._destroyClipboard();
	}

	_destroyClipboard() {
		if (this._clipboard) {
			this._clipboard.destroy();
			this._clipboard = null;
		}
	}

	_handleClipboardSuccess() {
		openToast({
			message: Liferay.Language.get('copied-link-to-the-clipboard'),
		});
	}
}

export default InfoPanel;
