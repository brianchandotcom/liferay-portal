var child_process = require('child_process');

run(
	'metalsoy',
	[]
);

run(
	'babel',
	[
		'-d',
		'classes/META-INF/resources',
		'src/main/resources/META-INF/resources'
	]
);

run(
	'liferay-npm-bundler',
	[])
;

run(
	'liferay-npm-bridge-generator',
	[
		'-i',
		'classes/META-INF/resources',
		'-o',
		'classes/META-INF/resources/bridge/frontend-js-web',
		'-g',
		'liferay/**/*.js',
		'-d',
		'(.*)\\.js$:bridge/frontend-js-web/$1',
		'-s',
		'(.*)\\.js$:frontend-js-web/$1'
	]
);

function run(cmd, args) {
	var proc = child_process.spawnSync(cmd, args, {stdio: 'inherit'});

	if (proc.status != 0) {
		process.exit(proc.status);
	}
}