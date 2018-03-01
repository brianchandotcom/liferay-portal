import core from 'metal';
import debounce from 'metal-debounce';
import dom from 'metal-dom';
import { CancellablePromise as Promise } from 'metal-promise';
import { Align } from 'metal-position';
import AutocompleteBase from './AutocompleteBase.es';
import Soy from 'metal-soy';

import 'frontend-js-web/liferay/compat/list/List.es';
import templates from './Autocomplete.soy';

const DOWN = 40;
const ENTER = 13;
const SPACE = 32;
const UP = 38;

/*
 * Autocomplete component.
 */
class Autocomplete extends AutocompleteBase {
	/**
	 * @inheritDoc
	 */
	attached() {
		super.attached();
		this.setAriaAttributes_();
		this.eventHandler_.add(dom.on(this.inputElement, 'focus', this.handleInputFocus_.bind(this)));
		this.eventHandler_.add(dom.on(document, 'click', this.handleDocClick_.bind(this)));
		this.eventHandler_.add(dom.on(window, 'resize', debounce(this.handleWindowResize_.bind(this), 100)));
		this.eventHandler_.add(dom.on(this.inputElement, 'keydown', this.handleKeyDown_.bind(this)));
		this.eventHandler_.add(this.getList().on('rendered', this.handleListRender_.bind(this)));
		if (this.visible) {
			this.align();
		}
	}

	/**
	 * Aligns main element to the input element.
	 */
	align() {
		this.element.style.width = this.inputElement.offsetWidth + 'px';
		var position = Align.align(this.element, this.inputElement, Align.Bottom, this.autoBestAlign);

		dom.removeClasses(this.element, this.positionCss_);
		switch (position) {
			case Align.Top:
			case Align.TopLeft:
			case Align.TopRight:
				this.positionCss_ = 'autocomplete-top';
				break;
			case Align.Bottom:
			case Align.BottomLeft:
			case Align.BottomRight:
				this.positionCss_ = 'autocomplete-bottom';
				break;
			default:
				this.positionCss_ = null;

		}
		dom.addClasses(this.element, this.positionCss_);
	}

	/**
	 * Actives an option of the suggestion list by inform an index.
	 * @param {number} index
	 * @protected
	 */
	activateListItem_(index) {
		let option = this.currentList_[index];
		dom.removeClasses(this.currentList_[this.activeIndex_], 'active');
		this.activeIndex_ = index;
		this.inputElement.setAttribute('aria-activedescendant', option.getAttribute('id'));
		dom.addClasses(option, 'active');
	}

	/**
	 * Returns the previous index or the last one if the active index was the first.
	 * @protected
	 * @return {number} Index
	 */
	decreaseIndex_() {
		return this.activeIndex_ === 0 ? this.getLastIndex_() : this.activeIndex_ - 1;
	}

	/**
	 * Returns the last index of the list.
	 * @protected
	 * @return {number} Index
	 */
	getLastIndex_() {
		return this.getList().items.length - 1;
	}

	/**
	 * Returns the `List` component being used to render the matched items.
	 * @return {!List}
	 */
	getList() {
		return this.components.list;
	}

	/**
	 * Handles action keys interactions.
	 * @param {!Event} event
	 * @protected
	 */
	handleActionKeys_() {
		let selectedItem = this.getList().items[this.activeIndex_];
		this.selectOption_(selectedItem);
	}

	/**
	 * Handles `click` events, stopping their propagation.
	 * @param {!Event} event
	 * @protected
	 */
	handleClick_(event) {
		event.stopPropagation();
	}

	/**
	 * Handles document click in order to hide autocomplete. If input element is
	 * focused autocomplete will not hide.
	 * @param {!Event} event
	 */
	handleDocClick_() {
		if (document.activeElement === this.inputElement) {
			return;
		}
		this.visible = false;
	}

	/**
	 * Handles input focus.
	 * @param {!Event} event
	 */
	handleInputFocus_() {
		this.request(this.inputElement.value);
	}

	/**
	 * Executed after List rendering.
	 * @param {number} index
	 * @protected
	 */
	handleListRender_() {
		if (this.visible) {
			this.currentList_ = this.element.querySelectorAll('.listitem');
			this.activateListItem_(0);
		}
	}

	/**
	 * Handles a `keydown` event on this component. Handles keyboard controls.
	 * @param {!Event} event
	 * @protected
	 */
	handleKeyDown_(event) {
		if (this.visible) {
			switch (event.keyCode) {
				case UP:
					this.activateListItem_(this.decreaseIndex_());
					event.preventDefault();
					break;
				case DOWN:
					this.activateListItem_(this.increaseIndex_());
					event.preventDefault();
					break;
				case ENTER:
				case SPACE:
					this.handleActionKeys_();
					event.preventDefault();
				break;
			}
		}
	}

	/**
	 * Handles window resize events. Realigns the autocomplete results list to
	 * the input field.
	 */
	handleWindowResize_() {
		if (this.visible) {
			this.align();
		}
	}

	/**
	 * Returns the next index or zero if the active index was the last.
	 * @protected
	 * @return {number} Index
	 */
	increaseIndex_() {
		return this.activeIndex_ === this.getLastIndex_() ? 0 : this.activeIndex_ + 1;
	}

	/**
	 * Listens to the itemSelected event and it tells autocomplete which
	 * element was selected.
	 * @param {!Element} item The list selected item.
	 * @protected
	 */
	onListItemSelected_(item) {
		let selectedIndex = parseInt(item.getAttribute('data-index'), 10);
		let selectedItem = this.getList().items[selectedIndex];
		this.selectOption_(selectedItem);
	}

	/**
	 * @inheritDoc
	 */
	request(query) {
		if (this.autocompleteClosing_) {
			// While closing the input element will be focused, causing another
			// request. This request should be ignored though, since we wish to close
			// the dropdown list, not open it again.
			return;
		}

		var self = this;
		return super.request(query).then(function(data) {
			if (data) {
				data.forEach(self.assertItemObjectStructure_);
				self.getList().items = data;
			}
			self.visible = !!(data && data.length > 0);
		});
	}

	/**
	 * Emits a `select` event with the information about the selected item and
	 * hides the list element.
	 * @param {!Object} item The list selected item.
	 * @protected
	 */
	selectOption_(selectedItem) {
		this.autocompleteClosing_ = true;
		this.emit('select', selectedItem);
		this.visible = false;
		this.autocompleteClosing_ = false;
	}

	/**
	 * Set the required ARIA attributes to the inputElement.
	 * @protected
	 */
	setAriaAttributes_() {
		this.inputElement.setAttribute('aria-activedescendant', '');
		this.inputElement.setAttribute('aria-autocomplete', 'list');
		this.inputElement.setAttribute('aria-haspopup', true);
		this.inputElement.setAttribute('aria-owns', this.listId);
		this.inputElement.setAttribute('role', 'combobox');
	}

	/**
	 * Synchronization logic for `visible` state.
	 * @param {boolean} visible
	 */
	syncVisible(visible) {
		super.syncVisible(visible);

		if (visible) {
			this.align();
		}
	}

	/**
	 * Asserts that formatted data is valid. Throws error if item is not in the
	 * valid syntax.
	 * @param {*} item
	 * @protected
	 */
	assertItemObjectStructure_(item) {
		if (!core.isObject(item)) {
			throw new Promise.CancellationError('Autocomplete item must be an object');
		}
		if (!item.hasOwnProperty('textPrimary')) {
			throw new Promise.CancellationError('Autocomplete item must be an object with \'textPrimary\' key');
		}
	}
}
Soy.register(Autocomplete, templates);

/**
 * State definition.
 * @type {!Object}
 * @static
 */
Autocomplete.STATE = {
	/**
	 * Activate or Deactivate the suggestion of the best align region. If true,
	 * the component will try to find a better region to align, otherwise,
	 * it will keep the position at the bottom.
	 * @type {boolean}
	 * @default true.
	 */
	autoBestAlign: {
		value: true,
		validator: core.isBoolean
	},

	/**
	 * Function that converts a given item to the format that should be used by
	 * the autocomplete.
	 * @type {!function()}
	 */
	format: {
		value: function(item) {
			if (core.isString(item)) {
				item = {
					textPrimary: item
				};
			}
			if (core.isObject(item) && !item.text) {
				item.text = item.textPrimary;
			}
			return item;
		}
	}
};

export { Autocomplete };
export default Autocomplete;