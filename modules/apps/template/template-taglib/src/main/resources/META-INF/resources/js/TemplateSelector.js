/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker} from '@clayui/core';
import DropDown from '@clayui/drop-down';
import Form, {ClayInput} from '@clayui/form';
import React, {useState} from 'react';

export default function DisplayTemplateSelector({namespace, props}) {
	const {displayStyle, displayStyleGroupId, items} = props;

	const [selectedDisplayStyle, setSelectedDisplayStyle] = useState({
		groupId: displayStyleGroupId,
		name: displayStyle,
	});

	const onSelectionChangeHandlder = (option) => {
		const [groupId, ...value] = option.split('-');

		setSelectedDisplayStyle({
			groupId,
			name: value.join('-'),
		});

		Liferay.fire('templateSelector:changedTemplate', {
			value: value.join('-'),
		});
	};

	return (
		<>
			<ClayInput
				id={`${namespace}preferences--displayStyle--`}
				name={`${namespace}preferences--displayStyle--`}
				type="hidden"
				value={selectedDisplayStyle.name}
			/>

			<ClayInput
				id={`${namespace}displayStyleGroupId`}
				name={`${namespace}preferences--displayStyleGroupId--`}
				type="hidden"
				value={selectedDisplayStyle.groupId}
			/>

			<Form.Group>
				<label htmlFor={`${namespace}displayStyle`}>
					{Liferay.Language.get('display-template')}
				</label>

				<Picker
					UNSAFE_menuClassName="cadmin"
					className="display-template-selector"
					id={`${namespace}displayStyle`}
					items={items}
					onSelectionChange={(key) => onSelectionChangeHandlder(key)}
					selectedKey={`${selectedDisplayStyle.groupId}-${selectedDisplayStyle.name}`}
				>
					{(group) => (
						<DropDown.Group
							header={group.label}
							items={group.items}
						>
							{(item) => (
								<Option
									key={`${
										item.groupId
											? item.groupId
											: displayStyleGroupId
									}-${item.value}`}
								>
									{item.label}
								</Option>
							)}
						</DropDown.Group>
					)}
				</Picker>
			</Form.Group>
		</>
	);
}
