import Component from 'metal-component';
import Soy from 'metal-soy';
import debounce from 'metal-debounce';
import {Config} from 'metal-state';

import templates from './FragmentPreview.soy';

/**
 * Defined ratios for preview sizing
 * @type {Object}
 */
const SIZE_RATIO = {
	desktop: { width: 16, height: 9 },
	'tablet-portrait': { width: 4, height: 3 },
	'mobile-portrait': { width: 10, height: 16 },
};

/**
 * Available preview sizes
 * @type {Array<string>}
 */
const PREVIEW_SIZES = Object.keys(SIZE_RATIO);

/**
 * Component that renders the preview of a Fragment.
 * It allows modifying the preview with an update method.
 */
class FragmentPreview extends Component {
	/**
	 * @inheritDoc
	 */
	attached() {
		this._updatePreview = debounce(this._updatePreview.bind(this), 100);

		this._updatePreviewSize = debounce(
			this._updatePreviewSize.bind(this),
			100,
		);

		window.addEventListener('resize', this._updatePreviewSize);

		this.on('cssChanged', this._updatePreview);
		this.on('htmlChanged', this._updatePreview);
		this.on('jsChanged', this._updatePreview);
	}

	/**
	 * @inheritDoc
	 */
	detached() {
		window.removeEventListener('resize', this._updatePreviewSize);

		this.off('cssChanged', this._updatePreview);
		this.off('htmlChanged', this._updatePreview);
		this.off('jsChanged', this._updatePreview);
	}

	/**
	 * @inheritDoc
	 */
	shouldUpdate(changes) {
		return changes._currentPreviewSize || changes._previewContent;
	}

	/**
	 * Changes the preview size
	 * @param {Event} event
	 * @protected
	 */
	_handlePreviewSizeButtonClick(Event) {
		this._currentPreviewSize =
			event.delegateTarget.dataset.previewSize || null;
	}

	/**
	 * Sets the previewSize property and queues an update
	 * @param {string} previewSize
	 * @protected
	 * @return {string}
	 */
	_setPreviewSize(previewSize) {
		this._updatePreviewSize();

		return previewSize;
	}

	/**
	 * Updates the rendered preview with the given content.
	 * Encapsulates the given code inside a frame and renders it.
	 * @protected
	 */
	_updatePreview() {
		this._previewContent =
			'data:text/html;charset=utf-8,' +
			encodeURIComponent(`
			<!DOCTYPE html>
			<html>
			<head>
				<meta name="viewport" content="width=device-width, initial-scale=1">
				<meta charset="utf-8">
				<style>${this.css || ''}</style>
			</head>
			<body>
				${this.html || ''}
				<script>${this.js || ''}</script>
			</body>
			</html>
		`);
	}

	/**
	 * Updates the preview size using the corresponding ratio
	 * @protected
	 */
	_updatePreviewSize() {
		const preview = this.refs.preview;

		if (preview) {
			if (this._currentPreviewSize) {
				const ratio = SIZE_RATIO[this._currentPreviewSize];
				const wrapper = this.refs.wrapper;

				if (ratio && wrapper) {
					const wrapperRect = wrapper.getBoundingClientRect();

					const scale = Math.min(
						wrapperRect.width * 0.9 / ratio.width,
						wrapperRect.height * 0.8 / ratio.height,
					);

					preview.style.width = `${ratio.width * scale}px`;
					preview.style.height = `${ratio.height * scale}px`;
				}
			} else {
				preview.style.width = '';
				preview.style.height = '';
			}
		}
	}
}

/**
 * State definition.
 * @type {!Object}
 * @static
 */
FragmentPreview.STATE = {
	/**
	 * CSS content of the preview
	 * @instance
	 * @memberOf FragmentPreview
	 * @type {!string}
	 */
	css: Config.string().required(),

	/**
	 * HTML content of the preview
	 * @instance
	 * @memberOf FragmentPreview
	 * @type {!string}
	 */
	html: Config.string().required(),

	/**
	 * JS content of the preview
	 * @instance
	 * @memberOf FragmentPreview
	 * @type {!string}
	 */
	js: Config.string().required(),

	/**
	 * Path of the available icons.
	 * @instance
	 * @memberOf FragmentEditor
	 * @type {!string}
	 */
	spritemap: Config.string().required(),

	/**
	 * Processed iframe content
	 * @default ''
	 * @instance
	 * @memberOf FragmentPreview
	 * @protected
	 * @type {?string}
	 */
	_previewContent: Config.string()
		.internal()
		.value(''),

	/**
	 * Ratio of the preview being rendered.
	 * This property is modified internally with the ui buttons
	 * presented to the user, but it can be safely altered externally.
	 * @default 'full'
	 * @instance
	 * @memberOf FragmentPreview
	 * @protected
	 * @type {?string}
	 */
	_currentPreviewSize: Config.oneOf(PREVIEW_SIZES)
		.internal()
		.value(null)
		.setter('_setPreviewSize'),

	/**
	 * List of available sizes
	 * @default PREVIEW_SIZES
	 * @instance
	 * @memberOf FragmentPreview
	 * @protected
	 * @type {?Array<string>}
	 */
	_previewSizes: Config.array()
		.internal()
		.value(PREVIEW_SIZES),
};

Soy.register(FragmentPreview, templates);

export {FragmentPreview};
export default FragmentPreview;