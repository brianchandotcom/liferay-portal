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

import {useEffect, useState} from 'react';

import {getSection} from '../utils/client.es';

export default function useSection(sectionTitle, siteKey) {
	const [section, setSection] = useState({});

	useEffect(() => {
		getSection(sectionTitle, siteKey)
			.then(section => {
				if (section.parentMessageBoardSectionId) {
					return Promise.all([
						section,
						getSection(
							section.parentMessageBoardSectionId,
							siteKey
						),
					]);
				}

				return [section, section];
			})
			.then(([section, parentSection]) => {
				section.parentSection = parentSection;
				setSection(section);
			});
	}, [sectionTitle, siteKey]);

	return section;
}
