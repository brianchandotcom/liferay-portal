import {Config} from 'metal-state';
import {ItemSelectorDialog, PortletBase} from 'frontend-js-web';

/**
 * @class MoveEntries
 */
class MoveEntries extends PortletBase {
	/**
	 * @inheritdoc
	 * @review
	 */
	created() {
		const selectFolderButton = document.getElementById(
			this.ns(this.selectFolderButtonId)
		);

		if (selectFolderButton) {
			this._handleSelectFolderButtonClick = this._handleSelectFolderButtonClick.bind(
				this
			);

			selectFolderButton.addEventListener(
				'click',
				this._handleSelectFolderButtonClick
			);
		}
	}

	/**
	 * @inheritdoc
	 * @review
	 */
	disposed() {
		const selectFolderButton = document.getElementById(
			this.ns(this.selectFolderButtonId)
		);

		if (selectFolderButton) {
			selectFolderButton.removeEventListener(
				'click',
				this._handleSelectFolderButtonClick
			);
		}
	}

	/**
	 * @private
	 * @review
	 */
	_handleSelectFolderButtonClick() {
		const itemSelectorDialog = new ItemSelectorDialog({
			buttonAddLabel: Liferay.Language.get('done'),
			eventName: this.ns(this.selectFolderEventName),
			title: Liferay.Language.get('select-folder'),
			url: this.selectFolderURL
		});

		itemSelectorDialog.on('selectedItemChange', event => {
			const selectedItem = event.selectedItem;

			if (selectedItem) {
				var folderData = {
					idString: 'newFolderId',
					idValue: selectedItem.folderId,
					nameString: 'folderName',
					nameValue: selectedItem.folderName
				};

				Liferay.Util.selectFolder(folderData, this.portletNamespace);
			}
		});

		itemSelectorDialog.open();
	}
}

/**
 * @memberof MoveEntries
 * @review
 * @static
 */
MoveEntries.STATE = {
	/**
	 * @default undefined
	 * @memberof MoveEntries
	 * @required
	 * @review
	 * @type {string}
	 */
	selectFolderButtonId: Config.string().required(),

	/**
	 * @default undefined
	 * @memberof MoveEntries
	 * @required
	 * @review
	 * @type {string}
	 */
	selectFolderEventName: Config.string().required(),

	/**
	 * @default undefined
	 * @memberof MoveEntries
	 * @required
	 * @review
	 * @type {string}
	 */
	selectFolderURL: Config.string().required()
};

export default MoveEntries;
