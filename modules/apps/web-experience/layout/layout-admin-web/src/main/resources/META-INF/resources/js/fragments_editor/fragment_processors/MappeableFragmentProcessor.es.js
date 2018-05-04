import dom from 'metal-dom';
import {EventHandler} from 'metal-events';

class MappeableFragmentProcessor {

	/**
	 * @inheritDoc
	 * @review
	 */

	constructor(fragmentEntryLink) {
		this.fragmentEntryLink = fragmentEntryLink;

		this._eventHandler = new EventHandler();
		this._handleEditableClick = this._handleEditableClick.bind(this);
	}

	/**
	 * @inheritDoc
	 * @review
	 */

	dispose() {
		this._removeListeners();
	}

	/**
	 * Finds an associated editor for a given editable id
	 * @return {null}
	 * @review
	 */

	findEditor() {
		return null;
	}

	/**
	 * @inheritDoc
	 * @review
	 */

	process() {
		this._removeListeners();

		if (this.fragmentEntryLink.showMapping) {
			this._eventHandler.add(
				dom.delegate(
					this.fragmentEntryLink.refs.content,
					'click',
					'lfr-editable',
					this._handleEditableClick
				)
			);
		}
	}

	/**
	 * Handle an editable element click
	 * @private
	 * @review
	 */

	_handleEditableClick(event) {
		this.fragmentEntryLink.emit(
			'mappeableFieldClicked',
			{
				editableId: event.delegateTarget.id,
				fragmentEntryLinkId: this.fragmentEntryLink.fragmentEntryLinkId
			}
		);
	}

	/**
	 * Remove all listeners added to the event handler
	 * @private
	 * @review
	 */

	_removeListeners() {
		this._eventHandler.removeAllListeners();
	}
}

export {MappeableFragmentProcessor};
export default MappeableFragmentProcessor;