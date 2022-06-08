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

import React from 'react';

import {
	useDispatch,
	useSelector,
} from '../../../../../../app/contexts/StoreContext';
import selectSegmentsExperienceId from '../../../../../../app/selectors/selectSegmentsExperienceId';
import updateItemConfig from '../../../../../../app/thunks/updateItemConfig';
import {FieldSet} from './FieldSet';

const FIELD_SET = {
	fields: [
		{
			label: Liferay.Language.get('css-classes'),
			name: 'cssClasses',
			type: 'cssClassSelector',
		},
	],
	label: Liferay.Language.get('css'),
};

export default function CSSFieldSet({item}) {
	const languageId = useSelector((state) => state.languageId);
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);
	const dispatch = useDispatch();

	const onValueSelect = (name, value) => {
		const nextConfig = {
			...item.config,
			[name]: value,
		};

		dispatch(
			updateItemConfig({
				itemConfig: nextConfig,
				itemId: item.itemId,
				segmentsExperienceId,
			})
		);
	};

	return Liferay.FeatureFlags['LPS-147511'] ? (
		<div className="mt-3">
			<FieldSet
				fields={FIELD_SET.fields}
				item={item}
				label={FIELD_SET.label}
				languageId={languageId}
				onValueSelect={onValueSelect}
				values={item.config}
			/>
		</div>
	) : null;
}
