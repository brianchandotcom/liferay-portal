'use strict';

var path = require('path');

module.exports = {
	appsDir: function() {
		return path.resolve(this.portalDir(), 'modules', 'apps');
	},

	ddlBundlePath: function(bundleName) {
		return path.resolve(this.appsDir(), 'forms-and-workflow', 'dynamic-data-lists', bundleName);
	},

	ddlBundleResourcesPath: function(bundleName) {
		return path.resolve(this.resourcesPath(this.ddlBundlePath(bundleName)));
	},

	ddmBundlePath: function(bundleName) {
		return path.resolve(this.appsDir(), 'forms-and-workflow', 'dynamic-data-mapping', bundleName);
	},

	ddmBundleResourcesPath: function(bundleName) {
		return path.resolve(this.resourcesPath(this.ddmBundlePath(bundleName)));
	},

	portalDir: function() {
		return path.resolve('../../../../../');
	},

	resourcesClassesPath: function(bundleDir) {
		return path.resolve(bundleDir, 'classes', 'META-INF', 'resources');
	},

	resourcesMainPath: function(bundleDir) {
		return path.resolve(bundleDir, 'src', 'main', 'resources', 'META-INF', 'resources');
	},

	resourcesPath: function(bundleDir) {
		return path.resolve(bundleDir, 'src', 'main', 'resources', 'META-INF', 'resources');
	}
};