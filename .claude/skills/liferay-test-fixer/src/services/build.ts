import { TestrayClient } from '@/lib/testray-client'

import { Build } from '@/types/testray'

export async function getBuild(id: Build['id']) {
	return await TestrayClient.get<Build>({
		url: `https://testray.liferay.com/o/c/builds/${id}`,
	})
}
