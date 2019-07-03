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
			message: Liferay.Language.get('link-copied-to-the-clipboard')
		});
	}
}

export default InfoPanel;
