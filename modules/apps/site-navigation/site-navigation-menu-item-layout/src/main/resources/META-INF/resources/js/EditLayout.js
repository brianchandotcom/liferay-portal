/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

export default function ({
	eventName,
	itemSelectorURL: initialItemSelectorURL,
	namespace,
}) {
	const namespaceId = (id) => `${namespace}${id}`;

	const groupIdInput = document.getElementById(namespaceId('groupId'));

	const layoutItemRemoveButton = document.getElementById(
		namespaceId('layoutItemRemove')
	);
	const layoutNameInput = document.getElementById(
		namespaceId('layoutNameInput')
	);

	const layoutUuidInput = document.getElementById(namespaceId('layoutUuid'));

	const privateLayoutInput = document.getElementById(
		namespaceId('privateLayout')
	);

	const chooseLayoutButton = document.getElementById(
		namespaceId('chooseLayout')
	);

	const itemSelectorURL = new URL(initialItemSelectorURL);

	const onChooseLayoutButtonClick = () => {
		Liferay.Util.openSelectionModal({
			multiple: true,
			onSelect: (selectedItem) => {
				if (selectedItem) {
					groupIdInput.value = selectedItem.groupId;
					layoutUuidInput.value = selectedItem.id;
					layoutNameInput.textContent = selectedItem.name;
					privateLayoutInput.value = selectedItem.privateLayout;

					itemSelectorURL.searchParams.set(
						`${Liferay.Util.getPortletNamespace(
							Liferay.PortletKeys.ITEM_SELECTOR
						)}layoutUuid`,
						selectedItem.id
					);

					layoutItemRemoveButton.classList.remove('hide');
				}
			},
			selectEventName: eventName,
			title: Liferay.Language.get('select-layout'),
			url: itemSelectorURL.href,
		});
	};

	chooseLayoutButton.addEventListener('click', onChooseLayoutButtonClick);

	const onLayoutItemRemoveButtonClick = () => {
		layoutNameInput.textContent = Liferay.Language.get('none');
		layoutUuidInput.value = '';

		layoutItemRemoveButton.classList.add('hide');
	};

	layoutItemRemoveButton.addEventListener(
		'click',
		onLayoutItemRemoveButtonClick
	);

	return {
		dispose() {
			chooseLayoutButton.removeEventListener(
				'click',
				onChooseLayoutButtonClick
			);
			layoutItemRemoveButton.removeEventListener(
				'click',
				onLayoutItemRemoveButtonClick
			);
		},
	};
}
