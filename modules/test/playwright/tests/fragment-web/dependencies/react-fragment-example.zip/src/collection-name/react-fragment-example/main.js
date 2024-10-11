/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint-disable */
Liferay.Loader.define(
	'__FRAGMENT_MODULE_NAME__',
	['module', 'require', '__REACT_PROVIDER__$react'],
	(__MODULE__, __REQUIRE__) => {
		(function (modules) {
			const installedModules = {};
			function __webpack_require__(moduleId) {
				if (installedModules[moduleId]) {
					return installedModules[moduleId].exports;
				}
				const module = (installedModules[moduleId] = {
					exports: {},
					i: moduleId,
					l: false,
				});
				modules[moduleId].call(
					module.exports,
					module,
					module.exports,
					__webpack_require__
				);
				module.l = true;

				return module.exports;
			}
			__webpack_require__.m = modules;
			__webpack_require__.c = installedModules;
			__webpack_require__.d = function (exports, name, getter) {
				if (!__webpack_require__.o(exports, name)) {
					Object.defineProperty(exports, name, {
						enumerable: true,
						get: getter,
					});
				}
			};
			__webpack_require__.r = function (exports) {
				if (typeof Symbol !== 'undefined' && Symbol.toStringTag) {
					Object.defineProperty(exports, Symbol.toStringTag, {
						value: 'Module',
					});
				}
				Object.defineProperty(exports, '__esModule', {value: true});
			};
			__webpack_require__.t = function (value, mode) {
				if (mode & 1) {
					value = __webpack_require__(value);
				}
				if (mode & 8) {
					return value;
				}
				if (
					mode & 4 &&
					typeof value === 'object' &&
					value &&
					value.__esModule
				) {
					return value;
				}
				const ns = Object.create(null);
				__webpack_require__.r(ns);
				Object.defineProperty(ns, 'default', {
					enumerable: true,
					value,
				});
				if (mode & 2 && typeof value !== 'string') {
					for (const key in value) {
						__webpack_require__.d(
							ns,
							key,
							((key) => {
								return value[key];
							}).bind(null, key)
						);
					}
				}

				return ns;
			};
			__webpack_require__.n = function (module) {
				const getter =
					module && module.__esModule
						? function getDefault() {
								return module['default'];
							}
						: function getModuleExports() {
								return module;
							};
				__webpack_require__.d(getter, 'a', getter);

				return getter;
			};
			__webpack_require__.o = function (object, property) {
				return Object.prototype.hasOwnProperty.call(object, property);
			};
			__webpack_require__.p = '';

			return __webpack_require__(
				(__webpack_require__.s =
					'./build/liferay-npm-bundler-workdir/generated/react-fragment-example.js')
			);
		})({
			'./build/liferay-npm-bundler-workdir/generated/react-fragment-example.js'(
				module,
				exports,
				__webpack_require__
			) {
				__MODULE__.exports = __webpack_require__(
					'./src/collection-name/react-fragment-example/main.js'
				);
			},
			'./src/collection-name/react-fragment-example/main.js'(
				module,
				__webpack_exports__,
				__webpack_require__
			) {
				'use strict';
				__webpack_require__.r(__webpack_exports__);
				const React_raw = __REQUIRE__('__REACT_PROVIDER__$react');
				const React =
					React_raw && React_raw.__esModule
						? React_raw['default']
						: React_raw;
				__webpack_exports__['default'] = function () {
					return React.createElement('h1', null, 'Hello World');
				};
			},
		});
	}
);

// # sourceMappingURL=main.js.map
