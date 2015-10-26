'use strict';

var gulp = require('gulp');
var gulpReplace = require('gulp-replace');
var path = require('path');

var soyDir = path.join('bower_components', 'metal', 'src', 'soy');
var soyImport = "'use strict';\n\nimport soyutils from 'module:soyutils';"
var soyRegex = /'use strict';(\s*import\s+soyutils\s+from\s+'module:soyutils';)?/

gulp.task(
	'addSoyImport',
	function(done) {
		return gulp.src(
				path.join(soyDir, 'SoyComponent.js')
			).pipe(
				gulpReplace(soyRegex, soyImport)
			).pipe(
				gulp.dest(soyDir)
			);
	}
);

gulp.task('default', ['addSoyImport', 'build:amd:jquery']);

var gulpMetal = require('gulp-metal');

gulpMetal.registerTasks(
	{
		buildAmdJqueryDest: 'classes/META-INF/resources',
		buildSrc: 'bower_components/crystal-*/src/**/*.js',
		bundleFileName: 'crystal.js',
		cssSrc: 'bower_components/crystal-*/src/**/*.css',
		globalName: 'crystal',
		scssSrc: 'bower_components/crystal-*/src/**/*.scss'
	}
);