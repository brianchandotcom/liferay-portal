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
import {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import React, {useContext, useEffect, useState} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import Link from '../../components/Link.es';
import PaginatedList from '../../components/PaginatedList.es';
import useQueryParams from '../../hooks/useQueryParams.es';
import {getTagsQuery} from '../../utils/client.es';
import lang from '../../utils/lang.es';
import {historyPushWithSlug, useDebounceCallback} from '../../utils/utils.es';

export default withRouter(
	({
		history,
		location,
		match: {
			params: {sectionTitle},
		},
	}) => {
		const context = useContext(AppContext);

		const [page, setPage] = useState(1);
		const [pageSize, setPageSize] = useState(20);
		const [search, setSearch] = useState('');

		const {data, loading} = useQuery(getTagsQuery, {
			variables: {page, pageSize, search, siteKey: context.siteKey},
		});

		const queryParams = useQueryParams(location);

		useEffect(() => {
			setPage(queryParams.get('page') || 1);
		}, [queryParams]);

		useEffect(() => {
			setPageSize(queryParams.get('pagesize') || 20);
		}, [queryParams]);

		const historyPushParser = historyPushWithSlug(history.push);

		const changePage = (page, pageSize) => {
			historyPushParser(
				`/questions/${context.section}/tags?page=${page}&pagesize=${pageSize}`
			);
		};

		const [debounceCallback] = useDebounceCallback((search) => {
			setSearch(search);
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
										disabled={
											!search &&
											data &&
											data.keywordsRanked &&
											!data.keywordsRanked.items.length
										}
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
										{loading && (
											<ClayLoadingIndicator small />
										)}
										{!loading && (
											<ClayButtonWithIcon
												displayType="unstyled"
												symbol="search"
												type="submit"
											/>
										)}
									</ClayInput.GroupInsetItem>
								</ClayInput.GroupItem>
							</ClayInput.Group>
						</div>
					</div>

					<div className="c-mt-3 row">
						<PaginatedList
							activeDelta={pageSize}
							activePage={page}
							changeDelta={(pageSize) =>
								changePage(page, pageSize)
							}
							changePage={(page) => changePage(page, pageSize)}
							data={data && data.keywordsRanked}
							loading={loading}
						>
							{(tag) => (
								<div
									className="col-md-3 question-tags"
									key={tag.id}
								>
									<Link
										title={tag.name}
										to={`/questions/${sectionTitle}/tag/${tag.name}`}
									>
										<div className="card card-interactive card-interactive-primary card-type-template template-card-horizontal">
											<div className="card-body">
												<div className="card-row">
													<div className="autofit-col autofit-col-expand">
														<div className="autofit-section">
															<div className="card-title">
																<span className="text-truncate">
																	{tag.name}
																</span>
															</div>
															<div>
																{lang.sub(
																	Liferay.Language.get(
																		'used-x-times'
																	),
																	[
																		tag.keywordUsageCount,
																	]
																)}
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</Link>
								</div>
							)}
						</PaginatedList>
					</div>
				</div>
			</>
		);
	}
);
