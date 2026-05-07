/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const EMAIL_REGEX = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/;

export function getDomain(value: string): string | null {
	const index = value.lastIndexOf('@');

	return index >= 0 ? value.slice(index + 1).toLowerCase() : null;
}

export function isValidEmail(value: string): boolean {
	return EMAIL_REGEX.test(value);
}

function parseBlockedDomains(raw: string | undefined): string[] {
	if (!raw) {
		return [];
	}

	return raw
		.split(',')
		.map((domain) => domain.trim().toLowerCase())
		.filter(Boolean);
}

export function replaceDomain(value: string, domain: string): string {
	const index = value.lastIndexOf('@');

	const localPart = index >= 0 ? value.slice(0, index + 1) : value + '@';

	return `${localPart}${domain}`;
}

export function validateBlockedDomain(
	value: string,
	blockedDomains: string | undefined
) {
	const domain = getDomain(value);
	const blockedDomainsList = parseBlockedDomains(blockedDomains);

	if (!domain || !blockedDomainsList.includes(`@${domain}`)) {
		return;
	}

	return {
		displayErrors: true as const,
		errorMessage: Liferay.Language.get(
			'the-email-domain-is-not-allowed-enter-an-email-address-with-a-different-domain'
		),
		valid: false as const,
	};
}

export function validateEmailFormat(value: string) {
	if (!value || isValidEmail(value)) {
		return;
	}

	return {
		displayErrors: true as const,
		errorMessage: Liferay.Language.get(
			'please-enter-a-valid-email-address'
		),
		valid: false as const,
	};
}
