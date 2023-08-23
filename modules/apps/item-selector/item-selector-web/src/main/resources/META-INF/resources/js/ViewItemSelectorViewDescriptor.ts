/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {delegate, getOpener} from 'frontend-js-web';

export interface Props {
	itemSelectorReturnType: string;
	itemSelectorSelectedEvent: string;
	namespace: string;
}

type DelegatedEvent<T extends Event> = T & {
	delegateTarget: HTMLElement;
};

export default function ({
	itemSelectorReturnType,
	itemSelectorSelectedEvent,
	namespace,
}: Props) {
	const updateSelectedCard = (
		event: DelegatedEvent<KeyboardEvent | MouseEvent>
	) => {
		document.querySelectorAll('.form-check-card.active').forEach((card) => {
			card.classList.remove('active');
		});

		const newSelectedCard = event.delegateTarget.closest(
			'.form-check-card'
		);

		if (newSelectedCard) {
			newSelectedCard.classList.add('active');
		}
	};

	const dispatchSelectEvent = (
		event: DelegatedEvent<KeyboardEvent | MouseEvent>
	) => {
		const element = event.delegateTarget.closest('li, tr, dd');
		const value = element instanceof HTMLElement && element.dataset.value;

		getOpener().Liferay.fire(itemSelectorSelectedEvent, {
			data: {
				returnType: itemSelectorReturnType,
				value: value || '',
			},
		});
	};

	const onClickHandler = delegate(
		document.getElementById(`${namespace}entriesContainer`)!,
		'click',
		'.entry',
		(event: DelegatedEvent<MouseEvent>) => {
			updateSelectedCard(event);
			dispatchSelectEvent(event);
		}
	);

	const onKeydownHandler = delegate(
		document.getElementById(`${namespace}entriesContainer`)!,
		'keydown',
		'.entry',
		(event: DelegatedEvent<KeyboardEvent>) => {
			if (event.code === 'Enter') {
				updateSelectedCard(event);
				dispatchSelectEvent(event);
			}
		}
	);

	return {
		dispose() {
			onClickHandler.dispose();
			onKeydownHandler.dispose();
		},
	};
}
