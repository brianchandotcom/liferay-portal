/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {sub} from 'frontend-js-web';

import {AutoFixCandidate} from '../types/AutoFix';
import {AutoFixDefinition} from './AutoFixDefinition';

// The Title Generator agent returns a JSON object shaped
// {"candidates":[{"title","rationale"}]}, sometimes wrapped in a markdown
// code fence.

function parseCandidates(response: string): AutoFixCandidate[] | undefined {
	const braceMatch = response.match(/\{[\s\S]*\}/);
	const fenceMatch = response.match(/```(?:json)?\s*([\s\S]*?)```/);

	for (const candidate of [fenceMatch?.[1], response, braceMatch?.[0]]) {
		if (!candidate) {
			continue;
		}

		try {
			const candidates = JSON.parse(candidate)?.candidates;

			if (Array.isArray(candidates)) {
				return candidates
					.filter((item) => Boolean(item?.title))
					.map((item) => ({
						rationale: item.rationale,
						value: item.title,
					}));
			}
		}
		catch {
			continue;
		}
	}

	return undefined;
}

export const TITLE_AUTO_FIX_DEFINITION: AutoFixDefinition = {
	agentExternalReferenceCode: 'L_SEO_STUDIO_TITLE_GENERATOR',
	getApplyErrorMessage: () =>
		Liferay.Language.get('unable-to-apply-the-title'),
	getCandidateLabel: (candidate) => candidate.value,
	getGenerateErrorMessage: () =>
		Liferay.Language.get('unable-to-generate-title-suggestions'),
	getPromptMessage: (pageName) =>
		sub(
			Liferay.Language.get('help-me-create-a-title-tag-for-the-page-x'),
			pageName
		),
	getResolvedPartialMessage: () =>
		Liferay.Language.get(
			'the-title-tag-was-applied-but-the-insight-could-not-be-marked-as-resolved'
		),
	getResolvedSuccessMessage: () =>
		Liferay.Language.get(
			'the-title-tag-was-applied-and-the-insight-was-resolved'
		),
	getSuggestionsIntroMessage: () =>
		Liferay.Language.get(
			'here-are-some-optimized-title-options-for-this-page'
		),
	insightTypeName: 'missingOrEmptyTitleTag',
	parseCandidates,
};
