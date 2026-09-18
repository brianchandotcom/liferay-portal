/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {

	// Every AlloyUI module the LPD-515xx campaign removes is resurrected behind
	// a deprecation feature flag, registered from its own AlloyUI group in
	// frontend-js-aui-web/src/main/resources/META-INF/resources/liferay
	// /modules_deprecated.js. Add one entry here per removal. The checker
	// asserts both directions: with the flag on the module must load, with the
	// flag off it must be gone.

	const CHECKS = [
		{
			featureFlagKey: 'LPD-57347',
			globals: ['Liferay.AutoFields'],
			moduleKey: 'liferay-auto-fields',
			removedBy: 'LPD-51537',
			title: 'AutoFields',
		},
	];

	const EXPECTED_GROUP = 'liferaydeprecated';

	const USE_TIMEOUT = 10000;

	function getModuleInfo(moduleKey) {
		if (typeof window.AUI !== 'function') {
			return null;
		}

		const env = AUI().Env;

		const loader = (env && env._loader) || null;

		if (!loader || !loader.moduleInfo) {
			return null;
		}

		return loader.moduleInfo[moduleKey] || null;
	}

	function isFeatureFlagEnabled(featureFlagKey) {
		return Boolean(
			window.Liferay &&
				Liferay.FeatureFlags &&
				Liferay.FeatureFlags[featureFlagKey]
		);
	}

	function isOverlayLoaded() {
		return Array.prototype.some.call(
			document.querySelectorAll('script[src]'),
			(script) => script.src.indexOf('modules_deprecated.js') !== -1
		);
	}

	function resolveGlobal(path) {
		return path
			.split('.')
			.reduce(
				(value, name) =>
					value === undefined || value === null ? value : value[name],
				window
			);
	}

	// Resolves rather than rejects, so one broken module cannot stop the run.
	// A module that is not registered never calls back at all, hence the
	// timeout.
	//
	// Do not trust the loader response: YUI keeps one cumulative Env._missed
	// list for the whole page and _notify sets response.success to false
	// whenever that list is not empty, even when the modules in it were
	// requested by unrelated code. Ask Env._attached about this module instead.

	function loadModule(moduleKey) {
		return new Promise((resolve) => {
			if (typeof window.AUI !== 'function') {
				resolve({loaded: false, reason: 'AUI is not on this page'});

				return;
			}

			let settled = false;

			const timeoutId = setTimeout(() => {
				if (!settled) {
					settled = true;

					resolve({
						loaded: false,
						reason: 'the loader never called back',
					});
				}
			}, USE_TIMEOUT);

			try {
				AUI().use(moduleKey, (A) => {
					if (settled) {
						return;
					}

					settled = true;

					clearTimeout(timeoutId);

					const env = A.Env || {};

					if (env._attached && env._attached[moduleKey]) {
						resolve({loaded: true});

						return;
					}

					const missed = env._missed || [];

					resolve({
						loaded: false,
						reason:
							missed.indexOf(moduleKey) === -1
								? 'the loader did not attach it'
								: 'the loader reported it missing',
					});
				});
			}
			catch (error) {
				settled = true;

				clearTimeout(timeoutId);

				resolve({loaded: false, reason: String(error)});
			}
		});
	}

	function assert(label, expected, actual) {
		return {
			actual,
			expected,
			label,
			passed: expected === actual,
		};
	}

	async function runCheck(check) {
		const enabled = isFeatureFlagEnabled(check.featureFlagKey);

		const moduleInfo = getModuleInfo(check.moduleKey);

		const assertions = [
			assert(
				'Registered with the AlloyUI loader',
				enabled,
				Boolean(moduleInfo)
			),
		];

		if (enabled) {
			assertions.push(
				assert(
					'Registered in the deprecated group',
					EXPECTED_GROUP,
					moduleInfo ? moduleInfo.group : '(not registered)'
				)
			);
		}

		const result = await loadModule(check.moduleKey);

		assertions.push(
			assert(
				enabled
					? 'Loads through AUI().use()'
					: 'Refuses to load through AUI().use()',
				enabled,
				result.loaded
			)
		);

		if (!result.loaded && result.reason) {
			assertions[assertions.length - 1].note = result.reason;
		}

		check.globals.forEach((path) => {
			assertions.push(
				assert(
					enabled
						? 'Global ' + path + ' is installed'
						: 'Global ' + path + ' is absent',
					enabled,
					resolveGlobal(path) !== undefined
				)
			);
		});

		return {
			assertions,
			check,
			enabled,
			passed: assertions.every((assertion) => assertion.passed),
		};
	}

	// Escape every interpolated value. An assertion can carry a loader error
	// message or a group name read from live AlloyUI metadata, and neither is
	// controlled by this file. Liferay.Util.escapeHTML calls String.replace,
	// so coerce first: expected and actual are often booleans.

	function escape(value) {
		return Liferay.Util.escapeHTML(String(value));
	}

	function renderAssertion(assertion) {
		return (
			'<li class="list-group-item list-group-item-flex" data-qa-id="' +
			'auiDeprecationCheckerAssertion" data-status="' +
			(assertion.passed ? 'pass' : 'fail') +
			'"><div class="autofit-col"><span class="label ' +
			(assertion.passed ? 'label-success' : 'label-danger') +
			'">' +
			(assertion.passed ? 'Pass' : 'Fail') +
			'</span></div><div class="autofit-col autofit-col-expand">' +
			escape(assertion.label) +
			(assertion.passed
				? ''
				: ' &mdash; expected <code>' +
					escape(assertion.expected) +
					'</code>, got <code>' +
					escape(assertion.actual) +
					'</code>') +
			(assertion.note
				? ' <em>(' + escape(assertion.note) + ')</em>'
				: '') +
			'</div></li>'
		);
	}

	function renderResult(result) {
		return (
			'<div class="card" data-qa-id="auiDeprecationCheckerModule" ' +
			'data-feature-flag-enabled="' +
			result.enabled +
			'" data-module-key="' +
			escape(result.check.moduleKey) +
			'" data-status="' +
			(result.passed ? 'pass' : 'fail') +
			'"><div class="card-body"><h4 class="card-title">' +
			escape(result.check.title) +
			' <span class="label ' +
			(result.enabled ? 'label-info' : 'label-secondary') +
			'">' +
			escape(result.check.featureFlagKey) +
			(result.enabled ? ' enabled' : ' disabled') +
			'</span></h4><p class="card-subtitle text-secondary"><code>' +
			escape(result.check.moduleKey) +
			'</code>, removed by ' +
			escape(result.check.removedBy) +
			'</p></div><ul class="list-group">' +
			result.assertions.map(renderAssertion).join('') +
			'</ul></div>'
		);
	}

	function render(container, results) {
		const failed = results.filter((result) => !result.passed);

		container.setAttribute('data-status', failed.length ? 'fail' : 'pass');

		container.innerHTML =
			'<div class="alert ' +
			(failed.length ? 'alert-danger' : 'alert-success') +
			'" data-qa-id="auiDeprecationCheckerSummary" data-status="' +
			(failed.length ? 'fail' : 'pass') +
			'" role="alert">' +
			(failed.length
				? failed.length +
					' of ' +
					results.length +
					' modules behaved unexpectedly'
				: 'All ' + results.length + ' modules behaved as expected') +
			'</div><p class="text-secondary">AlloyUI on this page: <code>' +
			(typeof window.AUI === 'function' ? 'yes' : 'no') +
			'</code>, <code>modules_deprecated.js</code> loaded: <code data-qa-id="' +
			'auiDeprecationCheckerOverlay">' +
			(isOverlayLoaded() ? 'yes' : 'no') +
			'</code></p>' +
			results.map(renderResult).join('');
	}

	async function run(container) {
		container.removeAttribute('data-status');

		container.innerHTML = '<p>Checking&hellip;</p>';

		const results = [];

		for (const check of CHECKS) {
			results.push(await runCheck(check));
		}

		render(container, results);
	}

	const containers = document.querySelectorAll(
		'[data-aui-deprecation-checker]'
	);

	Array.prototype.forEach.call(containers, (container) => {
		if (container.dataset.auiDeprecationCheckerStarted) {
			return;
		}

		container.dataset.auiDeprecationCheckerStarted = 'true';

		run(container);
	});
})();
