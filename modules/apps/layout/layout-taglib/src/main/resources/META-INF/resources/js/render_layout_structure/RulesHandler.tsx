/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {debounce, delegate, fetch} from 'frontend-js-web';
import moment from 'moment';

const getRulesResult = debounce(
	(
		computeRulesURL: string,
		fieldValues: Record<string, any>,
		ruleIds: string[],
		onSuccess: (
			rulesResult: {
				action: 'show' | 'hide' | 'enable' | 'disable';
				itemId: string;
			}[]
		) => void
	) => {
		const formData = new FormData();
		formData.append('fieldValues', JSON.stringify(fieldValues));
		formData.append('layoutStructureRuleIds', ruleIds.join(','));

		return fetch(computeRulesURL, {
			body: formData,
			method: 'POST',
		})
			.then((response) => response.json())
			.then(onSuccess);
	},
	300
);

export default function RulesHandler({
	evaluateRulesURL,
	itemIdsByRuleId,
	ruleIdsByItemId,
}: {
	evaluateRulesURL: string;
	itemIdsByRuleId: Record<string, string[]>;
	ruleIdsByItemId: Record<string, string[]>;
}) {
	const handlers: {dispose: () => void}[] = [];

	const forms = document.querySelectorAll('.lfr-layout-structure-item-form');

	for (const form of forms) {
		handlers.push(
			delegate(
				form,
				'change',
				'input, select, textarea',
				async (event) => {
					const input = event.delegateTarget as HTMLInputElement;

					if (input.type === 'hidden') {
						return;
					}

					evaluateRules({
						evaluateRulesURL,
						input,
						itemIdsByRuleId,
						ruleIdsByItemId,
					});
				}
			)
		);

		form.querySelectorAll('[data-field-type="select"]').forEach(
			(item: any) => {
				if (item.dataset.fieldName) {
					const input = item.querySelector(
						`input[name="${item.dataset.fieldName}"]`
					) as HTMLInputElement;

					if (input) {
						const proto = Object.getPrototypeOf(input);
						const descriptor = Object.getOwnPropertyDescriptor(
							proto,
							'value'
						);

						if (!descriptor) {
							return;
						}

						Object.defineProperty(input, 'value', {
							get() {
								return descriptor.get!.call(this);
							},
							set(newValue) {
								const oldValue = descriptor.get!.call(this);
								descriptor.set!.call(this, newValue);

								if (newValue !== oldValue) {
									evaluateRules({
										evaluateRulesURL,
										input,
										itemIdsByRuleId,
										ruleIdsByItemId,
									});
								}
							},
						});
					}
				}
			}
		);
	}

	return {
		disposed: () => {
			for (const handler of handlers) {
				handler.dispose();
			}
		},
	};
}

function evaluateRules({
	evaluateRulesURL,
	input,
	itemIdsByRuleId,
	ruleIdsByItemId,
}: {
	evaluateRulesURL: string;
	input: HTMLInputElement;
	itemIdsByRuleId: Record<string, string[]>;
	ruleIdsByItemId: Record<string, string[]>;
}) {
	const fragmentId = getLayoutStructureItemId(input);

	if (!fragmentId || !ruleIdsByItemId[fragmentId]) {
		return;
	}

	const itemIds = ruleIdsByItemId[fragmentId].flatMap(
		(ruleId) => itemIdsByRuleId[ruleId]
	);

	const fieldValues: Record<string, any> = {};

	for (const itemId of itemIds) {
		fieldValues[itemId] = getInputValue(itemId);
	}

	getRulesResult(
		evaluateRulesURL,
		fieldValues,
		ruleIdsByItemId[fragmentId],
		(
			rulesResult: {
				action: 'show' | 'hide' | 'enable' | 'disable';
				itemId: string;
			}[]
		) => {
			applyRulesResult(rulesResult);
		}
	);
}

function applyRulesResult(
	rulesResult: {
		action: 'show' | 'hide' | 'enable' | 'disable';
		itemId: string;
	}[]
) {
	for (const rule of rulesResult) {
		const item = findLayoutStructureItem(rule.itemId) as HTMLElement;

		if (!item) {
			continue;
		}

		if (rule.action === 'show') {
			item.style.removeProperty('display');
		}
		else if (rule.action === 'hide') {
			item.style.display = 'none';
		}
		else if (rule.action === 'enable') {
			const inputs = item.querySelectorAll(
				'input, button, select, textarea'
			);

			for (const input of inputs) {
				(input as any).disabled = false;
			}
		}
		else if (rule.action === 'disable') {
			const inputs = item.querySelectorAll(
				'input, button, select, textarea'
			);

			for (const input of inputs) {
				(input as any).disabled = true;
			}
		}
	}
}

function getLayoutStructureItemId(input: HTMLInputElement) {
	const fragmentInput = input.closest(
		'[data-layout-structure-item-id]'
	) as HTMLElement;

	if (!fragmentInput) {
		return null;
	}

	return fragmentInput.dataset.layoutStructureItemId;
}

function findLayoutStructureItem(id: string): HTMLElement | null {
	const fragment = document.querySelector(
		`[data-layout-structure-item-id="${id}"]`
	);

	if (!fragment) {
		return null;
	}

	return fragment as HTMLElement;
}

function getInputValue(fragmentId: string) {
	const fragment = findLayoutStructureItem(fragmentId);

	if (!fragment) {
		return null;
	}

	const input = fragment.querySelector('input');

	if (!input) {
		return null;
	}

	if (input.type === 'checkbox') {
		return String(input.checked);
	}

	if (input.type === 'datetime-local') {
		return moment(input.value).format('YYYY-MM-DD HH:mm:ss');
	}

	return input.value;
}
