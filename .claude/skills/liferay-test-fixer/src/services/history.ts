import { TestrayClient } from '@/lib/testray-client'

import { Case, History } from '@/types/testray'

export async function getCaseHistory({
	caseId,
	pageSize,
}: {
	caseId: Case['id']
	pageSize?: number
}) {
	return await TestrayClient.get<History>({
		url: `https://testray.liferay.com/o/testray-rest/v1.0/testray-case-result-history/${caseId}`,
		params: {
			pageSize,
			sort: 'executionDate:desc',
		},
	})
}
