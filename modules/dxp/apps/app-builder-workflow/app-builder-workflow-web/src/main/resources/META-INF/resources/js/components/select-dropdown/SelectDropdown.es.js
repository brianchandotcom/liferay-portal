/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import DropDownWithSearch from 'app-builder-web/js/components/dropdown-with-search/DropDownWithSearch.es';
import React from 'react';

export default function SelectDropdown({
	ariaLabelId,
	emptyResultMessage,
	items = [],
	label,
	onSelect,
	selectedValue,
	...otherProps
}) {
	const itemName = selectedValue || label;

	return (
		<>
			<DropDownWithSearch
				className="w-100"
				isEmpty={items.length === 0}
				label={label}
				trigger={
					<ClayButton
						aria-labelledby={ariaLabelId}
						className="clearfix w-100"
						displayType="secondary"
					>
						<span
							className="dropdown-button-title float-left text-left text-truncate"
							title={itemName}
						>
							{itemName}
						</span>

						<ClayIcon
							className="dropdown-button-asset float-right"
							symbol="caret-bottom"
						/>
					</ClayButton>
				}
				{...otherProps}
			>
				<DropDownWithSearch.Items
					emptyResultMessage={emptyResultMessage}
					items={items}
					onSelect={onSelect}
				/>
			</DropDownWithSearch>
		</>
	);
}
