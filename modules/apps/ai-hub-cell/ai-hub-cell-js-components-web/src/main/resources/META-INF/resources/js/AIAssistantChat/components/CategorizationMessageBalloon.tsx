/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import CategorizationSuggestions from '../../Categorization/components/CategorizationSuggestions';
import {
	COMMIT_EVENT,
	CategorizeEventPayload,
	OPEN_CATEGORIZATION_PANEL_EVENT,
} from '../../Categorization/events';
import {getCandidateCategories} from '../../Categorization/services/getCandidateCategories';
import {getExistingTags} from '../../Categorization/services/getExistingTags';
import {ECategorizationAgent, Suggestion} from '../../Categorization/types';
import useCategorizationAgent from '../../Categorization/useCategorizationAgent';

function getKey(suggestion: Suggestion): string {
	return `${suggestion.id ?? suggestion.name}`;
}

export default function CategorizationMessageBalloon({
	agent,
	classNameId,
	cmsGroupId,
	content,
	count,
	currentCategoryIds,
	currentTagNames,
	scopeId,
	targets,
}: CategorizeEventPayload) {
	const [committed, setCommitted] = useState(false);
	const [dismissed, setDismissed] = useState<string[]>([]);

	const {regenerate, resolveTargets, run, status, suggestions} =
		useCategorizationAgent(agent);

	useEffect(() => {
		let active = true;

		const fetchCandidateCategories = async () => {
			try {
				return {
					candidateCategories: await getCandidateCategories({
						classNameId,
						cmsGroupId,
						scopeId,
					}),
				};
			}
			catch (error) {
				console.warn((error as Error).message);

				return {candidateCategories: []};
			}
		};

		const fetchExistingTags = async () => {
			try {
				return {
					existingTags: await getExistingTags({cmsGroupId, scopeId}),
				};
			}
			catch (error) {
				console.warn((error as Error).message);

				return {existingTags: []};
			}
		};

		(async () => {
			const data =
				agent === ECategorizationAgent.AUTO_CATEGORIZE
					? await fetchCandidateCategories()
					: await fetchExistingTags();

			if (!active) {
				return;
			}

			if (targets?.length) {
				resolveTargets({content, count, ...data}, targets);
			}
			else {
				run({content, count, ...data});
			}
		})();

		return () => {
			active = false;
		};
	}, [
		agent,
		classNameId,
		cmsGroupId,
		content,
		count,
		resolveTargets,
		run,
		scopeId,
		targets,
	]);

	const visibleSuggestions = suggestions.filter(
		(suggestion) => !dismissed.includes(getKey(suggestion))
	);

	const isCategories = agent === ECategorizationAgent.AUTO_CATEGORIZE;

	const newCategoryCount = visibleSuggestions.filter(
		(suggestion) =>
			typeof suggestion.id === 'number' &&
			!(currentCategoryIds ?? []).includes(suggestion.id)
	).length;

	const lowerCaseCurrentTagNames = (currentTagNames ?? []).map((name) =>
		name.toLowerCase()
	);

	const newTagCount = visibleSuggestions.filter(
		(suggestion) =>
			!lowerCaseCurrentTagNames.includes(suggestion.name.toLowerCase())
	).length;

	const committedCount = isCategories ? newCategoryCount : newTagCount;

	const confirmationMessage = sub(
		isCategories
			? Liferay.Language.get(
					'great-i-have-added-x-categories-to-your-content'
				)
			: Liferay.Language.get('great-i-have-added-x-tags-to-your-content'),
		`${committedCount}`
	);

	return (
		<>
			<div className="ai-assistant-chat__ai-assistant-message-balloon mb-2 p-2 rounded">
				<CategorizationSuggestions
					committed={committed}
					kind={isCategories ? 'categories' : 'tags'}
					onCommit={(committedSuggestions) => {
						Liferay.fire(COMMIT_EVENT, {
							agent,
							scopeId,
							suggestions: committedSuggestions,
						});

						setCommitted(true);
					}}
					onDismiss={(suggestion) =>
						setDismissed((previousDismissed) => [
							...previousDismissed,
							getKey(suggestion),
						])
					}
					onRegenerate={() => {
						setCommitted(false);
						setDismissed([]);

						regenerate();
					}}
					status={status === 'idle' ? 'loading' : status}
					suggestions={visibleSuggestions}
				/>
			</div>

			{committed && committedCount > 0 ? (
				<div className="ai-assistant-chat__ai-assistant-message-balloon d-flex flex-column mb-2 rounded">
					<div className="d-flex flex-row">
						<div className="align-items-start d-inline-block ml-2 mt-2">
							<ClayIcon
								color="#0B5FFF"
								height={12}
								spritemap={Liferay.Icons.spritemap}
								symbol="stars"
								width={12}
							/>
						</div>

						<div className="m-2">
							{confirmationMessage}{' '}

							{sub(Liferay.Language.get('x-to-see-them'), [
								<ClayButton
									className="align-baseline border-0 font-weight-semi-bold p-0 text-decoration-underline"
									displayType="unstyled"
									key="open-categorization-panel"
									onClick={() =>
										Liferay.fire(
											OPEN_CATEGORIZATION_PANEL_EVENT,
											{}
										)
									}
								>
									{Liferay.Language.get('click-here')}
								</ClayButton>,
							])}
						</div>
					</div>
				</div>
			) : null}
		</>
	);
}
