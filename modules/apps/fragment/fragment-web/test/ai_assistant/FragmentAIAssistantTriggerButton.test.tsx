/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import FragmentAIAssistantTriggerButton from '../../src/main/resources/META-INF/resources/js/ai_assistant/FragmentAIAssistantTriggerButton';

const CONTENT_CHANGED_EVENT = 'cms:aiAssistant:contentChanged';

jest.mock('@liferay/ai-hub-cell-js-components-web', () => ({
	AIAssistantTriggerButton: () => null,
	CONTENT_CHANGED_EVENT: 'cms:aiAssistant:contentChanged',
}));

const listeners = new Map<string, Array<(payload: unknown) => void>>();

function fire(eventName: string, payload: unknown) {
	for (const listener of listeners.get(eventName) || []) {
		listener(payload);
	}
}

function getContentChangedPayload(fragmentSetExternalReferenceCode: string) {
	return {
		data: JSON.stringify({
			data: JSON.stringify({
				fragmentSetExternalReferenceCode,
				name: 'Hero Banner',
			}),
		}),
	};
}

function renderComponent(
	props: {fragmentSetExternalReferenceCode?: string} = {
		fragmentSetExternalReferenceCode: 'fragment-set-1',
	}
) {
	return render(
		<FragmentAIAssistantTriggerButton
			fragmentPortletNamespace="_namespace_"
			siteExternalReferenceCode="L_GUEST"
			{...props}
		/>
	);
}

describe('FragmentAIAssistantTriggerButton', () => {
	beforeEach(() => {
		listeners.clear();

		(Liferay.on as jest.Mock).mockImplementation(
			(eventName: string, listener: (payload: unknown) => void) => {
				listeners.set(eventName, [
					...(listeners.get(eventName) || []),
					listener,
				]);

				return {
					detach: () => {
						listeners.set(
							eventName,
							(listeners.get(eventName) || []).filter(
								(currentListener) =>
									currentListener !== listener
							)
						);
					},
				};
			}
		);

		(Liferay as any).Portlet = {refresh: jest.fn()};
	});

	afterEach(() => {
		(Liferay.on as jest.Mock).mockReset();

		delete (Liferay as any).Portlet;
	});

	it('does not refresh the portlet when the fragment belongs to another fragment set', () => {
		renderComponent();

		fire(CONTENT_CHANGED_EVENT, getContentChangedPayload('fragment-set-2'));

		expect(Liferay.Portlet.refresh).not.toHaveBeenCalled();
	});

	it('refreshes the portlet when the event has no fragment', () => {
		renderComponent();

		fire(CONTENT_CHANGED_EVENT, {data: undefined});

		expect(Liferay.Portlet.refresh).toHaveBeenCalledWith(
			'#p_p_id_namespace_'
		);
	});

	it('refreshes the portlet when the fragment belongs to the current fragment set', () => {
		renderComponent();

		fire(CONTENT_CHANGED_EVENT, getContentChangedPayload('fragment-set-1'));

		expect(Liferay.Portlet.refresh).toHaveBeenCalledWith(
			'#p_p_id_namespace_'
		);
	});

	it('refreshes the portlet when there is no current fragment set', () => {
		renderComponent({});

		fire(CONTENT_CHANGED_EVENT, getContentChangedPayload('fragment-set-2'));

		expect(Liferay.Portlet.refresh).toHaveBeenCalledWith(
			'#p_p_id_namespace_'
		);
	});

	it('stops refreshing the portlet after unmounting', () => {
		const {unmount} = renderComponent();

		unmount();

		fire(CONTENT_CHANGED_EVENT, getContentChangedPayload('fragment-set-1'));

		expect(Liferay.Portlet.refresh).not.toHaveBeenCalled();
	});
});
