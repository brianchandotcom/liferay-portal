import fs from 'node:fs'
import path from 'node:path'

import { CurrentlyPassingError, getFailureData } from '@/lib/get-failure-data'

async function main() {
	const arg = process.argv[2]

	if (!arg) {
		console.error('Usage: npm run collect-failure-data -- <caseResultId>')

		process.exit(1)
	}

	const caseResultId = Number(arg)

	if (!Number.isInteger(caseResultId) || caseResultId <= 0) {
		console.error(`Invalid case result id: ${arg}`)
		process.exit(1)
	}

	const failure = await getFailureData(caseResultId)

	const date = new Date().toISOString().split('T')[0]

	const outputDir = path.join(process.cwd(), 'output')

	fs.mkdirSync(outputDir, { recursive: true })

	const outputPath = path.join(
		outputDir,
		`test-failure-${caseResultId}-${date}.json`
	)

	fs.writeFileSync(outputPath, JSON.stringify(failure, null, 2))

	process.stdout.write(`${outputPath}\n`)
}

main().catch((err) => {
	if (err instanceof CurrentlyPassingError) {
		console.error(err.message)
		process.exit(2)
	}

	console.error(err instanceof Error ? err.message : err)
	process.exit(1)
})
