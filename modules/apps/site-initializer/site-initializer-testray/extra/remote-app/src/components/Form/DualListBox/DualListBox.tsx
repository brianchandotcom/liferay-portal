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
