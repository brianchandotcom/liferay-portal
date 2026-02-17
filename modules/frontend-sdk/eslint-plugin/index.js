/**
 * SPDX-FileCopyrightText: © 2021 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const generalConfig = require('./configs/general');
const metalConfig = require('./configs/metal');
const portalConfig = require('./configs/portal');
const reactConfig = require('./configs/react');
const auiRules = require('./rules/aui');
const generalRules = require('./rules/general');
const portalRules = require('./rules/portal');

module.exports = {
	configs: {
		general: generalConfig,
		metal: metalConfig,
		portal: portalConfig,
		react: reactConfig,
	},
	rules: {
		...auiRules,
		...generalRules,
		...portalRules,
	},
};
