import async from 'metal/src/async/async';
import core from 'metal/src/core';
import dom from 'metal-dom/src/dom';
import State from 'metal-state/src/State';

/**
 * TopSearch
 *
 * This class creates a basic component that enhances the default behaviour of the
 * search portlet form providing proper accessibility and a subtle integration with the
 * Porygon theme.
 */
class TopSearch extends State {
	/**
	 * @inheritDoc
	 */
	constructor() {
		super();

		this.search_ = dom.toElement('#search');
		this.searchIcon_ = dom.toElement('#banner .btn-search');
		this.searchInput_ = dom.toElement('#banner .search-input');

		if (this.searchInput_) {
			dom.addClasses(this.searchInput_, 'visible-xs');

			dom.on(this.searchIcon_, 'click', (e) => this.onSearchIconClick_(e));
			dom.on(this.searchInput_, 'blur', (e) => this.onSearchInputBlur_(e));
			dom.on(this.searchInput_, 'keydown', (e) => this.onSearchInputKeyDown_(e));

			this.on('visibleChanged', (e) => this.onVisibleChanged_(e));
		}
		else {
			this.searchIcon_.setAttribute('disabled', 'disabled');

			dom.addClasses(this.searchIcon_, 'disabled');
		}
	}

	/**
	 * Toggles the visibility of the search component when the user
	 * clicks on the search icon
	 *
	 * @param  {MouseEvent} event
	 * @protected
	 */
	onSearchIconClick_(event) {
		this.visible = dom.hasClass(this.searchInput_, 'visible-xs');
	}

	/**
	 * Hides the search component when the user leaves the search input
	 *
	 * @param  {BlurEvent} event
	 * @protected
	 */
	onSearchInputBlur_(event) {
		async.nextTick(
			() => {
				let stateActiveElementBlur = document.activeElement !== this.searchIcon_ && document.activeElement !== this.searchInput_;

				if (stateActiveElementBlur && (!this.searchInput_.value || this.searchInput_.value === '')) {
					this.visible = false;
				}
			}
		);
	}

	/**
	 * Hides the search component when the user presses the ESC key
	 *
	 * @param  {KeyboardEvent} event
	 * @protected
	 */
	onSearchInputKeyDown_(event) {
		this.visible = event.keyCode !== 27;
	}

	/**
	 * Updates the UI of the component to react to changes in the visible state
	 *
	 * @param  {Event} event
	 * @protected
	 */
	onVisibleChanged_(event) {
		let updateFn = this.visible ? 'addClasses' : 'removeClasses';

		dom[updateFn](document.body, 'search-opened');
		dom[updateFn](this.search_, 'focus');
		dom[updateFn](this.searchIcon_, 'open');

		dom.toggleClasses(this.searchInput_, 'visible-xs');

		if (this.visible) {
			this.searchInput_.focus();
		}
	}
}

/**
 * State definition.
 * @type {!Object}
 * @static
 */
TopSearch.STATE = {
	/**
	 * Indicates if the component is visible or not.
	 * @type {Object}
	 */
	visible: {
		validator: core.isBoolean,
		value: false
	}
};

export default TopSearch;