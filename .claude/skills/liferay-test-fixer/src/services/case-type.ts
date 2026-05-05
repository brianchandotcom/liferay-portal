import { TestrayClient } from '@/lib/testray-client'

import { CaseType } from '@/types/testray'

export async function getCaseTypes(ids: Array<CaseType['id']>) {
	return await TestrayClient.get<CaseType[]>({
		url: 'https://testray.liferay.com/o/c/casetypes/',
		params: {
			filter: ids.map((id) => `id eq '${id}'`).join(' or '),
		},
	})
}
