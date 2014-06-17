YUI.add(
	'liferay-browser-selectors',
	function(A) {
		var REGEX_VERSION_DOT = /\./g;

		var parseVersionNumber = function(str) {
			var count = 0;

			return parseFloat(
				str.replace(
					REGEX_VERSION_DOT,
					function() {
						return (count++ == 1) ? '' : '.';
					}
				)
			);
		};

		var DEFAULTS_VERSION = ['0','0'];

		var getVersion = function(regex, userAgent) {
			var version = (userAgent.match(regex) || DEFAULTS_VERSION)[1];

			return parseVersionNumber(version);
		};

		var MAP_OS_SELECTORS = {
			windows: 'win',
			macintosh: 'mac'
		};

		var BROWSERS = [
			'ie',
			'opera',
			'chrome',
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

		var nav = navigator;

		var CONFIG = A.config,
			DOC = CONFIG.doc;

		var userAgent = nav.userAgent;

		var UA = A.UA;
		var OS = UA.os;

		var UAX = {
			aol: 0,

			camino: 0,
			firefox: 0,
			flock: 0,
			mozilla: 0,
			netscape: 0,

			icab: 0,
			konqueror: 0,

			safari: 0,

			browser: 0,

			win: OS == 'windows',
			mac: OS == 'macintosh',
			rhino: OS == 'rhino',

			agent: userAgent
		};

		if (UA.ie) {
			UAX.aol = getVersion(/America Online Browser ([^\s]*);/, userAgent);

			var docMode = DOC.documentMode;

			if (docMode) {
				UA.browser = UA.ie;
				UA.ie = docMode;
			}
		}
		else if (UA.gecko) {
			UAX.netscape = getVersion(/(Netscape|Navigator)\/([^\s]*)/, userAgent);
			UAX.flock = getVersion(/Flock\/([^\s]*)/, userAgent);
			UAX.camino = getVersion(/Camino\/([^\s]*)/, userAgent);
			UAX.firefox = getVersion(/Firefox\/([^\s]*)/, userAgent);
		}
		else if (UA.webkit) {
			UAX.safari = getVersion(/Version\/([^\s]*) Safari/, userAgent);
		}
		else {
			UAX.icab = getVersion(/iCab(?:\/|\s)?([^\s]*)/, userAgent);
			UAX.konqueror = getVersion(/Konqueror\/([^\s]*)/, userAgent);
		}

		if (!UAX.win && !UAX.mac) {
			var linux = /Linux/.test(userAgent);
			var sun = /Solaris|SunOS/.test(userAgent);

			if (linux) {
				UA.os = 'linux';
				UAX.linux = linux;
			}
			else if (sun) {
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
		var version;
		var uaVersionMajor;
		var uaVersionMinor;

		var versionObj = {
			string: '',
			major: versionMajor
		};

		var i = BROWSERS.length;

		while (i--) {
			browser = BROWSERS[i];
			version = UA[browser];

			if (version > 0) {
				versionMajor = parseInt(version, 10);
				uaVersionMajor = browser + versionMajor;

				uaVersionMinor = (browser + version);

				if (String(version).indexOf('.') > -1) {
					uaVersionMinor = uaVersionMinor.replace(/\.(\d).*/, '-$1');
				}
				else {
					uaVersionMinor += '-0';
				}

				browserList.push(browser, uaVersionMajor, uaVersionMinor);

				versionObj.string = browser + '';
				versionObj.major = versionMajor;
			}
		}

		UA.version = versionObj;

		UA.renderer = '';

		var documentElement = DOC.documentElement;

		UA.dir = documentElement.getAttribute('dir') || 'ltr';

		if (UA.ie) {
			UA.renderer = 'trident';
		}
		else if (UA.gecko) {
			UA.renderer = 'gecko';
		}
		else if (UA.webkit) {
			UA.renderer = 'webkit';
		}
		else if (UA.opera) {
			UA.renderer = 'presto';
		}

		A.UA = UA;

		/*
		* Browser selectors
		*/

		var selectors = [
			UA.renderer,
			UA.dir,
			'js'
		].concat(browserList);

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

		// The methods in this if block only run once across all instances
		if (!documentElement._yuid) {
			documentElement.className += ' ' + UA.selectors;

			var vml,
				svg;

			vml = !(svg = !!(CONFIG.win.SVGAngle || DOC.implementation.hasFeature('http://www.w3.org/TR/SVG11/feature#BasicStructure', '1.1')));

			if (vml) {
				var div = DOC.createElement('div');
				var behaviorObj;

				div.innerHTML = '<v:shape adj="1"/>';

				behaviorObj = div.firstChild;

				behaviorObj.style.behavior = 'url(#default#VML)';

				if (!(behaviorObj && typeof behaviorObj.adj == 'object')) {
					vml = false;
				}

				div = null;
			}

			YUI._VML = vml;
			YUI._SVG = svg;

			A.stamp(documentElement);
		}

		UA.vml = YUI._VML;
		UA.svg = YUI._SVG;
	},
	'',
	{
		requires: ['yui-base']
	}
);