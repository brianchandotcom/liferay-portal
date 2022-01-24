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

import ClayAlert from '@clayui/alert';
import React from 'react';

import AuditBarChart from './AuditBarChart';
import EmptyAuditBarChart from './EmptyAuditBarChart';

export default function ({context, props}) {
	const {languageDirection, namespace} = context;
	const {learnHowURL, vocabularies} = props;

	const hasBarsCategoryFilters = new URLSearchParams(
		window.location.href
	).get('resetBarsCategoryFiltersURL');

	return vocabularies.length ? (
		<>
			{hasBarsCategoryFilters && (
				<div className="c-mb-3 c-mx-3 small text-info">
					<ClayAlert displayType="info">
						<span>
							{Liferay.Language.get(
								"press-escape-to-remove-the-bar's-category-filters"
							)}
						</span>
					</ClayAlert>
				</div>
			)}

			<AuditBarChart
				namespace={namespace}
				rtl={languageDirection === 'rtl'}
				vocabularies={vocabularies}
			/>
		</>
	) : (
		<EmptyAuditBarChart learnHowURL={learnHowURL} />
	);
}
