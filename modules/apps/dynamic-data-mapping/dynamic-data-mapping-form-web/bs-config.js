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
	cors: true,
	files: [
		"src/main/resources/META-INF/resources/metal",
		{
			match: ["src/**/*.es.js", "src/**/*.soy"],
			fn: callback
		}
	],
	port: 3000,
	proxy: "http://localhost:8080/",
	rewriteRules: [
		{
			match: /localhost\:8080/g,
			fn: function(req, res, match) {
				return 'localhost:3000';
			}
		}
	],
	serveStatic: [
		{
			route: `/o/js/resolved-module/${name}@${version}/metal`,
			dir: 'classes/META-INF/resources/metal'
		}
	],
	watch: true
};