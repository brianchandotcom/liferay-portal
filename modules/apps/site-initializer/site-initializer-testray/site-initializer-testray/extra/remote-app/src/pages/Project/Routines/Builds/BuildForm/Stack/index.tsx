/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
