import { hasHistory } from '@/lib/test-history'
import { getTypeLabel } from '@/lib/test-type'

import { getBuild } from '@/services/build'
import { getCases } from '@/services/case'
import { getCaseResult } from '@/services/case-result'
import { getCaseTypes } from '@/services/case-type'
import { getCaseHistory } from '@/services/history'

import { Case, CaseResult, CaseType, History } from '@/types/testray'

export class CurrentlyPassingError extends Error {
	constructor(message: string) {
		super(message)
		this.name = 'CurrentlyPassingError'
	}
}

export type FailureData = {
	name: string
	type: string
	errorTrace?: string
	lastPassSha?: string | null
	firstFailSha?: string | null
}

export async function getFailureData(
	caseResultId: CaseResult['id']
): Promise<FailureData> {
	const caseResult = await getCaseResult(caseResultId)

	if (!caseResult) {
		throw new Error(`No case result found for id ${caseResultId}`)
	}

	const caseId = caseResult.r_caseToCaseResult_c_caseId
	const buildId = caseResult.r_buildToCaseResult_c_buildId

	const [testCase] = await getCases([caseId])

	if (!testCase) {
		throw new Error(`No case found for id ${caseId}`)
	}

	if (caseResult.dueStatus === 'PASSED') {
		throw new CurrentlyPassingError(
			`Case result ${caseResultId} for test "${testCase.name}" has status PASSED. Nothing to fix.`
		)
	}

	const caseTypes = await getCaseTypes([
		testCase.r_caseTypeToCases_c_caseTypeId,
	])

	const type = getType(testCase, caseTypes)

	if (type === 'Java Log Assertor') {
		return {
			name: testCase.name,
			type,
			errorTrace: caseResult.errors,
		}
	}

	let history: History | null = null

	if (hasHistory(testCase)) {
		const build = await getBuild(buildId)
		const routineId = build.r_routineToBuilds_c_routineId

		const all = await getCaseHistory({ caseId, pageSize: 300 })

		history = all.filter((entry) => entry.testrayRoutineId === routineId)
	}

	const { lastPassSha, firstFailSha } = findHashes(history)

	return {
		name: testCase.name,
		type: getTypeLabel(type),
		errorTrace: caseResult.errors,
		lastPassSha,
		firstFailSha,
	}
}

type TypeName = CaseType['name'] | 'Java Log Assertor'

function getType(testCase: Case, caseTypes: CaseType[]): TypeName {
	if (testCase.name.includes('PortalLogAssertor')) {
		return 'Java Log Assertor'
	}

	const type = caseTypes.find(
		({ id }) => id === testCase.r_caseTypeToCases_c_caseTypeId
	)

	return type!.name
}

function findHashes(history: History | null) {
	if (!history) {
		return { lastPassSha: null, firstFailSha: null }
	}

	let lastPassSha: string | null = null
	let firstFailSha: string | null = null

	for (const entry of history) {
		if (entry.status === 'PASSED') {
			lastPassSha = entry.gitHash
			break
		}

		if (entry.status === 'FAILED') {
			firstFailSha = entry.gitHash
		}
	}

	return { lastPassSha, firstFailSha }
}
