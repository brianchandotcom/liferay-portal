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
		this._fetchFragmentContent(this.fragmentEntryId, this.index);
	}

	/**
	 * After each render, script tags need to be reapended to the DOM
	 * in order to trigger an execution (content changes do not trigger it).
	 * @inheritDoc
	 */
	rendered() {
		if (this.refs.content) {
			this.refs.content.querySelectorAll('script').forEach(script => {
				const parentNode = script.parentNode;
				const newScript = document.createElement('script');

				newScript.innerHTML = script.innerHTML;
				parentNode.removeChild(script);
				parentNode.appendChild(newScript);
			});
		}
	}

	/**
	 * @inheritDoc
	 * @param {object} changes
	 */
	willUpdate(changes) {
		if (changes.fragmentEntryId || changes.index) {
			const fragmentEntryId = changes.fragmentEntryId
				? changes.fragmentEntryId.newVal
				: this.fragmentEntryId;
			const fragmentEntryInstanceId = changes.index
				? changes.index.newVal
				: this.index;

			this._fetchFragmentContent(
				fragmentEntryId, fragmentEntryInstanceId);
		}
	}

	/**
	 * Fetches a fragment entry from the given ID, and stores the HTML,
	 * CSS and JS result into component properties.
	 * @param {!string} fragmentEntryId
	 * @param {!string} fragmentEntryInstanceId
	 * @private
	 */
	_fetchFragmentContent(fragmentEntryId, fragmentEntryInstanceId) {
		const formData = new FormData();

		formData.append(
			`${this.portletNamespace}fragmentEntryId`,
			fragmentEntryId
		);
		formData.append(
			`${this.portletNamespace}fragmentEntryInstanceId`,
			fragmentEntryInstanceId
		);

		this._loading = true;

		fetch(this.renderFragmentEntryURL, {
			body: formData,
			credentials: 'include',
			method: 'POST',
		})
			.then(response => response.json())
			.then(response => {
				this._content = Soy.toIncDom(response.content);
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
	 * URL for getting a fragment render result.
	 * @default undefined
	 * @instance
	 * @memberOf LayoutPageTemplateEditor
	 * @type {!string}
	 */
	renderFragmentEntryURL: Config.string().required(),

	/**
	 * Fragment spritemap
	 * @default undefined
	 * @instance
	 * @memberOf LayoutPageTemplateFragment
	 * @type {!string}
	 */
	spritemap: Config.string().required(),

	/**
	 * Fragment content to be rendered
	 * @default function(){}
	 * @instance
	 * @memberOf LayoutPageTemplateFragment
	 * @private
	 * @type {function}
	 */
	_content: Config.func()
		.internal()
		.value(Soy.toIncDom('')),

	/**
	 * Flag indicating that fragment information is being loaded
	 * @default false
	 * @instance
	 * @memberOf LayoutPageTemplateFragment
	 * @private
	 * @type {boolean}
	 */
	_loading: Config.bool().value(false),
};

Soy.register(LayoutPageTemplateFragment, templates);

export {LayoutPageTemplateFragment};
export default LayoutPageTemplateFragment;
