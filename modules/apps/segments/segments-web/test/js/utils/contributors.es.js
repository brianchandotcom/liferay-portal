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

import {applyConjunctionChangeToContributor} from '../../../src/main/resources/META-INF/resources/js/utils/contributors.es';

describe('applyConjunctionChangeToContributor', () => {
	it('changes contributors to selected conjunction', () => {
		const mockContributors = [
			{
				conjunctionId: 'and',
			},
			{
				conjunctionId: 'and',
			},
		];
		const newConjunctionName = 'or';

		const updatedContributors = applyConjunctionChangeToContributor(
			mockContributors,
			newConjunctionName
		);

		updatedContributors.map(c =>
			expect(c).toEqual(
				expect.objectContaining({conjunctionId: newConjunctionName})
			)
		);
	});

	it('does not change contributors to selected conjunction when it is not supported', () => {
		const mockContributors = [
			{
				conjunctionId: 'and',
			},
			{
				conjunctionId: 'and',
			},
		];
		const newConjunctionName = 'bad_conjunction';

		const updatedContributors = applyConjunctionChangeToContributor(
			mockContributors,
			newConjunctionName
		);

		updatedContributors.map(c =>
			expect(c).toEqual(expect.objectContaining({conjunctionId: 'and'}))
		);
	});

	it('does not change contributors to selected conjunction when it is already in the contributor', () => {
		const mockContributors = [
			{
				conjunctionId: 'and',
			},
			{
				conjunctionId: 'and',
			},
		];
		const newConjunctionName = 'and';

		const updatedContributors = applyConjunctionChangeToContributor(
			mockContributors,
			newConjunctionName
		);

		updatedContributors.map(c =>
			expect(c).toEqual(expect.objectContaining({conjunctionId: 'and'}))
		);
	});
});
