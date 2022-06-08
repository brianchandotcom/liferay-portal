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

import {SidebarCategory} from '@liferay/object-js-components-web';
import {useMemo} from 'react';

import {METADATA} from '../components/ObjectView/context';
import {defaultLanguageId} from '../utils/locale';

export default function useMetadata(elementList: SidebarCategory[]) {
	return useMemo(() => {
		if (!elementList || elementList.length === 0) {
			return [] as SidebarCategory[];
		}

		const [first, ...others] = elementList;
		const items = [...first.items];

		if (!Liferay.FeatureFlags['LPS-154872']) {
			METADATA.forEach(({label, name}) => {
				items.push({
					content: name,
					label: label[defaultLanguageId],
					tooltip: '',
				});
			});
		}

		return [{...first, items}, ...others];
	}, [elementList]);
}
