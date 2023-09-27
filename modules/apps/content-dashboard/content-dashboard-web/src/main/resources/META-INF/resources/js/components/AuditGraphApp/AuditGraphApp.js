/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import React from 'react';

import AuditBarChart from './AuditBarChart';
import EmptyAuditBarChart from './EmptyAuditBarChart';

export default function ({context, props}) {
	const {languageDirection, namespace} = context;
	const {learnHowLink, vocabularies} = props;

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
		<EmptyAuditBarChart learnHowLink={learnHowLink} />
	);
}
