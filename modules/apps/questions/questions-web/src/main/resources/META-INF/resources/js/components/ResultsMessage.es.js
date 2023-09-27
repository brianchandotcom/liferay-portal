/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import lang from '../utils/lang.es';
import {slugToText} from '../utils/utils.es';

export default function ResultsMessage({
	maxNumberOfSearchResults = 0,
	searchCriteria,
	totalCount = 0,
}) {
	return (
		<>
			{!!totalCount && (
				<div className="c-mt-3 c-mx-auto c-pt-3 c-px-3 col-xl-10">
					{totalCount >= maxNumberOfSearchResults ? (
						<span
							dangerouslySetInnerHTML={{
								__html: lang.sub(
									Liferay.Language.get(
										'there-are-more-than-x-results-for-x'
									),
									[
										maxNumberOfSearchResults,
										`<strong>"${slugToText(
											searchCriteria
										)}"</strong>`,
									]
								),
							}}
						/>
					) : (
						<span
							dangerouslySetInnerHTML={{
								__html: lang.sub(
									totalCount === 1
										? Liferay.Language.get('x-result-for-x')
										: Liferay.Language.get(
												'x-results-for-x'
										  ),
									[
										totalCount,
										`<strong>"${slugToText(
											searchCriteria
										)}"</strong>`,
									]
								),
							}}
						/>
					)}

					{totalCount > maxNumberOfSearchResults && (
						<div className="text-secondary">
							{Liferay.Language.get(
								'refine-the-search-criteria-to-reduce-results'
							)}
						</div>
					)}
				</div>
			)}
		</>
	);
}
