/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker} from '@clayui/core';
import DropDown from '@clayui/drop-down';
import Form, {ClayInput} from '@clayui/form';
import {InternalDispatch} from '@clayui/shared';
import React, {useState} from 'react';

type Props = {
	namespace: string;
	props: {
		displayStyle: string;
		displayStyleGroupId: string;
		displayStyleGroupKey: string;
		items: [
			{
				items: Array<{
					groupId: number;
					groupKey: string;
					label: string;
					value: string;
				}>;
				label: string;
			},
		];
	};
};

export default function DisplayTemplateSelector({namespace, props}: Props) {
	const {displayStyle, displayStyleGroupId, displayStyleGroupKey, items} =
		props;

	const [selectedDisplayStyle, setSelectedDisplayStyle] = useState({
		groupId: displayStyleGroupId,
		groupKey: displayStyleGroupKey,
		name: displayStyle,
	});

	const onSelectionChangeHandlder: InternalDispatch<React.Key> = (
		option: React.Key
	) => {
		if (typeof option !== 'string') {
			return;
		}

		const [groupId, groupKey, ...value] = option.split('_SEPARATOR_');

		setSelectedDisplayStyle({
			groupId,
			groupKey,
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

			<ClayInput
				id={`${namespace}displayStyleGroupKey`}
				name={`${namespace}preferences--displayStyleGroupKey--`}
				type="hidden"
				value={selectedDisplayStyle.groupKey}
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
					onSelectionChange={onSelectionChangeHandlder}
					selectedKey={`${selectedDisplayStyle.groupId}_SEPARATOR_${selectedDisplayStyle.groupKey}_SEPARATOR_${selectedDisplayStyle.name}`}
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
									}_SEPARATOR_${
										item.groupKey
											? item.groupKey
											: displayStyleGroupKey
									}_SEPARATOR_${item.value}`}
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
