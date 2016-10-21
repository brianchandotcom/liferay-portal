'use strict';

var metal = require('gulp-metal');

var srcBlob = 'src/**/*.es.js';
var testBlob = 'test/**/*.es.js';

metal.registerTasks({
	buildSrc: [srcBlob],
	formatGlobs: [srcBlob, testBlob],
	lintGlobs: [srcBlob, testBlob]
});