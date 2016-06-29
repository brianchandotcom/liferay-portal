'use strict';

var path = require('path');
var osgi = require('./osgi.js');

var portalDir = osgi.portalDir();

var frontendDir = path.join(portalDir, 'modules', 'apps', 'foundation', 'frontend-js', 'frontend-js-web');
var frontendTmpSrcDir = path.join(frontendDir, 'tmp', 'META-INF', 'resources');

var soyDir = path.join(portalDir, 'modules', 'apps', 'foundation', 'frontend-js', 'frontend-js-soyutils-web');
var soyJSDir = path.join(osgi.resourcesClassesPath(soyDir));

var ddmRendererResourcesDir = osgi.ddmBundleResourcesPath('dynamic-data-mapping-form-renderer');
var ddmTextResourcesDir = osgi.ddmBundleResourcesPath('dynamic-data-mapping-type-text');

module.exports = [
	frontendTmpSrcDir + '/aui/aui/aui.js',
	frontendTmpSrcDir + '/loader/loader.js',
	'mocks/*.js',
	soyJSDir + '/config.js',
	soyDir + '/classes/META-INF/resources/soyutils.js',
	{
		included: false,
		pattern: frontendTmpSrcDir + '/aui/**/*.js',
		served: true
	},
	ddmRendererResourcesDir + '/js/*.js',
	ddmRendererResourcesDir + '/*.js',
	ddmTextResourcesDir + '/*.js',
	{
		included: false,
		pattern: frontendTmpSrcDir + '/aui/**/*.css',
		served: true
	},
	{
		included: false,
		pattern: frontendTmpSrcDir + '/aui/**/*.png',
		served: true
	}
];