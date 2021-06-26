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

import ClayButton from '@clayui/button';
import ClayCard from '@clayui/card';
import ClayEmptyState from '@clayui/empty-state';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useManualQuery} from 'graphql-hooks';
import React, {useContext, useEffect, useState} from 'react';
import {Helmet} from 'react-helmet';
import {Redirect, withRouter} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import Alert from '../../components/Alert.es';
import Link from '../../components/Link.es';
import NewTopicModal from '../../components/NewTopicModal.es';
import {
	getSectionBySectionTitleQuery,
	getSectionsQuery,
} from '../../utils/client.es';
import lang from '../../utils/lang.es';
import {
	getBasePathWithHistoryRouter,
	historyPushWithSlug,
} from '../../utils/utils.es';

export default withRouter(({history}) => {
	const context = useContext(AppContext);
	const historyPushParser = historyPushWithSlug(history.push);
	const [topicModalVisibility, setTopicModalVisibility] = useState(false);

	const [error, setError] = useState({});
	const [loading, setLoading] = useState(true);
	const [sections, setSections] = useState({});

	useEffect(() => {
		document.title = 'Questions';
	}, []);

	const [getSections] = useManualQuery(getSectionsQuery, {
		variables: {siteKey: context.siteKey},
	});
	const [getSectionBySectionTitle] = useManualQuery(
		getSectionBySectionTitleQuery,
		{
			variables: {
				filter: `title eq '${context.rootTopicId}' or id eq '${context.rootTopicId}'`,
				siteKey: context.siteKey,
			},
		}
	);

	useEffect(() => {
		const fn =
			!context.rootTopicId || context.rootTopicId === '0'
				? getSections()
				: getSectionBySectionTitle().then((result) => ({
						...result,
						data: result.data.messageBoardSections.items[0],
				  }));

		fn.then((result) => ({
			...result,
			data: result.data.messageBoardSections,
		}))
			.then(({data, loading}) => {
				setSections(data || []);
				setLoading(loading);
			})
			.catch((error) => {
				if (process.env.NODE_ENV === 'development') {
					console.error(error);
				}
				setLoading(false);
				setError({message: 'Loading Topics', title: 'Error'});
			});
	}, [
		context.rootTopicId,
		context.siteKey,
		getSectionBySectionTitle,
		getSections,
	]);

	function descriptionTruncate(description) {
		return description?.length > 150
			? description.substring(0, 150) + '...'
			: description;
	}

	return (
		<section className="c-mt-3 questions-section">
			{!context.showCardsForTopicNavigation && (
				<Redirect to={'/questions/' + context.rootTopicId} />
			)}

			<div className="questions-container row">
				{!loading && (
					<>
						{sections &&
							sections.actions &&
							!!sections.actions.create &&
							sections.items &&
							sections.items.length > 0 && (
								<div className="c-mb-4 col-lg-4 col-md-6 col-xl-3">
									<div className="questions-card text-decoration-none text-secondary">
										<ClayCard
											className="questions-new-section"
											onClick={() =>
												setTopicModalVisibility(true)
											}
										>
											<ClayCard.Body>
												<ClayEmptyState
													description=""
													imgSrc={
														context.includeContextPath +
														'/assets/new_topic_illustration.png'
													}
													title=""
												>
													<ClayIcon symbol="plus" />
													<span className="c-ml-3 text-truncate">
														{Liferay.Language.get(
															'new-topic'
														)}
													</span>
												</ClayEmptyState>
											</ClayCard.Body>
										</ClayCard>
									</div>
								</div>
							)}

						{(sections.items &&
							sections.items.length > 0 &&
							sections.items.map((section) => (
								<div
									className="c-mb-4 col-lg-4 col-md-6 col-xl-3"
									key={section.id}
								>
									<Link
										className="questions-card text-decoration-none text-secondary"
										to={`/questions/${
											context.useTopicNamesInURL
												? section.title
												: section.id
										}`}
									>
										<ClayCard>
											<ClayCard.Body>
												<ClayCard.Description
													className="text-dark"
													displayType="title"
												>
													{section.title}
												</ClayCard.Description>

												<ClayCard.Description
													className="c-mt-3 flex-grow-1"
													displayType="text"
													truncate={true}
												>
													{descriptionTruncate(
														section.description
													)}
												</ClayCard.Description>

												<ClayCard.Description
													className="c-mt-4 justify-content-end small"
													displayType="text"
													truncate={false}
												>
													<span className="x-questions">
														{lang.sub(
															Liferay.Language.get(
																'x-questions'
															),
															[
																section.numberOfMessageBoardThreads,
															]
														)}
													</span>
													<button className="btn btn-link btn-sm d-xl-none float-right font-weight-bold p-0">
														View Topic
													</button>
												</ClayCard.Description>
											</ClayCard.Body>
										</ClayCard>
									</Link>
								</div>
							))) || (
							<ClayEmptyState
								description={Liferay.Language.get(
									'there-are-no-topics-in-this-page-be-the-first-to-create-a-topic'
								)}
								imgSrc={
									context.includeContextPath +
									'/assets/no_topics_illustration.png'
								}
								title={Liferay.Language.get(
									'this-page-has-no-topics'
								)}
							>
								{sections &&
									sections.actions &&
									!!sections.actions.create && (
										<ClayButton
											displayType="primary"
											onClick={() =>
												setTopicModalVisibility(true)
											}
										>
											{Liferay.Language.get('new-topic')}
										</ClayButton>
									)}
							</ClayEmptyState>
						)}
					</>
				)}

				<NewTopicModal
					currentSectionId={+context.rootTopicId}
					onClose={() => setTopicModalVisibility(false)}
					onCreateNavigateTo={() => {
						historyPushParser(`/tmp`);
						history.goBack();
					}}
					setError={setError}
					visible={topicModalVisibility}
				/>
			</div>
			{loading && <ClayLoadingIndicator />}

			<Alert info={error} />

			{context.historyRouterBasePath && (
				<Helmet>
					<title>Questions</title>
					<link
						href={getBasePathWithHistoryRouter(
							context.historyRouterBasePath
						)}
						rel="canonical"
					/>
				</Helmet>
			)}
		</section>
	);
});
