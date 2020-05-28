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

import {useQuery} from '@apollo/client';
import {useState} from 'react';

import {client, getSectionQuery} from '../utils/client.es';

export default function useSection(sectionTitle, siteKey) {
	const [section, setSection] = useState({});

	useQuery(getSectionQuery, {
		onCompleted(data) {
			const section = data.messageBoardSections.items[0];

			Promise.resolve(section)
				.then((section) => {
					if (section.parentMessageBoardSectionId) {
						return Promise.all([
							section,
							client.query({
								query: getSectionQuery,
								variables: {
									filter: `title eq '${section.parentMessageBoardSectionId}' or id eq '${section.parentMessageBoardSectionId}'`,
									siteKey,
								},
							}),
						]).then(([section, {data}]) => [
							section,
							data.messageBoardSections.items[0],
						]);
					}

					return [section, section];
				})
				.then(([section, parentSection]) => {
					setSection({...section, parentSection});
				});
		},
		variables: {
			filter: `title eq '${sectionTitle}' or id eq '${sectionTitle}'`,
			siteKey,
		},
	});

	return section;
}
