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

import {FRAGMENTS_EDITOR_ITEM_TYPES} from '../../src/main/resources/META-INF/resources/js/utils/constants';
import FragmentsEditor from '../../src/main/resources/META-INF/resources/js/FragmentsEditor.es';

describe('getBackgroundEditableTarget ', () => {
	it('marks containing fragment as active when clicking in the background image editable when no element is active', () => {
		const div = document.createElement('div');

		const fragmentId = '1';
		const backgroundImageEditableId = '2';

		div.innerHTML = `
			<div data-fragments-editor-item-type="${FRAGMENTS_EDITOR_ITEM_TYPES.fragment}" data-fragments-editor-item-id="${fragmentId}">
				<div data-fragments-editor-item-type="${FRAGMENTS_EDITOR_ITEM_TYPES.backgroundImageEditable}" data-fragments-editor-item-id="${backgroundImageEditableId}">
				</div>
			</div>
		`;

		const backgroundImageEditable = div.querySelector(
			`[data-fragments-editor-item-type="${FRAGMENTS_EDITOR_ITEM_TYPES.backgroundImageEditable}"][data-fragments-editor-item-id="${backgroundImageEditableId}"]`
		);

		const {
			activeItemId,
			activeItemType
		} = FragmentsEditor.getBackgroundEditableTarget(
			backgroundImageEditable,
			backgroundImageEditableId,
			FRAGMENTS_EDITOR_ITEM_TYPES.backgroundImageEditable,
			null,
			null
		);

		expect(activeItemId).toBe(fragmentId);
		expect(activeItemType).toBe(FRAGMENTS_EDITOR_ITEM_TYPES.fragment);
	});

	it('marks containing fragment as active when clicking in the background image editable when another fragment is active', () => {
		const div = document.createElement('div');

		const anotherFragmentId = '0';
		const fragmentId = '1';
		const backgroundImageEditableId = '2';

		div.innerHTML = `
			<div data-fragments-editor-item-type="${FRAGMENTS_EDITOR_ITEM_TYPES.fragment}" data-fragments-editor-item-id="${anotherFragmentId}">
			</div>
			<div data-fragments-editor-item-type="${FRAGMENTS_EDITOR_ITEM_TYPES.fragment}" data-fragments-editor-item-id="${fragmentId}">
				<div data-fragments-editor-item-type="${FRAGMENTS_EDITOR_ITEM_TYPES.backgroundImageEditable}" data-fragments-editor-item-id="${backgroundImageEditableId}">
				</div>
			</div>
		`;

		const backgroundImageEditable = div.querySelector(
			`[data-fragments-editor-item-type="${FRAGMENTS_EDITOR_ITEM_TYPES.backgroundImageEditable}"][data-fragments-editor-item-id="${backgroundImageEditableId}"]`
		);

		const {
			activeItemId,
			activeItemType
		} = FragmentsEditor.getBackgroundEditableTarget(
			backgroundImageEditable,
			backgroundImageEditableId,
			FRAGMENTS_EDITOR_ITEM_TYPES.backgroundImageEditable,
			anotherFragmentId,
			FRAGMENTS_EDITOR_ITEM_TYPES.fragment
		);

		expect(activeItemId).toBe(fragmentId);
		expect(activeItemType).toBe(FRAGMENTS_EDITOR_ITEM_TYPES.fragment);
	});

	it('marks background image editable active if the current active element is the editable parent', () => {
		const div = document.createElement('div');

		const fragmentId = '1';
		const backgroundImageEditableId = '2';

		div.innerHTML = `
			<div data-fragments-editor-item-type="${FRAGMENTS_EDITOR_ITEM_TYPES.fragment}" data-fragments-editor-item-id="${fragmentId}">
				<div data-fragments-editor-item-type="${FRAGMENTS_EDITOR_ITEM_TYPES.backgroundImageEditable}" data-fragments-editor-item-id="${backgroundImageEditableId}">
				</div>
			</div>
		`;

		const backgroundImageEditable = div.querySelector(
			`[data-fragments-editor-item-type="${FRAGMENTS_EDITOR_ITEM_TYPES.backgroundImageEditable}"][data-fragments-editor-item-id="${backgroundImageEditableId}"]`
		);

		const {
			activeItemId,
			activeItemType
		} = FragmentsEditor.getBackgroundEditableTarget(
			backgroundImageEditable,
			backgroundImageEditableId,
			FRAGMENTS_EDITOR_ITEM_TYPES.backgroundImageEditable,
			fragmentId,
			FRAGMENTS_EDITOR_ITEM_TYPES.fragment
		);

		expect(activeItemId).toBe(backgroundImageEditableId);
		expect(activeItemType).toBe(
			FRAGMENTS_EDITOR_ITEM_TYPES.backgroundImageEditable
		);
	});

	it('marks background image editable active if the current active element is the editable parent', () => {
		const div = document.createElement('div');

		const fragmentId = '1';
		const backgroundImageEditableId = '2';

		div.innerHTML = `
			<div data-fragments-editor-item-type="${FRAGMENTS_EDITOR_ITEM_TYPES.fragment}" data-fragments-editor-item-id="${fragmentId}">
				<div data-fragments-editor-item-type="${FRAGMENTS_EDITOR_ITEM_TYPES.backgroundImageEditable}" data-fragments-editor-item-id="${backgroundImageEditableId}">
				</div>
			</div>
		`;

		const backgroundImageEditable = div.querySelector(
			`[data-fragments-editor-item-type="${FRAGMENTS_EDITOR_ITEM_TYPES.backgroundImageEditable}"][data-fragments-editor-item-id="${backgroundImageEditableId}"]`
		);

		const {
			activeItemId,
			activeItemType
		} = FragmentsEditor.getBackgroundEditableTarget(
			backgroundImageEditable,
			backgroundImageEditableId,
			FRAGMENTS_EDITOR_ITEM_TYPES.backgroundImageEditable,
			fragmentId,
			FRAGMENTS_EDITOR_ITEM_TYPES.fragment
		);

		expect(activeItemId).toBe(backgroundImageEditableId);
		expect(activeItemType).toBe(
			FRAGMENTS_EDITOR_ITEM_TYPES.backgroundImageEditable
		);
	});

	it('marks background image editable active if the current active element is a background image editable within the same fragment', () => {
		const div = document.createElement('div');

		const fragmentId = '1';
		const backgroundImageEditableId = '2';
		const anotherBackgroundImageEditableId = '3';

		div.innerHTML = `
			<div data-fragments-editor-item-type="${FRAGMENTS_EDITOR_ITEM_TYPES.fragment}" data-fragments-editor-item-id="${fragmentId}">
				<div data-fragments-editor-item-type="${FRAGMENTS_EDITOR_ITEM_TYPES.backgroundImageEditable}" data-fragments-editor-item-id="${backgroundImageEditableId}">
				</div>
				<div data-fragments-editor-item-type="${FRAGMENTS_EDITOR_ITEM_TYPES.backgroundImageEditable}" data-fragments-editor-item-id="${anotherBackgroundImageEditableId}">
				</div>
			</div>
		`;

		document.body.innerHTML = div.innerHTML;

		const backgroundImageEditable = div.querySelector(
			`[data-fragments-editor-item-type="${FRAGMENTS_EDITOR_ITEM_TYPES.backgroundImageEditable}"][data-fragments-editor-item-id="${backgroundImageEditableId}"]`
		);

		const {
			activeItemId,
			activeItemType
		} = FragmentsEditor.getBackgroundEditableTarget(
			backgroundImageEditable,
			backgroundImageEditableId,
			FRAGMENTS_EDITOR_ITEM_TYPES.backgroundImageEditable,
			anotherBackgroundImageEditableId,
			FRAGMENTS_EDITOR_ITEM_TYPES.backgroundImageEditable
		);

		expect(activeItemId).toBe(backgroundImageEditableId);
		expect(activeItemType).toBe(
			FRAGMENTS_EDITOR_ITEM_TYPES.backgroundImageEditable
		);
	});
});
