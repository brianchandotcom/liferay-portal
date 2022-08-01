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

import {useState} from 'react';

import CheckBox from '../CheckBox/CheckBox';

type Props = {
	availableItems: any;
	hasRule: boolean;
	maxCheckedItems: number;
	setCheckBox: any;
};

const CheckBoxList = ({
	availableItems,
	hasRule,
	maxCheckedItems,
	setCheckBox,
}: Props) => {
	const [checkedItems, setCheckedItems] = useState<any[]>([]);

	const handleSelectedCheckbox = (checkedItem: string, setCheckBox: any) => {
		if (checkedItems.includes(checkedItem)) {
			return setCheckedItems(
				checkedItems.filter((item) => item !== checkedItem)
			);
		}

		if (
			(hasRule && checkedItems.length + 1 <= maxCheckedItems) ||
			!hasRule
		) {
			setCheckedItems([...checkedItems, checkedItem]);
			setCheckBox([...checkedItems, checkedItem]);
		}
	};

	return (
		<div>
			{availableItems?.map((value: any, index: number) => (
				<CheckBox
					checked={checkedItems.includes(value.key)}
					key={index}
					label={value.name}
					onChange={() =>
						handleSelectedCheckbox(value.key, setCheckBox)
					}
					readOnly
				/>
			))}
		</div>
	);
};
export default CheckBoxList;
