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

import {useEventListener} from '@liferay/frontend-js-react-web';
import {useConfig} from 'data-engine-js-components-web';
import {useEffect} from 'react';

const toggleFormBuilder = (managementToolbar) => {
	const formBuilderButtons = document.querySelectorAll(
		'.toolbar-group-field .nav-item .lfr-ddm-button'
	);
	const publishIcon = document.querySelector('.publish-icon');

	if (managementToolbar) {
		managementToolbar.classList.remove('hide');
	}

	formBuilderButtons.forEach((formBuilderButton) => {
		formBuilderButton.classList.remove('hide');
	});

	if (publishIcon) {
		publishIcon.classList.remove('hide');
	}

	return () => {
		if (managementToolbar) {
			managementToolbar.classList.add('hide');
		}

		formBuilderButtons.forEach((formBuilderButton) => {
			formBuilderButton.classList.add('hide');
		});

		if (publishIcon) {
			publishIcon.classList.add('hide');
		}
	};
};

const toggleRules = (managementToolbar) => {
	const formBuilderButtons = document.querySelectorAll(
		'.toolbar-group-field .nav-item .lfr-ddm-button'
	);

	managementToolbar.classList.remove('hide');

	formBuilderButtons.forEach((formBuilderButton) => {
		formBuilderButton.classList.add('hide');
	});

	return () => {
		managementToolbar.classList.add('hide');
	};
};

/**
 * This is a fake component that only takes advantage of the React lifecycle
 * to manipulate the visibility of the Management Toolbar, it is currently
 * rendered via JSP and it is necessary to control visibility via JavaScript.
 *
 * Creates a simulation that this component is in React and deals with the
 * visibility of elements in the DOM and events such as click.
 */
export const ManagementToolbar = ({
	onPlusClick,
	onPreviewClick,
	onPublishClick,
	onSaveClick,
	onShareClick,
	portletNamespace,
	variant = 'builder',
}) => {
	const {published, showPublishAlert} = useConfig();

	useEffect(() => {
		const managementToolbar = document.querySelector(
			`#${portletNamespace}managementToolbar`
		);

		const toggle = variant === 'builder' ? toggleFormBuilder : toggleRules;

		return toggle(managementToolbar);

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		const button = document.getElementById('addFieldButton');

		if (onPlusClick) {
			button.classList.remove('hide');
		}
		else {
			button.classList.add('hide');
		}
	}, [onPlusClick]);

	useEffect(() => {
		const shareButton = document.querySelector('.lfr-ddm-share-url-button');

		if (showPublishAlert && published) {
			shareButton.removeAttribute('title');
		}
	}, [showPublishAlert, published]);

	useEventListener(
		'click',
		onPlusClick,
		true,
		document.getElementById('addFieldButton')
	);

	useEventListener(
		'click',
		onPreviewClick,
		true,
		document.querySelector('.lfr-ddm-preview-button')
	);

	useEventListener(
		'click',
		onPublishClick,
		true,
		document.querySelector('.lfr-ddm-publish-button')
	);

	useEventListener(
		'click',
		onSaveClick,
		true,
		document.querySelector('.lfr-ddm-save-button')
	);

	useEventListener(
		'click',
		onShareClick,
		true,
		document.querySelector('.lfr-ddm-share-url-button')
	);

	return null;
};
