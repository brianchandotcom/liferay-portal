/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

YUI.add(
	'liferay-browser-selectors',
	A => {
		var REGEX_VERSION_DOT = /\./g;

		var YUI3_JS_ENABLED = 'yui3-js-enabled';

		var parseVersionNumber = function(str) {
			var count = 0;

			return parseFloat(
				str.replace(REGEX_VERSION_DOT, () => {
					return count++ == 1 ? '' : '.';
				})
			);
		};

		var DEFAULTS_VERSION = ['0', '0'];

		var getVersion = function(regex, userAgent) {
			var version = (userAgent.match(regex) || DEFAULTS_VERSION)[1];

			return parseVersionNumber(version);
		};

		var BROWSERS = [
			'ie',
			'opera',
			'chrome',
			'edge',
			'aol',
			'camino',
			'firefox',
			'flock',
			'mozilla',
			'netscape',
			'icab',
			'konqueror',
			'safari'
		];

		var MAP_OS_SELECTORS = {
			macintosh: 'mac',
			windows: 'win'
		};

		var nav = navigator;

		var CONFIG = A.config;

		var DOC = CONFIG.doc;

		var userAgent = nav.userAgent;

		var UA = A.UA;

		var OS = UA.os;

		var UAX = {
			agent: userAgent,

			aol: 0,
			browser: 0,
			camino: 0,
			firefox: 0,
			flock: 0,
			icab: 0,
			konqueror: 0,
			mozilla: 0,
			netscape: 0,
			safari: 0
		};

		UAX.mac = OS == 'macintosh';
		UAX.rhino = OS == 'rhino';
		UAX.win = OS == 'windows';

		var BrowserSelectors = {
			getSelectors() {
				// The methods in this if block only run once across all instances

				if (!UA.selectors) {
					if (userAgent.indexOf('Edge') !== -1) {
						UAX.edge = getVersion(/Edge\/([^\s]*)/, userAgent);
					}

					if (UA.ie) {
						UAX.aol = getVersion(
							/America Online Browser ([^\s]*);/,
							userAgent
						);

						var docMode = DOC.documentMode;

						if (docMode) {
							UA.browser = UA.ie;
							UA.ie = docMode;
						}
					} else if (UA.gecko) {
						UAX.netscape = getVersion(
							/(Netscape|Navigator)\/([^\s]*)/,
							userAgent
						);
						UAX.flock = getVersion(/Flock\/([^\s]*)/, userAgent);
						UAX.camino = getVersion(/Camino\/([^\s]*)/, userAgent);
						UAX.firefox = getVersion(
							/Firefox\/([^\s]*)/,
							userAgent
						);
					} else if (UA.webkit) {
						UAX.safari = getVersion(
							/Version\/([^\s]*) Safari/,
							userAgent
						);
					} else {
						UAX.icab = getVersion(
							/iCab(?:\/|\s)?([^\s]*)/,
							userAgent
						);
						UAX.konqueror = getVersion(
							/Konqueror\/([^\s]*)/,
							userAgent
						);
					}

					if (!UAX.win && !UAX.mac) {
						var linux = /Linux/.test(userAgent);
						var sun = /Solaris|SunOS/.test(userAgent);

						if (linux) {
							UA.os = 'linux';
							UAX.linux = linux;
						} else if (sun) {
							UA.os = 'sun';
							UAX.sun = sun;
						}
					}

					var touch = UA.touchEnabled;

					UAX.touch = touch;
					UAX.touchMobile = touch && !!UA.mobile;

					A.mix(UA, UAX);

					var browserList = [];
					var versionMajor = 0;

					var browser;
					var uaVersionMajor;
					var uaVersionMinor;
					var version;

					var versionObj = {
						major: versionMajor,
						string: ''
					};

					var i = BROWSERS.length;

					while (i--) {
						browser = BROWSERS[i];
						version = UA[browser];

						if (version > 0) {
							versionMajor = parseInt(version, 10);
							uaVersionMajor = browser + versionMajor;

							uaVersionMinor = browser + version;

							if (String(version).indexOf('.') > -1) {
								uaVersionMinor = uaVersionMinor.replace(
									/\.(\d).*/,
									'-$1'
								);
							} else {
								uaVersionMinor += '-0';
							}

							browserList.push(
								browser,
								uaVersionMajor,
								uaVersionMinor
							);

							versionObj.string = browser + '';
							versionObj.major = versionMajor;
						}
					}

					UA.version = versionObj;

					UA.renderer = '';

					if (UA.ie) {
						UA.renderer = 'trident';
					} else if (UA.edge) {
						UA.renderer = 'edgeHTML';
					} else if (UA.gecko) {
						UA.renderer = 'gecko';
					} else if (UA.webkit) {
						UA.renderer = 'webkit';
					} else if (UA.opera) {
						UA.renderer = 'presto';
					}

					A.UA = UA;

					/*
					 * Browser selectors
					 */

					var selectors = [UA.renderer, 'js'].concat(browserList);

					var osSelector = MAP_OS_SELECTORS[UA.os] || UA.os;

					selectors.push(osSelector);

					if (UA.mobile) {
						selectors.push('mobile');
					}

					if (UA.secure) {
						selectors.push('secure');
					}

					if (UA.touch) {
						selectors.push('touch');
					}

					UA.selectors = selectors.join(' ');

					var svg;
					var vml;

					vml = !(svg = !!(
						CONFIG.win.SVGAngle ||
						DOC.implementation.hasFeature(
							'http://www.w3.org/TR/SVG11/feature#BasicStructure',
							'1.1'
						)
					));

					if (vml) {
						var behaviorObj;
						var div = DOC.createElement('div');

						div.innerHTML = '<v:shape adj="1"/>';

						behaviorObj = div.firstChild;

						behaviorObj.style.behavior = 'url(#default#VML)';

						if (
							!(behaviorObj && typeof behaviorObj.adj == 'object')
						) {
							vml = false;
						}

						div = null;
					}

					YUI._VML = vml;
					YUI._SVG = svg;

					UA.vml = YUI._VML;
					UA.svg = YUI._SVG;
				}

				return UA.selectors;
			},

			run() {
				var documentElement = DOC.documentElement;

				var selectors = this.getSelectors();

				UA.dir = documentElement.getAttribute('dir') || 'ltr';

				if (documentElement.className.indexOf(UA.dir) === -1) {
					selectors += ' ' + UA.dir;
				}

				if (
					documentElement.className.indexOf(YUI3_JS_ENABLED) === -1 &&
					selectors.indexOf(YUI3_JS_ENABLED) === -1
				) {
					selectors += ' ' + YUI3_JS_ENABLED;
				}

				documentElement.className += ' ' + selectors;
			}
		};

		Liferay.BrowserSelectors = BrowserSelectors;
	},
	'',
	{
		requires: ['yui-base']
	}
);
