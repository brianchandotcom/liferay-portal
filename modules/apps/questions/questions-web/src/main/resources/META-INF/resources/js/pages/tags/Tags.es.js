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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayCard, {ClayCardWithNavigation} from '@clayui/card';
import {ClayInput} from '@clayui/form';
import {ClayPaginationWithBasicItems} from '@clayui/pagination';
import React, {useContext, useEffect, useState} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import Link from '../../components/Link.es';
import {getTags} from '../../utils/client.es';
import lang from '../../utils/lang.es';
import {
	dateToInternationalHuman,
	useDebounceCallback,
} from '../../utils/utils.es';

export default withRouter(
	({
		match: {
			params: {sectionTitle},
		},
	}) => {
		const context = useContext(AppContext);

		const [page, setPage] = useState(1);
		const [tags, setTags] = useState({});

		useEffect(() => {
			getTags(page, context.siteKey).then((data) => setTags(data || []));
		}, [page, context.siteKey]);

		const [debounceCallback] = useDebounceCallback((search) => {
			getTags(page, context.siteKey, search).then((data) =>
				setTags(data || [])
			);
		}, 500);

		return (
			<>
				<div className="container">
					<div className="row">
						<div className="col-md-4 offset-md-8">
							<ClayInput.Group className="c-mt-3">
								<ClayInput.GroupItem>
									<ClayInput
										className="bg-transparent form-control input-group-inset input-group-inset-after"
										onChange={(event) =>
											debounceCallback(event.target.value)
										}
										placeholder={Liferay.Language.get(
											'search'
										)}
										type="text"
									/>

									<ClayInput.GroupInsetItem
										after
										className="bg-transparent"
										tag="span"
									>
										<ClayButtonWithIcon
											displayType="unstyled"
											symbol="search"
											type="submit"
										/>
									</ClayInput.GroupInsetItem>
								</ClayInput.GroupItem>
							</ClayInput.Group>
						</div>
					</div>
					<div className="c-mt-3 row">
						{tags.items &&
							tags.items.map((tag) => (
								<div
									className="col-md-3 question-tags"
									key={tag.id}
								>
									<Link
										to={`/questions/${sectionTitle}/tag/${tag.name}`}
									>
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
															),
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
	}
);
