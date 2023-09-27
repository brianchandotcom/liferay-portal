/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import Link from '../components/Link.es';
import {
	getTagsOrderByDateCreatedQuery,
	getTagsOrderByNumberOfUsagesQuery,
	subscribeTagQuery,
	unsubscribeTagQuery,
} from '../utils/client.es';
import {ALL_SECTIONS_ID} from '../utils/contants.es';
import lang from '../utils/lang.es';
import {dateToInternationalHuman, deleteCacheKey} from '../utils/utils.es';
import SubscriptionButton from './SubscriptionButton.es';

function isClickOutside(target, ...elements) {
	return !elements.some((element) => {
		if (typeof element === 'string') {
			return !!target.closest(element);
		}

		return element && element.contains(target);
	});
}

export default function TagsLayout({
	context,
	orderBy,
	page,
	pageSize,
	search,
	tag,
}) {
	return (
		<div className="question-tags" key={tag.id}>
			<Link
				onClick={(event) => {
					const clickingOutside = isClickOutside(
						event.target,
						'#questions-subscription-link'
					);
					if (!clickingOutside) {
						event.preventDefault();
					}
				}}
				title={tag.name}
				to={`/questions/${ALL_SECTIONS_ID}?selectedtags=${tag.name}&taggedwith=some-specific-tag`}
			>
				<div className="align-items-center card card-interactive card-interactive-primary card-type-template d-flex justify-content-between template-card-horizontal">
					<div>
						<div className="card-body d-flex flex-column">
							<div className="card-row">
								<div className="autofit-row autofit-row-center autofit-row-expand">
									<div>
										<div className="autofit-col autofit-col-expand">
											<div className="autofit-section">
												<div className="card-title">
													<span className="question-tags-ellipsis text-truncate">
														{tag.name}
													</span>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>

							<div className="card-row">
								<div className="autofit-col autofit-col-expand card-subtitle">
									{orderBy === 'latest-created' ? (
										<div>
											{lang.sub(
												Liferay.Language.get(
													'created-on'
												),
												[
													dateToInternationalHuman(
														tag.dateCreated
													),
												]
											)}
										</div>
									) : (
										<div>
											{lang.sub(
												Liferay.Language.get(
													'used-x-times'
												),
												[tag.keywordUsageCount]
											)}
										</div>
									)}
								</div>
							</div>
						</div>
					</div>

					<div className="c-pr-3" id="questions-subscription-link">
						{tag.actions.subscribe && (
							<div className="autofit-col">
								<div className="autofit-section">
									<SubscriptionButton
										isSubscribed={tag.subscribed}
										onSubscription={() => {
											deleteCacheKey(
												getTagsOrderByDateCreatedQuery,
												{
													page,
													pageSize,
													search,
													siteKey: context,
												}
											);
											deleteCacheKey(
												getTagsOrderByNumberOfUsagesQuery,
												{
													page,
													pageSize,
													search,
													siteKey: context,
												}
											);
										}}
										queryVariables={{
											keywordId: tag.id,
										}}
										subscribeQuery={subscribeTagQuery}
										unsubscribeQuery={unsubscribeTagQuery}
									/>
								</div>
							</div>
						)}
					</div>
				</div>
			</Link>
		</div>
	);
}
