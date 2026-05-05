export function hasHistory(testCase: { name: string }) {
	return (
		testCase.name !== 'Top Level Build' &&
		!testCase.name.includes('PortalLogAssertor')
	)
}
