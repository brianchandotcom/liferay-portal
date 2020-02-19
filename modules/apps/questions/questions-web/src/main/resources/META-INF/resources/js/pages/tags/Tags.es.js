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
import {getTags} from '../../utils/client.es';
import lang from '../../utils/lang.es';
import {dateToInternationalHuman} from '../../utils/utils.es';

export default () => {
	const context = useContext(AppContext);

	const [page, setPage] = useState(1);
	const [tags, setTags] = useState({});

	useEffect(() => {
		getTags(page, context.siteKey).then(data => setTags(data));
	}, [page, context.siteKey]);

	return (
		<>
			<div className="container">
				<div className="row">
					{tags.items &&
						tags.items.map(tag => (
							<div
								className="col-md-3 question-tags"
								key={tag.id}
							>
								<Link to={`/questions/tag/${tag.name}`}>
									<ClayCardWithNavigation>
										<ClayCard.Body>
											<ClayCard.Description displayType="title">
												{tag.name}
											</ClayCard.Description>
											<ClayCard.Description displayType="text">
												{lang.sub(
													Liferay.Language.get(
														'used-x-times'
													),
													[tag.keywordUsageCount]
												)}
											</ClayCard.Description>
											<ClayCard.Description displayType="text">
												{lang.sub(
													Liferay.Language.get(
														'latest-usage'
													),
													[
														dateToInternationalHuman(
															tag.dateCreated
														)
													]
												)}
											</ClayCard.Description>
										</ClayCard.Body>
									</ClayCardWithNavigation>
								</Link>
							</div>
						))}
				</div>
			</div>
			{tags.lastPage > 1 && (
				<ClayPaginationWithBasicItems
					activePage={page}
					ellipsisBuffer={2}
					onPageChange={setPage}
					totalPages={Math.ceil(tags.totalCount / tags.pageSize)}
				/>
			)}
		</>
	);
};
