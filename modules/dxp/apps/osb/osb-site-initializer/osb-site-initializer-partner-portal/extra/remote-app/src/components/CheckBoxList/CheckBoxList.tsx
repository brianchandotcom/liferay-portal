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

import {useEffect, useState} from 'react';

import CheckBox from '../CheckBox/CheckBox';
import InputText from '../InputText';

type Props = {
	availableItems: any;
	formik: any;
	hasOther: boolean;
	hasRule: boolean;
	maxCheckedItems: number;
	setCheckBox: any;
};

const CheckBoxList = ({
	availableItems,
	formik,
	hasOther,
	hasRule,
	maxCheckedItems,
	setCheckBox,
}: Props) => {
	const [checkedItems, setCheckedItems] = useState<any[]>([]);
	const [disableOther, setDisableOther] = useState<boolean>(true);

	const handleSelectedCheckbox = (checkedItem: string) => {
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
		}
	};

	useEffect(() => {
		setCheckBox(checkedItems);
	}, [checkedItems, setCheckBox]);

	return (
		<div>
			{availableItems?.map((value: any, index: number) => (
				<CheckBox
					checked={checkedItems.includes(value.key)}
					key={index}
					label={value.name}
					onChange={() => handleSelectedCheckbox(value.key)}
					readOnly
				/>
			))}

			{hasOther && (
				<div>
					<CheckBox
						checked={checkedItems.includes('other')}
						key={Object.keys(availableItems).length + 1}
						label="Other"
						onChange={() => {
							setDisableOther(checkedItems.includes('other'));
							handleSelectedCheckbox('other');
						}}
						readOnly
					/>

					<InputText
						className="form-control shadow-none"
						disabled={disableOther}
						label=""
						name="businessSalesGoalsOther"
						onChange={formik.handleChange}
						placeholder="Input"
						type="text"
						value={formik.values.businessSalesGoalsOther}
					/>
				</div>
			)}
		</div>
	);
};
export default CheckBoxList;
