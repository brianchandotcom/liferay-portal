import { TestrayClient } from '@/lib/testray-client'

import { CaseResult } from '@/types/testray'

export async function getCaseResult(id: CaseResult['id']) {
	return await TestrayClient.get<CaseResult>({
		url: `https://testray.liferay.com/o/c/caseresults/${id}`,
	})
}
