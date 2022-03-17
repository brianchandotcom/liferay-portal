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
import {ClayCheckbox} from '@clayui/form';
import {useEffect, useState} from 'react';

const CheckboxFilter = ({availableItems, clearCheckboxes, updateFilters}) => {
	const [checkedItems, setCheckedItems] = useState([]);

	const handleSelectedCheckbox = (checkedItem) => {
		if (checkedItems.includes(checkedItem)) {
			return setCheckedItems(
				checkedItems.filter((item) => item !== checkedItem)
			);
		}

		setCheckedItems([...checkedItems, checkedItem]);
	};

	useEffect(() => {
		if (clearCheckboxes) {
			setCheckedItems([]);
		}
	}, [clearCheckboxes]);

	return (
		<div className="w-100">
			<div className="pt-2 px-3">
				{availableItems?.map((item, index) => (
					<ClayCheckbox
						checked={checkedItems.includes(item)}
						key={`${item}-${index}`}
						label={item}
						onChange={() => handleSelectedCheckbox(item)}
					/>
				))}
			</div>

			<div className="mb-3 mt-2 mx-3">
				<ClayButton
					className="w-100"
					onClick={() => updateFilters(checkedItems)}
					required
					small={true}
				>
					Apply
				</ClayButton>
			</div>
		</div>
	);
};
export default CheckboxFilter;
