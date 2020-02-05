/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

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

const fs = require('fs');
const path = require('path');

const BLACKLIST = /-ace-editor/;
const VARIANTS = /-(coverage|debug|min)\.js$/;
const WHITELIST = /\.js$/;

function filter(name) {
	return (
		!VARIANTS.test(name) && !BLACKLIST.test(name) && WHITELIST.test(name)
	);
}

function walk(dir, callback) {
	fs.readdirSync(dir, {withFileTypes: true}).forEach(entry => {
		const entryPath = path.join(dir, entry.name);

		if (entry.isDirectory()) {
			walk(entryPath, callback);
		}
		else if (filter(entryPath)) {
			callback(entryPath);
		}
	});
}

beforeEach(() => {
	jest.resetModules();

	const {YUI} = require('alloy-ui/build/aui/aui');

	const _YUI = YUI();

	global.AUI = function() {
		return _YUI;
	};

	_YUI.mix(AUI, YUI);

	global.YUI = AUI;

	const build = path.join(
		path.dirname(require.resolve('alloy-ui/build/aui/aui')),
		'..'
	);

	// eslint-disable-next-line liferay/no-dynamic-require
	walk(build, source => require(source));

	global.Liferay = {
		Language: {
			get(key) {
				return key;
			}
		},

		namespace(name) {
			Liferay[name] = {};

			return Liferay[name];
		}
	};
});
