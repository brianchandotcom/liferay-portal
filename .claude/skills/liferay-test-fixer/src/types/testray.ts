export type Status = 'PASSED' | 'FAILED' | 'BLOCKED' | 'UNTESTED'

export type CaseResult = {
	id: number
	dueStatus: Status
	errors?: string
	r_buildToCaseResult_c_buildId: number
	r_caseToCaseResult_c_caseId: number
}

export type Build = {
	id: number
	r_routineToBuilds_c_routineId: number
}

export type Case = {
	id: number
	name: string
	r_caseTypeToCases_c_caseTypeId: number
}

export type CaseType = {
	id: number
	name:
		| 'Playwright Test'
		| 'Modules Integration Test'
		| 'Modules Unit Test'
		| 'Modules Semantic Versioning Test'
		| 'Automated Functional Test'
		| 'JS Unit Test'
		| 'Batch'
}

export type History = Array<{
	gitHash: string
	status: Status
	testrayRoutineId: number
}>
