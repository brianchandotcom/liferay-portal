import core from 'metal';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './ListItem.soy.js';

/**
 * List component.
 */
class ListItem extends Component {
	/**
	 * Setter function for the `item` state key.
	 * @param {!Object} item
	 * @protected
	 */
	setterItemFn_(item) {
		if (item.textPrimary && core.isString(item.textPrimary)) {
			item.textPrimary = Soy.toIncDom(item.textPrimary);
		}
		if (item.textSecondary && core.isString(item.textSecondary)) {
			item.textSecondary = Soy.toIncDom(item.textSecondary);
		}
		if (item.avatar && item.avatar.content && core.isString(item.avatar.content)) {
			item.avatar.content = Soy.toIncDom(item.avatar.content);
		}
		if (Array.isArray(item.iconsHtml)) {
			item.iconsHtml = item.iconsHtml.map(Soy.toIncDom);
		}
		return item;
	}
}
Soy.register(ListItem, templates);

/**
 * List state definition.
 * @type {Object}
 * @static
 */
ListItem.STATE = {
	/**
	 * A unique identifier for each item.
	 * @type {string}
	 */
	id: {
		valueFn: () => 'list-component-item' + core.getUid()
	},

	/**
	 * The item to be rendered.
	 * @type {!Object}
	 */
	item: {
		validator: core.isObject,
		setter: 'setterItemFn_'
	},

	/**
	 * The index of the item in the list.
	 * @type {number}
	 */
	index: {
		value: -1
	}
};

export { ListItem };
export default ListItem;