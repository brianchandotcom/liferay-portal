/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {createPortletURL, openSelectionModal} from 'frontend-js-web';
import React, {useContext} from 'react';

import {AppContext} from '../../AppContext';

const getDocumentLibrarySelectorURL = (portletNamespace) => {
	const criterionJSON = {
		desiredItemSelectorReturnTypes:
			'com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType,com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType',
	};

	const documentLibrarySelectorParameters = {
		'0_json': JSON.stringify(criterionJSON),
		'criteria': 'file',
		'itemSelectedEventName': `${portletNamespace}selectDocumentLibrary`,
		'p_p_id': Liferay.PortletKeys.ITEM_SELECTOR,
		'p_p_state': 'pop_up',
		'refererGroupId': Liferay.ThemeDisplay.getSiteGroupId(),
	};

	const documentLibrarySelectorURL = createPortletURL(
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
		openSelectionModal({
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
