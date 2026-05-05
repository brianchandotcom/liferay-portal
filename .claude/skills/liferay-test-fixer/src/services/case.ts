import { TestrayClient } from '@/lib/testray-client'

import { Case } from '@/types/testray'

export async function getCases(ids: Array<Case['id']>) {
	const batchSize = 80

	let allCases: Case[] = []

	for (let i = 0; i < ids.length; i += batchSize) {
		const batch = ids.slice(i, i + batchSize)

		const filter = batch.map((id) => `id eq '${id}'`).join(' or ')

		const cases = await TestrayClient.get<Case[]>({
			url: 'https://testray.liferay.com/o/c/cases/',
			params: {
				filter,
				pageSize: 500,
			},
		})

		allCases = allCases.concat(cases)
	}

	return allCases
}
