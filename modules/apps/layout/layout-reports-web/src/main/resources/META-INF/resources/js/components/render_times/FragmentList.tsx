/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayLabel from '@clayui/label';
import {sub} from 'frontend-js-web';
import React from 'react';

import {Fragment} from '../../constants/fragments';

export default function FragmentList({
	ascendingSort,
	fragments,
}: {
	ascendingSort: boolean;
	fragments: Fragment[];
}) {
	return (
		<div className="page-audit__fragmentList">
			{fragments
				.sort((a: Fragment, b: Fragment) =>
					ascendingSort
						? a.renderTime - b.renderTime
						: b.renderTime - a.renderTime
				)
				.map(
					({
						cached,
						fragment,
						fragmentCollectionURL,
						fromMaster,
						itemId,
						name,
						renderTime,
					}) => {
						return (
							<div
								className="c-p-2 d-flex flex-column page-audit__fragment"
								key={itemId}
							>
								<span className="font-weight-bold position-relative">
									{name}

									<span className="page-audit__fragment__buttons">
										<ClayButtonWithIcon
											aria-label={sub(
												Liferay.Language.get(
													'locate-x-in-page'
												),
												name
											)}
											displayType="unstyled"
											size="sm"
											symbol="search"
											title={sub(
												Liferay.Language.get(
													'locate-x-in-page'
												),
												name
											)}
										/>

										{fragmentCollectionURL ? (
											<ClayButtonWithIcon
												aria-label={sub(
													Liferay.Language.get(
														'open-x-in-fragment-library'
													),
													name
												)}
												className="c-ml-2"
												displayType="unstyled"
												onClick={() =>
													window.open(
														fragmentCollectionURL,
														'_blank'
													)
												}
												size="sm"
												symbol="shortcut"
												title={sub(
													Liferay.Language.get(
														'open-x-in-fragment-library'
													),
													name
												)}
											/>
										) : null}
									</span>
								</span>

								<span>
									{renderTime}

									{Liferay.Language.get('ms')}
								</span>

								<span>
									<ClayLabel displayType="secondary">
										{fragment
											? Liferay.Language.get('fragment')
											: Liferay.Language.get('widget')}
									</ClayLabel>

									{fromMaster && (
										<ClayLabel displayType="secondary">
											{Liferay.Language.get(
												'from-master'
											)}
										</ClayLabel>
									)}

									{cached && (
										<ClayLabel displayType="info">
											{Liferay.Language.get('cached')}
										</ClayLabel>
									)}
								</span>
							</div>
						);
					}
				)}
		</div>
	);
}
