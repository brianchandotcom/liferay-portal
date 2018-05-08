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
 * @review
 */
class DiffVersionComparator extends Component {
	/**
	 * Returns diffVerson object of passed version.
	 * @param {String} version
	 * @protected
	 * @return {Object}
	 * @review
	 */
	findDiffVersion_(version) {
		return this.diffVersions.find(
			diffVersion => {
				return diffVersion.version === version;
			}
		);
	}

	/**
	 * Handles click event of close button on version filter header.
	 * @param {Event} event
	 * @protected
	 * @review
	 */
	handleCloseFilterClick_(event) {
		this.selectedVersion = null;

		this.loadDiffHtmlResults_(this.targetVersion);
	}

	/**
	 * Handles change event of language select element. Submits form to change
	 * language.
	 * @protected
	 * @review
	 */
	handleLanguageChange_() {
		submitForm(this.refs.form);
	}

	/**
	 * Handles change event of search input. Filters results based on input.
	 * @param {Event} event
	 * @protected
	 * @review
	 */
	handleSearchChange_(event) {
		const query = event.target.value.toLowerCase();

		let resultsLength = 0;

		const diffVersions = this.diffVersions.map(
			diffVersion => {
				const {label, userName} = diffVersion;

				const hidden = label.toLowerCase().indexOf(query) === -1 &&
					userName.toLowerCase().indexOf(query) === -1;

				if (!hidden) {
					resultsLength++;
				}

				return Object.assign(
					{},
					diffVersion,
					{
						hidden
					}
				);
			}
		);

		this.diffVersions = diffVersions;
		this.resultsLength = resultsLength;
	}

	/**
	 * Handles click event of version items. Sets `selectedVersion` and loads
	 * new `diffHtmlResults` value based on newly selected version.
	 * @param {Event} event
	 * @protected
	 * @review
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
	 * @param {String} targetVersion
	 * @protected
	 * @review
	 */
	loadDiffHtmlResults_(targetVersion) {
		const {portletNamespace} = this;

		const params = new MultiMap();

		params.add(`${portletNamespace}filterSourceVersion`, this.sourceVersion);
		params.add(`${portletNamespace}filterTargetVersion`, targetVersion);

		Ajax.request(this.resourceURL, 'get', null, null, params)
			.then(
				xhrResponse => {
					this.diffHtmlResults = xhrResponse.response;
				}
			)
			.catch(
				() => {
					this.diffHtmlResults = Liferay.Language.get('an-error-occurred-while-processing-the-requested-resource');
				}
			);
	}

	/**
	 * Setter for the `diffHtmlResults` STATE property. Necessary because server
	 * returns html values as object.
	 * @param {Object|String} value
	 * @protected
	 * @review
	 */
	setterDiffHtmlResults_(value) {
		if (isObject(value)) {
			value = value.value.content;
		}

		return value;
	}
}

DiffVersionComparator.STATE = {
	/**
	 * List of locales that are available for the current asset. Used to render
	 * locale selector.
	 * @instance
	 * @memberof DiffVersionComparator
	 * @review
	 * @type {Array.<Object>}
	 */
	availableLocales: Config.array(),

	/**
	 * HTML diff results that have been calculated by the server.
	 * @instance
	 * @memberof DiffVersionComparator
	 * @review
	 * @type {String|Object}
	 */
	diffHtmlResults: Config.setter('setterDiffHtmlResults_'),

	/**
	 * Array of diffVersion objects used for rendering dropdown menus and target
	 * list.
	 * @instance
	 * @memberof DiffVersionComparator
	 * @review
	 * @type {Array.<Object>}
	 */
	diffVersions: Config.array(),

	/**
	 * Currently selected language id.
	 * @instance
	 * @memberof DiffVersionComparator
	 * @review
	 * @type {String}
	 */
	languageId: Config.string(),

	/**
	 * Portlet namespace.
	 * @instance
	 * @memberof DiffVersionComparator
	 * @review
	 * @type {String}
	 */
	portletNamespace: Config.string(),

	/**
	 * Portlet resource URL.
	 * @instance
	 * @memberof DiffVersionComparator
	 * @review
	 * @type {String}
	 */
	resourceURL: Config.string(),

	/**
	 * Total length of results after search input is used.
	 * @instance
	 * @memberof DiffVersionComparator
	 * @review
	 * @type {number}
	 */
	resultsLength: Config.number(),

	/**
	 * Currently selected version. Used for version filter header.
	 * @instance
	 * @memberof DiffVersionComparator
	 * @review
	 * @type {Object}
	 */
	selectedVersion: Config.object(),

	/**
	 * Currently selected source version.
	 * @instance
	 * @memberof DiffVersionComparator
	 * @review
	 * @type {String}
	 */
	sourceVersion: Config.string(),

	/**
	 * Determines if version filter should display.
	 * @default false
	 * @instance
	 * @memberof DiffVersionComparator
	 * @review
	 * @type {boolean}
	 */
	showVersionFilter: Config.bool().value(false),

	/**
	 * Currently selected target version.
	 * @instance
	 * @memberof DiffVersionComparator
	 * @review
	 * @type {String}
	 */
	targetVersion: Config.string(),
};

Soy.register(DiffVersionComparator, templates);

export default DiffVersionComparator;
export {DiffVersionComparator};