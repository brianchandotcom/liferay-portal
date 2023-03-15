class ValidationError extends Error {
	field: string;

	constructor(field: string, localizedMessage: string) {
		super(localizedMessage);

		this.field = field;
	}
}

export default ValidationError;
