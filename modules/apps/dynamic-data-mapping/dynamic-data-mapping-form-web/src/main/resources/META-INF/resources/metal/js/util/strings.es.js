export const capitalize = text => {
	return text.charAt(0).toUpperCase() + text.slice(1);
};

const SPLIT_REGEX = /({\d+})/g;

export function sub(langKey, args) {
	const keyArray = langKey.split(SPLIT_REGEX).filter(val => val.length !== 0);

	for (let i = 0; i < args.length; i++) {
		const arg = args[i];

		const indexKey = `{${i}}`;

		let argIndex = keyArray.indexOf(indexKey);

		while (argIndex >= 0) {
			keyArray.splice(argIndex, 1, arg);

			argIndex = keyArray.indexOf(indexKey);
		}
	}

	return keyArray.join('');
}