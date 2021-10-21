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

import ClayButton from '@clayui/button';
import React, {useContext} from 'react';

import {AppContext} from '../../AppContext';

const getDocumentLibrarySelectorURL = (portletNamespace) => {
	const criterionJSON = {
		desiredItemSelectorReturnTypes:
			'com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType,com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType',
	};

	const documentLibrarySelectorParameters = {
		'0_json': JSON.stringify(criterionJSON),
		'criteria':
			'com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion',
		'itemSelectedEventName': `${portletNamespace}selectDocumentLibrary`,
		'p_p_id': Liferay.PortletKeys.ITEM_SELECTOR,
		'p_p_state': 'pop_up',
		'refererGroupId': Liferay.ThemeDisplay.getSiteGroupId(),
	};

	const documentLibrarySelectorURL = Liferay.Util.PortletURL.createPortletURL(
		themeDisplay.getLayoutRelativeControlPanelURL(),
		documentLibrarySelectorParameters
	);

	return documentLibrarySelectorURL.toString();
};

const FileSelector = ({disabled, onChange}) => {
	const {portletNamespace} = useContext(AppContext);

	const handleFieldChanged = (selectedItem) => {
		if (selectedItem?.value) {
			onChange(selectedItem, selectedItem.value);
		}
	};

	const handleSelectButtonClicked = () => {
		Liferay.Util.openSelectionModal({
			onSelect: handleFieldChanged,
			selectEventName: `${portletNamespace}selectDocumentLibrary`,
			title: Liferay.Language.get('select-document'),
			url: getDocumentLibrarySelectorURL(portletNamespace),
		});
	};

	return (
		<div className="document-library-form mb-4">
			<ClayButton
				className="select-button"
				disabled={disabled}
				displayType="secondary"
				onClick={handleSelectButtonClicked}
			>
				<span className="lfr-btn-label">
					{Liferay.Language.get('add-document')}
				</span>
			</ClayButton>
		</div>
	);
};

export default FileSelector;
