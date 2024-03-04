/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEventListener} from '@liferay/frontend-js-react-web';
import {useEffect, useRef, useState} from 'react';

export default function FocusManager() {
	const shiftRef = useRef(false);
	const tabRef = useRef(false);

	const [breadcrumbs, setBreadcrumbs] = useState(null);
	const [configSidebar, setConfigSidebar] = useState(null);
	const [layout, setLayout] = useState(null);
	const [sidebar, setSidebar] = useState(null);
	const [toolbar, setToolbar] = useState(null);

	// Store shift key status

	useEventListener(
		'keydown',
		(event) => {
			if (event.code === 'ShiftLeft' || event.code === 'ShiftRight') {
				shiftRef.current = true;
			}
			else if (event.code === 'Tab') {
				tabRef.current = true;
			}
		},
		true,
		window
	);

	useEventListener(
		'keyup',
		(event) => {
			if (event.code === 'ShiftLeft' || event.code === 'ShiftRight') {
				shiftRef.current = false;
			}
			else if (event.code === 'Tab') {
				tabRef.current = false;
			}
		},
		true,
		window
	);

	// Handle elements focusout

	useEventListener(
		'focusout',
		(event) => {
			if (breadcrumbs.contains(event.relatedTarget) || !tabRef.current) {
				return;
			}

			if (shiftRef.current) {
				focusLayout(shiftRef.current);
			}
			else {
				focusConfigSidebar();
			}
		},
		true,
		breadcrumbs
	);

	useEventListener(
		'focusout',
		(event) => {
			if (
				configSidebar.contains(event.relatedTarget) ||
				!tabRef.current
			) {
				return;
			}

			if (shiftRef.current) {
				focusBreadCrumb(shiftRef.current);
			}
		},
		true,
		configSidebar
	);

	useEventListener(
		'focusout',
		(event) => {
			if (layout.contains(event.relatedTarget) || !tabRef.current) {
				return;
			}

			if (shiftRef.current) {
				focusSidebar(shiftRef.current);
			}
			else {
				focusBreadCrumb(shiftRef.current);
			}
		},
		true,
		layout
	);

	useEventListener(
		'focusout',
		(event) => {
			if (sidebar.contains(event.relatedTarget) || !tabRef.current) {
				return;
			}

			if (shiftRef.current) {
				focusToolbar();
			}
			else {
				focusLayout(shiftRef.current);
			}
		},
		true,
		sidebar
	);

	useEventListener(
		'focusout',
		(event) => {
			if (toolbar.contains(event.relatedTarget) || !tabRef.current) {
				return;
			}

			if (!shiftRef.current) {
				focusSidebar(shiftRef.current);
			}
		},
		true,
		toolbar
	);

	useEffect(() => {
		setBreadcrumbs(
			document.querySelector('.page-editor__layout-breadcrumbs')
		);
		setConfigSidebar(
			document.querySelector('.page-editor__item-configuration-sidebar')
		);
		setLayout(document.querySelector('.page-editor'));
		setSidebar(document.querySelector('.page-editor__sidebar'));
		setToolbar(document.querySelector('.page-editor__toolbar'));
	}, []);

	return null;
}

function focusLayout(shift) {
	const topper = document.querySelector('.page-editor__topper[tabindex="0"]');

	// If there's a focusable fragment, focus it

	if (topper) {
		topper.focus();
	}

	// Otherwise, if going back, focus sidebar

	else if (shift) {
		focusSidebar(shift);
	}

	// Otherwise, focus config sidebar

	else {
		focusConfigSidebar();
	}
}

function focusConfigSidebar() {
	const sidebar = document.querySelector(
		'.page-editor__item-configuration-sidebar'
	);

	const firstTab = sidebar.querySelector(
		'.page-editor__page-structure__item-configuration-tab'
	);

	// If there's a tab, focus it

	if (firstTab) {
		firstTab.focus();
	}

	// Otherwise, focus the sidebar itself

	else {
		sidebar.focus();
	}
}

function focusBreadCrumb(shift) {
	const breadcrumbs = document.querySelector(
		'.page-editor__layout-breadcrumbs'
	);

	const breadcrumbLink = breadcrumbs.querySelector('.breadcrumb-link');

	// If there's a breadcrumb link, focus it

	if (breadcrumbLink) {
		breadcrumbLink.focus();
	}

	// Otherwise, if going back, focus layout

	else if (shift) {
		focusLayout(shift);
	}

	// Otherwise, focus config sidebar

	else {
		focusConfigSidebar();
	}
}

function focusSidebar(shift) {
	const sidebar = document.querySelector('.page-editor__sidebar');

	// Focus resizer if going back

	if (shift) {
		sidebar.querySelector('.page-editor__sidebar__resizer').focus();
	}

	// Otherwise, focus first button

	else {
		sidebar.querySelector('button').focus();
	}
}

function focusToolbar() {
	const publishButton = document.querySelector(
		'button[aria-label="Publish"]'
	);

	publishButton.focus();
}
