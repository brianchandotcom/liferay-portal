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

import React, {useMemo} from 'react';

import '../Editor.scss';

import './Sidebar.scss';
import {METADATAS} from '../../ObjectView/context';
import {Collapsable} from './Collapsable';
import Element from './Element';

export default function Sidebar({
	inputChannel,
	objectValidationRuleElements,
}: IProps) {
	const onItemClick = (item: ObjectValidationRuleElementItem) =>
		inputChannel.sendData(item.content);

	const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

	const metadatas = METADATAS.map((metadata) => ({
		content: metadata.name,
		label: metadata.label[defaultLanguageId],
		tooltip: '',
	}));

	const memoizedValue = useMemo(
		() => objectValidationRuleElements[0].items.concat(metadatas),
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[]
	);

	objectValidationRuleElements[0].items = memoizedValue;

	return (
		<div className="lfr-objects__object-editor-sidebar">
			<div className="px-3">
				<h5 className="my-3">{Liferay.Language.get('elements')}</h5>

				{objectValidationRuleElements.map(({items, label}) => (
					<Collapsable key={label} label={label}>
						<Element items={items} onItemClick={onItemClick} />
					</Collapsable>
				))}
			</div>
		</div>
	);
}
interface IProps {
	className?: string;
	inputChannel: {sendData: Function};
	objectValidationRuleElements: ObjectValidationRuleElement[];
}
