import dom from 'metal-dom';
import {EventHandler} from 'metal-events';

/**
 * Allow having editable image fields inside Fragments
 */

class EditableImageFragmentProcessor {

	/**
	 * @inheritDoc
	 * @review
	 */

	constructor(fragmentEntryLink) {
		this.fragmentEntryLink = fragmentEntryLink;

		this._eventHandler = new EventHandler();
		this._handleImageEditorChange = this._handleImageEditorChange.bind(this);
		this._handleImageSelectorClick = this._handleImageSelectorClick.bind(this);
	}

	/**
	 * @inheritDoc
	 * @review
	 */

	dispose() {
		this._removeListeners();
	}

	/**
	 * @inheritDoc
	 * @review
	 */

	process() {
		this._removeListeners();

		this._eventHandler.add(
			dom.delegate(
				this.fragmentEntryLink.refs.content,
				'click',
				'lfr-editable[type="image"]',
				this._handleImageSelectorClick
			)
		);
	}

	/**
	 * Handle item selector image changes and propagate them with an
	 * "editableChanged" event.
	 * @param {Event} changeEvent
	 * @param {HTMLElement} editableElement
	 * @private
	 */

	_handleImageEditorChange(changeEvent, editableElement) {
		const editableId = editableElement.id;
		const fragmentEntryLinkId = this.fragmentEntryLink.fragmentEntryLinkId;
		const imageElement = editableElement.querySelector('img');
		const selectedItem = changeEvent.newVal;

		if (imageElement && selectedItem) {
			const returnType = selectedItem.returnType;
			let url = '';

			if (returnType === EditableImageFragmentProcessor.RETURN_TYPES.url) {
				url = selectedItem.value;
			}
			else if (returnType === EditableImageFragmentProcessor.RETURN_TYPES.fileEntryItemSelector) {
				url = JSON.parse(selectedItem.value).url;
			}

			imageElement.src = url;

			this.fragmentEntryLink.emit(
				'editableChanged',
				{
					editableId,
					fragmentEntryLinkId,
					value: url
				}
			);
		}
	}

	/**
	 * Handle fragment image selector click
	 * @param {MouseEvent} clickEvent Click event.
	 * @private
	 */

	_handleImageSelectorClick(clickEvent) {
		const editableElement = clickEvent.delegateTarget;
		const eventName = `${this.fragmentEntryLink.portletNamespace}selectImage`;
		const title = Liferay.Language.get('select');
		const url = this.fragmentEntryLink.imageSelectorURL;

		AUI().use(
			'liferay-item-selector-dialog',
			A => {
				const itemSelectorDialog = new A.LiferayItemSelectorDialog(
					{
						eventName,
						on: {
							selectedItemChange: (changeEvent) => {
								this._handleImageEditorChange(
									changeEvent,
									editableElement
								);
							}
						},
						title,
						url
					}
				);

				itemSelectorDialog.open();
			}
		);
	}

	/**
	 * Remove all listeners added to the event handler
	 * @private
	 */

	_removeListeners() {
		this._eventHandler.removeAllListeners();
	}
}

/**
 * Posible types that can be returned by the image selector
 */

EditableImageFragmentProcessor.RETURN_TYPES = {
	fileEntryItemSelector: 'com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType',
	url: 'URL'
};

export {EditableImageFragmentProcessor};
export default EditableImageFragmentProcessor;