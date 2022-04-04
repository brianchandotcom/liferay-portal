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
