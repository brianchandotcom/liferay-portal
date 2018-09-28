import { Config } from 'metal-state';

import PortletBase from 'frontend-js-web/liferay/PortletBase.es';

class SharedWithMe extends PortletBase {
	constructor(config, ...args) {
		super(config, ...args);

		this._selectAssetTypeURL = config.selectAssetTypeURL;
		this._viewAssetTypeURL = config.viewAssetTypeURL;
	}

	onFilterItemClicked(event) {
			let namespace = this.namespace;
			let viewAssetTypeURL = this._viewAssetTypeURL;

			AUI().use(
				'liferay-item-selector-dialog',
				A => {
					var itemData = event.data.item.data;

					if (itemData.action === 'openAssetTypesSelector') {
						var itemSelectorDialog = new A.LiferayItemSelectorDialog(
							{
								eventName: namespace + 'selectAssetType',
								on: {
									selectedItemChange: function(event) {
										var selectedItem = event.newVal;

										if (selectedItem) {
											var uri = viewAssetTypeURL;

											uri = Liferay.Util.addParams(namespace + 'className=' + selectedItem, uri);

											location.href = uri;
										}
									}
								},
								'strings.add': Liferay.Language.get('select'),
								title: Liferay.Language.get('select-asset-type'),
								url: this._selectAssetTypeURL
							}
						);

						itemSelectorDialog.open();
					}
				}
			);
	}
}

export default SharedWithMe;