import Ajax from 'metal-ajax';
import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';
import {MultiMap} from 'metal-structs';
import {isObject} from 'metal';

import templates from './DiffVersionComparator.soy';

/**
 * DiffVersionComparator
 *
 * Adds functionality for dynamically comparing the html of differing versions
 * of assets.
 */
class DiffVersionComparator extends Component {
	/**
	 * Returns diffVerson object of passed version.
	 *
	 * @param {String} version
	 * @return {Object}
	 */
	findDiffVersion_(version) {
		return this.diffVersions.find(diffVersion => {
			return diffVersion.version === version;
		});
	}

	/**
	 * Handles click event of close button on version filter header.
	 *
	 * @param {Event} event
	 */
	handleCloseFilterClick_(event) {
		this.selectedVersion = null;
		this.loadDiffHtmlResults_(this.targetVersion);
	}

	/**
	 * Handles change event of language select element. Submits form to change
	 * language.
	 */
	handleLanguageChange_() {
		submitForm(this.refs.form);
	}

	/**
	 * Handles change event of search input. Filters results based on input.
	 *
	 * @param {Event} event
	 */
	handleSearchChange_(event) {
		const query = event.target.value.toLowerCase();

		let resultsLength = 0;

		const diffVersions = this.diffVersions.map(diffVersion => {
			const {label, userName} = diffVersion;

			const hidden = label.toLowerCase().indexOf(query) === -1 &&
				userName.toLowerCase().indexOf(query) === -1;

			if (!hidden) {
				resultsLength++;
			}

			return Object.assign({}, diffVersion, {
				hidden
			});
		});

		this.diffVersions = diffVersions;
		this.resultsLength = resultsLength;
	}

	/**
	 * Handles click event of version items. Sets `selectedVersion` and loads
	 * new `diffHtmlResults` value based on newly selected version.
	 *
	 * @param {Event} event
	 */
	handleVersionItemClick_(event) {
		const targetVersion = event.delegateTarget.getAttribute('data-version');

		this.selectedVersion = this.findDiffVersion_(targetVersion);
		this.loadDiffHtmlResults_(targetVersion);
	}

	/**
	 * Submits Ajax request for new `htmlDiffResults` value that will show the
	 * diff between the `targetVersion` argument and currently selected
	 * `sourceVersion`.
	 *
	 * @param {String} targetVersion
	 */
	loadDiffHtmlResults_(targetVersion) {
		const {portletNamespace} = this;

		const params = new MultiMap();

		params.add(`${portletNamespace}filterSourceVersion`, this.sourceVersion);
		params.add(`${portletNamespace}filterTargetVersion`, targetVersion);

		Ajax.request(this.resourceURL, 'get', null, null, params)
			.then(xhrResponse => {
				this.diffHtmlResults = xhrResponse.response;
			})
			.catch(() => {
				this.diffHtmlResults = Liferay.Language.get('an-error-occurred-while-processing-the-requested-resource');
			});
	}

	/**
	 * Setter for the `diffHtmlResults` STATE property. Necessary because server
	 * returns html values as object.
	 *
	 * @param {Object|String} value
	 */
	setterDiffHtmlResults_(value) {
		if (isObject(value)) {
			value = value.value.content;
		}

		return value;
	}
}

Soy.register(DiffVersionComparator, templates);

DiffVersionComparator.STATE = {
	/**
	 * List of locales that are available for the current asset. Used to render
	 * locale selector.
	 *
	 * @type {Array.<Object>}
	 */
	availableLocales: Config.array(),

	/**
	 * HTML diff results that have been calculated by the server.
	 *
	 * @type {String|Object}
	 */
	diffHtmlResults: Config.setter('setterDiffHtmlResults_'),

	/**
	 * Array of diffVersion objects used for rendering dropdown menus and target
	 * list.
	 *
	 * @type {Array.<Object>}
	 */
	diffVersions: Config.array(),

	/**
	 * Currently selected language id.
	 *
	 * @type {String}
	 */
	languageId: Config.string(),

	/**
	 * Portlet namespace.
	 *
	 * @type {String}
	 */
	portletNamespace: Config.string(),

	/**
	 * Portlet resource URL.
	 *
	 * @type {String}
	 */
	resourceURL: Config.string(),

	/**
	 * Total length of results after search input is used.
	 *
	 * @type {number}
	 */
	resultsLength: Config.number(),

	/**
	 * Currently selected version. Used for version filter header.
	 *
	 * @type {Object}
	 */
	selectedVersion: Config.object(),

	/**
	 * Currently selected source version.
	 *
	 * @type {String}
	 */
	sourceVersion: Config.string(),

	/**
	 * Determines if version filter should display.
	 *
	 * @type {boolean}
	 */
	showVersionFilter: Config.bool().value(false),

	/**
	 * Currently selected target version.
	 *
	 * @type {String}
	 */
	targetVersion: Config.string(),
};

export default DiffVersionComparator;