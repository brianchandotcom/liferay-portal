import { CaseType } from '@/types/testray'

type TypeName = CaseType['name'] | 'Java Log Assertor'

export function getTypeLabel(typeName: TypeName) {
	if (typeName === 'Playwright Test') {
		return 'Playwright'
	} else if (typeName === 'Modules Integration Test') {
		return 'Java Integration'
	} else if (typeName === 'Modules Unit Test') {
		return 'Java Unit'
	} else if (typeName === 'Modules Semantic Versioning Test') {
		return 'Java Semantic Versioning'
	} else if (typeName === 'Automated Functional Test') {
		return 'Poshi'
	} else if (typeName === 'JS Unit Test') {
		return 'JavaScript'
	}

	return typeName
}
