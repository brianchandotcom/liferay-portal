var fs = require('fs');
var gulp = require('gulp');

gulp.task(
	'hello',
	function(cb) {
		fs.writeFile('generated.txt', 'Hello World', cb)
	});