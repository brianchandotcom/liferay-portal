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

import Form from '..';
import ClayMultiSelect from '@clayui/multi-select';
import {useState} from 'react';

type MultiSelectProps = {
	label?: string;
};

const MultiSelect: React.FC<MultiSelectProps> = ({label}) => {
	const [value, setValue] = useState('');
	const [items, setItems] = useState([]);

	return (
		<Form.BaseWrapper label={label}>
			<ClayMultiSelect
				items={items}
				onChange={setValue}
				onItemsChange={(items: any) => setItems(items)}
				value={value}
			/>
		</Form.BaseWrapper>
	);
};

export default MultiSelect;
