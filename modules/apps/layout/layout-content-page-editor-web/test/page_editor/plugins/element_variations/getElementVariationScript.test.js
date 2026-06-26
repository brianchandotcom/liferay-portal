/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getElementVariationScript from '../../../../src/main/resources/META-INF/resources/page_editor/plugins/element_variations/getElementVariationScript';

const baseElementVariation = {
	audienceEntryERC: '',
	externalReferenceCode: '',
	hide: false,
	html: '',
	js: '',
	key: 'key',
	name: 'name',
	segmentsExperienceERC: '',
	targetElement: '#banner',
};

describe('getElementVariationScript', () => {
	it('targets the element with a querySelector', () => {
		const script = getElementVariationScript(baseElementVariation);

		expect(script).toContain(
			'const element = document.querySelector("#banner");'
		);
	});

	it('inlines the custom javascript', () => {
		const script = getElementVariationScript({
			...baseElementVariation,
			js: 'element.classList.add("featured");',
		});

		expect(script).toContain('element.classList.add("featured");');
	});
});
