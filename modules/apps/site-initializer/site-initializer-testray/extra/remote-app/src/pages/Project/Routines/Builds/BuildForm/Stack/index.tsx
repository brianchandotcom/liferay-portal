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

import {useState} from 'react';

import StackHistory from './StackHistory';
import StackList, {StackListProps} from './StackList';

type StackProps = {} & StackListProps;

const Stack: React.FC<StackProps> = ({
	append,
	fields,
	remove,
	...stackProps
}) => {
	const [fieldsHistory, setFieldsHistory] = useState<{id: number}[]>([]);

	const onRemove = (index: number) => {
		remove(index);

		setFieldsHistory([...fieldsHistory, fields[index] as any]);
	};

	return (
		<>
			<StackHistory
				append={append}
				fieldsHistory={fieldsHistory}
				setFieldsHistory={setFieldsHistory}
			/>

			<StackList
				{...stackProps}
				append={append}
				fields={fields}
				remove={onRemove}
			/>
		</>
	);
};

export default Stack;
