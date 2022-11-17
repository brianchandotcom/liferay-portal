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

import {
	AutoComplete,
	filterArrayByQuery,
} from '@liferay/object-js-components-web';
import React, {useMemo, useState} from 'react';

import {defaultLanguageId} from '../util/constants';

interface FreemarkerEditorSidebarContentProps {
	objectDefinitions: ObjectDefinition[];
}

export default function FreemarkerEditorSidebarContent({
	objectDefinitions,
}: FreemarkerEditorSidebarContentProps) {
	const [query, setQuery] = useState<string>('');
	const [selectedEntity, setSelectedEntity] = useState<ObjectDefinition>();

	const filteredEntities = useMemo(() => {
		return filterArrayByQuery(objectDefinitions, 'label', query);
	}, [objectDefinitions, query]);

	return (
		<AutoComplete<ObjectDefinition>
			emptyStateMessage={Liferay.Language.get('no-entities-were-found')}
			items={filteredEntities ?? []}
			label={Liferay.Language.get('entity')}
			onChangeQuery={setQuery}
			onSelectItem={(item) => {
				setSelectedEntity(item);
			}}
			query={query}
			value={selectedEntity?.label[defaultLanguageId]}
		>
			{({label, name}) => (
				<div className="d-flex justify-content-between">
					<div>{label[defaultLanguageId] ?? name}</div>
				</div>
			)}
		</AutoComplete>
	);
}
