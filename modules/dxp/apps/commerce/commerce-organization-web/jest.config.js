module.exports = {
	verbose: true,
	setupFilesAfterEnv: [
		'./setupTests.js'
	],
	moduleFileExtensions: ['js', 'jsx', 'html'],
	transform: {
		'^.+\\.(js|jsx)?$': 'babel-jest',
		'^.+\\.html?$': 'html-loader-jest'
	},
	testMatch: [
		'<rootDir>/**/*.spec.js',
		'<rootDir>/**/*.spec.jsx'
	],
	modulePathIgnorePatterns: [
		'<rootDir>[/\\\\](build|docs|node_modules|deploy|scripts)[/\\\\]'
	],
	collectCoverageFrom: [
		'**/*.{js,jsx}',
		'!**/node_modules/**',
		'!**/vendor/**'
	]
};
