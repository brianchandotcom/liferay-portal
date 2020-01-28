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

import ClayCard, {ClayCardWithNavigation} from '@clayui/card';
import {ClayPaginationWithBasicItems} from '@clayui/pagination';
import React, {useContext, useEffect, useState} from 'react';
import {Link} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import {getKeywords} from '../../utils/client.es';
import lang from '../../utils/lang.es';
import {dateToInternationalHuman} from '../../utils/utils.es';

export default () => {
	const context = useContext(AppContext);

	const [page, setPage] = useState(1);
	const [keywords, setKeywords] = useState({});

	useEffect(() => {
		getKeywords(page, context.siteKey).then(data => setKeywords(data));
	}, [context.siteKey, page]);

	return (
		<>
			<div className="container">
				<div className="row">
					{keywords.items &&
						keywords.items.map(keyword => (
							<div
								className="col-md-3 question-keywords"
								key={keyword.id}
							>
								<ClayCardWithNavigation>
									<ClayCard.Body>
										<ClayCard.Description displayType="title">
											<Link
												to={`/questions/keyword/${keyword.name}`}
											>
												{keyword.name}
											</Link>
										</ClayCard.Description>
										<ClayCard.Description displayType="text">
											{lang.sub(
												Liferay.Language.get(
													'used-x-times'
												),
												[keyword.keywordUsageCount]
											)}
										</ClayCard.Description>
										<ClayCard.Description displayType="text">
											{lang.sub(
												Liferay.Language.get(
													'latest-usage'
												),
												[
													dateToInternationalHuman(
														keyword.dateCreated
													)
												]
											)}
										</ClayCard.Description>
									</ClayCard.Body>
								</ClayCardWithNavigation>
							</div>
						))}
				</div>
			</div>
			{keywords.lastPage > 1 && (
				<ClayPaginationWithBasicItems
					activePage={page}
					ellipsisBuffer={2}
					onPageChange={setPage}
					totalPages={Math.ceil(
						keywords.totalCount / keywords.pageSize
					)}
				/>
			)}
		</>
	);
};
