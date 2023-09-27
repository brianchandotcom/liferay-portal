/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayDualListBox} from '@clayui/form';
import {useEffect, useState} from 'react';

export type BoxItem = {
	label: string;
	value: string;
};

export type Boxes<T = any> = Array<Array<BoxItem & T>>;

type DualListBoxProps = {
	boxes?: Boxes;
	leftLabel: string;
	rightLabel: string;
	setValue?: (value: any) => void;
};

const DualListBox: React.FC<DualListBoxProps> = ({
	boxes = [[], []],
	leftLabel,
	rightLabel,
	setValue = () => {},
}) => {
	const [items, setItems] = useState<Boxes>([[], []]);
	const [leftSelected, setLeftSelected] = useState<string[]>([]);
	const [rightSelected, setRightSelected] = useState<string[]>([]);

	const initialBoxes = JSON.stringify(boxes);

	useEffect(() => {
		setItems(JSON.parse(initialBoxes));
	}, [initialBoxes]);

	return (
		<ClayDualListBox
			items={items}
			left={{
				label: leftLabel,
				onSelectChange: setLeftSelected,
				selected: leftSelected,
			}}
			onItemsChange={(items) => {
				setItems(items);
				setValue(items);
			}}
			right={{
				label: rightLabel,
				onSelectChange: (value) => {
					setRightSelected(value);
				},
				selected: rightSelected,
			}}
			size={8}
		/>
	);
};

export default DualListBox;
