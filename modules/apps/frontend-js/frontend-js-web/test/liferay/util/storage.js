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

import {CONSENT_TYPES} from '../../../src/main/resources/META-INF/resources/liferay/util/consent';
import Storage from '../../../src/main/resources/META-INF/resources/liferay/util/storage';

const anyName = 'any-name';
const anyValue = 'any-value';

const optionalConsentTypes = [
	CONSENT_TYPES.PERFORMANCE,
	CONSENT_TYPES.PERSONALIZATION,
	CONSENT_TYPES.FUNCTIONAL,
];
const allConsentTypes = [CONSENT_TYPES.NECESSARY, ...optionalConsentTypes];

const setConsentCookies = (optional = false) => {
	const consentCookieMap = {
		[CONSENT_TYPES.NECESSARY]: true,
		[CONSENT_TYPES.PERFORMANCE]: optional,
		[CONSENT_TYPES.PERSONALIZATION]: optional,
		[CONSENT_TYPES.FUNCTIONAL]: optional,
	};

	for (const [name, value] of Object.entries(consentCookieMap)) {
		document.cookie = `${name}=${value}`;
	}
};

const clearConsentCookies = () => {
	for (const consentType of allConsentTypes) {
		document.cookie = `${consentType}=; max-age=0;`;
	}
};

describe.each([
	['LocalStorage', localStorage],
	['SessionStorage', sessionStorage],
])(`Liferay.Util.%s`, (storageName, storage) => {
	const LiferayStorage = new Storage(storage);

	afterEach(() => {
		storage.clear();
		clearConsentCookies();
		jest.restoreAllMocks();
	});

	describe(`Liferay.Util.${storageName}.setItem`, () => {
		it('Always allows setting a necessary data', () => {
			setConsentCookies(false);

			const isValueSet = LiferayStorage.setItem(
				anyName,
				anyValue,
				CONSENT_TYPES.NECESSARY
			);

			expect(isValueSet).toBe(true);

			const storedValue = storage.getItem(anyName);

			expect(storedValue).toBe(anyValue);
		});

		it.each(optionalConsentTypes)(
			`Allows setting %s values if enabled`,
			(consentType) => {
				setConsentCookies(true);

				const name = `${anyName}-${consentType}`;

				const isValueSet = LiferayStorage.setItem(
					name,
					anyValue,
					consentType
				);

				expect(isValueSet).toBe(true);

				const storedValue = storage.getItem(name);

				expect(storedValue).toBe(anyValue);
			}
		);

		it.each(optionalConsentTypes)(
			`Doesn't allow setting %s value if disabled`,
			(consentType) => {
				setConsentCookies(false);

				const name = `${anyName}-${consentType}`;

				const isValueSet = LiferayStorage.setItem(
					name,
					anyValue,
					consentType
				);

				expect(isValueSet).toBe(false);

				const storedValue = storage.getItem(name);

				expect(storedValue).toBeNull();
			}
		);

		it.each(optionalConsentTypes)(
			'Allows setting %s values if consent is not set',
			(consentType) => {
				expect(document.cookie.includes(consentType)).toBe(false);

				const isValueSet = LiferayStorage.setItem(
					anyName,
					anyValue,
					consentType
				);

				expect(isValueSet).toBe(true);
				expect(storage.getItem(anyName)).toBe(anyValue);
			}
		);
	});

	describe(`Liferay.Util.${storageName}.getItem`, () => {
		it.each(allConsentTypes)(
			"Returns null if value isn't set and %s enabled",
			(consentType) => {
				setConsentCookies(true);

				expect(LiferayStorage.getItem(anyName, consentType)).toBeNull();
			}
		);

		it.each(allConsentTypes)(
			'Returns value as string if the value is set and %s enabled',
			(consentType) => {
				setConsentCookies(true);

				storage.setItem(anyName, anyValue);

				expect(LiferayStorage.getItem(anyName, consentType)).toBe(
					anyValue
				);
			}
		);

		it.each(optionalConsentTypes)(
			"Returns null if value isn't set and %s disabled",
			(consentType) => {
				setConsentCookies(false);

				expect(LiferayStorage.getItem(anyName, consentType)).toBeNull();
			}
		);

		it.each(optionalConsentTypes)(
			'Returns null if the value is set and %s disabled',
			(consentType) => {
				setConsentCookies(false);

				storage.setItem(anyName, anyValue);

				expect(storage.getItem(anyName)).not.toBeNull();

				expect(LiferayStorage.getItem(anyName, consentType)).toBeNull();
			}
		);
	});

	describe(`Liferay.Util.${storageName}.removeItem`, () => {
		it('Removes value if it exists', () => {
			storage.setItem(anyName, anyValue);

			LiferayStorage.removeItem(anyName);
			expect(storage.getItem(anyName)).toBeNull();
		});

		it("Value still doesn't exist if it didn't exist before removal", () => {
			LiferayStorage.removeItem(anyName);
			expect(storage.getItem(anyName)).toBeNull();
		});
	});

	describe(`Liferay.Util.${storageName}.TYPES`, () => {
		it('Exists', () => {
			expect(LiferayStorage.TYPES).not.toBeUndefined();
		});
	});
});
