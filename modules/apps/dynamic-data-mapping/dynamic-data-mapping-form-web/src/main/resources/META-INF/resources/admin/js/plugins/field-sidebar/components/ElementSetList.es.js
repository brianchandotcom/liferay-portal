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

import {EmptyState, FieldType, SearchUtils} from 'data-engine-taglib';
import {
	elementSetAdded,
	useForm,
	useFormState,
} from 'dynamic-data-mapping-form-renderer';
import React from 'react';

const EmptyPanel = ({searchTerm}) => (
	<div className="mt-2">
		<EmptyState
			emptyState={{
				description: Liferay.Language.get(
					'there-are-no-element-sets-yet'
				),
				title: Liferay.Language.get('there-are-no-element-sets'),
			}}
			keywords={searchTerm}
			small
		/>
	</div>
);

const ElementSetList = ({elementSets, searchTerm = '', ...context}) => {
	const {activePage, pages} = useFormState();
	const dispatch = useForm();
	const regex = SearchUtils.getSearchRegex(searchTerm);

	const elementSetList = elementSets.filter(({name}) => regex.test(name));

	return elementSetList.length ? (
		<div className="mt-3">
			{elementSetList.map((elementSet, key) => {
				const payload = {
					elementSetId: elementSet.id,
					...context,
				};

				return (
					<FieldType
						dragType="elementSet:add"
						icon="forms"
						key={key}
						label={elementSet.name}
						onDoubleClick={() => {
							const indexes = {
								pageIndex: activePage,
								rowIndex: pages[activePage].rows.length,
							};

							dispatch(elementSetAdded({indexes, ...payload}));
						}}
						payload={payload}
					/>
				);
			})}
		</div>
	) : (
		<EmptyPanel searchTerm={searchTerm} />
	);
};

export default ElementSetList;
