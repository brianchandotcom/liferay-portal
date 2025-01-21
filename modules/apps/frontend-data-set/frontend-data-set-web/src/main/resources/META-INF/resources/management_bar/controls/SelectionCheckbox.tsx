/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox} from '@clayui/form';
import React from 'react';

interface SelectionCheckboxProps {
	handleCheckboxClick: () => void;
	items: Array<any>;
	selectedItemsValue: any;
}

const SelectionCheckbox = ({
	handleCheckboxClick,
	items,
	selectedItemsValue,
}: SelectionCheckboxProps) => {
	return (
		<ClayCheckbox
			checked={!!selectedItemsValue.length}
			indeterminate={
				!!selectedItemsValue.length &&
				items.length !== selectedItemsValue.length
			}
			name="items-selector"
			onChange={handleCheckboxClick}
			title={
				items.length !== selectedItemsValue.length
					? Liferay.Language.get('select-items')
					: Liferay.Language.get('clear-selection')
			}
		/>
	);
};

export default SelectionCheckbox;
