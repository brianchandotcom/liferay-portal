export default (...functions) =>
	functions.reduce((a, c) => (...args) => a(c(...args)));
