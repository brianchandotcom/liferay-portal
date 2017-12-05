import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import templates from './LayoutPageTemplateFragment.soy';

/**
 * LayoutPageTemplateFragment
 */
class LayoutPageTemplateFragment extends Component {
	/**
	 * @inheritDoc
	 */
	created() {
		this._fetchFragmentContent(this.fragmentEntryId);
	}

	/**
	 * @inheritDoc
	 */
	rendered() {
		if (!this._loading) {
			this.refs.style.innerHTML = this._css;
			this.refs.script.innerHTML = this._js;
		}
	}

	/**
	 * @inheritDoc
	 * @param {object} changes
	 */
	willUpdate(changes) {
		if (changes.fragmentEntryId) {
			this._fetchFragmentContent(changes.fragmentEntryId.newVal);
		}
	}

	/**
	 * Fetches a fragment entry from the given ID, and stores the HTML,
	 * CSS and JS result into component properties.
	 * @param {!string} fragmentEntryId
	 * @private
	 */
	_fetchFragmentContent(fragmentEntryId) {
		const formData = new FormData();

		formData.append(
			`${this.portletNamespace}fragmentEntryId`,
			fragmentEntryId
		);

		this._css = '';
		this._html = '';
		this._js = '';
		this._loading = true;

		fetch(this.fragmentEntryURL, {
			body: formData,
			credentials: 'include',
			method: 'POST',
		})
			.then(response => response.json())
			.then(responseContent => {
				this._css = responseContent.css || '';
				this._html = responseContent.html || '';
				this._js = responseContent.js || '';
				this._loading = false;
			});
	}

	/**
	 * Callback executed when the fragment remove button is clicked.
	 * It emits a 'fragmentRemoveButtonClick' event with the fragment index.
	 * @private
	 */
	_handleFragmentRemoveButtonClick() {
		this.emit('fragmentRemoveButtonClick', {
			fragmentIndex: this.index,
		});
	}
}

/**
 * State definition.
 * @type {!Object}
 * @static
 */
LayoutPageTemplateFragment.STATE = {
	/**
	 * Fragment entry ID
	 * @default undefined
	 * @instance
	 * @memberOf LayoutPageTemplateEditor
	 * @type {!string}
	 */
	fragmentEntryId: Config.string().required(),

	/**
	 * URL for getting a fragment entry information.
	 * @default undefined
	 * @instance
	 * @memberOf LayoutPageTemplateEditor
	 * @type {!string}
	 */
	fragmentEntryURL: Config.string().required(),

	/**
	 * Fragment index
	 * @default undefined
	 * @instance
	 * @memberOf LayoutPageTemplateFragment
	 * @type {!number}
	 */
	index: Config.number().required(),

	/**
	 * Fragment name
	 * @default undefined
	 * @instance
	 * @memberOf LayoutPageTemplateFragment
	 * @type {!string}
	 */
	name: Config.string().required(),

	/**
	 * Portlet namespace needed for prefixing form inputs
	 * @default undefined
	 * @instance
	 * @memberOf LayoutPageTemplateEditor
	 * @type {!string}
	 */
	portletNamespace: Config.string().required(),

	/**
	 * Fragment spritemap
	 * @default undefined
	 * @instance
	 * @memberOf LayoutPageTemplateFragment
	 * @type {!string}
	 */
	spritemap: Config.string().required(),

	/**
	 * Flag indicating that fragment information is being loaded
	 * @default false
	 * @instance
	 * @memberOf LayoutPageTemplateFragment
	 * @private
	 * @type {bool}
	 */
	_loading: Config.bool().value(false),

	/**
	 * Fragment CSS to be rendered
	 * @default ''
	 * @instance
	 * @memberOf LayoutPageTemplateFragment
	 * @private
	 * @type {string}
	 */
	_css: Config.string()
		.internal()
		.value(''),

	/**
	 * Fragment HTML to be rendered
	 * @default ''
	 * @instance
	 * @memberOf LayoutPageTemplateFragment
	 * @private
	 * @type {string}
	 */
	_html: Config.string()
		.internal()
		.value(''),

	/**
	 * Fragment JS to be rendered
	 * @default ''
	 * @instance
	 * @memberOf LayoutPageTemplateFragment
	 * @private
	 * @type {string}
	 */
	_js: Config.string()
		.internal()
		.value(''),
};

Soy.register(LayoutPageTemplateFragment, templates);

export {LayoutPageTemplateFragment};
export default LayoutPageTemplateFragment;
