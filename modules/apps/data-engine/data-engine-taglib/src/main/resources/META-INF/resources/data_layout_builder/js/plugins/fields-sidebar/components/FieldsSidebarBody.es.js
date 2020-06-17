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

import React, {useContext} from 'react';

import AppContext from '../../../AppContext.es';
import {dropLayoutBuilderField} from '../../../actions.es';
import FieldSets from '../../../components/field-sets/FieldSets.es';
import FieldTypeList from '../../../components/field-types/FieldTypeList.es';
import Sidebar from '../../../components/sidebar/Sidebar.es';
import DataLayoutBuilderContext from '../../../data-layout-builder/DataLayoutBuilderContext.es';

export default function ({keywords, setKeywords}) {
	const [dataLayoutBuilder] = useContext(DataLayoutBuilderContext);

	const [
		{
			config: {allowFieldSets},
		},
	] = useContext(AppContext);

	const onDoubleClick = ({name}) => {
		const {activePage, pages} = dataLayoutBuilder.getStore();

		dataLayoutBuilder.dispatch(
			'fieldAdded',
			dropLayoutBuilderField({
				addedToPlaceholder: true,
				dataLayoutBuilder,
				fieldTypeName: name,
				indexes: {
					columnIndex: 0,
					pageIndex: activePage,
					rowIndex: pages[activePage].rows.length,
				},
			})
		);
	};

	const fieldTypes = dataLayoutBuilder
		.getFieldTypes()
		.filter(({group}) => group === 'basic');

	fieldTypes.sort(({displayOrder: a}, {displayOrder: b}) => a - b);

	const tabs = [
		{
			label: Liferay.Language.get('fields'),
			render: () => (
				<FieldTypeList
					fieldTypes={fieldTypes}
					keywords={keywords}
					onDoubleClick={onDoubleClick}
				/>
			),
		},
	];

	if (allowFieldSets) {
		tabs.push({
			label: Liferay.Language.get('fieldsets'),
			render: () => <FieldSets keywords={keywords} />,
		});
	}

	return <Sidebar.Tabs setKeywords={setKeywords} tabs={tabs} />;
}
