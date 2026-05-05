export class TestrayException extends Error {
	status: number

	constructor({
		message = 'Testray request failed',
		status,
	}: {
		message?: string
		status: number
	}) {
		super(message)

		this.status = status
	}
}
