const {name, version} = require('./package.json');
const spawn = require('cross-spawn');

function callback() {
	spawn.sync(
		'npm',
		['run', 'build'],
		{
			stdio: 'inherit'
		}
	);
}

module.exports = {
	files: [
		"src/main/resources/META-INF/resources/metal",
		{
			match: ["src/**/*.es.js"],
			fn: callback
		}
	],
	port: 3000,
	proxy: "http://localhost:8080/",
	serveStatic: [
		{
			route: `/o/js/resolved-module/${name}@${version}/metal`,
			dir: 'classes/META-INF/resources/metal'
		}
	],
	watch: true
};