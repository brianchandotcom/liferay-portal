'use strict';

var gulp = require('gulp');
var path = require('path');
var replace = require('gulp-replace');

var soyFolder = path.join('bower_components', 'metal', 'src', 'soy');
var soyImport = "'use strict';\n\nimport soyutils from 'module:soyutils';"
var soyRegex = /'use strict';(\s*import\s+soyutils\s+from\s+'module:soyutils';)?/

gulp.task('addSoyImport', function(done) {
    return gulp.src(path.join(soyFolder, 'SoyComponent.js'))
		.pipe(replace(soyRegex, soyImport))
		.pipe(gulp.dest(soyFolder));
});

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