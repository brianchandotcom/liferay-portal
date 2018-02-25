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
	desktop: {
		height: 9,
		width: 16
	},
	'mobile-portrait': {
		height: 16,
		width: 10
	},
	'tablet-portrait': {
		height: 3,
		width: 4
	}
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
		this._updatePreview = debounce(
			this._updatePreview.bind(this),
			500
		);

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
	 * After each render, script tags need to be reapended to the DOM
	 * in order to trigger an execution (content changes do not trigger it).
	 * @inheritDoc
	 */

	rendered() {
		if (this.refs.preview) {
			this.refs.preview.querySelectorAll('script').forEach(
				(script) => {
					const newScript = document.createElement('script');
					const parentNode = script.parentNode;

					newScript.innerHTML = script.innerHTML;

					parentNode.removeChild(script);
					parentNode.appendChild(newScript);
				}
			);
		}
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

	_handlePreviewSizeButtonClick(event) {
		this._currentPreviewSize = event.delegateTarget.dataset.previewSize || null;
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
		if (!this._loading) {
			this._loading = true;

			const formData = new FormData();

			formData.append(`${this.namespace}css`, this.css);
			formData.append(`${this.namespace}html`, this.html);
			formData.append(`${this.namespace}js`, this.js);

			fetch(
				this.renderFragmentEntryURL,
				{
					body: formData,
					credentials: 'include',
					method: 'post'
				}
			).then(
				response => response.json()
			).then(
				response => {
					this._loading = false;
					this._previewContent = Soy.toIncDom(response.content);
				}
			);
		}
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
			}
			else {
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
	 * Namespace of the portlet being used.
	 * Necesary for getting the real inputs which interact with the server.
	 * @instance
	 * @memberOf FragmentEditor
	 * @type {!string}
	 */

	namespace: Config.string().required(),

	/**
	 * Render fragment entry URL
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditor
	 * @type {!string}
	 */

	renderFragmentEntryURL: Config.string().required(),

	/**
	 * Path of the available icons.
	 * @instance
	 * @memberOf FragmentEditor
	 * @type {!string}
	 */

	spritemap: Config.string().required(),

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
	 * Flag for checking if the preview content is being loaded
	 * @default false
	 * @instance
	 * @memberOf FragmentPreview
	 * @protected
	 * @type {boolean}
	 */

	_loading: Config.bool()
		.internal()
		.value(false),

	/**
	 * Processed iframe content
	 * @default ''
	 * @instance
	 * @memberOf FragmentPreview
	 * @protected
	 * @type {function}
	 */

	_previewContent: Config.func()
		.internal()
		.value(Soy.toIncDom('')),

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
		.value(PREVIEW_SIZES)
};

Soy.register(FragmentPreview, templates);

export {FragmentPreview};
export default FragmentPreview;